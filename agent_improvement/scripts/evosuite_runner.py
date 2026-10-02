"""
Stage: Generate automated test suites using EvoSuite 1.2.0.

EvoSuite requirements:
  - Must run with Java 8 or Java 11 (set java_8_path in config.yaml → tools)
  - Needs the project's compiled classes on the classpath

Key fix: EvoSuite 1.2.0 uses an old shaded ASM that cannot read bytecode >
class file major version 55 (Java 11). Modern test-scope JARs (e.g.
byte-buddy 1.18 with META-INF/versions/24/ multi-release entries) cause
"Unsupported class file major version 68" during InheritanceTree scanning.
Solution: build the EvoSuite classpath from *runtime scope only*, which
excludes test-only deps like byte-buddy, mockito, and JUnit 5.

Logic ported from:
  an earlier version of this pipeline (s2_evosuite_runner.py)
"""
from __future__ import annotations

import logging
import os
import re
import subprocess
import tempfile
import zipfile
from pathlib import Path
from typing import Optional, Tuple

log = logging.getLogger(__name__)

PROJECT_ROOT = Path(__file__).parent.parent


# ── Java home detection ───────────────────────────────────────────────────────

def _find_java8_home() -> str:
    """
    Auto-detect a Java 8 JAVA_HOME on macOS.
    Returns the home directory (not the binary). Prefer tools.java_homes["8"]
    in config.yaml; this fallback only globs the usual macOS install folders.
    """
    import glob
    candidates = sorted(glob.glob(str(Path.home() / "Library/Java/JavaVirtualMachines/*1.8*/Contents/Home"))
                        + glob.glob("/Library/Java/JavaVirtualMachines/*1.8*/Contents/Home"))
    for c in candidates:
        if Path(c).exists():
            return c
    java_home = os.environ.get("JAVA_HOME", "")
    if java_home and Path(java_home).exists():
        return java_home
    return ""


def _java_home_by_key(cfg, key: str) -> str:
    """Look up cfg.tools.java_homes[key] (SimpleNamespace OR dict)."""
    jh = getattr(cfg.tools, "java_homes", None)
    if jh is None:
        return ""
    val = jh.get(key) if isinstance(jh, dict) else getattr(jh, key, None)
    return str(val) if val else ""


def _get_java_home(cfg, project_dir: str = "") -> str:
    """JAVA_HOME for EvoSuite. EvoSuite 1.2.0 runs on Java 8 OR 11 and can only
    instrument bytecode up to Java 11 (major 55). So pick a JDK that MATCHES the
    project's bytecode: Java 11 for Java-11 projects (otherwise the Java-8 runtime
    rejects them with UnsupportedClassVersionError), else the configured Java 8.
    Java 17+ projects are unsupported — we warn; generation will still fail."""
    if project_dir:
        try:
            import build_executor as be
            rel = be.detect_java_release(Path(project_dir))
        except Exception:
            rel = None
        if rel == 11:
            j11 = _java_home_by_key(cfg, "11")
            if j11:
                return j11
            log.warning("[evosuite_runner] project is Java 11 but tools.java_homes['11'] "
                        "is unset — falling back to Java 8 (EvoSuite will likely fail).")
        elif rel and rel > 11:
            log.warning("[evosuite_runner] project is Java %d; EvoSuite 1.2.0 can only "
                        "instrument up to Java 11 — generation will fail.", rel)
    configured = getattr(cfg.tools, "java_8_path", "").strip()
    if configured:
        p = Path(configured)
        if p.name == "java":
            return str(p.parent.parent)  # bin/java → home
        return configured
    return _find_java8_home()


def _resolve_module_dir(project_dir: str, class_fqn: str) -> Optional[str]:
    """For a MULTI-MODULE project, find the submodule whose target/classes holds
    the class (the root aggregator has no target/classes). Returns the module
    dir, or None if the class isn't found anywhere."""
    rel = class_fqn.replace(".", "/") + ".class"
    root = Path(project_dir)
    if (root / "target" / "classes" / rel).exists():
        return str(root)
    matches = list(root.glob(f"**/target/classes/{rel}"))
    if matches:
        cf = matches[0]
        # cf = <module>/target/classes/<rel>; strip (2 + #rel-segments) levels.
        return str(cf.parents[1 + len(rel.split("/"))])
    return None


