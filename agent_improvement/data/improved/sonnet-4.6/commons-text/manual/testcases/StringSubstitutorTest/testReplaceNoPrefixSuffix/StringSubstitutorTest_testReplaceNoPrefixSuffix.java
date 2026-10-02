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

public class StringSubstitutorTest_testReplaceNoPrefixSuffix {

    // Variable values loaded into the substitutor for all tests
    private static final String ACTUAL_ANIMAL = "quick brown fox";
    private static final String ACTUAL_TARGET  = "lazy dog";

    protected Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        // Short keys used by other tests in the suite
        values.put("a",   "1");
        values.put("aa",  "11");
        values.put("aaa", "111");
        values.put("b",   "2");
        values.put("bb",  "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // Keys referenced by the classic template
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
     * Verifies that a suffix character ('}') appearing without a matching prefix ('${')
     * is treated as literal text and left unchanged, while a properly-formed variable
     * reference in the same template ({@code ${target}}) is still resolved normally.
     *
     * <p>Template:  {@code "The animal} jumps over the ${target}."}
     * <p>Expected:  {@code "The animal} jumps over the lazy dog."}
     */
    @Test
    void testReplaceNoPrefixSuffix() throws IOException {
        doReplace(
            "The animal} jumps over the lazy dog.",   // expected: only ${target} is substituted
            "The animal} jumps over the ${target}.",  // template: 'animal}' has no '${' prefix
            true
        );
    }

    // -----------------------------------------------------------------------
    // Helpers
    // -----------------------------------------------------------------------

    /**
     * Convenience method: creates a substitutor backed by {@link #values} and
     * asserts that {@code replaceTemplate} is transformed into {@code expectedResult}.
     */
    protected void doReplace(final String expectedResult, final String replaceTemplate, final boolean substring)
            throws IOException {
        doTestReplace(new StringSubstitutor(values), expectedResult, replaceTemplate, substring);
    }

    /**
     * Convenience method: asserts that the given template is returned unchanged by the substitutor.
     */
    protected void doNotReplace(final String replaceTemplate) throws IOException {
        doTestNoReplace(new StringSubstitutor(values), replaceTemplate);
    }

    /**
     * Exercises every {@code replace} / {@code replaceIn} overload and asserts each
     * produces {@code expectedResult}.  When {@code substring} is {@code true}, the
     * slice-based overloads (offset=1, length-2) are also checked against a trimmed
     * expected value.
     */
    protected void doTestReplace(final StringSubstitutor sub,
                                 final String expectedResult,
                                 final String replaceTemplate,
                                 final boolean substring) throws IOException {

        // When substring=true we also test slice overloads that strip the first and last characters
        final String expectedShortResult = substring
            ? expectedResult.substring(1, expectedResult.length() - 1)
            : expectedResult;

        // --- replace(String) ---
        final String actual = replace(sub, replaceTemplate);
        assertEquals(expectedResult, actual,
            () -> String.format("Index of difference: %,d",
                StringUtils.indexOfDifference(expectedResult, actual)));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(replaceTemplate, 1, replaceTemplate.length() - 2));
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
            assertEquals(expectedResult, buf.toString()); // untouched region keeps full result
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
     * Asserts that the substitutor leaves {@code replaceTemplate} completely unchanged,
     * or handles {@code null} input gracefully when {@code replaceTemplate} is {@code null}.
     */
    protected void doTestNoReplace(final StringSubstitutor substitutor, final String replaceTemplate)
            throws IOException {
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

    /** Delegates to {@link StringSubstitutor#replace(String)}; subclasses may override. */
    protected String replace(final StringSubstitutor stringSubstitutor, final String template) throws IOException {
        return stringSubstitutor.replace(template);
    }

    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual,
            () -> String.format("expected.length()=%,d, actual.length()=%,d",
                StringUtils.length(expected), StringUtils.length(actual)));
    }
}
