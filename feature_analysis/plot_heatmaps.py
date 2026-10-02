#!/usr/bin/env python3
"""
plot_heatmaps.py — feature-change heatmaps for RQ1.

    python3 feature_analysis/plot_heatmaps.py
    python3 feature_analysis/plot_heatmaps.py --granularity testsuites
    python3 feature_analysis/plot_heatmaps.py --top-n 15

INPUT — one file:

    feature_analysis/out/feature_analysis/feature_frequency.csv

written by feature_analysis.py. Columns:

    model, src, granularity, feature, tests_with_feature, tests_total, rate

This replaces the old loader, which globbed `Summary/<model>/*manual*.json`
and read a `featureFrequency` block. That format no longer exists; the same
numbers now come pre-aggregated in the CSV, so nothing has to be re-derived
from per-test JSON.

    old                          new
    featureFrequency[f].count →  tests_with_feature
    totalFiles                →  tests_total
    file name contains manual →  src column
    directory name            →  model column
    (no equivalent)           →  granularity column

GRANULARITY IS FIXED, NOT MIXED
    Default is `testcases`. A suite and a case are not comparable units: a
    suite holds ~20 cases, and a feature counts as present if it appears
    anywhere in the file, so suite-level rates are an OR-aggregation of their
    cases and come out systematically higher — measured on opus-4.8,
    `Extract Method` is 0.23 at suite level vs 0.04 at case level, and
    `Line Comment change` on auto is 0.87 vs 0.42. Putting both in one figure
    makes cells incomparable: the difference is the denominator, not the model.
    (`Javadoc change` runs the other way — 0.81 suite vs 0.97 case — because
    every split case gets its own Javadoc, which is exactly why mixing them
    cannot be fixed by a caveat in the caption.)

    Pass --granularity testsuites for the suite-level figure, and read the two
    side by side rather than merging them.

COLUMNS: 3 models, one figure per origin. Manual and auto are NOT put on one
    grid: they are different test populations (developer-written vs
    EvoSuite-generated), they do not share a row set — manual carries 63
    features, auto 20, with only 18 in common — so half of a combined grid
    would be structurally blank rather than informative. The manual-minus-auto
    Delta figure carries the cross-origin comparison instead.

OUTPUT — feature_analysis/out/heatmaps/, every figure as .pdf (no CSV, no
JSON, no TXT; --formats png,pdf adds PNG previews). The PDF is the one to
\\includegraphics: text and cells are vector, fonts are embedded as TrueType.
ONE plain run writes all of these:

  RQ1, from feature_frequency.csv (test cases):
    heatmap_manual_testcases_full.pdf   every feature seen in >= 1 developer-written test case
    heatmap_auto_testcases_full.pdf     every feature seen in >= 1 EvoSuite test case
    heatmap_manual_testcases.pdf        features at >= 2% of the cases in >= 1 model
    heatmap_auto_testcases.pdf
    heatmap_combined_testcases.pdf      both origins, 6 columns, >= 2% cut

  RQ3, from feature_by_test.csv + suite_cases.json:
    heatmap_rq3_propagation.pdf         how far a suite-level change reaches the suite's cases
    heatmap_rq3_direction_gt50.pdf      suite-only / case-only / shared; a change counts at case
                                        level when it is in > 50% of the suite's test cases

COMMENT ROWS: two per comment kind, "<Kind> Added/Updated" and "<Kind>
    Deleted", from the comment post-processor (feature_analysis.py --stage
    comments; definitions in lib/comment_units.py). A CSV still carrying the
    old coarse "... Change" rows or GumTree's raw verbs is refused.

No in-figure titles: the LaTeX captions carry them.
"""

import argparse
import json
import os
import sys
import textwrap

import collections
import csv
import numpy as np
import pandas as pd
import matplotlib
matplotlib.use("Agg")
import matplotlib.pyplot as plt
from matplotlib import colors as mcolors

# PDF/PS text as embedded TrueType (Type 42) instead of matplotlib's default
# Type 3, which camera-ready checks such as IEEE PDF eXpress and ACM TAPS flag.
matplotlib.rcParams["pdf.fonttype"] = 42
matplotlib.rcParams["ps.fonttype"] = 42

HERE = os.path.dirname(os.path.abspath(__file__))
PROJECT_ROOT = os.path.dirname(HERE)
sys.path.insert(0, HERE)
from lib.feature_registry import canonical_feature  # noqa: E402

# The comment features are TWO rows per kind — "<Kind> Added/Updated" and
# "<Kind> Deleted" (lib/comment_units.comment_entries) — in every figure. The
# old scheme (a coarse "... Change" row plus GumTree's raw verbs Added /
# Updated / Moved) is refused rather than plotted: "Line Comment Deleted"
# exists in both schemes with different definitions, so a stale CSV would
# otherwise draw silently wrong comment rows.
LEGACY_COMMENT_ROWS = {f"{k} {v}"
                       for k in ("Line Comment", "Block Comment", "Javadoc")
                       for v in ("Added", "Updated", "Moved", "Change")} | {"Javadoc Comment Change"}

# ===================== CONFIG =====================
FREQ_CSV = os.path.join(HERE, "out", "feature_analysis", "feature_frequency.csv")
OUT_DIR = os.path.join(HERE, "out", "heatmaps")

# Which granularity to plot. See the note in the module docstring — this is a
# single value on purpose.
GRANULARITY = "testcases"


