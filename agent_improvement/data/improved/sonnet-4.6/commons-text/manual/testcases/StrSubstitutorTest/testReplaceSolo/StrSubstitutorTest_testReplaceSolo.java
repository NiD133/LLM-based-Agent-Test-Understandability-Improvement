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

public class StrSubstitutorTest_testReplaceSolo {

    // Template variable placeholders and their expected resolved values
    private static final String ANIMAL_TEMPLATE = "${animal}";
    private static final String ANIMAL_VALUE    = "quick brown fox";
    private static final String TARGET_VALUE    = "lazy dog";

    private Map<String, String> values;

    @BeforeEach
    public void setUp() throws Exception {
        values = new HashMap<>();
        values.put("animal", ANIMAL_VALUE);
        values.put("target", TARGET_VALUE);
    }

    @AfterEach
    public void tearDown() throws Exception {
        values = null;
    }

    /**
     * Tests simple key replace: a template containing a single variable reference
     * is fully resolved to the variable's value.
     */
    @Test
    void testReplaceSolo() {
        // No substring (offset/length) variants are exercised for this single-token template
        final boolean testSubstringVariants = false;
        doTestReplace(ANIMAL_VALUE, ANIMAL_TEMPLATE, testSubstringVariants);
    }

    // -------------------------------------------------------------------------
    // Helper: assert that none of the replace overloads performs any substitution
    // -------------------------------------------------------------------------

    private void doTestNoReplace(final String replaceTemplate) {
        final StrSubstitutor sub = new StrSubstitutor(values);
        if (replaceTemplate == null) {
            // All overloads must return null / false for a null input
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
            // A template with no variable references must be returned unchanged
            assertEquals(replaceTemplate, sub.replace(replaceTemplate));
            final StrBuilder bld = new StrBuilder(replaceTemplate);
            assertFalse(sub.replaceIn(bld));
            assertEquals(replaceTemplate, bld.toString());
        }
    }

    // -------------------------------------------------------------------------
    // Helper: exercise every replace overload against a given template
    // -------------------------------------------------------------------------

    private void doTestReplace(final String expectedResult, final String replaceTemplate, final boolean testSubstringVariants) {
        final StrSubstitutor sub = new StrSubstitutor(values);
        doTestReplace(sub, expectedResult, replaceTemplate, testSubstringVariants);
    }

    private void doTestReplace(final StrSubstitutor sub, final String expectedResult, final String replaceTemplate, final boolean testSubstringVariants) {
        // When testing substring variants, the middle portion (index 1 to length-1) is processed;
        // the surrounding characters are excluded from the result.
        final String expectedSubstringResult = expectedResult.substring(1, expectedResult.length() - 1);

        // --- replace(String) / replace(String, offset, length) ---
        assertEquals(expectedResult, sub.replace(replaceTemplate));
        if (testSubstringVariants) {
            assertEquals(expectedSubstringResult, sub.replace(replaceTemplate, 1, replaceTemplate.length() - 2));
        }

        // --- replace(char[]) / replace(char[], offset, length) ---
        final char[] chars = replaceTemplate.toCharArray();
        assertEquals(expectedResult, sub.replace(chars));
        if (testSubstringVariants) {
            assertEquals(expectedSubstringResult, sub.replace(chars, 1, chars.length - 2));
        }

        // --- replace(StringBuffer) / replace(StringBuffer, offset, length) ---
        StringBuffer buf = new StringBuffer(replaceTemplate);
        assertEquals(expectedResult, sub.replace(buf));
        if (testSubstringVariants) {
            assertEquals(expectedSubstringResult, sub.replace(buf, 1, buf.length() - 2));
        }

        // --- replace(StringBuilder) / replace(StringBuilder, offset, length) ---
        StringBuilder builder = new StringBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(builder));
        if (testSubstringVariants) {
            assertEquals(expectedSubstringResult, sub.replace(builder, 1, builder.length() - 2));
        }

        // --- replace(StrBuilder) / replace(StrBuilder, offset, length) ---
        StrBuilder bld = new StrBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(bld));
        if (testSubstringVariants) {
            assertEquals(expectedSubstringResult, sub.replace(bld, 1, bld.length() - 2));
        }

        // --- replace(Object) — delegates to toString() ---
        final MutableObject<String> obj = new MutableObject<>(replaceTemplate);
        assertEquals(expectedResult, sub.replace(obj));

        // --- replaceIn(StringBuffer) / replaceIn(StringBuffer, offset, length) ---
        // replaceIn modifies the buffer in-place and returns true when a substitution occurred
        buf = new StringBuffer(replaceTemplate);
        assertTrue(sub.replaceIn(buf));
        assertEquals(expectedResult, buf.toString());
        if (testSubstringVariants) {
            buf = new StringBuffer(replaceTemplate);
            assertTrue(sub.replaceIn(buf, 1, buf.length() - 2));
            // The untouched leading/trailing characters remain, so the full buffer equals expectedResult
            assertEquals(expectedResult, buf.toString());
        }

        // --- replaceIn(StringBuilder) / replaceIn(StringBuilder, offset, length) ---
        builder = new StringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (testSubstringVariants) {
            builder = new StringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(builder, 1, builder.length() - 2));
            assertEquals(expectedResult, builder.toString());
        }

        // --- replaceIn(StrBuilder) / replaceIn(StrBuilder, offset, length) ---
        bld = new StrBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(bld));
        assertEquals(expectedResult, bld.toString());
        if (testSubstringVariants) {
            bld = new StrBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(bld, 1, bld.length() - 2));
            assertEquals(expectedResult, bld.toString());
        }
    }
}
