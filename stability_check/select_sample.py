#!/usr/bin/env python3
"""select_sample.py — draw the stability-check sample (362 test cases).

    python3 stability_check/select_sample.py              # writes data/sample_362.json + sample_summary.txt
    python3 stability_check/select_sample.py --dry-run    # print the statistics only

POPULATION  the case-level improvements of the main study, agent_improvement/data/improved/<model>/
            for the three models (5,760 cases each).

ELIGIBLE    a case is kept only if ALL of these hold, for every model:
              1. the same original test was given to every model (byte-identical);
              2. the split under data/original/ exists and is that same file;
              3. metrics.json exists, compile_status == COMPILE_SUCCESS, the
                 baseline counts are complete, PIT ran on the improved side,
                 and comparison.verdict.is_exact_match is true;
              4. the baseline metrics file next to the split carries the same
                 counts as the baseline embedded in metrics.json;
              5. the class is in data/dataset.json.
            → 5,202 of 5,760.

SAMPLE      N_FULL = 362 (95 % confidence, ±5 % margin for a population of
            6,040). Deterministic with SEED: cases are allocated to
            project × origin strata proportionally (largest remainder), each
            stratum is sorted by SLOC and cut into as many segments as it has
            seats, one case per segment, preferring the test class and the CUT
            used least so far; ties broken by a seeded random key. A nested
            pilot of N_PILOT = 100 is drawn the same way (it only fixes the
            order of the output; every case of the sample is used).
"""
from __future__ import annotations

import argparse
import hashlib
import json
import random
import re
from collections import Counter, defaultdict
from datetime import datetime
from pathlib import Path

HERE = Path(__file__).resolve().parent
PROJECT_ROOT = HERE.parent
DATA = PROJECT_ROOT / "agent_improvement" / "data"
IMPROVED = DATA / "improved"
ORIGINAL = DATA / "original"
DATASET = DATA / "dataset.json"
OUT_DIR = HERE / "data"

MODELS = ["opus-4.8", "sonnet-4.6", "gpt-5.5"]
SEED = 20260916
N_FULL = 362
N_PILOT = 100
SLOC_BINS = [(0, 10), (11, 15), (16, 30), (31, 60), (61, 10 ** 9)]
SRC_TO_CATEGORY = {"manual": "manual_cases", "auto": "auto_cases"}


# ── helpers ───────────────────────────────────────────────────────────────

def rel(p: Path) -> str:
    try:
        return str(Path(p).relative_to(PROJECT_ROOT))
    except ValueError:
        return str(p)


def sha1_file(p: Path) -> str | None:
    try:
        return hashlib.sha1(p.read_bytes()).hexdigest()
    except Exception:
        return None


def norm_bytes(p: Path) -> bytes:
    return p.read_bytes().replace(b"\r\n", b"\n")


def sloc(p: Path) -> int:
    """Non-blank, non-comment lines, excluding package/import lines."""
    n, in_block = 0, False
    for raw in p.read_text(encoding="utf-8", errors="replace").splitlines():
        line = raw.strip()
        if in_block:
            if "*/" in line:
                in_block = False
            continue
        if not line or line.startswith("//"):
            continue
        if line.startswith("/*"):
            if "*/" not in line:
                in_block = True
            continue
        if line.startswith(("import ", "package ")):
            continue
        n += 1
    return n


def sloc_bin(n: int) -> str:
    for lo, hi in SLOC_BINS:
        if lo <= n <= hi:
            return f"{lo}-{hi}" if hi < 10 ** 9 else f">{lo - 1}"
    return "?"


def fqn_from_class_path(class_path: str) -> str:
    m = re.search(r"src/[^/]+/java/(.+)\.java$", class_path)
    return m.group(1).replace("/", ".") if m else Path(class_path).stem


def load_dataset_index() -> dict:
    idx = {}
    for entry in json.loads(DATASET.read_text(encoding="utf-8")):
        pid = entry["repo"].split("/")[-1]
        by_fqn = {}
        for cp, tp in zip(entry["class_paths"], entry["test_paths"]):
            by_fqn.setdefault(fqn_from_class_path(cp), (cp, tp))
        idx[pid] = {"repo": entry["repo"], "by_fqn": by_fqn}
    return idx


def raw_counts(block: dict) -> tuple:
    return tuple(block.get(k) for k in
                 ("line_covered", "branch_covered", "mutants_total", "mutants_killed"))


def largest_remainder(total: int, weights: dict) -> dict:
    W = sum(weights.values())
    quota = {k: total * w / W for k, w in weights.items()}
    seats = {k: int(q) for k, q in quota.items()}
    left = total - sum(seats.values())
    for k in sorted(quota, key=lambda k: (-(quota[k] - seats[k]), -weights[k], k))[:left]:
        seats[k] += 1
    return seats


def segments(items: list, n: int) -> list[list]:
    size, extra = divmod(len(items), n)
    out, i = [], 0
    for s in range(n):
        j = i + size + (1 if s < extra else 0)
        out.append(items[i:j])
        i = j
    return out


# ── eligibility ───────────────────────────────────────────────────────────

