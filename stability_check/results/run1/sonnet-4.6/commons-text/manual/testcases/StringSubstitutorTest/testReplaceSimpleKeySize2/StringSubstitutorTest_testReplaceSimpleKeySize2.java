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
 * Tests that StringSubstitutor correctly resolves a two-character variable key ("aa")
 * to its mapped value ("11") when using the default ${...} syntax.
 */
public class StringSubstitutorTest_testReplaceSimpleKeySize2 {

    /** Variable value for the "animal" key used in classic-sentence tests. */
    private static final String ACTUAL_ANIMAL = "quick brown fox";

    /** Variable value for the "target" key used in classic-sentence tests. */
    private static final String ACTUAL_TARGET = "lazy dog";

    /**
     * Lookup map shared across helper methods; populated in setUp and cleared in tearDown.
     * Includes short single-, two-, and three-character keys as well as "animal" and "target".
     */
    protected Map<String, String> values;

    // -------------------------------------------------------------------------
    // Test lifecycle
    // -------------------------------------------------------------------------

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        // Short keys (1–3 chars) used to verify key-length edge cases
        values.put("a",   "1");
        values.put("aa",  "11");
        values.put("aaa", "111");
        values.put("b",   "2");
        values.put("bb",  "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // Normal-length keys used by classic-sentence tests
        values.put("animal", ACTUAL_ANIMAL);
        values.put("target", ACTUAL_TARGET);
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    // -------------------------------------------------------------------------
    // Test
    // -------------------------------------------------------------------------

    /**
     * Verifies that a two-character key ("aa") is fully resolved.
     * The template "${aa}" should expand to "11".
     */
    @Test
    void testReplaceSimpleKeySize2() throws IOException {
        // "aa" → "11"; substring=false means the whole string is replaced (no offset/length trimming)
        doReplace("11", "${aa}", false);
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    /**
     * Runs {@link #doTestReplace} with a freshly created substitutor backed by {@link #values}.
     *
     * @param expectedResult   the expected output after substitution
     * @param replaceTemplate  the input template containing variable references
     * @param substring        if {@code true}, also tests offset/length-bounded replacement
     */
    protected void doReplace(final String expectedResult,
                              final String replaceTemplate,
                              final boolean substring) throws IOException {
        doTestReplace(new StringSubstitutor(values), expectedResult, replaceTemplate, substring);
    }

    /**
     * Exercises every {@code replace} and {@code replaceIn} overload of {@code sub} with
     * {@code replaceTemplate} and asserts each result equals {@code expectedResult}.
     * When {@code substring} is {@code true}, the bounded (offset+length) variants are also
     * exercised and checked against {@code expectedShortResult}.
     *
     * @param sub              the substitutor under test
     * @param expectedResult   expected full-replacement result
     * @param replaceTemplate  template string to substitute into
     * @param substring        whether to also exercise the offset/length-bounded overloads
     */
    protected void doTestReplace(final StringSubstitutor sub,
                                  final String expectedResult,
                                  final String replaceTemplate,
                                  final boolean substring) throws IOException {
        final String expectedShortResult =
                substring ? expectedResult.substring(1, expectedResult.length() - 1) : expectedResult;

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
            assertEquals(expectedResult, buf.toString()); // remainder is untouched
        }

        // --- replaceIn(StringBuilder) ---
        builder = new StringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (substring) {
            builder = new StringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(builder, 1, builder.length() - 2));
            assertEquals(expectedResult, builder.toString()); // remainder is untouched
        }

        // --- replaceIn(TextStringBuilder) ---
        bld = new TextStringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(bld));
        assertEquals(expectedResult, bld.toString());
        if (substring) {
            bld = new TextStringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(bld, 1, bld.length() - 2));
            assertEquals(expectedResult, bld.toString()); // remainder is untouched
        }
    }

    /**
     * Delegates to {@link StringSubstitutor#replace(String)}; subclasses may override to
     * inject alternate replacement strategies (e.g., stream-based I/O).
     *
     * @param stringSubstitutor  the substitutor to use
     * @param template           the template string
     * @return the substituted result
     */
    protected String replace(final StringSubstitutor stringSubstitutor,
                              final String template) throws IOException {
        return stringSubstitutor.replace(template);
    }

    /**
     * Asserts that {@code sub} leaves {@code replaceTemplate} unchanged (no substitution occurs).
     * When {@code replaceTemplate} is {@code null}, all replace/replaceIn overloads are verified
     * to handle {@code null} gracefully.
     *
     * @param substitutor      the substitutor under test
     * @param replaceTemplate  the input that should pass through unmodified, or {@code null}
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
     * Convenience wrapper for {@link #doTestNoReplace} that creates a fresh substitutor
     * backed by {@link #values}.
     *
     * @param replaceTemplate  the input expected to pass through unmodified
     */
    protected void doNotReplace(final String replaceTemplate) throws IOException {
        doTestNoReplace(new StringSubstitutor(values), replaceTemplate);
    }
}
