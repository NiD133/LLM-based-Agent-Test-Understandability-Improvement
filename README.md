# Replication package — LLM-based test understandability improvement

This file explains how the subjects were selected, where every measurement
lives and how to read the files. One folder per task; the two steps that were
done by hand are marked as such.

```
agent_improvement/     Steps 1-5: class selection, EvoSuite, measurement, gate, split,
                       agent-based understandability improvement, behaviour check
  config.yaml            every step toggle and tool setting (JDK homes are read from .env)
  select_classes.py      Step 1 candidate classes
  scripts/               the pipeline (main.py and the modules it calls)
  prompts/               the two prompt templates of the improvement task
  .claude/ .agents/      the compile-check / repair-loop / build-project skills given to the agents
  data/                  subjects, baseline, original, improved (sections 1-4 below)
feature_analysis/      RQ1 / RQ3: RefactoringMiner + GumTree + JavaParser on every accepted pair, heatmaps
stability_check/       RQ1: 362 test cases improved three times per model, behaviour + Jaccard
human_evaluation/      RQ2: which tests were shown in which survey, the 15 surveys and their answers, analysis
downstream_tasks/      RQ4: oracle generation on EvoSuite suites (improve-first and remove-first)
tools/                 EvoSuite, JaCoCo, PIT, JUnit, GumTree, RefactoringMiner, JavaParser (shared)
local_workplace/       the 14 subject projects, built (not in git; see section 10)
.env                   API credentials and JDK homes (not in git; copy .env.example)
.env.example           the variables .env must define
requirements.txt
```

Paths in this file are relative to the repository root. Every script resolves
its own paths from its location, so the repository can be moved as a whole.

## Before you start

All data and results in this package can be read without running anything.
To run any script, do these two steps first:

1. **Create `.env`.** Copy `.env.example` to `.env` at the repository root and
   fill in the JDK homes (`JDK_8_HOME`, `JDK_11_HOME`, `JDK_21_HOME`; JDK 8
   builds the subjects and runs EvoSuite, JDK 11 runs every measurement, JDK 21
   runs RefactoringMiner, GumTree and the oracle-masking helper) and, only if you
   intend to call the agents (Step 4, stability
   runs, RQ4 oracle generation), the credentials `CLAUDE_CODE_OAUTH_TOKEN` or
   `ANTHROPIC_API_KEY`, and `CODEX_CLI_PATH`. Every config file
   refers to the JDKs through these variables; nothing else is machine-specific.
2. **Install the Python packages.** Python 3.11 or newer, then

   ```bash
   pip install -r requirements.txt
   ```

Steps 1–5, `stability_check/run_stability.py` and the RQ4 measurements also need
Maven and `git` on `PATH` and a built checkout of each subject project in
`local_workplace/` (section 10). The analysis scripts (`feature_analysis/`,
`human_evaluation/analyze_human_eval.py`, `downstream_tasks/.../summarize*.py`)
need only the JDK 21 and the Python packages.

## 1. From 14 projects to 140 classes

| # | Step | Input | Script / action | Output | Count |
|---|------|-------|-----------------|--------|------:|
| 1 | Project list | — | written by hand | `agent_improvement/data/subjects_14.json` | 14 projects |
| 2 | Candidate classes | `agent_improvement/data/subjects_14.json`, the checkouts in `local_workplace/` | `agent_improvement/select_classes.py` | `agent_improvement/data/classes_selected.json` | 764 classes |
| 3 | Subject sample | `agent_improvement/data/classes_selected.json` | **by hand**: at most 25 classes per project | `agent_improvement/data/dataset.json` | 291 classes |
| 4 | EvoSuite + suite measurement | `agent_improvement/data/dataset.json` | `agent_improvement/scripts/main.py` Steps 1, 2a, 2b | `agent_improvement/data/baseline/` | 291 classes measured |
| 5 | Adequacy gate | `agent_improvement/data/baseline/` | `agent_improvement/scripts/main.py` Step 2c (`filter_out`) | `agent_improvement/data/tests_for_improvements.gate_output.json`, `agent_improvement/data/original/` | 147 passed, 144 failed |
| 6 | Final subject set | gate output | **by hand**: 7 classes removed | `agent_improvement/data/tests_for_improvements.json` | 140 classes |
| 7 | Test cases | `agent_improvement/data/original/` | `agent_improvement/scripts/main.py` Step 3 (`split_and_measure_cases`) | `agent_improvement/data/original/<project>/{manual,auto}/testcases/` | 280 suites → 5,760 cases |

### Step 2 — candidate classes (`agent_improvement/select_classes.py`)

A production class under `src/main/java` is a candidate when all four hold:

