#!/usr/bin/env python3
"""
analyze_human_eval.py -- the human-evaluation numbers the paper reports, and
nothing else.

    .venv/bin/python human_evaluation/analyze_human_eval.py
    .venv/bin/python human_evaluation/analyze_human_eval.py --surveys 1 2 5

INPUTS

    human_evaluation/FinalSurveys/
        Agent_<N>_Understandability.qsf      the survey as it shipped: the
                                             stimuli, the Likert anchors, the
                                             feature option list, the two
                                             attention checks
        Agent_<N>_Understandability_*.zip    the Qualtrics response export
        (or a loose Agent_<N>_*.csv in the same folder)
    human_evaluation/analysis/design_map.csv
                                             written by identify_stimuli.py:
                                             the LLM and test source of every
                                             slot, which the .qsf does not carry

    <N> IS the survey version. The shipped .qsf is the only authority on what a
    participant actually saw, because slots were edited by hand after
    generation.

OUTPUTS -- human_evaluation/analysis/

    results.md      the numbers below, in the order the paper uses them
    results.json    the same numbers, unrounded

WHAT IS COMPUTED -- only what the paper reports

    Participants    how many took part, how many failed an attention check
                    (excluded from everything below), how many are included
    Part 1          mean and median rating per side, mean and median gain,
                    Wilcoxon signed-rank (W, p, matched-pairs rank-biserial r),
                    improved rated higher / equal / lower, Very easy and
                    Very hard counts per side
    Part 2          decisive comparisons, improved / original preferred, about
                    the same, preference rate, the Test A / Test B split with
                    Fisher's exact test, and the Part-1 rating statistics for
                    the ratings given with each comparison
    Overall         the Part-1 and Part-2 ratings pooled: mean and median
                    rating per side, mean and median gain, Wilcoxon, improved
                    rated higher / equal / lower
    By LLM          Part-1 mean ratings and mean gain, Part-2 preference rate
    By test source  Part-1 mean ratings and mean gain; and the paper's table:
                    Part-1 and Part-2 mean original / improved rating per test
                    source with the Wilcoxon p-value of each comparison
    Agreement       did the 3 participants of one survey give similar scores
                    to the same test version? Krippendorff's alpha (ordinal)
                    over the Part-1 and the Part-2 ratings, with a bootstrap
                    95% CI, pooled over both sides and per side; how often the
                    three ratings are identical / within one / within two
                    scale points; and for the Part-2 choice, how many
                    comparisons were unanimous (plus nominal alpha)
    Features        judgements with at least one feature, total selections;
                    per feature: times selected, share of judgements,
                    participants who selected it, mean Part-2 gain when
                    selected vs not, Cliff's delta

    identify_stimuli.py imports the side map, Survey, sloc and LOC_BUCKETS
    from this module, so keep those names.

THE DESIGN, AS SHIPPED
    Part 1  12 Likert items = 6 tests x 2 versions. Both versions of the same
            test ARE shown to the same participant, deliberately separated so
            they never sit next to each other (4-10 items apart, and the
            attention check at screen 9 splits the run). Half the pairs show
            the improved side first, half the original side first.

                pair  original  improved  gap  shown first
                  1     T06       T11      5   original
                  2     T10       T04      6   improved
                  3     T13       T05      8   improved
                  4     T03       T07      4   original
                  5     T02       T12     10   original
                  6     T08       T01      7   improved
                T09 is the attention check.

            => Part 1 is PAIRED data. 45 participants x 6 pairs = 270 pairs.

    Part 2  6 original-vs-improved pairs shown side by side, plus a 7th screen
            that is the second attention check, inserted before the third real
            pair. Test A is the improved side in three pairs and the original
            side in three, so a left/right preference cannot masquerade as a
            preference for improved.

                screen    P01  P02  P03  P04  P05  P06  P07
                Test A    imp  imp   AC  orig imp  orig orig

    Part 1's six tests and Part 2's six are disjoint within a survey, so no
    participant meets the same test logic in both parts.

WHY THE SIDE MAP IS HARD-CODED
    The .qsf carries no metadata at all -- no path, no model name, no suite
    name, not even an HTML comment -- so nothing in the file says which of two
    snippets is the improved one. The map below was recovered by matching every
    displayed code block against the original/improved sources on disk, and
    held with zero contradictions across every survey collected. It is the
    study's fixed design, not a per-survey randomisation.

    verify_structure() re-derives the six Part-1 PAIRINGS from code similarity
    alone and fails loudly if a survey disagrees, so a future survey built to a
    different layout cannot be analysed silently under the wrong map.

ATTENTION CHECKS
    Each survey ships its own pair of checks; nothing is hard-coded.

      Part 1 (P1_T09_AC)  an instructional check: the prompt tells the reader
                          to ignore the code and pick a specific point on the
                          scale. The required point is read out of the prompt.
      Part 2 (P2_P03_AC)  Test A and Test B are byte-identical, so the only
                          defensible answer is "They are about the same".
                          Detected by comparing the two code blocks.
"""

from __future__ import annotations

import argparse
import csv
import html
import io
import json
import logging
import re
import zipfile
from pathlib import Path

import numpy as np
import pandas as pd

HERE = Path(__file__).resolve().parent
SURVEY_DIR = HERE / "FinalSurveys"
OUT_DIR = HERE / "analysis"
META_FILE = OUT_DIR / "design_map.csv"

log = logging.getLogger("analyze_human_eval")

# ── the design, as shipped ───────────────────────────────────────────────────
# Part 1: which side each Likert item shows, and which items form a pair.
PART1_SIDE = {
    "P1_T01": "improved", "P1_T02": "original", "P1_T03": "original",
    "P1_T04": "improved", "P1_T05": "improved", "P1_T06": "original",
    "P1_T07": "improved", "P1_T08": "original", "P1_T10": "original",
    "P1_T11": "improved", "P1_T12": "improved", "P1_T13": "original",
}
# pair id -> (original item, improved item). Pair ids follow the build order.
PART1_PAIRS = {
    1: ("P1_T06", "P1_T11"), 2: ("P1_T10", "P1_T04"), 3: ("P1_T13", "P1_T05"),
    4: ("P1_T03", "P1_T07"), 5: ("P1_T02", "P1_T12"), 6: ("P1_T08", "P1_T01"),
}
PART1_AC = "P1_T09_AC"

# Part 2: which side is shown as "Test A" on each screen.
PART2_SIDE_A = {
    "P2_P01": "improved", "P2_P02": "improved", "P2_P04": "original",
    "P2_P05": "improved", "P2_P06": "original", "P2_P07": "original",
}
PART2_AC = "P2_P03_AC"

# LOC strata of the study (short <= 15 < medium <= 30 < long). Not used by the
# summary; identify_stimuli.py imports it.
LOC_BUCKETS = {"short": (0, 15), "medium": (15, 30)}   # above medium = "long"

