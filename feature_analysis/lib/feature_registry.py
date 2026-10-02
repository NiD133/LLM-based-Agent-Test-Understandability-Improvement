"""Canonical feature names and display order for structural analysis outputs."""

from __future__ import annotations

from collections import OrderedDict
from typing import Iterable


# The order here is the display order used by per-test summaries, aggregate JSON,
# CSVs, and heatmaps. Put reviewer-facing, high-level changes first; keep rarer
# RefactoringMiner-only changes later.
FEATURE_REGISTRY: list[dict[str, object]] = [
    {"id": "variable_rename", "display": "Variable Rename",
     "aliases": ["Variable name", "Variable_rename"]},
    # RefactoringMiner reports this as "Rename Method"; the id has always been
    # method_rename, so the display said "Method_name" by mistake.
    {"id": "method_rename", "display": "Method Rename",
     "aliases": ["Method name", "Method_name"]},
    # --- Comments: two directions per kind -----------------------------
    # What the pipeline emits (lib/comment_units.comment_entries): the
    # comment-unit post-processor's labels, split into "Added/Updated" (the
    # LLM wrote or rewrote a comment of that kind) and "Deleted" (an original
    # comment removed while its code stays). The Deleted rows reuse the
    # display names below.
    {"id": "line_comment_added_updated", "display": "Line Comment Added/Updated",
     "aliases": ["LineComment Added/Updated"]},
    {"id": "javadoc_added_updated", "display": "Javadoc Added/Updated",
     "aliases": []},
    {"id": "block_comment_added_updated", "display": "Block Comment Added/Updated",
     "aliases": ["BlockComment Added/Updated"]},
    # --- GumTree comments by raw edit verb, then the coarse roll-up -------
    # LEGACY: GumTree's own edit actions (lib/gumtree.refine_comment_feature).
    # No longer produced by the merge stage; kept so older outputs and
    # stability_check.py still canonicalise.
    {"id": "line_comment_added", "display": "Line Comment Added",
     "aliases": ["LineComment Added"]},
    {"id": "line_comment_deleted", "display": "Line Comment Deleted",
     "aliases": ["LineComment Deleted"]},
    {"id": "line_comment_updated", "display": "Line Comment Updated",
     "aliases": ["LineComment Updated"]},
    {"id": "line_comment_moved", "display": "Line Comment Moved",
     "aliases": ["LineComment Moved"]},
    {"id": "javadoc_added", "display": "Javadoc Added",
     "aliases": ["Javadoc Added"]},
    {"id": "javadoc_deleted", "display": "Javadoc Deleted",
     "aliases": ["Javadoc Deleted"]},
    {"id": "javadoc_updated", "display": "Javadoc Updated",
     "aliases": ["Javadoc Updated"]},
    {"id": "javadoc_moved", "display": "Javadoc Moved",
     "aliases": ["Javadoc Moved"]},
    {"id": "block_comment_added", "display": "Block Comment Added",
     "aliases": ["BlockComment Added"]},
    {"id": "block_comment_deleted", "display": "Block Comment Deleted",
     "aliases": ["BlockComment Deleted"]},
    {"id": "block_comment_updated", "display": "Block Comment Updated",
     "aliases": ["BlockComment Updated"]},
    {"id": "block_comment_moved", "display": "Block Comment Moved",
     "aliases": ["BlockComment Moved"]},
    {"id": "line_comment_change", "display": "Line Comment Change",
     "aliases": ["Comment change", "LineComment change", "Line Comment change"]},
    {"id": "block_comment_change", "display": "Block Comment Change",
     "aliases": ["BlockComment change", "Block Comment change"]},
    {"id": "javadoc_change", "display": "Javadoc Comment Change",
     "aliases": ["Javadoc change", "Javadoc changed"]},
    # --- JavaParser: test-method-internal segmentation -------------------
    {"id": "blank_line_separation_added", "display": "Blank-Line Separation Added",
     "aliases": ["Test Body Block change", "Blank lines"]},
    # --- RefactoringMiner -----------------------------------------------
    {"id": "attribute_rename", "display": "Attribute Rename",
     "aliases": ["Attribute name", "Attribute rename"]},
    {"id": "class_rename", "display": "Class Rename",
     "aliases": ["Class name", "Class rename"]},
    {"id": "identifier_rename", "display": "Identifier Rename",
     "aliases": ["Identifier rename", "Identifier update"]},
    {"id": "extract_variable", "display": "Extract Variable", "aliases": ["Extract Variable"]},
    {"id": "inline_variable", "display": "Inline Variable", "aliases": ["Inline Variable"]},
    {"id": "change_variable_type", "display": "Change Variable Type", "aliases": ["Change Variable Type"]},
    {"id": "add_variable_modifier", "display": "Add Variable Modifier", "aliases": ["Add Variable Modifier"]},
    {"id": "remove_variable_modifier", "display": "Remove Variable Modifier", "aliases": ["Remove Variable Modifier"]},
    {"id": "parameterize_variable", "display": "Parameterize Variable", "aliases": ["Parameterize Variable"]},
    {"id": "merge_variable", "display": "Merge Variable", "aliases": ["Merge Variable"]},
    {"id": "split_variable", "display": "Split Variable", "aliases": ["Split Variable"]},
    {"id": "extract_attribute", "display": "Extract Attribute", "aliases": ["Extract Attribute"]},
    {"id": "inline_attribute", "display": "Inline Attribute", "aliases": ["Inline Attribute"]},
    {"id": "change_attribute_type", "display": "Change Attribute Type", "aliases": ["Change Attribute Type"]},
    {"id": "add_attribute_modifier", "display": "Add Attribute Modifier", "aliases": ["Add Attribute Modifier"]},
    {"id": "remove_attribute_modifier", "display": "Remove Attribute Modifier", "aliases": ["Remove Attribute Modifier"]},
    {"id": "parameterize_attribute", "display": "Parameterize Attribute", "aliases": ["Parameterize Attribute"]},
    {"id": "merge_attribute", "display": "Merge Attribute", "aliases": ["Merge Attribute"]},
    {"id": "move_attribute", "display": "Move Attribute", "aliases": ["Move Attribute"]},
    {"id": "encapsulate_attribute", "display": "Encapsulate Attribute", "aliases": ["Encapsulate Attribute"]},
    {"id": "replace_variable_with_attribute", "display": "Replace Variable With Attribute", "aliases": ["Replace Variable With Attribute"]},
    {"id": "replace_attribute_with_variable", "display": "Replace Attribute With Variable", "aliases": ["Replace Attribute With Variable"]},
    {"id": "extract_method", "display": "Extract Method", "aliases": ["Extract Method"]},
    {"id": "inline_method", "display": "Inline Method", "aliases": ["Inline Method"]},
    {"id": "move_method", "display": "Move Method", "aliases": ["Move Method"]},
    {"id": "move_and_rename_method", "display": "Move And Rename Method", "aliases": ["Move And Rename Method"]},
    {"id": "extract_and_move_method", "display": "Extract And Move Method", "aliases": ["Extract And Move Method"]},
    {"id": "split_method", "display": "Split Method", "aliases": ["Split Method"]},
    {"id": "merge_method", "display": "Merge Method", "aliases": ["Merge Method"]},
    {"id": "change_method_access_modifier", "display": "Change Method Access Modifier", "aliases": ["Change Method Access Modifier"]},
    {"id": "add_method_modifier", "display": "Add Method Modifier", "aliases": ["Add Method Modifier"]},
    {"id": "remove_method_modifier", "display": "Remove Method Modifier", "aliases": ["Remove Method Modifier"]},
    {"id": "add_method_annotation", "display": "Add Method Annotation", "aliases": ["Add Method Annotation"]},
    {"id": "remove_method_annotation", "display": "Remove Method Annotation", "aliases": ["Remove Method Annotation"]},
    {"id": "modify_method_annotation", "display": "Modify Method Annotation", "aliases": ["Modify Method Annotation"]},
    {"id": "add_parameter", "display": "Add Parameter", "aliases": ["Add Parameter"]},
    {"id": "remove_parameter", "display": "Remove Parameter", "aliases": ["Remove Parameter"]},
    {"id": "reorder_parameter", "display": "Reorder Parameter", "aliases": ["Reorder Parameter"]},
    {"id": "split_parameter", "display": "Split Parameter", "aliases": ["Split Parameter"]},
    {"id": "rename_parameter", "display": "Rename Parameter", "aliases": ["Rename Parameter"]},
    {"id": "localize_parameter", "display": "Localize Parameter", "aliases": ["Localize Parameter"]},
    {"id": "change_parameter_type", "display": "Change Parameter Type", "aliases": ["Change Parameter Type"]},
    {"id": "add_parameter_modifier", "display": "Add Parameter Modifier", "aliases": ["Add Parameter Modifier"]},
    {"id": "remove_parameter_modifier", "display": "Remove Parameter Modifier", "aliases": ["Remove Parameter Modifier"]},
    {"id": "change_return_type", "display": "Change Return Type", "aliases": ["Change Return Type"]},
    {"id": "add_thrown_exception_type", "display": "Add Thrown Exception Type", "aliases": ["Add Thrown Exception Type"]},
    {"id": "remove_thrown_exception_type", "display": "Remove Thrown Exception Type", "aliases": ["Remove Thrown Exception Type"]},
    {"id": "change_thrown_exception_type", "display": "Change Thrown Exception Type", "aliases": ["Change Thrown Exception Type"]},
    {"id": "assert_throws", "display": "Assert Throws", "aliases": ["Assert Throws"]},
    {"id": "extract_class", "display": "Extract Class", "aliases": ["Extract Class"]},
    {"id": "extract_superclass", "display": "Extract Superclass", "aliases": ["Extract Superclass"]},
    {"id": "move_class", "display": "Move Class", "aliases": ["Move Class"]},
    {"id": "move_and_rename_class", "display": "Move And Rename Class", "aliases": ["Move And Rename Class"]},
    {"id": "merge_class", "display": "Merge Class", "aliases": ["Merge Class"]},
    {"id": "change_class_access_modifier", "display": "Change Class Access Modifier", "aliases": ["Change Class Access Modifier"]},
    {"id": "add_class_modifier", "display": "Add Class Modifier", "aliases": ["Add Class Modifier"]},
    {"id": "remove_class_modifier", "display": "Remove Class Modifier", "aliases": ["Remove Class Modifier"]},
    {"id": "add_class_annotation", "display": "Add Class Annotation", "aliases": ["Add Class Annotation"]},
    {"id": "remove_class_annotation", "display": "Remove Class Annotation", "aliases": ["Remove Class Annotation"]},
    {"id": "modify_class_annotation", "display": "Modify Class Annotation", "aliases": ["Modify Class Annotation"]},
    {"id": "add_variable_annotation", "display": "Add Variable Annotation", "aliases": ["Add Variable Annotation"]},
    {"id": "remove_variable_annotation", "display": "Remove Variable Annotation", "aliases": ["Remove Variable Annotation"]},
    {"id": "add_attribute_annotation", "display": "Add Attribute Annotation", "aliases": ["Add Attribute Annotation"]},
    {"id": "remove_attribute_annotation", "display": "Remove Attribute Annotation", "aliases": ["Remove Attribute Annotation"]},
    {"id": "modify_attribute_annotation", "display": "Modify Attribute Annotation", "aliases": ["Modify Attribute Annotation"]},
    {"id": "move_code", "display": "Move Code", "aliases": ["Move Code"]},
    {"id": "invert_condition", "display": "Invert Condition", "aliases": ["Invert Condition"]},
    {"id": "merge_conditional", "display": "Merge Conditional", "aliases": ["Merge Conditional"]},
    {"id": "replace_conditional_with_ternary", "display": "Replace Conditional With Ternary", "aliases": ["Replace Conditional With Ternary"]},
    {"id": "replace_loop_with_pipeline", "display": "Replace Loop With Pipeline", "aliases": ["Replace Loop With Pipeline"]},
    {"id": "replace_anonymous_with_lambda", "display": "Replace Anonymous With Lambda", "aliases": ["Replace Anonymous With Lambda"]},
    {"id": "replace_anonymous_with_class", "display": "Replace Anonymous With Class", "aliases": ["Replace Anonymous With Class"]},
    {"id": "replace_generic_with_diamond", "display": "Replace Generic With Diamond", "aliases": ["Replace Generic With Diamond"]},
    {"id": "try_with_resources", "display": "Try With Resources", "aliases": ["Try With Resources"]},
    {"id": "parameterize_test", "display": "Parameterize Test", "aliases": ["Parameterize Test"]},
    {"id": "pull_up_method", "display": "Pull Up Method", "aliases": ["Pull Up Method"]},
    {"id": "merge_catch", "display": "Merge Catch", "aliases": ["Merge Catch"]},
]