* physical LOC > 50 (non-blank lines after stripping comments)
* McCabe cyclomatic complexity > 5 (file level)
* at least one branch (`if`, `switch`/`case`, ternary)
* a developer-written test exists (`FooTest.java`, `TestFoo.java`, `FooTests.java`, …)

`agent_improvement/data/classes_selected.json` lists the 764 candidates with their `loc`, `cc`,
`branches` and the path of the matching manual test.

### Step 3 — the 291 subjects (`agent_improvement/data/dataset.json`)

From the candidates, at most 25 classes per project were picked by hand
(commons-text has 31; four projects had fewer than 25 candidates and were taken
whole). There is no script for this step. `agent_improvement/data/dataset.json` is the file every
later step reads (`scripts/main.py --subjects data/dataset.json`); each entry
pairs `class_paths[i]` with `test_paths[i]`.

### Step 4 — EvoSuite and suite-level measurement (`agent_improvement/data/baseline/`)

For each of the 291 classes, EvoSuite 1.2.0 generated a test suite (180 s per
class), and both the developer-written suite and the EvoSuite suite were
measured with JaCoCo 0.8.12 (line and branch coverage) and PIT 1.23.0
(mutation score). EvoSuite produced a usable suite for 269 of the 291 classes;
the remaining 22 are recorded as failures in their `gate_result.json`.

### Step 5 — the gate (`filter_out`)

A class passes when **both** suites reach 60 % line coverage, 60 % branch
coverage and a 40 % mutation score. The decision for every class is in
`agent_improvement/data/baseline/<project>/_meta/<Class>/gate_result.json`. The pipeline writes the
result of this step as `agent_improvement/data/tests_for_improvements.gate_output.json`:

* `projects`: the 147 classes that passed
* `_excluded`: the 144 classes that failed, each with the failing metrics

This file is the pipeline's output exactly as written; it was re-generated
from `agent_improvement/data/baseline/` with the current code and is identical to the original run.

### Step 6 — the 140 classes actually used (`agent_improvement/data/tests_for_improvements.json`)

`agent_improvement/data/tests_for_improvements.json` is the gate output with **7 classes removed by
hand** from `projects` (their `class_paths` and `test_paths` entries);
`_excluded` is unchanged. The 7 are:

| Project | Class |
|---|---|
| commons-compress | CpioUtil |
| commons-math | EnumeratedRealDistribution |
| commons-math | PolynomialFunctionNewtonForm |
| commons-math | SecantSolver |
| commons-text | StrMatcher |
| commons-text | StrTokenizer |
| jackson-annotations | JsonInclude |

Why: in the first improvement run (Claude Opus 4.8) not every test of these
7 classes was generated, because of API rate limits. Rather than re-run them,
we fixed the subject set to the 140 complete classes, and all three models
(Opus 4.8, Sonnet 4.6, GPT-5.5) were run on exactly these 140.
`preserve_manual_edits: true` in the file tells `filter_out` not to regenerate
it. Step 4 (agent improvement) reads this file and improves only the classes
listed in it.

### Step 7 — test cases (`split_and_measure_cases`)

Each suite of a gate-passing class is split into one file per test method
(JavaParser 3.27.0), and every case is measured with JaCoCo and PIT in the same
way as the suites. This yields 280 suites (140 developer-written, 140 EvoSuite)
and 5,760 cases (2,928 and 2,832), i.e. 6,040 inputs to the improvement step.

## 2. `agent_improvement/data/baseline/` — suite-level measurement of all 291 classes

One folder per project. Every class in `agent_improvement/data/dataset.json` has its two suites and
its gate decision here. Test cases are present **only for the 147 classes that
passed the gate** (the 144 that failed were never improved, so their cases are
not part of the study).

```
baseline/<project>/
  manual/testsuites/<Suite>.java                 developer-written suite, copied from the project
  manual/testsuites/<Suite>_metrics.json         JaCoCo + PIT result of that suite
  auto/testsuites/<Class>_ESTest.java            EvoSuite suite (absent for the 22 EvoSuite failures)
  auto/testsuites/<Class>_ESTest_metrics.json
  manual/testcases/<Suite>/<Suite>_<method>.java              one file per test method   ┐ 147 gate-passing
  manual/testcases/<Suite>/<Suite>_<method>_metrics.json                                  │ classes only
  auto/testcases/<Class>_ESTest/<Class>_ESTest_testNN.java, ..._metrics.json              ┘
  _evosuite_raw/<package>/<Class>_ESTest.java                 raw EvoSuite output
  _evosuite_raw/<package>/<Class>_ESTest_scaffolding.java     needed to compile the ESTest
  _meta/<Class>/gate_result.json                              the 60/60/40 decision
  _meta/<Class>/suite_coverage_comparison.{json,md}           manual vs EvoSuite, side by side
```

### `*_metrics.json` (one per suite or case)

