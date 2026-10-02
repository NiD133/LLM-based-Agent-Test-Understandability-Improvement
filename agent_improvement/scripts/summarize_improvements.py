#!/usr/bin/env python3
"""
summarize_improvements.py — Step 5b. Turn data/improved/ into four files.

    python scripts/summarize_improvements.py                    # config.yaml
    python scripts/summarize_improvements.py --config other.yaml
    python scripts/summarize_improvements.py --improved-root <dir>

Also run by scripts/main.py when pipeline_control.generate_summary is true.
Reads only state.json + metrics.json + the two .java files per test. Launches
no JVM, re-measures nothing, and writes only into data/improved/summary/ —
safe to re-run at any time. ~50 s for three models × 6040 tests.

OUTPUT — data/improved/summary/

  1. summary.md                   FOR THE PAPER
        One table and nothing else: per model, the accepted (exact-match)
        suites and cases for developer-written and EvoSuite tests, plus the
        totals — the paper's "accepted tests" table.

  2. feature_analysis_input.json  FOR feature_analysis (RQ1)
        One entry per (model, test) whose improvement IS exact match, with the
        relative paths of the original and the improved .java. This is the work
        list feature_analysis consumes to run RefactoringMiner + GumTree +
        JavaParser. Paths are relative to the project root so the whole folder
        stays movable.

  3. per_test.csv                 FOR STATS SOFTWARE and human_evaluation/select_human_eval.py
        One row per (model, test), exact or not: verdict + sub-flags, coverage,
        mutation score, LOC, oracle counts, tokens, both compile statuses.

  4. suite_cases.json             FOR feature_analysis/plot_heatmaps.py (RQ3)
        One entry per (model, project, src, suite) — a "suite unit": the
        suite-level improvement plus the case-level improvements of the same
        suite. Records whether the suite is exact, how many of its cases are,
        and which ones are not.

HOW A SUITE UNIT IS ADMITTED TO RQ3
    RQ3 pairs the two granularities on the same suite, so a unit is only
    usable when both sides are trustworthy: the suite-level improvement must
    be exact AND at least CASE_EXACT_THRESHOLD of that suite's case-level
    improvements must be exact (0.90 by default, --case-exact-threshold to
    change). Non-exact cases are never analysed, so a unit admitted below 1.0
    contributes a case side built from its exact cases only. Every unit also
    carries its raw counts, its `case_exact_fraction` and the strict
    `both_exact` flag, so a different threshold needs no re-run.

WHY feature_analysis_input.json KEEPS EACH MODEL'S OWN EXACT SET
    The three runs do not agree on which tests came out exact (5810 / 5772 /
    5608 here, intersecting at 5485). Analysing each model on its own set makes
    cross-model feature frequencies compare different test populations. Rather
    than pick one, every entry carries `exact_in_all_models`: run the tools
    over each model's own exact set once, then subset to the intersection at
    aggregation time for any cross-model claim.
"""

from __future__ import annotations

import argparse
import json
import logging
import sys
from collections import defaultdict
from pathlib import Path
from typing import Optional

PROJECT_ROOT = Path(__file__).resolve().parent.parent
sys.path.insert(0, str(Path(__file__).resolve().parent))

log = logging.getLogger("summarize_improvements")


# ── RQ3 admission threshold ──────────────────────────────────────────────────
# A suite unit joins the suite-vs-case analysis when the suite-level
# improvement is exact AND at least this fraction of that suite's case-level
# improvements are exact. 1.0 is the strict reading (every case); 0.90 admits a
# suite that lost up to a tenth of its cases.
#
# Override per run with --case-exact-threshold; the value used is recorded in
# suite_cases.json's _meta and printed in summary.md, so no output is ever
# ambiguous about which criterion produced it.
#
# NOTE on what a threshold below 1.0 means for the comparison: the suite side
# always covers 100% of the suite's tests (it is one file), while the case side
# then covers only `case_exact_fraction` of them. The two sides are no longer
# over the same set of tests. `case_exact_fraction` is kept on every unit so
# that imbalance stays visible and can be controlled for.
CASE_EXACT_THRESHOLD = 0.90


