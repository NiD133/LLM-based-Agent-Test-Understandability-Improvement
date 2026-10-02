#!/usr/bin/env python3
"""
Oracle counter / masker (deterministic, no agent).

Used by summarize_improvements._n_oracles to count the oracle statements of
every original and improved test (`n_oracles_original` / `n_oracles_improved`
in data/improved/summary/per_test.csv). The masking part is what the count is
defined by: an oracle is one whole statement-leading assertion / verification
call, deleted up to its matching ';'.

Original description:

The downstream task mirrors agent_with_repair but with a different goal: every
ORACLE (assertion / verification statement) in a test is DELETED and replaced
by a placeholder comment; the agent is later asked to FILL the oracles back in,
and the filled test is compiled + coverage-analysed.

This module does ONLY the masking (a deterministic script — NOT the agent). It
is written so it can be:
  • run standalone to produce a REVIEW folder of masked tests for inspection
    (like the generate_improvement_prompts dry-run), and
  • imported by the future build_downstream_tasks step.

Masking rule (per the project owner): remove the WHOLE oracle statement and
replace it with a single line comment. We delete the entire statement
(name … matching ';'), which correctly handles:
  - multi-line assertions
  - lambdas, e.g. assertThrows(E.class, () -> { obj.m(); })  (the inner ';'
    sits at paren-depth ≥ 1 and is NOT treated as the statement terminator)
  - chained calls, e.g. verify(mock).doThing(arg);
  - parens / ';' inside string, char and comment literals are ignored.

What counts as an oracle (statement-leading method call):
  assertX (JUnit 4/5 + Hamcrest assertThat), fail, and the EvoSuite/Mockito
  verification helpers (verifyException, verifyNoMoreInteractions, verify…).
"""
from __future__ import annotations

import re
from typing import List, Tuple


# The comment that replaces each removed oracle. Tagged so the downstream agent
# (and you, during review) can grep for it. {n} = 1-based index within the file.
PLACEHOLDER = "// [ORACLE_{n}] TODO: generate the assertion(s) for this test"

# Two classes of oracle names, to avoid masking a CUT method that happens to be
# called `verify`/`fail`:
#   • DISTINCTIVE — names that only ever mean "assertion" (assert*, the EvoSuite
#     verifyException, Mockito verifyNoMoreInteractions/verifyZeroInteractions).
#     Allowed with ANY (or no) receiver: assertEquals, Assertions.assertTrue, …
#   • AMBIGUOUS — `fail` and `verify` are also plausible CUT method names, so we
#     only treat them as oracles when BARE (static import) or qualified by a
#     known assertion class (Assert/Assertions/MatcherAssert/Mockito/Verify).
#     `someCutObject.verify(...)` is therefore left intact.
_DISTINCT = (r"(?:[A-Za-z_]\w*\s*\.\s*)*"
             r"(?:assert[A-Z]\w*|verifyException|verifyNoMoreInteractions|verifyZeroInteractions)")
_AMBIG = r"(?:(?:Assert|Assertions|MatcherAssert|Mockito|Verify)\s*\.\s*)?(?:fail|verify)"
_ORACLE_CALL = r"(?:" + _DISTINCT + r"|" + _AMBIG + r")\s*\("
# An oracle call begins a statement when preceded by ';', '{', '}', a lambda
# '->' body, or line start. It can ALSO be the right-hand side of an assignment,
# e.g. `final X e = assertThrows(C.class, () -> cut.m());` — here the assert is
# the RHS. We match that too (the `=` boundary) so the WHOLE statement, incl the
# `Type var =` prefix, is removed; otherwise the assertThrows survives masking
# and keeps killing mutants. `(?<![=!<>])=(?!=)` is a real assignment '=', not
# ==, !=, <= or >=.
_ORACLE_RE = re.compile(
    r"(?P<boundary>(?:^|[;{}])\s*|->\s*|(?<![=!<>])=(?!=)\s*)"
    r"(?P<call>" + _ORACLE_CALL + r")",
    re.MULTILINE,
)


def _code_regions(src: str) -> List[bool]:
    """Return a list `is_code[i]` — True when char i is real code (NOT inside a
    string literal, char literal, line comment or block comment). Used so we
    never treat parens/semicolons/keywords inside literals or comments as code.
    """
    n = len(src)
    is_code = [True] * n
    i = 0
    state = "code"  # code | str | char | line | block
    while i < n:
        c = src[i]
        nxt = src[i + 1] if i + 1 < n else ""
        if state == "code":
            if c == '"':
                state = "str"; is_code[i] = False
            elif c == "'":
                state = "char"; is_code[i] = False
            elif c == "/" and nxt == "/":
                state = "line"; is_code[i] = False
            elif c == "/" and nxt == "*":
                state = "block"; is_code[i] = False
        elif state == "str":
            is_code[i] = False
            if c == "\\":
                if i + 1 < n:
                    is_code[i + 1] = False
                i += 2; continue
            if c == '"':
                state = "code"
        elif state == "char":
            is_code[i] = False
            if c == "\\":
                if i + 1 < n:
                    is_code[i + 1] = False
                i += 2; continue
            if c == "'":
                state = "code"
        elif state == "line":
            is_code[i] = False
            if c == "\n":
                state = "code"
        elif state == "block":
            is_code[i] = False
            if c == "*" and nxt == "/":
                is_code[i + 1] = False
                i += 2; state = "code"; continue
        i += 1
    return is_code


