package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.commons.text.matcher.StringMatcher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link StringSubstitutor} focusing on edge-case / malformed variable
 * expressions (the so-called "weird patterns").  The key rule under test:
 * when an expression cannot be resolved — because the variable name is empty,
 * contains only whitespace, or is itself a nested / unclosed expression — the
 * substitutor must return the original template string unchanged.
 */
public class StringSubstitutorTest_testReplaceWeirdPattens {

    // Values used by the classic substitution scenario (kept for completeness
    // so helper methods from the base class continue to work unchanged).
    private static final String ACTUAL_ANIMAL = "quick brown fox";
    private static final String ACTUAL_TARGET  = "lazy dog";
    private static final String CLASSIC_RESULT   = "The quick brown fox jumps over the lazy dog.";
    private static final String CLASSIC_TEMPLATE = "The ${animal} jumps over the ${target}.";

    /** The shortest "no-op" expression: prefix + suffix with no variable name. */
    private static final String EMPTY_EXPR = "${}";

    protected Map<String, String> values;

    // -------------------------------------------------------------------------
    // Helper assertions
    // -------------------------------------------------------------------------

    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual,
            () -> String.format("expected.length()=%,d, actual.length()=%,d",
                StringUtils.length(expected), StringUtils.length(actual)));
    }

    /**
     * Asserts that {@code replaceTemplate} is returned unchanged when the
     * substitutor is backed by the standard {@link #values} map.
     */
    protected void doNotReplace(final String replaceTemplate) throws IOException {
        doTestNoReplace(new StringSubstitutor(values), replaceTemplate);
    }

    /**
     * Asserts that {@code replaceTemplate} is transformed into
     * {@code expectedResult} by the substitutor backed by {@link #values}.
     *
     * @param substring {@code true} to also verify partial-range replacement
     */
    protected void doReplace(final String expectedResult, final String replaceTemplate,
            final boolean substring) throws IOException {
        doTestReplace(new StringSubstitutor(values), expectedResult, replaceTemplate, substring);
    }

    /**
     * Low-level helper: verifies that the given substitutor does NOT modify
     * {@code replaceTemplate} (or handles {@code null} correctly).
     */
    protected void doTestNoReplace(final StringSubstitutor substitutor,
            final String replaceTemplate) throws IOException {
        if (replaceTemplate == null) {
            assertNull(replace(substitutor, (String) null));
            assertNull(substitutor.replace((String) null, 0, 100));
            assertNull(substitutor.replace((char[]) null));
            assertNull(substitutor.replace((char[]) null, 0, 100));
            assertNull(substitutor.replace((StringBuffer) null));
            assertNull(substitutor.replace((StringBuffer) null, 0, 100));
            assertNull(substitutor.replace((TextStringBuilder) null));
            assertNull(substitutor.replace((TextStringBuilder) null, 0, 100));
            assertNull(substitutor.replace((Object) null));
            assertFalse(substitutor.replaceIn((StringBuffer) null));
            assertFalse(substitutor.replaceIn((StringBuffer) null, 0, 100));
            assertFalse(substitutor.replaceIn((TextStringBuilder) null));
            assertFalse(substitutor.replaceIn((TextStringBuilder) null, 0, 100));
        } else {
            assertEquals(replaceTemplate, replace(substitutor, replaceTemplate));
            final TextStringBuilder builder = new TextStringBuilder(replaceTemplate);
            assertFalse(substitutor.replaceIn(builder));
            assertEquals(replaceTemplate, builder.toString());
        }
    }

    /**
     * Low-level helper: verifies that the given substitutor transforms
     * {@code replaceTemplate} into {@code expectedResult} across every
     * supported input type (String, char[], StringBuffer, StringBuilder,
     * TextStringBuilder, and Object).
     */
    protected void doTestReplace(final StringSubstitutor sub, final String expectedResult,
            final String replaceTemplate, final boolean substring) throws IOException {
        final String expectedShortResult =
            substring ? expectedResult.substring(1, expectedResult.length() - 1) : expectedResult;

        // replace using String
        final String actual = replace(sub, replaceTemplate);
        assertEquals(expectedResult, actual,
            () -> String.format("Index of difference: %,d",
                StringUtils.indexOfDifference(expectedResult, actual)));
        if (substring) {
            assertEquals(expectedShortResult,
                sub.replace(replaceTemplate, 1, replaceTemplate.length() - 2));
        }

        // replace using char[]
        final char[] chars = replaceTemplate.toCharArray();
        assertEquals(expectedResult, sub.replace(chars));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(chars, 1, chars.length - 2));
        }

        // replace using StringBuffer
        StringBuffer buf = new StringBuffer(replaceTemplate);
        assertEquals(expectedResult, sub.replace(buf));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(buf, 1, buf.length() - 2));
        }

        // replace using StringBuilder
        StringBuilder builder = new StringBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(builder));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(builder, 1, builder.length() - 2));
        }

        // replace using TextStringBuilder
        TextStringBuilder bld = new TextStringBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(bld));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(bld, 1, bld.length() - 2));
        }

        // replace using Object (toString() returns the template)
        final MutableObject<String> obj = new MutableObject<>(replaceTemplate);
        assertEquals(expectedResult, sub.replace(obj));

        // replace in StringBuffer (in-place mutation)
        buf = new StringBuffer(replaceTemplate);
        assertTrue(sub.replaceIn(buf), replaceTemplate);
        assertEquals(expectedResult, buf.toString());
        if (substring) {
            buf = new StringBuffer(replaceTemplate);
            assertTrue(sub.replaceIn(buf, 1, buf.length() - 2));
            assertEquals(expectedResult, buf.toString()); // remainder untouched
        }

        // replace in StringBuilder (in-place mutation)
        builder = new StringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (substring) {
            builder = new StringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(builder, 1, builder.length() - 2));
            assertEquals(expectedResult, builder.toString()); // remainder untouched
        }

        // replace in TextStringBuilder (in-place mutation)
        bld = new TextStringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(bld));
        assertEquals(expectedResult, bld.toString());
        if (substring) {
            bld = new TextStringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(bld, 1, bld.length() - 2));
            assertEquals(expectedResult, bld.toString()); // remainder untouched
        }
    }

    /** Delegates to {@link StringSubstitutor#replace(String)}; subclasses may override. */
    protected String replace(final StringSubstitutor stringSubstitutor,
            final String template) throws IOException {
        return stringSubstitutor.replace(template);
    }

    // -------------------------------------------------------------------------
    // Lifecycle
    // -------------------------------------------------------------------------

    @BeforeEach
    public void setUp() throws Exception {
        values = new HashMap<>();
        // single-character keys
        values.put("a", "1");
        values.put("b", "2");
        // two-character keys
        values.put("aa", "11");
        values.put("bb", "22");
        // three-character keys
        values.put("aaa", "111");
        values.put("bbb", "222");
        // composite key whose value is itself a single-character key
        values.put("a2b", "b");
        // natural-language keys used by the classic template
        values.put("animal", ACTUAL_ANIMAL);
        values.put("target", ACTUAL_TARGET);
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    // -------------------------------------------------------------------------
    // Tests
    // -------------------------------------------------------------------------

    /**
     * Verifies that malformed or unresolvable variable expressions are passed
     * through without modification, and that a small set of "escape + nested"
     * patterns produces the expected literal output.
     *
     * <p>Patterns are grouped by the reason they cannot be substituted:
     * <ol>
     *   <li>Empty or whitespace-only variable names</li>
     *   <li>Incomplete delimiters (missing prefix or suffix)</li>
     *   <li>Orphan suffix or dollar signs</li>
     *   <li>Nested expressions that ultimately have no resolvable name</li>
     *   <li>Nested expressions that contain a valid key but are still unresolvable
     *       at the outer level</li>
     *   <li>Escaped-dollar patterns that partially resolve an inner variable and
     *       produce a literal <code>${...}</code> string in the output</li>
     * </ol>
     */
    @Test
    void testReplaceWeirdPattens() throws IOException {

        // --- Group 1: empty string and expressions with an empty / whitespace-only variable name ---
        // The substitutor requires a non-empty, non-whitespace variable name.
        doNotReplace(StringUtils.EMPTY);  // completely empty input
        doNotReplace(EMPTY_EXPR);         // "${}"  — variable name is absent
        doNotReplace("${ }");             // variable name is a single space
        doNotReplace("${\t}");            // variable name is a tab
        doNotReplace("${\n}");            // variable name is a newline
        doNotReplace("${\b}");            // variable name is a backspace

        // --- Group 2: incomplete expressions — missing prefix or suffix ---
        doNotReplace("${");   // prefix present, suffix missing
        doNotReplace("$}");   // dollar present but no '{', so not a valid prefix
        doNotReplace("$$}");  // two dollars then '}' — still no valid expression

        // --- Group 3: orphan suffix / trailing characters after an empty expression ---
        doNotReplace("}");       // bare '}' — not part of any expression
        doNotReplace("${}$");    // empty expression followed by an unmatched '$'
        doNotReplace("${}$$");   // empty expression followed by two unmatched '$'

        // --- Group 4: nested / broken patterns with no resolvable variable anywhere ---
        // The inner expression is itself malformed, so the outer one cannot resolve.
        doNotReplace("${${");       // two unclosed prefixes
        doNotReplace("${${}}");     // inner expression is empty, outer has no name
        doNotReplace("${$${}}");    // inner has an escaped '$' but still empty
        doNotReplace("${$$${}}");   // similar — double-escaped '$', inner still empty
        doNotReplace("${$$${$}}");  // innermost is "${$}" which is not a known key
        doNotReplace("${${}}");     // duplicate of the two-unclosed-prefix case
        doNotReplace("${${ }}");    // inner expression is whitespace-only

        // --- Group 5: nested patterns that contain a valid key at the innermost level,
        //              but the outer expression itself cannot be resolved ---
        // e.g., "${$${a}}" — the inner "${a}" resolves to "1", making the outer key
        // "$${1}" which is not in the map, so the whole expression is left unchanged.
        doNotReplace("${$${a}}");
        doNotReplace("${$$${a}}");
        doNotReplace("${${a}}");

        // Unclosed outer prefix: even though "${a}" would resolve, the outer "${${"
        // never closes so nothing is substituted.
        doNotReplace("${${${a}");
        doNotReplace("${ ${a}");
        doNotReplace("${ ${ ${a}");

        // --- Group 6: escape + nested expressions that DO resolve partially ---
        // A leading "$$" is an escape sequence that produces a literal "$".
        // The portion after the escape is then evaluated independently.
        //   "$${${a}}"   → escape gives "$", then "${${a}}" inner "${a}"=1 → "${1}"  → result: "${1}"
        doReplace("${1}",     "$${${a}}",        false);
        //   "$${ ${a}}"  → escape gives "$", then "{ ${a}}" where "${a}"=1 → "{ 1}" → result: "${ 1}"
        doReplace("${ 1}",    "$${ ${a}}",       false);
        //   "$${${a}${b}}" → escape "$", inner "${a}"=1, "${b}"=2 → "${12}"           → result: "${12}"
        doReplace("${12}",    "$${${a}${b}}",    false);
        //   "$${ ${a} ${b} }" → escape "$", "${a}"=1, "${b}"=2 → "${ 1 2 }"          → result: "${ 1 2 }"
        doReplace("${ 1 2 }", "$${ ${a} ${b} }", false);
        //   "${${${a}${b}" → "${${"  cannot close (no matching '}' for outer),
        //                    but "${b}"=2 is resolved; result: "${${${a}2"
        doReplace("${${${a}2", "${${${a}${b}", false);
    }
}
