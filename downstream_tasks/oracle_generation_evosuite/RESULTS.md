# EvoSuite Oracle Generation — Results

Downstream oracle generation on automatically generated (EvoSuite) test suites, model `claude-sonnet-4-6`, three runs per condition. Every oracle is removed from a suite; an LLM agent regenerates the oracles; we then measure how much of the suite's fault-detection power the regenerated oracles recover, comparing the **original** test version against its understandability-**improved** counterpart.

**Design.** Two orders — *improve-first* and *mask-first* — are each run under three information-access settings (a nested read-scope ladder S1s ⊃ S2s ⊃ S3s).

- **Settings.** **S1s** whole project readable · **S2s** class under test + masked test only · **S3s** masked test only.
- **Scoring.** A suite whose regenerated oracle fails to compile or is wrong is scored at its oracle-free **floor** (primary policy). An alternative policy that instead keeps only suites where both arms pass is reported per order for the mutation score.
- **Reading the tables.** Values are per-suite means over the three runs. **Δ** = improved − original; **p** is a paired Wilcoxon signed-rank test over suites; **W/L/T** counts suites where the improved version wins / loses / ties.

---

## improve-first

improve **then** mask — the realistic order; the improver saw the oracles.  
*Usable suites: n = 48.*

#### S1s — whole project readable  ·  3 runs  ·  n = 48 suites

| Metric | Original | Improved | Δ | Δ% | p (Wilcoxon) | W/L/T |
|:--|--:|--:|--:|--:|--:|:--:|
| Mutation score (%) | 63.94 | 67.04 | +3.10 | +4.8% | 0.0025 | 31/8/9 |
| Fault-detection retention | 0.843 | 0.883 | +0.040 | +4.8% | 0.0294 | 26/9/13 |
| Stake recovered | 17.55 | 20.28 | +2.73 | +15.6% | 0.0064 | 26/8/14 |
| Oracles filled | 15.3 | 17.7 | +2.4 | +15.7% | 0.0005 | 34/12/2 |
| Abstained | 0.33 | 0.12 | -0.20 | -61.7% | 0.0007 | 4/21/23 |
| Reasoning tokens | 8220 | 6274 | -1946 | -23.7% | 0.0 | 10/38/0 |
| Output tokens | 13357 | 11274 | -2082 | -15.6% | 0.0002 | 11/37/0 |
| Total tokens | 945634 | 849196 | -96438 | -10.2% | 0.2423 | 24/24/0 |
| Wall-clock time (s) | 175.0 | 153.8 | -21.1 | -12.1% | 0.0066 | 16/32/0 |
| Turns | 22.4 | 21.6 | -0.8 | -3.8% | 0.9564 | 24/22/2 |
| API calls | 20.2 | 19.5 | -0.7 | -3.4% | 0.7932 | 24/22/2 |
| First-compile success | 1.000 | 0.993 | -0.007 | -0.7% | n/a | 0/1/47 |
| Wrong-oracle rate | 0.083 | 0.076 | -0.007 | -8.3% | 0.9645 | 5/6/37 |
| Cost (USD) | 0.670 | 0.596 | -0.074 | -11.0% | 0.0097 | 17/31/0 |

*Stake recovery: original 72.6%, improved 83.9% (total stake 1160 mutants).*

#### S2s — class under test only  ·  3 runs  ·  n = 48 suites

| Metric | Original | Improved | Δ | Δ% | p (Wilcoxon) | W/L/T |
|:--|--:|--:|--:|--:|--:|:--:|
| Mutation score (%) | 60.62 | 65.70 | +5.08 | +8.4% | 0.0003 | 30/7/11 |
| Fault-detection retention | 0.794 | 0.854 | +0.061 | +7.6% | 0.0023 | 26/6/16 |
| Stake recovered | 16.22 | 19.24 | +3.02 | +18.6% | 0.0012 | 26/5/17 |
| Oracles filled | 14.5 | 17.4 | +3.0 | +20.4% | 0.0002 | 34/11/3 |
| Abstained | 0.69 | 0.16 | -0.53 | -77.0% | 0.0003 | 3/21/24 |
| Reasoning tokens | 7213 | 6226 | -986 | -13.7% | 0.0062 | 14/34/0 |
| Output tokens | 12116 | 10982 | -1134 | -9.4% | 0.0222 | 15/33/0 |
| Total tokens | 860718 | 827996 | -32722 | -3.8% | 0.6666 | 26/22/0 |
| Wall-clock time (s) | 164.2 | 150.9 | -13.3 | -8.1% | 0.0199 | 16/32/0 |
| Turns | 21.0 | 20.7 | -0.3 | -1.5% | 0.3112 | 26/16/6 |
| API calls | 19.0 | 18.7 | -0.3 | -1.6% | 0.3484 | 26/16/6 |
| First-compile success | 0.993 | 0.993 | +0.000 | +0.0% | n/a | 1/1/46 |
| Wrong-oracle rate | 0.118 | 0.097 | -0.021 | -17.6% | 0.286 | 3/8/37 |
| Cost (USD) | 0.613 | 0.580 | -0.034 | -5.5% | 0.3722 | 20/28/0 |

