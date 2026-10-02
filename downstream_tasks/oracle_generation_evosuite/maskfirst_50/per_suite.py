#!/usr/bin/env python3
"""Per-suite breakdown: 3-run means per arm, every metric, sorted by Δmutation."""
import json, statistics as st, sys
from pathlib import Path
HERE = Path(__file__).resolve().parent
SET = ("S1s", "S2s", "S3s")
NAMES = {"S1s": "S1s 项目可读", "S2s": "S2s 只给 CUT", "S3s": "S3s 只给测试"}


def pairs(lab):
    p = HERE / "runs" / lab / "results.json"
    rs = [r for r in json.loads(p.read_text()) if not (r.get("quota_exhausted") or r.get("sdk_error"))]
    d = {}
    for r in rs:
        d.setdefault(r["suite"], {})[r["arm"]] = r
    return {c: v for c, v in d.items() if len(v) == 2}


def mut(r):
    m = r["rebuilt_mutation_pct"]
    return m if m is not None else r["floor_mutation_pct"]


def main():
    prep = {r["suite"]: r for r in json.loads((HERE / "prep_summary.json").read_text())}
    imp = {r["suite"]: r for r in json.loads((HERE / "improve" / "results.json").read_text())}
    for s in SET:
        P = [pairs(f"{s}_run{n}") for n in (1, 2, 3)]
        common = sorted(set.intersection(*[set(p) for p in P]))
        rows = []
        for c in common:
            g = lambda arm, k: st.mean(p[c][arm][k] or 0 for p in P)
            mo = st.mean(mut(p[c]["original"]) for p in P)
            mi = st.mean(mut(p[c]["improved"]) for p in P)
            rows.append(dict(
                suite=c, proj=prep[c]["project"], loc=prep[c]["loc"],
                renamed=f"{imp[c]['tests_renamed']}/{imp[c]['n_tests_masked']}",
                floor=prep[c]["floor_mut"], gt=prep[c]["gt_mut"], stake=prep[c]["stake"],
                mo=mo, mi=mi, dm=mi - mo,
                so=g("original", "stake_recovered"), si=g("improved", "stake_recovered"),
                fo=g("original", "oracles_in_filled"), fi=g("improved", "oracles_in_filled"),
                ao=g("original", "abstained"), ai=g("improved", "abstained"),
                ro=g("original", "reasoning_tokens"), ri=g("improved", "reasoning_tokens"),
                oo=g("original", "output_tokens"), oi=g("improved", "output_tokens"),
                wo=g("original", "wall_clock_s"), wi=g("improved", "wall_clock_s"),
                wro=st.mean(1 if (p[c]["original"].get("filled_test_failures") or 0) > 0 else 0 for p in P),
                wri=st.mean(1 if (p[c]["improved"].get("filled_test_failures") or 0) > 0 else 0 for p in P)))
        rows.sort(key=lambda r: -r["dm"])
        w = sum(1 for r in rows if r["dm"] > 1e-9); l = sum(1 for r in rows if r["dm"] < -1e-9)
        print(f"\n{'='*150}\n### {NAMES[s]} — 逐 suite（3 轮均值，按 Δmutation 排序）  improved 胜 {w} 负 {l} 平 {len(rows)-w-l}")
        print(f"{'suite':32s}{'项目':18s}{'LOC':>5}{'改名':>8}{'floor':>7}{'GT':>6}"
              f"{'mut_o':>7}{'mut_i':>7}{'Δmut':>8}{'stake_o':>8}{'stake_i':>8}{'/stake':>7}"
              f"{'fill_o':>7}{'fill_i':>7}{'abs_o':>6}{'abs_i':>6}{'reas_o':>8}{'reas_i':>8}{'out_o':>8}{'out_i':>8}{'s_o':>6}{'s_i':>6}{'错_o':>6}{'错_i':>6}")
        for r in rows:
            print(f"{r['suite'][:31]:32s}{r['proj'][:17]:18s}{r['loc']:5d}{r['renamed']:>8}"
                  f"{r['floor']:7.1f}{r['gt']:6.1f}{r['mo']:7.1f}{r['mi']:7.1f}{r['dm']:+8.1f}"
                  f"{r['so']:8.1f}{r['si']:8.1f}{r['stake']:7d}"
                  f"{r['fo']:7.1f}{r['fi']:7.1f}{r['ao']:6.1f}{r['ai']:6.1f}"
                  f"{r['ro']:8.0f}{r['ri']:8.0f}{r['oo']:8.0f}{r['oi']:8.0f}"
                  f"{r['wo']:6.0f}{r['wi']:6.0f}{r['wro']:6.2f}{r['wri']:6.2f}")


if __name__ == "__main__":
    main()