# ── ROW THRESHOLD — edit this ───────────────────────────────────────────────
# A feature earns a row when AT LEAST ONE model reaches `min_rate` on that
# origin. "At least one model" on purpose: a feature only one model produces is
# exactly the asymmetry worth showing, and a mean-based cut would delete it
# (Add Method Annotation is .07 on sonnet and .00-.01 on the other two).
#
# WHY A RATE AND NOT A RAW COUNT: this threshold is what the paper will call a
# "common feature", and it feeds the human study's coverage claim, so it has to
# be explainable. A count of 50 is 1.79%-1.93% depending on the group (n runs
# 2595-2786), which means the definition drifts slightly between groups and
# "why 50?" can only be answered by converting it to a rate anyway. 2% and
# count 50 select the SAME 23 manual features and the same 10 auto ones, so
# this is a change of wording, not of figures.
#
# At 2%: 23 of 63 manual features, carrying 95.9% of all observed feature
# occurrences; 10 of 20 auto features, carrying 99.7%. The auto side is flat
# from 1% to 5% (the other 10 features together account for 0.3%), so the
# choice only moves the manual figure.
#
# The figure title also prints the equivalent count, because the cells show
# rates to two decimals: 0.008 and 0.0104 both print as "0.01" while falling on
# opposite sides of a 1% cut, and a reader needs the integer to check the cut.
#
# Test suites are left unfiltered: n is only ~130 there, so 2% is ~3 tests.
THRESHOLDS = {
    "testcases":  {"min_rate": 0.02},
    "testsuites": {"min_rate": None},
    "ALL":        {"min_rate": 0.02},
}

# Features dropped from EVERY figure (RQ1 and RQ3): edits to a method's
# `throws` clause. 78% of them delete an unneeded `throws Exception` from a
# setUp()/tearDown() method that was carried into the per-case file; they
# change a signature, not the test body. They stay in feature_frequency.csv.
# Feature counts quoted in this file's comments predate this exclusion.
EXCLUDED_FEATURES = {"Remove Thrown Exception Type",
                     "Add Thrown Exception Type",
                     "Change Thrown Exception Type"}

# Paper labels for features whose internal name reads poorly in a figure.
# Only the RQ1 figures are relabelled; the CSVs keep the internal name, and the
# RQ3 code still matches it (RQ3_EXCLUDE_SUBSTR).
DISPLAY_NAMES = {"Blank-Line Separation Added": "Blank Line Insertion"}
# ────────────────────────────────────────────────────────────────────────────

# Color config
USE_DISCRETE_COLORS = True
CMAP_CONTINUOUS = "viridis"
CONTINUOUS_GAMMA = 1.0

# Manual/Auto heatmaps (rates in [0,1]).
# Two separate problems were fixed here.
#
# BOUNDS: chosen by measuring where the cells of the two FILTERED figures
# actually sit (93 manual cells, 30 auto). The original bounds put 46 of 93
# manual cells in one bin, [0.01, 0.05) — 49% of the figure a single shade.
# Raising the cuts made it worse (80% of manual cells ended up in the three
# palest bins) because the mass is at the LOW end: it needs MORE cuts there.
# With the bounds below the largest bin holds ~24% of manual cells and ~23% of
# auto ones, and both figures still share one scale so colours stay comparable.
#
# COLOURS: the old ramp started at #f7fbff, which is white to the eye, so the
# two or three lowest bins were indistinguishable from an empty cell. The ramp
# below reserves pure white for EXACTLY ZERO — "this model never did it" is a
# different statement from "it did it rarely" — via the 1e-9 bound, and starts
# the tinted range at a blue that is visible on paper.
MANUAL_AUTO_BOUNDS = [0.0, 1e-9, 0.02, 0.04, 0.10, 0.25, 0.50, 0.80, 1.0]
MANUAL_AUTO_COLORS = [
    "#ffffff",                                      # exactly 0
    "#d6e6f4", "#aacfe8", "#7db8dc", "#4f9ecd",     # rare -> common
    "#2b7bba", "#155a9c", "#0a3a75",
]

# Δ heatmap (manual − auto), centered at 0
DIFF_BOUNDS = [-1.0, -0.6, -0.2, -0.05, 0.0, 0.05, 0.2, 0.6, 1.0]
DIFF_COLORS = [
    "#7f0000", "#b2182b", "#ef8a62", "#fddbc7",
    "#d9f0d3", "#a6dba0", "#5aae61", "#1b7837",
]

ROUND_DECIMALS = 2
DROP_ALL_ZERO_ROWS = True