# ── helpers ──────────────────────────────────────────────────────────────────

def _usage(state: dict) -> dict:
    """Token usage out of state.json's runtime_observability block. Codex
    writes no such block, so its tests come back all-zero — see `recorded`."""
    u = (((state.get("runtime_observability") or {}).get("token_usage") or {})
         .get("usage") or {})
    inp = int(u.get("input_tokens") or 0)
    out = int(u.get("output_tokens") or 0)
    cc = int(u.get("cache_creation_input_tokens") or 0)
    cr = int(u.get("cache_read_input_tokens") or 0)
    return {"input": inp, "output": out, "cache_creation": cc,
            "cache_read": cr, "total": inp + out + cc + cr}


def _sloc(text: str) -> int:
    """Source lines of code: non-blank lines after stripping `//` and `/* */`
    comments, with string/char literals preserved so a `//` inside a string is
    not mistaken for a comment.

    Character-for-character the same routine as main._sloc. These LOC values
    have to agree with the ones the pipeline writes elsewhere (and with
    human_eval's LOC buckets), so this must not drift into a simpler variant."""
    res: list[str] = []
    i, n, state = 0, len(text), "code"
    while i < n:
        c = text[i]; nxt = text[i + 1] if i + 1 < n else ""
        if state == "code":
            if c == '"': state = "str"; res.append(c)
            elif c == "'": state = "char"; res.append(c)
            elif c == "/" and nxt == "/": state = "line"
            elif c == "/" and nxt == "*": state = "block"; i += 2; continue
            else: res.append(c)
        elif state == "str":
            res.append(c)
            if c == "\\" and i + 1 < n: res.append(text[i + 1]); i += 2; continue
            if c == '"': state = "code"
        elif state == "char":
            res.append(c)
            if c == "\\" and i + 1 < n: res.append(text[i + 1]); i += 2; continue
            if c == "'": state = "code"
        elif state == "line":
            if c == "\n": state = "code"; res.append(c)
        elif state == "block":
            if c == "*" and nxt == "/": state = "code"; i += 2; continue
            if c == "\n": res.append(c)
        i += 1
    return sum(1 for ln in "".join(res).splitlines() if ln.strip())


def _n_oracles(text: str) -> Optional[int]:
    """How many oracle statements the test contains, via the same parser the
    masking task uses, so the counts agree with the downstream oracle work."""
    try:
        import mask_oracles
        return mask_oracles.mask_oracles(text)[1]
    except Exception:
        return None


def _class_path_map(data_root: Path) -> dict:
    """{class_fqn: class_path} from data/dataset.json, so each record can carry
    the source path the old exact_match.json had. Empty when the manifest is
    absent — the field simply comes back None."""
    manifest = data_root / "dataset.json"
    if not manifest.exists():
        return {}
    try:
        subjects = json.loads(manifest.read_text(encoding="utf-8"))
    except Exception:
        return {}
    out = {}
    for s in subjects:
        for cp in (s.get("class_paths") or []):
            p = str(cp)
            for prefix in ("src/main/java/", "src/java/"):
                if prefix in p:
                    fqn = p.split(prefix, 1)[1]
                    break
            else:
                fqn = p
            out[fqn[:-5].replace("/", ".") if fqn.endswith(".java") else fqn] = p
    return out


# ── one test ─────────────────────────────────────────────────────────────────

def _rel(p: Path) -> str:
    """A project-root-relative path string, whichever way the root was passed.

    The manifest must never carry absolute paths: the whole folder gets moved
    (it already has been once), and 297 hardcoded absolute paths in a sibling
    dataset had to be rewritten afterwards. Falls back to the absolute form
    only when the file genuinely lives outside the project."""
    try:
        # relative to the REPOSITORY root (one level above agent_improvement/), so
        # feature_analysis/ and human_evaluation/ can resolve the paths as well
        return str(p.resolve().relative_to(PROJECT_ROOT.parent))
    except ValueError:
        return str(p.resolve())