# Part-2 screens in slot order: the attention check sits between slots 2 and 3,
# so the on-screen numbering skips P03.
PART2_SLOT = {"P2_P01": 1, "P2_P02": 2, "P2_P04": 3,
              "P2_P05": 4, "P2_P06": 5, "P2_P07": 6}


# ════════════════════════ .qsf reading ══════════════════════════════════════
# Inlined from create_qsf.py so this script stands alone.

CODE_RE = re.compile(r"(<code\b[^>]*>)(.*?)(</code>)", re.S)


def load_qsf(path: Path) -> dict:
    return json.loads(path.read_text(encoding="utf-8"))


def question_map(data: dict) -> dict:
    return {e["Payload"]["QuestionID"]: e["Payload"]
            for e in data["SurveyElements"] if e.get("Element") == "SQ"}


def blocks(data: dict) -> list:
    for e in data["SurveyElements"]:
        if e.get("Element") == "BL":
            p = e["Payload"]
            return list(p.values()) if isinstance(p, dict) else p
    return []


def block_named(data: dict, description: str) -> dict:
    for b in blocks(data):
        if b.get("Description") == description:
            return b
    raise SystemExit(f"block {description!r} not found")


def question_ids(block: dict) -> list:
    return [be["QuestionID"] for be in block.get("BlockElements", [])
            if be.get("Type") == "Question"]


def code_blocks(question_text: str) -> list:
    return [m.group(2) for m in CODE_RE.finditer(question_text or "")]


_LABEL_RE = re.compile(r"<b\b[^>]*>\s*Test\s+([AB])\s*</b>", re.I)
_DETAILS_RE = re.compile(r"<details\b.*?</details>", re.S)


def split_test_a_b(question_text: str) -> tuple[str, str]:
    """(Test A, Test B) source out of one Part-2 stimulus.

    Blocks are attributed to a side by the position of its <b>Test A</b> /
    <b>Test B</b> heading, and every block under one heading is concatenated.
    Taking "the first two <code> elements" is NOT safe: a stimulus that was
    edited by hand in Qualtrics' rich-text editor can end up with a side split
    across two <code> elements, with a stray empty one, or with a mangled style
    attribute -- all three occur in the collected surveys, and the naive reading
    silently returns the stray as one side.

    The class under test is blanked with spaces first, so its own code block is
    excluded while every offset stays valid."""
    qt = question_text or ""
    body = _DETAILS_RE.sub(lambda m: " " * len(m.group(0)), qt)
    labels = [(m.start(), m.group(1).upper()) for m in _LABEL_RE.finditer(body)]
    codes = [(m.start(), dehighlight(m.group(2))) for m in CODE_RE.finditer(body)]
    if not labels:                      # no headings: fall back to positional
        return ((codes[0][1] if len(codes) > 0 else ""),
                (codes[1][1] if len(codes) > 1 else ""))
    buckets: dict[str, list] = {"A": [], "B": []}
    for pos, code in codes:
        owner = None
        for lpos, lab in labels:
            if lpos < pos:
                owner = lab
            else:
                break
        if owner:
            buckets[owner].append(code)
    return ("\n".join(buckets["A"]).strip("\n"), "\n".join(buckets["B"]).strip("\n"))


def cut_source(question_text: str) -> str:
    """The class under test shown in the collapsible <details> block."""
    m = _DETAILS_RE.search(question_text or "")
    if not m:
        return ""
    cbs = code_blocks(m.group(0))
    return "\n".join(dehighlight(c) for c in cbs).strip("\n")


def dehighlight(code_html: str) -> str:
    """The span-based highlighted HTML back to plain Java."""
    return html.unescape(re.sub(r"<[^>]+>", "", code_html))


def text_of(html_: str) -> str:
    """Visible prose of a question, with code elided."""
    s = re.sub(r"<code\b.*?</code>", " ", html_ or "", flags=re.S)
    return re.sub(r"\s+", " ", html.unescape(re.sub(r"<[^>]+>", " ", s))).strip()


# ════════════════════════ code measures ═════════════════════════════════════

def _strip_comments(text: str) -> tuple[str, int]:
    """(code with comments removed, number of lines carrying a comment).

    String and char literals are preserved so a `//` inside a string is not
    mistaken for a comment. Same state machine the selection script uses, so
    the LOC numbers stay comparable with the strata definition."""
    res: list[str] = []
    comment_lines: set = set()
    i, n, state, line = 0, len(text), "code", 0
    while i < n:
        c = text[i]
        nxt = text[i + 1] if i + 1 < n else ""
        if c == "\n":
            line += 1
        if state == "code":
            if c == '"':
                state = "str"; res.append(c)
            elif c == "'":
                state = "char"; res.append(c)
            elif c == "/" and nxt == "/":
                state = "line"; comment_lines.add(line)
            elif c == "/" and nxt == "*":
                state = "block"; comment_lines.add(line); i += 2; continue
            else:
                res.append(c)
        elif state == "str":
            res.append(c)
            if c == "\\" and i + 1 < n:
                res.append(text[i + 1]); i += 2; continue
            if c == '"':
                state = "code"
        elif state == "char":
            res.append(c)
            if c == "\\" and i + 1 < n:
                res.append(text[i + 1]); i += 2; continue
            if c == "'":
                state = "code"
        elif state == "line":
            if c == "\n":
                state = "code"; res.append(c)
            else:
                comment_lines.add(line)
        elif state == "block":
            comment_lines.add(line)
            if c == "*" and nxt == "/":
                state = "code"; i += 2; continue
            if c == "\n":
                res.append(c)
        i += 1
    return "".join(res), len(comment_lines)


def sloc(code: str) -> int:
    stripped, _ = _strip_comments(code)
    return sum(1 for ln in stripped.splitlines() if ln.strip())


# Types and calls that say nothing about WHICH class is under test.
_TOKEN_STOP = {
    "Test", "Override", "String", "Integer", "Long", "Double", "Boolean", "Object",
    "Exception", "Throwable", "RuntimeException", "IllegalArgumentException",
    "NullPointerException", "IllegalStateException", "Assert", "Assertions", "Class",
    "System", "Math", "List", "ArrayList", "Map", "HashMap", "Set", "HashSet",
    "Arrays", "Collections", "Deprecated", "SuppressWarnings", "BeforeEach", "Before",
    "After", "AfterEach", "RunWith", "EvoRunner", "EvoRunnerParameters",
}


