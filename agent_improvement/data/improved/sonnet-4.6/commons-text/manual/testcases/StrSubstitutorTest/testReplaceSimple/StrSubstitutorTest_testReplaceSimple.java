package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.lang3.SystemProperties;
import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceSimple {

    /** Variable lookup table used by all tests in this class. */
    private Map<String, String> values;

    /**
     * Verifies that {@code replaceTemplate} passes through unchanged when none of its
     * content matches a known variable.  When {@code replaceTemplate} is {@code null},
     * every {@code replace} / {@code replaceIn} overload must return {@code null} or
     * {@code false} rather than throwing.
     */
    private void doTestNoReplace(final String replaceTemplate) {
        final StrSubstitutor sub = new StrSubstitutor(values);
        if (replaceTemplate == null) {
            // Every overload must handle null input gracefully.
            assertNull(sub.replace((String) null));
            assertNull(sub.replace((String) null, 0, 100));
            assertNull(sub.replace((char[]) null));
            assertNull(sub.replace((char[]) null, 0, 100));
            assertNull(sub.replace((StringBuffer) null));
            assertNull(sub.replace((StringBuffer) null, 0, 100));
            assertNull(sub.replace((StrBuilder) null));
            assertNull(sub.replace((StrBuilder) null, 0, 100));
            assertNull(sub.replace((Object) null));
            assertFalse(sub.replaceIn((StringBuffer) null));
            assertFalse(sub.replaceIn((StringBuffer) null, 0, 100));
            assertFalse(sub.replaceIn((StrBuilder) null));
            assertFalse(sub.replaceIn((StrBuilder) null, 0, 100));
        } else {
            // Non-null template with no matching variables must be returned as-is.
            assertEquals(replaceTemplate, sub.replace(replaceTemplate));
            final StrBuilder bld = new StrBuilder(replaceTemplate);
            assertFalse(sub.replaceIn(bld));
            assertEquals(replaceTemplate, bld.toString());
        }
    }

    /**
     * Convenience overload that builds a fresh {@link StrSubstitutor} from {@link #values}
     * and delegates to {@link #doTestReplace(StrSubstitutor, String, String, boolean)}.
     */
    private void doTestReplace(final String expectedResult, final String replaceTemplate, final boolean testSubstring) {
        final StrSubstitutor sub = new StrSubstitutor(values);
        doTestReplace(sub, expectedResult, replaceTemplate, testSubstring);
    }

    /**
     * Asserts that {@code sub} produces {@code expectedResult} when applied to
     * {@code replaceTemplate} across every supported input type (String, char[],
     * StringBuffer, StringBuilder, StrBuilder, and Object).
     *
     * <p>When {@code testSubstring} is {@code true}, the method also verifies the
     * offset+length overloads by stripping the first and last character from the
     * template and confirming that only that inner slice is substituted while the
     * surrounding characters remain untouched.
     */
    private void doTestReplace(final StrSubstitutor sub, final String expectedResult, final String replaceTemplate, final boolean testSubstring) {
        // The "short" result covers everything except the first and last character
        // of the full result, matching what the offset+length overloads should produce.
        final String expectedShortResult = expectedResult.substring(1, expectedResult.length() - 1);

        // --- replace(String) overloads ---
        assertEquals(expectedResult, sub.replace(replaceTemplate));
        if (testSubstring) {
            assertEquals(expectedShortResult, sub.replace(replaceTemplate, 1, replaceTemplate.length() - 2));
        }

        // --- replace(char[]) overloads ---
        final char[] chars = replaceTemplate.toCharArray();
        assertEquals(expectedResult, sub.replace(chars));
        if (testSubstring) {
            assertEquals(expectedShortResult, sub.replace(chars, 1, chars.length - 2));
        }

        // --- replace(StringBuffer) overloads ---
        StringBuffer buf = new StringBuffer(replaceTemplate);
        assertEquals(expectedResult, sub.replace(buf));
        if (testSubstring) {
            assertEquals(expectedShortResult, sub.replace(buf, 1, buf.length() - 2));
        }

        // --- replace(StringBuilder) overloads ---
        StringBuilder builder = new StringBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(builder));
        if (testSubstring) {
            assertEquals(expectedShortResult, sub.replace(builder, 1, builder.length() - 2));
        }

        // --- replace(StrBuilder) overloads ---
        StrBuilder bld = new StrBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(bld));
        if (testSubstring) {
            assertEquals(expectedShortResult, sub.replace(bld, 1, bld.length() - 2));
        }

        // --- replace(Object) — delegates to toString() ---
        final MutableObject<String> obj = new MutableObject<>(replaceTemplate);
        assertEquals(expectedResult, sub.replace(obj));

        // --- replaceIn(StringBuffer) overloads (in-place mutation) ---
        buf = new StringBuffer(replaceTemplate);
        assertTrue(sub.replaceIn(buf));
        assertEquals(expectedResult, buf.toString());
        if (testSubstring) {
            buf = new StringBuffer(replaceTemplate);
            assertTrue(sub.replaceIn(buf, 1, buf.length() - 2));
            // Characters outside the substituted range stay as-is, so the full
            // buffer still equals the full expected result.
            assertEquals(expectedResult, buf.toString());
        }

        // --- replaceIn(StringBuilder) overloads (in-place mutation) ---
        builder = new StringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (testSubstring) {
            builder = new StringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(builder, 1, builder.length() - 2));
            // Same reasoning as StringBuffer: untouched characters are preserved.
            assertEquals(expectedResult, builder.toString());
        }

        // --- replaceIn(StrBuilder) overloads (in-place mutation) ---
        bld = new StrBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(bld));
        assertEquals(expectedResult, bld.toString());
        if (testSubstring) {
            bld = new StrBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(bld, 1, bld.length() - 2));
            // Same reasoning: only the inner slice is substituted; outer chars unchanged.
            assertEquals(expectedResult, bld.toString());
        }
    }

    @BeforeEach
    public void setUp() throws Exception {
        values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    @AfterEach
    public void tearDown() throws Exception {
        values = null;
    }

    /**
     * Tests that a template containing two variables is fully resolved:
     * {@code ${animal}} and {@code ${target}} are replaced with their mapped values,
     * and the result is verified across every supported input type and overload.
     */
    @Test
    void testReplaceSimple() {
        final String template       = "The ${animal} jumps over the ${target}.";
        final String expectedResult = "The quick brown fox jumps over the lazy dog.";
        doTestReplace(expectedResult, template, true);
    }
}