def _read_test(leaf: Path, model: str, root: Path, cp_map: dict) -> Optional[dict]:
    """One record for one test folder, or None when the path is not a leaf."""
    rel = leaf.relative_to(root)                    # <model>/<proj>/<src>/<gran>/…
    parts = rel.parts
    if len(parts) < 5:
        return None
    project, src, gran, suite = parts[1], parts[2], parts[3], parts[4]
    case = parts[5] if len(parts) > 5 else ""

    try:
        state = json.loads((leaf / "state.json").read_text(encoding="utf-8"))
    except Exception:
        state = {}
    status = state.get("status", "STATE_UNREADABLE") if state else "STATE_UNREADABLE"

    test_key = "/".join([project, src, gran, suite] + ([case] if case else []))
    rec = {
        "model": model,
        # `relative_test` is model-qualified and named EXACTLY like the column
        # feature_analysis writes in feature_by_test.csv, so the two CSVs join
        # on one column with no key munging. `test_key` is the model-free form,
        # used to intersect the models' target sets.
        "relative_test": f"{model}/{test_key}",
        "test_key": test_key,
        "project": project, "src": src, "gran": gran,
        "suite": suite, "case": case,
        "test_id": f"{suite}_{case}" if case else suite,
        "class_fqn": None, "class_path": None,
        # TWO compile statuses, deliberately kept apart: the agent session's
        # outcome and the measurement's outcome are separate events and they
        # disagree on ~186 opus tests. See `status_disagreement`.
        "state_status": status,            # what the AGENT SESSION ended as
        "compile_status": None,            # what the MEASUREMENT recorded
        "coverage_available": False,
        "mutation_available": False,
        "is_exact_match": None,            # None = no verdict at all
        "is_lines_equal": None,
        "is_branches_equal": None,
        "is_mut_superset": None,
        "line_pct": None, "branch_pct": None, "mutation_score_pct": None,
        "mutants_killed_original": None, "mutants_killed_improved": None,
        "loc_original": None, "loc_improved": None,
        "n_oracles_original": None, "n_oracles_improved": None,
        "attempts_used": state.get("attempts_used"),
        # Relative so the whole folder stays movable.
        "original_path": None, "improved_path": None,
    }
    rec.update({f"tok_{k}": v for k, v in _usage(state).items()})

    mp = leaf / "metrics.json"
    if mp.exists():
        try:
            blob = json.loads(mp.read_text(encoding="utf-8"))
        except Exception:
            blob = {}
        rec["compile_status"] = blob.get("compile_status")
        imp = blob.get("improved") or {}
        base = blob.get("baseline") or {}
        rec["class_fqn"] = imp.get("target_class")
        rec["class_path"] = cp_map.get(rec["class_fqn"])
        rec["coverage_available"] = bool(imp.get("coverage_available"))
        rec["mutation_available"] = imp.get("mutants_killed") is not None
        for k in ("line_pct", "branch_pct", "mutation_score_pct"):
            rec[k] = imp.get(k)
        rec["mutants_killed_improved"] = imp.get("mutants_killed")
        rec["mutants_killed_original"] = base.get("mutants_killed")

        comparison = blob.get("comparison") or {}
        verdict = comparison.get("verdict") or {}
        if verdict:
            rec["is_exact_match"] = bool(verdict.get("is_exact_match"))
            rec["is_mut_superset"] = verdict.get("is_superset_or_equal")
            # `verdict` carries only those two booleans. The per-dimension
            # breakdown is re-derived from the diff blocks, which is exactly
            # what main._is_exact_match compares:
            #   lines    — the two covered-line SETS are equal
            #   branches — branches_by_line_diff lists ONLY differing lines,
            #              so an empty list means identical branch coverage
            #   mutants  — nothing the baseline killed went missing
            cl = comparison.get("covered_lines_diff") or {}
            rec["is_lines_equal"] = not (cl.get("only_in_improved")
                                         or cl.get("only_in_baseline"))
            rec["is_branches_equal"] = not (comparison.get("branches_by_line_diff") or [])
            km = comparison.get("killed_mutants_diff") or {}
            if rec["is_mut_superset"] is None:
                rec["is_mut_superset"] = not km.get("only_in_baseline")

    # The improved .java sits directly in the leaf; the original carries the
    # SAME file name under original/. Both are needed by feature_analysis, so
    # record their paths as well as their LOC and oracle counts.
    orig_dir = leaf / "original"
    originals = sorted(orig_dir.glob("*.java")) if orig_dir.is_dir() else []
    if originals:
        o = originals[0]
        try:
            otxt = o.read_text(encoding="utf-8", errors="replace")
            rec["loc_original"] = _sloc(otxt)
            rec["n_oracles_original"] = _n_oracles(otxt)
            rec["original_path"] = _rel(o)
        except Exception:
            pass
        improved = leaf / o.name
        if improved.is_file():
            try:
                itxt = improved.read_text(encoding="utf-8", errors="replace")
                rec["loc_improved"] = _sloc(itxt)
                rec["n_oracles_improved"] = _n_oracles(itxt)
                rec["improved_path"] = _rel(improved)
            except Exception:
                pass
    return rec


