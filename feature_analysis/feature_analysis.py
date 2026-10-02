#!/usr/bin/env python3
"""
feature_analysis.py -- which features did the improvement introduce?

For every (original, improved) test pair under DATA_ROOT, three tools with
non-overlapping scopes detect the changes:

  RefactoringMiner   semantic refactorings  (Variable_rename, Method_name, Extract Variable, ...)
  GumTree            comments only          (Line Comment / Javadoc / Block Comment, each split
                                             into Added/Updated and Deleted -- via the comment
                                             post-processor, not GumTree's raw edit verbs)
  JavaParser         @Test-body segmentation (Test Body Block change = block count delta)

Data layout it expects (DATA_ROOT):

  <model>/<project>/<src>/<gran>/.../<test_dir>/
      <Name>.java            improved test
      original/<Name>.java   original test (same file name)

The data folder is never written to; everything goes under OUT_ROOT:

  diff/<rel test>/structural_diff/   GumTree + RefactoringMiner raw and summary files (stage diff)
  comments/<rel test>/comment_labels.json   comment units + post-processed labels (stage comments)
  blocks/<rel test>/test_blocks_javaparser.json + blocks/summary.json          (stage blocks)
  per_test/<test>.json               the three tools merged for one test        (stage merge)
  feature_by_test.csv                one row per test, features present
  feature_frequency.csv              per (model, src, granularity): count / total / rate per feature
  feature_summary.json, feature_analysis.md

Run
  python feature_analysis.py                         # all stages, paths from CONFIG below
  python feature_analysis.py --stage diff --jobs 16
  python feature_analysis.py --stage comments        # needs the diff stage's gumtree_comments_diff.json
  python feature_analysis.py --data-root <dir> --out <dir>
  python feature_analysis.py --filter BoundedReader_ESTest --limit 5   # debug a subset
"""

from __future__ import annotations

import argparse
import csv
import json
import logging
import sys
import time
from collections import Counter, defaultdict
from datetime import datetime
from pathlib import Path
from typing import Any

HERE = Path(__file__).resolve().parent
sys.path.insert(0, str(HERE))

from lib import comment_units  # noqa: E402
from lib import javaparser_blocks  # noqa: E402
from lib.common import _detect_java_home, _resolve_tool_bin  # noqa: E402
from lib.feature_registry import canonical_feature, ordered_feature_items, sort_entries, sort_features  # noqa: E402

# ============================== CONFIG ======================================
# Everything below is read from feature_analysis/config.yaml — edit THAT file,
# not this one. Each value can still be overridden on the command line.
#
# The loader resolves relative paths against the PROJECT ROOT (this folder's
# parent), so the config stays portable and shares the main study's tools/.
# ============================================================================

PROJECT_ROOT = HERE.parent
CONFIG_PATH = HERE / "config.yaml"


def _resolve(value: str) -> str:
    """`~` expands; a relative path resolves against the project root."""
    if not value:
        return ""
    p = Path(str(value)).expanduser()
    return str(p if p.is_absolute() else (PROJECT_ROOT / p))


def _load_config(path: Path = CONFIG_PATH) -> dict:
    """feature_analysis/config.yaml, or {} when it is absent."""
    if not path.exists():
        return {}
    import yaml
    return yaml.safe_load(path.read_text(encoding="utf-8")) or {}


_CFG = _load_config()

# Root of the <model>/<project>/<src>/<gran>/.../<test>/ tree (read-only).
DATA_ROOT = _resolve(_CFG.get("data_root") or "agent_improvement/data/improved")

# Model trees that live OUTSIDE data_root, as {reported_model_name: path}.
EXTRA_ROOTS: dict = {k: _resolve(v)
                     for k, v in (_CFG.get("extra_roots") or {}).items()}

# Where all output goes (created if missing).
OUT_ROOT = _resolve(_CFG.get("out") or "feature_analysis/out/feature_analysis")

# Tool launchers / jars. On Windows the .bat next to a launcher is picked
# automatically by lib.common._resolve_tool_bin.
GUMTREE_BIN = _resolve(_CFG.get("gumtree_bin") or "tools/gumtree/bin/gumtree")
REFACTORINGMINER_BIN = _resolve(
    _CFG.get("refactoringminer_bin") or "tools/refactoringminer/bin/RefactoringMiner")