| Field | Meaning |
|---|---|
| `compile_success`, `coverage_available` | whether the test compiled and JaCoCo produced a report |
| `line_covered`, `line_missed`, `line_pct` | JaCoCo line coverage of the class under test |
| `branch_covered`, `branch_missed`, `branch_pct` | JaCoCo branch coverage |
| `covered_lines`, `missed_lines` | the line numbers behind the percentages |
| `branches_by_line` | per line: `{line, covered_branches, missed_branches}` |
| `mutants_total`, `mutants_killed`, `mutation_score_pct` | PIT result |
| `mutation_status_counts` | PIT statuses: `KILLED`, `SURVIVED`, `NO_COVERAGE`, … |
| `killed_mutants` | every killed mutant: mutated class, method, line, mutator |
| `test_type`, `source`, `method_name` | `suite`/`case`, `manual`/`evosuite`, and the test method for a case |

`mutation_score_pct` is `null` when PIT could not produce a report; the error
text is in `mutation_error`. `*_path` fields point to intermediate files on the
machine that ran the measurement and are not part of the package.

### `_meta/<Class>/gate_result.json`

```json
{"passed": true, "on_fail": "skip_class",
 "manual_suite": {"line_pct": {"value": 92.1, "threshold": 60.0, "ok": true}, "branch_pct": {...}, "mutation_score_pct": {...}},
 "auto_suite":   {...same three metrics...},
 "reason": "all enabled checks met"}
```

### Reading it

```python
import json, pathlib
root = pathlib.Path("agent_improvement/data/baseline")
for gate in root.glob("*/_meta/*/gate_result.json"):
    g = json.load(open(gate))
    print(gate.parts[-3], gate.parts[-2], g["passed"], g["reason"])

m = json.load(open(root / "commons-cli/manual/testsuites/CommandLineTest_metrics.json"))
print(m["line_pct"], m["branch_pct"], m["mutation_score_pct"], len(m["killed_mutants"]))
```

## 3. `agent_improvement/data/original/` — the 140 classes the study improves

Same file layout as `agent_improvement/data/baseline/`, restricted to the 140 classes in
`agent_improvement/data/tests_for_improvements.json`, and with test cases for every class:

```
original/<project>/
  manual/testsuites/<Suite>.java, <Suite>_metrics.json
  auto/testsuites/<Class>_ESTest.java, <Class>_ESTest_metrics.json
  manual/testcases/<Suite>/<Suite>_<method>.java, <Suite>_<method>_metrics.json
  auto/testcases/<Class>_ESTest/<Class>_ESTest_testNN.java, <Class>_ESTest_testNN_metrics.json
  _meta/<Class>/gate_result.json, suite_coverage_comparison.{json,md}
```

280 suites and 5,760 cases, 6,040 `.java` files in total. These are the
**inputs** of the improvement step and the **baseline** of the behaviour check:
an improved test is accepted only if it covers the same lines and branches and
kills the same mutants as the `_metrics.json` next to its original.

```python
# every improvement input, with its baseline metrics
for java in pathlib.Path("agent_improvement/data/original").glob("*/*/test*/**/*.java"):
    metrics = java.with_name(java.stem + "_metrics.json")
    ...
```

## 4. `agent_improvement/data/improved/` — one folder per model, one folder per test

```
improved/<model>/<project>/<src>/testsuites/<Suite>/
improved/<model>/<project>/<src>/testcases/<Suite>/<Case>/
    <Name>.java            the improved test (same file name as the original)
    original/<Name>.java   the original test the agent was given
    prompt.txt             the user message of turn 1
    trace.txt              readable transcript of the agent session (turns, tool calls, compile results)
    state.json             how the session ended
    metrics.json           the behaviour check: improved vs original
```

`<model>` is `opus-4.8`, `sonnet-4.6` or `gpt-5.5`; `<src>` is `manual` or
`auto`. Each model folder holds the same 6,040 tests. The `Case` folder name is
the test method (`testSingleOption`) for developer-written tests and `testNN`
for EvoSuite tests.

### `state.json`

| Field | Meaning |
|---|---|
| `status` | `COMPILE_SUCCESS`, `COMPILE_FAIL`, `MAX_ATTEMPTS`, `LLM_ERROR` — how the agent session ended |
| `attempts_used`, `agent_compile_attempts` | compile/repair rounds the agent used |
| `model`, `provider_model`, `actual_provider_model`, `prompt_version` | what was run |
| `initial_prompt` | the turn-1 user message (same text as `prompt.txt`) |
| `messages` | the session's messages |
| `runtime_observability.token_usage` | input/output/cache tokens (Claude runs only; Codex records none) |
| `project_id`, `target_class`, `suite_class`, `test_id`, `test_category`, `is_evosuite` | what the test is |
| `*_path`, `agent_sandbox`, `original_java`, `last_java_path` | paths on the machine that ran the session |

