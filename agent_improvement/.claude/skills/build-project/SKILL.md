---
name: build-project
description: Build (COMPILE ONLY) the whole Maven project — including the Class Under Test you reconstructed — by running `bash compile.sh` in your working directory. Invoke this skill EVERY time after you Write (or Edit) the CUT file and before you declare the session complete. The skill compiles the project's main + test sources and returns either `COMPILE_OK` (you may stop the session and reply with a one-line confirmation) or `COMPILE_FAIL` followed by the compiler stderr (hand off to the `repair-loop` skill). It does NOT run the tests — it only verifies that everything compiles. Required: the project together with your reconstructed CUT must COMPILE before the pipeline can measure anything, so do NOT end the session on an unverified file.
---

# How to build the project

`compile.sh` sits in your working directory and already knows how to build
this Maven project with the correct JDK, module, and flags. You do NOT need
to construct any `mvn` or `javac` command yourself.

## The command

From your working directory, run exactly:

```bash
bash compile.sh
```

The script runs a **compile-only** Maven build (`test-compile`) over the
whole module — the project's main sources (including the CUT you wrote at the
path in your task) and its test sources — and prints **one of two literal
strings** as the LAST line of stdout:

- `COMPILE_OK` (exit code 0)
- `COMPILE_FAIL (exit=<N>)` followed by the compiler stderr (exit ≠ 0)

Use that final line as your decision signal — do not paraphrase or interpret
the diagnostics beyond what the line tells you.

## Outcome A — success

```
COMPILE_OK
```

Exit code 0. The whole project — including your reconstructed CUT —
compiles. **You are done.** Reply with a short one-sentence confirmation and
stop. Do NOT run `compile.sh` again, do NOT make extra edits.

## Outcome B — failure

```
CutName.java:42: error: cannot find symbol
        ...
COMPILE_FAIL (exit=1)
```

Exit code is non-zero. The stderr points at the exact file, line, and symbol
that failed. Hand the failure to the `repair-loop` skill.

## Things to remember

- This skill only **COMPILES**. It does NOT run the tests, so you will NOT
  see how many tests pass or fail — that is **intentional**. Runtime
  correctness (does the test actually pass, does it cover the code) is
  measured by the pipeline AFTER the session ends, not by you. Do not try to
  game the tests to "pass": write a **faithful** CUT that genuinely
  implements the behaviour the test specifies, and make the project COMPILE.
- The JUnit test and every other project file are READ-ONLY. The CUT you
  write at the path in your task is the ONLY file you create or modify.
- Bash is restricted to `bash compile.sh` only. Do NOT shell out to Python,
  Maven, EvoSuite, or any other tool — those run in a separate post-session
  pipeline step you don't control.
- Do NOT introduce new dependencies or test frameworks. Reuse the project's
  existing classes, interfaces, and types wherever the test implies them.