def build_pool() -> tuple[list[dict], Counter]:
    ds = load_dataset_index()
    why = Counter()
    pool = []
    leaves = sorted(p for p in (IMPROVED / MODELS[0]).glob("*/*/testcases/*/*") if p.is_dir())
    for leaf in leaves:
        leaf_rel = str(leaf.relative_to(IMPROVED / MODELS[0]))
        project_id, src, _, suite, case = leaf_rel.split("/")
        test_id = f"{suite}_{case}"

        origs = []
        for m in MODELS:
            got = sorted((IMPROVED / m / leaf_rel / "original").glob("*.java"))
            origs.append(got[0] if got else None)
        if any(o is None for o in origs):
            why["missing in a model"] += 1
            continue
        if len({norm_bytes(o) for o in origs}) != 1:
            why["input differs between models"] += 1
            continue

        split = ORIGINAL / project_id / src / "testcases" / suite / f"{test_id}.java"
        if not split.exists():
            why["no split under data/original"] += 1
            continue
        if norm_bytes(split) != norm_bytes(origs[0]):
            why["data/original differs from the model input"] += 1
            continue

        blobs, states, reason = {}, {}, None
        for m in MODELS:
            mp, sp = IMPROVED / m / leaf_rel / "metrics.json", IMPROVED / m / leaf_rel / "state.json"
            if not mp.exists():
                reason = "metrics.json missing"
                break
            j = json.loads(mp.read_text(encoding="utf-8"))
            b, i = j.get("baseline") or {}, j.get("improved") or {}
            if j.get("compile_status") != "COMPILE_SUCCESS":
                reason = "not COMPILE_SUCCESS"
                break
            if any(v is None for v in raw_counts(b)):
                reason = "incomplete baseline"
                break
            if i.get("mutants_total") is None:
                reason = "no PIT data on the improved side"
                break
            if not ((j.get("comparison") or {}).get("verdict") or {}).get("is_exact_match"):
                reason = "not exact-match for every model"
                break
            blobs[m] = j
            states[m] = json.loads(sp.read_text(encoding="utf-8")) if sp.exists() else {}
        if reason:
            why[reason] += 1
            continue

        canon = split.with_name(f"{test_id}_metrics.json")
        if not canon.exists() or raw_counts(json.loads(canon.read_text(encoding="utf-8"))) \
                != raw_counts(next(iter(blobs.values()))["baseline"]):
            why["baseline file next to the split disagrees with metrics.json"] += 1
            continue

        state = next(iter(states.values()))
        class_name = state.get("target_class")
        entry = ds.get(project_id)
        pair = entry["by_fqn"].get(class_name) if entry and class_name else None
        if not pair:
            why["class not in data/dataset.json"] += 1
            continue
        class_path, test_path = pair
        scaffolding = None
        if src == "auto":
            raw = (DATA / "baseline" / project_id / "_evosuite_raw")
            hits = list(raw.rglob(f"{suite}_scaffolding.java"))
            scaffolding = rel(hits[0]) if hits else None

        pool.append({
            "leaf_rel": leaf_rel,
            "project_id": project_id,
            "repo": entry["repo"],
            "src": src,
            "test_category": SRC_TO_CATEGORY[src],
            "suite_class": suite,
            "case_name": case,
            "test_id": test_id,
            "class_path": class_path,
            "test_path": test_path,
            "class_name": class_name,
            "original_split": rel(split),
            "scaffolding": scaffolding,
            "sloc": sloc(split),
        })
    why["ELIGIBLE"] = len(pool)
    why["population"] = len(leaves)
    return pool, why


# ── sampling ──────────────────────────────────────────────────────────────