JAVAPARSER_JAR = _resolve(
    _CFG.get("javaparser_jar") or "tools/javaparser/javaparser-core-3.27.0.jar")

# JDK 21 home (RefactoringMiner 3.0.12 needs Java 21; GumTree needs 17).
# "" = JDK_21_HOME from .env at the repository root, else auto-detect the
# newest JDK >= 21, else `java` on PATH.
try:
    from dotenv import load_dotenv as _load_dotenv
    _load_dotenv(PROJECT_ROOT / ".env")
except ImportError:
    pass
JAVA_HOME = _resolve(_CFG.get("java_home") or "") if _CFG.get("java_home") else ""
_JDK_ENV_VARS = ("JDK_21_HOME", "STRUCTURAL_ANALYSIS_JAVA_HOME")

JOBS = int(_CFG.get("jobs") or 8)          # parallel GumTree + RefactoringMiner workers
TOOL_TIMEOUT_SECONDS = int(_CFG.get("tool_timeout_seconds") or 120)

# Tests to leave out, matched by suffix against the test path relative to
# DATA_ROOT, e.g. "commons-io/auto/testcases/FileAlterationObserver_ESTest/test04".
EXCLUDE: set[str] = set(_CFG.get("exclude") or [])

# Work list. When set, the (original, improved) pairs are read FROM this
# file instead of being discovered by scanning data_root — see
# pairs_from_manifest() for why that matters. Empty = scan, the old
# behaviour. Override per run with --manifest.
MANIFEST = _resolve(_CFG.get("manifest")) if _CFG.get("manifest") else ""
# ============================================================================

# DIRECTIONAL on purpose. The detector counts the blank-line-delimited groups
# in a @Test body; the old feature fired on ANY change, which merged "the model
# added structure" with "the model removed it". At case level that merge was
# harmless — over 16,410 cases the count increases 12,362 times and decreases
# TWICE — but at suite level, where the count is summed over the whole file so
# per-method gains and losses cancel, it was actively misleading: 53% of
# sonnet-4.6's EvoSuite suites are net DECREASES, which the old feature counted
# as the same event as an increase.
#
# Named for what is measured, not for what it is taken to mean: the metric is
# blank-line grouping, not readability. The link to readability belongs in the
# prose, with a citation, not in the feature name.
BLOCK_FEATURE = "Blank-Line Separation Added"
STAGES = ("diff", "comments", "blocks", "merge")

log = logging.getLogger("feature_analysis")


# ----------------------------------------------------------------------------
# discovery
# ----------------------------------------------------------------------------
def _excluded(rel: Path, exclude: set[str]) -> bool:
    rp = rel.as_posix()
    return any(rp == e or rp.endswith("/" + e) for e in exclude)


def discover_pairs(data_root: Path, exclude: set[str] = frozenset(),
                   filter_substr: str | None = None, limit: int | None = None) -> list[tuple[Path, Path, Path]]:
    """[(original_java, improved_java, test_dir)] for every test dir holding
    original/<Name>.java and a sibling <Name>.java."""
    pairs = []
    for orig_dir in sorted(data_root.rglob("original")):
        if not orig_dir.is_dir():
            continue
        test_dir = orig_dir.parent
        rel = test_dir.relative_to(data_root)
        if _excluded(rel, exclude):
            continue
        if filter_substr and filter_substr.replace("\\", "/") not in rel.as_posix():
            continue
        for original in sorted(orig_dir.glob("*.java")):
            improved = test_dir / original.name
            if improved.exists():
                pairs.append((original, improved, test_dir))
                break
    return pairs[:limit] if limit else pairs


