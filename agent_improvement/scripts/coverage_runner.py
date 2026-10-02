"""
JaCoCo-based coverage measurement.

Mutation testing (PIT) was extracted into `mutation_measurement.py`.
Callers run the two measurements in sequence and merge the dicts.

TWO measurement paths are used depending on the test type:

  PATH A ─ Direct javac + Java-agent (EvoSuite JUnit-4 tests)
  ─────────────────────────────────────────────────────────────
  Why: The project's pom.xml has no evosuite-standalone-runtime dependency,
  so Maven cannot compile EvoSuite tests.  Even if it could, EvoSuite's
  default separateClassLoader=true discards JaCoCo's runtime instrumentation
  (see EvoSuite docs: "JaCoCo with runtime instrumentation: does not work
  with (1), requires (2)").

  How:
    1. javac  — compile test + scaffolding with project_cp + evosuite-runtime
    2. java   — run with TWO java agents:
                  -javaagent:jacocoagent.jar   → JaCoCo coverage probes
                  -javaagent:evosuite-runtime  → EvoSuite InstrumentingAgent
               Runner: org.junit.runner.JUnitCore <TestFQN>
    3. jacococli.jar report  — convert jacoco.exec → jacoco.xml
    4. parse_coverage()      — extract metrics + covered line numbers

  PATH B ─ Maven Surefire (manual JUnit-5 tests)
  ─────────────────────────────────────────────────────────────
  Why: Manual test suites use JUnit 5 (Jupiter).  Maven Surefire handles
  JUnit 5 automatically; no Java agent ordering issues.

  How:
    1. Copy test to src/test/java/<pkg>/ inside the project tree
    2. mvn clean test jacoco:report  (project's own JaCoCo config)
    3. Read target/site/jacoco/jacoco.xml → copy to work_dir
    4. Clean up temp file

Auto-routing: the measure() entry point checks whether the test source
contains "org.evosuite" and routes accordingly.

EvoSuite generation MUST use -Duse_separate_classloader=false
(set in evosuite_runner.py) so that InstrumentingAgent mode is active.
"""
from __future__ import annotations

import logging
import os
import re
import shutil
import subprocess
import tempfile
import xml.etree.ElementTree as ET
from dataclasses import dataclass, field, asdict
from pathlib import Path
from typing import Optional

import build_executor
import pipeline_paths

log = logging.getLogger(__name__)

PROJECT_ROOT = Path(__file__).parent.parent


# ─────────────────────────────────────────────────────────────────────────────
# Data types
# ─────────────────────────────────────────────────────────────────────────────

@dataclass
class CoverageResult:
    line_covered: int = 0
    line_missed: int = 0
    branch_covered: int = 0
    branch_missed: int = 0
    method_covered: int = 0
    method_missed: int = 0
    # Granular hit data for exact-equivalence checks between original and
    # improved tests:
    #
    #   covered_lines       sorted list of line numbers where ≥1 instruction
    #                       was executed   (cb > 0 OR ci > 0)
    #   missed_lines        sorted list of line numbers where the line exists
    #                       in source but had zero instruction hits
    #   branches_by_line    per-line branch breakdown read from JaCoCo's
    #                       <line nr=… cb=… mb=…/> attributes — a list of
    #                       {"line": int, "covered_branches": int,
    #                                     "missed_branches": int}
    #                       suitable for "do both tests take the same branches
    #                       on every line?" comparisons
    covered_lines:    list = field(default_factory=list)
    missed_lines:     list = field(default_factory=list)
    branches_by_line: list = field(default_factory=list)

    @property
    def line_pct(self) -> float:
        total = self.line_covered + self.line_missed
        return round(self.line_covered / total * 100, 2) if total else 0.0

    @property
    def branch_pct(self) -> float:
        total = self.branch_covered + self.branch_missed
        return round(self.branch_covered / total * 100, 2) if total else 0.0

    def to_dict(self) -> dict:
        d = asdict(self)
        d["line_pct"] = self.line_pct
        d["branch_pct"] = self.branch_pct
        return d


# ─────────────────────────────────────────────────────────────────────────────
# Classpath helpers  (used by LLM repair loop in main.py)
# ─────────────────────────────────────────────────────────────────────────────

def build_manual_classpath(project_cp: str, compiled_test_dir: str, cfg) -> str:
    parts = [compiled_test_dir, project_cp,
             str(PROJECT_ROOT / cfg.tools.junit_jar),
             str(PROJECT_ROOT / cfg.tools.hamcrest_jar)]
    return ":".join(p for p in parts if p)


def build_evosuite_classpath(project_cp: str, compiled_test_dir: str, cfg) -> str:
    parts = [compiled_test_dir, project_cp,
             str(PROJECT_ROOT / cfg.tools.junit_jar),
             str(PROJECT_ROOT / cfg.tools.hamcrest_jar),
             str(PROJECT_ROOT / cfg.tools.evosuite_runtime)]
    return ":".join(p for p in parts if p)


# ─────────────────────────────────────────────────────────────────────────────
# Step 0: javac compilation  (used by LLM repair loop AND Path-A coverage)
# ─────────────────────────────────────────────────────────────────────────────

def compile_test(java_file: str, classpath: str, output_dir: str,
                 javac_path: str = "javac",
                 extra_files: list = None,
                 release: Optional[int] = None,
                 sourcepath: Optional[str] = None) -> tuple[bool, str]:
    """
    Compile a .java file (and optional extra files) with javac.
    Returns (success, stderr).

    `release` (when set) adds `--release N` so the test compiles against the
    SAME Java API level the project declares in its pom (detected via
    build_executor.detect_java_release). This mirrors the `--release` flag the
    agent's sandbox compile.sh already uses, and protects projects pinned to an
    older Java level from a newer default javac silently targeting Java 11.

    Graceful fallback: if the local javac is too old to target that release
    (javac errors with "release version N not supported"), we transparently
    retry WITHOUT --release rather than fail — the project may still run on the
    default target. Pass `release=None` (the default) to keep old behaviour.
    """
    Path(output_dir).mkdir(parents=True, exist_ok=True)
    files = [java_file] + (extra_files or [])
    base = [javac_path, "-encoding", "UTF-8"]
    # `-sourcepath` lets javac compile referenced helper SOURCES on demand
    # (e.g. a project's test-only TestUtils) when their .class files aren't
    # already built — used by PATH C where target/test-classes may be empty.
    sp = ["-sourcepath", sourcepath] if sourcepath else []
    tail = ["-cp", classpath, "-d", output_dir] + sp + files
    rel  = ["--release", str(int(release))] if release else []

    result = subprocess.run(base + rel + tail, capture_output=True, text=True)
    if (result.returncode != 0 and rel
            and "release version" in (result.stderr or "")):
        log.warning("[coverage_runner] javac cannot target --release %s; "
                    "retrying without it for %s", release, Path(java_file).name)
        result = subprocess.run(base + tail, capture_output=True, text=True)
    if result.returncode != 0:
        log.debug("[coverage_runner] Compile FAIL: %s\n%s", java_file, result.stderr)
        return False, result.stderr
    return True, ""


