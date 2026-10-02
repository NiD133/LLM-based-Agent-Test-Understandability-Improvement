"""
(2) RefactoringMiner executor + summary generation.

RefactoringMiner only diffs git commits, so this builds a throwaway git repo
(commit original test -> commit improved test), runs the RefactoringMiner
binary on that single commit, then summarises the detected refactorings into
the same {type, description, location} schema used by gumtree.py.

This is the Python equivalent of running RefactoringMiner + the schema-shaped
summary; the Java side (RefactoringSummary.java) only printed a pre-existing
RefactoringMiner JSON and never executed the tool.

Best-effort: failures are recorded in the returned metadata, never raised.
"""

from __future__ import annotations

import os
import re
import tempfile
from pathlib import Path
from typing import Any

from .common import (
    PROJECT_ROOT,
    _dedupe_summary,
    _detect_java_home,
    _java_env,
    _ns_get,
    _read_json,
    _redact_command,
    _resolve_tool_bin,
    _run,
    _write_json,
)


# Local tools/ dir (bundled). Relative → resolved against PROJECT_ROOT, and the
# Windows .bat launcher is picked automatically. RefactoringMiner-3.0.12 is
# Java-21 bytecode.
DEFAULT_REFACTORINGMINER_BIN = "tools/refactoringminer/bin/RefactoringMiner"
# "" → use env override (REFACTORINGMINER_JAVA_HOME / STRUCTURAL_ANALYSIS_JAVA_HOME)
# or auto-detect a JDK >= 21; falls back to `java` on PATH if none is found.
DEFAULT_REFACTORINGMINER_JAVA_HOME = ""
_REFACTORINGMINER_MIN_JDK = 21


def run_refactoringminer(
    original: Path,
    improved: Path,
    out: Path,
    settings: Any,
) -> dict[str, Any]:
    """Run RefactoringMiner on (original, improved) via a throwaway git repo.

    Writes:
      - refactoringminer_raw.json
      - refactoringminer_summary.json
    Returns {"summary": [...], "_metadata": {...}}.
    """
    refminer_bin = _resolve_tool_bin(
        str(_ns_get(settings, "refactoringminer_bin", DEFAULT_REFACTORINGMINER_BIN)))
    java_home = str(_ns_get(settings, "refactoringminer_java_home", DEFAULT_REFACTORINGMINER_JAVA_HOME)) \
        or _detect_java_home(_REFACTORINGMINER_MIN_JDK,
                             ("REFACTORINGMINER_JAVA_HOME", "STRUCTURAL_ANALYSIS_JAVA_HOME"))
    timeout = int(_ns_get(settings, "timeout_seconds", 120))
    raw_path = out / "refactoringminer_raw.json"
    summary_path = out / "refactoringminer_summary.json"

    metadata: dict[str, Any] = {
        "status": "SKIPPED",
        "refactoringminer_bin": refminer_bin,
        "java_home": java_home,
    }
    if not Path(refminer_bin).exists():
        payload = {"summary": [], "_metadata": {**metadata, "error": "RefactoringMiner binary not found"}}
        _write_json(summary_path, payload)
        return payload

    with tempfile.TemporaryDirectory(prefix="structural_diff_git_") as tmp:
        repo = Path(tmp)
        rel_file = _java_relative_path(original)
        work_file = repo / rel_file
        work_file.parent.mkdir(parents=True, exist_ok=True)
        work_file.write_text(original.read_text(encoding="utf-8", errors="replace"), encoding="utf-8")

        _git(repo, ["init"])
        _git(repo, ["config", "user.email", "structural-diff@example.local"])
        _git(repo, ["config", "user.name", "Structural Diff"])
        _git(repo, ["remote", "add", "origin", "https://local.example/structural-diff.git"])
        _git(repo, ["add", "."])
        _git(repo, ["commit", "-m", "original test"])

        work_file.write_text(improved.read_text(encoding="utf-8", errors="replace"), encoding="utf-8")
        _git(repo, ["add", "."])
        no_changes = _git(repo, ["diff", "--cached", "--quiet"])["returncode"] == 0
        if no_changes:
            raw_no_change = {"commits": [{"sha1": "NO_CHANGE", "refactorings": []}]}
            _write_json(raw_path, raw_no_change)
            metadata = {
                "status": "OK",
                "refactoringminer_bin": refminer_bin,
                "java_home": java_home,
                "no_changes": True,
            }
            payload = {"summary": [], "_metadata": metadata}
            _write_json(summary_path, payload)
            return payload

        _git(repo, ["commit", "-m", "improved test"])
        improved_commit = _git(repo, ["rev-parse", "HEAD"])["stdout"].strip()

        cmd = [refminer_bin, "-c", str(repo), improved_commit, "-json", str(raw_path)]
        result = _run(cmd, cwd=PROJECT_ROOT, env=_java_env(java_home), timeout=timeout)

    metadata = {
        "status": "OK" if result["returncode"] == 0 and raw_path.exists() else "ERROR",
        "refactoringminer_bin": refminer_bin,
        "java_home": java_home,
        "command": _redact_command(cmd),
        "returncode": result["returncode"],
        "stdout": result["stdout"],
        "stderr": result["stderr"],
    }
    if not raw_path.exists():
        payload = {"summary": [], "_metadata": metadata}
        _write_json(summary_path, payload)
        return payload

    raw = _read_json(raw_path)
    summary = _summarize_refactoringminer(raw)
    payload = {"summary": summary, "_metadata": metadata}
    _write_json(summary_path, payload)
    return payload


def _summarize_refactoringminer(raw: dict[str, Any]) -> list[dict[str, Any]]:
    summary: list[dict[str, Any]] = []
    for commit in raw.get("commits", []) or []:
        for refactoring in commit.get("refactorings", []) or []:
            ref_type = str(refactoring.get("type", "Refactoring"))
            location = {
                "source": "refactoringminer",
                "refactoring_type": ref_type,
            }
            # Task 3 (locate the change): pull the line from RefactoringMiner's
            # own locations (improved=rightSide preferred, else leftSide).
            line, side = _refminer_line(refactoring)
            if line is not None:
                location["line"] = line
                location["side"] = side
            summary.append({
                "type": _map_refactoring_type(ref_type),
                "description": str(refactoring.get("description", ref_type)),
                "location": location,
            })
    return _dedupe_summary(summary)


def _refminer_line(refactoring: dict[str, Any]):
    """(line, side) from a RefactoringMiner refactoring's locations."""
    for key, side in (("rightSideLocations", "improved"), ("leftSideLocations", "original")):
        locs = refactoring.get(key) or []
        if locs and isinstance(locs[0], dict) and locs[0].get("startLine") is not None:
            return int(locs[0]["startLine"]), side
    return None, None


def _map_refactoring_type(ref_type: str) -> str:
    mapping = {
        "Rename Variable": "Variable name",
        "Rename Parameter": "Variable name",
        "Rename Method": "Method name",
        "Rename Attribute": "Attribute name",
        "Rename Class": "Class name",
    }
    return mapping.get(ref_type, ref_type)


def _java_relative_path(source: Path) -> Path:
    text = source.read_text(encoding="utf-8", errors="replace")
    package_match = re.search(r"^\s*package\s+([A-Za-z_][\w.]*)\s*;", text, re.MULTILINE)
    if not package_match:
        return Path(source.name)
    return Path("src/test/java") / Path(*package_match.group(1).split(".")) / source.name


def _git(repo: Path, args: list[str]) -> dict[str, Any]:
    return _run(["git", "-C", str(repo), *args], cwd=PROJECT_ROOT, env=os.environ.copy(), timeout=60)
