package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceNoPrefixSuffix {

    private Map<String, String> values;

    /**
     * Verifies that when a null or unchanged template is passed, the substitutor
     * returns null (for null input) or leaves the string unmodified and reports
     * no replacements made.
     */
    private void doTestNoReplace(final String replaceTemplate) {
        final StrSubstitutor sub = new StrSubstitutor(values);
        if (replaceTemplate == null) {
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
            assertEquals(replaceTemplate, sub.replace(replaceTemplate));
            final StrBuilder bld = new StrBuilder(replaceTemplate);
            assertFalse(sub.replaceIn(bld));
            assertEquals(replaceTemplate, bld.toString());
        }
    }

    private void doTestReplace(final String expectedResult, final String replaceTemplate, final boolean substring) {
        final StrSubstitutor sub = new StrSubstitutor(values);
        doTestReplace(sub, expectedResult, replaceTemplate, substring);
    }

    /**
     * Exercises {@code replace} and {@code replaceIn} across every supported
     * source type (String, char[], StringBuffer, StringBuilder, StrBuilder,
     * Object) and, when {@code substring} is true, also verifies that replacing
     * only an interior slice of the source produces the corresponding interior
     * slice of {@code expectedResult}.
     */
    private void doTestReplace(final StrSubstitutor sub, final String expectedResult, final String replaceTemplate, final boolean substring) {
        // The short result covers everything except the first and last characters.
        final String expectedShortResult = expectedResult.substring(1, expectedResult.length() - 1);

        // --- replace returning a new String ---
        assertEquals(expectedResult, sub.replace(replaceTemplate));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(replaceTemplate, 1, replaceTemplate.length() - 2));
        }

        // --- replace from char[] ---
        final char[] chars = replaceTemplate.toCharArray();
        assertEquals(expectedResult, sub.replace(chars));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(chars, 1, chars.length - 2));
        }

        // --- replace from StringBuffer ---
        StringBuffer buf = new StringBuffer(replaceTemplate);
        assertEquals(expectedResult, sub.replace(buf));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(buf, 1, buf.length() - 2));
        }

        // --- replace from StringBuilder ---
        StringBuilder builder = new StringBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(builder));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(builder, 1, builder.length() - 2));
        }

        // --- replace from StrBuilder ---
        StrBuilder bld = new StrBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(bld));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(bld, 1, bld.length() - 2));
        }

        // --- replace from Object (toString() returns the template) ---
        final MutableObject<String> obj = new MutableObject<>(replaceTemplate);
        assertEquals(expectedResult, sub.replace(obj));

        // --- replaceIn mutates a StringBuffer in place ---
        buf = new StringBuffer(replaceTemplate);
        assertTrue(sub.replaceIn(buf));
        assertEquals(expectedResult, buf.toString());
        if (substring) {
            buf = new StringBuffer(replaceTemplate);
            assertTrue(sub.replaceIn(buf, 1, buf.length() - 2));
            // Characters outside the slice are untouched, so the full result still matches.
            assertEquals(expectedResult, buf.toString());
        }

        // --- replaceIn mutates a StringBuilder in place ---
        builder = new StringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (substring) {
            builder = new StringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(builder, 1, builder.length() - 2));
            // Characters outside the slice are untouched, so the full result still matches.
            assertEquals(expectedResult, builder.toString());
        }

        // --- replaceIn mutates a StrBuilder in place ---
        bld = new StrBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(bld));
        assertEquals(expectedResult, bld.toString());
        if (substring) {
            bld = new StrBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(bld, 1, bld.length() - 2));
            // Characters outside the slice are untouched, so the full result still matches.
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
     * Verifies that a lone closing suffix ('}') in the template is treated as
     * plain text and is NOT interpreted as part of a variable reference.
     *
     * <p>The template {@code "The animal} jumps over the ${target}."} contains:
     * <ul>
     *   <li>{@code animal}} – a closing suffix with no preceding opening prefix;
     *       it must be kept verbatim.</li>
     *   <li>{@code ${target}} – a well-formed variable that resolves to
     *       {@code "lazy dog"}.</li>
     * </ul>
     * The expected result is {@code "The animal} jumps over the lazy dog."}.
     */
    @Test
    void testReplaceNoPrefixSuffix() {
        doTestReplace("The animal} jumps over the lazy dog.", "The animal} jumps over the ${target}.", true);
    }
}
