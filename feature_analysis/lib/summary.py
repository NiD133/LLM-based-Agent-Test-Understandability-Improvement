"""
(3) Per-test structural-diff summary generation.

Orchestrates gumtree.run_gumtree + refactoringminer.run_refactoringminer for a
single (original, improved) Java test, then integrates their two summaries into
one deduplicated `structural_diff_summary.json` (plus a concise count-by-type
view) for that test.

This module is intentionally best-effort: GumTree and RefactoringMiner are
external tools, so failures are recorded in metadata files but never block the
main compile/coverage/mutation pipeline.

Public entry point (backward-compatible with the old structural_diff.py):
    write_structural_diff_outputs(original_java_path, improved_java_path, out_dir, cfg)
"""

from __future__ import annotations

from pathlib import Path
from typing import Any

from .common import _dedupe_summary, _ns_get, _write_json
from .feature_registry import canonicalize_entry, ordered_feature_items, sort_entries, sort_features
from .gumtree import run_gumtree
from .refactoringminer import run_refactoringminer


# GumTree owns COMMENTS ONLY. Its code actions (renames, moves, inserts) are
# dropped here: RefactoringMiner owns semantic refactorings, and blank-line /
# segmentation structure is owned by the JavaParser block-count analysis
# (lib/javaparser_blocks.py), which measures it per
# @Test body instead of per file.
GUMTREE_FINAL_TYPES = {
    "Line Comment change",
    "Block Comment change",
    "Javadoc change",
}


def write_structural_diff_outputs(
    original_java_path: str | Path,
    improved_java_path: str | Path,
    out_dir: str | Path,
    cfg: Any = None,
) -> dict[str, Any]:
    """Write GumTree + RefactoringMiner diff artifacts into *out_dir*.

    Expected output files:
      - gumtree_comments_diff.json   the raw `java-jdtc` diff (the only grammar
                                     run; see run_gumtree for why java-jdt was
                                     dropped)
      - gumtree_summary.json
      - refactoringminer_raw.json
      - refactoringminer_summary.json
      - structural_diff_summary.json
      - structural_diff_metadata.json
    """
    out = Path(out_dir)
    out.mkdir(parents=True, exist_ok=True)
    metadata_path = out / "structural_diff_metadata.json"

    settings = _ns_get(cfg, "structural_diff", None)
    enabled = bool(_ns_get(settings, "enabled", True))
    if not enabled:
        metadata = {"enabled": False, "status": "SKIPPED"}
        _write_json(metadata_path, metadata)
        return metadata

    original = Path(original_java_path)
    improved = Path(improved_java_path)
    metadata: dict[str, Any] = {
        "enabled": True,
        "original_java_path": str(original),
        "improved_java_path": str(improved),
        "out_dir": str(out),
        "tools": {},
    }

    gumtree_summary: dict[str, Any] = {"summary": []}
    refminer_summary: dict[str, Any] = {"summary": []}

    try:
        gumtree_summary = run_gumtree(original, improved, out, settings)
        metadata["tools"]["gumtree"] = gumtree_summary.get("_metadata", {})
    except Exception as exc:  # pragma: no cover - defensive external-tool guard
        metadata["tools"]["gumtree"] = {"status": "ERROR", "error": str(exc)}
        _write_json(out / "gumtree_summary.json", {"summary": [], "error": str(exc)})

    try:
        refminer_summary = run_refactoringminer(original, improved, out, settings)
        metadata["tools"]["refactoringminer"] = refminer_summary.get("_metadata", {})
    except Exception as exc:  # pragma: no cover - defensive external-tool guard
        metadata["tools"]["refactoringminer"] = {"status": "ERROR", "error": str(exc)}
        _write_json(out / "refactoringminer_summary.json", {"summary": [], "error": str(exc)})

    combined = _merge_summaries(
        gumtree_summary.get("summary", []),
        refminer_summary.get("summary", []),
    )
    combined_payload = {
        "summary": combined,
        "concise_summary": _concise_summary(combined),
        "sources": {
            "gumtree_summary": "gumtree_summary.json",
            "refactoringminer_summary": "refactoringminer_summary.json",
        },
    }
    _write_json(out / "structural_diff_summary.json", combined_payload)

    metadata["status"] = "OK"
    _write_json(metadata_path, metadata)
    return metadata


def _merge_summaries(*summaries: list[dict[str, Any]]) -> list[dict[str, Any]]:
    merged: list[dict[str, Any]] = []
    for summary in summaries:
        for item in summary or []:
            normalized = _canonicalize_source_entry(item)
            if normalized and _keep_final_entry(normalized):
                merged.append(normalized)
    return sort_entries(_dedupe_summary(merged))


def _canonicalize_source_entry(entry: dict[str, Any]) -> dict[str, Any] | None:
    adjusted = dict(entry)
    location = adjusted.get("location") if isinstance(adjusted.get("location"), dict) else {}
    if str(location.get("source", "")).strip() == "gumtree" and str(adjusted.get("type", "")).strip() == "Comment change":
        kind = _gumtree_comment_kind(adjusted)
        if kind == "LineComment":
            adjusted["type"] = "LineComment change"
        elif kind == "BlockComment":
            adjusted["type"] = "BlockComment change"
        elif kind == "Javadoc":
            adjusted["type"] = "Javadoc change"
        else:
            return None
    return canonicalize_entry(adjusted)


def _keep_final_entry(entry: dict[str, Any]) -> bool:
    location = entry.get("location") if isinstance(entry.get("location"), dict) else {}
    if str(location.get("source", "")).strip() == "gumtree":
        return str(entry.get("type", "")).strip() in GUMTREE_FINAL_TYPES
    return True


def _gumtree_comment_kind(entry: dict[str, Any]) -> str:
    location = entry.get("location") if isinstance(entry.get("location"), dict) else {}
    text = str(location.get("tree") or entry.get("description") or "")
    for kind in ("LineComment", "BlockComment", "Javadoc"):
        if kind in text:
            return kind
    return ""


def _concise_summary(summary: list[dict[str, Any]]) -> dict[str, Any]:
    counts: dict[str, int] = {}
    for item in summary:
        item_type = str(item.get("type", "Unknown"))
        counts[item_type] = counts.get(item_type, 0) + 1
    return {
        "counts_by_type": ordered_feature_items(counts),
        "total_changes": len(summary),
        "features_present": sort_features(counts),
    }