# ── Classpath builder (runtime scope only — no multi-release Java 24 jars) ───

# EvoSuite 1.2.0 bundles an old shaded ASM. It throws
#   "Unsupported class file major version N"
# for bytecode newer than it understands (observed: byte-buddy 1.17.5 ships
# META-INF/versions/24/ → major 68 = Java 24). EvoSuite scans the ENTIRE
# -projectCP to build its inheritance tree, so ONE such jar aborts generation
# with zero tests. Maven's -DincludeScope=runtime/compile does NOT reliably
# drop these (commons-lang resolves byte-buddy/mockito onto compile+runtime),
# so we post-filter the classpath by actual bytecode version. Java 11
# (major 55) is EvoSuite 1.2.0's officially-supported ceiling. The offenders
# are invariably test/mock frameworks the CUT never references, so dropping
# them is safe for generation.
_EVOSUITE_MAX_MAJOR = 55  # Java 11


def _entry_evosuite_readable(entry: str, max_major: int = _EVOSUITE_MAX_MAJOR) -> bool:
    """False if a classpath jar holds bytecode EvoSuite 1.2.0's ASM can't read.

    Checks two things: (1) multi-release versioned classes for Java > 11
    (META-INF/versions/N), and (2) base classes whose own major version
    exceeds the ceiling. Directories (our own target/classes) and unreadable
    jars are kept (fail-open)."""
    p = Path(entry)
    if not p.exists() or p.is_dir():
        return True
    if p.suffix != ".jar":
        return True
    try:
        with zipfile.ZipFile(p) as zf:
            names = zf.namelist()
            for n in names:
                m = re.match(r"META-INF/versions/(\d+)/", n)
                if m and int(m.group(1)) > 11:
                    return False
            # Scan ALL base .class entries (short-circuit on first violation).
            # A [:30] sample used to miss jars that MIX old + new bytecode —
            # e.g. jline-3.30.13 has Java-22 (major 66) classes past the first
            # 30, which crash EvoSuite's ASM with "Unsupported class file major
            # version 66".
            for n in names:
                if not n.endswith(".class") or n.startswith("META-INF/") or "module-info" in n:
                    continue
                with zf.open(n) as f:
                    head = f.read(8)
                if len(head) >= 8 and int.from_bytes(head[6:8], "big") > max_major:
                    return False
    except Exception:
        return True
    return True


def build_evosuite_classpath(project_dir: str, build_system: str,
                              maven_path: str = "mvn") -> str:
    """
    Build a classpath for EvoSuite that contains ONLY:
      1. target/classes (compiled SUT)
      2. Runtime-scope Maven dependencies

    WHY runtime-only:
      Test-scope deps like byte-buddy 1.18 include META-INF/versions/24/
      multi-release entries (Java 24 bytecode). EvoSuite 1.2.0's shaded ASM
      cannot read class file major version 68 and crashes with
      "Unsupported class file major version 68" during classpath scanning.
      Runtime-scope excludes these test-only deps entirely.
    """
    project = Path(project_dir)
    classes_dir = project / "target" / "classes"

    if build_system != "maven":
        # Gradle fallback — just use target/build classes
        log.warning("[evosuite_runner] Non-Maven build — using target/classes only for EvoSuite")
        return str(classes_dir.resolve())

    # Extract runtime-scope deps only
    with tempfile.NamedTemporaryFile(suffix=".txt", delete=False) as tf:
        cp_file = tf.name

    cmd = [
        maven_path, "-q",
        "-f", str(project / "pom.xml"),
        "dependency:build-classpath",
        "-DincludeScope=runtime",
        f"-Dmdep.outputFile={cp_file}",
        "-Drat.skip=true",
        "--no-transfer-progress",
    ]
    result = subprocess.run(cmd, capture_output=True, text=True,
                            cwd=str(project))
    runtime_cp = ""
    if result.returncode == 0:
        runtime_cp = Path(cp_file).read_text().strip()
    else:
        log.warning("[evosuite_runner] Could not extract runtime classpath: %s",
                    result.stderr[-500:])
    try:
        os.unlink(cp_file)
    except OSError:
        pass

    # Always ensure paths are absolute
    parts = [str(classes_dir.resolve())]
    if runtime_cp:
        # Convert each entry to absolute path, then DROP any jar whose bytecode
        # is too new for EvoSuite 1.2.0's ASM (byte-buddy/mockito multi-release
        # Java 24 jars). Without this, EvoSuite crashes during classpath scan
        # and generates zero tests (observed on commons-lang).
        abs_entries = [str(Path(e).resolve()) for e in runtime_cp.split(":") if e.strip()]
        kept, dropped = [], []
        for e in abs_entries:
            (kept if _entry_evosuite_readable(e) else dropped).append(e)
        if dropped:
            log.warning(
                "[evosuite_runner] Dropped %d classpath entr%s with bytecode "
                "newer than EvoSuite 1.2.0 can read (Java > 11): %s",
                len(dropped), "y" if len(dropped) == 1 else "ies",
                ", ".join(Path(d).name for d in dropped),
            )
        parts.extend(kept)

    cp = ":".join(parts)
    log.debug("[evosuite_runner] EvoSuite classpath (runtime only):\n  %s",
              "\n  ".join(parts))
    return cp