def cut_tokens(code: str) -> set:
    """What a test keeps no matter how thoroughly it is rewritten: the types it
    names and the methods it calls on the class under test.

    Renaming the test method, renaming locals, adding Javadoc and extracting
    variables all leave this set alone, which is why it identifies the two
    versions of one test as a pair while plain text similarity cannot (measured
    on the collected surveys: nearest neighbour by this set recovers 84/84 of
    the Part-1 pairings, raw SequenceMatcher recovers almost none)."""
    types = {t for t in re.findall(r"\b([A-Z][A-Za-z0-9_]{2,})\b", code)} - _TOKEN_STOP
    calls = set(re.findall(r"\.([a-z][A-Za-z0-9_]*)\s*\(", code))
    return types | calls


def jaccard(a: set, b: set) -> float:
    return len(a & b) / len(a | b) if (a | b) else 0.0


def overlap(a: set, b: set) -> float:
    """|A∩B| / min(|A|,|B|). Preferred over Jaccard when comparing the two
    versions of one test, because an Extract Method can cut a 93-line original
    down to 28 lines and Jaccard then punishes the size difference rather than
    measuring whether the same class is under test."""
    return len(a & b) / min(len(a), len(b)) if (a and b) else 0.0


# Below this, two snippets on one Part-2 screen are probably not two versions of
# the same test. Calibrated on the collected surveys: every genuine pair scored
# >= 0.33 and all but one >= 0.75.
OVERLAP_MIN = 0.25


# ════════════════════════ survey structure ══════════════════════════════════

class Survey:
    """One shipped .qsf, reduced to what the analysis needs."""

    def __init__(self, path: Path, version: int):
        self.path = path
        self.version = version
        data = load_qsf(path)
        self.name = data["SurveyEntry"]["SurveyName"]
        qmap = question_map(data)

        # ── Part 1: the Likert items, in screen order ──────────────────────
        self.part1: dict[str, dict] = {}
        for screen, qid in enumerate(question_ids(block_named(data, "Block 2")), 1):
            q = qmap[qid]
            tag = q.get("DataExportTag") or ""
            if not re.match(r"^P1_T\d+(_AC)?$", tag):
                continue
            cbs = code_blocks(q.get("QuestionText", ""))
            code = dehighlight(cbs[0]) if cbs else ""
            self.part1[tag] = {
                "screen": screen, "code": code, "prompt": text_of(q.get("QuestionText", "")),
                "is_ac": tag.endswith("_AC"),
            }

        # ── Part 2: the pairs, in screen order ─────────────────────────────
        self.part2: dict[str, dict] = {}
        order = 0
        for qid in question_ids(block_named(data, "Block 3")):
            q = qmap[qid]
            tag = q.get("DataExportTag") or ""
            m = re.match(r"^(P2_P\d+(?:_AC)?)_(\d)_(STIM|CHOICE|RATE|ASPECT|WHY)$", tag)
            if not m:
                continue
            key, kind = m.group(1), m.group(3)
            rec = self.part2.setdefault(key, {"is_ac": "_AC" in key, "aspects": None})
            if kind == "STIM":
                order += 1
                rec["order"] = order
                rec["code_a"], rec["code_b"] = split_test_a_b(q["QuestionText"])
                rec["cut"] = cut_source(q["QuestionText"])
                h = re.search(r"<h3[^>]*><b>(.*?)</b></h3>", q["QuestionText"])
                rec["heading"] = h.group(1) if h else ""
                rec["prompt"] = text_of(q["QuestionText"])
            elif kind == "CHOICE":
                rec["choice_tag"] = tag
                rec["choice_options"] = [v.get("Display", "") for v in
                                         (q.get("Choices") or {}).values()]
            elif kind == "RATE":
                rec["rate_tag"] = tag
            elif kind == "ASPECT":
                rec["aspect_tag"] = tag
                rec["aspects"] = [re.sub(r"\s+", " ", html.unescape(
                    re.sub(r"<[^>]+>", "", v.get("Display", "")))).strip()
                    for v in (q.get("Choices") or {}).values()]
            elif kind == "WHY":
                rec["why_tag"] = tag

        # ── attention checks: required answers, read out of the survey ─────
        ac1 = self.part1.get(PART1_AC)
        self.ac1_required = None
        if ac1:
            m = re.search(r"select\s*['\"\u2018\u201c]?\s*(\d)\s*=", ac1["prompt"])
            self.ac1_required = m.group(1) if m else None
        ac2 = self.part2.get(PART2_AC)
        self.ac2_required = None
        self.ac2_identical = None
        if ac2 and ac2.get("code_a") is not None:
            a = re.sub(r"\s+", " ", ac2.get("code_a", "")).strip()
            b = re.sub(r"\s+", " ", ac2.get("code_b", "")).strip()
            self.ac2_identical = bool(a) and a == b
            if self.ac2_identical:
                opts = ac2.get("choice_options") or []
                same = [o for o in opts if "about the same" in o.lower()]
                self.ac2_required = same[0] if same else None

        self.aspect_options = next(
            (r["aspects"] for r in self.part2.values() if r.get("aspects")), [])

    # ── structural self-check ──────────────────────────────────────────────
    def verify_structure(self) -> dict:
        """Re-derive the design from the code alone and compare with the
        hard-coded map. Returns {"errors": [...], "warnings": [...], "empty": [...]}.

        The .qsf says nothing about which snippets belong together, so this is a
        genuinely independent check: if a future survey is built to a different
        layout, the analysis stops instead of silently applying the wrong side
        map. `empty` lists stimuli whose code block is blank, which means a slot
        was never filled in and the judgements on it cannot be used."""
        errors, warnings, empty = [], [], []
        real = {t: v for t, v in self.part1.items() if not v["is_ac"]}
        if set(real) != set(PART1_SIDE):
            errors.append(f"Part 1 items are {sorted(real)}, "
                          f"expected {sorted(set(PART1_SIDE))}")
            return {"errors": errors, "warnings": warnings, "empty": empty}
        if set(self.part2) != set(PART2_SIDE_A) | {PART2_AC}:
            errors.append(f"Part 2 screens are {sorted(self.part2)}, "
                          f"expected {sorted(set(PART2_SIDE_A) | {PART2_AC})}")

        # ── blank stimuli: a slot that was never pasted in ─────────────────
        for tag, rec in real.items():
            if sloc(rec["code"]) == 0:
                empty.append(f"P1/{tag}")
        for key in PART2_SIDE_A:
            rec = self.part2.get(key)
            if not rec:
                continue
            for lab in ("a", "b"):
                if sloc(rec.get(f"code_{lab}", "")) == 0:
                    empty.append(f"{key}/Test{lab.upper()}")

        # ── Part 1 pairings, from the class under test alone ───────────────
        want = {}
        for pid, (o, i) in PART1_PAIRS.items():
            want[o], want[i] = i, o
        toks = {t: cut_tokens(v["code"]) for t, v in real.items()}
        self.pair_margins = {}
        for t in sorted(real):
            if not toks[t]:
                continue                      # blank, already reported
            scored = sorted(((jaccard(toks[t], toks[u]), u) for u in real if u != t),
                            reverse=True)
            self.pair_margins[t] = scored[0][0] - scored[1][0]
            if scored[0][1] != want[t]:
                by_tag = {u: j for j, u in scored}
                errors.append(
                    f"Part 1 {t}: the most similar test is {scored[0][1]} "
                    f"(J={scored[0][0]:.2f}) but the design pairs it with "
                    f"{want[t]} (J={by_tag[want[t]]:.2f})")

        # ── Part 2: the two snippets on one screen must share the CUT ──────
        self.part2_overlap = {}
        for key in PART2_SIDE_A:
            rec = self.part2.get(key)
            if not rec:
                continue
            ta, tb = cut_tokens(rec.get("code_a", "")), cut_tokens(rec.get("code_b", ""))
            o = overlap(ta, tb)
            self.part2_overlap[key] = o
            if not (ta and tb):
                continue                      # blank, already reported
            if o < OVERLAP_MIN:
                warnings.append(f"Part 2 {key}: Test A and Test B share little of the "
                                f"class under test (overlap={o:.2f}) — check that they "
                                f"are two versions of one test")
        return {"errors": errors, "warnings": warnings, "empty": empty}


