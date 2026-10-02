#!/usr/bin/env python3
"""Generate an English Markdown results report (RESULTS.md) for the EvoSuite
oracle-generation study: both orders (improve-first, mask-first) x three settings
(S1s, S2s, S3s), every metric, plus cross-run reproducibility. The numbers are
computed exactly as in summarize_all.py. They also match each study's
FINAL_REPORT.txt, except the Spearman rho: here it is tie-corrected, whereas the
per-study summarize.py still uses the no-ties shortcut formula.

Usage:  python3 results_md.py            # writes RESULTS.md next to this script
        python3 results_md.py out.md     # custom output path
"""
import json, math, statistics as st, itertools, sys
from pathlib import Path

HERE = Path(__file__).resolve().parent
SET = ("S1s", "S2s", "S3s")
NAMES = {"S1s": "S1s — whole project readable",
         "S2s": "S2s — class under test only",
         "S3s": "S3s — masked test only"}
# name, dir, min-records-to-count-a-run-as-done, one-line order description
STUDIES = [
    ("improve-first", HERE / "improvefirst_49", 90,
     "improve **then** mask — the realistic order; the improver saw the oracles."),
    ("mask-first", HERE / "maskfirst_50", 94,
     "mask **then** improve — the control; the improver never saw an oracle."),
]


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


# label, value function (row, policy), format
M = [("Mutation score (%)", lambda r, p: mut(r, p), "{:.2f}"),
     ("Fault-detection retention", lambda r, p: r["fault_detection_retention"], "{:.3f}"),
     ("Stake recovered", lambda r, p: r["stake_recovered"], "{:.2f}"),
     ("Oracles filled", lambda r, p: r["oracles_in_filled"], "{:.1f}"),
     ("Abstained", lambda r, p: r["abstained"], "{:.2f}"),
     ("Reasoning tokens", lambda r, p: r["reasoning_tokens"], "{:.0f}"),
     ("Output tokens", lambda r, p: r["output_tokens"], "{:.0f}"),
     ("Total tokens", lambda r, p: tot_tokens(r), "{:.0f}"),
     ("Wall-clock time (s)", lambda r, p: r["wall_clock_s"], "{:.1f}"),
     ("Turns", lambda r, p: r["num_turns"], "{:.1f}"),
     ("API calls", lambda r, p: r["api_calls"], "{:.1f}"),
     ("First-compile success", lambda r, p: 1.0 if r["first_compile_ok"] else 0.0, "{:.3f}"),
     ("Wrong-oracle rate", lambda r, p: 1.0 if (r.get("filled_test_failures") or 0) > 0 else 0.0, "{:.3f}"),
     ("Cost (USD)", lambda r, p: r["cost_usd"], "{:.3f}")]

OUT = []
def w(s=""):
    OUT.append(s)


def stats(base, setting, pol, runs, f):
    """Paired original-vs-improved stats for one metric function f."""
    P = [pairs(base, f"{setting}_run{n}") for n in runs]
    P = [p for p in P if p]
    if not P:
        return None
    common = sorted(set.intersection(*[set(p) for p in P]))
    po, pi, pd = [], [], []
    for c in common:
        vo = [f(p[c]["original"], pol) for p in P]; vi = [f(p[c]["improved"], pol) for p in P]
        vo = [x for x in vo if x is not None]; vi = [x for x in vi if x is not None]
        if not vo or not vi:
            continue
        po.append(st.mean(vo)); pi.append(st.mean(vi)); pd.append(st.mean(vi) - st.mean(vo))
    if not pd:
        return None
    A, B = st.mean(po), st.mean(pi); p = wil(pd)
    win = sum(1 for x in pd if x > 1e-9); los = sum(1 for x in pd if x < -1e-9)
    return {"orig": A, "imp": B, "delta": B - A, "p": p, "w": win, "l": los, "t": len(pd) - win - los, "n": len(pd)}


def ncommon(base, setting, runs):
    P = [pairs(base, f"{setting}_run{n}") for n in runs]
    P = [p for p in P if p]
    return len(set.intersection(*[set(p) for p in P])) if P else 0


def full_table(base, setting, runs):
    w(f"#### {NAMES[setting]}  ·  {len(runs)} runs  ·  n = {ncommon(base, setting, runs)} suites")
    w()
    w("| Metric | Original | Improved | Δ | Δ% | p (Wilcoxon) | W/L/T |")
    w("|:--|--:|--:|--:|--:|--:|:--:|")
    for name, f, fmt in M:
        s = stats(base, setting, "B", runs, f)
        if not s:
            continue
        sfmt = fmt.replace("{:", "{:+")
        dpct = f"{s['delta'] / s['orig'] * 100:+.1f}%" if s["orig"] else "n/a"
        pstr = f"{s['p']}" if s["p"] is not None else "n/a"
        w(f"| {name} | {fmt.format(s['orig'])} | {fmt.format(s['imp'])} | {sfmt.format(s['delta'])} "
          f"| {dpct} | {pstr} | {s['w']}/{s['l']}/{s['t']} |")
    # stake recovery footnote
    P = [pairs(base, f"{setting}_run{n}") for n in runs]; P = [p for p in P if p]
    common = sorted(set.intersection(*[set(p) for p in P]))
    so = sum(st.mean(p[c]["original"]["stake_recovered"] for p in P) for c in common)
    si = sum(st.mean(p[c]["improved"]["stake_recovered"] for p in P) for c in common)
    sm = sum(P[0][c]["original"]["stake_mutants"] for c in common)
    w()
    w(f"*Stake recovery: original {so / sm:.1%}, improved {si / sm:.1%} (total stake {sm} mutants).*")
    w()