# ─────────────────────────────────────────────────────────────────────────────
# Path-A helpers
# ─────────────────────────────────────────────────────────────────────────────


def _needs_junit5_plugin(java_file: str) -> bool:
    """Return True if the test source appears to use JUnit 5 / Jupiter.

    Routing semantics for PIT's --testPlugin:
      True  → use `--testPlugin junit5` (PIT JUnit 5 plugin jar required)
      False → use `--testPlugin junit`, which covers BOTH JUnit 4 and
              JUnit 3 (PIT's `junit` plugin uses JUnit 4's runner, and
              JUnit 4 transparently runs JUnit 3 `extends TestCase`
              tests via JUnit38ClassRunner — so the junit-4.13.2.jar we
              already ship is enough for all three legacy versions).
    """
    try:
        source = Path(java_file).read_text(encoding="utf-8", errors="replace")
    except OSError:
        return False
    return (
        "org.junit.jupiter" in source
        or "org.junit.platform" in source
    )


def _get_java_tools(cfg) -> tuple[str, str]:
    """Return (javac_path, java_path) from config."""
    java_home = getattr(cfg.tools, "java_home_compile", "")
    if java_home:
        return (str(Path(java_home) / "bin" / "javac"),
                str(Path(java_home) / "bin" / "java"))
    return "javac", "java"


def _build_exec_classpath(project_cp: str, compiled_dir: str, cfg,
                           include_evosuite: bool) -> str:
    """Build the classpath for direct javac/java execution.

    Order matters: our pinned JUnit 4.13.2 + Hamcrest go BEFORE `project_cp`.
    Some projects drag in an ancient junit (e.g. commons-jxpath pulls
    junit:junit:3.8.1) which also ships `junit.runner.Version` and the
    `junit.framework.*` packages. If that jar precedes ours, PIT reads
    Version.id() = "3.8.1" and aborts with "a recent version of JUnit 4 must
    be on the classpath", so mutation produces nothing for EvoSuite suites.
    Putting 4.13.2 first makes it win. It does NOT shadow JUnit 5
    (org.junit.jupiter.*) used by modern manual tests — different packages."""
    parts = [compiled_dir,
             str(PROJECT_ROOT / cfg.tools.junit_jar),
             str(PROJECT_ROOT / cfg.tools.hamcrest_jar)]
    if include_evosuite:
        parts.append(str(PROJECT_ROOT / cfg.tools.evosuite_runtime))
    parts.append(project_cp)
    return ":".join(p for p in parts if p)


def _run_jacoco_report(java: str, jacococli_jar: str, exec_file,
                       classfiles_dir: str, sourcefiles_dir: str,
                       xml_output: str, html_output: str = "") -> bool:
    """
    Convert one OR MORE jacoco.exec files into jacoco.xml using jacococli.jar.
    `exec_file` may be a single path (str) or a list of paths — jacococli's
    `report` command accepts multiple execfiles and merges them (used by the
    Maven path to combine the parent-JVM and forked-test-JVM coverage dumps).
    When `html_output` is set, ALSO emit a browsable HTML report there (open
    its index.html to see which exact lines/branches each test covered, with
    green/red/yellow highlighting). Returns True on success.
    """
    exec_list = exec_file if isinstance(exec_file, (list, tuple)) else [exec_file]
    exec_list = [str(e) for e in exec_list if e]
    cmd = [
        java, "-jar", jacococli_jar,
        "report", *exec_list,
        "--classfiles", classfiles_dir,
        "--xml", xml_output,
    ]
    if html_output:
        cmd += ["--html", html_output]
    if Path(sourcefiles_dir).exists():
        cmd += ["--sourcefiles", sourcefiles_dir]

    log.debug("[coverage_runner] jacococli: %s", " ".join(cmd))
    result = subprocess.run(cmd, capture_output=True, text=True, timeout=120)
    if result.returncode != 0:
        log.warning("[coverage_runner] jacococli report failed:\n%s",
                    result.stderr[-1000:])
    if html_output and Path(html_output).exists():
        log.info("[coverage_runner] JaCoCo HTML report: %s/index.html", html_output)
    return Path(xml_output).exists()


def _jacoco_html_dir(work_dir, cfg) -> str:
    """HTML report dir under `work_dir`, or '' when tools.jacoco_html is off.
    Enable with `tools.jacoco_html: true` to also get a browsable, line-by-line
    coverage report next to each test's jacoco.xml."""
    if getattr(getattr(cfg, "tools", None), "jacoco_html", False):
        return str(Path(work_dir) / "html")
    return ""


# ─────────────────────────────────────────────────────────────────────────────
# PATH A: Direct javac + Java-agent execution  (EvoSuite JUnit-4 tests)
# ─────────────────────────────────────────────────────────────────────────────