def pairs_from_manifest(manifest: Path, data_root: Path,
                        exclude: set[str] = frozenset(),
                        filter_substr: str | None = None,
                        limit: int | None = None) -> list[tuple[Path, Path, Path]]:
    """The same [(original_java, improved_java, test_dir)] list, but taken from
    agent_improvement/data/improved/summary/feature_analysis_input.json instead of scanning.

    WHY: scanning finds EVERY pair on disk, including the improvements whose
    behaviour changed. Feature analysis on those is meaningless — a test whose
    coverage or killed mutants moved is not a rewrite of the same test — so the
    work list has to come from the exact-match verdict, which lives in that
    manifest. The manifest also carries `exact_in_all_models`, letting a
    cross-model comparison subset afterwards without re-running the tools.

    The manifest stores paths relative to the PROJECT ROOT (so the folder stays
    movable); `test_dir` is the improved file's parent, which keeps
    `test_dir.relative_to(data_root)` == <model>/<project>/<src>/<gran>/… and
    therefore leaves scope_of(), diff_dir() and the output layout untouched."""
    blob = json.loads(manifest.read_text(encoding="utf-8"))
    entries = blob.get("tests") if isinstance(blob, dict) else blob
    if not entries:
        raise SystemExit(f"manifest has no `tests` entries: {manifest}")

    project_root = HERE.parent
    pairs, missing, outside = [], [], []
    for e in entries:
        o, i = e.get("original"), e.get("improved")
        if not (o and i):
            continue
        op, ip = Path(o), Path(i)
        if not op.is_absolute():
            op = project_root / op
        if not ip.is_absolute():
            ip = project_root / ip
        test_dir = ip.parent
        try:
            rel = test_dir.resolve().relative_to(data_root)
        except ValueError:
            # A manifest entry pointing outside the configured data root is a
            # configuration mismatch, not a per-test problem — collect them all
            # and fail once with the count rather than silently skipping.
            outside.append(i)
            continue
        if _excluded(rel, exclude):
            continue
        if filter_substr and filter_substr.replace("\\", "/") not in rel.as_posix():
            continue
        if not (op.is_file() and ip.is_file()):
            missing.append(i)
            continue
        pairs.append((op, ip, test_dir))

    if outside:
        raise SystemExit(
            f"{len(outside)} manifest entr(ies) are not under the data root "
            f"{data_root} — first: {outside[0]}. Point --data-root at the tree "
            f"the manifest was built from.")
    if missing:
        log.warning(f"{len(missing)} manifest entr(ies) skipped, .java missing "
                    f"on disk (first: {missing[0]})")
    return pairs[:limit] if limit else pairs


def scope_of(rel: Path) -> dict[str, str]:
    """<model>/<project>/<src>/<gran>/... -> named parts ('?' when the path is shorter)."""
    parts = list(rel.parts) + ["?"] * 4
    return {"model": parts[0], "project": parts[1], "src": parts[2], "granularity": parts[3]}


# ----------------------------------------------------------------------------
# stage: diff  (GumTree + RefactoringMiner)
# ----------------------------------------------------------------------------
def tool_settings(java_home: str) -> dict[str, Any]:
    """The cfg lib/summary.py reads: {"structural_diff": {...}}."""
    return {"structural_diff": {
        "enabled": True,
        "gumtree_bin": GUMTREE_BIN,
        "refactoringminer_bin": REFACTORINGMINER_BIN,
        "gumtree_java_home": java_home,
        "refactoringminer_java_home": java_home,
        "timeout_seconds": TOOL_TIMEOUT_SECONDS,
    }}


def check_tools(stages: tuple[str, ...]) -> str:
    """Fail fast on a wrong path instead of silently writing SKIPPED summaries.
    Returns the JDK home to use ('' = java on PATH)."""
    missing = []
    if "diff" in stages:
        for name, p in (("GUMTREE_BIN", GUMTREE_BIN), ("REFACTORINGMINER_BIN", REFACTORINGMINER_BIN)):
            if not Path(_resolve_tool_bin(p)).exists():
                missing.append(f"{name} = {p}")
    if "blocks" in stages and not Path(JAVAPARSER_JAR).exists():
        missing.append(f"JAVAPARSER_JAR = {JAVAPARSER_JAR}")
    if "comments" in stages and not gumtree_jar().exists():
        missing.append(f"gumtree.jar (next to GUMTREE_BIN = {GUMTREE_BIN}) = {gumtree_jar()}")
    if missing:
        raise SystemExit("tool path(s) not found -- fix CONFIG in feature_analysis.py:\n  " + "\n  ".join(missing))
    java_home = JAVA_HOME or _detect_java_home(21, _JDK_ENV_VARS)
    if JAVA_HOME and not Path(JAVA_HOME).exists():
        raise SystemExit(f"JAVA_HOME = {JAVA_HOME} does not exist")
    log.info(f"JDK: {java_home or '(java on PATH)'}")
    return java_home