# ════════════════════════ response reading ══════════════════════════════════

def read_responses(folder: Path, version: int) -> pd.DataFrame:
    """The Qualtrics export for one survey. Three header rows; data from row 4."""
    rows = None
    for z in sorted(folder.glob(f"Agent_{version}_Understandability*.zip")):
        with zipfile.ZipFile(z) as zf:
            names = [n for n in zf.namelist() if n.lower().endswith(".csv")]
            if not names:
                continue
            with zf.open(names[0]) as fh:
                rows = list(csv.reader(io.TextIOWrapper(fh, encoding="utf-8-sig")))
        break
    if rows is None:
        for c in sorted(folder.glob(f"Agent_{version}_Understandability*.csv")):
            rows = list(csv.reader(c.open(encoding="utf-8-sig")))
            break
    if rows is None or len(rows) < 4:
        return pd.DataFrame()
    header = rows[0]
    df = pd.DataFrame(rows[3:], columns=header)
    df["survey"] = version
    return df


RATING_RE = re.compile(r"^\s*(\d)")


def rating(v) -> float:
    """1-5 out of a Qualtrics label. The shipped surveys use two dash styles
    ('5 - Very easy' and '5-Very easy') and contain the typo 'Netural', so only
    the leading digit is trusted."""
    if v is None:
        return np.nan
    m = RATING_RE.match(str(v))
    return float(m.group(1)) if m else np.nan


def split_aspects(value: str, options: list[str]) -> list[str]:
    """Qualtrics joins a multi-select with commas, and three of the option
    labels CONTAIN commas ('Values or code extracted into named variables,
    fields, or helper methods'). Splitting on ',' shreds them, so the value is
    consumed by matching whole option labels, longest first."""
    s = (value or "").strip()
    if not s:
        return []
    opts = sorted(options, key=len, reverse=True)
    out, pos = [], 0
    while pos < len(s):
        for o in opts:
            if s.startswith(o, pos):
                out.append(o)
                pos += len(o)
                break
        else:
            # unknown text (an "Other" free-text spill, or a label that changed)
            nxt = s.find(",", pos)
            frag = (s[pos:] if nxt < 0 else s[pos:nxt]).strip()
            if frag:
                out.append(f"<unparsed:{frag}>")
            pos = len(s) if nxt < 0 else nxt
        while pos < len(s) and s[pos] in ", ":
            pos += 1
    return out


# ════════════════════════ build the tidy tables ═════════════════════════════

def build_tables(surveys: dict[int, Survey], responses: dict[int, pd.DataFrame]):
    """One row per respondent, per Part-1 pair and per Part-2 pair -- only the
    columns the summary needs."""
    participants, p1_pairs, p2_pairs = [], [], []

    for v, sv in sorted(surveys.items()):
        df = responses.get(v)
        if df is None or df.empty:
            continue

        # A pair with a blank side (a slot that was never pasted in) cannot be
        # judged; its rows are kept here and dropped in summarize().
        p1_ok = {pid: sloc(sv.part1[o]["code"]) > 0 and sloc(sv.part1[i]["code"]) > 0
                 for pid, (o, i) in PART1_PAIRS.items()}
        p2_ok = {}
        for key in PART2_SIDE_A:
            rec = sv.part2.get(key)
            if rec:
                p2_ok[key] = (sloc(rec.get("code_a", "")) > 0
                              and sloc(rec.get("code_b", "")) > 0)

        for _, r in df.iterrows():
            rid = r.get("ResponseId", "")
            if str(r.get("Finished", "")).lower() not in ("true", "1"):
                log.warning("survey %d: response %s is not finished", v, rid)

            # ── attention checks ──────────────────────────────────────────
            ac1 = RATING_RE.match(str(r.get(f"{PART1_AC}_1", "")))
            ac1_got = ac1.group(1) if ac1 else None
            ac1_pass = sv.ac1_required is not None and ac1_got == sv.ac1_required
            ac2_ans = str(r.get(f"{PART2_AC}_1_CHOICE", "") or "")
            ac2_pass = (sv.ac2_required is not None
                        and ac2_ans.strip().lower() == sv.ac2_required.strip().lower())
            participants.append({"survey": v, "response_id": rid,
                                 "ac1_pass": ac1_pass, "ac2_pass": ac2_pass,
                                 "ac_pass_all": ac1_pass and ac2_pass})

            # ── Part 1: the two separate ratings of one test ──────────────
            for pid, (o, i) in PART1_PAIRS.items():
                ro, ri = rating(r.get(f"{o}_1", "")), rating(r.get(f"{i}_1", ""))
                p1_pairs.append({"survey": v, "response_id": rid, "pair": pid,
                                 "rating_original": ro, "rating_improved": ri,
                                 "delta": ri - ro, "stimulus_ok": p1_ok[pid]})

            # ── Part 2: forced choice, two ratings, selected features ─────
            for key, side_a in PART2_SIDE_A.items():
                rec = sv.part2.get(key)
                if not rec:
                    continue
                side_b = "original" if side_a == "improved" else "improved"
                choice = str(r.get(f"{key}_1_CHOICE", "") or "")
                if choice.startswith("Test A"):
                    winner = side_a
                elif choice.startswith("Test B"):
                    winner = side_b
                elif choice:
                    winner = "same"
                else:
                    winner = ""
                ra, rb = rating(r.get(f"{key}_2_RATE_1")), rating(r.get(f"{key}_2_RATE_2"))
                r_imp, r_org = (ra, rb) if side_a == "improved" else (rb, ra)
                asp = split_aspects(str(r.get(f"{key}_3_ASPECT", "") or ""),
                                    rec.get("aspects") or sv.aspect_options)
                for a in asp:
                    if a.startswith("<unparsed"):
                        log.warning("survey %d, %s, %s: feature %r matches no option "
                                    "label and is not counted", v, rid, key, a)
                p2_pairs.append({"survey": v, "response_id": rid, "pair": key,
                                 "side_a": side_a, "winner": winner,
                                 "rating_original": r_org, "rating_improved": r_imp,
                                 "delta": r_imp - r_org,
                                 "aspects": tuple(a for a in asp
                                                  if not a.startswith("<unparsed")),
                                 "stimulus_ok": p2_ok.get(key, True)})

    return {"participants": pd.DataFrame(participants),
            "part1_pairs": pd.DataFrame(p1_pairs),
            "part2_pairs": pd.DataFrame(p2_pairs)}


