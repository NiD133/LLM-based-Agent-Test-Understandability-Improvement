#!/usr/bin/env python3
"""analyse_stability_results.py — stability of the improvement across repeated runs (RQ1).

    python3 stability_check/analyse_stability_results.py              # feature analysis of run1/run2 if needed, then the report
    python3 stability_check/analyse_stability_results.py --skip-run   # report only, from the existing structural diffs

Three runs of the same 362 test cases × three models: the main study
(data/improved/, "main") and two re-runs (results/run1, results/run2).

Two questions, two tables, written to results/stability.md and results/stability.json:

  1. Behaviour preservation — how many re-run improvements pass the exact-match
     check (same covered lines, same covered branches, same killed mutants),
     from each re-run's metrics.json.
  2. Consistency — for every (model, case) and every pair of runs, the Jaccard
     similarity of the sets of feature changes, where two changes are the same
     when they have the same feature type AND affect the same element of the
     original test (the paper's "element-level" rule). A change is a
     RefactoringMiner refactoring or a GumTree comment edit, anchored on the
     original file; both tools report positions on that file.

The structural diffs come from feature_analysis/feature_analysis.py: the main
run's from feature_analysis/out/feature_analysis/diff/, the re-runs' from
results/analysis/<run>/diff/ (computed here on first use).
"""
from __future__ import annotations

import argparse
import bisect
import json
import re
import sys
from collections import Counter, defaultdict
from dataclasses import dataclass
from datetime import datetime
from itertools import combinations
from pathlib import Path
from statistics import mean

HERE = Path(__file__).resolve().parent
PROJECT_ROOT = HERE.parent
sys.path.insert(0, str(PROJECT_ROOT / "feature_analysis"))
import feature_analysis as fa                              # noqa: E402
from lib import javaparser_blocks as jp                    # noqa: E402
from lib.feature_registry import canonical_feature         # noqa: E402
from lib.refactoringminer import _map_refactoring_type     # noqa: E402

# ============================== CONFIG ======================================
SAMPLE = HERE / "data" / "sample_362.json"
MODELS = ["opus-4.8", "sonnet-4.6", "gpt-5.5"]
MODEL_NAMES = {"opus-4.8": "Claude Opus 4.8", "sonnet-4.6": "Claude Sonnet 4.6", "gpt-5.5": "GPT-5.5"}
RUNS = {                                   # run name -> <model>/<project>/<src>/testcases/<Suite>/<case>/
    "main": PROJECT_ROOT / "agent_improvement" / "data" / "improved",
    "run1": HERE / "results" / "run1",
    "run2": HERE / "results" / "run2",
}
DIFFS = {                                  # run name -> structural_diff root (feature_analysis output)
    "main": PROJECT_ROOT / "feature_analysis" / "out" / "feature_analysis" / "diff",
    "run1": HERE / "results" / "analysis" / "run1" / "diff",
    "run2": HERE / "results" / "analysis" / "run2" / "diff",
}
OUT = HERE / "results"
# ============================================================================

WIDE_TYPES = {"METHOD_DECLARATION", "TYPE_DECLARATION", "ANONYMOUS_CLASS_DECLARATION",
              "LAMBDA_EXPRESSION", "BLOCK"}
SPAN_RE = re.compile(r"\[(\d+),(\d+)\]")
TYPE_DECL_RE = re.compile(
    r"^\s*(?:@[\w.]+(?:\([^)]*\))?\s+)*"
    r"(?:(?:public|protected|private|static|final|abstract|sealed|non-sealed|strictfp)\s+)*"
    r"(?:class|interface|enum|record)\s+[A-Za-z_$][\w$]*")