def run_coverage_direct(
    test_java_path: str,
    test_class_name: str,
    project_classpath: str,
    sut_classes_dir: str,
    work_dir: str,
    cfg,
    scaffolding_java: str = None,
    timeout: int = 180,
    is_evosuite: bool = True,
) -> Optional[str]:
    """
    Compile & run an EvoSuite test directly (no Maven), collect JaCoCo XML.

    Steps:
      1. javac  ─ compile test + scaffolding with evosuite-standalone-runtime on cp
      2. java   ─ run with -javaagent:jacocoagent.jar + -javaagent:evosuite-runtime
                  using JUnitCore runner → produces jacoco.exec
      3. jacococli report ─ exec → XML

    Returns path to jacoco.xml, or None on failure.

    NOTE: `is_evosuite` is authoritative (passed by caller from state.json).
    We do NOT text-grep `org.evosuite` in the file — an LLM-improved test
    may have legitimately stripped dead `import org.evosuite.*` lines, but
    the scaffolding + runtime classpath are still needed.
    """
    work = Path(work_dir)
    work.mkdir(parents=True, exist_ok=True)
    compiled_dir = str(work / "test-classes")

    javac, java = _get_java_tools(cfg)
    use_evo = bool(is_evosuite)

    compile_cp = _build_exec_classpath(project_classpath, compiled_dir, cfg,
                                        include_evosuite=use_evo)

    # ── 1. Compile ───────────────────────────────────────────────────────────
    # Pin --release to the project's declared Java level so this measurement
    # compile matches the agent's sandbox compile (which already uses --release)
    # and respects projects pinned to an older Java version. project_dir is
    # derived from the SUT classes dir: …/<proj>/target/classes → …/<proj>.
    release = build_executor.detect_java_release(Path(sut_classes_dir).parent.parent)
    extra = [scaffolding_java] if (use_evo and scaffolding_java) else None
    ok, err = compile_test(test_java_path, compile_cp, compiled_dir, javac, extra,
                           release=release)
    if not ok:
        log.error("[coverage_runner] Compilation FAILED for %s:\n%s",
                  test_class_name, err[-1500:])
        return None
    log.info("[coverage_runner] Compiled: %s", test_class_name)

    # ── 2. Run with JaCoCo (+ EvoSuite) Java agents ──────────────────────────
    test_fqn = _infer_fqn(test_java_path)
    exec_file = str(work / "jacoco.exec")
    # CRITICAL: the JaCoCo agent defaults to append=true, which MERGES this
    # run's probes into any pre-existing destfile. If `work` is ever reused
    # (a re-measured class, a re-run pipeline, a colliding test_id), coverage
    # ACCUMULATES across runs — e.g. a per-case EvoSuite test that really
    # covers 16 lines gets reported as 46 because a previous run's exec was
    # still there. We force append=false on the agent (below) AND defensively
    # remove any stale exec so each measurement reflects ONLY this test.
    try:
        Path(exec_file).unlink()
    except FileNotFoundError:
        pass
    run_cp = _build_exec_classpath(project_classpath, compiled_dir, cfg,
                                    include_evosuite=use_evo)
    jacoco_agent     = str((PROJECT_ROOT / cfg.tools.jacoco_agent).resolve())
    evosuite_runtime = str((PROJECT_ROOT / cfg.tools.evosuite_runtime).resolve())

    # macOS: a JVM that initialises AWT (EvoSuite scaffolding touches
    # java.awt.GraphicsEnvironment) registers as a foreground GUI app — it
    # steals focus and shows "JUnitCore" in the menu bar. headless + UIElement
    # keep the test JVM a background agent (no Dock icon, no focus steal).
    run_cmd = [java, "-Djava.awt.headless=true", "-Dapple.awt.UIElement=true"]
    # JaCoCo agent FIRST — instruments bytecode as classes are loaded.
    # append=false: overwrite, never merge with a stale exec (see note above).
    run_cmd += [f"-javaagent:{jacoco_agent}=destfile={exec_file},append=false"]
    if use_evo:
        # EvoSuite InstrumentingAgent SECOND — adds its own hooks on top.
        # Required when separateClassLoader=false (already patched into
        # @EvoRunnerParameters by evosuite_runner._ensure_jacoco_compatible_annotation).
        run_cmd += [f"-javaagent:{evosuite_runtime}"]
        # Java 9+ blocks the in-process Attach API by default. EvoSuite's
        # runtime uses ByteBuddy.installExternal() to self-attach a second
        # agent at test-start, which then throws
        #   "Could not self-attach to current VM using external process"
        # Allowing self-attach removes the block. Safe in a test sandbox.
        run_cmd += ["-Djdk.attach.allowAttachSelf=true"]
    run_cmd += ["-cp", run_cp,
                "org.junit.runner.JUnitCore", test_fqn]

    log.info("[coverage_runner] Running (direct): %s", test_fqn)
    log.debug("[coverage_runner] cmd: %s", " ".join(run_cmd))

    try:
        result = subprocess.run(run_cmd, capture_output=True, text=True,
                                timeout=timeout)
    except subprocess.TimeoutExpired:
        log.error("[coverage_runner] Execution timed out for %s", test_fqn)
        return None

    if result.returncode not in (0, 1):   # JUnitCore returns 1 on test failures (OK)
        log.warning("[coverage_runner] Java exited %d for %s\nstdout: %s\nstderr: %s",
                    result.returncode, test_fqn,
                    result.stdout[-600:], result.stderr[-600:])

    # Even if tests fail, JaCoCo should have written partial coverage data
    if not Path(exec_file).exists():
        log.error("[coverage_runner] jacoco.exec not created for %s\n"
                  "stdout: %s\nstderr: %s",
                  test_fqn, result.stdout[-600:], result.stderr[-600:])
        return None

    # ── 3. Generate XML ───────────────────────────────────────────────────────
    project_dir    = str(Path(sut_classes_dir).parent.parent)
    src_main_java  = str(Path(project_dir) / "src" / "main" / "java")
    jacococli_jar  = str((PROJECT_ROOT / cfg.tools.jacoco_cli).resolve())
    jacoco_xml     = str(work / "jacoco.xml")

    ok = _run_jacoco_report(java, jacococli_jar, exec_file,
                             sut_classes_dir, src_main_java, jacoco_xml,
                             html_output=_jacoco_html_dir(work, cfg))
    if not ok:
        log.error("[coverage_runner] Failed to generate jacoco.xml for %s", test_fqn)
        return None

    log.info("[coverage_runner] Report ready (direct): %s", jacoco_xml)
    return jacoco_xml


# ─────────────────────────────────────────────────────────────────────────────
# PATH B: Maven Surefire  (manual JUnit-5 tests)
# ─────────────────────────────────────────────────────────────────────────────

def _extract_package_from_source(java_file: str) -> str:
    source = Path(java_file).read_text(encoding="utf-8")
    m = re.search(r"^\s*package\s+([\w.]+)\s*;", source, re.MULTILINE)
    return m.group(1) if m else ""


