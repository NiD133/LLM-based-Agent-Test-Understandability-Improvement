"""
AST-based splitter for JUnit 3/4/5 test suites.

The heavy lifting lives in a small Java CLI under
`tools/javaparser/TestSplitter.java` that uses com.github.javaparser to
walk the suite's AST. This Python module is a thin wrapper:

  1. Locate (and on first use, lazily javac-compile) the Java CLI.
  2. Invoke `java -cp javaparser-core.jar:tools/javaparser TestSplitter
                  --input <suite.java> --out <output_dir>`.
  3. Parse the JSON manifest the CLI writes to stdout.
  4. Return `SplitResult` records that match the original public API so
     downstream `main.py` / measurement code is unchanged.

The CLI handles JUnit 3 (`extends TestCase` + `testXxx()` naming),
JUnit 4 (`@Test`), and JUnit 5 (`@Test` / `@ParameterizedTest` /
`@RepeatedTest` / `@TestFactory` / `@TestTemplate`), plus `@Nested`
inner classes (flattened — outer + inner fields and lifecycle methods
are kept; the new class is named `<Outer>_<Inner>_<method>`).
"""
from __future__ import annotations

import json
import logging
import shutil
import subprocess
from dataclasses import dataclass
from pathlib import Path
from typing import NamedTuple, Optional

log = logging.getLogger(__name__)

PROJECT_ROOT = Path(__file__).parent.parent
JAVAPARSER_DIR = PROJECT_ROOT / "tools" / "javaparser"
JAVAPARSER_JAR = JAVAPARSER_DIR / "javaparser-core-3.27.0.jar"
CLI_SRC        = JAVAPARSER_DIR / "TestSplitter.java"
CLI_CLASS      = JAVAPARSER_DIR / "TestSplitter.class"
CLI_NAME       = "TestSplitter"


# ── Public types ─────────────────────────────────────────────────────────────

class SplitResult(NamedTuple):
    method_name: str
    source_code: str                  # full standalone Java source
    file_name: str                    # e.g. PosixParserTest_testFoo.java
    java_path: str                    # absolute path on disk
    scaffolding_path: Optional[str] = None  # set for EvoSuite splits
    junit_version: str = ""           # "3" or "4-5"
    marker: str = ""                  # "@Test", "@ParameterizedTest", "name_convention", ...


# ── JDK resolution ───────────────────────────────────────────────────────────

def _resolve_java_tools(cfg=None) -> tuple[str, str]:
    """Return (javac_path, java_path).

    Prefers cfg.tools.java_home_compile if set, then $JAVA_HOME, then PATH.
    """
    java_home = ""
    if cfg is not None:
        java_home = getattr(getattr(cfg, "tools", None), "java_home_compile", "") or ""
    if not java_home:
        import os
        java_home = os.environ.get("JAVA_HOME", "")
    if java_home:
        javac = Path(java_home) / "bin" / "javac"
        java  = Path(java_home) / "bin" / "java"
        if javac.exists() and java.exists():
            return str(javac), str(java)
    javac = shutil.which("javac") or "javac"
    java  = shutil.which("java")  or "java"
    return javac, java


def _ensure_cli_built(cfg=None) -> None:
    """Compile tools/javaparser/TestSplitter.java if the .class is missing or stale.

    Idempotent — does nothing when the .class file is newer than the source.
    """
    if not JAVAPARSER_JAR.exists():
        raise FileNotFoundError(
            f"javaparser-core jar not found at {JAVAPARSER_JAR}. "
            "Re-run the tools/ setup or download it from Maven Central."
        )
    if not CLI_SRC.exists():
        raise FileNotFoundError(
            f"TestSplitter source not found at {CLI_SRC}."
        )
    if CLI_CLASS.exists() and CLI_CLASS.stat().st_mtime >= CLI_SRC.stat().st_mtime:
        return
    javac, _ = _resolve_java_tools(cfg)
    log.info("[test_splitter] compiling %s …", CLI_SRC.name)
    cmd = [javac, "-cp", str(JAVAPARSER_JAR), "-d", str(JAVAPARSER_DIR), str(CLI_SRC)]
    result = subprocess.run(cmd, capture_output=True, text=True)
    if result.returncode != 0:
        raise RuntimeError(
            f"javac failed for {CLI_SRC.name}:\n{result.stderr.strip()}"
        )


# ── Public API ───────────────────────────────────────────────────────────────

def split_suite(
    java_file: str,
    output_dir: str,
    is_evosuite: bool = False,
    scaffolding_java: str = None,
    cfg=None,
    include_junit3: bool = True,
    include_junit4_5: bool = True,
) -> list[SplitResult]:
    """Split a test-suite .java into one standalone file per test method.

    The returned list mirrors the previous regex-based splitter so callers
    in `main.py` do not need to change. `cfg` is consulted only to pick
    the JDK (cfg.tools.java_home_compile); pass None to fall back to
    $JAVA_HOME or PATH.
    """
    _ensure_cli_built(cfg)

    java_path = Path(java_file)
    out_dir = Path(output_dir)
    out_dir.mkdir(parents=True, exist_ok=True)

    _, java = _resolve_java_tools(cfg)
    cp = f"{JAVAPARSER_JAR}:{JAVAPARSER_DIR}"
    cmd = [java, "-cp", cp, CLI_NAME,
           "--input", str(java_path),
           "--out",   str(out_dir)]
    cmd.append("--include-junit3"      if include_junit3   else "--no-include-junit3")
    cmd.append("--include-junit4-5"    if include_junit4_5 else "--no-include-junit4-5")

    result = subprocess.run(cmd, capture_output=True, text=True)
    if result.returncode != 0:
        log.warning("[test_splitter] TestSplitter CLI failed (%s) for %s:\n%s",
                    result.returncode, java_path.name, result.stderr.strip())
        return []

    try:
        manifest = json.loads(result.stdout or "[]")
    except json.JSONDecodeError as e:
        log.warning("[test_splitter] could not parse CLI manifest for %s: %s\n--- stdout ---\n%s",
                    java_path.name, e, result.stdout[:1000])
        return []

    splits: list[SplitResult] = []
    for entry in manifest:
        out_path = Path(entry["file_path"])
        try:
            source = out_path.read_text(encoding="utf-8")
        except OSError:
            source = ""
        splits.append(SplitResult(
            method_name=entry["method_name"],
            source_code=source,
            file_name=entry["file_name"],
            java_path=str(out_path.resolve()),
            scaffolding_path=scaffolding_java if is_evosuite else None,
            junit_version=entry.get("junit_version", ""),
            marker=entry.get("marker", ""),
        ))

    if not splits:
        log.warning("[test_splitter] no test methods found in %s", java_file)
    else:
        log.info("[test_splitter] split %s → %d test case(s) in %s "
                 "(junit3=%d, junit4-5=%d)",
                 java_path.stem, len(splits), out_dir,
                 sum(1 for s in splits if s.junit_version == "3"),
                 sum(1 for s in splits if s.junit_version == "4-5"))
    return splits
