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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link StringSubstitutor} covering the case where substitution
 * produces output identical to the input template.
 */
public class StringSubstitutorTest_testReplaceToIdentical {

    // Constants for the "classic" fox-and-dog substitution scenario
    private static final String ACTUAL_ANIMAL = "quick brown fox";
    private static final String ACTUAL_TARGET = "lazy dog";
    private static final String CLASSIC_RESULT = "The quick brown fox jumps over the lazy dog.";
    private static final String CLASSIC_TEMPLATE = "The ${animal} jumps over the ${target}.";

    /** An empty variable expression used to probe edge-case handling. */
    private static final String EMPTY_EXPR = "${}";

    protected Map<String, String> values;

    // -----------------------------------------------------------------------
    // Helpers
    // -----------------------------------------------------------------------

    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual,
                () -> String.format("expected.length()=%,d, actual.length()=%,d",
                        StringUtils.length(expected), StringUtils.length(actual)));
    }

    /**
     * Asserts that the given template is left unchanged after substitution
     * (i.e. no variable reference in it resolves to a different value).
     */
    protected void doNotReplace(final String replaceTemplate) throws IOException {
        doTestNoReplace(new StringSubstitutor(values), replaceTemplate);
    }

    /**
     * Asserts that substituting variables in {@code replaceTemplate} produces
     * {@code expectedResult}.  When {@code substring} is {@code true}, the
     * helper also verifies that operating on the inner slice of the template
     * (offset 1, last-char excluded) produces the corresponding inner slice of
     * the expected result.
     */
    protected void doReplace(final String expectedResult, final String replaceTemplate,
            final boolean substring) throws IOException {
        doTestReplace(new StringSubstitutor(values), expectedResult, replaceTemplate, substring);
    }

    /**
     * Verifies that no substitution occurs for {@code replaceTemplate}.
     * When {@code replaceTemplate} is {@code null}, all overloads must return
     * {@code null} / {@code false}.
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
     * Exercises every {@code replace} / {@code replaceIn} overload to confirm
     * they all return {@code expectedResult} for {@code replaceTemplate}.
     */
    protected void doTestReplace(final StringSubstitutor sub, final String expectedResult,
            final String replaceTemplate, final boolean substring) throws IOException {

        // Slice of the expected result that corresponds to the inner substring
        // of the template (used only when substring == true).
        final String expectedShortResult = substring
                ? expectedResult.substring(1, expectedResult.length() - 1)
                : expectedResult;

        // replace(String)
        final String actual = replace(sub, replaceTemplate);
        assertEquals(expectedResult, actual,
                () -> String.format("Index of difference: %,d",
                        StringUtils.indexOfDifference(expectedResult, actual)));
        if (substring) {
            assertEquals(expectedShortResult,
                    sub.replace(replaceTemplate, 1, replaceTemplate.length() - 2));
        }

        // replace(char[])
        final char[] chars = replaceTemplate.toCharArray();
        assertEquals(expectedResult, sub.replace(chars));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(chars, 1, chars.length - 2));
        }

        // replace(StringBuffer)
        StringBuffer buf = new StringBuffer(replaceTemplate);
        assertEquals(expectedResult, sub.replace(buf));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(buf, 1, buf.length() - 2));
        }

        // replace(StringBuilder)
        StringBuilder builder = new StringBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(builder));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(builder, 1, builder.length() - 2));
        }

        // replace(TextStringBuilder)
        TextStringBuilder bld = new TextStringBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(bld));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(bld, 1, bld.length() - 2));
        }

        // replace(Object) — Object.toString() returns the template string
        final MutableObject<String> obj = new MutableObject<>(replaceTemplate);
        assertEquals(expectedResult, sub.replace(obj));

        // replaceIn(StringBuffer)
        buf = new StringBuffer(replaceTemplate);
        assertTrue(sub.replaceIn(buf), replaceTemplate);
        assertEquals(expectedResult, buf.toString());
        if (substring) {
            buf = new StringBuffer(replaceTemplate);
            assertTrue(sub.replaceIn(buf, 1, buf.length() - 2));
            // Characters outside the slice are untouched, so the full buffer
            // still equals the full expected result.
            assertEquals(expectedResult, buf.toString());
        }

        // replaceIn(StringBuilder)
        builder = new StringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (substring) {
            builder = new StringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(builder, 1, builder.length() - 2));
            assertEquals(expectedResult, builder.toString());
        }

        // replaceIn(TextStringBuilder)
        bld = new TextStringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(bld));
        assertEquals(expectedResult, bld.toString());
        if (substring) {
            bld = new TextStringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(bld, 1, bld.length() - 2));
            assertEquals(expectedResult, bld.toString());
        }
    }

    /**
     * Delegates to {@link StringSubstitutor#replace(String)}.  Subclasses may
     * override to inject alternative replacement strategies (e.g. stream-based).
     */
    protected String replace(final StringSubstitutor stringSubstitutor,
            final String template) throws IOException {
        return stringSubstitutor.replace(template);
    }

    // -----------------------------------------------------------------------
    // Lifecycle
    // -----------------------------------------------------------------------

    @BeforeEach
    public void setUp() throws Exception {
        values = new HashMap<>();
        // Short single-character keys and their single-digit values
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // Standard "animal / target" keys used in the classic template
        values.put("animal", ACTUAL_ANIMAL);
        values.put("target", ACTUAL_TARGET);
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    // -----------------------------------------------------------------------
    // Test
    // -----------------------------------------------------------------------

    /**
     * Verifies that substitution can produce output identical to its input.
     *
     * <p>The lookup map is arranged so that resolving {@code ${animal}} yields
     * a string that, after all escaping and nested substitution is applied,
     * reconstructs the original {@code ${animal}} literal:
     *
     * <ol>
     *   <li>{@code animal} → {@code "$${${thing}}"}</li>
     *   <li>{@code thing}  → {@code "animal"}</li>
     *   <li>Inner substitution: {@code ${thing}} → {@code "animal"},
     *       giving {@code "$$" + "{animal}"} → {@code "$${animal}"}</li>
     *   <li>{@code "$$"} is the escape sequence for a literal {@code "$"},
     *       so the final value is the string {@code "${animal}"} — identical
     *       to the original placeholder.</li>
     * </ol>
     *
     * <p>As a result, the full template {@code "The ${animal} jumps."} is
     * returned unchanged.
     */
    @Test
    void testReplaceToIdentical() throws IOException {
        // "animal" resolves to a value that — after nested substitution and
        // escape processing — produces the literal text "${animal}" again.
        values.put("animal", "$${${thing}}");
        values.put("thing", "animal");

        // The template and the expected result are the same string.
        doReplace("The ${animal} jumps.", "The ${animal} jumps.", true);
    }
}