def run_coverage_maven(
    test_java_path: str,
    test_class_name: str,
    project_dir: str,
    work_dir: str,
    cfg,
    timeout: int = 300,
    extra_java_files: list[str] = None,
    sut_classes_dir: str = "",
    mvn_module: str = "",
) -> Optional[str]:
    """Run Maven Surefire with our own JaCoCo agent + jacococli report.

    This bypasses two common pitfalls of relying on the project's pom:
      • Projects whose pom does NOT declare jacoco-maven-plugin would
        skip `jacoco:report` ("Skipping JaCoCo execution due to missing
        execution data file"), and
      • Projects that hardcode `<argLine>...</argLine>` in surefire config
        (e.g. jsoup's `-Xss640k`) ignore CLI `-DargLine=...`, so the
        JaCoCo agent never attaches to the forked test JVM.

    Strategy:
      1. Copy the JaCoCo agent jar to a space-free path so `-javaagent:`
         survives JVM tokenisation (the project itself may live under a
         path containing spaces).
      2. Tell Surefire NOT to fork (`forkCount=0`), so tests run inside
         the Maven JVM itself.
      3. Attach the JaCoCo agent via `MAVEN_OPTS` — the Maven JVM picks it
         up, and any test running in-process is instrumented.
      4. Run `mvn test -Dtest=...` to compile + execute → produces
         `jacoco.exec`.
      5. Convert .exec → .xml via `jacococli`.
    """
    project = Path(project_dir)
    project_pom = project / "pom.xml"
    if not project_pom.exists():
        log.error("[coverage_runner] No pom.xml in %s", project_dir)
        return None

    # Maven RE-COMPILES the project here, so prefer a COMPILE-capable JDK
    # (`java_home_maven`, set per-project by main.py to the build JDK). Old
    # `-source 1.5` projects fail under the run JDK (11+), which would zero out
    # coverage. Fall back to java_home_compile when the maven JDK isn't set.
    java_home    = (getattr(cfg.tools, "java_home_maven", "")
                    or getattr(cfg.tools, "java_home_compile", ""))
    maven_path   = getattr(cfg.tools, "maven_path", "mvn")
    jacoco_agent = (PROJECT_ROOT / cfg.tools.jacoco_agent).resolve()
    jacoco_cli   = str((PROJECT_ROOT / cfg.tools.jacoco_cli).resolve())
    _, java_exe  = _get_java_tools(cfg)

    # Where to copy improved tests into the project tree. For a single-module
    # project this is `<root>/src/test/java/<pkg>/`. For multi-module (e.g.
    # guava), use the test-module's source tree: `<root>/<module>/src/test/java/<pkg>/`.
    # When the test file ALREADY lives under <project_root>/, skip the copy
    # entirely — Surefire will pick it up from its original location.
    package  = _extract_package_from_source(test_java_path)
    pkg_path = Path(*package.split(".")) if package else Path()
    module_root = project / mvn_module if mvn_module else project
    dest_dir = module_root / "src" / "test" / "java" / pkg_path
    dest_dir.mkdir(parents=True, exist_ok=True)

    work = Path(work_dir)
    work.mkdir(parents=True, exist_ok=True)
    exec_file = work / "jacoco.exec"
    if exec_file.exists():
        exec_file.unlink()

    # MAVEN_OPTS is consumed by the mvn shell script via word-splitting —
    # any space inside its value (agent path OR destfile path) causes the
    # JVM to misparse the args. Stage BOTH the agent jar AND a temporary
    # destfile under whitespace-free paths, then copy the .exec back to
    # `work` after Maven exits.
    safe_agent = _stage_agent_in_safe_path(jacoco_agent)
    safe_exec  = _safe_exec_path(test_class_name)              # parent JVM (MAVEN_OPTS)
    safe_exec_fork = _safe_exec_path(test_class_name + "__fork")  # forked test JVM (argLine)
    for _se in (safe_exec, safe_exec_fork):
        if Path(_se).exists():
            Path(_se).unlink()

    copied: list[Path] = []
    # Developer originals we move aside before overwriting them with an
    # IMPROVED test of the same name, so they can be restored in `finally`.
    # Without this, copying the improved test over the repo's own same-named
    # test and then unlinking the copy PERMANENTLY DESTROYS the original.
    preserved: list[tuple[Path, Path]] = []
    try:
        # Only copy the test into the project tree when it's not ALREADY
        # somewhere inside the project (i.e. for IMPROVED tests living under
        # data/). For ORIGINAL tests the file already sits in the right
        # module's source tree — copying creates a duplicate that Surefire
        # may discover twice or not at all (the latter happened on guava
        # because the duplicate landed in the aggregator root, outside any
        # module's source set).
        src_abs = Path(test_java_path).resolve()
        try:
            src_abs.relative_to(project.resolve())
            test_already_in_project = True
        except ValueError:
            test_already_in_project = False

        if not test_already_in_project:
            dest = dest_dir / Path(test_java_path).name
            if src_abs != dest.resolve():
                # If a developer original of the same name already lives here,
                # move it aside (restored in `finally`) so the copy + cleanup
                # below never destroys the repo's own test.
                if dest.exists():
                    _bak = dest.with_name(dest.name + ".orig_bak")
                    if _bak.exists():
                        _bak.unlink()
                    shutil.move(str(dest), str(_bak))
                    preserved.append((dest, _bak))
                shutil.copy2(test_java_path, dest)
                copied.append(dest)
            for extra in (extra_java_files or []):
                ed = dest_dir / Path(extra).name
                if Path(extra).resolve() != ed.resolve():
                    if ed.exists():
                        _ebak = ed.with_name(ed.name + ".orig_bak")
                        if _ebak.exists():
                            _ebak.unlink()
                        shutil.move(str(ed), str(_ebak))
                        preserved.append((ed, _ebak))
                    shutil.copy2(extra, ed)
                    copied.append(ed)

        env = os.environ.copy()
        if java_home:
            env["JAVA_HOME"] = java_home
            env["PATH"] = f"{java_home}/bin:" + env.get("PATH", "")
        # TWO JaCoCo agents, two destfiles, to cover BOTH execution layouts:
        #  (A) MAVEN_OPTS agent → instruments the Maven JVM itself. Wins when
        #      -DforkCount=0 is honoured (tests run in-process).
        #  (B) -DargLine agent → instruments the *forked* test JVM. Wins when
        #      the project's pom hardcodes surefire forkCount≥1 (e.g. commons-io
        #      sets forkCount=1/reuseForks=false), so our -DforkCount=0 is
        #      ignored and the in-process MAVEN_OPTS agent sees zero CUT classes
        #      (it dumped the parent JVM, not the fork → coverage came back 0%).
        #      Surefire's argLine uses late-binding `@{argLine}` in most modern
        #      poms, so a CLI -DargLine reaches the fork; where the pom has no
        #      argLine config, -DargLine sets it directly.
        # jacococli merges both .exec files for the report, so whichever JVM
        # actually ran the tests contributes its coverage.
        env["MAVEN_OPTS"] = (
            env.get("MAVEN_OPTS", "")
            + f" -javaagent:{safe_agent}=destfile={safe_exec},append=false"
        ).strip()

        cmd = [
            maven_path, "-q", "test",
            "-f", str(project_pom),
            f"-Dtest={test_class_name}",
            f"-DargLine=-javaagent:{safe_agent}=destfile={safe_exec_fork},append=false",
            "-DforkCount=0",                          # try to run in Maven JVM
            "-Dmaven.test.failure.ignore=true",
            # With multi-module + -pl + -am, `mvn test` runs Surefire in
            # every reachable module. The test class only exists in one of
            # them, so Surefire would fail with "No tests matching pattern"
            # in the others. This flag downgrades that to a warning.
            "-Dsurefire.failIfNoSpecifiedTests=false",
            "-Drat.skip=true",
            "-Denforcer.skip=true",
            "-Dcheckstyle.skip=true",
            "-Dspotbugs.skip=true",
            "-Dpmd.skip=true",
            "--no-transfer-progress",
        ]
        # Multi-module Maven: only operate on the submodule that owns the test.
        # `-am` (also-make) builds any required upstream submodules first so
        # the SUT module's classes are available.
        if mvn_module:
            cmd += ["-pl", mvn_module, "-am"]
        log.info("[coverage_runner] mvn test + jacoco-agent: %s", test_class_name)

        try:
            result = subprocess.run(cmd, cwd=str(project),
                                    capture_output=True, text=True,
                                    env=env, timeout=timeout)
        except subprocess.TimeoutExpired:
            log.error("[coverage_runner] Maven timed out for %s", test_class_name)
            (work / "maven_timeout.txt").write_text(
                f"Timed out after {timeout} seconds while running: {' '.join(cmd)}\n",
                encoding="utf-8",
            )
            return None

        (work / "maven_stdout.txt").write_text(result.stdout or "", encoding="utf-8")
        (work / "maven_stderr.txt").write_text(result.stderr or "", encoding="utf-8")

        # Bring BOTH .exec files back into work/ (parent + fork). jacococli
        # merges them, so whichever JVM ran the tests contributes coverage.
        exec_files: list[str] = []
        exec_fork = work / "jacoco_fork.exec"
        for src_exec, dst_exec in ((safe_exec, exec_file), (safe_exec_fork, exec_fork)):
            sp = Path(src_exec)
            if sp.exists() and sp.stat().st_size > 0:
                shutil.copy2(sp, dst_exec)
                exec_files.append(str(Path(dst_exec).resolve()))
            sp.unlink(missing_ok=True)

        if not exec_files:
            log.warning("[coverage_runner] no jacoco.exec produced for %s "
                        "(mvn exit=%d). See %s for details.",
                        test_class_name, result.returncode, work / "maven_stdout.txt")
            return None

        # ── Convert .exec → .xml via jacococli ─────────────────────────────
        # Honour an explicit `sut_classes_dir` so multi-module projects
        # point jacococli at the right submodule's compiled classes.
        if sut_classes_dir and Path(sut_classes_dir).exists():
            classes_dir = Path(sut_classes_dir)
        else:
            classes_dir = (project / mvn_module / "target" / "classes") if mvn_module \
                          else (project / "target" / "classes")
        src_main = (project / mvn_module / "src" / "main" / "java") if mvn_module \
                   else (project / "src" / "main" / "java")
        xml_out     = work / "jacoco.xml"
        ok = _run_jacoco_report(
            java=java_exe,
            jacococli_jar=jacoco_cli,
            exec_file=exec_files,          # merge parent + fork execs
            classfiles_dir=str(classes_dir.resolve()),
            sourcefiles_dir=str(src_main.resolve()),
            xml_output=str(xml_out.resolve()),
            html_output=_jacoco_html_dir(work, cfg),
        )
        if not ok or not xml_out.exists():
            log.warning("[coverage_runner] jacococli failed to produce XML for %s",
                        test_class_name)
            return None
        log.info("[coverage_runner] Report ready (Maven): %s", xml_out)
        return str(xml_out)

    finally:
        for f in copied:
            try:
                f.unlink(missing_ok=True)
            except Exception:
                pass
        # Restore any developer originals moved aside above, so measuring an
        # IMPROVED test never leaves the repo's own same-named test deleted.
        for _orig, _bak in preserved:
            try:
                if _bak.exists():
                    shutil.move(str(_bak), str(_orig))
            except Exception:
                pass