def collect(improved_root: Path) -> list[dict]:
    """Every test under every model folder of `improved_root`."""
    records: list[dict] = []
    if not improved_root.exists():
        log.warning("[summary] %s does not exist — nothing to do", improved_root)
        return records
    cp_map = _class_path_map(improved_root.parent)
    for model_dir in sorted(p for p in improved_root.iterdir()
                            if p.is_dir() and p.name != "summary"):
        model = model_dir.name
        leaves = (sorted(model_dir.glob("*/*/testsuites/*/state.json"))
                  + sorted(model_dir.glob("*/*/testcases/*/*/state.json")))
        for sp in leaves:
            rec = _read_test(sp.parent, model, improved_root, cp_map)
            if rec:
                records.append(rec)
        log.info("[summary] %-14s %5d test(s)", model, len(leaves))
    return records


# ── suite units (RQ3) ────────────────────────────────────────────────────────

def build_suite_units(records: list[dict],
                      threshold: float = CASE_EXACT_THRESHOLD) -> dict:
    """{(model, project, src, suite): unit} — the suite-level improvement of a
    suite together with the case-level improvements of that same suite.

    RQ3 compares the two granularities on the same suite, so this is the
    natural unit of analysis. Every unit carries its raw counts and its
    `case_exact_fraction`, so a different threshold needs no re-run."""
    units: dict = {}
    for r in records:
        key = (r["model"], r["project"], r["src"], r["suite"])
        u = units.setdefault(key, {
            "model": r["model"], "project": r["project"], "src": r["src"],
            "suite": r["suite"], "class_fqn": None,
            "suite_exact": None,          # None = no suite-level test at all
            "n_cases": 0, "n_cases_exact": 0,
            "case_exact_fraction": None,
            "non_exact_cases": [],
        })
        if r["class_fqn"] and not u["class_fqn"]:
            u["class_fqn"] = r["class_fqn"]
        if r["gran"] == "testsuites":
            u["suite_exact"] = (r["is_exact_match"] is True)
        else:
            u["n_cases"] += 1
            if r["is_exact_match"] is True:
                u["n_cases_exact"] += 1
            else:
                u["non_exact_cases"].append(r["case"])
    for u in units.values():
        u["non_exact_cases"].sort()
        if u["n_cases"]:
            u["case_exact_fraction"] = round(u["n_cases_exact"] / u["n_cases"], 4)
        # `both_exact` is the STRICT reading (every case exact) and is kept
        # regardless of the threshold, so the strict subset is always available
        # without a re-run. `passes` is the admission decision actually in use.
        u["both_exact"] = bool(u["suite_exact"]) and u["n_cases"] > 0 \
            and u["n_cases_exact"] == u["n_cases"]
        u["passes"] = bool(u["suite_exact"]) and u["n_cases"] > 0 \
            and u["case_exact_fraction"] >= threshold
    return units


