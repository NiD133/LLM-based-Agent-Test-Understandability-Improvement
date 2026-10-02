package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the solo-escaping behaviour of {@link StrSubstitutor}.
 *
 * <p>Solo escaping means that a double escape prefix ({@code $$}) immediately
 * before a variable reference suppresses substitution and is itself consumed,
 * leaving the literal variable token in the output. For example:
 * <pre>
 *   template : $${animal}
 *   result   : ${animal}   (the variable is NOT resolved)
 * </pre>
 */
public class StrSubstitutorTest_testReplaceSoloEscaping {

    /** Variable map shared by all helpers: animal → "quick brown fox", target → "lazy dog". */
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
    // Test
    // -----------------------------------------------------------------------

    /**
     * Verifies that a lone escape prefix ({@code $$}) disables substitution:
     * {@code $${animal}} must yield the literal string {@code ${animal}}
     * rather than the resolved value {@code "quick brown fox"}.
     *
     * <p>The {@code false} flag passed to {@link #doTestReplace} tells the
     * helper not to exercise substring-range overloads (those need a template
     * long enough to carve a meaningful interior slice from, which a single
     * token like {@code $${animal}} does not provide).
     */
    @Test
    void testReplaceSoloEscaping() {
        // Input "$${animal}" contains the escape prefix "$$" followed by the
        // variable token "{animal}".  The substitutor should strip the leading
        // "$" (the escape character) and leave the rest as-is: "${animal}".
        doTestReplace("${animal}", "$${animal}", false);
    }

    // -----------------------------------------------------------------------
    // Helper: exercise every replace overload with the same template
    // -----------------------------------------------------------------------

    /**
     * Convenience overload that builds a default {@link StrSubstitutor} from
     * {@link #values} and delegates to
     * {@link #doTestReplace(StrSubstitutor, String, String, boolean)}.
     */
    private void doTestReplace(final String expectedResult, final String replaceTemplate, final boolean substring) {
        final StrSubstitutor sub = new StrSubstitutor(values);
        doTestReplace(sub, expectedResult, replaceTemplate, substring);
    }

    /**
     * Exercises every {@code replace} / {@code replaceIn} overload offered by
     * {@link StrSubstitutor} and asserts that each one produces
     * {@code expectedResult}.
     *
     * <p>When {@code substring} is {@code true} the helper additionally calls
     * the offset+length overloads on the interior of the template (trimming one
     * character from each end) and asserts they return the correspondingly
     * trimmed slice of {@code expectedResult}.
     *
     * @param sub             the substitutor under test
     * @param expectedResult  the full replacement that every overload must produce
     * @param replaceTemplate the template string fed to each overload
     * @param substring       whether to also test the offset+length overloads
     */
    private void doTestReplace(final StrSubstitutor sub, final String expectedResult,
                               final String replaceTemplate, final boolean substring) {
        // When testing substring overloads, trim one char from each end of both
        // the template and the expected result so the slices correspond.
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

        // --- replace(Object) — Object.toString() returns the template --------
        final MutableObject<String> obj = new MutableObject<>(replaceTemplate);
        assertEquals(expectedResult, sub.replace(obj));

        // --- replaceIn(StringBuffer) -----------------------------------------
        buf = new StringBuffer(replaceTemplate);
        assertTrue(sub.replaceIn(buf));
        assertEquals(expectedResult, buf.toString());
        if (substring) {
            buf = new StringBuffer(replaceTemplate);
            assertTrue(sub.replaceIn(buf, 1, buf.length() - 2));
            // Characters outside the specified range are left untouched, so the
            // full buffer still equals the full expected result.
            assertEquals(expectedResult, buf.toString());
        }

        // --- replaceIn(StringBuilder) ----------------------------------------
        builder = new StringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (substring) {
            builder = new StringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(builder, 1, builder.length() - 2));
            assertEquals(expectedResult, builder.toString());
        }

        // --- replaceIn(StrBuilder) -------------------------------------------
        bld = new StrBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(bld));
        assertEquals(expectedResult, bld.toString());
        if (substring) {
            bld = new StrBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(bld, 1, bld.length() - 2));
            assertEquals(expectedResult, bld.toString());
        }
    }
}
