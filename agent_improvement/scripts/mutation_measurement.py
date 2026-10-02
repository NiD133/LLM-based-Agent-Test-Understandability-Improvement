"""
PIT mutation testing for one compiled test file.

This module is the mutation-score counterpart to `coverage_runner` (which now
only measures JaCoCo line/branch coverage). Callers are expected to invoke
`measure()` separately after a coverage run when
`config.mutation_testing.enabled` is true, then merge the returned dict into
the per-test metrics payload.

Workflow:
  1. Compile the test (idempotent — javac is fast) using
     `coverage_runner.compile_test`.
  2. Invoke
        java -cp <pit_jars>:<test+sut+junit> \
             org.pitest.mutationtest.commandline.MutationCoverageReport \
             --reportDir <work>/pit_report \
             --targetClasses <SUT FQN> \
             --targetTests   <TEST FQN> \
             --sourceDirs    <SUT src/main/java> \
             --outputFormats XML \
             --timestampedReports false \
             --testPlugin    junit | junit5
  3. Parse `mutations.xml`:
        killed   = status == KILLED
        survived = everything else (SURVIVED / NO_COVERAGE / TIMED_OUT /
                                    MEMORY_ERROR / RUN_ERROR / ...)
        score    = 100 * killed / (killed + survived)
"""
from __future__ import annotations

import json
import logging
import re
import shutil
import subprocess
import xml.etree.ElementTree as ET
from pathlib import Path
from typing import Optional

import coverage_runner

log = logging.getLogger(__name__)

PROJECT_ROOT = Path(__file__).parent.parent


# ─────────────────────────────────────────────────────────────────────────────
# Public API
# ─────────────────────────────────────────────────────────────────────────────

def enabled(cfg) -> bool:
    """Return True iff mutation testing is configured to run."""
    mt = getattr(cfg, "mutation_testing", None)
    return bool(mt and getattr(mt, "enabled", False))