def classify_units(units: dict) -> dict:
    """model → the three buckets the summary reports:
         1. passing            suite exact AND case_exact_fraction >= threshold
         2. suite_ok_below     suite exact BUT the fraction falls short
         3. suite_not_exact    no suite side to compare (count only)"""
    out: dict = {}
    for (model, _p, _s, _q), u in units.items():
        b = out.setdefault(model, {"passing": [], "suite_ok_below": [],
                                   "suite_not_exact": []})
        if not u["suite_exact"]:
            b["suite_not_exact"].append(u)
        elif u["passes"]:
            b["passing"].append(u)
        else:
            b["suite_ok_below"].append(u)
    return out


# ── aggregation ──────────────────────────────────────────────────────────────

def _empty_cell() -> dict:
    return {
        "total": 0,
        "state_status": defaultdict(int),
        "compile_status": defaultdict(int),
        "state_success": 0,
        "compile_success": 0,
        "status_disagreement": 0,
        "coverage_available": 0,
        "mutation_available": 0,
        "exact_match": 0,
        "not_exact": 0,
        "no_verdict": 0,
        # Among NOT-exact tests, which sub-check failed. A test can fail more
        # than one, so these do NOT sum to `not_exact`.
        "fail_lines": 0, "fail_branches": 0, "fail_mutants": 0,
        "tokens": {"n": 0, "recorded": 0, "input": 0, "output": 0,
                   "cache_creation": 0, "cache_read": 0, "total": 0},
    }


def _add(cell: dict, r: dict) -> None:
    cell["total"] += 1
    cell["state_status"][r["state_status"]] += 1
    cell["compile_status"][r["compile_status"] or "NO_METRICS"] += 1
    state_ok = r["state_status"] == "COMPILE_SUCCESS"
    metrics_ok = r["compile_status"] == "COMPILE_SUCCESS"
    cell["state_success"] += state_ok
    cell["compile_success"] += metrics_ok
    cell["status_disagreement"] += (state_ok != metrics_ok)
    cell["coverage_available"] += bool(r["coverage_available"])
    cell["mutation_available"] += bool(r["mutation_available"])
    if r["is_exact_match"] is True:
        cell["exact_match"] += 1
    elif r["is_exact_match"] is False:
        cell["not_exact"] += 1
        if r["is_lines_equal"] is False:
            cell["fail_lines"] += 1
        if r["is_branches_equal"] is False:
            cell["fail_branches"] += 1
        if r["is_mut_superset"] is False:
            cell["fail_mutants"] += 1
    else:
        cell["no_verdict"] += 1
    t = cell["tokens"]
    t["n"] += 1
    # Codex state.json has no runtime_observability block, so its usage is
    # ABSENT rather than zero. Count how many tests actually carry usage.
    if int(r.get("tok_total") or 0) > 0:
        t["recorded"] += 1
    for k in ("input", "output", "cache_creation", "cache_read", "total"):
        t[k] += int(r.get(f"tok_{k}") or 0)


def aggregate(records: list[dict], keys: Optional[set] = None) -> dict:
    """model → {"ALL": cell, "<gran>": cell, "<src>/<gran>": cell}. `keys`
    restricts the input to a set of test_key values (the aligned view)."""
    out: dict = {}
    for r in records:
        if keys is not None and r["test_key"] not in keys:
            continue
        m = out.setdefault(r["model"], {"ALL": _empty_cell()})
        _add(m["ALL"], r)
        _add(m.setdefault(r["gran"], _empty_cell()), r)
        _add(m.setdefault(f"{r['src']}/{r['gran']}", _empty_cell()), r)
    return out


