# Stability of the improvement across three runs (362 test cases per model)

## Behaviour preservation of the re-runs

| Model | run1 | run2 | both re-runs |
|---|--:|--:|--:|
| Claude Opus 4.8 | 361/362 | 360/362 | 721/724 (99.59%) |
| Claude Sonnet 4.6 | 361/362 | 362/362 | 723/724 (99.86%) |
| GPT-5.5 | 360/362 | 361/362 | 721/724 (99.59%) |
| All | | | 2165/2172 (99.68%) |

## Consistency of the changes (element-level Jaccard)

| Model | main vs run1 | main vs run2 | run1 vs run2 | all pairs | comparisons |
|---|--:|--:|--:|--:|--:|
| Claude Opus 4.8 | 0.791 | 0.801 | 0.800 | **0.797** | 1086 |
| Claude Sonnet 4.6 | 0.616 | 0.588 | 0.602 | **0.602** | 1086 |
| GPT-5.5 | 0.681 | 0.701 | 0.681 | **0.688** | 1068 |
| All | 0.696 | 0.697 | 0.695 | **0.696** | 3240 |