*Stake recovery: original 67.1%, improved 79.6% (total stake 1160 mutants).*

#### S3s — masked test only  ·  3 runs  ·  n = 48 suites

| Metric | Original | Improved | Δ | Δ% | p (Wilcoxon) | W/L/T |
|:--|--:|--:|--:|--:|--:|:--:|
| Mutation score (%) | 46.72 | 62.04 | +15.32 | +32.8% | 0.0 | 35/6/7 |
| Fault-detection retention | 0.513 | 0.814 | +0.302 | +58.8% | 0.0 | 34/7/7 |
| Stake recovered | 6.98 | 16.15 | +9.17 | +131.4% | 0.0 | 35/5/8 |
| Oracles filled | 13.1 | 16.1 | +3.0 | +23.0% | 0.0 | 38/9/1 |
| Abstained | 1.49 | 0.58 | -0.91 | -60.9% | 0.0001 | 6/27/15 |
| Reasoning tokens | 6724 | 5269 | -1455 | -21.6% | 0.0022 | 13/35/0 |
| Output tokens | 11474 | 9716 | -1758 | -15.3% | 0.0015 | 10/38/0 |
| Total tokens | 753818 | 693097 | -60720 | -8.1% | 0.9509 | 27/21/0 |
| Wall-clock time (s) | 154.4 | 132.0 | -22.4 | -14.5% | 0.0035 | 13/35/0 |
| Turns | 20.1 | 19.7 | -0.4 | -2.0% | 0.1821 | 28/15/5 |
| API calls | 19.1 | 18.7 | -0.4 | -2.1% | 0.1841 | 28/15/5 |
| First-compile success | 1.000 | 0.951 | -0.049 | -4.9% | n/a | 0/3/45 |
| Wrong-oracle rate | 0.382 | 0.111 | -0.271 | -70.9% | 0.0002 | 5/22/21 |
| Cost (USD) | 0.543 | 0.488 | -0.054 | -10.0% | 0.0355 | 16/32/0 |

*Stake recovery: original 28.9%, improved 66.8% (total stake 1160 mutants).*

**Alternative scoring — keep only suites where both arms pass (mutation score):**

| Setting | Original | Improved | Δ | p (Wilcoxon) | W/L/T |
|:--|--:|--:|--:|--:|:--:|
| S1s | 67.06 | 69.81 | +2.75 | 0.001 | 28/7/11 |
| S2s | 65.95 | 69.86 | +3.90 | 0.0003 | 28/6/11 |
| S3s | 58.13 | 65.66 | +7.53 | 0.0006 | 23/4/8 |

**Reproducibility — per-suite Δ mutation score, Spearman ρ across the three runs:**

| Setting | Per-run mean Δ | Pairwise ρ | Mean ρ |
|:--|:--|:--|--:|
| S1s | +3.81 +2.16 +3.32 | +0.39, +0.32, +0.44 | +0.38 |
| S2s | +6.02 +4.68 +4.55 | +0.29, +0.44, +0.44 | +0.39 |
| S3s | +15.90 +13.60 +16.46 | +0.54, +0.52, +0.63 | +0.56 |

---

## mask-first

mask **then** improve — the control; the improver never saw an oracle.  
*Usable suites: n = 47.*

#### S1s — whole project readable  ·  3 runs  ·  n = 47 suites

| Metric | Original | Improved | Δ | Δ% | p (Wilcoxon) | W/L/T |
|:--|--:|--:|--:|--:|--:|:--:|
| Mutation score (%) | 63.39 | 64.36 | +0.97 | +1.5% | 0.309 | 22/12/13 |
| Fault-detection retention | 0.825 | 0.822 | -0.003 | -0.4% | 0.829 | 18/12/17 |
| Stake recovered | 15.44 | 15.63 | +0.19 | +1.2% | 0.6435 | 18/12/17 |
| Oracles filled | 13.4 | 14.4 | +1.0 | +7.6% | 0.0002 | 28/7/12 |
| Abstained | 0.35 | 0.39 | +0.04 | +10.0% | 0.8314 | 11/12/24 |
| Reasoning tokens | 7621 | 6984 | -636 | -8.3% | 0.2444 | 19/28/0 |
| Output tokens | 12402 | 11235 | -1167 | -9.4% | 0.1657 | 21/26/0 |
| Total tokens | 833688 | 781625 | -52064 | -6.2% | 0.5749 | 26/21/0 |
| Wall-clock time (s) | 166.2 | 153.8 | -12.4 | -7.5% | 0.3463 | 22/25/0 |
| Turns | 20.8 | 20.5 | -0.3 | -1.3% | 0.503 | 25/14/8 |
| API calls | 18.7 | 18.4 | -0.3 | -1.4% | 0.5485 | 25/14/8 |
| First-compile success | 1.000 | 0.993 | -0.007 | -0.7% | n/a | 0/1/46 |
| Wrong-oracle rate | 0.099 | 0.106 | +0.007 | +7.1% | 1.0 | 7/6/34 |
| Cost (USD) | 0.616 | 0.572 | -0.044 | -7.2% | 0.3914 | 20/27/0 |