def _diff_one(job: tuple[str, str, str, dict]) -> tuple[str, str | None]:
    """Worker: one pair. Top-level so it pickles for ProcessPoolExecutor."""
    from lib import summary
    original, improved, out_dir, cfg = job
    try:
        meta = summary.write_structural_diff_outputs(original, improved, out_dir, cfg)
        bad = [t for t, m in (meta.get("tools") or {}).items() if m.get("status") != "OK"]
        return out_dir, (f"tool status not OK: {', '.join(bad)}" if bad else None)
    except Exception as e:  # noqa: BLE001 -- external tools are best-effort
        return out_dir, str(e)


def diff_dir(out_root: Path, rel: Path) -> Path:
    return out_root / "diff" / rel / "structural_diff"


def stage_diff(pairs, data_root: Path, out_root: Path, java_home: str, jobs: int, force: bool) -> None:
    from concurrent.futures import ProcessPoolExecutor, as_completed

    cfg = tool_settings(java_home)
    todo, skipped = [], 0
    for original, improved, test_dir in pairs:
        sd = diff_dir(out_root, test_dir.relative_to(data_root))
        if not force and (sd / "structural_diff_summary.json").exists():
            skipped += 1
            continue
        todo.append((str(original), str(improved), str(sd), cfg))
    log.info(f"[diff] {len(pairs)} pair(s): {len(todo)} to run, {skipped} already done, jobs={jobs}")

    done = failed = 0
    start = time.time()

    def report(out_dir: str, err: str | None) -> None:
        nonlocal done, failed
        if err is None:
            done += 1
        else:
            failed += 1
            log.warning(f"[diff] FAIL {Path(out_dir).parent}: {err}")
        n = done + failed
        if n % 100 == 0 or n == len(todo):
            rate = n / max(time.time() - start, 1e-6)
            log.info(f"[diff] {n}/{len(todo)}  failed={failed}  {rate:.2f}/s  "
                     f"eta {(len(todo) - n) / rate / 60 if rate else 0:.0f} min")

    if jobs <= 1:
        for job in todo:
            report(*_diff_one(job))
    else:
        with ProcessPoolExecutor(max_workers=jobs) as pool:
            for fut in as_completed([pool.submit(_diff_one, j) for j in todo]):
                report(*fut.result())
    log.info(f"[diff] done={done}, skipped(existing)={skipped}, failed={failed}")


# ----------------------------------------------------------------------------
# stage: comments  (GumTree java-jdtc diff -> comment-unit labels)
# ----------------------------------------------------------------------------
def comments_path(out_root: Path, rel: Path) -> Path:
    return out_root / "comments" / rel / "comment_labels.json"


def gumtree_jar() -> Path:
    """tools/gumtree/lib/gumtree.jar, next to the launcher GUMTREE_BIN names."""
    return Path(_resolve_tool_bin(GUMTREE_BIN)).resolve().parent.parent / "lib" / "gumtree.jar"