# ── rendering ────────────────────────────────────────────────────────────────

def _pct(n: int, d: int) -> str:
    return f"{100.0 * n / d:.1f}%" if d else "—"


MODEL_NAMES = {"opus-4.8": "Claude Opus 4.8", "sonnet-4.6": "Claude Sonnet 4.6",
               "gpt-5.5": "GPT-5.5"}
MODEL_ORDER = ["opus-4.8", "sonnet-4.6", "gpt-5.5"]


def render_md(agg: dict) -> str:
    """ONE table, nothing else: accepted (exact-match) tests per model, split by
    test origin (manual = developer-written, auto = EvoSuite) and granularity
    (suites / cases), plus the totals. This is the paper's "accepted tests"
    table."""
    models = sorted(agg, key=lambda m: (MODEL_ORDER.index(m) if m in MODEL_ORDER else 99, m))

    def cell(c: dict) -> tuple[int, int]:
        return c["exact_match"], c["exact_match"] + c["not_exact"]

    def fmt(n: int, d: int) -> str:
        return f"{n:,} ({_pct(n, d)})" if d else "—"

    cols = [("manual", "testsuites"), ("manual", "testcases"),
            ("auto", "testsuites"), ("auto", "testcases"),
            (None, "testsuites"), (None, "testcases"), (None, None)]
    # denominators from the first model (every model covers the same 6,040 tests)
    first = agg[models[0]]
    def denom(src, gran):
        key = f"{src}/{gran}" if src else (gran or "ALL")
        return cell(first.get(key, _empty_cell()))[1]
    names = ["Manual Suites", "Manual Cases", "Auto Suites", "Auto Cases",
             "Suites", "Cases", "Total"]
    hdr = "| Model | " + " | ".join(f"{n} (/{denom(s, g):,})"
                                   for n, (s, g) in zip(names, cols)) + " |"
    L = ["# Accepted improvements: tests that preserve behaviour (exact match) "
         "per model and test origin", "",
         hdr, "|---|--:|--:|--:|--:|--:|--:|--:|"]
    for m in models:
        row = [MODEL_NAMES.get(m, m)]
        for src, gran in cols:
            key = f"{src}/{gran}" if src else (gran or "ALL")
            n, d = cell(agg[m].get(key, _empty_cell()))
            row.append(fmt(n, d))
        L.append("| " + " | ".join(row) + " |")
    return "\n".join(L) + "\n"


# ── outputs ──────────────────────────────────────────────────────────────────


