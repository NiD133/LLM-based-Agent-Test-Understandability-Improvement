package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link StrSubstitutor} behaviour when a template contains a variable
 * prefix ("${") that is never closed by a suffix ("}").
 */
public class StrSubstitutorTest_testReplacePrefixNoSuffix {

    /** Variable values shared by the substitutor under test. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    /**
     * An unterminated variable reference ("${animal" with no closing "}") must be
     * left untouched, while a well-formed reference ("${target}") on the same line
     * is still resolved normally.
     */
    @Test
    void testReplacePrefixNoSuffix() {
        // The unterminated "${animal" consumes everything up to the first "}", so the first
        // "${target}" is absorbed into that unresolved reference and left verbatim. Only the
        // trailing, well-formed "${target}" is resolved to its value ("lazy dog").
        final String template = "The ${animal jumps over the ${target} ${target}.";
        final String expected = "The ${animal jumps over the ${target} lazy dog.";

        assertReplacedConsistentlyAcrossAllInputTypes(expected, template);
    }

    /**
     * Runs the substitution against every input type {@link StrSubstitutor} accepts and
     * asserts they all yield {@code expected}. Both the whole-template overloads and the
     * (offset, length) substring overloads are exercised.
     * <p>
     * For the substring overloads we substitute only the interior of the template
     * (dropping the first and last character). Because the trimmed boundary characters do
     * not break any variable reference, the visible result is identical to substituting the
     * whole template, hence {@code expected} is reused for those checks too.
     *
     * @param expected the fully substituted text
     * @param template the raw template containing the variable references
     */
    private void assertReplacedConsistentlyAcrossAllInputTypes(final String expected, final String template) {
        final StrSubstitutor substitutor = new StrSubstitutor(values);

        // The interior result equals the full result minus its first and last character.
        final String expectedInterior = expected.substring(1, expected.length() - 1);

        // --- replace(...) overloads: produce a new value, leaving the source unchanged ---

        // String input
        assertEquals(expected, substitutor.replace(template));
        assertEquals(expectedInterior, substitutor.replace(template, 1, template.length() - 2));

        // char[] input
        final char[] chars = template.toCharArray();
        assertEquals(expected, substitutor.replace(chars));
        assertEquals(expectedInterior, substitutor.replace(chars, 1, chars.length - 2));

        // StringBuffer input
        final StringBuffer buffer = new StringBuffer(template);
        assertEquals(expected, substitutor.replace(buffer));
        assertEquals(expectedInterior, substitutor.replace(buffer, 1, buffer.length() - 2));

        // StringBuilder input
        final StringBuilder builder = new StringBuilder(template);
        assertEquals(expected, substitutor.replace(builder));
        assertEquals(expectedInterior, substitutor.replace(builder, 1, builder.length() - 2));

        // StrBuilder input
        final StrBuilder strBuilder = new StrBuilder(template);
        assertEquals(expected, substitutor.replace(strBuilder));
        assertEquals(expectedInterior, substitutor.replace(strBuilder, 1, strBuilder.length() - 2));

        // Object input (its toString() supplies the template)
        final MutableObject<String> object = new MutableObject<>(template);
        assertEquals(expected, substitutor.replace(object));

        // --- replaceIn(...) overloads: substitute in place and report whether anything changed ---

        // StringBuffer, whole buffer
        StringBuffer inPlaceBuffer = new StringBuffer(template);
        assertTrue(substitutor.replaceIn(inPlaceBuffer));
        assertEquals(expected, inPlaceBuffer.toString());

        // StringBuffer, interior only (the untouched boundary chars keep the full expected text)
        inPlaceBuffer = new StringBuffer(template);
        assertTrue(substitutor.replaceIn(inPlaceBuffer, 1, inPlaceBuffer.length() - 2));
        assertEquals(expected, inPlaceBuffer.toString());

        // StringBuilder, whole builder
        StringBuilder inPlaceBuilder = new StringBuilder(template);
        assertTrue(substitutor.replaceIn(inPlaceBuilder));
        assertEquals(expected, inPlaceBuilder.toString());

        // StringBuilder, interior only
        inPlaceBuilder = new StringBuilder(template);
        assertTrue(substitutor.replaceIn(inPlaceBuilder, 1, inPlaceBuilder.length() - 2));
        assertEquals(expected, inPlaceBuilder.toString());

        // StrBuilder, whole builder
        StrBuilder inPlaceStrBuilder = new StrBuilder(template);
        assertTrue(substitutor.replaceIn(inPlaceStrBuilder));
        assertEquals(expected, inPlaceStrBuilder.toString());

        // StrBuilder, interior only
        inPlaceStrBuilder = new StrBuilder(template);
        assertTrue(substitutor.replaceIn(inPlaceStrBuilder, 1, inPlaceStrBuilder.length() - 2));
        assertEquals(expected, inPlaceStrBuilder.toString());
    }
}