# ── Print geometry ─────────────────────────────────────────────────────────
# THE FIGURE IS GENERATED AT THE SIZE IT WILL BE PRINTED AT.
#
# The old code drew a 13 x 15 in figure with 10 pt text. \includegraphics
# [width=\textwidth] then scaled it by 7/13 = 0.54 and the reader saw 5.4 pt
# (by \columnwidth: 0.25, i.e. 2.5 pt). That is the blur — NOT the resolution:
# 300 dpi into 7 in is 2100 px, far above any camera-ready floor, and raising
# DPI cannot fix it because the scale factor is applied afterwards.
#
# Generating at FIG_WIDTH_IN makes the scale factor 1.0, so the point sizes
# below are literally the point sizes on paper. 7 pt is the usual floor for
# figure text in IEEE/ACM styles; caption text is ~8 pt, so these read as
# slightly-smaller-than-caption, which is what a dense table should look like.
#
# --fig-width 3.4 for a single column; --font-scale to trade legibility
# against how many columns fit.
DPI = 300                  # 220 was below most venues' camera-ready floor
# Every figure is written once per format. PDF only by default — it is what the
# paper includes; --formats png,pdf adds PNG previews. DPI only matters for the
# PNG — the PDF has no raster content.
OUTPUT_FORMATS = ("pdf",)
FIG_WIDTH_IN = 7.0         # \textwidth of a 2-column IEEE/ACM page
CELL_FONTSIZE = 7.0
LABEL_FONTSIZE = 7.5
TITLE_FONTSIZE = 8.5
ROW_HEIGHT_IN = 0.24       # ~2.5x the 7 pt line height
CHROME_H_IN = 1.30         # title + rotated x labels + margins
# --no-title: a paper figure carries its title in the LaTeX caption. The
# figure then gets shorter by one title line and its pad, rather than handing
# that space to taller rows.
SHOW_TITLE = True
TITLE_H_IN = 0.25
# Width of one character as a fraction of the font size, used only to warn
# when the cells are too narrow for "0.63 (1646)" to fit.
CHAR_W_EM = 0.0068
# Show the raw count beside the rate. This is what caps the font size: at 7 in
# with 6 columns, "0.63 (1645)" needs ~0.63 in per column at 7 pt, whereas
# "0.63" needs 0.23 in and would allow ~11 pt. --no-counts trades the per-cell
# n for legibility; the denominator is still in the title ("of ~2759").
SHOW_COUNTS = True
# Rate above which the cell text flips to white. Black on the two darkest
# blues is barely readable.
DARK_TEXT_FLIP = 0.50
# Mark cells whose raw count is below this, so "0.00 (6)" is visibly different
# from "0.00 (0)" — a rate of zero and six observations are not the same claim.
SMALL_N_MARK = 10
# ==================================================


# ── input ────────────────────────────────────────────────────────────────────

def load_dataframe(freq_csv: str, granularity: str) -> pd.DataFrame:
    """The long table, in the shape the plotting code below expects:
    model / data_type / feature / count / total / rate.

    `data_type` is the CSV's `src` (manual = developer-written, auto =
    EvoSuite-generated) — the name is kept so the plotting helpers read the
    same as before."""
    if not os.path.isfile(freq_csv):
        raise SystemExit(f"{freq_csv} not found — run "
                         f"feature_analysis/feature_analysis.py first")
    df = pd.read_csv(freq_csv)
    want = {"model", "src", "granularity", "feature",
            "tests_with_feature", "tests_total", "rate"}
    missing = want - set(df.columns)
    if missing:
        raise SystemExit(f"{freq_csv} is missing column(s): {sorted(missing)}")

    df = df[df["granularity"] == granularity].copy()
    if df.empty:
        have = sorted(pd.read_csv(freq_csv)["granularity"].unique())
        raise SystemExit(f"no rows with granularity={granularity!r}; "
                         f"the CSV has {have}")

    df = df.rename(columns={"src": "data_type",
                            "tests_with_feature": "count",
                            "tests_total": "total"})
    df["feature"] = df["feature"].map(canonical_feature)
    check_comment_rows(set(df["feature"]), freq_csv)
    df = df[~df["feature"].isin(EXCLUDED_FEATURES)]
    df["feature"] = df["feature"].replace(DISPLAY_NAMES)
    for c in ("count", "total", "rate"):
        df[c] = pd.to_numeric(df[c], errors="coerce")
    return df[["model", "data_type", "feature", "count", "total", "rate"]]


def check_comment_rows(features, source):
    """Fail on comment rows of the old scheme — see LEGACY_COMMENT_ROWS."""
    stale = sorted(set(features) & LEGACY_COMMENT_ROWS)
    if stale:
        raise SystemExit(f"{source} still has old-scheme comment rows {stale} — "
                         f"re-run feature_analysis/feature_analysis.py --stage "
                         f"comments, then --stage merge")


# ── matrices ─────────────────────────────────────────────────────────────────


def order_by_total_count(mat):
    """Reorder rows by the total number of tests carrying the feature, summed
    over the models, most first.

    Replaces the registry's fixed display order. The registry order groups
    features by what they act on (variables, then attributes, then methods,
    then annotations), which reads as arbitrary on a heatmap: the rows a reader
    cares about end up scattered down a column of near-identical pale cells.
    Sorting by mass puts them at the top and pushes the tail to the bottom,
    where it can be skimmed or cut.

    The sum is over counts, not rates: rates would let a feature with a high
    rate in one small group outrank one that is common everywhere, and the
    denominators here differ by 7% anyway.

    Consequence worth knowing: the manual and auto figures then have DIFFERENT
    row orders, so the two cannot be compared row-by-row side by side. That is
    fine as long as the cross-origin comparison is read off the Delta figure,
    which is what it is for."""
    if mat.empty or not isinstance(mat.columns, pd.MultiIndex):
        return mat
    if "count" not in mat.columns.get_level_values(0):
        return mat
    return mat.loc[mat["count"].sum(axis=1).sort_values(ascending=False).index]


def order_by_origin_blocks(mat, models, origins=("manual", "auto")):
    """Row order for the combined figure: three contiguous blocks, each sorted
    by total count, most first.

        1. features BOTH origins produced
        2. features only the first origin (manual) produced
        3. features only the second origin (auto) produced

    Sorting the whole figure by count alone interleaves the gaps: a
    manual-only feature with a big count lands between two rows that have data
    on both sides, so the blank half-cells appear scattered down the middle of
    the figure and read as missing data rather than as a property of the
    feature. Blocking them puts every gap in one contiguous run at the bottom,
    which is what makes the figure legible as "the manual grid, with the
    shorter auto grid beside it".

    On the current data block 3 is empty: two features are auto-only
    (Add Variable Annotation, 19 occurrences; Split Variable, 6) and neither
    clears the 2% threshold. The block is built anyway so a different
    threshold or granularity does not silently reorder the figure."""
    if mat.empty or not isinstance(mat.columns, pd.MultiIndex):
        return mat
    cnt = mat["count"]
    have = {o: cnt[[f"{o} {m}" for m in models if f"{o} {m}" in cnt.columns]].sum(axis=1) > 0
            for o in origins}
    first, second = origins[0], origins[1]
    total = cnt.sum(axis=1)

    def block(rows):
        return list(total.loc[rows].sort_values(ascending=False).index)

    both = mat.index[have[first] & have[second]]
    only_first = mat.index[have[first] & ~have[second]]
    only_second = mat.index[~have[first] & have[second]]
    return mat.loc[block(both) + block(only_first) + block(only_second)]


