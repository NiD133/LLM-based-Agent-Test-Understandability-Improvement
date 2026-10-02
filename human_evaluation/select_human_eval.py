#!/usr/bin/env python3
"""
select_human_eval.py — pick and lay out the 180 test pairs for the human study.

    python3 human_evaluation/select_human_eval.py
    python3 human_evaluation/select_human_eval.py --export-code
    python3 human_evaluation/select_human_eval.py --seed 7        # sensitivity run

WHAT THIS PRODUCES

    15 surveys x 2 parts x 6 pairs = 180 pairs, no pair and no test logic
    reused anywhere. Part 1 shows a pair's two sides as two separate Likert
    items at fixed, separated positions; Part 2 shows them side by side with
    the class under test.

THE DESIGN, AS THREE INDEPENDENT CONSTRAINTS ON THE SAME 180 SLOTS

  1. COLUMN QUOTA — 6 columns of 30. The columns are the columns of the RQ1
     heatmap: (model x origin). 90 manual / 90 auto, 60 per model.

  2. LOC QUOTA — inside each column, a fixed split by the size of the ORIGINAL
     test (imports excluded, see _sloc):

         manual   10 short / 12 medium /  8 long
         auto     10 short / 18 medium /  2 long

     They differ because the data differ, not because the measurement does:
     both origins use the same thresholds, but only 6 of 2,787 EvoSuite cases
     exceed 30 non-import lines (0.2%), so the auto long cell is exhausted at
     6 pairs study-wide and the remainder goes to medium. Summed over the 30
     parts this still yields exactly 2 short + 3 medium + 1 long per part.

  3. COVERAGE FLOOR — within each column, every transformation type that is
     RECURRENT IN THAT COLUMN (rate >= 2% there, i.e. a cell of the RQ1
     heatmap above threshold) must be carried by at least K_MIN pairs of that
     column. Column-wise on purpose: 5 of the 23 types exceed 2% in exactly
     one column, so covering them with another model's pairs would report a
     model difference as if it were shared behaviour.

WHY K_MIN = 3 AND NOT 5
    In the gpt-5.5/manual column two recurrent types — Modify Class Annotation
    and Remove Thrown Exception Type — have ZERO short and ZERO medium
    candidates; they occur only in long tests, and no single test carries both.
    At K_MIN=3 they already consume 6 of that column's 8 long slots and the
    column's long bucket ends exactly full. K_MIN=4 would need 8 long slots for
    those two types alone and K_MIN=5 would need 10, against a quota of 8.
    Measured: K_MIN=3 succeeds in all six columns, K_MIN=4 and 5 do not.
    K_MIN=5 also costs 22 of 30 slots in the one manual column where it is
    feasible, which would leave the sample no longer mostly random.

TWO PHASES, AND WHY THE SPLIT MATTERS

    floor   the picks needed to satisfy constraint 3        ~45 of 180 (25%)
    random  uniform draw inside (column, LOC bucket)        ~135 of 180 (75%)

    Pure stratified random cannot support a coverage claim: over 300 draws it
    covered a median of 20 of the 23 types, reached all 23 in 2% of draws, and
    left a mean of 4.6-5.2 types with ZERO pairs in each manual column.
    Repairing a random draw afterwards reaches the same guarantee but replaces
    a median of 141 of 180 pairs, so "mostly random" stops being true. Seeding
    first costs 45 and leaves the other 135 untouched.

    `phase` in the CSV records which pairs were which, so a reviewer can redo
    the analysis on the random 135 alone.

NOTHING HERE LOOKS AT AN OUTCOME
    Selection reads the RQ1 structural analysis and the exact-match verdicts.
    No human rating exists when it runs, so there is no direction to tune
    toward. Ship this file, --seed, and the two input CSVs and the 180 pairs
    reproduce exactly.

INPUTS
    agent_improvement/data/improved/summary/per_test.csv    the candidate pool
    feature_analysis/out/feature_analysis/feature_by_test.csv   per-test features
    feature_analysis/out/feature_analysis/feature_frequency.csv per-column rates

OUTPUTS — human_evaluation/
    human_eval_selection.csv      180 rows, one per pair (the main artefact)
    human_eval_part1_layout.csv   15 x 12 rows, the Part-1 position schedule
    human_eval_summary.md         quota check + per-column coverage table
    human_eval_selection.json     full record incl. config and seed
    surveys/                      only with --export-code: the .java files
"""

from __future__ import annotations

import argparse
import collections
import csv
import json
import logging
import random
import re
import shutil
import sys
from pathlib import Path
from typing import Optional

HERE = Path(__file__).resolve().parent
PROJECT_ROOT = HERE.parent
sys.path.insert(0, str(PROJECT_ROOT / "feature_analysis"))
from lib.feature_registry import canonical_feature          # noqa: E402
from lib.gumtree import COMMENT_KINDS, coarsen_comment_feature  # noqa: E402

log = logging.getLogger("select_human_eval")

# ═══════════════════════════ CONFIG — edit here ═══════════════════════════

N_SURVEYS = 15
PARTS_PER_SURVEY = 2
PAIRS_PER_PART = 6                       # => 15 * 2 * 6 = 180 pairs

# LOC mix inside ONE part. Must sum to PAIRS_PER_PART.
LOC_MIX_PER_PART = {"short": 2, "medium": 3, "long": 1}

