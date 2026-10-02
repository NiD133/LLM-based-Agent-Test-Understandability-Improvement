#!/usr/bin/env python3
"""Unified EvoSuite oracle-generation summary.

One entry point that produces, for BOTH orders (improve-first, mask-first) across
the three access settings (S1s, S2s, S3s), the paired original-vs-improved tables
under both scoring policies (B = wrong oracle scored at its floor, primary;
A = both arms must pass), plus per-suite cross-run Spearman reproducibility.

The computation is byte-for-byte the same as each study's own summarize.py, so the
numbers match the per-study FINAL_REPORT.txt exactly -- except the Spearman rho,
which is tie-corrected here (spearman() below) while summarize.py still uses the
no-ties shortcut formula. Only the core 6 conditions
(2 orders x 3 settings, run1..3) are reported; extra variants such as mask-first's
S3s_th (thinking) are ignored.

Usage:
  python3 summarize_all.py            # both orders
  python3 summarize_all.py improve    # improve-first only
  python3 summarize_all.py mask       # mask-first only
"""
import json, math, statistics as st, itertools, sys
from pathlib import Path

HERE = Path(__file__).resolve().parent
SET = ("S1s", "S2s", "S3s")
NAMES = {"S1s": "S1s 项目可读", "S2s": "S2s 只给 CUT", "S3s": "S3s 只给测试"}

# name -> (study dir, header line, min records for a run to count as "done")
STUDIES = {
    "improve-first": (HERE / "improvefirst_49",
                      "improve-FIRST EvoSuite · sonnet-4-6 · 无 marker · summarized thinking", 90),
    "mask-first":    (HERE / "maskfirst_50",
                      "mask-first EvoSuite · sonnet-4-6 · 无 marker", 94),
}


def rows(base, lab):
    p = base / "runs" / lab / "results.json"
    if not p.exists():
        return []
    return [r for r in json.loads(p.read_text()) if not (r.get("quota_exhausted") or r.get("sdk_error"))]


def pairs(base, lab):
    d = {}
    for r in rows(base, lab):
        d.setdefault(r["suite"], {})[r["arm"]] = r
    return {c: v for c, v in d.items() if len(v) == 2}


def mut(r, pol):
    m = r["rebuilt_mutation_pct"]
    return m if m is not None else (r["floor_mutation_pct"] if pol == "B" else None)


def tot_tokens(r):
    u = ((r.get("tokens") or {}).get("usage") or {})
    return sum(u.get(k, 0) for k in ("input_tokens", "cache_creation_input_tokens",
                                     "cache_read_input_tokens", "output_tokens"))


def wil(v):
    v = [x for x in v if abs(x) > 1e-9]
    if len(v) < 6:
        return None
    rk = sorted(range(len(v)), key=lambda i: abs(v[i])); rn = [0] * len(v); i = 0
    while i < len(rk):
        j = i
        while j + 1 < len(rk) and abs(v[rk[j + 1]]) == abs(v[rk[i]]):
            j += 1
        for t in range(i, j + 1):
            rn[rk[t]] = (i + j) / 2 + 1
        i = j + 1
    W = sum(r for r, x in zip(rn, v) if x > 0); n = len(v)
    return round(math.erfc(abs((W - n * (n + 1) / 4) / math.sqrt(n * (n + 1) * (2 * n + 1) / 24)) / math.sqrt(2)), 4)


def ranks(v):
    """1-based ranks; tied values share the mean of their positions."""
    o = sorted(range(len(v)), key=lambda i: v[i]); rn = [0] * len(v); i = 0
    while i < len(o):
        j = i
        while j + 1 < len(o) and v[o[j + 1]] == v[o[i]]:
            j += 1
        for t in range(i, j + 1):
            rn[o[t]] = (i + j) / 2 + 1
        i = j + 1
    return rn


def spearman(x, y):
    """Spearman's rho with ties = Pearson correlation of the average ranks (as
    scipy.stats.spearmanr). The shortcut 1 - 6*sum(d^2)/(n(n^2-1)) assumes no
    ties, but many suites have a per-run delta of exactly 0."""
    rx, ry = ranks(x), ranks(y); mx, my = st.mean(rx), st.mean(ry)
    sxy = sum((a - mx) * (b - my) for a, b in zip(rx, ry))
    sxx = sum((a - mx) ** 2 for a in rx); syy = sum((b - my) ** 2 for b in ry)
    return sxy / math.sqrt(sxx * syy) if sxx and syy else float("nan")


