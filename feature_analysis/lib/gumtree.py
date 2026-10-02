"""
(1) GumTree executor + postprocessor -> GumTree summary.

Runs GumTree `textdiff` twice (plain `java-jdt` and comment-aware `java-jdtc`),
merges the comment/Javadoc actions into the raw diff, then postprocesses the
GumTree-owned signals -- which are COMMENTS ONLY: line comments, block comments,
and Javadocs.

Scope rationale (experiments in structural_analysis_v2/experiments/):
  - Code actions (renames, moves, inserts) are NOT emitted. GumTree only sees
    them as untyped SimpleName updates, which cannot be classified reliably;
    RefactoringMiner owns semantic refactorings and cross-validates at 95-98%
    (experiments/gumtree_refactoringminer_identifier_cross_validation_summary.md).
  - Blank lines are NOT emitted. Whole-file blank-line delta was a crude proxy
    (r=0.85) for the far more precise per-@Test-body block count produced by
    the JavaParser block count (lib/javaparser_blocks.py), which owns that signal now.

All three comment kinds capture insertion, deletion AND modification.

This is the Python equivalent of StructuralAnalysis/GumtreeExecutor.java *plus*
the summary-categorisation step that the Java side never implemented.

Best-effort: GumTree is an external tool, so failures are recorded in the
returned metadata but never raised to the caller.
"""

from __future__ import annotations

import re
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
# Windows .bat launcher is picked automatically. gumtree.jar is Java-17 bytecode.
DEFAULT_GUMTREE_BIN = "tools/gumtree/bin/gumtree"
# "" → use env override (GUMTREE_JAVA_HOME / STRUCTURAL_ANALYSIS_JAVA_HOME) or
# auto-detect a JDK >= 17; falls back to `java` on PATH if none is found.
DEFAULT_GUMTREE_JAVA_HOME = ""
_GUMTREE_MIN_JDK = 17


def run_gumtree(
    original: Path,
    improved: Path,
    out: Path,
    settings: Any,
) -> dict[str, Any]:
    """Run GumTree on (original, improved) and write the gumtree artifacts.

    Writes:
      - gumtree_comments_diff.json   the raw `java-jdtc` diff
      - gumtree_summary.json         the comment features extracted from it
    Returns {"summary": [...], "_metadata": {...}}.

    ONLY the `java-jdtc` grammar is run. The `java-jdt` pass that used to run
    alongside it was removed after checking all 17190 pairs of the main study:
    java-jdt contributed a comment feature that java-jdtc lacked in ZERO tests,
    while java-jdtc found comment features java-jdt missed in 10664 of them, so
    the union of the two equalled java-jdtc alone for 100% of tests. Dropping
    the second pass therefore leaves the feature output unchanged and halves
    GumTree's runtime.

    What IS lost: java-jdt's non-comment AST actions, which differed from
    java-jdtc's in ~23% of tests. Nothing downstream consumed them —
    _summarize_gumtree only emits the three comment features — but if you ever
    want a code-level (non-comment) AST diff, add the pass back rather than
    reading java-jdtc's actions as if they were java-jdt's.
    """
    gumtree_bin = _resolve_tool_bin(
        str(_ns_get(settings, "gumtree_bin", DEFAULT_GUMTREE_BIN)))
    java_home = str(_ns_get(settings, "gumtree_java_home", DEFAULT_GUMTREE_JAVA_HOME)) \
        or _detect_java_home(_GUMTREE_MIN_JDK,
                             ("GUMTREE_JAVA_HOME", "STRUCTURAL_ANALYSIS_JAVA_HOME"))
    timeout = int(_ns_get(settings, "timeout_seconds", 120))

    # `gumtree_comments_diff.json` keeps its name even though it is now the
    # ONLY diff: stability_check.py reads that exact filename, and the 17190
    # files already on disk use it.
    comments_path = out / "gumtree_comments_diff.json"
    summary_path = out / "gumtree_summary.json"

    metadata: dict[str, Any] = {
        "status": "SKIPPED",
        "gumtree_bin": gumtree_bin,
        "java_home": java_home,
    }
    if not Path(gumtree_bin).exists():
        payload = {"summary": [], "_metadata": {**metadata, "error": "gumtree binary not found"}}
        _write_json(summary_path, payload)
        return payload

    comments_cmd = [gumtree_bin, "textdiff", "-g", "java-jdtc", str(original), str(improved), "-o", str(comments_path)]
    env = _java_env(java_home)

    comments_result = _run(comments_cmd, cwd=PROJECT_ROOT, env=env, timeout=timeout)

    metadata = {
        "status": "OK" if comments_result["returncode"] == 0 and comments_path.exists() else "ERROR",
        "gumtree_bin": gumtree_bin,
        "java_home": java_home,
        "grammar": "java-jdtc",
        "comments_command": _redact_command(comments_cmd),
        "comments_returncode": comments_result["returncode"],
        "comments_stdout": comments_result["stdout"],
        "comments_stderr": comments_result["stderr"],
    }
    if not comments_path.exists():
        payload = {"summary": [], "_metadata": metadata}
        _write_json(summary_path, payload)
        return payload

    summary = _summarize_gumtree(_read_json(comments_path), original, improved)
    payload = {"summary": summary, "_metadata": metadata}
    _write_json(summary_path, payload)
    return payload