K_MIN = 3                 # coverage floor per (column, recurrent type)
COMMON_RATE = 0.02        # a type is "recurrent in a column" at >= this rate
GRANULARITY = "testcases"
EXCLUDE_IMPORTS = True
LOC_BUCKETS = {"short": (0, 15), "medium": (15, 30)}     # above medium = long
SEED = 20260924

# ── the floor is TWO-TIER, and the second tier is the important one ─────────
# Only Part 2 asks the evaluator WHICH change made the test easier to read, so
# a transformation that never appears in a Part-2 pair produces no attribution
# data at all. Enforcing the floor only over a column's 30 pairs does not
# prevent that: part assignment is independent of it, and a type with exactly
# three pairs has a ~12% chance of landing all three in Part 1. Measured on the
# one-tier version: five (column, type) combinations ended with ZERO Part-2
# pairs, including Extract Method on opus-4.8/manual, and 24 more had fewer
# than three.
#
# So the floor is applied INSIDE Part 2 first. It cannot be K_MIN there: a
# column contributes 15 pairs to Part 2 and gpt-5.5/manual needs 16 to reach 3
# for each of its 18 types. At 2 it needs 11, and every column fits. Two pairs
# x 3-5 evaluators per survey = 6-10 attribution responses per (column, type),
# and the common types get far more by carrying along with the rare ones.
K_MIN_PART2 = 2

# Per column, per part, per LOC bucket. Each column still sums to 30 and each
# part still sums to 6 with the 2/3/1 LOC mix:
#     Part 2   manual 5/5/5   auto 5/10/0   -> 30 short, 45 medium, 15 long
#     Part 1   manual 5/6/4   auto 5/9/1    -> 30 short, 45 medium, 15 long
# Auto contributes no long pairs to Part 2 because the whole study only has
# three of them (one per model); they go to Part 1, where the floor does not
# depend on them.
PART_QUOTA = {
    ("manual", 1): {"short": 5, "medium": 6, "long": 4},
    ("manual", 2): {"short": 5, "medium": 5, "long": 5},
    ("auto",   1): {"short": 5, "medium": 9, "long": 1},
    ("auto",   2): {"short": 5, "medium": 10, "long": 0},
}
QUOTA = {src: {b: PART_QUOTA[(src, 1)][b] + PART_QUOTA[(src, 2)][b]
               for b in PART_QUOTA[(src, 1)]}
         for src in ("manual", "auto")}      # manual 10/11/9, auto 10/19/1

# Part 1 shows the six pairs as twelve Likert items plus a fixed attention
# check, which the survey skeleton already carries at screen position 9. So
# item t renders at screen t for t <= 8 and at screen t+1 from t = 9 on.
#
# PART1_SCHEDULE is item 1..12 -> (pair slot, side). It is an explicit table
# rather than a formula because a formula produced a schedule a reader could
# learn: every pair was exactly six items apart and the sides alternated
# O I O I O I / I O I O I O. Here the six screen gaps are all different
# (4, 5, 6, 7, 8, 10) and the side sequence has no period.
#
#     item     1   2   3   4   5   6   7   8   9  10  11  12
#     screen   1   2   3   4   5   6   7   8  10  11  12  13
#     pair     6   5   4   2   3   1   4   6   2   1   5   3
#     side     I   O   O   I   I   O   I   O   O   I   I   O
#
# The invariants the table has to satisfy, all checked at startup by
# _check_part1_schedule(): every pair appears exactly twice with opposite
# sides; three pairs are seen improved-first and three original-first, so
# presentation order is not confounded with version; six items show the
# original and six the improved, three of each in the first half and three in
# the second; no side repeats more than twice in a row; and no pair's two
# sides are closer than four screens apart.
#
# The schedule is identical in all fifteen surveys. The CONTENT is not: deal()
# shuffles which test lands on which pair slot, independently per survey, so a
# position is not tied to any particular test.
PART1_SCHEDULE = [
    (6, "improved"), (5, "original"), (4, "original"), (2, "improved"),
    (3, "improved"), (1, "original"), (4, "improved"), (6, "original"),
    (2, "original"), (1, "improved"), (5, "improved"), (3, "original"),
]
ATTENTION_CHECK_SCREEN = 9

# Part 2 pair n shows Test A / Test B in this order — improved, improved,
# original, improved, original, original. Three of each, fixed across all
# fifteen surveys.
PART2_IMPROVED_FIRST = (True, True, False, True, False, False)

# Eligibility. n_features>=1 is required: with no detected change the two sides
# are the same test and the pair is an empty question. The rest are
# presentation limits — set any to None to disable.
FILTERS = {
    "min_oracles_original": 1,      # a test with no assertion has nothing to read
    "max_loc_improved": 60,         # must fit on one questionnaire screen
    "max_growth_ratio": 3.0,        # improved/original; beyond this it is not
                                    #   the same test at a different quality
    "max_cut_lines": 800,           # Part 2 shows the CUT beside the pair
}

# ═══════════════════════════ paths ═══════════════════════════

PER_TEST_CSV = PROJECT_ROOT / "agent_improvement/data/improved/summary/per_test.csv"
FEATURE_CSV = PROJECT_ROOT / "feature_analysis/out/feature_analysis/feature_by_test.csv"
FREQ_CSV = PROJECT_ROOT / "feature_analysis/out/feature_analysis/feature_frequency.csv"
WORKPLACE = PROJECT_ROOT / "local_workplace"
OUT_DIR = HERE