def run(improved_root: Path,
        threshold: float = CASE_EXACT_THRESHOLD) -> Optional[Path]:
    """Collect, aggregate and write the four files into <improved_root>/summary/.

    `threshold` is the RQ3 admission rule — see CASE_EXACT_THRESHOLD."""
    records = collect(improved_root)
    if not records:
        return None

    models = sorted({r["model"] for r in records})
    exact_keys = {m: {r["test_key"] for r in records
                      if r["model"] == m and r["is_exact_match"] is True}
                  for m in models}
    exact_in_all = set.intersection(*exact_keys.values()) if exact_keys else set()

    units = build_suite_units(records, threshold)
    buckets = classify_units(units)
    agg = aggregate(records)

    out = improved_root / "summary"
    out.mkdir(parents=True, exist_ok=True)

    # 1. FOR A HUMAN
    (out / "summary.md").write_text(render_md(agg), encoding="utf-8")

    # 2. FOR feature_analysis — each model's OWN exact set, tagged with whether
    #    the test is exact everywhere, so the cross-model subset needs no re-run.
    fa = [{
        "model": r["model"],
        "relative_test": r["relative_test"],
        "test_key": r["test_key"],
        "project": r["project"], "src": r["src"], "gran": r["gran"],
        "suite": r["suite"], "case": r["case"],
        "class_fqn": r["class_fqn"],
        "original": r["original_path"],
        "improved": r["improved_path"],
        "exact_in_all_models": r["test_key"] in exact_in_all,
    } for r in sorted(records, key=lambda x: (x["model"], x["test_key"]))
        if r["is_exact_match"] is True
        and r["original_path"] and r["improved_path"]]
    (out / "feature_analysis_input.json").write_text(json.dumps({
        "tests": fa,
    }, indent=2, ensure_ascii=False), encoding="utf-8")

    # 3. FOR STATS / human_evaluation.select_human_eval — one row per (model, test), exact or not.
    import csv
    cols = ["relative_test", "model", "test_key", "project", "src", "gran", "suite", "case",
            "test_id", "class_fqn", "class_path", "state_status", "compile_status",
            "coverage_available", "mutation_available", "is_exact_match", "is_lines_equal",
            "is_branches_equal", "is_mut_superset", "line_pct", "branch_pct",
            "mutation_score_pct", "mutants_killed_original", "mutants_killed_improved",
            "loc_original", "loc_improved", "n_oracles_original", "n_oracles_improved",
            "attempts_used", "original_path", "improved_path",
            "tok_input", "tok_output", "tok_cache_creation", "tok_cache_read", "tok_total"]
    with (out / "per_test.csv").open("w", newline="", encoding="utf-8") as f:
        w = csv.DictWriter(f, fieldnames=cols, extrasaction="ignore")
        w.writeheader()
        for r in sorted(records, key=lambda x: (x["model"], x["test_key"])):
            w.writerow({k: ("" if r.get(k) is None else r.get(k)) for k in cols})

    # 4. FOR RQ3 — one unit per (model, project, src, suite).
    (out / "suite_cases.json").write_text(json.dumps({
        "units": [units[k] for k in sorted(units)],
    }, indent=2, ensure_ascii=False), encoding="utf-8")

    log.info("[summary] %s", out)
    log.info("[summary]   summary.md                  — %d model(s)", len(models))
    log.info("[summary]   feature_analysis_input.json — %d pair(s), %d exact in all",
             len(fa), len(exact_in_all))
    n_pass = sum(1 for u in units.values() if u["passes"])
    log.info("[summary]   suite_cases.json            — %d unit(s), %d pass "
             "(suite exact and >= %.0f%% of cases exact)",
             len(units), n_pass, threshold * 100)
    return out


def main() -> None:
    ap = argparse.ArgumentParser(description=__doc__,
                                 formatter_class=argparse.RawDescriptionHelpFormatter)
    ap.add_argument("--config", default=str(PROJECT_ROOT / "config.yaml"))
    ap.add_argument("--improved-root",
                    help="override data/improved/ (default: from --config paths:)")
    ap.add_argument("--case-exact-threshold", type=float,
                    default=CASE_EXACT_THRESHOLD,
                    help="RQ3 admission: a suite unit needs its suite exact "
                         "AND at least this fraction of its cases exact "
                         f"(default {CASE_EXACT_THRESHOLD}; 1.0 = every case)")
    args = ap.parse_args()
    logging.basicConfig(level=logging.INFO, format="%(message)s")

    if args.improved_root:
        root = Path(args.improved_root).expanduser()
        root = root if root.is_absolute() else (PROJECT_ROOT / root)
    else:
        import yaml
        import pipeline_paths
        cfg = yaml.safe_load(open(args.config)) or {}
        paths = cfg.get("paths") or {}
        pipeline_paths.configure(paths.get("workplace_dir"), paths.get("data_root"))
        root = pipeline_paths.data_root() / "improved"

    if run(root, args.case_exact_threshold) is None:
        raise SystemExit(f"no improvement data found under {root}")


if __name__ == "__main__":
    main()