def make_heatmap_matrix(df, dtype, top_n):
    """MultiIndex-column matrix with ('rate'|'count', model) columns, for one
    origin. A feature a model never produced becomes rate 0 / count 0 rather
    than being dropped, so every column has the same rows."""
    sub = df[df["data_type"] == dtype]
    if sub.empty:
        return pd.DataFrame()

    mat_rate = sub.pivot_table(index="feature", columns="model",
                               values="rate", aggfunc="first")
    mat_count = sub.pivot_table(index="feature", columns="model",
                                values="count", aggfunc="first")

    if top_n and top_n > 0:
        top = mat_rate.mean(axis=1).sort_values(ascending=False).head(top_n).index
        mat_rate, mat_count = mat_rate.loc[top], mat_count.loc[top]

    mat = pd.concat({"rate": mat_rate, "count": mat_count}, axis=1).fillna(0.0)
    return order_by_total_count(mat)


def make_combined_matrix(df, models, min_rate, origins=("manual", "auto"),
                         group_by="origin", rows="union"):
    """The single 6-column figure: 3 models x 2 test origins.

    Returns (matrix, mask). The mask marks the cells to leave EMPTY — see
    blank_missing_origin_blocks — so the figure reads as the manual figure with
    the (shorter) auto figure set beside it, rather than as a grid with a
    column of "0.00 (0)" filler.

    `group_by` decides the column order, i.e. which comparison is adjacent:
      "origin" -> manual x3 | auto x3. The three models sit side by side, so
                  MODEL differences read directly and the origin effect shows
                  as a shift between the two blocks.
      "model"   -> gpt(m,a) | opus(m,a) | sonnet(m,a). Each model's two origins
                  are adjacent, so the model x origin INTERACTION reads
                  directly.

    `rows`:
      "union"        -> every feature either origin produced (the default). The
                        auto half then has gaps, which the mask turns into
                        white space instead of zeros.
      "intersection" -> only features both origins produce above the threshold.
                        No gaps, ~10 rows, but drops the 13 features EvoSuite
                        tests never receive.
    """
    cols = []
    if group_by == "model":
        for m in models:
            cols += [(o, m) for o in origins]
    else:
        for o in origins:
            cols += [(o, m) for m in models]

    rate, count = {}, {}
    for origin, m in cols:
        s = df[(df["data_type"] == origin) & (df["model"] == m)].set_index("feature")
        rate[f"{origin} {m}"] = s["rate"]
        count[f"{origin} {m}"] = s["count"]
    mat = pd.concat({"rate": pd.DataFrame(rate),
                     "count": pd.DataFrame(count)}, axis=1).fillna(0.0)

    if min_rate is not None:
        r = mat["rate"]
        if rows == "intersection":
            keep = pd.Series(True, index=mat.index)
            for o in origins:
                keep &= (r[[f"{o} {m}" for m in models]] >= min_rate).any(axis=1)
        else:
            keep = (r >= min_rate).any(axis=1)
        mat = mat.loc[keep]
    mat = order_by_origin_blocks(mat, models, origins)
    return mat, blank_missing_origin_blocks(mat, models, origins)


def blank_missing_origin_blocks(mat, models, origins):
    """True where a cell should be drawn as empty white space.

    A whole origin block of a row is blanked when that origin produced the
    feature ZERO times across ALL its models — EvoSuite tests never receive an
    Attribute Rename, so printing "0.00 (0)" three times says nothing and
    invites the reader to compare a number that does not exist.

    Blanking is per BLOCK, not per cell, on purpose: inside a block that did
    produce the feature, a single model's zero IS informative ("gpt-5.5 added
    no Javadoc while the other two did"), so those cells keep their 0.00."""
    if mat.empty or not isinstance(mat.columns, pd.MultiIndex):
        return None
    cnt = mat["count"]
    mask = pd.DataFrame(False, index=cnt.index, columns=cnt.columns)
    for o in origins:
        block = [f"{o} {m}" for m in models if f"{o} {m}" in cnt.columns]
        if block:
            mask.loc[cnt[block].sum(axis=1) == 0, block] = True
    return mask


def apply_threshold(mat, min_rate):
    """Keep the features where at least one model reaches `min_rate`.

    Returns (kept, dropped_names). `min_rate` None means no threshold."""
    if mat.empty or not isinstance(mat.columns, pd.MultiIndex) or min_rate is None:
        return mat, []
    ok = (mat["rate"] >= min_rate).any(axis=1)
    return mat.loc[ok], list(mat.index[~ok])


# ── RQ3: suite level vs case level ───────────────────────────────────────────
# These two figures need the PAIRING, so they read feature_by_test.csv (one row
# per test) and suite_cases.json (which suite unit each case belongs to and
# whether that unit is usable), not the pre-aggregated frequency CSV.