def _stage_agent_in_safe_path(agent_jar: Path) -> str:
    """Return a whitespace-free path to the JaCoCo agent jar.

    `-javaagent:<path>=<opts>` is tokenised by the JVM on whitespace, so
    if PROJECT_ROOT contains spaces (e.g. "…/New_Agent…_improvement copy/"),
    the JVM fails to load the agent. We cache a copy under a temp dir whose
    absolute path is guaranteed not to have spaces.
    """
    if " " not in str(agent_jar):
        return str(agent_jar)
    cache = Path(tempfile.gettempdir()) / "jacocoagent_safe.jar"
    if (not cache.exists()) or cache.stat().st_mtime < agent_jar.stat().st_mtime:
        shutil.copy2(agent_jar, cache)
    return str(cache)


def _safe_exec_path(test_class_name: str) -> str:
    """Return a whitespace-free path for the JaCoCo destfile.

    The mvn shell script consumes `MAVEN_OPTS` via bash word-splitting, so
    spaces inside `destfile=<path>` get re-interpreted as separate JVM args.
    We write the .exec to a stable per-test name under `/tmp/jacoco_exec/`
    (no spaces guaranteed) and copy it back to the caller's work_dir after
    Maven exits.
    """
    safe_dir = Path(tempfile.gettempdir()) / "jacoco_exec"
    safe_dir.mkdir(parents=True, exist_ok=True)
    return str(safe_dir / f"{test_class_name}.exec")


# ─────────────────────────────────────────────────────────────────────────────
# PATH C: Direct JUnit-5 execution  (manual JUnit-5 tests, Maven-fork fallback)
# ─────────────────────────────────────────────────────────────────────────────

_JUNIT5_RUNNER_SRC = PROJECT_ROOT / "tools" / "junit5runner" / "JUnit5Runner.java"