def measure(
    test_java_path: str,
    target_class_fqn: str,
    project_classpath: str,
    sut_classes_dir: str,
    work_dir: str,
    cfg,
    is_evosuite: bool = False,
    scaffolding_java: str = None,
) -> Optional[dict]:
    """Run PIT (Pitest CLI) on a single test file and parse the mutation report.

    Returns a dict whose keys are PIT-namespaced (`mutants_total`,
    `mutants_killed`, `mutation_score_pct`, `mutation_report_path`,
    `mutation_status_counts`, `killed_mutants*`, plus failure keys
    `mutation_error`, `mutation_log_dir`, `mutation_stdout_path`,
    `mutation_stderr_path` when something went wrong) so the caller can
    `result.update(mutation)` without overwriting coverage keys.

    Returns None if PIT is not configured at all — callers should always
    treat None as "skipped, no error".
    """
    pit_cp = _resolve_pit_classpath(cfg)
    if pit_cp is None:
        log.warning("[mutation_measurement] skipped: no PIT jars resolved")
        return _failure("Mutation testing skipped: no PIT jars resolved.")

    test_java = Path(test_java_path)
    work = Path(work_dir)
    work.mkdir(parents=True, exist_ok=True)
    compiled_dir = str(work / "test-classes")
    Path(compiled_dir).mkdir(parents=True, exist_ok=True)

    javac, java = coverage_runner._get_java_tools(cfg)
    # Trust state.json's is_evosuite flag. Don't text-grep "org.evosuite" —
    # LLM-improved tests may have legitimately removed dead imports.
    # (Same fix as coverage_runner.measure's routing.)
    use_evo = bool(is_evosuite)
    use_junit5 = coverage_runner._needs_junit5_plugin(str(test_java))

    test_cp = coverage_runner._build_exec_classpath(
        project_classpath, compiled_dir, cfg, include_evosuite=use_evo,
    )

    # ── 1. Compile the test (idempotent — javac is fast) ────────────────────
    extra = [scaffolding_java] if (use_evo and scaffolding_java) else None
    ok, err = coverage_runner.compile_test(
        str(test_java), test_cp, compiled_dir, javac, extra,
    )
    if not ok:
        log.warning("[mutation_measurement] test compile failed for %s\n%s",
                    test_java.name, err[-1000:])
        return _failure(
            f"Mutation test compile failed for {test_java.name}.",
            stderr_text=err[-4000:],
        )

    test_fqn = coverage_runner._infer_fqn(str(test_java))

    # ── 2. Source dirs (best-effort discovery) ──────────────────────────────
    project_dir = Path(sut_classes_dir).parent.parent
    src_main_java = project_dir / "src" / "main" / "java"
    if not src_main_java.exists():
        src_main_java = project_dir / "src" / "java"
    if not src_main_java.exists():
        src_main_java = project_dir   # last-resort fallback

    # ── 3. Invoke PIT ───────────────────────────────────────────────────────
    report_dir = work / "pit_report"
    if report_dir.exists():
        shutil.rmtree(report_dir, ignore_errors=True)
    report_dir.mkdir(parents=True, exist_ok=True)

    plugin_cp_parts = [pit_cp]
    test_plugin = "junit"
    if use_junit5:
        junit5_plugin_jar = _resolve_optional_jar(cfg, "pit_junit5_plugin_jar")
        if not junit5_plugin_jar:
            return _failure(
                "JUnit 5 test detected, but mutation_testing.pit_junit5_plugin_jar "
                "is not configured or the jar is missing.",
                report_dir=report_dir,
            )
        plugin_cp_parts.append(junit5_plugin_jar)
        junit5_launcher_jar = _resolve_junit_platform_launcher(test_cp)
        if not junit5_launcher_jar:
            return _failure(
                "JUnit 5 test detected, but junit-platform-launcher could not "
                "be found on the PIT classpath.",
                report_dir=report_dir,
            )
        plugin_cp_parts.append(junit5_launcher_jar)
        test_plugin = "junit5"
    # The SUT's own compiled classes MUST take precedence over anything bundled
    # in pit_jars. We ship commons-lang3-3.14.0 inside pit_jars (commons-text's
    # XMLReportListener needs it — see Bug 3), but pit_jars are prepended, so
    # when the project UNDER TEST is itself commons-lang, that 3.14.0 copy of
    # e.g. ToStringStyle shadows the project's own (newer) class. The two
    # differ — 3.14.0 uses a plain `ThreadLocal` (getRegistry() returns null on
    # a clean thread) whereas the SUT uses `ThreadLocal.withInitial(...)`
    # (never null). commons-lang's shared @AfterEach asserts
    # `ToStringStyle.getRegistry().isEmpty()`, which then NPEs under PIT,
    # failing every test "without mutation" → no mutation score. Putting the
    # SUT classes dir FIRST makes the project's real class win. Harmless for
    # other projects (only their own compiled classes gain precedence) and it
    # keeps Bug 3 working: when the SUT is NOT commons-lang, commons-lang3.Range
    # is still resolved from the bundled 3.14.0 jar.
    sut_first = str(Path(sut_classes_dir).resolve()) if sut_classes_dir else ""
    cp_order = ([sut_first] if sut_first else []) + plugin_cp_parts + [test_cp]
    full_cp = ":".join(p for p in cp_order if p)

    timeout_s = int(getattr(getattr(cfg, "mutation_testing", None),
                            "timeout_seconds", 300))
    # Mutate the CUT *and all of its nested/inner classes*. PIT's
    # --targetClasses is a glob list; the bare FQN matches ONLY the outer
    # class. Many CUTs keep their real logic in an inner class (e.g. the
    # jackson annotation CUTs put everything in `JsonIgnoreProperties$Value`),
    # so the bare FQN produced "Created 0 mutation test units → No mutations
    # found" and mutation_score came back None. Appending `<FQN>$*` includes
    # every inner class while the exact `<FQN>` still covers the outer one.
    # This mirrors the coverage_runner inner-class aggregation fix so coverage
    # and mutation measure the SAME class scope. Harmless for CUTs without
    # inner classes (the glob simply matches nothing extra).
    target_classes_glob = f"{target_class_fqn},{target_class_fqn}$*"
    cmd = [
        java,
        "-cp", full_cp,
        "org.pitest.mutationtest.commandline.MutationCoverageReport",
        "--reportDir", str(report_dir),
        "--targetClasses", target_classes_glob,
        "--targetTests", test_fqn,
        "--sourceDirs", str(src_main_java),
        "--outputFormats", "XML",
        "--timestampedReports", "false",
        "--testPlugin", test_plugin,
    ]
    # EvoSuite tests use @RunWith(EvoRunner.class). PIT runs each mutant in a
    # forked *minion* JVM; EvoRunner then tries to self-attach its ByteBuddy
    # instrumentation agent into that minion. Java 9+ blocks self-attach by
    # default, so the minion dies with
    #   "Could not self-attach to current VM using external process"
    # and PIT produces an empty mutations.xml (mutation score = 0/None for ALL
    # EvoSuite suites). coverage_runner.run_coverage_direct already solves the
    # identical problem for its own JVM (see its -javaagent/-Djdk... block);
    # here we forward the same two flags into PIT's minions via --jvmArgs.
    if use_evo:
        # PIT's --jvmArgs accepts ONE value only (passing the flag twice errors
        # with "Found multiple arguments for option jvmArgs").
        #
        # We deliberately do NOT forward "-javaagent:<evosuite-runtime>" here:
        # PIT re-tokenizes the minion command line on whitespace, so a jar path
        # containing a space (this project lives under ".../...improvement copy/")
        # gets split and the minion dies with "Error opening zip file or JAR
        # manifest missing: .../...improvement". It isn't needed anyway — the
        # evosuite-standalone-runtime jar is already on the minion classpath
        # (full_cp, include_evosuite=use_evo), so EvoRunner locates the agent
        # itself; it only needed permission to self-attach. The single -D flag
        # below (no spaces) grants exactly that and fixes MINION_DIED.
        #
        # ALSO open java.desktop/java.awt: EvoSuite's scaffolding @BeforeClass
        # initEvoSuiteFramework() -> GuiSupport.<clinit> reflectively reads
        # java.awt.GraphicsEnvironment.headless. On JDK 16+ (strong
        # encapsulation) the PIT minion throws InaccessibleObjectException, the
        # test "did not pass without mutation", and PIT aborts with
        # "Mutation testing requires a green suite" → mutation = 0 / None for
        # the whole suite (observed on commons-jxpath, spatial4j, and several
        # auto suites). The `=` form is used (NOT a space) so PIT's whitespace
        # re-tokenisation of --jvmArgs does not split it. PIT's --jvmArgs takes
        # ONE value and splits it on commas, so we comma-join the two flags;
        # neither contains a comma. Harmless on JDK 9-15 (the module exists).
        cmd += ["--jvmArgs",
                "-Djdk.attach.allowAttachSelf=true,"
                "--add-opens=java.desktop/java.awt=ALL-UNNAMED,"
                # macOS: keep PIT's minion JVMs headless / background agents so
                # they don't steal focus or flash in the menu bar.
                "-Djava.awt.headless=true,"
                "-Dapple.awt.UIElement=true"]
    log.info("[mutation_measurement] PIT: %s on %s", target_class_fqn, test_fqn)
    log.debug("[mutation_measurement] pit cmd: %s", " ".join(cmd))

    try:
        result = subprocess.run(cmd, capture_output=True, text=True,
                                timeout=timeout_s)
    except subprocess.TimeoutExpired:
        log.warning("[mutation_measurement] PIT timed out (%ds) for %s",
                    timeout_s, test_fqn)
        return _failure(
            f"PIT timed out after {timeout_s}s for {test_fqn}.",
            report_dir=report_dir,
        )

    mutations_xml = report_dir / "mutations.xml"
    if not mutations_xml.exists() or mutations_xml.stat().st_size == 0:
        log.warning("[mutation_measurement] PIT did not produce a usable "
                    "mutations.xml for %s (exit=%s). Logs saved under %s.\n"
                    "stdout tail: %s\nstderr tail: %s",
                    test_fqn, getattr(result, "returncode", "?"), report_dir,
                    (result.stdout or "")[-600:], (result.stderr or "")[-600:])
        return _failure(
            f"PIT did not produce a usable mutations.xml for {test_fqn} "
            f"(exit={getattr(result, 'returncode', '?')}).",
            report_dir=report_dir,
            stdout_text=result.stdout or "",
            stderr_text=result.stderr or "",
        )

    return _parse_pit_report(mutations_xml)