# ── Pre-run class file verification ──────────────────────────────────────────

def verify_class_file(project_dir: str, class_fqn: str) -> Optional[str]:
    """
    Check that <class_fqn>.class exists inside target/classes.
    Returns the absolute path to the .class file, or None if missing.
    """
    rel_path = class_fqn.replace(".", "/") + ".class"
    class_file = Path(project_dir) / "target" / "classes" / rel_path
    abs_path = class_file.resolve()
    if abs_path.exists():
        log.info("[evosuite_runner] ✓ Found class file: %s", abs_path)
        return str(abs_path)
    log.error(
        "[evosuite_runner] ✗ Class file NOT found: %s\n"
        "  Expected at: %s\n"
        "  Run 'mvn compile' in the project directory first.",
        class_fqn, abs_path,
    )
    return None


# ── Main runner ───────────────────────────────────────────────────────────────

def run_evosuite(
    *,
    evosuite_jar: str,
    class_fqn: str,
    project_classpath: str,
    output_dir: str,
    search_budget: int = 60,
    java_home: str = "",
    extra_flags: list[str] | None = None,
    timeout: int = 600,
    verbose: bool = False,
) -> bool:
    """
    Run EvoSuite for a single class. Returns True on success.

    Args:
        evosuite_jar:       Path to evosuite-1.2.0.jar
        class_fqn:          Fully-qualified class name, e.g. org.apache.commons.io.HexDump
        project_classpath:  Runtime classpath (use build_evosuite_classpath() to build it)
        output_dir:         Where to write generated *_ESTest.java files
        search_budget:      Seconds EvoSuite spends searching
        java_home:          Path to Java 8 or 11 home dir (required for EvoSuite 1.2.0)
        extra_flags:        Additional EvoSuite CLI flags
        timeout:            Subprocess timeout in seconds (budget + overhead)
        verbose:            If True, pass -Dshow_progress=true to EvoSuite
    """
    Path(output_dir).mkdir(parents=True, exist_ok=True)

    # Ensure all classpath entries are absolute paths
    abs_cp_parts = [
        str(Path(p).resolve()) for p in project_classpath.split(":") if p.strip()
    ]
    abs_cp = ":".join(abs_cp_parts)

    env = os.environ.copy()
    java = "java"
    if java_home:
        env["JAVA_HOME"] = java_home
        env["PATH"] = f"{java_home}/bin:" + env.get("PATH", "")
        java = str(Path(java_home) / "bin" / "java")

    # Verify Java version before running EvoSuite
    try:
        ver_result = subprocess.run(
            [java, "-version"], capture_output=True, text=True, env=env, timeout=10
        )
        ver_output = (ver_result.stderr + ver_result.stdout).split("\n")[0]
        log.info("[evosuite_runner] Using Java: %s", ver_output.strip())
    except Exception as e:
        log.warning("[evosuite_runner] Could not check Java version: %s", e)

    abs_output = str(Path(output_dir).resolve())
    abs_jar = str(Path(evosuite_jar).resolve())

    cmd = [
        java,
        "-jar", abs_jar,
        "-class", class_fqn,
        "-projectCP", abs_cp,
        f"-Dtest_dir={abs_output}",
        f"-Dsearch_budget={search_budget}",
        "-Dminimize=true",
        "-Dassertions=true",
        "-Dcriterion=LINE:BRANCH",
        # REQUIRED for JaCoCo compatibility:
        # separateClassLoader=true (default) makes EvoSuite use a custom classloader
        # that discards any prior bytecode instrumentation from JaCoCo.
        # false → EvoSuite uses its Java agent (InstrumentingAgent) instead,
        # allowing JaCoCo's runtime instrumentation to coexist.
        "-Duse_separate_classloader=false",
        f"-Dshow_progress={'true' if verbose else 'false'}",
    ]
    if extra_flags:
        cmd.extend(extra_flags)

    # ── Log the full command at INFO level (as requested) ─────────────────
    log.info("[evosuite_runner] Full EvoSuite command:")
    log.info("  %s -jar %s \\", java, abs_jar)
    log.info("  -class %s \\", class_fqn)
    log.info("  -projectCP <%d entries>", len(abs_cp_parts))
    for i, entry in enumerate(abs_cp_parts):
        log.info("    [%d] %s", i, entry)
    log.info("  -Dtest_dir=%s  -Dsearch_budget=%d", abs_output, search_budget)

    try:
        result = subprocess.run(
            cmd,
            capture_output=True,
            text=True,
            env=env,
            timeout=timeout,
        )
    except subprocess.TimeoutExpired:
        log.error("[evosuite_runner] EvoSuite timed out for %s after %ds",
                  class_fqn, timeout)
        return False
    except FileNotFoundError as e:
        log.error("[evosuite_runner] EvoSuite failed — executable not found: %s", e)
        log.error("Check that java_8_path in config.yaml → tools points to Java 8 or 11.")
        return False

    if result.returncode not in (0, 1):
        stderr_tail = "\n".join(result.stderr.strip().split("\n")[-50:])
        log.error("[evosuite_runner] EvoSuite exited with code %d for %s",
                  result.returncode, class_fqn)
        log.error("[evosuite_runner] EvoSuite stderr (last 50 lines):\n%s", stderr_tail)
        if "UnsupportedClassVersionError" in result.stderr:
            log.error(
                "UnsupportedClassVersionError: EvoSuite 1.2.0 requires Java 8 or 11. "
                "Update java_8_path in config.yaml → tools."
            )
        return False

    # Log any errors/warnings from EvoSuite stdout/stderr
    for line in result.stderr.splitlines():
        if "ERROR" in line or "WARN" in line or "major version" in line:
            log.debug("[evosuite_runner] EvoSuite: %s", line)

    if result.returncode != 0:
        log.warning("[evosuite_runner] EvoSuite returned code 1 for %s — may still "
                    "have generated partial tests", class_fqn)

    # Verify output files were actually created
    generated = list(Path(output_dir).rglob("*_ESTest.java"))
    if not generated:
        # Print the last part of stderr to explain why
        stderr_snippet = "\n".join(result.stderr.strip().split("\n")[-30:])
        log.warning(
            "[evosuite_runner] EvoSuite ran (exit=%d) but generated no *_ESTest.java "
            "for %s.\nEvoSuite output:\n%s",
            result.returncode, class_fqn, stderr_snippet,
        )
        return False

    # JaCoCo compatibility: EvoSuite's default @EvoRunnerParameters has
    # separateClassLoader=true, which puts the SUT classes in an isolated
    # classloader that JaCoCo's java-agent cannot instrument. Patch the
    # generated annotation to set separateClassLoader=false so JaCoCo can
    # see SUT execution at coverage-measurement time. Idempotent.
    for f in generated:
        try:
            _ensure_jacoco_compatible_annotation(f)
        except Exception as e:
            log.warning("[evosuite_runner] could not patch annotation in %s: %s", f, e)

    log.info("[evosuite_runner] ✓ EvoSuite generated %d test file(s) for %s",
             len(generated), class_fqn)
    return True