# ── original-file index (line starts + method ranges via JavaParser) ──────────
class Original:
    def __init__(self, path: Path, methods: list[dict]):
        self.text = path.read_text(encoding="utf-8", errors="replace", newline="")
        self.line_starts = [0] + [m.end() for m in re.finditer("\n", self.text)]
        self.lines = self.text.split("\n")
        self.methods = [(m["method_begin_line"], m["method_end_line"], m["name"])
                        for m in methods if m.get("name") != "<parse-error>" and m["method_begin_line"] > 0]
        seen: Counter = Counter()
        self._line_key: dict[int, str] = {}
        for i, raw in enumerate(self.lines, 1):
            t = " ".join(raw.split())
            seen[t] += 1
            self._line_key[i] = t if seen[t] == 1 else f"{t} #{seen[t]}"

    def line(self, offset: int) -> int:
        return bisect.bisect_right(self.line_starts, offset)

    def line_key(self, n: int) -> str:
        return "@" + self._line_key.get(n, f"line {n}")

    def method_at(self, start: int, end: int | None = None) -> str:
        end = start if end is None else end
        if 1 <= start <= len(self.lines) and TYPE_DECL_RE.match(self.lines[start - 1]):
            return "<class>"
        inside = [(e - b, b, n) for b, e, n in self.methods if b <= start <= e]
        if inside:
            _, b, n = min(inside)
            return f"{n}@{b}"
        after = [(b, n) for b, e, n in self.methods if end < b <= end + 3]
        if after:
            b, n = min(after)
            return f"{n}@{b}"
        return "<class>"


def index_originals(paths: list[Path], work: Path) -> dict[str, Original]:
    jar = Path(fa.JAVAPARSER_JAR)
    java_home = fa.JAVA_HOME or fa._detect_java_home(21, fa._JDK_ENV_VARS)
    cp = jp.compile_helper(work / "_javaparser_helper", jar, java_home)
    methods = jp.run_javaparser_batch(paths, cp, java_home)
    return {str(p): Original(p, methods.get(str(p), [])) for p in paths}


# ── changes ───────────────────────────────────────────────────────────────────
@dataclass(frozen=True)
class Change:
    tool: str
    feature: str
    method: str      # enclosing original method ("name@line") or "<class>"
    element: str     # the original element the change affects

    @property
    def key(self) -> tuple:
        return (self.feature, self.method, self.element)


def _norm(s) -> str:
    return " ".join(str(s or "").split())


def rm_changes(raw_path: Path, orig: Original) -> list[Change]:
    try:
        raw = json.loads(raw_path.read_text(encoding="utf-8"))
    except Exception:
        return []
    out = {}
    for commit in raw.get("commits", []) or []:
        for r in commit.get("refactorings", []) or []:
            feature = canonical_feature(_map_refactoring_type(str(r.get("type"))))
            left = [l for l in (r.get("leftSideLocations") or []) if l.get("startLine") is not None]
            if not left:
                continue
            named = [l for l in left if l.get("codeElement") and l["codeElementType"] not in WIDE_TYPES]
            narrow = [l for l in left if l["codeElementType"] not in WIDE_TYPES]
            if named:
                loc = named[0]
                start, end, element = loc["startLine"], loc["endLine"], _norm(loc["codeElement"])
            elif narrow:
                start, end = min(l["startLine"] for l in narrow), max(l["endLine"] for l in narrow)
                element = ""
            else:
                loc = left[0]
                start, end = loc["startLine"], loc["startLine"]
                element = _norm(loc.get("codeElement"))
            if not element:
                element = orig.line_key(start)
            c = Change("refactoringminer", feature, orig.method_at(start, end), element)
            out[(c.feature, start, end, c.element, _norm(r.get("description"))[:300])] = c
    return list(out.values())


def _comment_feature(tree: str) -> str | None:
    if "LineComment" in tree:
        return "Line Comment change"
    if "BlockComment" in tree:
        return "Block Comment change"
    if "Javadoc" in tree:
        return "Javadoc change"
    return None


def _span(label: str):
    m = None
    for m in SPAN_RE.finditer(label):
        pass
    return (int(m.group(1)), int(m.group(2))) if m else None


def gt_changes(diff_path: Path, orig: Original) -> list[Change]:
    try:
        raw = json.loads(diff_path.read_text(encoding="utf-8"))
    except Exception:
        return []
    matches = []
    for m in raw.get("matches", []) or []:
        s, d = _span(str(m.get("src", ""))), _span(str(m.get("dest", "")))
        if s and d:
            matches.append((d[0], d[1], s[0], s[1]))
    out = {}
    for a in raw.get("actions", []) or []:
        tree = str(a.get("tree", ""))
        feature, span = _comment_feature(tree), _span(tree)
        if not feature or not span:
            continue
        action = str(a.get("action", ""))
        if action.startswith("insert"):
            s, e = span
            enclosing = [m for m in matches if m[0] <= s and e <= m[1] and (m[1] - m[0]) > (e - s)]
            if not enclosing:
                continue
            enc = min(enclosing, key=lambda m: m[1] - m[0])
            following = [m for m in matches if e <= m[0] < enc[1]]
            if following:
                first = min(m[0] for m in following)
                nxt = max((m for m in following if m[0] == first), key=lambda m: m[1] - m[0])
                line = orig.line(nxt[2])
            else:
                line = orig.line(max(enc[3] - 1, enc[2]))
            start = end = line
        else:
            start, end = orig.line(span[0]), orig.line(max(span[1] - 1, span[0]))
        c = Change("gumtree", feature, orig.method_at(start, end), orig.line_key(start))
        out[(c.feature, start, end, f"{action}: {tree[:160]}")] = c
    return list(out.values())


