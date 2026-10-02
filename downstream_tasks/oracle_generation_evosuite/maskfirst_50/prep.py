#!/usr/bin/env python3
"""Stage 1 — delete every oracle, then measure the ground truth and the floor.

GT    = the original suite with its oracles (ceiling).
floor = the same suite with ALL oracles gone (what the improver will work on).
stake = killed(GT) - killed(floor): the mutants the oracles are worth.
"""
import json, argparse, shutil
from concurrent.futures import ThreadPoolExecutor, as_completed
from pathlib import Path
import mf

HERE = Path(__file__).resolve().parent
OUT = HERE / "suites"


def one(rec, force):
    s = rec["suite"]
    d = OUT / s
    (d / "masked").mkdir(parents=True, exist_ok=True)
    (d / "original").mkdir(parents=True, exist_ok=True)
    gt_f = d / "original" / f"{s}.java"
    mk_f = d / "masked" / f"{s}.java"
    if not gt_f.exists() or force:
        shutil.copy2(rec["original"], gt_f)
    if not mk_f.exists() or force:
        masked, info = mf.mask_all_oracles(gt_f.read_text(errors="ignore"))
        mk_f.write_text(masked, encoding="utf-8")
        (d / "mask_info.json").write_text(json.dumps(info, indent=1))
    info = json.loads((d / "mask_info.json").read_text())
    res = {}
    for kind, f in (("gt", gt_f), ("floor", mk_f)):
        p = d / f"{kind}.json"
        if p.exists() and not force:
            res[kind] = json.loads(p.read_text()); continue
        m = mf.measure(rec, f, HERE / "_work" / f"{s}__{kind}")
        m["junit"] = mf.compile_and_run(rec, f, HERE / "_work" / f"{s}__{kind}__junit")
        p.write_text(json.dumps(m, indent=1))
        res[kind] = m
    gk = set(res["gt"].get("killed_keys") or [])
    fk = set(res["floor"].get("killed_keys") or [])
    return dict(suite=s, project=rec["project"], loc=rec["loc"],
                n_tests=info["n_tests"], n_masked=info["n_tests_masked"],
                n_oracles=info["n_oracles_removed"], emptied=info["n_methods_emptied"],
                gt_mut=res["gt"].get("mutation_score_pct"), floor_mut=res["floor"].get("mutation_score_pct"),
                gt_killed=len(gk), floor_killed=len(fk), stake=len(gk - fk),
                gt_line=res["gt"].get("line_pct"), floor_line=res["floor"].get("line_pct"),
                gt_junit=res["gt"]["junit"]["status"], floor_junit=res["floor"]["junit"]["status"],
                gt_err=res["gt"].get("mutation_error"), floor_err=res["floor"].get("mutation_error"))


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--jobs", type=int, default=4)
    ap.add_argument("--force", action="store_true")
    a = ap.parse_args()
    recs = mf.load50()
    rows = []
    with ThreadPoolExecutor(a.jobs) as ex:
        futs = {ex.submit(one, r, a.force): r for r in recs}
        for n, f in enumerate(as_completed(futs), 1):
            r = futs[f]
            try:
                row = f.result()
            except Exception as e:
                print(f"  [{n}/{len(recs)}] {r['suite']:34s} FAILED {type(e).__name__}: {e}", flush=True)
                continue
            rows.append(row)
            print(f"  [{n}/{len(recs)}] {row['suite']:34s}{row['project']:20s}"
                  f"gt {row['gt_mut']} floor {row['floor_mut']} stake {row['stake']:3d} "
                  f"空方法 {row['emptied']}/{row['n_masked']} junit {row['gt_junit']}/{row['floor_junit']}", flush=True)
    (HERE / "prep_summary.json").write_text(json.dumps(rows, indent=1))
    ok = [r for r in rows if r["stake"] and r["floor_junit"] == "PASS" and r["gt_junit"] == "PASS"
          and not r["gt_err"] and not r["floor_err"]]
    print(f"\n[prep] {len(rows)} 个测完；可用 {len(ok)} 个")
    for r in rows:
        why = []
        if r["gt_junit"] != "PASS": why.append(f"GT 测试 {r['gt_junit']}")
        if r["floor_junit"] != "PASS": why.append(f"floor 测试 {r['floor_junit']}")
        if r["gt_err"]: why.append("GT PIT 失败")
        if r["floor_err"]: why.append("floor PIT 失败")
        if not r["stake"]: why.append("stake=0")
        if why:
            print(f"    剔除 {r['suite']:34s}{' + '.join(why)}")
    import statistics as st
    if ok:
        print(f"  stake 合计 {sum(r['stake'] for r in ok)}，中位 {st.median(r['stake'] for r in ok):.0f}")
        print(f"  变空的方法占比 中位 {st.median(r['emptied']/max(1,r['n_masked']) for r in ok):.0%}")


if __name__ == "__main__":
    main()