BY_TEST_CSV = os.path.join(HERE, "out", "feature_analysis", "feature_by_test.csv")
SUITE_CASES_JSON = os.path.join(PROJECT_ROOT, "agent_improvement", "data", "improved",
                                "summary", "suite_cases.json")

# Excluded from the suite-vs-case comparison only — both are fine in the RQ1
# figures, which compare models at ONE granularity.
#   Block Comment*: the splitter drops the EvoSuite/licence file header from the
#     per-case files, so 88% of suite-level block-comment changes have zero
#     case-level coverage by construction.
#   Blank-Line Separation Added: produced at CASE LEVEL ONLY (see
#     feature_analysis.merge_one), so in a suite-vs-case view it would read as
#     100% case-only by construction rather than as a finding.
RQ3_EXCLUDE_SUBSTR = ("Block Comment", "Blank-Line Separation")


def case_present(n_with, n_cases, case_rule=None):
    """Whether a change found in `n_with` of a suite's `n_cases` test cases
    counts as present at case level. None = in at least one (the main rule);
    (share, strict) = in more than (strict) / at least `share` of them."""
    if case_rule is None:
        return n_with > 0
    share, strict = case_rule
    frac = n_with / n_cases
    return frac > share if strict else frac >= share


def load_pairs(by_test_csv=BY_TEST_CSV, suite_cases=SUITE_CASES_JSON, case_rule=None):
    """(suite_features, case_features, n_cases) keyed by (model, project, src,
    suite), restricted to the units where BOTH arms preserved behaviour.
    `case_rule` decides when a feature counts at case level (case_present)."""
    with open(suite_cases, encoding="utf-8") as f:
        units = json.load(f)["units"]
    usable = {(u["model"], u["project"], u["src"], u["suite"])
              for u in units if u.get("passes")}

    suite_f, case_counts, n_cases = {}, collections.defaultdict(collections.Counter), collections.Counter()
    with open(by_test_csv, encoding="utf-8") as f:
        for r in csv.DictReader(f):
            if r["complete"] != "True":
                continue
            parts = r["relative_test"].split("/")
            key = (parts[0], parts[1], parts[2], parts[4])
            if key not in usable:
                continue
            feats = {canonical_feature(x.strip())
                     for x in r["features_present"].split(";") if x.strip()}
            check_comment_rows(feats, by_test_csv)
            feats = {x for x in feats
                     if x not in EXCLUDED_FEATURES
                     and not any(s in x for s in RQ3_EXCLUDE_SUBSTR)}
            if r["granularity"] == "testsuites":
                suite_f[key] = feats
            else:
                n_cases[key] += 1
                case_counts[key].update(feats)
    case_f = collections.defaultdict(set)
    for key, counts in case_counts.items():
        case_f[key] = {x for x, c in counts.items()
                       if case_present(c, n_cases[key], case_rule)}
    return suite_f, case_f, n_cases


COVERAGE_BINS = [(0.0, 0.0, "0"), (0.0, 0.2, "(0,.2]"), (0.2, 0.4, "(.2,.4]"),
                 (0.4, 0.6, "(.4,.6]"), (0.6, 0.8, "(.6,.8]"), (0.8, 1.0, "(.8,1]")]


def make_propagation_matrix(by_test_csv=BY_TEST_CSV, suite_cases=SUITE_CASES_JSON,
                            min_units=25):
    """Rows = feature, columns = coverage band, value = share of the units where
    the feature is present AT SUITE LEVEL whose case-level coverage falls there.

    Answers: when a transformation appears in the suite-level rewrite, in how
    many of that suite's individually-rewritten cases does it also appear?
    Conditioned on suite presence, so the first column ("0") is exactly the
    suite-only case and no separate suite-only figure is needed."""
    with open(suite_cases, encoding="utf-8") as f:
        units = json.load(f)["units"]
    usable = {(u["model"], u["project"], u["src"], u["suite"])
              for u in units if u.get("passes")}

    suite_f, per_case, n_cases = {}, collections.defaultdict(set), collections.Counter()
    with open(by_test_csv, encoding="utf-8") as f:
        for r in csv.DictReader(f):
            if r["complete"] != "True":
                continue
            parts = r["relative_test"].split("/")
            key = (parts[0], parts[1], parts[2], parts[4])
            if key not in usable:
                continue
            feats = {canonical_feature(x.strip())
                     for x in r["features_present"].split(";") if x.strip()}
            check_comment_rows(feats, by_test_csv)
            feats = {x for x in feats
                     if x not in EXCLUDED_FEATURES
                     and not any(s in x for s in RQ3_EXCLUDE_SUBSTR)}
            if r["granularity"] == "testsuites":
                suite_f[key] = feats
            else:
                n_cases[key] += 1
                for x in feats:
                    per_case[(key, x)].add(parts[5])

    def band(v):
        if v == 0:
            return "0"
        for lo, hi, lab in COVERAGE_BINS[1:]:
            if lo < v <= hi:
                return lab
        return "(.8,1]"

    dist = collections.defaultdict(collections.Counter)
    for key, feats in suite_f.items():
        n = n_cases[key]
        if not n:
            continue
        for x in feats:
            dist[x][band(len(per_case[(key, x)]) / n)] += 1

    labels = [lab for _lo, _hi, lab in COVERAGE_BINS]
    rows, counts = {}, {}
    for x, c in dist.items():
        n = sum(c.values())
        if n < min_units:
            continue
        rows[x] = [c[lab] / n for lab in labels]
        counts[x] = [c[lab] for lab in labels]
    if not rows:
        return pd.DataFrame()
    rate = pd.DataFrame(rows, index=labels).T
    cnt = pd.DataFrame(counts, index=labels).T
    mat = pd.concat({"rate": rate, "count": cnt}, axis=1)
    # Most-propagating first: the share at or above 0.6.
    order = (rate["(.6,.8]"] + rate["(.8,1]"]).sort_values(ascending=False).index
    return mat.loc[order]