M = [("mutation score (%)", lambda r, p: mut(r, p), "{:.2f}"),
     ("fault retention", lambda r, p: r["fault_detection_retention"], "{:.3f}"),
     ("stake recovered", lambda r, p: r["stake_recovered"], "{:.2f}"),
     ("oracles filled", lambda r, p: r["oracles_in_filled"], "{:.1f}"),
     ("abstained", lambda r, p: r["abstained"], "{:.2f}"),
     ("reasoning tokens", lambda r, p: r["reasoning_tokens"], "{:.0f}"),
     ("output tokens", lambda r, p: r["output_tokens"], "{:.0f}"),
     ("total tokens", lambda r, p: tot_tokens(r), "{:.0f}"),
     ("wall clock (s)", lambda r, p: r["wall_clock_s"], "{:.1f}"),
     ("turns", lambda r, p: r["num_turns"], "{:.1f}"),
     ("api calls", lambda r, p: r["api_calls"], "{:.1f}"),
     ("first-compile OK", lambda r, p: 1.0 if r["first_compile_ok"] else 0.0, "{:.3f}"),
     ("wrong-oracle rate", lambda r, p: 1.0 if (r.get("filled_test_failures") or 0) > 0 else 0.0, "{:.3f}"),
     ("cost (USD)", lambda r, p: r["cost_usd"], "{:.3f}")]


def table(base, setting, pol, runs):
    P = [pairs(base, f"{setting}_run{n}") for n in runs]
    P = [p for p in P if p]
    if not P:
        return
    common = sorted(set.intersection(*[set(p) for p in P]))
    print(f"\n{'─'*92}\n### {NAMES[setting]} · {len(P)} run · n={len(common)} suites")
    print(f"{'指标':<22}{'original':>11}{'improved':>11}{'Δ':>10}{'Δ%':>9}{'p':>9}{'赢/输/平':>11}")
    for name, f, fmt in M:
        po, pi, pd = [], [], []
        for c in common:
            vo = [f(p[c]["original"], pol) for p in P]; vi = [f(p[c]["improved"], pol) for p in P]
            vo = [x for x in vo if x is not None]; vi = [x for x in vi if x is not None]
            if not vo or not vi:
                continue
            po.append(st.mean(vo)); pi.append(st.mean(vi)); pd.append(st.mean(vi) - st.mean(vo))
        if not pd:
            continue
        A, B = st.mean(po), st.mean(pi); p = wil(pd)
        w = sum(1 for x in pd if x > 1e-9); l = sum(1 for x in pd if x < -1e-9)
        print(f"{name:<22}{fmt.format(A):>11}{fmt.format(B):>11}{B-A:>+10.2f}"
              f"{(f'{(B-A)/A*100:+.1f}%' if A else 'n/a'):>9}{(str(p) if p is not None else 'n/a'):>9}"
              f"{f'{w}/{l}/{len(pd)-w-l}':>11}")
    so = sum(st.mean(p[c]["original"]["stake_recovered"] for p in P) for c in common)
    si = sum(st.mean(p[c]["improved"]["stake_recovered"] for p in P) for c in common)
    sm = sum(P[0][c]["original"]["stake_mutants"] for c in common)
    print(f"  stake 回收率: original {so/sm:.1%}  improved {si/sm:.1%}  (stake 合计 {sm})")


def reproducibility(base, done):
    print("\n" + "=" * 92 + "\n复现性（per-suite Δmutation 的 Spearman rho）")
    for s in SET:
        ns = [n for n in (1, 2, 3) if f"{s}_run{n}" in done]
        if len(ns) < 2:
            continue
        P = [pairs(base, f"{s}_run{n}") for n in ns]
        common = sorted(set.intersection(*[set(p) for p in P]))
        ds = [{c: mut(p[c]["improved"], "B") - mut(p[c]["original"], "B") for c in common} for p in P]
        rh = [spearman([ds[a][c] for c in common], [ds[b][c] for c in common])
              for a, b in itertools.combinations(range(len(ns)), 2)]
        print(f"  {s}: 每轮 Δ = " + " ".join(f"{st.mean(d.values()):+5.2f}" for d in ds)
              + (f"   rho {[round(r,2) for r in rh]} 均值 {st.mean(rh):+.2f}" if rh else ""))


def run_study(name):
    base, header, thr = STUDIES[name]
    # core 6 labels only (S1s/S2s/S3s x run1..3); ignore extra variants like S3s_th
    done = [f"{s}_run{n}" for s in SET for n in (1, 2, 3) if len(rows(base, f"{s}_run{n}")) >= thr]
    print("#" * 92)
    print(f"{header} · 完成的 label: {', '.join(done) or '（无）'}")
    for pol, lab in (("B", "B 写错 oracle 记为 floor（主口径）"), ("A", "两臂都通过才计入")):
        print("\n" + "=" * 92 + f"\n口径 {lab}")
        for s in SET:
            ns = [n for n in (1, 2, 3) if f"{s}_run{n}" in done]
            if ns:
                table(base, s, pol, tuple(ns))
    reproducibility(base, done)


def main():
    alias = {"improve": "improve-first", "improve-first": "improve-first", "improvefirst": "improve-first",
             "mask": "mask-first", "mask-first": "mask-first", "maskfirst": "mask-first"}
    arg = (sys.argv[1].lower() if len(sys.argv) > 1 else "all")
    if arg in ("all", "both"):
        names = list(STUDIES)
    elif arg in alias:
        names = [alias[arg]]
    else:
        print(f"unknown order '{sys.argv[1]}'; use: improve | mask | all")
        return
    for i, nm in enumerate(names):
        if i:
            print("\n")
        run_study(nm)


if __name__ == "__main__":
    main()