*Stake recovery: original 72.8%, improved 73.7% (total stake 997 mutants).*

#### S2s — class under test only  ·  3 runs  ·  n = 47 suites

| Metric | Original | Improved | Δ | Δ% | p (Wilcoxon) | W/L/T |
|:--|--:|--:|--:|--:|--:|:--:|
| Mutation score (%) | 61.52 | 61.24 | -0.27 | -0.4% | 0.6359 | 19/14/14 |
| Fault-detection retention | 0.805 | 0.791 | -0.014 | -1.8% | 0.8446 | 17/14/16 |
| Stake recovered | 14.00 | 14.01 | +0.01 | +0.1% | 0.9099 | 16/14/17 |
| Oracles filled | 13.1 | 14.1 | +1.0 | +7.5% | 0.0005 | 31/8/8 |
| Abstained | 0.41 | 0.50 | +0.09 | +20.7% | 0.2768 | 13/9/25 |
| Reasoning tokens | 6313 | 6324 | +11 | +0.2% | 0.6954 | 22/25/0 |
| Output tokens | 10580 | 10397 | -183 | -1.7% | 0.5534 | 22/25/0 |
| Total tokens | 705356 | 732265 | +26909 | +3.8% | 0.072 | 28/19/0 |
| Wall-clock time (s) | 148.8 | 143.7 | -5.1 | -3.5% | 0.4525 | 19/28/0 |
| Turns | 19.1 | 19.8 | +0.6 | +3.4% | 0.0155 | 28/11/8 |
| API calls | 17.1 | 17.8 | +0.6 | +3.8% | 0.0127 | 28/11/8 |
| First-compile success | 1.000 | 1.000 | +0.000 | +0.0% | n/a | 0/0/47 |
| Wrong-oracle rate | 0.106 | 0.113 | +0.007 | +6.7% | 0.6603 | 7/7/33 |
| Cost (USD) | 0.535 | 0.537 | +0.002 | +0.4% | 0.6415 | 25/22/0 |

*Stake recovery: original 66.0%, improved 66.1% (total stake 997 mutants).*

#### S3s — masked test only  ·  3 runs  ·  n = 47 suites

| Metric | Original | Improved | Δ | Δ% | p (Wilcoxon) | W/L/T |
|:--|--:|--:|--:|--:|--:|:--:|
| Mutation score (%) | 48.63 | 54.74 | +6.10 | +12.6% | 0.071 | 24/14/9 |
| Fault-detection retention | 0.542 | 0.657 | +0.115 | +21.2% | 0.1717 | 20/16/11 |
| Stake recovered | 7.32 | 10.08 | +2.76 | +37.7% | 0.1103 | 21/14/12 |
| Oracles filled | 11.7 | 12.9 | +1.2 | +10.7% | 0.0001 | 30/10/7 |
| Abstained | 1.38 | 1.24 | -0.13 | -9.8% | 0.3564 | 13/15/19 |
| Reasoning tokens | 6226 | 6147 | -78 | -1.3% | 0.1895 | 17/30/0 |
| Output tokens | 10558 | 10079 | -479 | -4.5% | 0.3684 | 22/25/0 |
| Total tokens | 652461 | 639123 | -13338 | -2.0% | 0.7832 | 23/24/0 |
| Wall-clock time (s) | 143.9 | 137.3 | -6.6 | -4.6% | 0.5464 | 21/26/0 |
| Turns | 18.5 | 18.4 | -0.2 | -0.9% | 0.5614 | 20/17/10 |
| API calls | 17.5 | 17.4 | -0.1 | -0.8% | 0.5412 | 20/17/10 |
| First-compile success | 0.986 | 0.979 | -0.007 | -0.7% | n/a | 2/2/43 |
| Wrong-oracle rate | 0.348 | 0.206 | -0.142 | -40.8% | 0.025 | 8/13/26 |
| Cost (USD) | 0.489 | 0.472 | -0.017 | -3.5% | 0.9494 | 22/25/0 |

*Stake recovery: original 34.5%, improved 47.5% (total stake 997 mutants).*

**Alternative scoring — keep only suites where both arms pass (mutation score):**

| Setting | Original | Improved | Δ | p (Wilcoxon) | W/L/T |
|:--|--:|--:|--:|--:|:--:|
| S1s | 67.03 | 67.85 | +0.82 | 0.0218 | 22/8/16 |
| S2s | 65.08 | 65.48 | +0.40 | 0.517 | 18/12/15 |
| S3s | 61.10 | 60.40 | -0.71 | 0.9317 | 13/11/10 |

**Reproducibility — per-suite Δ mutation score, Spearman ρ across the three runs:**

| Setting | Per-run mean Δ | Pairwise ρ | Mean ρ |
|:--|:--|:--|--:|
| S1s | +0.88 +0.61 +1.41 | -0.00, -0.04, -0.06 | -0.03 |
| S2s | -3.38 +2.81 -0.26 | -0.17, -0.20, +0.03 | -0.11 |
| S3s | +4.59 +7.09 +6.64 | +0.39, +0.60, +0.45 | +0.48 |

---
