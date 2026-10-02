#!/usr/bin/env python3
"""Stage 1 — mask BOTH arms, then measure each arm's ceiling and floor.

Mirror of the mask-first prep, with the one difference that defines the study:
the improvement already happened (RQ1, claude-sonnet-4-6), so BOTH suites arrive
with their oracles and BOTH are masked here with the same mask_all_oracles.

Per arm:
  GT    = the suite with its oracles (that arm's ceiling)
  floor = the same suite with EVERY oracle deleted (what the agent receives)
  stake = killed(GT) - killed(floor)

The original arm is byte-identical to study A's, so its gt.json / floor.json are
copied over rather than re-measured; --remeasure-original forces a fresh run.
Both arms' GTs are measured so that any drift from the exact-match check (which
was computed in a different session) is visible rather than assumed.
"""
import json, argparse, shutil
from concurrent.futures import ThreadPoolExecutor, as_completed
from pathlib import Path
import mf

HERE = Path(__file__).resolve().parent
OUT = HERE / "suites"
STUDY_A = Path(__file__).resolve().parent.parent / "maskfirst_50" / "suites"
ARMS = ("original", "improved")


def one(rec, force, remeasure_original):
    s = rec["suite"]
    d = OUT / s
    res = {}
    for arm in ARMS:
        (d / arm).mkdir(parents=True, exist_ok=True)
        gt_f = d / arm / f"{s}.java"                 # with oracles
        mk_f = d / arm / "masked" / f"{s}.java"      # every oracle deleted
        mk_f.parent.mkdir(parents=True, exist_ok=True)
        if not gt_f.exists() or force:
            shutil.copy2(rec[arm], gt_f)
        if not mk_f.exists() or force:
            masked, info = mf.mask_all_oracles(gt_f.read_text(errors="ignore"))
            mk_f.write_text(masked, encoding="utf-8")
            (d / arm / "mask_info.json").write_text(json.dumps(info, indent=1))
        res[arm] = {"info": json.loads((d / arm / "mask_info.json").read_text())}

        reuse = (arm == "original" and not remeasure_original and not force)
        for kind, f in (("gt", gt_f), ("floor", mk_f)):
            p = d / arm / f"{kind}.json"
            src_a = STUDY_A / s / f"{kind}.json"
            if p.exists() and not force:
                res[arm][kind] = json.loads(p.read_text()); continue
            if reuse and src_a.exists():
                shutil.copy2(src_a, p)
                res[arm][kind] = json.loads(p.read_text())
                res[arm][kind]["_reused_from_study_A"] = True
                continue
            m = mf.measure(rec, f, HERE / "_work" / f"{s}__{arm}__{kind}")
            m["junit"] = mf.compile_and_run(rec, f, HERE / "_work" / f"{s}__{arm}__{kind}__junit")
            p.write_text(json.dumps(m, indent=1))
            res[arm][kind] = m

    row = {"suite": s, "project": rec["project"], "loc": rec["loc"]}
    for arm in ARMS:
        gk = set(res[arm]["gt"].get("killed_keys") or [])
        fk = set(res[arm]["floor"].get("killed_keys") or [])
        i = res[arm]["info"]
        row.update({
            f"{arm}_n_tests": i["n_tests"], f"{arm}_n_masked": i["n_tests_masked"],
            f"{arm}_n_oracles": i["n_oracles_removed"], f"{arm}_emptied": i["n_methods_emptied"],
            f"{arm}_gt_mut": res[arm]["gt"].get("mutation_score_pct"),
            f"{arm}_floor_mut": res[arm]["floor"].get("mutation_score_pct"),
            f"{arm}_gt_killed": len(gk), f"{arm}_floor_killed": len(fk),
            f"{arm}_stake": len(gk - fk),
            f"{arm}_gt_junit": res[arm]["gt"]["junit"]["status"],
            f"{arm}_floor_junit": res[arm]["floor"]["junit"]["status"],
            f"{arm}_gt_err": res[arm]["gt"].get("mutation_error"),
            f"{arm}_floor_err": res[arm]["floor"].get("mutation_error"),
            f"{arm}_reused": bool(res[arm]["gt"].get("_reused_from_study_A")),
        })
    go = set(res["original"]["gt"].get("killed_keys") or [])
    gi = set(res["improved"]["gt"].get("killed_keys") or [])
    fo = set(res["original"]["floor"].get("killed_keys") or [])
    fi = set(res["improved"]["floor"].get("killed_keys") or [])
    row["same_gt_killed"] = go == gi
    row["gt_only_original"] = len(go - gi)
    row["gt_only_improved"] = len(gi - go)
    row["same_floor_killed"] = fo == fi
    row["floor_only_original"] = len(fo - fi)
    row["floor_only_improved"] = len(fi - fo)
    # shared ground truth = the ORIGINAL suite's oracles, exactly as in study A
    row["shared_stake"] = len(go - fo)
    return row


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--jobs", type=int, default=4)
    ap.add_argument("--force", action="store_true")
    ap.add_argument("--remeasure-original", action="store_true",
                    help="re-measure the original arm instead of reusing study A's numbers")
    ap.add_argument("--only")
    a = ap.parse_args()
    recs = mf.load()
    if a.only:
        recs = [r for r in recs if r["suite"] in a.only.split(",")]
    rows = []
    with ThreadPoolExecutor(a.jobs) as ex:
        futs = {ex.submit(one, r, a.force, a.remeasure_original): r for r in recs}
        for n, f in enumerate(as_completed(futs), 1):
            r = futs[f]
            try:
                row = f.result()
            except Exception as e:
                print(f"  [{n}/{len(recs)}] {r['suite']} FAILED {type(e).__name__}: {e}", flush=True)
                continue
            rows.append(row)
            print(f"  [{n}/{len(recs)}] {row['suite']:34s} "
                  f"orig {row['original_floor_mut']}→{row['original_gt_mut']} stake {row['original_stake']:3d} | "
                  f"impr {row['improved_floor_mut']}→{row['improved_gt_mut']} stake {row['improved_stake']:3d} | "
                  f"same GT {row['same_gt_killed']} same floor {row['same_floor_killed']}", flush=True)
    rows.sort(key=lambda r: r["suite"])
    (HERE / "prep_summary.json").write_text(json.dumps(rows, indent=1))
    usable = [r for r in rows
              if r["shared_stake"] > 0
              and r["original_gt_junit"] == "PASS" and r["original_floor_junit"] == "PASS"
              and r["improved_gt_junit"] == "PASS" and r["improved_floor_junit"] == "PASS"
              and not r["original_gt_err"] and not r["original_floor_err"]
              and not r["improved_gt_err"] and not r["improved_floor_err"]]
    (HERE / "usable.json").write_text(json.dumps([r["suite"] for r in usable], indent=1))
    print(f"\n[prep] {len(rows)} suites measured")
    print(f"[prep] GT killed-mutant set identical across arms : {sum(1 for r in rows if r['same_gt_killed'])}/{len(rows)}")
    print(f"[prep] floor killed-mutant set identical          : {sum(1 for r in rows if r['same_floor_killed'])}/{len(rows)}")
    print(f"[prep] shared stake (original GT - original floor) total {sum(r['shared_stake'] for r in rows)}")
    print(f"[prep] usable -> usable.json : {len(usable)}/{len(rows)}")
    for r in rows:
        if r not in usable:
            print(f"        dropped {r['suite']}: stake={r['shared_stake']} "
                  f"junit o({r['original_gt_junit']}/{r['original_floor_junit']}) "
                  f"i({r['improved_gt_junit']}/{r['improved_floor_junit']})")


if __name__ == "__main__":
    main()