def stage_comments(pairs, data_root: Path, out_root: Path, java_home: str, jobs: int, force: bool) -> None:
    """Re-pair GumTree's comment edits at comment-unit level (lib/comment_postprocess.py).

    WHY a stage of its own rather than part of `diff`: it reads the diff stage's
    gumtree_comments_diff.json plus the full java-jdtc trees of both sides, so
    it can be re-run -- after a threshold change -- without re-running GumTree
    and RefactoringMiner on 17,190 pairs.

    The trees are parsed into a TEMPORARY directory and dropped afterwards:
    parsing all 34,380 files takes ~15 s, while keeping them costs ~570 MB."""
    import tempfile
    from concurrent.futures import ProcessPoolExecutor, as_completed

    from lib import comment_postprocess

    todo, skipped, no_diff = [], 0, 0
    for original, improved, test_dir in pairs:
        rel = test_dir.relative_to(data_root)
        out = comments_path(out_root, rel)
        if not force and out.exists():
            skipped += 1
            continue
        diff = diff_dir(out_root, rel) / "gumtree_comments_diff.json"
        if not diff.exists():
            no_diff += 1      # the merge stage reports these tests as incomplete
            continue
        todo.append((rel, original, improved, diff, out))
    log.info(f"[comments] {len(pairs)} pair(s): {len(todo)} to run, {skipped} already done, "
             f"{no_diff} without a GumTree diff, jobs={jobs}")
    if not todo:
        return

    start = time.time()
    with tempfile.TemporaryDirectory(prefix="comment_asts_") as tmp:
        ast = Path(tmp) / "ast"
        work = [(rel.as_posix(), str(o), str(i), str(d),
                 str(ast / rel / "o.json"), str(ast / rel / "i.json"), str(out))
                for rel, o, i, d, out in todo]
        failed = comment_postprocess.parse_asts(
            [(w[1], w[4]) for w in work] + [(w[2], w[5]) for w in work],
            java_home, gumtree_jar(), Path(tmp) / "parse", nproc=max(1, min(jobs, 8)))
        log.info(f"[comments] parsed {2 * len(work)} file(s) with java-jdtc in "
                 f"{time.time() - start:.0f}s ({failed} failed)")

        done = errors = 0
        if jobs <= 1:
            results = (comment_postprocess.label_one(w) for w in work)
            for out, err in results:
                done += 1
                if err:
                    errors += 1
                    log.warning(f"[comments] ERROR {Path(out).parent}: {err}")
        else:
            with ProcessPoolExecutor(max_workers=jobs) as pool:
                for fut in as_completed([pool.submit(comment_postprocess.label_one, w) for w in work]):
                    out, err = fut.result()
                    done += 1
                    if err:
                        errors += 1
                        log.warning(f"[comments] ERROR {Path(out).parent}: {err}")
    log.info(f"[comments] labelled {done} test(s) in {time.time() - start:.0f}s, errors={errors}")


# ----------------------------------------------------------------------------
# stage: blocks  (JavaParser)
# ----------------------------------------------------------------------------
def stage_blocks(pairs, data_root: Path, out_root: Path, java_home: str) -> None:
    log.info(f"[blocks] {len(pairs)} pair(s), jar={JAVAPARSER_JAR}")
    summary = javaparser_blocks.run_blocks(pairs, data_root, out_root / "blocks", Path(JAVAPARSER_JAR), java_home)
    log.info(f"[blocks] {summary['totals']}")


# ----------------------------------------------------------------------------
# stage: merge  (three tools -> one view per test + frequency tables)
# ----------------------------------------------------------------------------
def _load(path: Path) -> dict[str, Any]:
    try:
        return json.loads(path.read_text(encoding="utf-8"))
    except Exception:
        return {}


def block_parse_error(block: dict[str, Any]) -> str | None:
    for side in ("original", "improved"):
        err = (block.get(side) or {}).get("parse_error")
        if err:
            return f"{side}: {err}"
    return None


def block_entries(block: dict[str, Any]) -> list[dict[str, Any]]:
    """One entry when the @Test-body was split into MORE blank-line-delimited
    groups than before. A side JavaParser could not parse has 0 blocks by
    construction, so no entry then. Decreases produce no entry — see
    BLOCK_FEATURE for why the feature is directional."""
    if not block or block_parse_error(block):
        return []
    delta = int(block.get("delta", {}).get("total_blocks", 0) or 0)
    if delta <= 0:
        return []
    o = int(block.get("original", {}).get("total_blocks", 0) or 0)
    i = int(block.get("improved", {}).get("total_blocks", 0) or 0)
    return [{
        "type": BLOCK_FEATURE,
        "description": f"test-method body split into more blocks: {o} -> {i} ({delta:+d})",
        "location": {"source": "javaparser", "side": "improved"},
        "original_blocks": o,
        "improved_blocks": i,
        "delta_blocks": delta,
    }]


