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

public class StrSubstitutorTest_testReplaceEscaping {

    // Variable names and their substitution values used across all tests.
    private static final String VAR_ANIMAL = "animal";
    private static final String VAR_TARGET = "target";
    private static final String VALUE_ANIMAL = "quick brown fox";
    private static final String VALUE_TARGET = "lazy dog";

    private Map<String, String> values;

    @BeforeEach
    public void setUp() throws Exception {
        values = new HashMap<>();
        values.put(VAR_ANIMAL, VALUE_ANIMAL);
        values.put(VAR_TARGET, VALUE_TARGET);
    }

    @AfterEach
    public void tearDown() throws Exception {
        values = null;
    }

    /**
     * Tests that prefixing a variable reference with $$ (the default escape character doubled)
     * prevents substitution, leaving the literal ${...} text in the output,
     * while adjacent normal variable references are still resolved.
     *
     * Template : "The $${animal} jumps over the ${target}."
     * Expected : "The ${animal} jumps over the lazy dog."
     *            ^^^^^^^^^^^^ escaped — not substituted
     *                                   ^^^^^^^^ substituted normally
     */
    @Test
    void testReplaceEscaping() {
        final String template = "The $${animal} jumps over the ${target}.";
        final String expected = "The ${animal} jumps over the lazy dog.";

        doTestReplace(expected, template, /* substring= */ true);
    }

    // -----------------------------------------------------------------------
    // Helpers
    // -----------------------------------------------------------------------

    /**
     * Asserts that no replacement occurs for the given template (or null input).
     * Covers every replace/replaceIn overload to ensure consistent null-safety
     * and no-op behaviour.
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

    /**
     * Convenience overload that builds a default substitutor from {@link #values}.
     */
    private void doTestReplace(final String expectedResult, final String replaceTemplate, final boolean substring) {
        doTestReplace(new StrSubstitutor(values), expectedResult, replaceTemplate, substring);
    }

    /**
     * Exercises every replace / replaceIn overload and confirms each produces
     * {@code expectedResult} for the given {@code replaceTemplate}.
     *
     * When {@code substring} is true, also verifies that replacing only the
     * inner slice [1, len-2] of the template yields the same inner slice of
     * the expected result — the outer characters must be left untouched.
     */
    private void doTestReplace(final StrSubstitutor sub,
                                final String expectedResult,
                                final String replaceTemplate,
                                final boolean substring) {

        // The "short" result is expectedResult with its first and last chars stripped,
        // matching what a substring replace of [1, len-2] should produce.
        final String expectedShortResult = expectedResult.substring(1, expectedResult.length() - 1);

        // --- replace(String) ---
        assertEquals(expectedResult, sub.replace(replaceTemplate));
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

        // --- replace(StrBuilder) ---
        StrBuilder bld = new StrBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(bld));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(bld, 1, bld.length() - 2));
        }

        // --- replace(Object) — StrSubstitutor calls toString() on the object ---
        final MutableObject<String> obj = new MutableObject<>(replaceTemplate);
        assertEquals(expectedResult, sub.replace(obj));

        // --- replaceIn(StringBuffer) — mutates the buffer in place ---
        buf = new StringBuffer(replaceTemplate);
        assertTrue(sub.replaceIn(buf));
        assertEquals(expectedResult, buf.toString());
        if (substring) {
            buf = new StringBuffer(replaceTemplate);
            assertTrue(sub.replaceIn(buf, 1, buf.length() - 2));
            // The outer characters are untouched; the whole buffer now matches the full expected result.
            assertEquals(expectedResult, buf.toString());
        }

        // --- replaceIn(StringBuilder) — mutates the builder in place ---
        builder = new StringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (substring) {
            builder = new StringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(builder, 1, builder.length() - 2));
            assertEquals(expectedResult, builder.toString());
        }

        // --- replaceIn(StrBuilder) — mutates the StrBuilder in place ---
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