# GumTree's six edit actions collapse into four verbs a reader recognises.
# insert-tree/delete-tree fire when a whole subtree moves (a Javadoc block is a
# subtree, so Javadoc additions come through as insert-tree); insert-node and
# delete-node fire for leaves (a line comment is a leaf). They are the same
# event as far as the feature is concerned, so they share a verb.
_ACTION_VERB = {
    "insert-node": "Added", "insert-tree": "Added",
    "delete-node": "Deleted", "delete-tree": "Deleted",
    "update-node": "Updated",
    "move-tree": "Moved", "move-node": "Moved",
}

# The three comment kinds, and the coarse feature each one rolls up to.
COMMENT_KINDS = ("LineComment", "BlockComment", "Javadoc")
COARSE_COMMENT = {k: f"{k} change" for k in COMMENT_KINDS}
# The registry's reviewer-facing spellings, so a caller that canonicalises
# before refining still matches. Javadoc's display name says "Comment" too.
_SPACED = {"LineComment": "Line Comment", "BlockComment": "Block Comment",
           "Javadoc": "Javadoc"}
_DISPLAY_COARSE = {"LineComment": "Line Comment Change",
                   "BlockComment": "Block Comment Change",
                   "Javadoc": "Javadoc Comment Change"}
# Every fine-grained name this module can emit, in reading order.
FINE_COMMENT = [f"{k} {v}" for k in COMMENT_KINDS
                for v in ("Added", "Deleted", "Updated", "Moved")]


def comment_kind(tree: str) -> str | None:
    """Which comment kind an action's tree description belongs to.

    Checked in this order because a Javadoc's tree string can also contain the
    substring "Comment"; LineComment is checked first for the same reason the
    original summariser did — the order IS the disambiguation."""
    for kind in COMMENT_KINDS:
        if kind in tree:
            return kind
    return None


def refine_comment_feature(feature: str, action: object) -> str:
    """'LineComment change' + 'insert-node' -> 'LineComment Added'.

    WHY: the coarse feature merges opposite edits. Across the main study's
    17190 pairs, "LineComment change" covers 30484 additions AND 27148
    deletions — nearly one-to-one — so "the model changed the line comments"
    hides that it deletes almost as many as it adds. Javadoc runs the other way
    (13433 added vs 1847 deleted). Splitting them is what lets the result say
    the models replace scattered line comments with structured Javadoc.

    Idempotent: a name that is already fine-grained is returned unchanged, so
    this can be applied again downstream without double-refining."""
    verb = _ACTION_VERB.get(str(action or ""))
    for kind in COMMENT_KINDS:
        # Already fine-grained in either spelling -> leave it alone.
        for v in ("Added", "Deleted", "Updated", "Moved"):
            if feature in (f"{kind} {v}", f"{_SPACED[kind]} {v}"):
                return feature
        # Coarse, in the raw spelling ("LineComment change") or the registry's
        # display spelling ("Line Comment Change"). Both reach here because
        # callers differ in whether they canonicalise first.
        if feature in (COARSE_COMMENT[kind], _DISPLAY_COARSE[kind]):
            return f"{kind} {verb}" if verb else feature
    return feature


def coarsen_comment_feature(feature: str) -> str:
    """The inverse roll-up: 'Line Comment Added' -> 'Line Comment Change'."""
    for kind in COMMENT_KINDS:
        for v in ("Added", "Deleted", "Updated", "Moved"):
            if feature in (f"{kind} {v}", f"{_SPACED[kind]} {v}"):
                return _DISPLAY_COARSE[kind]
    return feature


def _summarize_gumtree(diff: dict[str, Any], original: Path, improved: Path) -> list[dict[str, Any]]:
    actions = diff.get("actions", []) or []
    summary: list[dict[str, Any]] = []
    original_text = original.read_text(encoding="utf-8", errors="replace")
    improved_text = improved.read_text(encoding="utf-8", errors="replace")

    for action in actions:
        action_name = str(action.get("action", ""))
        tree = str(action.get("tree", ""))
        label = action.get("label")
        location = {
            "source": "gumtree",
            "action": action_name,
            "tree": tree,
        }
        if "at" in action:
            location["at"] = action["at"]
        # Task 3 (locate the change): attach a line number from the node's byte
        # span — improved side for inserts, original side otherwise.
        span_start, _span_end = _span(tree)
        if span_start is not None:
            side = "improved" if action_name.startswith("insert") else "original"
            side_text = improved_text if side == "improved" else original_text
            location["side"] = side
            location["line"] = _line_at(side_text, span_start)

        kind = comment_kind(tree)
        if kind:
            summary.append({
                "type": refine_comment_feature(COARSE_COMMENT[kind], action_name),
                "description": _describe_tree_update(action_name, tree, label),
                "location": location,
            })
            continue

    return _dedupe_summary(summary)


def _span(tree: str):
    """Last [start,end] char span in a GumTree tree label, or (None, None)."""
    m = None
    for m in re.finditer(r"\[(\d+),(\d+)\]", tree):
        pass
    return (int(m.group(1)), int(m.group(2))) if m else (None, None)


def _line_at(text: str, byte_pos):
    if byte_pos is None or not (0 <= byte_pos <= len(text)):
        return None
    return text[:byte_pos].count("\n") + 1


def _describe_tree_update(action: str, tree: str, label: Any) -> str:
    if label is not None:
        return f"{action}: {tree} -> {label}"
    return f"{action}: {tree}"


