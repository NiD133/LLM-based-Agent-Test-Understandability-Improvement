---
name: repair-loop
description: Repair a Java JUnit test that failed to compile. Invoke this skill whenever the `compile-check` skill (i.e. `bash compile.sh`) reports `COMPILE_FAIL` with javac errors such as `cannot find symbol`, `package X does not exist`, `incompatible types`, `';' expected`, `method X cannot be applied to given types`, or similar. The skill walks the focused-edit + recompile cycle: read the javac stderr, identify the offending line, consult the CUT source (its path is given in your task) to find the correct API name, Edit ONLY the offending lines (not the whole test), then re-invoke `compile-check`. Stop after `COMPILE_OK`, or after the same error persists for 3 attempts, or when max_turns is exhausted. Do NOT use this skill for runtime test failures — this pipeline does not run tests during the agent session.
---

# Iterative compile-error repair

When the `compile-check` skill reports `COMPILE_FAIL`, walk the
cycle below. Do not abandon the refactoring, do not revert to the original
test, and do not rewrite the test wholesale. Make the smallest possible
local change that fixes the compile error.

## The repair cycle

1. **Read the first error in the stderr.** Cascade errors usually disappear
   once the first one is fixed — start there.
2. **Identify file, line, and symbol** from the `javac` message.
3. **Read the CUT source** (its path is given in your task) to find the real
   API. If you already read it earlier in this session, do NOT re-Read it —
   the file is already in your context.
4. **Use the `Edit` tool** to change ONLY the lines tied to the error.
5. **Run `bash compile.sh` again.**
6. **Repeat** until `COMPILE_OK` or you hit a stopping condition.

**One (Edit → bash compile.sh) cycle = 1 attempt.** Count attempts yourself,
regardless of whether the next `bash compile.sh` reports the SAME error or
a NEW one. The pipeline does not maintain this counter for you.

## Worked example: repairing a renamed method call

Stderr from `compile.sh`:

```
NullReaderTest.java:42: error: cannot find symbol
        reader.getLengthLong();
              ^
  symbol:   method getLengthLong()
  location: variable reader of type NullReader
COMPILE_FAIL (exit=1)
```

Diagnosis: line 42 calls a method that does not exist on `NullReader`.

Workflow:

1. Open the CUT source (its path is in your task) and search for `Length` or
   `get…Long` to find the real method name. Discover it is
   `public long getPosition()`.
2. Edit only line 42:

   ```java
   // Before
   reader.getLengthLong();
   // After
   reader.getPosition();
   ```

3. Run `bash compile.sh`. If it prints `COMPILE_OK`, stop with a one-line
   confirmation.

## Common error patterns and what they usually mean

| stderr fragment | likely cause | fix direction |
|---|---|---|
| `cannot find symbol … method X` | renamed / mistyped method | check CUT, use the real method name |
| `cannot find symbol … variable X` | typo'd local var or removed field | re-introduce or correct the name |
| `cannot find symbol … class X` | missing `import` | add a single `import` line at the top |
| `package X does not exist` | wrong package in `import` | verify the CUT's package, fix the import |
| `incompatible types: A cannot be converted to B` | type mismatch | cast, change var type, or call a converter |
| `';' expected` / `<identifier> expected` | syntax error from a partial Edit | re-read the broken line and fix it |
| `method X in class Y cannot be applied to given types` | wrong overload / arg count | check CUT signature, fix the call |
| `class X is public, should be declared in a file named X.java` | class-vs-file-name mismatch | rename either the class OR the file so they match. The pipeline does NOT rename for you. |

## What NOT to do

- Do NOT re-write the whole test on every failure — keep edits focused on
  the reported lines.
- Do NOT re-Read the output test in full on every cycle — your context
  already has it after the first read.
- Do NOT add new third-party dependencies. Only standard JDK + what the
  original test already imports.
- Do NOT add comments saying "fixed compile error" — the diff is the
  explanation.
- Do NOT silently change runtime behaviour while repairing — same
  assertions, same call sequence, same fixtures. Repair = make-it-compile,
  not improve-some-more.
- Do NOT modify the `package` declaration — Maven discovery will break.
- Do NOT remove `@Test` / `@BeforeEach` / `@AfterEach` / `@BeforeAll` /
  `@AfterAll` annotations.

## Stopping conditions

Stop when **any** of these is true:

- `bash compile.sh` prints `COMPILE_OK` → reply with one line and stop.
- The same error persists after 3 attempts → reply with one line describing
  what's stuck and stop. Further attempts unlikely to help.
- The SDK ends the session because `max_turns` was reached → you do not
  control this; nothing to do.