def make_direction_matrix(by_test_csv=BY_TEST_CSV, suite_cases=SUITE_CASES_JSON,
                          min_units=25, case_rule=None):
    """Rows = feature, columns = suite-only / both / case-only, as shares of the
    units where the feature appears at EITHER level.

    Answers: which level favours which transformation? Unlike the propagation
    figure this is symmetric — it can show a feature the case arm produces and
    the suite arm does not, which the propagation view cannot represent at all.

    `case_rule`: see case_present / RQ3_CASE_RULES. Under a threshold, a unit
    whose cases have the change below it and whose suite does not have it at
    all counts at NEITHER level and leaves the denominator."""
    suite_f, case_f, _n = load_pairs(by_test_csv, suite_cases, case_rule)
    keys = set(suite_f) | set(case_f)
    tally = collections.defaultdict(lambda: [0, 0, 0])   # suite-only, both, case-only
    for k in keys:
        s, c = suite_f.get(k, set()), case_f.get(k, set())
        for x in s | c:
            tally[x][0 if x not in c else (1 if x in s else 2)] += 1
        for x in c - s:
            pass
    rows, counts = {}, {}
    for x, (so, both, co) in tally.items():
        n = so + both + co
        if n < min_units:
            continue
        rows[x] = [so / n, both / n, co / n]
        counts[x] = [so, both, co]
    if not rows:
        return pd.DataFrame()
    labels = ["suite-only", "both", "case-only"]
    rate = pd.DataFrame(rows, index=labels).T
    cnt = pd.DataFrame(counts, index=labels).T
    mat = pd.concat({"rate": rate, "count": cnt}, axis=1)
    # Suite-leaning at the top, case-leaning at the bottom.
    return mat.loc[(rate["suite-only"] - rate["case-only"]).sort_values(
        ascending=False).index]


# ── plotting ─────────────────────────────────────────────────────────────────

def _apply_row_filtering(mat, decimals=ROUND_DECIMALS,
                         drop_all_zero_rows=DROP_ALL_ZERO_ROWS):
    if mat.empty:
        return mat
    rates = mat["rate"] if isinstance(mat.columns, pd.MultiIndex) else mat
    rates = rates.round(decimals)
    keep = (rates.abs().sum(axis=1) > 0) if drop_all_zero_rows \
        else pd.Series(True, index=rates.index)
    return mat.loc[keep]


def _clip_to_bounds(values, bounds):
    if values.size == 0:
        return values
    return np.clip(np.array(values, dtype=float, copy=True),
                   bounds[0], np.nextafter(bounds[-1], np.inf))


def _fit_title(fig, text, fontsize, width_in):
    """Wrap a title that would run past the figure edge.

    MEASURED IN A LOOP, not estimated from the character count: the title
    carries the row threshold and its denominator, so a clipped one silently
    drops the number a reader needs to check the cut against. One pass is not
    enough — textwrap counts characters, but "=" ">=" and an em dash are much
    wider than a digit, so a line of the "right" character count can still
    overflow. Shrink the character budget until every produced line measures
    within the width.
    """
    r = fig.canvas.get_renderer()

    def width(line):
        probe = fig.text(0.5, 0.5, line, fontsize=fontsize)
        w = probe.get_window_extent(renderer=r).width / fig.dpi
        probe.remove()
        return w

    out = []
    for line in str(text).split("\n"):
        w = width(line)
        if not line or w <= width_in:
            out.append(line)
            continue
        n = max(20, int(len(line) * width_in / w))
        for _ in range(8):
            parts = textwrap.wrap(line, n)
            if all(width(x) <= width_in for x in parts):
                break
            n = max(20, int(n * 0.93))
        out.extend(parts)
    return "\n".join(out)


def _cells(data, **kw):
    """The heatmap cells as vector rectangles rather than an image.

    imshow is embedded in a PDF as a bitmap, whose cell edges blur once the
    paper is zoomed; pcolormesh draws one rectangle per cell. edgecolors="face"
    closes the hairline seams PDF viewers otherwise show between neighbours.
    Cells stay centred on integer coordinates with row 0 at the top, exactly
    where imshow put them, so ticks, dividers and cell text need no change."""
    n_rows, n_cols = np.shape(data)
    ax = plt.gca()
    im = ax.pcolormesh(np.arange(n_cols + 1) - 0.5, np.arange(n_rows + 1) - 0.5,
                       data, edgecolors="face", linewidth=0.3, **kw)
    ax.set_xlim(-0.5, n_cols - 0.5)
    ax.set_ylim(n_rows - 0.5, -0.5)
    return im


def _colorbar(im, ticks=None, labels=None):
    """The colorbar inherits the figure's point sizes. Left at the matplotlib
    default it renders at 10 pt, which is larger than the cell text it is
    supposed to annotate once the figure is generated at print width."""
    cb = plt.colorbar(im, fraction=0.046, pad=0.04)
    if ticks is not None:
        cb.set_ticks(ticks, labels=labels)
    cb.ax.tick_params(labelsize=LABEL_FONTSIZE, length=2, width=0.5)
    cb.outline.set_linewidth(0.5)
    return cb