# The comment features exist at two granularities (lib/gumtree). The RQ1
# figures and this script both use the coarse roll-up; the twelve verb-level
# rows are a refinement of the same edits, so counting both would count one
# test twice on two rows.
_VERBS = ("Added", "Deleted", "Updated", "Moved")
FINE_COMMENT = {canonical_feature(f"{k} {v}") for k in COMMENT_KINDS for v in _VERBS}


# ═══════════════════════════ LOC ═══════════════════════════

_IMPORT_RE = re.compile(r"\s*(import|package)\b")


def _sloc(text: str, exclude_imports: bool = EXCLUDE_IMPORTS) -> int:
    """Non-blank lines after stripping // and /* */ comments, with string and
    char literals preserved so a `//` inside a string is not taken for a
    comment. The import filter runs BEFORE comment stripping.

    Imports are excluded because they are ~31% of a split test case's
    non-comment lines and are identical boilerplate across the cases of one
    suite: counting them makes the three strata collapse into one (measured on
    the full pool: with imports counted, auto has 42 short and 671 long; with
    them excluded, 2,475 short and 6 long — the strata invert)."""
    if exclude_imports:
        text = "\n".join(l for l in text.splitlines() if not _IMPORT_RE.match(l))
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


def _bucket(loc: int) -> str:
    for name, (lo, hi) in LOC_BUCKETS.items():
        if lo < loc <= hi:
            return name
    return "long"


# ═══════════════════════════ inputs ═══════════════════════════

def load_features() -> dict:
    """{relative_test: {coarse feature, ...}} — canonical names, comment rows
    rolled up to the coarse level, verb-level rows dropped."""
    if not FEATURE_CSV.exists():
        raise SystemExit(f"{FEATURE_CSV} not found — run feature_analysis first")
    out = {}
    with FEATURE_CSV.open(encoding="utf-8") as f:
        for r in csv.DictReader(f):
            s = set()
            for raw in (r.get("features_present") or "").split(";"):
                raw = raw.strip()
                if not raw:
                    continue
                k = canonical_feature(raw)
                s.add(k)
                s.add(canonical_feature(coarsen_comment_feature(k)))
            out[r["relative_test"]] = {x for x in s if x not in FINE_COMMENT}
    return out


def load_column_sets() -> dict:
    """{(model, src): {types recurrent in THAT column}} straight off the same
    CSV the RQ1 heatmap is drawn from, so the two always agree."""
    if not FREQ_CSV.exists():
        raise SystemExit(f"{FREQ_CSV} not found — run feature_analysis first")
    cols = collections.defaultdict(set)
    with FREQ_CSV.open(encoding="utf-8") as f:
        for r in csv.DictReader(f):
            if r["granularity"] != GRANULARITY:
                continue
            feat = canonical_feature(r["feature"])
            if feat in FINE_COMMENT:
                continue
            if float(r["rate"] or 0) >= COMMON_RATE:
                cols[(r["model"], r["src"])].add(feat)
    return dict(cols)


def load_candidates(features: dict) -> tuple[list, collections.Counter]:
    """Every eligible pair. A 'pair' is one (model, test) improvement; the
    'test logic' it is built on is (project, src, suite, case) and is shared by
    up to three pairs, one per model — only one of them may ever be used."""
    if not PER_TEST_CSV.exists():
        raise SystemExit(f"{PER_TEST_CSV} not found — run "
                         f"scripts/summarize_improvements.py first")
    cands, dropped = [], collections.Counter()
    cut_lines_cache: dict = {}
    with PER_TEST_CSV.open(encoding="utf-8") as f:
        for r in csv.DictReader(f):
            if r["gran"] != GRANULARITY:
                dropped["not a test case"] += 1; continue
            if r["is_exact_match"] != "True":
                dropped["not exact match"] += 1; continue
            op = PROJECT_ROOT / r["original_path"] if r["original_path"] else None
            ip = PROJECT_ROOT / r["improved_path"] if r["improved_path"] else None
            if not (op and ip and op.is_file() and ip.is_file()):
                dropped["java file missing"] += 1; continue

            feats = features.get(r["relative_test"], set())
            if not feats:
                dropped["no detected change (n_features=0)"] += 1; continue

            loc_o = _sloc(op.read_text(encoding="utf-8", errors="replace"))
            loc_i = _sloc(ip.read_text(encoding="utf-8", errors="replace"))
            if loc_o <= 0:
                dropped["original measures 0 lines"] += 1; continue

            if FILTERS["min_oracles_original"] is not None:
                try: n_or = int(r["n_oracles_original"] or 0)
                except ValueError: n_or = 0
                if n_or < FILTERS["min_oracles_original"]:
                    dropped["no assertion in the original"] += 1; continue
            if FILTERS["max_loc_improved"] is not None and loc_i > FILTERS["max_loc_improved"]:
                dropped["improved too long for one screen"] += 1; continue
            if FILTERS["max_growth_ratio"] is not None and loc_i / loc_o > FILTERS["max_growth_ratio"]:
                dropped["improved grew more than the cap"] += 1; continue

            cut_rel, cut_n = r["class_path"], None
            if FILTERS["max_cut_lines"] is not None:
                key = (r["project"], cut_rel)
                if key not in cut_lines_cache:
                    p = WORKPLACE / r["project"] / cut_rel if cut_rel else None
                    cut_lines_cache[key] = (
                        sum(1 for _ in p.open(encoding="utf-8", errors="replace"))
                        if p and p.is_file() else None)
                cut_n = cut_lines_cache[key]
                if cut_n is not None and cut_n > FILTERS["max_cut_lines"]:
                    dropped["class under test too long"] += 1; continue

            cands.append({
                "relative_test": r["relative_test"], "model": r["model"],
                "project": r["project"], "src": r["src"],
                "suite": r["suite"], "case": r["case"],
                "class_fqn": r["class_fqn"], "class_path": cut_rel,
                "cut_lines": cut_n,
                "original": r["original_path"], "improved": r["improved_path"],
                "loc_original": loc_o, "loc_improved": loc_i,
                "loc_bucket": _bucket(loc_o),
                "n_oracles_original": r["n_oracles_original"],
                "features": feats,
                "logic": (r["project"], r["src"], r["suite"], r["case"]),
            })
    return cands, dropped