def _resolve_platform_launcher(project_cp: str, cfg) -> str:
    """Return a junit-platform-launcher jar to APPEND to the PATH-C classpath,
    or "" if the launcher is already present (no action needed) / none found.

    Why: PATH C compiles+runs JUnit5Runner, which uses
    `org.junit.platform.launcher.*`. Most project poms declare only
    `junit-jupiter-api/engine` and rely on Surefire's bundled launcher, so the
    launcher is NOT on the project's own classpath (jfreechart is exactly this
    case → PATH C runner compile failed → 0% coverage). We supply it ourselves:

      1. If a `junit-platform-launcher-*.jar` is already on `project_cp`, use it.
      2. Else read the `junit-platform-engine-<ver>.jar` version on `project_cp`
         and pick the SAME-version launcher from ~/.m2 (launcher ⇄ engine must
         be the same JUnit-Platform version).
      3. Else fall back to a configured `cfg.tools.junit_platform_launcher_jar`,
         then to the newest launcher jar found anywhere in ~/.m2.
    """
    entries = [e for e in (project_cp or "").split(os.pathsep) if e]
    for e in entries:
        if re.search(r"junit-platform-launcher-[\d.]", os.path.basename(e)):
            return ""  # already on cp

    m2 = Path.home() / ".m2" / "repository" / "org" / "junit" / "platform" / "junit-platform-launcher"

    # Derive the platform version from junit-platform-engine on the cp.
    want_ver = ""
    for e in entries:
        mm = re.search(r"junit-platform-engine-([\d.]+(?:-\w+)?)\.jar$", os.path.basename(e))
        if mm:
            want_ver = mm.group(1)
            break
    if want_ver:
        cand = m2 / want_ver / f"junit-platform-launcher-{want_ver}.jar"
        if cand.exists():
            return str(cand)

    # Configured fallback.
    cfg_jar = getattr(getattr(cfg, "tools", None), "junit_platform_launcher_jar", "") or ""
    if cfg_jar:
        p = (PROJECT_ROOT / cfg_jar) if not os.path.isabs(cfg_jar) else Path(cfg_jar)
        if p.exists():
            return str(p)

    # Last resort: newest launcher anywhere in ~/.m2 (launcher is backward
    # compatible with same-or-older engines).
    if m2.exists():
        found = sorted(m2.glob("*/junit-platform-launcher-*.jar"),
                       key=lambda p: p.stat().st_mtime)
        if found:
            return str(found[-1])
    return ""


def _project_test_source_roots(project_dir: str) -> list[str]:
    """All `src/test/java` roots under the project (single- or multi-module).

    Passed to javac as `-sourcepath` in PATH C so that test-helper classes the
    suite references (e.g. jfreechart's `org.jfree.chart.TestUtils`) are
    compiled on demand — the project's `target/test-classes` is often empty at
    measurement time, so these helpers are otherwise unresolved → compile fail."""
    root = Path(project_dir)
    if not root.exists():
        return []
    roots = []
    # Common single-module location first.
    direct = root / "src" / "test" / "java"
    if direct.is_dir():
        roots.append(str(direct))
    # Multi-module: <root>/<module>/src/test/java (bounded glob).
    for p in root.glob("*/src/test/java"):
        if p.is_dir() and str(p) not in roots:
            roots.append(str(p))
    return roots


def _compile_junit5_runner(javac: str, project_cp: str, cfg) -> Optional[str]:
    """Compile tools/junit5runner/JUnit5Runner.java against the JUnit-Platform
    launcher and return the dir holding JUnit5Runner.class, or None if it can't
    be compiled.

    The launcher is resolved via `_resolve_platform_launcher` (appended to the
    project cp when the pom doesn't declare it) — so PATH C no longer depends on
    the project happening to put junit-platform-launcher on its own classpath.

    The compiled runner is cached per-launcher-jar (the launcher path is part of
    the cache key) so projects on different platform versions don't clobber each
    other's JUnit5Runner.class."""
    if not _JUNIT5_RUNNER_SRC.exists():
        return None
    launcher = _resolve_platform_launcher(project_cp, cfg)
    compile_cp = (project_cp + (os.pathsep + launcher if launcher else "")) or "."
    # Cache key incorporates the launcher so different platform versions don't
    # share a stale JUnit5Runner.class.
    key = re.sub(r"[^\w.-]", "_", Path(launcher).name) if launcher else "oncp"
    out = Path(tempfile.gettempdir()) / f"junit5runner_classes__{key}"
    out.mkdir(parents=True, exist_ok=True)
    klass = out / "JUnit5Runner.class"
    if klass.exists() and klass.stat().st_mtime >= _JUNIT5_RUNNER_SRC.stat().st_mtime:
        return str(out)
    cmd = [javac, "-encoding", "UTF-8", "-cp", compile_cp, "-d", str(out),
           str(_JUNIT5_RUNNER_SRC)]
    res = subprocess.run(cmd, capture_output=True, text=True)
    if res.returncode != 0:
        log.debug("[coverage_runner] PATH C runner compile failed (no platform "
                  "launcher?):\n%s", res.stderr[-600:])
        return None
    return str(out)