def draw(pool: list[dict]):
    rng = random.Random(SEED)
    strata = defaultdict(list)
    for c in pool:
        c["_key"] = rng.random()
        strata[(c["project_id"], c["src"])].append(c)
    sizes = {s: len(v) for s, v in strata.items()}
    alloc_full = largest_remainder(N_FULL, sizes)
    alloc_pilot = largest_remainder(N_PILOT, sizes)
    for s in sorted(alloc_pilot):
        while alloc_pilot[s] > alloc_full[s]:
            donor = max((d for d in alloc_full if alloc_full[d] > alloc_pilot[d]),
                        key=lambda d: (alloc_full[d] - alloc_pilot[d], sizes[d]))
            alloc_full[donor] -= 1
            alloc_full[s] += 1

    picked_full, picked_pilot = [], []
    for s in sorted(strata):
        cases = sorted(strata[s], key=lambda c: (c["sloc"], c["_key"]))
        suite_use, class_use = Counter(), Counter()
        chosen = []
        for seg in segments(cases, alloc_full[s]) if alloc_full[s] else []:
            c = min(seg, key=lambda c: (suite_use[c["suite_class"]], class_use[c["class_name"]], c["_key"]))
            suite_use[c["suite_class"]] += 1
            class_use[c["class_name"]] += 1
            chosen.append(c)
        picked_full += chosen

        chosen = sorted(chosen, key=lambda c: (c["sloc"], c["_key"]))
        used, taken = set(), set()
        for seg in segments(chosen, alloc_pilot[s]) if alloc_pilot[s] else []:
            free = [c for c in seg if id(c) not in taken]
            fresh = [c for c in free if c["suite_class"] not in used]
            if not fresh:
                mid = seg[len(seg) // 2]["sloc"]
                fresh = sorted((c for c in chosen if c["suite_class"] not in used and id(c) not in taken),
                               key=lambda c: (abs(c["sloc"] - mid), c["_key"]))[:1]
            c = min(fresh or free, key=lambda c: c["_key"])
            used.add(c["suite_class"])
            taken.add(id(c))
            picked_pilot.append(c)

    pilot_ids = {id(c) for c in picked_pilot}
    rest = [c for c in picked_full if id(c) not in pilot_ids]
    rng.shuffle(picked_pilot)
    rng.shuffle(rest)
    rows = []
    for c in picked_pilot + rest:
        row = {k: v for k, v in c.items() if not k.startswith("_")}
        row["stratum"] = f"{c['project_id']}/{c['src']}"
        row["sloc_bin"] = sloc_bin(c["sloc"])
        # fingerprints of everything the agent reads, so run_stability.py can
        # refuse to run when an input changed since the sample was drawn
        row["original_sha1"] = sha1_file(PROJECT_ROOT / row["original_split"])
        row["cut_sha1"] = sha1_file(PROJECT_ROOT / "local_workplace" / row["project_id"] / row["class_path"])
        if row.get("scaffolding"):
            row["scaffolding_sha1"] = sha1_file(PROJECT_ROOT / row["scaffolding"])
        rows.append(row)
    return rows, alloc_full, sizes


def pct(n, d):
    return f"{100 * n / d:5.1f}%" if d else "  —  "


def summarise(pool, rows, sizes) -> str:
    L = [f"\n{'project':22} {'pool':>6} {'share':>6} │ {'sample':>6} {'share':>6}"]
    projects = sorted({s[0] for s in sizes}, key=lambda p: -sum(v for s, v in sizes.items() if s[0] == p))
    for p in projects:
        n_pool = sum(v for s, v in sizes.items() if s[0] == p)
        n_full = sum(1 for r in rows if r["project_id"] == p)
        L.append(f"{p:22} {n_pool:6} {pct(n_pool, len(pool))} │ {n_full:6} {pct(n_full, len(rows))}")
    L.append("")
    for label, key in (("origin", "src"), ("SLOC", "sloc_bin")):
        vals = ["auto", "manual"] if key == "src" else [sloc_bin(lo) for lo, _ in SLOC_BINS]
        L.append(f"{label:22} " + "  ".join(f"{v:>8}" for v in vals))
        for name, grp in (("pool", pool), ("sample", rows)):
            cnt = Counter((r[key] if key in r else sloc_bin(r["sloc"])) for r in grp)
            L.append(f"  {name:20} " + "  ".join(f"{pct(cnt[v], len(grp)):>8}" for v in vals))
    suites = Counter((r["project_id"], r["src"], r["suite_class"]) for r in rows)
    classes = Counter((r["project_id"], r["class_name"]) for r in rows)
    L += ["", f"sample {len(rows)} cases · {len({r['project_id'] for r in rows})} projects · "
              f"{len(suites)} test classes (max {max(suites.values())}/class) · "
              f"{len(classes)} CUT classes (max {max(classes.values())}/class) · "
              f"SLOC median {sorted(r['sloc'] for r in rows)[len(rows) // 2]}"]
    return "\n".join(L)


def main() -> int:
    ap = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    ap.add_argument("--dry-run", action="store_true", help="print the statistics, write nothing")
    ap.add_argument("--force", action="store_true", help="overwrite an existing sample file")
    args = ap.parse_args()

    out_p = OUT_DIR / f"sample_{N_FULL}.json"
    if not args.dry_run and not args.force and out_p.exists():
        raise SystemExit(f"{rel(out_p)} exists — the sample is frozen; use --force to redraw")

    pool, why = build_pool()
    n_population = why.pop("population")
    print(f"population {n_population} → eligible {why.pop('ELIGIBLE')}")
    for reason, n in why.most_common():
        print(f"  {n:5}  {reason}")
    rows, alloc, sizes = draw(pool)
    report = summarise(pool, rows, sizes)
    print(report)
    if args.dry_run:
        return 0
    OUT_DIR.mkdir(parents=True, exist_ok=True)
    out_p.write_text(json.dumps({
        "_meta": {
            "generated": datetime.now().isoformat(timespec="seconds"),
            "population": "data/improved/<model>/*/*/testcases, " + ", ".join(MODELS),
            "n_population": n_population, "n_eligible": len(pool), "excluded": dict(why),
            "seed": SEED, "n_cases": len(rows),
        },
        "targets": rows,
    }, indent=2), encoding="utf-8")
    (OUT_DIR / "sample_summary.txt").write_text(report + "\n", encoding="utf-8")
    print(f"wrote {rel(out_p)} ({len(rows)} cases) and {rel(OUT_DIR / 'sample_summary.txt')}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