# ═══════════════════════════ selection ═══════════════════════════

def seed_column(pool: list, targets: set, k_min: int, quota: dict,
                banned: set, rng: random.Random,
                have: Optional[collections.Counter] = None) -> tuple[list, list]:
    """The coverage floor for one column.

    Types are processed rarest-first: what is scarce is not candidates but
    QUOTA, and a type confined to one LOC bucket has to be served while that
    bucket still has room. Among the candidates carrying the current target we
    take the one with the highest GAIN — how many still-unmet types it carries
    — because one pair carries five types on average and up to fifteen, so a
    good pick advances many targets at once. (Measured on gpt-5.5/manual step
    1: 53 candidates, gain 3..14; taking the best gives 14, drawing at random
    would give 6.1 on average, and the whole phase would cost roughly twice as
    many slots.) Remaining bucket room only breaks ties, and the rng only
    breaks ties among those — ordering by slack FIRST makes the choice nearly
    blind to coverage and doubles the cost (44 slots vs 24, measured).

    Returns (picked, unmet) — `unmet` is empty when the floor was reached."""
    supply = collections.Counter()
    per_bucket = collections.defaultdict(collections.Counter)
    for p in pool:
        for f in p["features"] & targets:
            supply[f] += 1
            per_bucket[f][p["loc_bucket"]] += 1

    # Order: most BUCKET-CONSTRAINED first, then rarest.
    #
    # Raw rarity is the wrong priority on its own. What runs out is a bucket's
    # quota, not candidates: Modify Class Annotation has 77 candidates in the
    # gpt-5.5/manual column and is still the hardest type to place, because all
    # 77 are long tests and the column only has 8 long slots. A type with 53
    # candidates spread over three buckets is not competing for anything
    # scarce. So a type that can only be served from one bucket is placed
    # before one that has a choice, and rarity only breaks ties inside a
    # constraint level. Ordering by rarity alone lets the earlier, denser picks
    # (which are long, because feature-dense tests are long) eat the long quota
    # before the long-only types are reached, and the floor then fails.
    def constraint(f):
        usable = sum(1 for b, n in per_bucket[f].items()
                     if n >= k_min and quota.get(b, 0) >= k_min)
        return (usable or 1, supply[f], f)

    used = collections.Counter()
    # `have` carries counts already earned elsewhere — phase B tops a column up
    # to K_MIN counting the pairs phase A already placed in Part 2.
    have = collections.Counter() if have is None else collections.Counter(have)
    picked: list = []
    for f in sorted(targets, key=constraint):
        while have[f] < k_min:
            best, best_key = None, None
            for p in rng.sample(pool, len(pool)):          # ties broken by seed
                if f not in p["features"] or p["logic"] in banned:
                    continue
                b = p["loc_bucket"]
                room = quota[b] - used[b]
                if room <= 0:
                    continue
                gain = sum(1 for g in (p["features"] & targets) if have[g] < k_min)
                key = (gain, room)
                if best_key is None or key > best_key:
                    best, best_key = p, key
            if best is None:
                unmet = [x for x in targets if have[x] < k_min]
                return picked, unmet
            used[best["loc_bucket"]] += 1
            banned.add(best["logic"])
            best["phase"] = "floor"
            picked.append(best)
            for g in (best["features"] & targets):
                have[g] += 1
    return picked, []


