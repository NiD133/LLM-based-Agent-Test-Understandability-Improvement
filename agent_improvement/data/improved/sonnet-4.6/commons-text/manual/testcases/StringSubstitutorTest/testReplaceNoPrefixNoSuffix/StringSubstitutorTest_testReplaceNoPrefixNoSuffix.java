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
 * Tests that {@link StringSubstitutor} does not replace a variable token when it appears
 * in the template without the required prefix/suffix delimiters (e.g. plain {@code animal}
 * instead of {@code ${animal}}).
 */
public class StringSubstitutorTest_testReplaceNoPrefixNoSuffix {

    /** The value mapped to the "animal" key in the substitution map. */
    private static final String ACTUAL_ANIMAL = "quick brown fox";

    /** The value mapped to the "target" key in the substitution map. */
    private static final String ACTUAL_TARGET = "lazy dog";

    /** Substitution values shared across test helpers. */
    protected Map<String, String> values;

    // -----------------------------------------------------------------------
    // Assertion helpers
    // -----------------------------------------------------------------------

    /**
     * Asserts two {@link CharSequence}s are equal, appending their lengths to the
     * failure message so mismatches are easier to diagnose.
     */
    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual,
                () -> String.format("expected.length()=%,d, actual.length()=%,d",
                        StringUtils.length(expected), StringUtils.length(actual)));
    }

    // -----------------------------------------------------------------------
    // Convenience wrappers used by the test method
    // -----------------------------------------------------------------------

    /**
     * Asserts that the given template is returned unchanged after substitution
     * (i.e. no replacements should occur).
     */
    protected void doNotReplace(final String replaceTemplate) throws IOException {
        doTestNoReplace(new StringSubstitutor(values), replaceTemplate);
    }

    /**
     * Asserts that substitution produces {@code expectedResult} for the given template.
     *
     * @param expectedResult  the fully-substituted string that is expected
     * @param replaceTemplate the template string containing variable placeholders
     * @param substring       if {@code true}, also verify substitution on a trimmed sub-range
     */
    protected void doReplace(final String expectedResult, final String replaceTemplate,
            final boolean substring) throws IOException {
        doTestReplace(new StringSubstitutor(values), expectedResult, replaceTemplate, substring);
    }

    // -----------------------------------------------------------------------
    // Core test logic shared with potential sub-class tests
    // -----------------------------------------------------------------------

    /**
     * Verifies that substitution leaves the template unchanged, exercising every
     * {@code replace} / {@code replaceIn} overload.
     */
    protected void doTestNoReplace(final StringSubstitutor substitutor,
            final String replaceTemplate) throws IOException {
        if (replaceTemplate == null) {
            // Null input should propagate as null from all replace overloads
            assertNull(replace(substitutor, (String) null));
            assertNull(substitutor.replace((String) null, 0, 100));
            assertNull(substitutor.replace((char[]) null));
            assertNull(substitutor.replace((char[]) null, 0, 100));
            assertNull(substitutor.replace((StringBuffer) null));
            assertNull(substitutor.replace((StringBuffer) null, 0, 100));
            assertNull(substitutor.replace((TextStringBuilder) null));
            assertNull(substitutor.replace((TextStringBuilder) null, 0, 100));
            assertNull(substitutor.replace((Object) null));
            // replaceIn with null should return false (nothing replaced)
            assertFalse(substitutor.replaceIn((StringBuffer) null));
            assertFalse(substitutor.replaceIn((StringBuffer) null, 0, 100));
            assertFalse(substitutor.replaceIn((TextStringBuilder) null));
            assertFalse(substitutor.replaceIn((TextStringBuilder) null, 0, 100));
        } else {
            // Non-null template with no matching variables must be returned as-is
            assertEquals(replaceTemplate, replace(substitutor, replaceTemplate));
            final TextStringBuilder builder = new TextStringBuilder(replaceTemplate);
            assertFalse(substitutor.replaceIn(builder));
            assertEquals(replaceTemplate, builder.toString());
        }
    }

    /**
     * Verifies substitution correctness across every {@code replace} / {@code replaceIn}
     * overload that {@link StringSubstitutor} exposes.
     *
     * @param sub             the configured substitutor
     * @param expectedResult  expected output after full substitution
     * @param replaceTemplate the template string
     * @param substring       if {@code true}, also check partial-range substitution:
     *                        the range [1, length-2] is substituted and the result
     *                        of that partial call is compared against
     *                        {@code expectedResult[1..length-2]}
     */
    protected void doTestReplace(final StringSubstitutor sub, final String expectedResult,
            final String replaceTemplate, final boolean substring) throws IOException {
        // The expected result when operating on the inner substring [1, length-2]
        final String expectedShortResult = substring
                ? expectedResult.substring(1, expectedResult.length() - 1)
                : expectedResult;

        // --- replace(String) ---
        final String actual = replace(sub, replaceTemplate);
        assertEquals(expectedResult, actual,
                () -> String.format("Index of difference: %,d",
                        StringUtils.indexOfDifference(expectedResult, actual)));
        if (substring) {
            assertEquals(expectedShortResult,
                    sub.replace(replaceTemplate, 1, replaceTemplate.length() - 2));
        }

        // --- replace(char[]) ---
        final char[] chars = replaceTemplate.toCharArray();
        assertEquals(expectedResult, sub.replace(chars));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(chars, 1, chars.length - 2));
        }

        // --- replace(StringBuffer) ---
        StringBuffer buf = new StringBuffer(replaceTemplate);
        assertEquals(expectedResult, sub.replace(buf));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(buf, 1, buf.length() - 2));
        }

        // --- replace(StringBuilder) ---
        StringBuilder builder = new StringBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(builder));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(builder, 1, builder.length() - 2));
        }

        // --- replace(TextStringBuilder) ---
        TextStringBuilder bld = new TextStringBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(bld));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(bld, 1, bld.length() - 2));
        }

        // --- replace(Object) — Object.toString() returns the template ---
        final MutableObject<String> obj = new MutableObject<>(replaceTemplate);
        assertEquals(expectedResult, sub.replace(obj));

        // --- replaceIn(StringBuffer) ---
        buf = new StringBuffer(replaceTemplate);
        assertTrue(sub.replaceIn(buf), replaceTemplate);
        assertEquals(expectedResult, buf.toString());
        if (substring) {
            buf = new StringBuffer(replaceTemplate);
            assertTrue(sub.replaceIn(buf, 1, buf.length() - 2));
            // Characters outside the substituted range are left untouched,
            // so the full buffer matches the expected full result.
            assertEquals(expectedResult, buf.toString());
        }

        // --- replaceIn(StringBuilder) ---
        builder = new StringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (substring) {
            builder = new StringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(builder, 1, builder.length() - 2));
            assertEquals(expectedResult, builder.toString());
        }

        // --- replaceIn(TextStringBuilder) ---
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
     * Delegates to {@link StringSubstitutor#replace(String)}.
     * Subclasses may override to exercise a different {@code replace} variant.
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
        // Single-character keys/values (edge cases for short tokens)
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // Human-readable keys used in template-style tests
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
     * Verifies that a variable token written without the required prefix/suffix
     * delimiters is treated as literal text and is NOT substituted.
     *
     * <p>The template {@code "The animal jumps over the ${target}."} contains:
     * <ul>
     *   <li>{@code animal} — no {@code ${}} markers, so it must remain as-is.</li>
     *   <li>{@code ${target}} — properly delimited, so it is replaced with
     *       {@value #ACTUAL_TARGET}.</li>
     * </ul>
     */
    @Test
    void testReplaceNoPrefixNoSuffix() throws IOException {
        doReplace(
                "The animal jumps over the lazy dog.",   // expected: "animal" left as literal
                "The animal jumps over the ${target}.",  // template: only ${target} is delimited
                true);
    }
}