# ─────────────────────────────────────────────────────────────────────────────
# Config resolution
# ─────────────────────────────────────────────────────────────────────────────

def _resolve_pit_classpath(cfg) -> Optional[str]:
    """Return a colon-separated path covering every PIT jar in config."""
    mt = getattr(cfg, "mutation_testing", None)
    if not mt:
        return None
    jars_cfg = getattr(mt, "pit_jars", None) or []
    if not isinstance(jars_cfg, list):
        try:
            jars_cfg = list(jars_cfg)
        except TypeError:
            jars_cfg = []

    resolved, missing = [], []
    for j in jars_cfg:
        p = Path(j)
        if not p.is_absolute():
            p = PROJECT_ROOT / p
        if p.exists():
            resolved.append(str(p.resolve()))
        else:
            missing.append(str(p))
    if missing:
        log.warning("[mutation_measurement] PIT jars missing (mutation testing "
                    "will fail until installed): %s", ", ".join(missing))
    if not resolved:
        return None
    return ":".join(resolved)


def _resolve_optional_jar(cfg, key: str) -> Optional[str]:
    """Resolve one optional mutation-testing support jar from config."""
    mt = getattr(cfg, "mutation_testing", None)
    if not mt:
        return None
    jar_cfg = getattr(mt, key, "") or ""
    if not jar_cfg:
        return None
    p = Path(jar_cfg)
    if not p.is_absolute():
        p = PROJECT_ROOT / p
    if not p.exists():
        log.warning("[mutation_measurement] configured %s does not exist: %s", key, p)
        return None
    return str(p.resolve())