def fill_cell(cands: list, models: list, need: dict, src: str, part: int,
              bucket: str, banned: set, rng: random.Random) -> list:
    """Fill one (src, part, LOC bucket) cell for ALL THREE models at once.

    Filling model by model would be wrong where a cell is tight. A test logic
    is improved by up to three models but may be used only once, so the three
    columns of one cell compete for the same logics. The auto/long cell has
    five usable logics for three models and two of them exist for only two of
    the three, so a per-model greedy can hand those to the models that had
    other options and leave one model with nothing. Each cell is therefore
    solved as an assignment: repeatedly take the logic wanted by the FEWEST
    models still short and give it to the neediest of those. That is
    most-constrained-first, and it succeeds whenever an assignment exists."""
    want = {m: need.get(m, 0) for m in models}
    if not any(want.values()):
        return []
    by_logic: dict = collections.defaultdict(dict)
    for c in cands:
        if (c["src"] == src and c["loc_bucket"] == bucket
                and c["logic"] not in banned):
            by_logic[c["logic"]][c["model"]] = c
    order = list(by_logic)
    rng.shuffle(order)
    out = []
    while any(v > 0 for v in want.values()):
        live = [lg for lg in order if any(want[m] > 0 for m in by_logic[lg])]
        if not live:
            raise SystemExit(
                f"cell {src}/part{part}/{bucket}: cannot fill "
                f"{ {m: n for m, n in want.items() if n > 0} } — "
                f"{len(order)} usable test logic(s) left. Lower this cell's "
                f"quota or relax a filter.")
        lg = min(live, key=lambda l: (sum(1 for m in by_logic[l] if want[m] > 0),
                                      -max(want[m] for m in by_logic[l])))
        m = max((m for m in by_logic[lg] if want[m] > 0), key=lambda m: want[m])
        c = by_logic[lg][m]
        c.setdefault("phase", "random")
        c["part"] = part
        out.append(c)
        banned.add(lg)
        want[m] -= 1
        order.remove(lg)
    return out


def select(cands: list, columns: dict, rng: random.Random) -> tuple[list, dict]:
    """Three phases. Order matters: the scarcest guarantee is bought first.

      A  Part-2 floor  — K_MIN_PART2 pairs per (column, recurrent type),
                         placed in Part 2, inside the Part-2 bucket quota.
      B  overall floor — top up to K_MIN per (column, type) using Part-1 slots.
      C  random fill   — every remaining (src, part, bucket) cell, all models
                         together, uniform inside the cell.
    """
    chosen, report = [], {}
    banned: set = set()
    models = sorted({c["model"] for c in cands})
    used = collections.Counter()          # (model, src, part, bucket) -> n

    for col in sorted(columns):
        model, src = col
        pool = [p for p in cands if p["model"] == model and p["src"] == src]

        # ── A: the Part-2 floor ────────────────────────────────────────────
        p2, unmet = seed_column(pool, columns[col], K_MIN_PART2,
                                PART_QUOTA[(src, 2)], banned, rng)
        if unmet:
            raise SystemExit(
                f"column {model}/{src}: cannot reach K_MIN_PART2="
                f"{K_MIN_PART2} in Part 2 for {', '.join(sorted(unmet))} "
                f"inside {PART_QUOTA[(src, 2)]}. Lower K_MIN_PART2.")
        for p in p2:
            p["part"] = 2
            used[(model, src, 2, p["loc_bucket"])] += 1

        # ── B: top the column up to K_MIN overall, in Part 1 ───────────────
        have = collections.Counter()
        for p in p2:
            for f in (p["features"] & columns[col]):
                have[f] += 1
        short = {f for f in columns[col] if have[f] < K_MIN}
        p1: list = []
        if short:
            p1, unmet = seed_column(pool, short, K_MIN, PART_QUOTA[(src, 1)],
                                    banned, rng, have=have)
            if unmet:
                raise SystemExit(
                    f"column {model}/{src}: cannot reach K_MIN={K_MIN} for "
                    f"{', '.join(sorted(unmet))} inside the Part-1 quota "
                    f"{PART_QUOTA[(src, 1)]}. Lower K_MIN.")
            for p in p1:
                p["part"] = 1
                used[(model, src, 1, p["loc_bucket"])] += 1

        for p in p2 + p1:
            p["column"] = f"{model}/{src}"
        chosen += p2 + p1
        report[col] = {"n_targets": len(columns[col]),
                       "floor_p2": len(p2), "floor_p1": len(p1), "random": 0}

    # ── C: random fill, cell by cell ───────────────────────────────────────
    for src in ("manual", "auto"):
        for part in (2, 1):
            for bucket in ("long", "medium", "short"):   # tightest first
                need = {m: PART_QUOTA[(src, part)][bucket]
                           - used[(m, src, part, bucket)] for m in models}
                got = fill_cell(cands, models, need, src, part, bucket,
                                banned, rng)
                for p in got:
                    p["column"] = f"{p['model']}/{src}"
                    report[(p["model"], src)]["random"] += 1
                chosen += got
    return chosen, report


# ═══════════════════════════ dealing into surveys ═══════════════════════════