def jaccard(a: list[Change], b: list[Change]):
    ka, kb = {c.key for c in a}, {c.key for c in b}
    union = ka | kb
    return (len(ka & kb) / len(union)) if union else None


# ── data access ───────────────────────────────────────────────────────────────
def improved_java(leaf: Path) -> Path | None:
    for o in sorted((leaf / "original").glob("*.java")):
        if (leaf / o.name).exists():
            return leaf / o.name
    return None


def is_exact(leaf: Path):
    mp = leaf / "metrics.json"
    if not mp.exists():
        return None
    try:
        m = json.loads(mp.read_text(encoding="utf-8"))
    except Exception:
        return None
    v = (m.get("comparison") or {}).get("verdict") or {}
    return bool(v.get("is_exact_match")) if v else None


# ── main ──────────────────────────────────────────────────────────────────────
def main() -> int:
    ap = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    ap.add_argument("--jobs", type=int, default=fa.JOBS)
    ap.add_argument("--skip-run", action="store_true", help="do not (re)run feature_analysis on the re-runs")
    args = ap.parse_args()

    sample = json.loads(SAMPLE.read_text(encoding="utf-8"))["targets"]
    leaves = [t["leaf_rel"] for t in sample]
    for name, root in RUNS.items():
        if not root.is_dir():
            raise SystemExit(f"run {name!r}: {root} does not exist")
    if not args.skip_run:
        for name in ("run1", "run2"):
            print(f"[{name}] feature analysis of {RUNS[name]}", flush=True)
            fa.run(RUNS[name], DIFFS[name].parent, jobs=args.jobs, filter_substr=None, exclude=set())
    OUT.mkdir(parents=True, exist_ok=True)

    # 1. behaviour preservation of the re-runs
    behaviour = {}
    for run in ("run1", "run2"):
        for m in MODELS:
            n_exact = n_total = 0
            for rel in leaves:
                e = is_exact(RUNS[run] / m / rel)
                if e is None:
                    continue
                n_total += 1
                n_exact += e
            behaviour[(run, m)] = (n_exact, n_total)

    # 2. originals + changes per (model, case, run)
    originals, problems = {}, Counter()
    for m in MODELS:
        for rel in leaves:
            files = {}
            for run, root in RUNS.items():
                leaf = root / m / rel
                imp = improved_java(leaf)
                if imp is None:
                    problems[f"{run}: improved/original file missing"] += 1
                    continue
                files[run] = leaf / "original" / imp.name
            if len({f.read_bytes() for f in files.values()}) != 1:
                problems["original differs between runs (case skipped)"] += 1
                continue
            if len(files) >= 2:
                originals[(m, rel)] = next(iter(files.values()))
    idx = index_originals(sorted(set(originals.values())), OUT / "analysis")

    changes: dict[tuple, dict[str, list[Change]]] = defaultdict(dict)
    for (m, rel), orig_path in originals.items():
        orig = idx[str(orig_path)]
        for run in RUNS:
            sd = DIFFS[run] / m / rel / "structural_diff"
            if not (sd / "refactoringminer_raw.json").exists() or not (sd / "gumtree_comments_diff.json").exists():
                problems[f"{run}: structural_diff missing"] += 1
                continue
            changes[(m, rel)][run] = (rm_changes(sd / "refactoringminer_raw.json", orig)
                                      + gt_changes(sd / "gumtree_comments_diff.json", orig))

    # 3. pairwise Jaccard, mean per model (over pairs where at least one run changed something)
    per_pair_rows = []
    js_by_model: dict[str, list[float]] = defaultdict(list)
    js_by_model_pair: dict[tuple, list[float]] = defaultdict(list)
    for (m, rel), per in sorted(changes.items()):
        for ra, rb in combinations(sorted(per), 2):
            j = jaccard(per[ra], per[rb])
            per_pair_rows.append({"model": m, "case": rel, "run_a": ra, "run_b": rb,
                                  "changes_a": len(per[ra]), "changes_b": len(per[rb]), "jaccard": j})
            if j is not None:
                js_by_model[m].append(j)
                js_by_model_pair[(m, ra, rb)].append(j)
    all_js = [j for js in js_by_model.values() for j in js]

    # 4. write
    pairs = sorted({(r["run_a"], r["run_b"]) for r in per_pair_rows})
    result = {
        "generated": datetime.now().isoformat(timespec="seconds"),
        "sample": {"file": str(SAMPLE.relative_to(PROJECT_ROOT)), "n_cases": len(leaves)},
        "runs": {k: str(v.relative_to(PROJECT_ROOT)) for k, v in RUNS.items()},
        "behaviour_preservation": {
            "rule": "exact match: same covered lines, same covered branches, same killed mutants",
            "per_run_model": {f"{run}/{m}": {"exact": e, "total": t} for (run, m), (e, t) in behaviour.items()},
            "per_model": {m: {"exact": sum(behaviour[(r, m)][0] for r in ("run1", "run2")),
                              "total": sum(behaviour[(r, m)][1] for r in ("run1", "run2"))} for m in MODELS},
            "total": {"exact": sum(e for e, _ in behaviour.values()), "total": sum(t for _, t in behaviour.values())},
        },
        "consistency": {
            "rule": "two changes are the same when they have the same feature type and affect the same "
                    "element of the original test; Jaccard = |same| / |distinct changes in either run|; "
                    "mean over (case, run pair) comparisons where at least one run changed something",
            "per_model": {m: {"mean_jaccard": round(mean(js_by_model[m]), 4), "comparisons": len(js_by_model[m]),
                              "per_run_pair": {f"{a}-{b}": round(mean(js_by_model_pair[(m, a, b)]), 4)
                                               for a, b in pairs if js_by_model_pair[(m, a, b)]}}
                          for m in MODELS},
            "all_models": {"mean_jaccard": round(mean(all_js), 4), "comparisons": len(all_js)},
        },
        "problems": dict(problems),
        "comparisons": per_pair_rows,
    }
    (OUT / "stability.json").write_text(json.dumps(result, indent=2, ensure_ascii=False), encoding="utf-8")

    bp = result["behaviour_preservation"]
    L = ["# Stability of the improvement across three runs (362 test cases per model)", "",
         "## Behaviour preservation of the re-runs", "",
         "| Model | run1 | run2 | both re-runs |", "|---|--:|--:|--:|"]
    for m in MODELS:
        cells = []
        for run in ("run1", "run2"):
            e, t = behaviour[(run, m)]
            cells.append(f"{e}/{t}")
        pm = bp["per_model"][m]
        cells.append(f"{pm['exact']}/{pm['total']} ({100 * pm['exact'] / pm['total']:.2f}%)")
        L.append(f"| {MODEL_NAMES[m]} | " + " | ".join(cells) + " |")
    tot = bp["total"]
    L.append(f"| All | | | {tot['exact']}/{tot['total']} ({100 * tot['exact'] / tot['total']:.2f}%) |")
    L += ["", "## Consistency of the changes (element-level Jaccard)", "",
          "| Model | " + " | ".join(f"{a} vs {b}" for a, b in pairs) + " | all pairs | comparisons |",
          "|---|" + "--:|" * (len(pairs) + 2)]
    for m in MODELS:
        c = result["consistency"]["per_model"][m]
        L.append(f"| {MODEL_NAMES[m]} | " + " | ".join(f"{c['per_run_pair'].get(f'{a}-{b}', float('nan')):.3f}" for a, b in pairs)
                 + f" | **{c['mean_jaccard']:.3f}** | {c['comparisons']} |")
    am = result["consistency"]["all_models"]
    L.append(f"| All | " + " | ".join(f"{mean([j for (mm, a_, b_), js in js_by_model_pair.items() if (a_, b_) == (a, b) for j in js]):.3f}" for a, b in pairs)
             + f" | **{am['mean_jaccard']:.3f}** | {am['comparisons']} |")
    (OUT / "stability.md").write_text("\n".join(L) + "\n", encoding="utf-8")
    print("\n".join(L))
    if problems:
        print("\nproblems:", dict(problems))
    print(f"\nwrote {OUT / 'stability.md'} and {OUT / 'stability.json'}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