def run_coverage_direct_junit5(
    test_java_path: str,
    target_class_fqn: str,
    project_classpath: str,
    sut_classes_dir: str,
    work_dir: str,
    cfg,
    timeout: int = 180,
) -> Optional[str]:
    """Run a manual JUnit-5 test DIRECTLY (no Maven) with the JaCoCo agent.

    Why this exists: PATH B (Maven Surefire) breaks when a project's pom forces
    a surefire fork (commons-io: forkCount=1/reuseForks=false) and binds the
    agent via late `@{argLine}` — our agent then instruments the parent Maven
    JVM, not the fork, so coverage comes back 0% even though the tests run.
    Running the JUnit Platform Launcher in THIS JVM (one process, agent
    attached at startup) sidesteps all surefire fork/argLine quirks.

    Returns path to jacoco.xml, or None if Path C is not applicable (no
    platform launcher, compile fail, or no exec produced) — caller falls back
    to PATH B.
    """
    work = Path(work_dir); work.mkdir(parents=True, exist_ok=True)
    compiled_dir = str(work / "test-classes")
    javac, java = _get_java_tools(cfg)

    runner_dir = _compile_junit5_runner(javac, project_classpath, cfg)
    if runner_dir is None:
        return None

    # The JUnit-Platform launcher is usually NOT on the project's own cp (poms
    # declare only jupiter-api/engine and rely on Surefire's bundled launcher).
    # Supply it so both the runner and the test JVM can launch JUnit 5.
    launcher = _resolve_platform_launcher(project_classpath, cfg)
    launcher_cp = (os.pathsep + launcher) if launcher else ""

    # `src/test/java` roots so javac auto-compiles test-only helpers the suite
    # references (e.g. jfreechart `TestUtils`) when target/test-classes is empty.
    project_dir = str(Path(sut_classes_dir).parent.parent)
    test_roots = _project_test_source_roots(project_dir)
    sourcepath = os.pathsep.join(test_roots) if test_roots else None

    # Compile the test against the project cp (which carries junit-jupiter +
    # the project's own target/test-classes for any inherited base classes).
    compile_cp = ":".join([compiled_dir, project_classpath,
                           str(PROJECT_ROOT / cfg.tools.junit_jar),
                           str(PROJECT_ROOT / cfg.tools.hamcrest_jar)]) + launcher_cp
    ok, err = compile_test(test_java_path, compile_cp, compiled_dir, javac, None,
                           sourcepath=sourcepath)
    if not ok:
        log.debug("[coverage_runner] PATH C test compile failed for %s\n%s",
                  Path(test_java_path).name, err[-600:])
        return None

    test_fqn = _infer_fqn(test_java_path)
    exec_file = str(work / "jacoco.exec")
    jacoco_agent = str((PROJECT_ROOT / cfg.tools.jacoco_agent).resolve())
    run_cp = ":".join([runner_dir, compiled_dir, project_classpath,
                       str(PROJECT_ROOT / cfg.tools.junit_jar),
                       str(PROJECT_ROOT / cfg.tools.hamcrest_jar)]) + launcher_cp
    run_cmd = [java,
               # macOS: keep the test JVM headless / a background agent so it
               # doesn't grab focus or show in the menu bar (see note above).
               "-Djava.awt.headless=true", "-Dapple.awt.UIElement=true",
               f"-javaagent:{jacoco_agent}=destfile={exec_file},append=false",
               # harmless on JDK ≤15, needed on 16+ for any java.awt reflection
               "--add-opens=java.desktop/java.awt=ALL-UNNAMED",
               "-cp", run_cp, "JUnit5Runner", test_fqn]
    log.info("[coverage_runner] Running (direct JUnit5): %s", test_fqn)
    try:
        result = subprocess.run(run_cmd, capture_output=True, text=True, timeout=timeout)
    except subprocess.TimeoutExpired:
        log.warning("[coverage_runner] PATH C timed out for %s", test_fqn)
        return None
    # --add-opens is rejected by JDK 8 ("Unrecognized option"); retry without it.
    if result.returncode == 2 and "add-opens" in (result.stderr or ""):
        run_cmd = [c for c in run_cmd if not c.startswith("--add-opens")]
        result = subprocess.run(run_cmd, capture_output=True, text=True, timeout=timeout)

    if not Path(exec_file).exists() or Path(exec_file).stat().st_size == 0:
        log.debug("[coverage_runner] PATH C produced no exec for %s\nstderr: %s",
                  test_fqn, (result.stderr or "")[-600:])
        return None

    src_main_java = str(Path(project_dir) / "src" / "main" / "java")
    jacococli_jar = str((PROJECT_ROOT / cfg.tools.jacoco_cli).resolve())
    jacoco_xml    = str(work / "jacoco.xml")
    ok = _run_jacoco_report(java, jacococli_jar, exec_file,
                            sut_classes_dir, src_main_java, jacoco_xml,
                            html_output=_jacoco_html_dir(work, cfg))
    if not ok:
        return None
    log.info("[coverage_runner] Report ready (direct JUnit5): %s", jacoco_xml)
    return jacoco_xml


# ─────────────────────────────────────────────────────────────────────────────
# Parse JaCoCo XML  (shared by both paths)
# ─────────────────────────────────────────────────────────────────────────────

def parse_coverage(xml_path: str, target_class_fqn: str) -> Optional[CoverageResult]:
    """
    Parse a JaCoCo XML report and return metrics for target_class_fqn.

    Extracts both aggregate counters (LINE/BRANCH/METHOD) from <class> elements
    AND per-line hit data from <sourcefile> → <line> elements.
    """
    if not Path(xml_path).exists():
        log.warning("[coverage_runner] XML not found: %s", xml_path)
        return None

    slash_name   = target_class_fqn.replace(".", "/")           # org/apache/.../HexDump
    pkg_slash    = "/".join(slash_name.split("/")[:-1])          # org/apache/commons/io
    simple_name  = slash_name.split("/")[-1]                     # HexDump
    source_fname = simple_name + ".java"                         # HexDump.java

    metrics   = {"LINE": [0, 0], "BRANCH": [0, 0], "METHOD": [0, 0]}
    cov_lines : list[int] = []
    miss_lines: list[int] = []
    branches_by_line: list[dict] = []
    class_found = False
    src_found   = False

    try:
        tree = ET.parse(xml_path)
        root = tree.getroot()
        for package in root.findall("package"):
            pkg_name = package.get("name", "")

            # ── aggregate counters from <class> ──────────────────────────────
            # Aggregate the OUTER class AND all of its nested/inner classes
            # (JaCoCo names them "Outer$Inner", "Outer$1", etc.). Many targets
            # keep their real logic in an inner class — e.g. the jackson
            # annotation CUTs put everything in `JsonIgnoreProperties$Value`,
            # leaving the outer class with a single line. Matching only the
            # exact name reported line_pct/branch_pct = 0% for those classes
            # even though the inner class was ~90% covered. The per-line data
            # below is already file-scoped (covers inner classes), so exact-
            # match was correct; this fixes the aggregate percentages to agree.
            inner_prefix = slash_name + "$"
            for cls in package.findall("class"):
                cname = cls.get("name", "")
                if cname == slash_name or cname.startswith(inner_prefix):
                    class_found = True
                    for counter in cls.findall("counter"):
                        ctype = counter.get("type")
                        if ctype in metrics:
                            metrics[ctype][0] += int(counter.get("missed", 0))
                            metrics[ctype][1] += int(counter.get("covered", 0))

            # ── per-line hit data from <sourcefile> ──────────────────────────
            if pkg_name == pkg_slash:
                for sf in package.findall("sourcefile"):
                    if sf.get("name", "") == source_fname:
                        src_found = True
                        for line in sf.findall("line"):
                            nr = int(line.get("nr", 0))
                            ci = int(line.get("ci", 0))  # covered instructions
                            mi = int(line.get("mi", 0))  # missed instructions
                            cb = int(line.get("cb", 0))  # covered branches
                            mb = int(line.get("mb", 0))  # missed branches
                            if ci > 0:
                                cov_lines.append(nr)
                            elif mi > 0:
                                miss_lines.append(nr)
                            # Only record lines that actually have branches
                            if cb or mb:
                                branches_by_line.append({
                                    "line": nr,
                                    "covered_branches": cb,
                                    "missed_branches": mb,
                                })

    except ET.ParseError as e:
        log.error("[coverage_runner] XML parse error: %s", e)
        return None

    if not class_found:
        log.warning("[coverage_runner] Class not found in XML: %s", target_class_fqn)
        return None

    if not src_found:
        log.debug("[coverage_runner] Sourcefile element not found for %s "
                  "(line-level data unavailable)", source_fname)

    return CoverageResult(
        line_missed   = metrics["LINE"][0],
        line_covered  = metrics["LINE"][1],
        branch_missed = metrics["BRANCH"][0],
        branch_covered= metrics["BRANCH"][1],
        method_missed = metrics["METHOD"][0],
        method_covered= metrics["METHOD"][1],
        covered_lines = sorted(cov_lines),
        missed_lines  = sorted(miss_lines),
        branches_by_line = sorted(branches_by_line, key=lambda b: b["line"]),
    )