def merge_one(rel: Path, out_root: Path) -> dict[str, Any]:
    sd = diff_dir(out_root, rel)
    summary = _load(sd / "structural_diff_summary.json")
    meta = _load(sd / "structural_diff_metadata.json")
    block = _load(out_root / "blocks" / rel / "test_blocks_javaparser.json")
    comments = _load(comments_path(out_root, rel))

    tool_status = {
        "gumtree": (meta.get("tools") or {}).get("gumtree", {}).get("status", "MISSING"),
        "refactoringminer": (meta.get("tools") or {}).get("refactoringminer", {}).get("status", "MISSING"),
        "javaparser": "OK" if block and not block_parse_error(block) else
                      ("PARSE_ERROR" if block else "MISSING"),
        # The comment features come from the comments stage, so a test it did
        # not label has NO comment result -- not "no comment change".
        "comments": comments.get("status", "MISSING") if comments else "MISSING",
    }
    by_tool: dict[str, list[dict[str, Any]]] = {"gumtree": [], "refactoringminer": [], "javaparser": []}
    for e in summary.get("summary", []) or []:
        src = str((e.get("location") or {}).get("source", ""))
        if src in by_tool:
            by_tool[src].append(e)
    # CASE LEVEL ONLY. The detector sums block counts over every @Test method in
    # the file, so at case level (one file = one test method) the value IS that
    # method's block count, while at suite level (~20 methods) per-method gains
    # and losses cancel: a suite where one method gained two groups and another
    # lost two records no change at all. That makes the suite-level number a
    # DIFFERENT quantity from the case-level one rather than a coarser version
    # of it, so it is not produced at suite level at all.
    #
    # `tool_status["javaparser"]` above is deliberately left untouched: the
    # parse still ran and still succeeded (780/780 suites), and folding this
    # into `complete` would silently change which tests enter every frequency
    # table.
    by_tool["javaparser"] = (block_entries(block)
                             if scope_of(rel)["granularity"] == "testcases"
                             else [])

    # Comments: the post-processed unit labels REPLACE GumTree's raw edit verbs.
    # Two features per kind -- "<Kind> Added/Updated" and "<Kind> Deleted" (see
    # lib/comment_units.comment_entries). The raw verbs split one rewritten
    # comment into update + delete + insert, never report a Javadoc edit, and
    # miss comments inside removed code; they stay available, unused, in
    # diff/<rel>/structural_diff/gumtree_summary.json.
    by_tool["gumtree"] = (comment_units.comment_entries(comments)
                          if tool_status["comments"] == "OK" else [])

    integrated = sort_entries(by_tool["gumtree"] + by_tool["refactoringminer"] + by_tool["javaparser"])
    counts = Counter(canonical_feature(e.get("type")) for e in integrated)
    record = {
        "relative_test": rel.as_posix(),
        **scope_of(rel),
        "tool_status": tool_status,
        "complete": all(s == "OK" for s in tool_status.values()),
        "features_present": sort_features(counts),
        "counts_by_type": dict(ordered_feature_items(dict(counts))),
        "gumtree_entries": by_tool["gumtree"],
        "refactoringminer_entries": by_tool["refactoringminer"],
        "javaparser_entries": by_tool["javaparser"],
        "integrated_summary": integrated,
    }
    if block:
        record["javaparser_block_counts"] = {
            "original_blocks": block.get("original", {}).get("total_blocks", 0),
            "improved_blocks": block.get("improved", {}).get("total_blocks", 0),
            "delta_blocks": block.get("delta", {}).get("total_blocks", 0),
            "original_test_methods": block.get("original", {}).get("test_method_count", 0),
            "improved_test_methods": block.get("improved", {}).get("test_method_count", 0),
            "parse_error": block_parse_error(block),
        }
    return record