ALIAS_TO_DISPLAY: dict[str, str] = {}
FEATURE_ORDER: list[str] = []
for spec in FEATURE_REGISTRY:
    display = str(spec["display"])
    FEATURE_ORDER.append(display)
    ALIAS_TO_DISPLAY[display] = display
    for alias in spec.get("aliases", []):
        ALIAS_TO_DISPLAY[str(alias)] = display

FEATURE_RANK = {feature: idx for idx, feature in enumerate(FEATURE_ORDER)}


def canonical_feature(name: object) -> str:
    """Return the reviewer-facing canonical feature name for a raw feature."""
    text = str(name or "").strip()
    return ALIAS_TO_DISPLAY.get(text, text)


def canonicalize_entry(entry: dict) -> dict:
    out = dict(entry)
    out["type"] = canonical_feature(out.get("type"))
    return out


def sort_features(features: Iterable[object]) -> list[str]:
    unique = {canonical_feature(feature) for feature in features if str(feature or "").strip()}
    return sorted(unique, key=lambda f: (FEATURE_RANK.get(f, len(FEATURE_RANK)), f))


def sort_entries(entries: Iterable[dict]) -> list[dict]:
    return sorted(
        entries,
        key=lambda entry: (
            FEATURE_RANK.get(canonical_feature(entry.get("type")), len(FEATURE_RANK)),
            str(entry.get("description", "")),
        ),
    )


def ordered_feature_items(mapping: dict[str, object]) -> "OrderedDict[str, object]":
    return OrderedDict(
        (feature, mapping[feature])
        for feature in sorted(mapping, key=lambda f: (FEATURE_RANK.get(f, len(FEATURE_RANK)), f))
    )
