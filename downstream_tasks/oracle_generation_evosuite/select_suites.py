#!/usr/bin/env python3
"""Select the 50 EvoSuite suites used in the RQ4 oracle-generation studies.

Deterministic, no random seed. Three steps:

1. Candidate pool - every EvoSuite suite (src = auto, gran = testsuites) whose
   sonnet-4.6 improvement preserves behaviour (is_exact_match in
   agent_improvement/data/improved/summary/per_test.csv), together with its
   original suite (agent_improvement/data/original) and EvoSuite scaffolding
   (agent_improvement/data/baseline/<project>/_evosuite_raw).  127 suites.
2. Screen - compile and run every ORIGINAL suite once (javac + JUnitCore under
   the EvoSuite runtime agent, JDK 11); a suite whose original does not compile
   or does not pass is unusable as a subject.  126 suites pass
   (StrMatcher_ESTest does not compile).
3. Stratified allocation - per project quota = max(1, min(6, round(share * 50))),
   then adjusted to exactly 50 by adding to (or removing from) the largest pools
   first; within a project the suites are sorted by LOC and picked at evenly
   spaced ranks, so each project contributes its smallest, largest and
   intermediate suites.

Writes pool_screened.json (the 127 candidates with their screen outcome) and
maskfirst_50/selected50.json (paths relative to the repository root).
improvefirst_49/selected49.json is the same list minus Hex_ESTest (see the
root README, section 8).

The screen outcome used for the study is recorded in pool_screened.json; the
root README (section 8) says how it was obtained and explains the
StrMatcher_ESTest record.  Re-running the screen today needs JDK 11 (JDK_11_HOME in .env at the
repository root) and the built projects in local_workplace/.

Usage:
    python3 select_suites.py --screen-file pool_screened.json --check
                                             # reproduce selected50.json from the recorded screen
    python3 select_suites.py                 # pool -> fresh screen -> select (rewrites both files)
    python3 select_suites.py --no-screen     # skip step 2 (assumes every original passes)
"""
import argparse, collections, csv, json, sys
from concurrent.futures import ThreadPoolExecutor, as_completed
from pathlib import Path

HERE = Path(__file__).resolve().parent
ROOT = HERE.parents[1]                                  # repository root
sys.path.insert(0, str(HERE / "maskfirst_50"))
import mf                                               # noqa: E402  (compile_and_run, JDK 11, classpath)

MODEL = "sonnet-4.6"
PER_TEST = ROOT / "agent_improvement/data/improved/summary/per_test.csv"
ORIGINAL = ROOT / "agent_improvement/data/original"
BASELINE = ROOT / "agent_improvement/data/baseline"
IMPROVED = ROOT / "agent_improvement/data/improved" / MODEL
N, CAP = 50, 6


def rel(p: Path) -> str:
    p = Path(p).resolve()
    return str(p.relative_to(ROOT)) if p.is_relative_to(ROOT) else str(p)


def build_pool():
    rows = [r for r in csv.DictReader(PER_TEST.open(encoding="utf-8"))
            if r["model"] == MODEL and r["src"] == "auto" and r["gran"] == "testsuites"
            and r["is_exact_match"] == "True"]
    pool = []
    for r in sorted(rows, key=lambda r: (r["project"], r["suite"])):
        proj, suite = r["project"], r["suite"]
        m = json.loads((IMPROVED / proj / "auto/testsuites" / suite / "metrics.json").read_text())
        b = m.get("baseline") or {}
        orig = ORIGINAL / proj / "auto/testsuites" / f"{suite}.java"
        scaf = sorted((BASELINE / proj / "_evosuite_raw").rglob(f"{suite}_scaffolding.java"))
        if not orig.exists() or not scaf:
            continue
        pool.append(dict(
            suite=suite, project=proj, class_fqn=r["class_fqn"], class_path=r["class_path"],
            original=rel(orig), scaffolding=rel(scaf[0]),
            improved=rel(IMPROVED / proj / "auto/testsuites" / suite / f"{suite}.java"),
            loc=int(r["loc_original"]), n_oracles=int(r["n_oracles_original"]),
            killed=b.get("mutants_killed"), mut_pct=b.get("mutation_score_pct"),
            line_pct=b.get("line_pct"), branch_pct=b.get("branch_pct")))
    return pool