def plot_matrix(mat, title, out, center0=False, vmin=None, vmax=None,
                drop_all_zero_rows=DROP_ALL_ZERO_ROWS,
                round_decimals=ROUND_DECIMALS, divider_after=None,
                mask=None):
    mat = _apply_row_filtering(mat, round_decimals, drop_all_zero_rows)
    if mask is not None and not mat.empty:
        mask = mask.reindex(index=mat.index)
    if mat.empty:
        print(f"[WARN] nothing to plot: {title} (every row filtered)")
        return

    if isinstance(mat.columns, pd.MultiIndex):
        data, cols = mat["rate"].values, list(mat["rate"].columns)
    else:
        data, cols = mat.values, list(mat.columns)
    # A masked cell is left to the axes background, so the half of a row that
    # an origin never produced is genuinely empty rather than a row of zeros.
    if mask is not None:
        data = np.ma.masked_array(data, mask=mask.reindex(columns=cols).values)

    # Width is FIXED at the print width; tight_layout gives the row labels the
    # room they need and the cells take the rest. Only the height grows with
    # the row count — a 29-row heatmap cannot be short AND legible.
    chrome_h = CHROME_H_IN if SHOW_TITLE else CHROME_H_IN - TITLE_H_IN
    fig = plt.figure(figsize=(FIG_WIDTH_IN,
                              max(2.4, mat.shape[0] * ROW_HEIGHT_IN
                                       + chrome_h)))

    if center0:
        if USE_DISCRETE_COLORS:
            cmap = mcolors.ListedColormap(DIFF_COLORS)
            cmap = cmap.copy(); cmap.set_bad("white")
            norm = mcolors.BoundaryNorm(DIFF_BOUNDS, cmap.N, clip=True)
            im = _cells(_clip_to_bounds(data, DIFF_BOUNDS), cmap=cmap, norm=norm)
            _colorbar(im, DIFF_BOUNDS)
        else:
            vmax = vmax if vmax is not None else max(
                float(np.nanmax(np.abs(data))) if data.size else 1.0, 1e-6)
            im = _cells(data, vmin=(vmin if vmin is not None else -vmax),
                        vmax=vmax, cmap="RdBu_r")
            _colorbar(im)
    else:
        if USE_DISCRETE_COLORS:
            cmap = mcolors.ListedColormap(MANUAL_AUTO_COLORS)
            cmap = cmap.copy(); cmap.set_bad("white")
            norm = mcolors.BoundaryNorm(MANUAL_AUTO_BOUNDS, cmap.N, clip=True)
            im = _cells(_clip_to_bounds(data, MANUAL_AUTO_BOUNDS),
                        cmap=cmap, norm=norm)
            # The zero bin is [0, 1e-9), so labelling both of its bounds
            # printed "0.00" twice. Label that bin once, at its middle, and
            # every other bin at its bounds.
            upper = MANUAL_AUTO_BOUNDS[2:]
            _colorbar(im, [MANUAL_AUTO_BOUNDS[1] / 2] + upper,
                      ["0"] + [f"{b:.2f}" for b in upper])
        else:
            norm = mcolors.PowerNorm(
                gamma=CONTINUOUS_GAMMA,
                vmin=vmin if vmin is not None else (np.nanmin(data) if data.size else 0.0),
                vmax=vmax if vmax is not None else (np.nanmax(data) if data.size else 1.0))
            im = _cells(data, cmap=CMAP_CONTINUOUS, norm=norm)
            _colorbar(im)

    plt.xticks(range(len(cols)), cols, rotation=30, ha="right",
               fontsize=LABEL_FONTSIZE)
    plt.tick_params(length=2, width=0.5)
    plt.yticks(range(mat.shape[0]), mat.index, fontsize=LABEL_FONTSIZE)
    if SHOW_TITLE:
        plt.title(_fit_title(fig, title, TITLE_FONTSIZE, FIG_WIDTH_IN - 0.2),
                  fontsize=TITLE_FONTSIZE)

    # A hard line between the origin blocks: the two halves are different test
    # populations, so a reader scanning a row must notice the boundary.
    for d in ([] if divider_after is None else
              (divider_after if isinstance(divider_after, (list, tuple))
               else [divider_after])):
        if 0 < d < len(cols):
            plt.axvline(d - 0.5, color="black", linewidth=1.2)


    # Each cell shows the rate AND the raw count: the denominators differ by an
    # order of magnitude between origins and granularities, so a rate on its
    # own is not readable.
    for i in range(mat.shape[0]):
        for j in range(len(cols)):
            if mask is not None and bool(mask.reindex(columns=cols).iat[i, j]):
                continue
            colour = "black"
            if isinstance(mat.columns, pd.MultiIndex):
                r, c = mat["rate"].iat[i, j], mat["count"].iat[i, j]
                if not np.isfinite(r):
                    s = ""
                else:
                    n = int(c)
                    # `*` = too few observations to read the rate as an
                    # estimate; without it "0.00 (6)" and "0.00 (0)" look alike.
                    # It is kept even when the counts are hidden — it is the
                    # only thing separating a rate of zero from no data.
                    mark = "*" if 0 < n < SMALL_N_MARK else ""
                    s = (f"{r:.{round_decimals}f}{mark}" if not SHOW_COUNTS
                         else f"{r:.{round_decimals}f} ({n}){mark}")
                    if r >= DARK_TEXT_FLIP:
                        colour = "white"
            else:
                v = mat.iat[i, j]
                s = (f"{v:+.{round_decimals}f}" if center0
                     else f"{v:.{round_decimals}f}")
                if center0 and abs(v) >= DARK_TEXT_FLIP:
                    colour = "white"
            plt.text(j, i, s, ha="center", va="center",
                     fontsize=CELL_FONTSIZE, color=colour)

    plt.tight_layout()

    # The one thing a fixed width can get wrong: too many columns for the cell
    # text. Measure the drawn axes rather than guessing, and say so — a silent
    # overlap is exactly the failure this whole block exists to prevent.
    # plt.title centres on the AXES, and the axes is pushed right by the row
    # labels and left by the colorbar, so a title wrapped to the figure width
    # still ran off the right edge. Re-centre it on the FIGURE, which is only
    # possible here: the axes position is not final until tight_layout.
    ax = plt.gca()
    pos = ax.get_position()
    ax.title.set_x((0.5 - pos.x0) / pos.width)

    ax_in = (ax.get_window_extent()
             .transformed(fig.dpi_scale_trans.inverted()))
    col_in = ax_in.width / max(1, len(cols))
    need_in = (12 if SHOW_COUNTS else 6) * CHAR_W_EM * CELL_FONTSIZE
    if col_in < need_in:
        print(f"[WARN] {os.path.basename(out)}: columns are {col_in:.2f} in, "
              f"cell text needs ~{need_in:.2f} in — pass a larger --fig-width "
              f"or a smaller --font-scale")

    stem = os.path.splitext(out)[0]
    for ext in OUTPUT_FORMATS:
        plt.savefig(f"{stem}.{ext}", dpi=DPI)
    plt.close()
    print(f"[OK] {os.path.relpath(stem, PROJECT_ROOT)}"
          f".{{{','.join(OUTPUT_FORMATS)}}}  "
          f"({FIG_WIDTH_IN:.1f} x {fig.get_figheight():.1f} in @ {DPI} dpi, "
          f"cell {CELL_FONTSIZE:g} pt)")