def frequency_rows(records: list[dict[str, Any]]) -> list[list[Any]]:
    """Share of tests with each feature, per (model, src, granularity) and per
    (model, src, ALL). Presence, not count: a test with 4 renames counts once."""
    groups: dict[tuple[str, str, str], list[dict]] = defaultdict(list)
    for r in records:
        groups[(r["model"], r["src"], r["granularity"])].append(r)
        groups[(r["model"], r["src"], "ALL")].append(r)
    rows = []
    for (model, src, gran), rs in sorted(groups.items()):
        feats = Counter(f for r in rs for f in r["features_present"])
        for f, n in ordered_feature_items(dict(feats)).items():
            rows.append([model, src, gran, f, n, len(rs), round(n / len(rs), 4)])
    return rows


def write_csv(path: Path, header: list[str], rows: list[list[Any]]) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    with path.open("w", newline="", encoding="utf-8") as fh:
        w = csv.writer(fh)
        w.writerow(header)
        w.writerows(rows)


def stage_merge(pairs, data_root: Path, out_root: Path) -> dict[str, Any]:
    records, incomplete = [], []
    for _original, _improved, test_dir in pairs:
        rel = test_dir.relative_to(data_root)
        rec = merge_one(rel, out_root)
        (out_root / "per_test").mkdir(parents=True, exist_ok=True)
        (out_root / "per_test" / (rel.as_posix().replace("/", "__") + ".json")).write_text(
            json.dumps(rec, indent=2, ensure_ascii=False), encoding="utf-8")
        records.append(rec)
        if not rec["complete"]:
            incomplete.append({"test": rec["relative_test"], "tool_status": rec["tool_status"]})

    # Frequencies use complete tests only: a failed tool would silently read as "no change".
    complete = [r for r in records if r["complete"]]
    freq = frequency_rows(complete)
    write_csv(out_root / "feature_frequency.csv",
              ["model", "src", "granularity", "feature", "tests_with_feature", "tests_total", "rate"], freq)
    write_csv(out_root / "feature_by_test.csv",
              ["relative_test", "model", "project", "src", "granularity", "complete",
               "n_features", "features_present", "original_blocks", "improved_blocks", "delta_blocks"],
              [[r["relative_test"], r["model"], r["project"], r["src"], r["granularity"], r["complete"],
                len(r["features_present"]), "; ".join(r["features_present"]),
                *[(r.get("javaparser_block_counts") or {}).get(k, "")
                  for k in ("original_blocks", "improved_blocks", "delta_blocks")]]
               for r in records])

    tool_tests = Counter()
    for r in complete:
        for t in ("gumtree", "refactoringminer", "javaparser"):
            if r[f"{t}_entries"]:
                tool_tests[t] += 1
    report = {
        "generated_at": datetime.now().isoformat(timespec="seconds"),
        "data_root": str(data_root),
        "tool_boundary": {
            "gumtree": "comment changes only, post-processed at comment-unit level: "
                       "Line Comment / Javadoc / Block Comment, each Added/Updated or Deleted",
            "refactoringminer": "semantic refactorings",
            "javaparser": f"'{BLOCK_FEATURE}' when the @Test-body block count INCREASES",
        },
        "tests_total": len(records),
        "tests_complete": len(complete),
        "tests_incomplete": incomplete,
        "tests_with_change_per_tool": dict(tool_tests),
        "feature_test_counts": dict(ordered_feature_items(dict(
            Counter(f for r in complete for f in r["features_present"])))),
        "frequency": [dict(zip(["model", "src", "granularity", "feature", "tests_with_feature",
                                "tests_total", "rate"], row)) for row in freq],
    }
    (out_root / "feature_summary.json").write_text(json.dumps(report, indent=2, ensure_ascii=False),
                                                   encoding="utf-8")
    (out_root / "feature_analysis.md").write_text(render_markdown(report, freq), encoding="utf-8")
    log.info(f"[merge] {len(records)} tests ({len(complete)} complete) -> {out_root}")
    if incomplete:
        log.warning(f"[merge] {len(incomplete)} test(s) left out of frequencies (a tool failed); "
                    "see tests_incomplete in feature_summary.json")
    return report