def apply_meta(T: dict, path: Path) -> None:
    """Join each slot's LLM (`model`) and test source (`src`) from the design
    map written by identify_stimuli.py; the .qsf carries neither. Key: survey,
    part (1 or 2), slot (1..6; Part-2 screens map to slots via PART2_SLOT)."""
    m = pd.read_csv(path)
    need = ["survey", "part", "slot", "model", "src"]
    missing = [c for c in need if c not in m.columns]
    if missing:
        raise SystemExit(f"{path}: missing column(s) {missing}")
    m = m[need].copy()
    for c in ("survey", "part", "slot"):
        m[c] = pd.to_numeric(m[c], errors="coerce").astype("Int64")
    for name, part in (("part1_pairs", 1), ("part2_pairs", 2)):
        df = T[name].copy()
        df["slot"] = (df["pair"] if part == 1
                      else df["pair"].map(PART2_SLOT)).astype("Int64")
        merged = df.merge(m[m["part"] == part].drop(columns=["part"]),
                          on=["survey", "slot"], how="left", validate="m:1")
        if merged[["model", "src"]].isna().to_numpy().any():
            raise SystemExit(f"{path}: some {name} rows have no model/src entry; "
                             f"re-run identify_stimuli.py")
        T[name] = merged


# ════════════════════════ statistics ════════════════════════════════════════

def wilcoxon_paired(a, b) -> dict:
    """Wilcoxon signed-rank on paired ratings (zero differences dropped) and
    the matched-pairs rank-biserial correlation r."""
    from scipy import stats
    d = np.asarray(a, float) - np.asarray(b, float)
    d = d[~np.isnan(d)]
    nz = d[d != 0]
    out = {"n": int(len(d)), "mean_delta": float(np.mean(d)),
           "median_delta": float(np.median(d))}
    if len(nz) == 0:                       # every pair tied: nothing to test
        out.update({"W": float("nan"), "p": float("nan"), "r": float("nan")})
        return out
    res = stats.wilcoxon(nz, alternative="two-sided", zero_method="wilcox")
    ranks = stats.rankdata(np.abs(nz))
    rp, rn = ranks[nz > 0].sum(), ranks[nz < 0].sum()
    out.update({"W": float(res.statistic), "p": float(res.pvalue),
                "r": float((rp - rn) / (rp + rn))})
    return out


def cliffs_delta(x, y) -> float:
    """P(X > Y) - P(X < Y) over all cross pairs."""
    x = np.asarray(x, float)
    y = np.asarray(y, float)
    x, y = x[~np.isnan(x)][:, None], y[~np.isnan(y)][None, :]
    return float(((x > y).sum() - (x < y).sum()) / (x.size * y.size))


def krippendorff_alpha(units: list[list[float]], level: str = "ordinal") -> dict:
    """Krippendorff's alpha for one variable rated by several raters.

    `units` is one list per unit (a test version shown in one survey) holding
    the ratings it received; which rater gave which rating does not matter, and
    units differ in how many ratings they have (3-4 per survey here, minus the
    participants excluded by an attention check). Units with fewer than two
    ratings carry no agreement information and are dropped. Follows
    Krippendorff (2011), coincidence-matrix form; `level` is "ordinal" for the
    Likert ratings (the paper's choice), "nominal" and "interval" are kept for
    checking."""
    vals = sorted({v for u in units for v in u if not np.isnan(v)})
    idx = {v: k for k, v in enumerate(vals)}
    K = len(vals)
    O = np.zeros((K, K))
    kept = 0
    for u in units:
        u = [v for v in u if not np.isnan(v)]
        m = len(u)
        if m < 2:
            continue
        kept += 1
        # every ordered pair of two different ratings in the unit
        for i, a in enumerate(u):
            for j, b in enumerate(u):
                if i != j:
                    O[idx[a], idx[b]] += 1.0 / (m - 1)
    n_c = O.sum(axis=1)
    n = n_c.sum()
    if kept == 0 or n <= 1:
        return {"alpha": float("nan"), "units": kept, "ratings": int(round(n))}
    if level == "nominal":
        delta = 1.0 - np.eye(K)
    elif level == "interval":
        v = np.asarray(vals, float)
        delta = (v[:, None] - v[None, :]) ** 2
    elif level == "ordinal":
        cum = np.cumsum(n_c)
        delta = np.zeros((K, K))
        for c in range(K):
            for k in range(K):
                lo, hi = min(c, k), max(c, k)
                between = cum[hi] - (cum[lo - 1] if lo else 0.0)
                delta[c, k] = (between - (n_c[c] + n_c[k]) / 2) ** 2
    else:
        raise ValueError(level)
    D_o = (O * delta).sum() / n
    D_e = (np.outer(n_c, n_c) * delta).sum() / (n * (n - 1))
    alpha = 1.0 - D_o / D_e if D_e > 0 else float("nan")
    return {"alpha": float(alpha), "units": kept, "ratings": int(round(n))}


def fmt_p(p):
    if np.isnan(p):
        return "p = n/a"
    return "p < 0.001" if p < 0.001 else f"p = {p:.3f}"


# ════════════════════════ summary ═══════════════════════════════════════════

MODEL_ORDER = ["gpt-5.5", "opus-4.8", "sonnet-4.6"]
SRC_LABEL = {"manual": "developer-written", "auto": "EvoSuite"}


def ordered(values, preferred):
    """`preferred` first, anything else after it in sorted order."""
    vals = set(values)
    return [v for v in preferred if v in vals] + sorted(vals - set(preferred))


def rating_block(df: pd.DataFrame) -> dict:
    """Per-side mean and median, gain, Wilcoxon: the same numbers in both parts."""
    o, i = df.rating_original, df.rating_improved
    w = wilcoxon_paired(i, o)
    return {"n": len(df),
            "mean_original": float(o.mean()), "mean_improved": float(i.mean()),
            "median_original": float(o.median()), "median_improved": float(i.median()),
            "mean_gain": w["mean_delta"], "median_gain": w["median_delta"],
            "wilcoxon_W": w["W"], "wilcoxon_p": w["p"], "rank_biserial_r": w["r"]}


