#!/usr/bin/env python3
"""Stage 3 — consistency gate.

An improved test only enters the experiment if it is behaviourally identical to
the floor it was built from: same covered lines, same covered branches, same
killed-mutant set, and it still passes. Without this, a later mutation-score
difference could just mean the improver changed what the test does.
"""
import json, argparse
from concurrent.futures import ThreadPoolExecutor, as_completed
from pathlib import Path
import mf

HERE = Path(__file__).resolve().parent


def one(rec, force):
    s = rec["suite"]
    imp = HERE / "improved" / s / f"{s}.java"
    p = HERE / "suites" / s / "improved_floor.json"
    if p.exists() and not force:
        m = json.loads(p.read_text())
    else:
        m = mf.measure(rec, imp, HERE / "_work" / f"{s}__impfloor")
        m["junit"] = mf.compile_and_run(rec, imp, HERE / "_work" / f"{s}__impfloor__junit")
        p.write_text(json.dumps(m, indent=1))
    fl = json.loads((HERE / "suites" / s / "floor.json").read_text())
    fk = set(fl.get("killed_keys") or []); ik = set(m.get("killed_keys") or [])
    same_lines = sorted(fl.get("covered_lines") or []) == sorted(m.get("covered_lines") or [])
    fb = {(b["line"], b["covered_branches"]) for b in (fl.get("branches_by_line") or [])}
    ib = {(b["line"], b["covered_branches"]) for b in (m.get("branches_by_line") or [])}
    passed = (m["junit"]["status"] == "PASS" and same_lines and fb == ib and fk == ik
              and not m.get("mutation_error"))
    return dict(suite=s, project=rec["project"], pass_gate=passed,
                junit=m["junit"]["status"], same_lines=same_lines, same_branches=fb == ib,
                same_killed=fk == ik, floor_killed=len(fk), improved_killed=len(ik),
                lost=len(fk - ik), gained=len(ik - fk), mut_err=bool(m.get("mutation_error")))


def main():
    ap = argparse.ArgumentParser(); ap.add_argument("--jobs", type=int, default=4)
    ap.add_argument("--force", action="store_true"); a = ap.parse_args()
    imp = {r["suite"] for r in json.loads((HERE / "improve" / "results.json").read_text())}
    recs = [r for r in mf.load50() if r["suite"] in imp]
    rows = []
    with ThreadPoolExecutor(a.jobs) as ex:
        futs = {ex.submit(one, r, a.force): r for r in recs}
        for n, f in enumerate(as_completed(futs), 1):
            r = futs[f]
            try: row = f.result()
            except Exception as e:
                print(f"  [{n}] {r['suite']} FAILED {e}"); continue
            rows.append(row)
            mark = "OK " if row["pass_gate"] else "剔除"
            print(f"  [{n}/{len(recs)}] {mark} {row['suite']:34s} junit={row['junit']:6s} "
                  f"lines={row['same_lines']} branches={row['same_branches']} "
                  f"killed {row['floor_killed']}→{row['improved_killed']} (丢{row['lost']} 多{row['gained']})", flush=True)
    (HERE / "gate_summary.json").write_text(json.dumps(rows, indent=1))
    ok = [r for r in rows if r["pass_gate"]]
    print(f"\n[gate] {len(ok)}/{len(rows)} 通过一致性门槛")


if __name__ == "__main__":
    main()