def _find_statement_end(src: str, start: int, is_code: List[bool]) -> int:
    """From `start` (index of the oracle name), return the index just AFTER the
    terminating ';' at paren-depth 0, scanning only real code. -1 if not found."""
    depth = 0
    i = start
    n = len(src)
    while i < n:
        if is_code[i]:
            c = src[i]
            if c == "(":
                depth += 1
            elif c == ")":
                depth -= 1
            elif c == ";" and depth <= 0:
                return i + 1
        i += 1
    return -1


def _find_statement_start(src: str, pos: int, is_code: List[bool]) -> int:
    """Scan backward from `pos` to the index just after the previous statement
    boundary (';', '{', '}') at paren-depth 0 in real code, then skip forward
    over whitespace AND any leading comment lines. Used to delete the WHOLE
    statement (including any `Type var =` assignment prefix) when an oracle sits
    on an assignment RHS — WITHOUT swallowing a descriptive `// …` / `/* … */`
    comment that sits ABOVE the statement, so it stays in the masked file and
    reviewers can still check oracle equivalence against it."""
    i = pos - 1
    depth = 0
    start = 0
    while i >= 0:
        if is_code[i]:
            c = src[i]
            if c == ")":
                depth += 1
            elif c == "(":
                depth -= 1
            elif c in ";{}" and depth <= 0:
                start = i + 1
                break
        i -= 1
    # Skip forward over whitespace AND comment characters (is_code=False) so a
    # comment ABOVE the statement is left intact instead of being absorbed into
    # the deleted oracle span. Lands `start` on the first REAL-code char.
    while start < pos and (src[start] in " \t\r\n" or not is_code[start]):
        start += 1
    return start


def _delete_span(source: str, s: int, e: int) -> Tuple[int, int]:
    """For DELETE mode (setting 2): widen an oracle span `[s, e)` so the WHOLE
    physical line vanishes — leading indentation AND the trailing newline — so
    no blank line or stray indent is left behind.

      • start: back to the line start IFF only whitespace precedes the oracle on
        that line (don't eat real code that shares the line).
      • end:   forward over trailing spaces/tabs + one CR?LF (the now-empty
        line). If non-whitespace code follows the ';' on the same line, keep it."""
    ls = source.rfind("\n", 0, s) + 1            # index just after prev newline (0 if none)
    del_start = ls if source[ls:s].strip() == "" else s
    j, n = e, len(source)
    while j < n and source[j] in " \t":
        j += 1
    if j < n and source[j] == "\r":
        j += 1
    if j < n and source[j] == "\n":
        del_end = j + 1                          # swallow the trailing newline → no blank line
    else:
        del_end = e                              # code follows ';' on this line → keep it
    return del_start, del_end


def mask_oracles(source: str,
                 placeholder: str = PLACEHOLDER,
                 mode: str = "comment") -> Tuple[str, int, List[str]]:
    """Replace every oracle statement in `source`.

    `mode`:
      • "comment" (setting 1, default) — replace the statement with a
        `// [ORACLE_n] TODO …` placeholder, KEEPING the statement's leading
        indentation. The agent sees where/how many oracles to fill.
      • "delete"  (setting 2) — delete the statement AND its leading indentation
        AND the trailing newline, leaving NO trace. The agent must infer where
        oracles belong (no positional anchor) — the harder, more realistic task.

    Returns (masked_source, num_masked, removed_snippets). `removed_snippets`
    are the ORIGINAL oracle texts (whitespace-collapsed) in file order — kept for
    the downstream textual-equivalence check, identical for both modes.
    Idempotent-safe (already-masked files contain no oracle calls)."""
    is_code = _code_regions(source)

    # Collect (stmt_start, stmt_end) spans. stmt_start = index of the oracle
    # name (after the boundary char/whitespace); stmt_end = just after ';'.
    spans: List[Tuple[int, int, str]] = []
    for m in _ORACLE_RE.finditer(source):
        call_start = m.start("call")
        if not is_code[call_start]:
            continue  # the match landed inside a string/comment — skip
        end = _find_statement_end(source, call_start, is_code)
        if end == -1:
            continue
        # On an assignment RHS (`Type var = assertThrows(...)`) extend the
        # deletion back over the `Type var =` prefix so the WHOLE statement goes.
        if "=" in m.group("boundary"):
            stmt_start = _find_statement_start(source, call_start, is_code)
        else:
            stmt_start = call_start
        spans.append((stmt_start, end, source[stmt_start:end]))

    if not spans:
        return source, 0, []

    # Drop overlapping spans (keep earliest/widest) so an assignment span that
    # swallows a later statement-leading match isn't double-counted.
    spans.sort()
    deduped: List[Tuple[int, int, str]] = []
    for s, e, snip in spans:
        if deduped and s < deduped[-1][1]:
            continue
        deduped.append((s, e, snip))
    spans = deduped

    # Rebuild the source. comment → insert placeholder at the span; delete →
    # widen to the whole line and insert nothing.
    out = []
    cursor = 0
    removed: List[str] = []
    for idx, (s, e, snippet) in enumerate(spans, start=1):
        if mode == "delete":
            ds, de = _delete_span(source, s, e)
            ds = max(ds, cursor)                 # never back up past the last cut
            out.append(source[cursor:ds])
            cursor = de
        else:
            out.append(source[cursor:s])
            out.append(placeholder.format(n=idx))
            cursor = e
        removed.append(" ".join(snippet.split()))
    out.append(source[cursor:])
    return "".join(out), len(spans), removed
