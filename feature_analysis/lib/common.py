"""
Shared helpers for the lib package.

Subprocess execution, Java env setup, JSON I/O, and summary-dedup logic used
by gumtree.py, refactoringminer.py and summary.py. Extracted verbatim from the
former scripts/structural_diff.py so behaviour is unchanged.

Tool paths are NOT searched for here: feature_analysis.py passes the paths
filled in at its top (GUMTREE_BIN, REFACTORINGMINER_BIN) straight through.
"""

from __future__ import annotations

import json
import os
import subprocess
from pathlib import Path
from typing import Any


# This package lives at <root>/lib/, so parents[1] == the folder root.
PROJECT_ROOT = Path(__file__).resolve().parents[1]


def _resolve_under_project_root(p: str) -> str:
    """Absolute paths are used as given; a relative path resolves against
    PROJECT_ROOT (not the cwd), so `tools/...` works from anywhere."""
    if not p:
        return p
    pp = Path(p)
    if pp.is_absolute():
        return str(pp)
    return str((PROJECT_ROOT / pp).resolve())


def _run(
    cmd: list[str],
    cwd: Path,
    env: dict[str, str],
    timeout: int,
) -> dict[str, Any]:
    try:
        proc = subprocess.run(
            cmd,
            cwd=str(cwd),
            env=env,
            text=True,
            stdout=subprocess.PIPE,
            stderr=subprocess.PIPE,
            timeout=timeout,
            check=False,
        )
        return {
            "returncode": proc.returncode,
            "stdout": proc.stdout[-12000:],
            "stderr": proc.stderr[-12000:],
        }
    except subprocess.TimeoutExpired as exc:
        return {
            "returncode": 124,
            "stdout": (exc.stdout or "")[-12000:] if isinstance(exc.stdout, str) else "",
            "stderr": (exc.stderr or "")[-12000:] if isinstance(exc.stderr, str) else f"Timed out after {timeout}s",
        }


def _java_env(java_home: str) -> dict[str, str]:
    env = os.environ.copy()
    if java_home and Path(java_home).exists():
        env["JAVA_HOME"] = java_home
        env["PATH"] = str(Path(java_home) / "bin") + os.pathsep + env.get("PATH", "")
    return env


def _resolve_tool_bin(path: str) -> str:
    """Resolve a Gradle-style launcher path, picking the right one for the OS.

    The GumTree / RefactoringMiner dists ship two launchers side by side:
      bin/gumtree        (POSIX shell script)
      bin/gumtree.bat    (Windows batch script)
    On Windows we must invoke the .bat; elsewhere the extension-less script.
    Relative paths resolve against PROJECT_ROOT so `tools/...` works anywhere.
    """
    resolved = Path(_resolve_under_project_root(path))
    if os.name == "nt":
        bat = resolved.with_suffix(".bat")
        if bat.exists():
            return str(bat)
    return str(resolved)


# Common JDK install roots per OS, newest-first is decided by version parsing below.
_JDK_SEARCH_GLOBS = [
    # Windows
    "C:/Program Files/Eclipse Adoptium/jdk-*",
    "C:/Program Files/Java/jdk-*",
    "C:/Program Files/Microsoft/jdk-*",
    "C:/Program Files/Amazon Corretto/jdk*",
    str(Path.home() / ".jdks" / "*"),
    # macOS
    "/Library/Java/JavaVirtualMachines/*/Contents/Home",
    # Linux
    "/usr/lib/jvm/*",
]


def _jdk_major(java_home: Path) -> int | None:
    """Best-effort major version from a JDK home path (e.g. jdk-21.0.2 -> 21).

    On macOS the home is <...>/jdk-21.jdk/Contents/Home, so the version sits two
    levels above the home dir; try the nearest path components in turn."""
    import re as _re
    names = [java_home.name] + [par.name for par in list(java_home.parents)[:2]]
    for name in names:
        m = _re.search(r"(?:jdk[-_]?|temurin[-_]?|corretto[-_]?)(\d+)", name, _re.IGNORECASE)
        if m:
            return int(m.group(1))
    for name in names:
        m = _re.search(r"(\d+)", name)
        if m:
            return int(m.group(1))
    return None


def _detect_java_home(min_major: int, env_vars: tuple[str, ...] = ()) -> str:
    """Find a JDK home whose major version is >= min_major.

    Resolution order:
      1. explicit env-var overrides (first that points at an existing dir),
      2. auto-detect the highest-versioned JDK in common install locations,
      3. "" (caller falls back to whatever `java` is on PATH).
    """
    import glob as _glob
    for var in env_vars:
        val = os.environ.get(var, "").strip()
        if val and Path(val).exists():
            return val

    best: tuple[int, str] | None = None
    for pattern in _JDK_SEARCH_GLOBS:
        for hit in _glob.glob(pattern):
            home = Path(hit)
            if not (home / "bin").exists():
                continue
            major = _jdk_major(home)
            if major is None or major < min_major:
                continue
            if best is None or major > best[0]:
                best = (major, str(home))
    return best[1] if best else ""


def _redact_command(cmd: list[str]) -> list[str]:
    return [str(part) for part in cmd]


def _ns_get(obj: Any, name: str, default: Any = None) -> Any:
    if obj is None:
        return default
    if isinstance(obj, dict):
        return obj.get(name, default)
    return getattr(obj, name, default)


def _read_json(path: Path) -> dict[str, Any]:
    try:
        return json.loads(path.read_text(encoding="utf-8"))
    except json.JSONDecodeError:
        return {}


def _write_json(path: Path, data: Any) -> None:
    path.write_text(json.dumps(data, indent=2, ensure_ascii=False), encoding="utf-8")


def _stable_json(value: Any) -> str:
    return json.dumps(value, sort_keys=True, ensure_ascii=False)


def _dedupe_summary(items: list[dict[str, Any]]) -> list[dict[str, Any]]:
    seen: set[str] = set()
    deduped: list[dict[str, Any]] = []
    for item in items:
        key = json.dumps(
            {
                "type": item.get("type"),
                "description": item.get("description"),
            },
            sort_keys=True,
            ensure_ascii=False,
        )
        if key not in seen:
            deduped.append(item)
            seen.add(key)
    return deduped