def rating_lines(s: dict) -> list[str]:
    return [
        f"- mean rating: original **{s['mean_original']:.2f}**, "
        f"improved **{s['mean_improved']:.2f}**",
        f"- median rating: original **{s['median_original']:g}**, "
        f"improved **{s['median_improved']:g}**",
        f"- gain (improved − original): mean **{s['mean_gain']:+.2f}**, "
        f"median **{s['median_gain']:+.2f}**",
        f"- Wilcoxon signed-rank: W = {s['wilcoxon_W']:g}, "
        f"{fmt_p(s['wilcoxon_p'])}, matched-pairs rank-biserial "
        f"r = {s['rank_biserial_r']:.2f}",
    ]


def summarize(T: dict, feature_options: list[str]) -> tuple[list[str], dict]:
    P, p1, p2 = T["participants"], T["part1_pairs"], T["part2_pairs"]
    L: list[str] = []
    res: dict = {}
    add = L.append
    add("# Human evaluation — summary\n")
    add("_Generated by `human_evaluation/analyze_human_eval.py` from "
        "`human_evaluation/FinalSurveys/`._\n")

    # ── participants ─────────────────────────────────────────────────────
    keep = set(P.loc[P.ac_pass_all, "response_id"])
    res["participants"] = {
        "total": len(P),
        "failed_attention_check": int((~P.ac_pass_all).sum()),
        "failed_part1_check": int((~P.ac1_pass).sum()),
        "failed_part2_check": int((~P.ac2_pass).sum()),
        "included": len(keep),
    }
    rp = res["participants"]
    add("## Participants\n")
    add(f"- took part: **{rp['total']}**")
    add(f"- failed an attention check: **{rp['failed_attention_check']}** "
        f"(Part-1 check: {rp['failed_part1_check']}, "
        f"Part-2 check: {rp['failed_part2_check']}) — excluded from everything below")
    add(f"- included in the evaluation: **{rp['included']}**")
    add("")

    # Excluded respondents and blank stimuli out; Part-1 pairs need both ratings.
    p1 = p1[p1.response_id.isin(keep) & p1.stimulus_ok]
    p2 = p2[p2.response_id.isin(keep) & p2.stimulus_ok]
    if not (T["part1_pairs"].stimulus_ok.all() and T["part2_pairs"].stimulus_ok.all()):
        log.warning("judgements on blank stimuli were dropped")
    if p1.delta.isna().any():
        log.warning("%d Part-1 pair(s) missing a rating were dropped",
                    int(p1.delta.isna().sum()))
        p1 = p1.dropna(subset=["delta"])
    p2r = p2.dropna(subset=["delta"])       # Part-2 judgements with both ratings

    # ── Part 1 ───────────────────────────────────────────────────────────
    s1 = rating_block(p1)
    s1.update({
        "participants": int(p1.response_id.nunique()),
        "higher": int((p1.delta > 0).sum()), "equal": int((p1.delta == 0).sum()),
        "lower": int((p1.delta < 0).sum()),
        "very_easy_original": int((p1.rating_original == 5).sum()),
        "very_easy_improved": int((p1.rating_improved == 5).sum()),
        "very_hard_original": int((p1.rating_original == 1).sum()),
        "very_hard_improved": int((p1.rating_improved == 1).sum()),
    })
    res["part1"] = s1
    add("## Part 1 — individual ratings\n")
    add(f"- paired evaluations: **{s1['n']}** from {s1['participants']} participants")
    L += rating_lines(s1)
    add(f"- improved rated higher in **{s1['higher']}**, equal in "
        f"**{s1['equal']}**, lower in **{s1['lower']}**")
    add(f"- *Very easy* (5): original {s1['very_easy_original']} → improved "
        f"{s1['very_easy_improved']}; *Very hard* (1): original "
        f"{s1['very_hard_original']} → improved {s1['very_hard_improved']}")
    add("")

    # ── Part 2 ───────────────────────────────────────────────────────────
    from scipy import stats
    dec = p2[p2.winner.isin(["improved", "original"])]
    wins = int((dec.winner == "improved").sum())
    a_imp, a_org = dec[dec.side_a == "improved"], dec[dec.side_a == "original"]
    ai_w, ao_w = int((a_imp.winner == "improved").sum()), int((a_org.winner == "improved").sum())
    fisher_p = float(stats.fisher_exact([[ai_w, len(a_imp) - ai_w],
                                         [ao_w, len(a_org) - ao_w]])[1])
    s2 = rating_block(p2r)
    s2.update({
        "comparisons": len(p2), "decisive": len(dec), "improved_preferred": wins,
        "original_preferred": len(dec) - wins,
        "about_the_same": int((p2.winner == "same").sum()),
        "no_answer": int((p2.winner == "").sum()),
        "preference_rate": wins / len(dec),
        "improved_as_A": {"wins": ai_w, "decisive": len(a_imp)},
        "improved_as_B": {"wins": ao_w, "decisive": len(a_org)},
        "position_fisher_p": fisher_p,
    })
    res["part2"] = s2
    add("## Part 2 — pairwise preference and ratings\n")
    add(f"- comparisons: **{s2['comparisons']}**; decisive: **{s2['decisive']}**; "
        f"about the same: **{s2['about_the_same']}**"
        + (f"; no answer: {s2['no_answer']}" if s2["no_answer"] else ""))
    add(f"- improved preferred in **{wins}/{len(dec)}** decisive comparisons = "
        f"**{s2['preference_rate'] * 100:.1f}%**; original preferred in "
        f"**{s2['original_preferred']}**")
    add(f"- improved shown as Test A: preferred in **{ai_w}/{len(a_imp)}**; "
        f"as Test B: **{ao_w}/{len(a_org)}**; Fisher's exact test {fmt_p(fisher_p)}")
    add(f"- ratings given with each comparison ({s2['n']} judgements):")
    L += ["  " + ln for ln in rating_lines(s2)]
    add("")

    # ── overall: both parts' ratings pooled ──────────────────────────────
    # Every Part-1 pair and every Part-2 comparison yields one original and
    # one improved rating of the same test, so the two can be pooled to state
    # the size of the improvement over all ratings collected.
    cols = ["rating_original", "rating_improved", "delta"]
    both = pd.concat([p1[cols], p2r[cols]], ignore_index=True)
    so = rating_block(both)
    so.update({
        "part1_pairs": len(p1), "part2_pairs": len(p2r),
        "higher": int((both.delta > 0).sum()), "equal": int((both.delta == 0).sum()),
        "lower": int((both.delta < 0).sum()),
    })
    res["overall"] = so
    add("## Overall — Part-1 and Part-2 ratings pooled\n")
    add(f"- paired ratings: **{so['n']}** ({so['part1_pairs']} Part-1 pairs + "
        f"{so['part2_pairs']} Part-2 comparisons)")
    L += rating_lines(so)
    add(f"- improved rated higher in **{so['higher']}**, equal in "
        f"**{so['equal']}**, lower in **{so['lower']}**")
    add("")

    # ── by LLM and by test source ────────────────────────────────────────
    add("## Differences across LLMs and test origins\n")
    add("| LLM | Part-1 mean original | Part-1 mean improved | Part-1 mean gain "
        "| Part-2 preference rate |")
    add("|---|--:|--:|--:|--:|")
    res["by_llm"] = {}
    for k in ordered(p1.model.unique(), MODEL_ORDER):
        s, d = p1[p1.model == k], dec[dec.model == k]
        dw = int((d.winner == "improved").sum())
        res["by_llm"][k] = {
            "part1_mean_original": float(s.rating_original.mean()),
            "part1_mean_improved": float(s.rating_improved.mean()),
            "part1_mean_gain": float(s.delta.mean()),
            "part2_improved_preferred": dw, "part2_decisive": len(d),
            "part2_preference_rate": dw / len(d) if len(d) else float("nan"),
        }
        e = res["by_llm"][k]
        add(f"| {k} | {e['part1_mean_original']:.2f} | {e['part1_mean_improved']:.2f} "
            f"| {e['part1_mean_gain']:+.2f} | {e['part2_preference_rate'] * 100:.1f}% "
            f"({dw}/{len(d)}) |")
    add("")
    add("| test source | Part-1 mean original | Part-1 mean improved | Part-1 mean gain |")
    add("|---|--:|--:|--:|")
    res["by_source"] = {}
    for k in ordered(p1.src.unique(), list(SRC_LABEL)):
        s = p1[p1.src == k]
        res["by_source"][k] = {
            "label": SRC_LABEL.get(k, k),
            "part1_mean_original": float(s.rating_original.mean()),
            "part1_mean_improved": float(s.rating_improved.mean()),
            "part1_mean_gain": float(s.delta.mean()),
        }
        e = res["by_source"][k]
        add(f"| {k} ({e['label']}) | {e['part1_mean_original']:.2f} | "
            f"{e['part1_mean_improved']:.2f} | {e['part1_mean_gain']:+.2f} |")
    add("")

    # The paper's table: one row per part, per test source the mean original
    # and improved rating and the Wilcoxon p-value of that paired comparison.
    srcs = ordered(p1.src.unique(), list(SRC_LABEL))
    res["by_source_table"] = {}
    for row, df in (("Individual Ratings", p1), ("Pairwise Ratings", p2r)):
        res["by_source_table"][row] = {}
        for k in srcs:
            s = df[df.src == k]
            w = wilcoxon_paired(s.rating_improved, s.rating_original)
            res["by_source_table"][row][k] = {
                "n": len(s),
                "mean_original": float(s.rating_original.mean()),
                "mean_improved": float(s.rating_improved.mean()),
                "mean_gain": w["mean_delta"], "wilcoxon_W": w["W"], "wilcoxon_p": w["p"],
            }
    add("| | " + " | ".join(f"{SRC_LABEL.get(k, k)} original | {SRC_LABEL.get(k, k)} "
                            f"improved | {SRC_LABEL.get(k, k)} p" for k in srcs) + " |")
    add("|---|" + "--:|--:|--:|" * len(srcs))
    for row, cells in res["by_source_table"].items():
        add(f"| {row} | " + " | ".join(
            f"{c['mean_original']:.2f} | {c['mean_improved']:.2f} | {fmt_p(c['wilcoxon_p'])}"
            for c in cells.values()) + " |")
    add("")

    # ── inter-rater agreement ────────────────────────────────────────────
    # Each survey was answered by 3 participants, so every test version (a
    # unit) has 3 ratings: Krippendorff's alpha (ordinal) says how similar
    # they are. The 95% CI resamples the units (fixed seed). Alpha is relative
    # to how much the units differ, so it is given pooled over both sides --
    # where agreeing that the improved version reads better already counts --
    # and per side, where only the finer gradation within a side can agree.
    def units_of(df: pd.DataFrame, side: str | None = None) -> list[list[float]]:
        long = pd.concat([
            df[["survey", "pair", "rating_original"]].rename(
                columns={"rating_original": "rating"}).assign(side="original"),
            df[["survey", "pair", "rating_improved"]].rename(
                columns={"rating_improved": "rating"}).assign(side="improved"),
        ])
        if side is not None:
            long = long[long.side == side]
        return [g.rating.tolist() for _, g in long.groupby(["survey", "pair", "side"])]

    def alpha_ci(units: list[list[float]], level: str = "ordinal",
                 B: int = 2000, seed: int = 0) -> dict:
        rng = np.random.default_rng(seed)
        n = len(units)
        reps = [krippendorff_alpha([units[i] for i in rng.integers(0, n, n)], level)["alpha"]
                for _ in range(B)]
        lo, hi = np.nanpercentile(reps, [2.5, 97.5])
        return {"ci_low": float(lo), "ci_high": float(hi), "bootstrap": B}

    def agreement_of(df: pd.DataFrame) -> dict:
        us = units_of(df)
        spread = np.array([max(u) - min(u) for u in us])
        out = {**krippendorff_alpha(us, "ordinal"), **alpha_ci(us),
               "identical": int((spread == 0).sum()),
               "within_1": int((spread <= 1).sum()),
               "within_2": int((spread <= 2).sum()),
               "max_spread": int(spread.max())}
        for side in ("original", "improved"):
            u2 = units_of(df, side)
            out[side] = {**krippendorff_alpha(u2, "ordinal"), **alpha_ci(u2)}
        return out

    # Part-2 choice: did the three participants pick the same side?
    code = {"improved": 0.0, "original": 1.0, "same": 2.0}
    pref = p2[p2.winner.isin(code)]
    pu = [g.winner.tolist() for _, g in pref.groupby(["survey", "pair"])]
    res["agreement"] = {
        "part1_ratings": agreement_of(p1),
        "part2_ratings": agreement_of(p2r),
        "part2_preference": {
            **krippendorff_alpha([[code[w] for w in u] for u in pu], "nominal"),
            "comparisons": len(pu),
            "unanimous": int(sum(len(set(u)) == 1 for u in pu)),
            "unanimous_improved": int(sum(set(u) == {"improved"} for u in pu)),
        },
    }
    ra = res["agreement"]
    add("## Inter-rater agreement\n")
    add("- Krippendorff's alpha (ordinal) over the ratings of the same test version "
        "by the 3 participants of one survey; 95% CI from "
        f"{ra['part1_ratings']['bootstrap']} bootstrap resamples of the test versions; "
        "pooled over both sides and per side")
    for label, k in (("Part-1", "part1_ratings"), ("Part-2", "part2_ratings")):
        a = ra[k]
        add(f"- {label} ratings: **α = {a['alpha']:.2f}** [{a['ci_low']:.2f}, {a['ci_high']:.2f}] "
            f"({a['units']} test versions, {a['ratings']} ratings); "
            f"original only α = {a['original']['alpha']:.2f} "
            f"[{a['original']['ci_low']:.2f}, {a['original']['ci_high']:.2f}], "
            f"improved only α = {a['improved']['alpha']:.2f} "
            f"[{a['improved']['ci_low']:.2f}, {a['improved']['ci_high']:.2f}]")
        add(f"  - the three ratings of a test version are identical in **{a['identical']}/{a['units']}**, "
            f"within one scale point in **{a['within_1']}/{a['units']}**, "
            f"within two in **{a['within_2']}/{a['units']}** (largest spread: {a['max_spread']})")
    pp = ra["part2_preference"]
    add(f"- Part-2 preference: the three participants chose the same side in "
        f"**{pp['unanimous']}/{pp['comparisons']}** comparisons "
        f"({pp['unanimous_improved']} of them for the improved version); "
        f"nominal α = {pp['alpha']:.2f}, low only because nearly every choice is "
        f"*improved*, which leaves almost no disagreement to expect by chance")
    add("")

    # ── features (Part 2) ────────────────────────────────────────────────
    n_sel = p2.aspects.map(len)
    n_part = int(p2.response_id.nunique())
    res["features"] = {"judgements": len(p2),
                       "judgements_with_a_feature": int((n_sel > 0).sum()),
                       "selections": int(n_sel.sum()), "per_feature": []}
    rows = []
    for f in feature_options + sorted(set().union(*p2.aspects) - set(feature_options)):
        cited = p2.aspects.map(lambda a, f=f: f in a)
        cr = p2r.aspects.map(lambda a, f=f: f in a)
        din, dout = p2r.delta[cr], p2r.delta[~cr]
        rows.append({
            "feature": f, "selected": int(cited.sum()),
            "pct_of_judgements": cited.mean() * 100,
            "participants": int(p2[cited].response_id.nunique()),
            "mean_gain_selected": float(din.mean()) if len(din) else float("nan"),
            "mean_gain_not_selected": float(dout.mean()) if len(dout) else float("nan"),
            "cliffs_delta": cliffs_delta(din, dout) if len(din) and len(dout) else float("nan"),
        })
    rows.sort(key=lambda r: -r["selected"])
    res["features"]["per_feature"] = rows
    rf = res["features"]
    add("## Feature impact (Part 2)\n")
    add(f"- judgements with at least one feature selected: "
        f"**{rf['judgements_with_a_feature']}/{rf['judgements']}**")
    add(f"- feature selections: **{rf['selections']}**")
    add("- gain = the Part-2 rating of the improved version minus that of the "
        "original, on the same comparison")
    add("")
    add(f"| feature | selected | % of {rf['judgements']} judgements | participants "
        f"(of {n_part}) | mean gain when selected | mean gain when not | Cliff's δ |")
    add("|---|--:|--:|--:|--:|--:|--:|")
    fmt = lambda x, spec: "—" if np.isnan(x) else format(x, spec)
    for r in rows:
        add(f"| {r['feature']} | {r['selected']} | {r['pct_of_judgements']:.1f}% | "
            f"{r['participants']} | {fmt(r['mean_gain_selected'], '+.2f')} | "
            f"{fmt(r['mean_gain_not_selected'], '+.2f')} | "
            f"{fmt(r['cliffs_delta'], '+.3f')} |")
    add("")
    return L, res