def render_markdown(report: dict[str, Any], freq: list[list[Any]]) -> str:
    L = ["# Feature change analysis", "",
         f"Generated `{report['generated_at']}`  ",
         f"Data root: `{report['data_root']}`", "",
         "| Tool | Owns |", "|---|---|"]
    L += [f"| {t} | {d} |" for t, d in report["tool_boundary"].items()]
    L += ["", f"Tests: **{report['tests_total']}**, complete (all three tools OK): "
              f"**{report['tests_complete']}**", "",
          "## Feature frequency (share of tests with the feature)", "",
          "| Model | Src | Granularity | Feature | Tests | Total | Rate |",
          "|---|---|---|---|---:|---:|---:|"]
    L += [f"| {m} | {s} | {g} | {f} | {n} | {t} | {r:.2f} |" for m, s, g, f, n, t, r in freq]
    if report["tests_incomplete"]:
        L += ["", "## Incomplete tests (left out of the frequencies)", ""]
        L += [f"- `{x['test']}`: {x['tool_status']}" for x in report["tests_incomplete"][:50]]
    return "\n".join(L) + "\n"


# ----------------------------------------------------------------------------
# public entry point (stability_check.py calls this for every run)
# ----------------------------------------------------------------------------
def run(data_root: str | Path, out_root: str | Path, stages: tuple[str, ...] = STAGES,
        jobs: int = JOBS, force: bool = False, filter_substr: str | None = None,
        limit: int | None = None, exclude: set[str] | None = None,
        manifest: str | Path | None = None) -> dict[str, Any] | None:
    data_root, out_root = Path(data_root).resolve(), Path(out_root).resolve()
    if not data_root.is_dir():
        raise SystemExit(f"data root does not exist: {data_root}")
    out_root.mkdir(parents=True, exist_ok=True)
    log.info(f"data root: {data_root}")
    log.info(f"out root:  {out_root}")
    java_home = check_tools(stages)

    excl = EXCLUDE if exclude is None else exclude
    if manifest:
        manifest = Path(manifest)
        if not manifest.is_file():
            raise SystemExit(f"manifest not found: {manifest}")
        log.info(f"work list: {manifest}")
        pairs = pairs_from_manifest(manifest, data_root, excl, filter_substr, limit)
        log.info(f"{len(pairs)} test pair(s) from manifest")
        if not pairs:
            raise SystemExit("manifest yielded no usable pairs")
    else:
        log.info("work list: scanning data root (no manifest configured)")
        pairs = discover_pairs(data_root, excl, filter_substr, limit)
        log.info(f"{len(pairs)} test pair(s) found")
        if not pairs:
            raise SystemExit("no (original/<Name>.java, <Name>.java) pairs found under the data root")

    if "diff" in stages:
        stage_diff(pairs, data_root, out_root, java_home, jobs, force)
    if "comments" in stages:
        stage_comments(pairs, data_root, out_root, java_home, jobs, force)
    if "blocks" in stages:
        stage_blocks(pairs, data_root, out_root, java_home)
    if "merge" in stages:
        return stage_merge(pairs, data_root, out_root)
    return None


def main() -> None:
    ap = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    ap.add_argument("--stage", choices=[*STAGES, "all"], default="all")
    ap.add_argument("--data-root", default=DATA_ROOT)
    ap.add_argument("--out", default=OUT_ROOT)
    ap.add_argument("--jobs", type=int, default=JOBS)
    ap.add_argument("--force", action="store_true", help="re-run GumTree/RefactoringMiner even if output exists")
    ap.add_argument("--filter", default=None, help="only tests whose path contains this substring")
    ap.add_argument("--limit", type=int, default=None, help="only the first N tests")
    ap.add_argument("--manifest", default=MANIFEST,
                    help="read the (original, improved) work list from this "
                         "JSON (data/improved/summary/feature_analysis_input.json) "
                         "instead of scanning the data root")
    args = ap.parse_args()
    logging.basicConfig(level=logging.INFO, format="%(message)s")

    stages = STAGES if args.stage == "all" else (args.stage,)
    run(args.data_root, args.out, stages, args.jobs, args.force, args.filter,
        args.limit, manifest=args.manifest)


if __name__ == "__main__":
    main()