def _resolve_junit_platform_launcher(test_cp: str) -> Optional[str]:
    """Best-effort resolution of junit-platform-launcher for PIT JUnit 5 runs."""
    parts = [p for p in (test_cp or "").split(":") if p]
    for p in parts:
        if "junit-platform-launcher" in p and Path(p).exists():
            return str(Path(p).resolve())

    engine_jar = next((p for p in parts if "junit-platform-engine-" in p), "")
    version = ""
    if engine_jar:
        m = re.search(r"junit-platform-engine-([0-9][^/]+)\.jar", engine_jar)
        if m:
            version = m.group(1)

    if version:
        candidate = (
            Path.home() / ".m2" / "repository" / "org" / "junit" / "platform"
            / "junit-platform-launcher" / version
            / f"junit-platform-launcher-{version}.jar"
        )
        if candidate.exists():
            return str(candidate.resolve())
    return None


# ─────────────────────────────────────────────────────────────────────────────
# Result helpers
# ─────────────────────────────────────────────────────────────────────────────

def _failure(
    message: str,
    report_dir: Optional[Path] = None,
    stdout_text: str = "",
    stderr_text: str = "",
) -> dict:
    """Build a structured mutation failure payload for metrics.json."""
    result = {"mutation_error": message}
    if report_dir is not None:
        report_dir.mkdir(parents=True, exist_ok=True)
        result["mutation_log_dir"] = str(report_dir.resolve())
        if stdout_text:
            stdout_path = report_dir / "pit_stdout.txt"
            stdout_path.write_text(stdout_text, encoding="utf-8")
            result["mutation_stdout_path"] = str(stdout_path.resolve())
        if stderr_text:
            stderr_path = report_dir / "pit_stderr.txt"
            stderr_path.write_text(stderr_text, encoding="utf-8")
            result["mutation_stderr_path"] = str(stderr_path.resolve())
    return result