def deal(chosen: list, rng: random.Random) -> list:
    """Spread each part's pairs over the surveys.

    Every part takes exactly one pair from each of the six columns, which gives
    3 manual / 3 auto and 2 per model for free. The LOC mix is dealt so that
    every part is 2 short + 3 medium + 1 long; the totals were chosen to make
    that exact. Balance is then reached by swapping pairs BETWEEN surveys
    WITHIN one (part, column, bucket), which can disturb neither margin."""
    per_part = sum(LOC_MIX_PER_PART.values())
    out = []
    for part in (1, 2):
        items = [p for p in chosen if p["part"] == part]
        if len(items) != N_SURVEYS * per_part:
            raise SystemExit(f"part {part}: {len(items)} pairs, expected "
                             f"{N_SURVEYS * per_part}")
        # Deal each (column, bucket) group round-robin over the surveys, then
        # repair the per-survey LOC mix by same-bucket swaps.
        surveys: list[list] = [[] for _ in range(N_SURVEYS)]
        groups = collections.defaultdict(list)
        for p in items:
            groups[(p["column"], p["loc_bucket"])].append(p)
        for g in groups.values():
            rng.shuffle(g)
        order, keys = [], sorted(groups)
        while any(groups[k] for k in keys):
            for k in keys:
                if groups[k]:
                    order.append(groups[k].pop())
        for i, p in enumerate(order):
            surveys[i % N_SURVEYS].append(p)

        def cost(sv):
            lb = collections.Counter(p["loc_bucket"] for p in sv)
            cb = collections.Counter(p["column"] for p in sv)
            return (sum(abs(lb[b] - n) for b, n in LOC_MIX_PER_PART.items())
                    + sum(abs(v - 1) for v in cb.values())
                    + abs(len(cb) - per_part))

        for _ in range(60000):
            if sum(cost(sv) for sv in surveys) == 0:
                break
            i, j = rng.randrange(N_SURVEYS), rng.randrange(N_SURVEYS)
            if i == j:
                continue
            a, b = rng.randrange(per_part), rng.randrange(per_part)
            before = cost(surveys[i]) + cost(surveys[j])
            surveys[i][a], surveys[j][b] = surveys[j][b], surveys[i][a]
            if cost(surveys[i]) + cost(surveys[j]) > before:
                surveys[i][a], surveys[j][b] = surveys[j][b], surveys[i][a]

        bad = [(n + 1, cost(sv)) for n, sv in enumerate(surveys) if cost(sv)]
        if bad:
            raise SystemExit(f"part {part}: could not balance surveys {bad}")

        for n, sv in enumerate(surveys, start=1):
            rng.shuffle(sv)
            for slot, p in enumerate(sv, start=1):
                side_a = ("improved"
                          if PART2_IMPROVED_FIRST[(slot - 1) % len(PART2_IMPROVED_FIRST)]
                          else "original")
                out.append({**p, "survey": n, "part": part, "pair_slot": slot,
                            "side_a": side_a if part == 2 else ""})
    return out


def part1_screen(item: int) -> int:
    """Questionnaire position of Part-1 item `item`, stepping over the fixed
    attention check the skeleton carries at ATTENTION_CHECK_SCREEN."""
    return item if item < ATTENTION_CHECK_SCREEN else item + 1


def _check_part1_schedule() -> None:
    n = len(PART1_SCHEDULE)
    if n != 2 * PAIRS_PER_PART:
        raise SystemExit(f"PART1_SCHEDULE has {n} items, expected "
                         f"{2 * PAIRS_PER_PART}")
    seen = collections.defaultdict(list)
    for item, (pair, side) in enumerate(PART1_SCHEDULE, start=1):
        seen[pair].append((item, side))
    for pair in range(1, PAIRS_PER_PART + 1):
        got = seen.get(pair, [])
        if len(got) != 2 or {s for _, s in got} != {"original", "improved"}:
            raise SystemExit(f"PART1_SCHEDULE: pair {pair} must appear twice, "
                             f"once per side; got {got}")
        gap = part1_screen(got[1][0]) - part1_screen(got[0][0])
        if gap < 4:
            raise SystemExit(f"PART1_SCHEDULE: pair {pair} sides are {gap} "
                             f"screens apart, minimum is 4")
    sides = [s for _, s in PART1_SCHEDULE]
    if sides.count("improved") != n // 2:
        raise SystemExit("PART1_SCHEDULE must show each side six times")
    if sum(1 for v in seen.values() if v[0][1] == "improved") != PAIRS_PER_PART // 2:
        raise SystemExit("PART1_SCHEDULE must show three pairs improved-first")
    half = n // 2
    for tag, chunk in (("first", sides[:half]), ("second", sides[half:])):
        if chunk.count("improved") != half // 2:
            raise SystemExit(f"PART1_SCHEDULE: the {tag} half is unbalanced")
    run = 1
    for a, b in zip(sides, sides[1:]):
        run = run + 1 if a == b else 1
        if run > 2:
            raise SystemExit("PART1_SCHEDULE: more than two of a side in a row")


def part1_layout(rows: list) -> list:
    """Lay Part 1 out by PART1_SCHEDULE.

    `item` is the index within Part 1, 1..12; `screen` is where the
    questionnaire renders it once the attention check is stepped over."""
    by_slot = collections.defaultdict(dict)
    for r in rows:
        if r["part"] == 1:
            by_slot[(r["survey"], r["pair_slot"])] = r
    out = []
    for survey in sorted({r["survey"] for r in rows}):
        for item, (pair, side) in enumerate(PART1_SCHEDULE, start=1):
            r = by_slot[(survey, pair)]
            out.append({"survey": survey, "item": item,
                        "screen": part1_screen(item),
                        "pair_slot": pair, "side": side,
                        "relative_test": r["relative_test"], "path": r[side]})
    return out


# ═══════════════════════════ outputs ═══════════════════════════

PAIR_FIELDS = ["survey", "part", "pair_slot", "side_a", "phase", "column",
               "model", "src", "project", "suite", "case", "class_fqn",
               "class_path", "cut_lines", "loc_bucket", "loc_original",
               "loc_improved", "n_oracles_original", "relative_test",
               "original", "improved"]