### `metrics.json`

| Key | Meaning |
|---|---|
| `compile_status` | result of compiling the improved test in the measurement step (independent of the agent's own compile) |
| `baseline` | the original test's metrics — same fields as a `*_metrics.json` in `agent_improvement/data/original/` |
| `improved` | the improved test's metrics, same fields |
| `comparison.deltas` | `improved − baseline` for `line_pct`, `branch_pct`, `mutation_score_pct` |
| `comparison.covered_lines_diff` | `only_in_improved`, `only_in_baseline`, `common_count` |
| `comparison.killed_mutants_diff` | `only_in_improved`, `only_in_baseline` |
| `comparison.verdict.is_exact_match` | **the acceptance flag**: same covered lines, same covered branches, same killed mutants |
| `comparison.verdict.is_superset_or_equal` | improved kills at least the baseline's mutants |

### `improved/summary/` — the behaviour-check aggregate

Written by `agent_improvement/scripts/summarize_improvements.py` from the files above; no JVM needed.

Produced by `pipeline_control.generate_summary: true` in `agent_improvement/config.yaml`
(`agent_improvement/scripts/main.py` calls `agent_improvement/scripts/summarize_improvements.py`).

| File | Content |
|---|---|
| `summary.md` | one table: accepted (exact-match) suites and cases per model, by test origin, with totals — the paper's "accepted tests" table. Computed from `comparison.verdict.is_exact_match` of every `metrics.json` |
| `feature_analysis_input.json` | `{"tests": [...]}` — one entry per (model, test) whose improvement is exact match (17,190 entries: 5,810 Opus, 5,772 Sonnet, 5,608 GPT-5.5). Each entry: `model`, `project`, `src`, `gran`, `suite`, `case`, `class_fqn`, `original` and `improved` (paths of the two `.java` files, relative to the repository root), `exact_in_all_models`. The work list of `feature_analysis/feature_analysis.py` (RQ1) |
| `suite_cases.json` | `{"units": [...]}` — one entry per (model, project, src, suite), 840 in total, with `suite_exact`, `n_cases`, `n_cases_exact`, `case_exact_fraction`, `non_exact_cases`, `both_exact` and `passes` (= suite exact AND ≥ 90 % of its cases exact). 719 units pass: 245 Opus, 236 Sonnet, 238 GPT-5.5. `feature_analysis/plot_heatmaps.py` pairs suite and cases only for passing units (RQ3) |

### Reading it

```python
import json, pathlib
root = pathlib.Path("agent_improvement/data/improved/sonnet-4.6")
accepted = total = 0
for m in root.glob("*/*/test*/**/metrics.json"):
    j = json.load(open(m)); total += 1
    accepted += bool(j["comparison"]["verdict"]["is_exact_match"])
print(accepted, "/", total)

# or read the accepted pairs the analysis uses
pairs = json.load(open("agent_improvement/data/improved/summary/feature_analysis_input.json"))["tests"]
```

## 5. `feature_analysis/` — which changes did the agents make (RQ1, RQ3)

```
feature_analysis/
  config.yaml            input tree, output folder, tool paths, JDK, worker count
  feature_analysis.py    runs the three detectors on every accepted (original, improved) pair
  plot_heatmaps.py       draws the seven paper figures
  lib/                   shared code: common.py (subprocess, JDK detection, JSON), gumtree.py +
                         comment_units.py + comment_postprocess.py + java/BatchParse.java (GumTree,
                         comment changes only), refactoringminer.py, javaparser_blocks.py (blank-line
                         separation), summary.py (merge), feature_registry.py (feature names and order)
  out/feature_analysis/  per test: diff/<test>/structural_diff/ (raw tool output), comments/, blocks/,
                         per_test/<test>.json; aggregated: feature_by_test.csv, feature_frequency.csv
  out/heatmaps/          the figures (below)
```

Input: `agent_improvement/data/improved/summary/feature_analysis_input.json`. Three detectors with
non-overlapping scopes: RefactoringMiner (refactorings such as Variable Rename,
Extract Variable, Extract Method), GumTree with the comment-aware `java-jdtc`
parser (line, block and Javadoc comments added/updated or deleted), JavaParser
(blank-line separation inside `@Test` bodies). `feature_by_test.csv` has one row
per test with the features found; `feature_frequency.csv` has, per model × origin ×
granularity, the share of tests in which each feature occurs at least once.

```bash
python3 feature_analysis/feature_analysis.py        # ~5 min per 1,000 pairs with 16 workers
python3 feature_analysis/plot_heatmaps.py           # → feature_analysis/out/heatmaps/
```

| Figure | Content |
|---|---|
| `heatmap_manual_testcases_full.pdf`, `heatmap_auto_testcases_full.pdf` | every feature seen in at least one developer-written / EvoSuite test case of at least one model |
| `heatmap_manual_testcases.pdf`, `heatmap_auto_testcases.pdf` | the features present in ≥ 2 % of the cases of at least one model |
| `heatmap_combined_testcases.pdf` | both origins side by side (the paper's RQ1 figure) |
| `heatmap_rq3_propagation.pdf` | for each feature found at suite level, in what share of that suite's case-level improvements it also appears (RQ3) |
| `heatmap_rq3_direction_gt50.pdf` | suite-only / case-only / shared, where a feature counts at case level when it is in more than 50 % of the suite's cases (RQ3) |

The RQ3 figures use only the 719 suite units with `passes = true` in
`agent_improvement/data/improved/summary/suite_cases.json`.

## 6. `stability_check/` — repeated runs on 362 test cases (RQ1)

```
stability_check/
  select_sample.py                draws the sample from agent_improvement/data/improved (deterministic, seed 20260916)
  data/sample_362.json            the 362 cases: project, suite, case, class, paths, SLOC, input hashes
  data/sample_summary.txt         how the sample follows the eligible pool (project, origin, SLOC)
  run_stability.py                re-runs the improvement (Step 4) and measurement (Step 5) for the sample;
                                  also holds its helpers (config, main.py inputs, session state, quota, fingerprints)
  config.yaml                     sample file, number of runs, models, output folder
  results/run1/, run2/            the two re-runs: <model>/<project>/<src>/testcases/<Suite>/<case>/
                                  with the improved test, original/, state.json, metrics.json
  results/analysis/               structural diffs of the re-runs (feature_analysis output)
  analyse_stability_results.py    behaviour preservation + element-level Jaccard → results/stability.md, .json
```

**Sample.** Population: the 5,760 test cases of the main study. A case is
eligible when, for all three models, the same original was improved, the
improvement compiled, PIT ran, the behaviour check passed (exact match) and the
baseline metrics are complete: 5,202 cases. From these, 362 (95 % confidence,
±5 % margin for 6,040 tests) were allocated proportionally over project × origin;
within each stratum the cases are sorted by SLOC and one case is taken per
segment, preferring test classes and CUTs used least so far. The draw is
deterministic: `select_sample.py` reproduces `sample_362.json` from the data
in this repository.

**Runs.** The main study's improvements are run "main"; `run_stability.py`
produced run1 and run2 with the same prompt, skills, settings and drivers
(`agent_improvement/scripts/main.py` Steps 4 and 5, redirected into `results/run<k>/` through
environment variables). Codex sessions carry no token usage.

**Analysis (`analyse_stability_results.py`).** Behaviour preservation: the exact-match verdict in
each re-run's `metrics.json`. Consistency: for every (model, case) and each of
the three run pairs, the Jaccard similarity of the two change sets, where two
changes are the same when they have the same feature type and affect the same
element of the original test. Changes are the RefactoringMiner refactorings and
GumTree comment edits of the structural diffs, anchored on the original file.

```bash
python3 stability_check/select_sample.py --dry-run     # show the pool and the sample statistics
python3 stability_check/run_stability.py --status      # what the runs contain
python3 stability_check/analyse_stability_results.py --skip-run   # the report from the existing diffs
```

`results/stability.md`: 2,165 of 2,172 re-run improvements preserve behaviour
(99.68 %); mean Jaccard 0.696 over all models (Opus 4.8 0.797, Sonnet 4.6 0.602,
GPT-5.5 0.688).

## 7. `human_evaluation/` — the human study (RQ2)

```
human_evaluation/
  QualificationTest/Qualification_Test.qsf      the screening questionnaire (10 JUnit multiple-choice questions)
  QualificationTest/qualification_scores.csv    one row per participant (P01-P49): first-attempt score, role,
                                                years of experience, the ten answers
  select_human_eval.py        stage 1-2: picks the 180 pairs and lays them out over 15 surveys x 2 parts
  human_eval_selection.json   its output: the 180 generated pairs with model, origin, project, suite, case,
                              LOC bucket, features, survey, part, slot, phase (floor / random), Test A side
  human_eval_summary.md       the same selection for a reader: quotas, question order, feature coverage,
                              the pair list of every survey
  analysis/manual_replacements.csv
                              stage 3: the 57 slots replaced by hand before deployment
                              (generated pair -> shipped pair, same model / origin / LOC bucket flags)
  survey_allocation.md        what the 15 shipped surveys contain: totals, feature coverage, and per survey
                              and part every slot's test, model, origin, LOC, features, screen and A/B side
  FinalSurveys/Agent_<N>_Understandability.qsf            the 15 surveys as shipped
  FinalSurveys/Agent_<N>_Understandability_responses.csv  the Qualtrics responses, de-identified
                              (IP, location, name and e-mail columns removed; Prolific IDs replaced by P01-P49)
  identify_stimuli.py         recovers, from the shipped .qsf files, which test each slot shows
  analysis/design_map.csv     its output, one row per slot; analyze_human_eval.py reads it
  analyze_human_eval.py       the numbers the paper reports -> analysis/results.md, analysis/results.json
```

**How the 180 pairs were chosen.** Candidates are the behaviour-preserving
(exact-match) case-level improvements. Stage 1, coverage: for each of the six
model × origin columns, every feature that occurs in ≥ 2 % of that column's test
cases (a row of the RQ1 heatmap) is carried by at least three pairs of the
column; this fixed 56 pairs. Stage 2, random: the remaining 124 pairs were drawn
uniformly inside model × origin × length strata (short ≤ 15, medium 16–30,
long > 30 LOC of the original test without blank, comment, package and import
lines) with seed 20260924, and laid out so that Part 1 shows the two versions of
a pair at least four screens apart (three pairs original-first) and Part 2 puts
the improved version as Test A in three of six pairs. Stage 3, by hand: before
deployment 57 pairs were replaced by pairs from the same stratum
(`analysis/manual_replacements.csv`). `survey_allocation.md` shows the result:
90 developer-written / 90 EvoSuite pairs, 29–31 per model × origin column, all
14 projects, and every recurrent feature covered in its column.

The selection script is kept as the record of stages 1–2. Its input
`feature_by_test.csv` was regenerated after the draw (comment features were
re-defined), so running it today yields a different set that satisfies the same
rules; the surveys that were actually shipped are the `.qsf` files, and
`identify_stimuli.py` recovers their content.

```bash
python3 human_evaluation/identify_stimuli.py       # -> analysis/design_map.csv
python3 human_evaluation/analyze_human_eval.py     # -> analysis/results.md, results.json
```

**Participants.** 100 people took the qualification test; the 49 who entered
the main study all scored ≥ 7/10 on their first attempt (`qualification_scores.csv`).
Four failed an attention check and are excluded from the analysis.

## 8. `downstream_tasks/` — oracle generation on EvoSuite suites (RQ4)

```
downstream_tasks/
  oracle_generation_evosuite/
    select_suites.py              how the 50 suites were chosen (pool → screen → stratified pick)
    pool_screened.json            the 127 candidates with the screen outcome used for the study
    maskfirst_50/                 study A: remove oracles → improve → regenerate oracles (50 suites)
    improvefirst_49/              study B: RQ1 improvement → remove oracles → regenerate (49 suites)
    summarize_all.py, results_md.py   both studies, three settings → RESULTS.md, COMBINED_REPORT.txt
  oracle_masking/
    mask_v2.py, javatools/UnitMap.java   oracle removal (JavaParser): every assertion / verify / fail /
                                         expected-exception is deleted, test methods are kept
  prompts/                        the agent prompts of the oracle-generation task:
                                  downstream_system_prompt_s1_open.txt, _s2_cut_only.txt, _s3_test_only.txt
                                  (one per setting) and downstream_user_message_unmarked.txt
  ds_common.py                    shared helpers: config, agent session (Claude Agent SDK), sandbox skills,
                                  read-scope enforcement, token/effort accounting, oracle counting
```

**Subjects — `select_suites.py`.** Deterministic, no random seed, three steps:

1. *Candidate pool* (127 suites): every EvoSuite suite (`src = auto`, `gran = testsuites`)
   whose `sonnet-4.6` improvement preserves behaviour (`is_exact_match` in
   `agent_improvement/data/improved/summary/per_test.csv`), with its original suite
   (section 3), its EvoSuite scaffolding (`data/baseline/<project>/_evosuite_raw/`), the
   improved suite, LOC, oracle count and the baseline coverage and mutation figures.
2. *Screen*: each original suite is compiled and run once (javac + JUnitCore under the
   EvoSuite runtime agent, JDK 11); a suite whose original does not compile or does not pass
   cannot serve as a subject. The outcome used for the study is recorded in
   `pool_screened.json` (126 pass, 1 excluded).
3. *Stratified allocation* over the 126: per-project quota `max(1, min(6, round(share × 50)))`,
   adjusted to exactly 50 by adding to (or removing from) the largest pools first; within a
   project the suites are sorted by LOC and taken at evenly spaced ranks, so each project
   contributes its smallest, largest and intermediate suites. Output `maskfirst_50/selected50.json`.

```bash
# reproduce selected50.json from the recorded screen (no JDK needed)
python3 downstream_tasks/oracle_generation_evosuite/select_suites.py --screen-file downstream_tasks/oracle_generation_evosuite/pool_screened.json --check
# redo everything including the compile-and-run screen (JDK_11_HOME, built local_workplace/)
python3 downstream_tasks/oracle_generation_evosuite/select_suites.py
```

*Screening note.* The screen run on 2026-09-20 reported `StrMatcher_ESTest` (commons-lang) as
not compiling and excluded it. That result came from the screening script, which located the
suite file by name across the original suites and picked the commons-text suite of the same
name, which does not compile against the commons-lang scaffolding. Screened with the correct
file, the commons-lang suite compiles and passes (24 tests). `pool_screened.json` keeps the
outcome as it was used for the study, with a `screen_note` on that record; a fresh screen today
yields 127 passes, a different allocation (commons-lang 6 / commons-io 5 instead of 5 / 6) and
five different suites. The study's selection is reproduced with `--screen-file`.

*Hex.* Study B uses 49 suites: `improvefirst_49/selected49.json` is `selected50.json` minus
`Hex_ESTest`. Hex was dropped when study B was set up (2026-09-21) because the exact-match
summary available at the time flagged its RQ1 improvement as killing the same number of
mutants (40 → 40) but not the same set; that summary had been produced with an earlier mutant
key. The released `metrics.json` of that test and `per_test.csv` compare the killed-mutant sets
directly and show that Hex is an exact match (`killed_mutants_diff` empty on both sides). The
exclusion is therefore not required by the selection rule; the study was run on the 49 and was
not re-run with Hex added. The 49 are exact matches: 49/49 compile, same covered lines, same
covered branches, same killed-mutant set.

**Settings.** The agent (`claude-sonnet-4-6`, Claude Agent SDK, at most 80 turns) receives
a suite with all oracles removed and must put them back. Three read scopes, nested:
`S1s` the whole project is readable except the project's own manual test for the class,
`S2s` only the class under test and the masked suite, `S3s` only the masked suite.
Each setting is run three times (`runs/<setting>_run<k>/`).

**Study A — `maskfirst_50/`** (`run_all.sh` runs stages 2–4 after `prep.py`):

| stage | script | output |
|---|---|---|
| 1 remove oracles, measure | `prep.py` | `suites/<S>/{original/<S>.java, masked/<S>.java, gt.json, floor.json, mask_info.json}`, `prep_summary.json` |
| 2 improve the oracle-free suite | `improve.py` | `improve/results.json`, `improve/results/<unit>/` (trace, state, tests), `improved/<S>/<S>.java` |
| 3 consistency gate | `gate.py` | `gate_summary.json` (improved suite covers the same lines and branches and kills the same mutants as its floor); 47 of 49 pass |
| 4 regenerate oracles, 3 settings × 3 runs | `oraclegen.py` | `runs/<label>/results.json` (94 rows = 47 suites × 2 arms), `runs/<label>/results/<unit>/{state.json, trace.txt, thinking.txt, tests/}` |
| summary | `summarize.py`, `per_suite.py`, `combined_table.py` | `FINAL_REPORT.txt`, `PER_SUITE.txt`, `COMBINED_TABLE.txt` |

`gt.json` is the suite with its oracles (ground truth), `floor.json` the same suite with every
oracle removed; `stake` = mutants killed by the ground truth but not by the floor, i.e. what the
oracles are worth.

**Study B — `improvefirst_49/`** (`prep.py`, then `run_all.sh`, then `summarize.py`) mirrors
study A with the order flipped: the improved arm is the RQ1 `sonnet-4.6` improvement with every
oracle removed, so the improver had seen the oracles, which is what the study measures.
`prep.py` masks both arms with the same `mask_all_oracles` and measures each arm's ground truth
and floor (`suites/<S>/{original,improved}/{<S>.java, masked/<S>.java, gt.json, floor.json,
mask_info.json}`; the original arm's `gt.json` / `floor.json` are copied from study A because the
file is byte-identical). `usable.json` lists the 48 suites with a positive stake whose four
measurements all succeeded. `oraclegen.py` is study A's (`runs/<label>/results.json`, 96 rows =
48 × 2); `summarize.py` → `FINAL_REPORT.txt`. Differences from study A that the paper reports:

* No improvement stage and no gate; the RQ1 exact-match check plays that role. Floors are
  therefore per arm; `prep_summary.json` records `same_gt_killed` / `same_floor_killed` per suite,
  and every result row carries the arm's own `arm_gt_mutation_pct`, `arm_floor_mutation_pct`,
  `arm_stake_mutants`, `arm_stake_recovered`, `arm_fault_detection_retention` next to the shared
  (original-suite) ground-truth fields.
* Thinking is requested with `display: "summarized"` (`--no-summarized` turns it off), so
  `thinking.txt` contains the summarised reasoning; study A ran with the default and its
  `thinking.txt` files are empty. The reasoning setting itself (adaptive) is the same.
* Two suites have an arm-asymmetric structure and were kept, since every oracle is masked:
  `DayOfYear_ESTest` (24 → 25 test methods, 33 → 34 oracles; the improver added a test) and
  `CodecEncoding_ESTest` (25 → 23 oracles). Exact match constrains covered lines, branches and
  killed mutants, not the number of test methods or oracles. `prep_summary.json` records
  `*_n_tests` and `*_n_oracles` per arm.

**Reading `runs/<label>/results.json`.** One row per (suite, arm). The main fields:
`rebuilt_mutation_pct`, `rebuilt_n_killed` (the regenerated suite), `gt_mutation_pct`,
`floor_mutation_pct`, `stake_mutants`, `stake_recovered`, `fault_detection_retention`
(= killed by the regenerated suite / killed by the ground truth), `oracles_in_filled`,
`abstained`, `filled_test_status` (`PASS` = the regenerated oracles hold on the real class;
otherwise the arm is scored at its floor, "policy B"), `wall_clock_s`, `tokens` (SDK usage
including thinking tokens and cost), `turns`/`api_calls`. `quota_exhausted` / `sdk_error`
rows are excluded from every table.

**Results.** `oracle_generation_evosuite/RESULTS.md` (and `COMBINED_REPORT.txt`) hold the
paired original-vs-improved tables for both studies and all three settings: per-suite means
over the three runs, paired Wilcoxon signed-rank tests, win/loss/tie counts and cross-run
Spearman reproducibility, produced by `results_md.py` / `summarize_all.py` from the
`results.json` files.

## 9. `tools/`

| Folder / file | Used by |
|---|---|
| `evosuite-1.2.0.jar`, `evosuite-standalone-runtime-1.2.0.jar` | Step 2a generation; compiling and running EvoSuite tests in every measurement |
| `jacoco/`, `pit/`, `junit/`, `junit5runner/JUnit5Runner.java` | coverage and mutation measurement (Steps 2b, 3, 5; stability; downstream) |
| `javaparser/javaparser-core-3.27.0.jar`, `TestSplitter.java` | test-suite splitting (Step 3), blank-line feature, oracle masking |
| `javaparser/OracleStripper.java` | oracle counting in `downstream_tasks/ds_common.py` |
| `gumtree/`, `refactoringminer/` | `feature_analysis/`, `stability_check/` |

## 10. Re-running

```bash
# all commands from the repository root
python3 agent_improvement/select_classes.py        # Step 2 → agent_improvement/data/classes_selected.json
# edit agent_improvement/config.yaml → pipeline_control: measure_baseline, filter_out, split_and_measure_cases
python3 agent_improvement/scripts/main.py --config agent_improvement/config.yaml --subjects agent_improvement/data/dataset.json
# generate_summary: true writes agent_improvement/data/improved/summary/ at the end of the run
```

Re-running Steps 4–5 regenerates `agent_improvement/data/baseline/`, the gate decisions and a
`agent_improvement/data/tests_for_improvements.gate_output.json`-shaped manifest. Because EvoSuite is
search-based, a fresh run produces different suites and therefore a comparable
but not identical set of gate-passing classes; the suites the paper used are
the ones in `agent_improvement/data/baseline/` and `agent_improvement/data/original/`.

The improvement prompt is fixed in code: the system prompt is
`agent_improvement/prompts/improve_system_prompt.txt`, the user message is
`agent_improvement/prompts/understandability_improvement_constraints.txt`
(`llm_refactor.PROMPT_VERSION`). `generate_improvement_prompts: true` writes the
exact prompt each agent would receive to `agent_improvement/ImprovePrompts/`
without calling any model.

### Before running Steps 1-5 on a new machine

* `.env` and the Python packages as described in "Before you start". The study used
  Corretto 1.8.0_442, Temurin 11 and JDK 21. `agent_improvement/config.yaml`
  refers to the JDKs as `${JDK_11_HOME}` etc.; `main.load_config` expands them, so no
  path is edited in the config itself. `feature_analysis/`, `stability_check/` and
  `downstream_tasks/` read `JDK_21_HOME` / `JDK_11_HOME` from the same file.
* `local_workplace/<project>` must hold a built checkout of each subject project.
  The checkouts the study used are not part of the package; Step 1
  (`clone_and_build`) clones the current head of each repository when the folder
  is missing, which may differ from the versions the study measured.
* Maven and `git` must be on `PATH`. If `JDK_21_HOME` is not set,
  `feature_analysis/` and `stability_check/` look for a JDK 21 by themselves
  (`java_home: ""` in `feature_analysis/config.yaml`).
