#!/usr/bin/env python3
"""One consolidated table: every metric x three settings, side by side.
3-run means per suite, then paired across arms (n=47). Policy B (a wrong oracle
scores that arm its floor) is used for mutation score; every other metric is
measured directly."""
import json, math, statistics as st
from pathlib import Path
HERE = Path(__file__).resolve().parent
SET = ("S1s", "S2s", "S3s")


def pairs(lab):
    rs = [r for r in json.loads((HERE / "runs" / lab / "results.json").read_text())
          if not (r.get("quota_exhausted") or r.get("sdk_error"))]
    d = {}
    for r in rs:
        d.setdefault(r["suite"], {})[r["arm"]] = r
    return {c: v for c, v in d.items() if len(v) == 2}


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
    return round(math.erfc(abs((W - n * (n + 1) / 4) / math.sqrt(n * (n + 1) * (2 * n + 1) / 24)) / math.sqrt(2)), 3)


def mut(r):
    m = r["rebuilt_mutation_pct"]
    return m if m is not None else r["floor_mutation_pct"]


def per(r, num, den):
    n_ = r[num] or 0; d = r[den] or 0
    return None if d <= 0 else n_ / d


METRICS = [
    ("mutation score (%)", lambda r: mut(r), "{:.2f}"),
    ("fault retention", lambda r: r["fault_detection_retention"], "{:.3f}"),
    ("stake recovered (个)", lambda r: r["stake_recovered"], "{:.2f}"),
    ("oracles filled (条)", lambda r: r["oracles_in_filled"], "{:.2f}"),
    ("abstained (条)", lambda r: r["abstained"], "{:.2f}"),
    ("wrong-oracle rate", lambda r: 1.0 if (r.get("filled_test_failures") or 0) > 0 else 0.0, "{:.3f}"),
    ("first-compile OK", lambda r: 1.0 if r["first_compile_ok"] else 0.0, "{:.3f}"),
    ("reasoning tokens", lambda r: r["reasoning_tokens"], "{:.0f}"),
    ("visible output tokens", lambda r: r["visible_output_tokens"], "{:.0f}"),
    ("output tokens", lambda r: r["output_tokens"], "{:.0f}"),
    ("total tokens (含cache)", lambda r: sum(((r.get("tokens") or {}).get("usage") or {}).get(k, 0)
                                             for k in ("input_tokens", "cache_creation_input_tokens",
                                                       "cache_read_input_tokens", "output_tokens")), "{:.0f}"),
    ("reasoning / oracle", lambda r: per(r, "reasoning_tokens", "oracles_in_filled"), "{:.0f}"),
    ("output / oracle", lambda r: per(r, "output_tokens", "oracles_in_filled"), "{:.0f}"),
    ("visible out / oracle", lambda r: per(r, "visible_output_tokens", "oracles_in_filled"), "{:.0f}"),
    ("wall clock (s)", lambda r: r["wall_clock_s"], "{:.1f}"),
    ("api time (s)", lambda r: r["api_time_s"], "{:.1f}"),
    ("turns", lambda r: r["num_turns"], "{:.2f}"),
    ("api calls", lambda r: r["api_calls"], "{:.2f}"),
    ("tool calls", lambda r: r["tool_calls"], "{:.2f}"),
    ("edit calls", lambda r: r["edit_calls"], "{:.2f}"),
    ("compile runs", lambda r: r["compile_runs"], "{:.2f}"),
    ("cost (USD)", lambda r: r["cost_usd"], "{:.3f}"),
]


def cell(s, f):
    P = [pairs(f"{s}_run{n}") for n in (1, 2, 3)]
    common = sorted(set.intersection(*[set(p) for p in P]))
    o, i, d = [], [], []
    for c in common:
        vo = [f(p[c]["original"]) for p in P]; vi = [f(p[c]["improved"]) for p in P]
        vo = [x for x in vo if x is not None]; vi = [x for x in vi if x is not None]
        if not vo or not vi:
            continue
        o.append(st.mean(vo)); i.append(st.mean(vi)); d.append(st.mean(vi) - st.mean(vo))
    if not d:
        return None
    A, B = st.mean(o), st.mean(i)
    w = sum(1 for x in d if x > 1e-9); l = sum(1 for x in d if x < -1e-9)
    return A, B, B - A, ((B - A) / A * 100 if A else None), wil(d), w, l, len(d) - w - l


def main():
    print("=" * 170)
    print("mask-first EvoSuite · claude-sonnet-4-6 · 无 marker · 47 suites × 2 臂 × 3 setting × 3 run = 846 sessions")
    print("每个 suite 先对 3 次 run 取均值，再做配对比较（Wilcoxon signed-rank, n=47）")
    print("=" * 170)
    head = f"{'指标':<24}"
    for s in SET:
        head += f"│{s+' orig':>12}{'imp':>11}{'Δ%':>9}{'p':>8}{'赢/输/平':>11}"
    print(head)
    print("─" * 170)
    for name, f, fmt in METRICS:
        line = f"{name:<24}"
        for s in SET:
            c = cell(s, f)
            if c is None:
                line += "│" + " " * 51
                continue
            A, B, D, pc, p, w, l, t = c
            line += (f"│{fmt.format(A):>12}{fmt.format(B):>11}"
                     f"{(f'{pc:+.1f}%' if pc is not None else 'n/a'):>9}"
                     f"{(str(p) if p is not None else 'n/a'):>8}{f'{w}/{l}/{t}':>11}")
        print(line)
    print("─" * 170)
    for s in SET:
        P = [pairs(f"{s}_run{n}") for n in (1, 2, 3)]
        common = sorted(set.intersection(*[set(p) for p in P]))
        so = sum(st.mean(p[c]["original"]["stake_recovered"] for p in P) for c in common)
        si = sum(st.mean(p[c]["improved"]["stake_recovered"] for p in P) for c in common)
        sm = sum(P[0][c]["original"]["stake_mutants"] for c in common)
        print(f"  {s} stake 回收率: original {so/sm:6.1%}  improved {si/sm:6.1%}  (stake 合计 {sm})")


if __name__ == "__main__":
    main()