def write_outputs(rows, layout, cands, dropped, report, columns, seed):
    """Two files, and only two.

    human_eval_selection.json  everything create_qsf.py needs
    human_eval_summary.md      everything a person needs to check it
    """
    OUT_DIR.mkdir(parents=True, exist_ok=True)
    ordered = sorted(rows, key=lambda r: (r["survey"], r["part"], r["pair_slot"]))

    (OUT_DIR / "human_eval_selection.json").write_text(json.dumps({
        "config": {
            "n_surveys": N_SURVEYS, "parts_per_survey": PARTS_PER_SURVEY,
            "pairs_per_part": PAIRS_PER_PART,
            "loc_mix_per_part": LOC_MIX_PER_PART,
            "quota_per_column": QUOTA,
            "quota_per_column_part": {f"{s_}/part{p_}": q
                                      for (s_, p_), q in PART_QUOTA.items()},
            "k_min": K_MIN, "k_min_part2": K_MIN_PART2,
            "common_rate": COMMON_RATE, "granularity": GRANULARITY,
            "exclude_imports": EXCLUDE_IMPORTS,
            "loc_buckets": {k: list(v) for k, v in LOC_BUCKETS.items()},
            "filters": FILTERS, "seed": seed,
            "attention_check_screen": ATTENTION_CHECK_SCREEN,
            "part1_schedule": [{"item": i, "screen": part1_screen(i),
                                "pair_slot": p, "side": sd}
                               for i, (p, sd) in enumerate(PART1_SCHEDULE, 1)],
            "part2_side_a": [{"pair_slot": i,
                              "side_a": "improved" if v else "original"}
                             for i, v in enumerate(PART2_IMPROVED_FIRST, 1)],
        },
        "candidate_pool": len(cands),
        "dropped": dict(dropped),
        "columns": {f"{m}/{s_}": sorted(v) for (m, s_), v in columns.items()},
        "recurrent_types": sorted(set().union(*columns.values())),
        "pairs": [{**{k: r[k] for k in PAIR_FIELDS},
                   "features": sorted(r["features"])} for r in ordered],
        "part1_layout": layout,
    }, indent=2, ensure_ascii=False), encoding="utf-8")

    # ── summary.md ─────────────────────────────────────────────────────────
    models = sorted({r["model"] for r in rows})
    L = ["# Human Evaluation — 180 pairs over 15 surveys", "",
         f"Seed `{seed}`. Drawn from {len(cands)} eligible pairs "
         f"({len({c['logic'] for c in cands})} distinct test logics).", "",
         "## Summary", "",
         "| model | manual | auto | total |", "|---|--:|--:|--:|"]
    for m in models:
        mm = sum(1 for r in rows if r["model"] == m and r["src"] == "manual")
        aa = sum(1 for r in rows if r["model"] == m and r["src"] == "auto")
        L.append(f"| {m} | {mm} | {aa} | {mm + aa} |")
    tm = sum(1 for r in rows if r["src"] == "manual")
    L.append(f"| **total** | **{tm}** | **{len(rows) - tm}** | **{len(rows)}** |")

    lb = collections.Counter(r["loc_bucket"] for r in rows)
    pb = collections.Counter(r["part"] for r in rows)
    fp = collections.Counter(r["phase"] for r in rows)
    L += ["",
          f"LOC: short {lb['short']} / medium {lb['medium']} / long {lb['long']}"
          f"  —  Part 1 {pb[1]} / Part 2 {pb[2]}"
          f"  —  coverage floor {fp['floor']} / random {fp['random']}", ""]

    # ── the question order, which is the same in all fifteen surveys ──────
    items = list(range(1, len(PART1_SCHEDULE) + 1))
    L += ["## Question order — identical in every survey", "",
          "### Part 1 — the six pairs as twelve Likert items", "",
          "`pair` is the slot number used in the per-survey lists below; which "
          "test sits in a slot differs per survey. The attention check is a "
          f"fixed question at screen {ATTENTION_CHECK_SCREEN} and is the same "
          "in all fifteen surveys.", "",
          "| | " + " | ".join(str(i) for i in items) + " |",
          "|---" * (len(items) + 1) + "|",
          "| **item** | " + " | ".join(str(i) for i in items) + " |",
          "| **screen** | " + " | ".join(str(part1_screen(i)) for i in items) + " |",
          "| **pair** | " + " | ".join(str(PART1_SCHEDULE[i - 1][0]) for i in items) + " |",
          "| **side** | " + " | ".join(
              "I" if PART1_SCHEDULE[i - 1][1] == "improved" else "O"
              for i in items) + " |", ""]
    gaps = {}
    for i, (pair, _) in enumerate(PART1_SCHEDULE, start=1):
        gaps.setdefault(pair, []).append(part1_screen(i))
    L += ["Screen gap between the two sides of a pair: "
          + ", ".join(f"pair {p} = {v[1] - v[0]}"
                      for p, v in sorted(gaps.items(), key=lambda kv: kv[1][1] - kv[1][0]))
          + ". Three pairs are seen improved-first and three original-first.",
          "",
          "### Part 2 — which side is Test A", "",
          "| pair | " + " | ".join(str(i + 1) for i in range(PAIRS_PER_PART)) + " |",
          "|---" * (PAIRS_PER_PART + 1) + "|",
          "| **Test A** | " + " | ".join(
              "I" if v else "O" for v in PART2_IMPROVED_FIRST) + " |",
          "| **Test B** | " + " | ".join(
              "O" if v else "I" for v in PART2_IMPROVED_FIRST) + " |",
          "", "(I = improved, O = original)", ""]

    # coverage table, same shape as the RQ1 heatmap
    cols = sorted(columns)
    L += ["## Coverage — pairs carrying each type, per column", "",
          f"`n (p2)` = pairs in the column, of which in Part 2. Bold = the type "
          f"is recurrent in that column (≥ {COMMON_RATE:.0%}), so the floor "
          f"applies: ≥ {K_MIN} overall and ≥ {K_MIN_PART2} in Part 2. "
          f"A dash = not recurrent there.", "",
          "| transformation | " + " | ".join(f"{m}/{s_}" for m, s_ in cols) + " |",
          "|---" * (len(cols) + 1) + "|"]
    ok = True
    for t in sorted(set().union(*columns.values())):
        cells = []
        for col in cols:
            inc = [r for r in rows if (r["model"], r["src"]) == col
                   and t in r["features"]]
            n, n2 = len(inc), sum(1 for r in inc if r["part"] == 2)
            if t in columns[col]:
                good = n >= K_MIN and n2 >= K_MIN_PART2
                ok = ok and good
                cells.append(f"**{n} ({n2})**" if good else f"{n} ({n2}) ⚠")
            else:
                cells.append(f"{n} ({n2})" if n else "–")
        L.append(f"| {t} | " + " | ".join(cells) + " |")
    L += ["", "All floors met." if ok
          else "**A floor was missed — see the marked cells.**", ""]

    # ── the 15 surveys ─────────────────────────────────────────────────────
    lay = collections.defaultdict(list)
    for x in layout:
        lay[x["survey"]].append(x)
    for sv in range(1, N_SURVEYS + 1):
        L += [f"## Survey {sv}", ""]
        for part in (1, 2):
            items = [r for r in ordered if r["survey"] == sv and r["part"] == part]
            if part == 1:
                L += ["### Part 1 — 6 pairs shown as 12 Likert items",
                      "(slot order and sides: see the table above)", ""]
            else:
                L += ["### Part 2 — 6 side-by-side comparisons", ""]
            for r in items:
                extra = (f"  — Test A = {r['side_a']}" if part == 2 else "")
                L.append(f"{r['pair_slot']}. **[{r['model']}]** "
                         f"{r['project']} / {r['suite']} / {r['case']}"
                         f"  ({r['src']}, {r['loc_bucket']}, "
                         f"{r['loc_original']}→{r['loc_improved']} LOC)"
                         f"{extra}")
                L.append(f"   - features: {', '.join(sorted(r['features']))}")
            L.append("")
    (OUT_DIR / "human_eval_summary.md").write_text("\n".join(L), encoding="utf-8")