_ANNOTATION_RE = re.compile(
    r"(@EvoRunnerParameters\s*\()([^)]*)(\))",
    re.DOTALL,
)


def _ensure_jacoco_compatible_annotation(java_file: Path) -> None:
    """Add `separateClassLoader = false` to `@EvoRunnerParameters(...)` if not
    already present. Idempotent — running twice leaves the file unchanged."""
    src = java_file.read_text(encoding="utf-8")
    m = _ANNOTATION_RE.search(src)
    if not m:
        return  # nothing to patch
    body = m.group(2)
    if "separateClassLoader" in body:
        return  # already patched (or user-provided)
    body_trim = body.rstrip()
    sep = ", " if body_trim and not body_trim.endswith(",") else " "
    new_body = body_trim + f"{sep}separateClassLoader = false"
    patched = src[:m.start(2)] + new_body + src[m.end(2):]
    if patched != src:
        java_file.write_text(patched, encoding="utf-8")
        log.debug("[evosuite_runner] patched annotation for JaCoCo in %s", java_file.name)


def find_evosuite_outputs(output_dir: str, class_fqn: str) -> dict[str, str | None]:
    """Locate the generated *_ESTest.java and *_ESTest_scaffolding.java files."""
    simple_name = class_fqn.split(".")[-1]
    base_dir = Path(output_dir)

    test_file = None
    scaffolding_file = None

    for f in base_dir.rglob(f"{simple_name}_ESTest.java"):
        test_file = str(f)
        break
    for f in base_dir.rglob(f"{simple_name}_ESTest_scaffolding.java"):
        scaffolding_file = str(f)
        break

    return {"test": test_file, "scaffolding": scaffolding_file}