def main():
    w("# EvoSuite Oracle Generation — Results")
    w()
    w("Downstream oracle generation on automatically generated (EvoSuite) test suites, "
      "model `claude-sonnet-4-6`, three runs per condition. Every oracle is removed from a "
      "suite; an LLM agent regenerates the oracles; we then measure how much of the suite's "
      "fault-detection power the regenerated oracles recover, comparing the **original** test "
      "version against its understandability-**improved** counterpart.")
    w()
    w("**Design.** Two orders — *improve-first* and *mask-first* — are each run under three "
      "information-access settings (a nested read-scope ladder S1s ⊃ S2s ⊃ S3s).")
    w()
    w("- **Settings.** **S1s** whole project readable · **S2s** class under test + masked test only · **S3s** masked test only.")
    w("- **Scoring.** A suite whose regenerated oracle fails to compile or is wrong is scored at its oracle-free **floor** (primary policy). "
      "An alternative policy that instead keeps only suites where both arms pass is reported per order for the mutation score.")
    w("- **Reading the tables.** Values are per-suite means over the three runs. "
      "**Δ** = improved − original; **p** is a paired Wilcoxon signed-rank test over suites; "
      "**W/L/T** counts suites where the improved version wins / loses / ties.")
    w()
    w("---")
    w()
    for name, base, thr, desc in STUDIES:
        done = [f"{s}_run{n}" for s in SET for n in (1, 2, 3) if len(rows(base, f"{s}_run{n}")) >= thr]
        n_any = ncommon(base, "S1s", tuple(n for n in (1, 2, 3) if f"S1s_run{n}" in done))
        w(f"## {name}")
        w()
        w(f"{desc}  \n*Usable suites: n = {n_any}.*")
        w()
        # full per-metric tables (primary scoring)
        for s in SET:
            ns = tuple(n for n in (1, 2, 3) if f"{s}_run{n}" in done)
            if ns:
                full_table(base, s, ns)
        # alternative scoring: mutation score only
        w("**Alternative scoring — keep only suites where both arms pass (mutation score):**")
        w()
        w("| Setting | Original | Improved | Δ | p (Wilcoxon) | W/L/T |")
        w("|:--|--:|--:|--:|--:|:--:|")
        for s in SET:
            ns = tuple(n for n in (1, 2, 3) if f"{s}_run{n}" in done)
            if not ns:
                continue
            a = stats(base, s, "A", ns, lambda r, p: mut(r, p))
            if not a:
                continue
            pstr = f"{a['p']}" if a["p"] is not None else "n/a"
            w(f"| {s} | {a['orig']:.2f} | {a['imp']:.2f} | {a['delta']:+.2f} | {pstr} | {a['w']}/{a['l']}/{a['t']} |")
        w()
        # reproducibility
        w("**Reproducibility — per-suite Δ mutation score, Spearman ρ across the three runs:**")
        w()
        w("| Setting | Per-run mean Δ | Pairwise ρ | Mean ρ |")
        w("|:--|:--|:--|--:|")
        for s in SET:
            ns = [n for n in (1, 2, 3) if f"{s}_run{n}" in done]
            if len(ns) < 2:
                continue
            P = [pairs(base, f"{s}_run{n}") for n in ns]
            common = sorted(set.intersection(*[set(p) for p in P]))
            ds = [{c: mut(p[c]["improved"], "B") - mut(p[c]["original"], "B") for c in common} for p in P]
            rh = [spearman([ds[a][c] for c in common], [ds[b][c] for c in common])
                  for a, b in itertools.combinations(range(len(ns)), 2)]
            means = " ".join(f"{st.mean(d.values()):+.2f}" for d in ds)
            rhos = ", ".join(f"{r:+.2f}" for r in rh)
            w(f"| {s} | {means} | {rhos} | {st.mean(rh):+.2f} |")
        w()
        w("---")
        w()

    dest = Path(sys.argv[1]) if len(sys.argv) > 1 else (HERE / "RESULTS.md")
    dest.write_text("\n".join(OUT), encoding="utf-8")
    print(f"wrote {dest}  ({len(OUT)} lines)")


if __name__ == "__main__":
    main()