# ═══════════════════════════ main ═══════════════════════════

def main() -> None:
    global K_MIN, K_MIN_PART2, OUT_DIR
    ap = argparse.ArgumentParser(description=__doc__,
                                 formatter_class=argparse.RawDescriptionHelpFormatter)
    ap.add_argument("--seed", type=int, default=SEED,
                    help=f"selection is deterministic per seed (default {SEED})")
    ap.add_argument("--k-min", type=int, default=K_MIN,
                    help=f"coverage floor per (column, type) (default {K_MIN})")
    ap.add_argument("--k-min-part2", type=int, default=K_MIN_PART2,
                    help=f"of which, in Part 2 (default {K_MIN_PART2})")
    ap.add_argument("--out", default=str(OUT_DIR))
    args = ap.parse_args()
    logging.basicConfig(level=logging.INFO, format="%(message)s")

    K_MIN, K_MIN_PART2, OUT_DIR = args.k_min, args.k_min_part2, Path(args.out)

    rng = random.Random(args.seed)
    features = load_features()
    columns = load_column_sets()
    cands, dropped = load_candidates(features)
    log.info("[human_eval] pool %d pair(s) / %d distinct test logic(s); dropped %s",
             len(cands), len({c["logic"] for c in cands}), dict(dropped))
    for col in sorted(columns):
        log.info("[human_eval] column %-12s %-7s %2d recurrent type(s)",
                 col[0], col[1], len(columns[col]))

    need = N_SURVEYS * PARTS_PER_SURVEY * PAIRS_PER_PART
    planned = sum(sum(PART_QUOTA[(s_, p_)].values())
                  for (m, s_) in columns for p_ in (1, 2))
    if planned != need:
        raise SystemExit(f"PART_QUOTA sums to {planned} pairs but the survey "
                         f"shape needs {need}")
    if sum(LOC_MIX_PER_PART.values()) != PAIRS_PER_PART:
        raise SystemExit("LOC_MIX_PER_PART must sum to PAIRS_PER_PART")
    _check_part1_schedule()

    chosen, report = select(cands, columns, rng)
    rows = deal(chosen, rng)
    layout = part1_layout(rows)

    logics = [r["logic"] for r in rows]
    assert len(logics) == len(set(logics)), "a test logic was used twice"
    assert len(rows) == need, f"dealt {len(rows)} pairs, expected {need}"

    write_outputs(rows, layout, cands, dropped, report, columns, args.seed)
    nf = sum(1 for r in rows if r["phase"] == "floor")
    log.info("[human_eval] %d pairs (%d floor + %d random) over %d surveys -> %s",
             len(rows), nf, len(rows) - nf, N_SURVEYS, OUT_DIR)


if __name__ == "__main__":
    main()