# ════════════════════════ main ══════════════════════════════════════════════

def main() -> None:
    ap = argparse.ArgumentParser(description=__doc__,
                                 formatter_class=argparse.RawDescriptionHelpFormatter)
    ap.add_argument("--surveys", nargs="*", type=int, default=None,
                    help="only these survey versions (default: every one found)")
    ap.add_argument("--survey-dir", type=Path, default=SURVEY_DIR)
    ap.add_argument("--out", type=Path, default=OUT_DIR)
    ap.add_argument("--meta", type=Path, default=META_FILE,
                    help="design map from identify_stimuli.py, for the LLM and "
                         "test-source breakdown (default: %(default)s)")
    ap.add_argument("-v", "--verbose", action="store_true")
    args = ap.parse_args()
    logging.basicConfig(level=logging.DEBUG if args.verbose else logging.INFO,
                        format="%(levelname)s %(message)s")

    if not args.survey_dir.is_dir():
        raise SystemExit(f"{args.survey_dir} not found")
    if not args.meta.is_file():
        raise SystemExit(f"{args.meta} not found; run identify_stimuli.py first")

    surveys, responses = {}, {}
    for qsf in sorted(args.survey_dir.glob("Agent_*_Understandability.qsf")):
        m = re.search(r"Agent_(\d+)_", qsf.name)
        if not m:
            continue
        v = int(m.group(1))
        if args.surveys and v not in args.surveys:
            continue
        sv = Survey(qsf, v)
        # Guard, not a result: the hard-coded side map must match what shipped.
        chk = sv.verify_structure()
        for e in chk["errors"]:
            log.error("survey %d: %s", v, e)
        for wn in chk["warnings"]:
            log.warning("survey %d: %s", v, wn)
        for em in chk["empty"]:
            log.error("survey %d: BLANK stimulus %s — the slot was never filled in; "
                      "judgements on it are dropped", v, em)
        df = read_responses(args.survey_dir, v)
        if df.empty:
            log.info("survey %-2d  responses not collected yet", v)
            continue
        surveys[v], responses[v] = sv, df
        log.info("survey %-2d  %d response(s)", v, len(df))

    if not surveys:
        raise SystemExit("no survey with responses found")
    option_lists = {tuple(sv.aspect_options) for sv in surveys.values()}
    if len(option_lists) > 1:
        log.warning("the feature option list differs between survey versions")
    feature_options = list(surveys[min(surveys)].aspect_options)

    T = build_tables(surveys, responses)
    apply_meta(T, args.meta)
    lines, res = summarize(T, feature_options)

    args.out.mkdir(parents=True, exist_ok=True)
    (args.out / "results.md").write_text("\n".join(lines), encoding="utf-8")
    (args.out / "results.json").write_text(
        json.dumps(res, indent=2, default=lambda o: o.item() if hasattr(o, "item") else str(o)),
        encoding="utf-8")
    print("\n".join(lines))
    log.info("results -> %s", args.out / "results.md")


if __name__ == "__main__":
    main()