def _parse_pit_report(mutations_xml: Path) -> Optional[dict]:
    """Parse a PIT mutations.xml and return killed/total/score."""
    try:
        tree = ET.parse(str(mutations_xml))
    except ET.ParseError as e:
        log.warning("[mutation_measurement] could not parse PIT report %s: %s",
                    mutations_xml, e)
        return None

    root = tree.getroot()
    killed = 0
    total = 0
    zero_tests_run_for_all = True
    killed_mutants: list[dict] = []
    status_counts: dict[str, int] = {}
    for m in root.findall("mutation"):
        total += 1
        status = (m.get("status") or "").upper()
        status_counts[status] = status_counts.get(status, 0) + 1
        tests_run = m.get("numberOfTestsRun")
        if tests_run not in (None, "", "0"):
            zero_tests_run_for_all = False
        if status == "KILLED":
            killed += 1
            killed_mutants.append(_pit_mutation_to_dict(m))
    score = round((killed / total) * 100.0, 2) if total else 0.0
    result = {
        "mutants_total": total,
        "mutants_killed": killed,
        "mutation_score_pct": score,
        "mutation_report_path": str(mutations_xml.resolve()),
        "mutation_status_counts": status_counts,
    }
    if killed_mutants:
        killed_path = mutations_xml.parent / "killed_mutants.json"
        killed_path.write_text(
            json.dumps(killed_mutants, indent=2, ensure_ascii=False),
            encoding="utf-8",
        )
        result["killed_mutants"] = killed_mutants
        result["killed_mutants_preview"] = killed_mutants[:10]
        result["killed_mutants_path"] = str(killed_path.resolve())
    if total and zero_tests_run_for_all:
        result["mutation_error"] = (
            "PIT reported numberOfTestsRun=0 for every mutant. "
            "Test discovery/execution likely failed."
        )
    return result


def _pit_mutation_to_dict(mutation: ET.Element) -> dict:
    """Convert one <mutation> node into a readable JSON-serializable dict."""
    def _text(tag: str) -> str:
        node = mutation.find(tag)
        return (node.text or "").strip() if node is not None and node.text else ""

    mutator = _text("mutator")
    tests_run_raw = mutation.get("numberOfTestsRun")
    try:
        tests_run = int(tests_run_raw) if tests_run_raw not in (None, "") else 0
    except ValueError:
        tests_run = 0

    line_number_raw = _text("lineNumber")
    try:
        line_number = int(line_number_raw) if line_number_raw else 0
    except ValueError:
        line_number = 0

    return {
        "status": (mutation.get("status") or "").upper(),
        "detected": (mutation.get("detected") or "").lower() == "true",
        "tests_run": tests_run,
        "source_file": _text("sourceFile"),
        "mutated_class": _text("mutatedClass"),
        "mutated_method": _text("mutatedMethod"),
        "method_description": _text("methodDescription"),
        "line_number": line_number,
        "mutator": mutator,
        "mutator_short": mutator.split(".")[-1] if mutator else "",
        "description": _text("description"),
        "killing_test": _text("killingTest"),
        "indexes": [idx.text for idx in mutation.findall("./indexes/index") if idx.text],
        "blocks": [blk.text for blk in mutation.findall("./blocks/block") if blk.text],
    }
