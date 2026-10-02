package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StrSubstitutor} performs a simple two-variable
 * substitution consistently across every supported input type.
 */
public class StrSubstitutorTest_testReplaceSimple {

    /** Template containing the {@code ${animal}} and {@code ${target}} variables. */
    private static final String TEMPLATE = "The ${animal} jumps over the ${target}.";

    /** Result of substituting every variable in {@link #TEMPLATE}. */
    private static final String EXPECTED = "The quick brown fox jumps over the lazy dog.";

    /** Variable values shared by all assertions. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    /**
     * Tests simple key replace.
     *
     * <p>Each supported input type (String, char[], StringBuffer, StringBuilder,
     * StrBuilder and a generic Object) is exercised in three ways:</p>
     * <ul>
     *   <li>{@code replace(whole)} substitutes the full template;</li>
     *   <li>{@code replace(part, offset, length)} substitutes a sub-range;</li>
     *   <li>{@code replaceIn(buffer)} substitutes in place and reports a change.</li>
     * </ul>
     */
    @Test
    void testReplaceSimple() {
        final StrSubstitutor sub = new StrSubstitutor(values);

        // The sub-range drops the first and last character of the template; because
        // those characters lie outside every variable, the substituted sub-range is
        // simply EXPECTED without its own first and last character.
        final int rangeOffset = 1;
        final int rangeLength = TEMPLATE.length() - 2;
        final String expectedRange = EXPECTED.substring(1, EXPECTED.length() - 1);

        // --- replace(...): returns a substituted copy, leaving the source untouched ---

        // from a String
        assertEquals(EXPECTED, sub.replace(TEMPLATE));
        assertEquals(expectedRange, sub.replace(TEMPLATE, rangeOffset, rangeLength));

        // from a char[]
        final char[] chars = TEMPLATE.toCharArray();
        assertEquals(EXPECTED, sub.replace(chars));
        assertEquals(expectedRange, sub.replace(chars, rangeOffset, rangeLength));

        // from a StringBuffer
        final StringBuffer buffer = new StringBuffer(TEMPLATE);
        assertEquals(EXPECTED, sub.replace(buffer));
        assertEquals(expectedRange, sub.replace(buffer, rangeOffset, rangeLength));

        // from a StringBuilder
        final StringBuilder stringBuilder = new StringBuilder(TEMPLATE);
        assertEquals(EXPECTED, sub.replace(stringBuilder));
        assertEquals(expectedRange, sub.replace(stringBuilder, rangeOffset, rangeLength));

        // from a StrBuilder
        final StrBuilder strBuilder = new StrBuilder(TEMPLATE);
        assertEquals(EXPECTED, sub.replace(strBuilder));
        assertEquals(expectedRange, sub.replace(strBuilder, rangeOffset, rangeLength));

        // from a generic Object whose toString() is the template
        final MutableObject<String> templateObject = new MutableObject<>(TEMPLATE);
        assertEquals(EXPECTED, sub.replace(templateObject));

        // --- replaceIn(...): substitutes in place and returns true when it changes ---

        // in a StringBuffer (whole, then sub-range)
        StringBuffer inBuffer = new StringBuffer(TEMPLATE);
        assertTrue(sub.replaceIn(inBuffer));
        assertEquals(EXPECTED, inBuffer.toString());
        inBuffer = new StringBuffer(TEMPLATE);
        assertTrue(sub.replaceIn(inBuffer, rangeOffset, rangeLength));
        // the untouched remainder keeps the full result
        assertEquals(EXPECTED, inBuffer.toString());

        // in a StringBuilder (whole, then sub-range)
        StringBuilder inStringBuilder = new StringBuilder(TEMPLATE);
        assertTrue(sub.replaceIn(inStringBuilder));
        assertEquals(EXPECTED, inStringBuilder.toString());
        inStringBuilder = new StringBuilder(TEMPLATE);
        assertTrue(sub.replaceIn(inStringBuilder, rangeOffset, rangeLength));
        assertEquals(EXPECTED, inStringBuilder.toString());

        // in a StrBuilder (whole, then sub-range)
        StrBuilder inStrBuilder = new StrBuilder(TEMPLATE);
        assertTrue(sub.replaceIn(inStrBuilder));
        assertEquals(EXPECTED, inStrBuilder.toString());
        inStrBuilder = new StrBuilder(TEMPLATE);
        assertTrue(sub.replaceIn(inStrBuilder, rangeOffset, rangeLength));
        assertEquals(EXPECTED, inStrBuilder.toString());
    }
}