def has_rows(mat, drop_all_zero_rows=DROP_ALL_ZERO_ROWS,
             round_decimals=ROUND_DECIMALS):
    """Whether anything survives row filtering. plot_matrix applies the same
    filter again, so this only decides whether to bother calling it — nothing
    is written to disk any more."""
    return not _apply_row_filtering(mat, round_decimals,
                                    drop_all_zero_rows).empty


# ── main ─────────────────────────────────────────────────────────────────────

def main():
    """Draw the seven figures the paper uses, without in-figure titles (the
    LaTeX caption carries the title), into feature_analysis/out/heatmaps/:

        heatmap_manual_testcases_full.pdf   every feature seen in >= 1 manual test case of >= 1 model
        heatmap_auto_testcases_full.pdf     same for EvoSuite test cases
        heatmap_manual_testcases.pdf        features at >= 2% of manual cases in >= 1 model
        heatmap_auto_testcases.pdf          same for EvoSuite cases
        heatmap_combined_testcases.pdf      both origins side by side, >= 2% in >= 1 model x origin
        heatmap_rq3_propagation.pdf         suite-to-case propagation (RQ3)
        heatmap_rq3_direction_gt50.pdf      suite-only / case-only / shared, a change counts at
                                            case level when it is in > 50% of the suite's cases
    """
    global SHOW_TITLE
    SHOW_TITLE = False

    ap = argparse.ArgumentParser(description=__doc__,
                                 formatter_class=argparse.RawDescriptionHelpFormatter)
    ap.add_argument("--freq-csv", default=FREQ_CSV)
    ap.add_argument("--out", default=OUT_DIR)
    args = ap.parse_args()

    gran = "testcases"
    min_rate = THRESHOLDS[gran]["min_rate"]
    os.makedirs(args.out, exist_ok=True)

    df = load_dataframe(args.freq_csv, gran)
    print(f"[INFO] {df['feature'].nunique()} feature(s), {len(df)} row(s)")
    models = sorted(df["model"].dropna().unique())

    # RQ1 — per origin: every detected feature, then the >= 2% cut
    for dtype in ("manual", "auto"):
        mat = make_heatmap_matrix(df, dtype, None)
        if mat.empty:
            continue
        full = order_by_total_count(mat)
        if has_rows(full, drop_all_zero_rows=False):
            plot_matrix(full, "", os.path.join(args.out, f"heatmap_{dtype}_{gran}_full.pdf"),
                        drop_all_zero_rows=False)
        kept, dropped = apply_threshold(mat, min_rate)
        print(f"[INFO] {dtype}: {len(kept)} feature(s) at >= {min_rate:.0%}, {len(dropped)} below")
        if not kept.empty and has_rows(kept):
            plot_matrix(kept, "", os.path.join(args.out, f"heatmap_{dtype}_{gran}.pdf"))

    # RQ1 — both origins in one grid, >= 2% cut
    comb, comb_mask = make_combined_matrix(df, models, min_rate, group_by="origin", rows="union")
    if has_rows(comb):
        n_cols = len(comb["rate"].columns)
        plot_matrix(comb, "", os.path.join(args.out, f"heatmap_combined_{gran}.pdf"),
                    divider_after=list(range(len(models), n_cols, len(models))), mask=comb_mask)

    # RQ3 — propagation, and direction with the > 50% case rule
    prop = make_propagation_matrix()
    if has_rows(prop, drop_all_zero_rows=False):
        plot_matrix(prop, "", os.path.join(args.out, "heatmap_rq3_propagation.pdf"),
                    drop_all_zero_rows=False)
    direc = make_direction_matrix(case_rule=(0.50, True))
    if has_rows(direc, drop_all_zero_rows=False):
        plot_matrix(direc, "", os.path.join(args.out, "heatmap_rq3_direction_gt50.pdf"),
                    drop_all_zero_rows=False)


if __name__ == "__main__":
    main()