# ─────────────────────────────────────────────────────────────────────────────
# Consistency check helper  (used by LLM repair loop)
# ─────────────────────────────────────────────────────────────────────────────


# ─────────────────────────────────────────────────────────────────────────────
# Entry point used by main.py
# ─────────────────────────────────────────────────────────────────────────────

def measure(
    test_java_path: str,
    target_class_fqn: str,
    project_classpath: str,
    sut_classes_dir: str,
    work_dir: str,
    cfg,
    project_dir: str = "",
    is_evosuite: bool = False,
    scaffolding_java: str = None,
    mvn_module: str = "",
) -> Optional[dict]:
    """
    Measure JaCoCo line/branch coverage for one test file, plus PIT mutation
    score if `cfg.mutation_testing.enabled` is true.

    Routing:
      • is_evosuite=True (from state.json — authoritative ground truth)
            → PATH A (direct javac + java agent + evosuite-runtime classpath)
      • otherwise
            → PATH B (Maven Surefire — uses the project's normal test cp)

    NOTE: do NOT additionally text-grep "org.evosuite" in the file. An
    LLM-improved EvoSuite test may legitimately strip dead
    `import org.evosuite.*` lines (e.g. if the case doesn't actually use
    EvoAssertions), which used to misroute the test to PATH B → Maven
    classpath missing JUnit 4 + EvoSuite scaffolding → compile fail →
    coverage falsely reported as 0%. Trust state.json.
    """
    test_java = Path(test_java_path)
    test_class_name = _infer_simple_class_name(str(test_java))

    # ── Route ─────────────────────────────────────────────────────────────────
    if is_evosuite:
        xml_path = run_coverage_direct(
            test_java_path   = str(test_java),
            test_class_name  = test_class_name,
            project_classpath= project_classpath,
            sut_classes_dir  = str(sut_classes_dir),
            work_dir         = work_dir,
            cfg              = cfg,
            scaffolding_java = scaffolding_java,
            is_evosuite      = True,    # propagate ground truth
        )
    else:
        project_dir = str(project_dir or _find_project_dir(cfg))
        xml_path = run_coverage_maven(
            test_java_path  = str(test_java),
            test_class_name = test_class_name,
            project_dir     = project_dir,
            work_dir        = work_dir,
            cfg             = cfg,
            sut_classes_dir = sut_classes_dir,
            mvn_module      = mvn_module,
        )
        # PATH B → PATH C fallback. When the project's pom forces a surefire
        # fork (e.g. commons-io forkCount=1/reuseForks=false + late @{argLine}),
        # the Maven JaCoCo agent instruments the parent JVM, not the fork, so
        # PATH B returns 0% coverage for a CUT the tests actually exercise.
        # Detect that (XML missing OR the CUT shows zero covered lines) and
        # re-measure by running JUnit 5 directly in one JVM (no Maven). Only
        # triggers on the broken case, so healthy projects keep using PATH B.
        _covB = parse_coverage(xml_path, target_class_fqn) if xml_path else None
        if _covB is None or not _covB.covered_lines:
            xml_c = run_coverage_direct_junit5(
                test_java_path    = str(test_java),
                target_class_fqn  = target_class_fqn,
                project_classpath = project_classpath,
                sut_classes_dir   = str(sut_classes_dir),
                work_dir          = str(Path(work_dir) / "pathc"),
                cfg               = cfg,
            )
            cov_c = parse_coverage(xml_c, target_class_fqn) if xml_c else None
            if cov_c is not None and cov_c.covered_lines:
                log.info("[coverage_runner] PATH B reported no coverage for %s; "
                         "PATH C (direct JUnit5) recovered %d covered lines.",
                         target_class_fqn, len(cov_c.covered_lines))
                xml_path = xml_c

    if xml_path is None:
        return {"compile_success": False,
                "compile_error": "Coverage run failed — check logs"}

    cov = parse_coverage(xml_path, target_class_fqn)
    if cov is None:
        return {"compile_success": True, "coverage_available": False}

    result = {"compile_success": True, "coverage_available": True}
    result.update(cov.to_dict())

    # Mutation testing is no longer attached here — callers run
    # `mutation_measurement.measure(...)` separately and merge the result.
    return result
# ─────────────────────────────────────────────────────────────────────────────
# Internal helpers
# ─────────────────────────────────────────────────────────────────────────────

def _infer_fqn(java_file: str) -> str:
    source = Path(java_file).read_text(encoding="utf-8")
    pkg_m = re.search(r"package\s+([\w.]+)\s*;", source)
    cls_m = re.search(
        r"(?m)^\s*(?:public\s+)?(?:abstract\s+|final\s+)?class\s+(\w+)\b",
        source,
    )
    package   = pkg_m.group(1) if pkg_m else ""
    classname = cls_m.group(1) if cls_m else Path(java_file).stem
    return f"{package}.{classname}" if package else classname


def _infer_simple_class_name(java_file: str) -> str:
    source = Path(java_file).read_text(encoding="utf-8")
    cls_m = re.search(
        r"(?m)^\s*(?:public\s+)?(?:abstract\s+|final\s+)?class\s+(\w+)\b",
        source,
    )
    return cls_m.group(1) if cls_m else Path(java_file).stem


def _find_project_dir(cfg) -> Path:
    """Locate the cloned project directory under the workplace root."""
    workplace = pipeline_paths.workplace_dir()
    if workplace.exists():
        subdirs = [d for d in workplace.iterdir() if d.is_dir()]
        if subdirs:
            return subdirs[0]
    return PROJECT_ROOT