def screen(pool, jobs):
    work_root = HERE / "_screen"
    def one(rec):
        abs_rec = dict(rec, scaffolding=str(ROOT / rec["scaffolding"]))
        out = mf.compile_and_run(abs_rec, ROOT / rec["original"], work_root / rec["suite"])
        return out["status"], out.get("total"), out.get("failures")
    with ThreadPoolExecutor(jobs) as ex:
        futs = {ex.submit(one, r): r for r in pool}
        for f in as_completed(futs):
            r = futs[f]
            try:
                r["screen"], r["tests"], r["failures"] = f.result()
            except Exception as e:                       # noqa: BLE001
                r["screen"], r["tests"], r["failures"] = f"ERR:{type(e).__name__}", None, None
            if r["screen"] != "PASS":
                print(f"  excluded {r['suite']:36s}{r['project']:20s}{r['screen']}", flush=True)
    try:
        import shutil; shutil.rmtree(work_root, ignore_errors=True)
    except Exception:
        pass


def select(pool):
    by = collections.defaultdict(list)
    for r in pool:
        by[r["project"]].append(r)
    T = len(pool)
    quota = {p: max(1, min(CAP, round(len(v) * N / T))) for p, v in by.items()}
    while sum(quota.values()) != N:
        d = N - sum(quota.values())
        order = sorted(by, key=lambda p: -len(by[p]))
        for p in (order if d > 0 else list(reversed(order))):
            if d > 0 and quota[p] < min(CAP, len(by[p])):
                quota[p] += 1; d -= 1
            elif d < 0 and quota[p] > 1:
                quota[p] -= 1; d += 1
            if d == 0:
                break
    sel = []
    for p, v in by.items():
        v = sorted(v, key=lambda r: r["loc"])
        k = quota[p]
        idx = [round(i * (len(v) - 1) / (k - 1)) for i in range(k)] if k > 1 else [len(v) // 2]
        seen = set()
        for i in idx:
            while i in seen:
                i = (i + 1) % len(v)
            seen.add(i); sel.append(v[i])
    sel.sort(key=lambda r: (r["project"], r["loc"]))
    return sel, quota, by


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--no-screen", action="store_true", help="skip compiling/running the originals")
    ap.add_argument("--screen-file", help="reuse the screen outcome recorded in this pool_screened.json")
    ap.add_argument("--jobs", type=int, default=6)
    ap.add_argument("--out", default=str(HERE / "maskfirst_50/selected50.json"))
    ap.add_argument("--check", action="store_true", help="do not write; compare with --out")
    a = ap.parse_args()

    pool = build_pool()
    print(f"candidate pool (exact-match {MODEL} EvoSuite suites): {len(pool)}")
    if a.screen_file:
        rec = {r["suite"]: r for r in json.loads(Path(a.screen_file).read_text())}
        for r in pool:
            s = rec[r["suite"]]
            r["screen"], r["tests"], r["failures"] = s["screen"], s["tests"], s["failures"]
            if s["screen"] != "PASS":
                print(f"  excluded {r['suite']:36s}{r['project']:20s}{s['screen']}  (recorded)")
    elif a.no_screen:
        for r in pool:
            r["screen"], r["tests"], r["failures"] = "PASS", None, None
    else:
        screen(pool, a.jobs)
        if not a.check:
            (HERE / "pool_screened.json").write_text(json.dumps(pool, indent=1))
            print(f"wrote {rel(HERE / 'pool_screened.json')}")
    ok = [r for r in pool if r["screen"] == "PASS"]
    print(f"pass screen: {len(ok)}/{len(pool)}")

    sel, quota, by = select(ok)
    print(f"\nselected {len(sel)} from {len(by)} projects")
    print(f"  {'project':22s}{'pool':>5s}{'picked':>7s}  LOC range")
    for p in sorted(by):
        locs = [r["loc"] for r in sel if r["project"] == p]
        print(f"  {p:22s}{len(by[p]):5d}{quota[p]:7d}  {min(locs)}-{max(locs)}")

    out = Path(a.out)
    if a.check:
        ref = json.loads(out.read_text())
        same = [(r["project"], r["suite"]) for r in sel] == [(r["project"], r["suite"]) for r in ref]
        print(f"\nmatches {rel(out)}: {same}")
        return 0 if same else 1
    out.write_text(json.dumps(sel, indent=1))
    print(f"\nwrote {rel(out)}")
    return 0


if __name__ == "__main__":
    sys.exit(main())
