package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link StrSubstitutor} treats a variable whose key is NOT present
 * in the value map: the placeholder is expected to be left untouched in the output,
 * while known keys (and default-value expressions) are still resolved.
 */
public class StrSubstitutorTest_testReplaceUnknownKey {

    /** Variable values shared by every assertion in a single test run. */
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

    /**
     * An unknown key (here {@code ${person}}) must survive substitution verbatim,
     * whereas the known key {@code ${target}} and the default-value expression
     * {@code ${undefined.number:-1234567890}} must be resolved.
     */
    @Test
    void testReplaceUnknownKey() {
        assertReplaces(
                "The ${person} jumps over the lazy dog.",
                "The ${person} jumps over the ${target}.");
        assertReplaces(
                "The ${person} jumps over the lazy dog. 1234567890.",
                "The ${person} jumps over the ${target}. ${undefined.number:-1234567890}.");
    }

    /**
     * Runs the substitution defined by {@link #values} over {@code template} and asserts
     * that it yields {@code expected}, exercising every input flavour {@link StrSubstitutor}
     * supports: {@code String}, {@code char[]}, {@code StringBuffer}, {@code StringBuilder},
     * {@code StrBuilder} and a generic {@code Object}.
     *
     * <p>For each input type it checks both the "replace whole text" overloads and, where the
     * type is mutable, the in-place {@code replaceIn} variants. It also checks the
     * offset/length overloads against the template with its first and last characters
     * stripped off, expecting the correspondingly trimmed result.</p>
     */
    private void assertReplaces(final String expected, final String template) {
        final StrSubstitutor sub = new StrSubstitutor(values);

        // The template minus its first and last character; the offset/length overloads
        // below substitute only this inner span, so they must yield the trimmed result.
        final String expectedInner = expected.substring(1, expected.length() - 1);
        final int innerOffset = 1;
        final int innerLengthOf = template.length() - 2;

        // --- replace(...) returning a new String, one assertion per input type ---

        // String
        assertEquals(expected, sub.replace(template));
        assertEquals(expectedInner, sub.replace(template, innerOffset, innerLengthOf));

        // char[]
        final char[] chars = template.toCharArray();
        assertEquals(expected, sub.replace(chars));
        assertEquals(expectedInner, sub.replace(chars, innerOffset, chars.length - 2));

        // StringBuffer
        final StringBuffer buffer = new StringBuffer(template);
        assertEquals(expected, sub.replace(buffer));
        assertEquals(expectedInner, sub.replace(buffer, innerOffset, buffer.length() - 2));

        // StringBuilder
        final StringBuilder builder = new StringBuilder(template);
        assertEquals(expected, sub.replace(builder));
        assertEquals(expectedInner, sub.replace(builder, innerOffset, builder.length() - 2));

        // StrBuilder
        final StrBuilder strBuilder = new StrBuilder(template);
        assertEquals(expected, sub.replace(strBuilder));
        assertEquals(expectedInner, sub.replace(strBuilder, innerOffset, strBuilder.length() - 2));

        // Generic Object whose toString() returns the template.
        final MutableObject<String> obj = new MutableObject<>(template);
        assertEquals(expected, sub.replace(obj));

        // --- replaceIn(...) mutating the buffer in place and returning whether it changed ---

        // StringBuffer, whole text
        StringBuffer inPlaceBuffer = new StringBuffer(template);
        assertTrue(sub.replaceIn(inPlaceBuffer));
        assertEquals(expected, inPlaceBuffer.toString());
        // StringBuffer, inner span only: the untouched remainder still yields the full result.
        inPlaceBuffer = new StringBuffer(template);
        assertTrue(sub.replaceIn(inPlaceBuffer, innerOffset, inPlaceBuffer.length() - 2));
        assertEquals(expected, inPlaceBuffer.toString());

        // StringBuilder, whole text
        StringBuilder inPlaceBuilder = new StringBuilder(template);
        assertTrue(sub.replaceIn(inPlaceBuilder));
        assertEquals(expected, inPlaceBuilder.toString());
        // StringBuilder, inner span only.
        inPlaceBuilder = new StringBuilder(template);
        assertTrue(sub.replaceIn(inPlaceBuilder, innerOffset, inPlaceBuilder.length() - 2));
        assertEquals(expected, inPlaceBuilder.toString());

        // StrBuilder, whole text
        StrBuilder inPlaceStrBuilder = new StrBuilder(template);
        assertTrue(sub.replaceIn(inPlaceStrBuilder));
        assertEquals(expected, inPlaceStrBuilder.toString());
        // StrBuilder, inner span only.
        inPlaceStrBuilder = new StrBuilder(template);
        assertTrue(sub.replaceIn(inPlaceStrBuilder, innerOffset, inPlaceStrBuilder.length() - 2));
        assertEquals(expected, inPlaceStrBuilder.toString());
    }
}
