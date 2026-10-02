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

/**
 * Tests that StrSubstitutor correctly replaces a single variable reference in a template string.
 *
 * <p>The variable map is pre-populated with:
 * <ul>
 *   <li>"animal" -> "quick brown fox"</li>
 *   <li>"target" -> "lazy dog"</li>
 * </ul>
 */
public class StrSubstitutorTest_testReplaceSolo {

    /** Lookup map used by every test in this class. */
    private Map<String, String> values;

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

    // -----------------------------------------------------------------------
    // Helper: assert that null / no-variable inputs are passed through unchanged
    // -----------------------------------------------------------------------

    /**
     * Asserts that the substitutor leaves {@code replaceTemplate} unchanged (no variables to expand),
     * or that all overloads accept {@code null} gracefully when {@code replaceTemplate} is {@code null}.
     */
    private void doTestNoReplace(final String replaceTemplate) {
        final StrSubstitutor sub = new StrSubstitutor(values);
        if (replaceTemplate == null) {
            // Every overload must return null / false for null input
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
            // Template contains no variable markers: output must equal input
            assertEquals(replaceTemplate, sub.replace(replaceTemplate));
            final StrBuilder bld = new StrBuilder(replaceTemplate);
            assertFalse(sub.replaceIn(bld));
            assertEquals(replaceTemplate, bld.toString());
        }
    }

    // -----------------------------------------------------------------------
    // Helper: assert replacement across every supported input/output type
    // -----------------------------------------------------------------------

    /**
     * Convenience overload that builds a fresh {@link StrSubstitutor} from {@link #values}.
     */
    private void doTestReplace(final String expectedResult, final String replaceTemplate, final boolean substring) {
        final StrSubstitutor sub = new StrSubstitutor(values);
        doTestReplace(sub, expectedResult, replaceTemplate, substring);
    }

    /**
     * Verifies that {@code sub} expands {@code replaceTemplate} to {@code expectedResult} using
     * every {@code replace} / {@code replaceIn} overload.
     *
     * <p>When {@code substring} is {@code true} the inner sub-range
     * (offset 1, length-2 characters) is also verified independently.
     *
     * @param sub             the configured substitutor under test
     * @param expectedResult  the fully-expanded string that every overload must produce
     * @param replaceTemplate the template string containing variable references
     * @param substring       whether to additionally test partial-range replacement
     */
    private void doTestReplace(final StrSubstitutor sub,
                                final String expectedResult,
                                final String replaceTemplate,
                                final boolean substring) {

        // The inner portion used when testing sub-range replacement
        final String expectedShortResult = expectedResult.substring(1, expectedResult.length() - 1);

        // --- replace(String) -------------------------------------------------
        assertEquals(expectedResult, sub.replace(replaceTemplate));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(replaceTemplate, 1, replaceTemplate.length() - 2));
        }

        // --- replace(char[]) -------------------------------------------------
        final char[] chars = replaceTemplate.toCharArray();
        assertEquals(expectedResult, sub.replace(chars));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(chars, 1, chars.length - 2));
        }

        // --- replace(StringBuffer) -------------------------------------------
        StringBuffer buf = new StringBuffer(replaceTemplate);
        assertEquals(expectedResult, sub.replace(buf));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(buf, 1, buf.length() - 2));
        }

        // --- replace(StringBuilder) ------------------------------------------
        StringBuilder builder = new StringBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(builder));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(builder, 1, builder.length() - 2));
        }

        // --- replace(StrBuilder) ---------------------------------------------
        StrBuilder bld = new StrBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(bld));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(bld, 1, bld.length() - 2));
        }

        // --- replace(Object) — delegates to toString() -----------------------
        final MutableObject<String> obj = new MutableObject<>(replaceTemplate);
        assertEquals(expectedResult, sub.replace(obj));

        // --- replaceIn(StringBuffer) — modifies the buffer in place ----------
        buf = new StringBuffer(replaceTemplate);
        assertTrue(sub.replaceIn(buf));
        assertEquals(expectedResult, buf.toString());
        if (substring) {
            buf = new StringBuffer(replaceTemplate);
            assertTrue(sub.replaceIn(buf, 1, buf.length() - 2));
            // Remainder outside the range is untouched, so the full result still matches
            assertEquals(expectedResult, buf.toString());
        }

        // --- replaceIn(StringBuilder) — modifies the builder in place --------
        builder = new StringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (substring) {
            builder = new StringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(builder, 1, builder.length() - 2));
            // Remainder outside the range is untouched, so the full result still matches
            assertEquals(expectedResult, builder.toString());
        }

        // --- replaceIn(StrBuilder) — modifies the builder in place -----------
        bld = new StrBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(bld));
        assertEquals(expectedResult, bld.toString());
        if (substring) {
            bld = new StrBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(bld, 1, bld.length() - 2));
            // Remainder outside the range is untouched, so the full result still matches
            assertEquals(expectedResult, bld.toString());
        }
    }

    // -----------------------------------------------------------------------
    // Test: single variable in template
    // -----------------------------------------------------------------------

    /**
     * Verifies that a template containing exactly one variable reference — {@code ${animal}} —
     * is fully replaced by its mapped value across all supported input and output types.
     *
     * <p>The expected substitution is:
     * <pre>
     *   "${animal}"  →  "quick brown fox"
     * </pre>
     */
    @Test
    void testReplaceSolo() {
        final String template = "${animal}";
        final String expectedValue = "quick brown fox";

        doTestReplace(expectedValue, template, false);
    }
}