# ── High-level generate() used by main.py ────────────────────────────────────

def generate(
    target_class_fqn: str,
    project_classpath: str,   # kept for API compat; we rebuild for EvoSuite
    output_dir: str,
    cfg,
    project_dir: str = "",
    build_system: str = "maven",
) -> Optional[Tuple[str, str]]:
    """
    Generate EvoSuite tests for one class.

    NOTE: project_classpath (built for coverage/compile steps) intentionally
    ignored here — we rebuild a runtime-only classpath to avoid multi-release
    JAR issues with EvoSuite's old ASM.

    Returns (suite_java_path, scaffolding_java_path), or None on failure.
    """
    java_home = _get_java_home(cfg, project_dir)
    evosuite_jar = str(PROJECT_ROOT / cfg.tools.evosuite_jar)
    time_budget = int(getattr(cfg.tools, "evosuite_time_budget", 60))
    maven_path = getattr(cfg.tools, "maven_path", "mvn")

    if not project_dir:
        # Derive from the workplace root's first subdir. Read at call time so a
        # config-driven override is honoured (this module is imported eagerly).
        import pipeline_paths
        workplace = pipeline_paths.workplace_dir()
        subdirs = [d for d in workplace.iterdir() if d.is_dir()] if workplace.exists() else []
        project_dir = str(subdirs[0]) if subdirs else str(PROJECT_ROOT)

    # ── Verify the class file exists first ──────────────────────────────────
    # MULTI-MODULE: aggregator roots (commons-math) have no target/classes — the
    # SUT lives in a submodule. Resolve it so verify + classpath point there.
    effective_dir = project_dir
    class_path_result = verify_class_file(project_dir, target_class_fqn)
    if class_path_result is None:
        mod = _resolve_module_dir(project_dir, target_class_fqn)
        if mod and mod != project_dir:
            log.info("[evosuite_runner] multi-module: class found in submodule %s", mod)
            effective_dir = mod
            class_path_result = verify_class_file(mod, target_class_fqn)
        if class_path_result is None:
            return None

    # ── Build runtime-only classpath for EvoSuite ───────────────────────────
    evo_cp = build_evosuite_classpath(effective_dir, build_system, maven_path)

    ok = run_evosuite(
        evosuite_jar=evosuite_jar,
        class_fqn=target_class_fqn,
        project_classpath=evo_cp,
        output_dir=output_dir,
        search_budget=time_budget,
        java_home=java_home,
        timeout=time_budget + 300,
        verbose=False,
    )
    if not ok:
        return None

    outputs = find_evosuite_outputs(output_dir, target_class_fqn)
    if not outputs["test"]:
        return None

    return outputs["test"], outputs["scaffolding"]


