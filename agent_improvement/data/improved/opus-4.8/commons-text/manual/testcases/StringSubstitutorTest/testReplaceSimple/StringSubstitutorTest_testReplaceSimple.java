package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StringSubstitutor} performs simple {@code ${key}} replacement
 * consistently across every input type its {@code replace}/{@code replaceIn} overloads accept
 * (String, char[], StringBuffer, StringBuilder, TextStringBuilder and arbitrary Object),
 * including the offset/length variants that operate on a sub-range of the input.
 */
public class StringSubstitutorTest_testReplaceSimple {

    private static final String ANIMAL = "quick brown fox";
    private static final String TARGET = "lazy dog";

    /** Template containing two {@code ${key}} expressions. */
    private static final String TEMPLATE = "The ${animal} jumps over the ${target}.";
    /** Expected text after both expressions are substituted. */
    private static final String RESULT = "The quick brown fox jumps over the lazy dog.";

    /** Variable bindings made available to the substitutor under test. */
    private Map<String, String> values;

    @BeforeEach
    void setUp() {
        values = new HashMap<>();
        // Short keys/values, kept from the original fixture so behaviour is unchanged.
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // Keys actually referenced by TEMPLATE.
        values.put("animal", ANIMAL);
        values.put("target", TARGET);
    }

    /**
     * Tests simple key replacement against the full template and every supported input type.
     */
    @Test
    void testReplaceSimple() throws IOException {
        final StringSubstitutor sub = new StringSubstitutor(values);

        // When replacing a sub-range (offset 1, dropping the first and last char of the template),
        // only the inner characters are processed, so the leading 'T' and trailing '.' are dropped.
        final String innerResult = RESULT.substring(1, RESULT.length() - 1);

        // --- replace(...) overloads: each returns a new String, leaving the input untouched ---

        assertEquals(RESULT, sub.replace(TEMPLATE),
            () -> "Index of difference: " + StringUtils.indexOfDifference(RESULT, sub.replace(TEMPLATE)));
        assertEquals(innerResult, sub.replace(TEMPLATE, 1, TEMPLATE.length() - 2));

        final char[] chars = TEMPLATE.toCharArray();
        assertEquals(RESULT, sub.replace(chars));
        assertEquals(innerResult, sub.replace(chars, 1, chars.length - 2));

        final StringBuffer buffer = new StringBuffer(TEMPLATE);
        assertEquals(RESULT, sub.replace(buffer));
        assertEquals(innerResult, sub.replace(buffer, 1, buffer.length() - 2));

        final StringBuilder builder = new StringBuilder(TEMPLATE);
        assertEquals(RESULT, sub.replace(builder));
        assertEquals(innerResult, sub.replace(builder, 1, builder.length() - 2));

        final TextStringBuilder textBuilder = new TextStringBuilder(TEMPLATE);
        assertEquals(RESULT, sub.replace(textBuilder));
        assertEquals(innerResult, sub.replace(textBuilder, 1, textBuilder.length() - 2));

        // Arbitrary Object: replace(Object) substitutes against its toString().
        assertEquals(RESULT, sub.replace(new MutableObject<>(TEMPLATE)));

        // --- replaceIn(...) overloads: substitute in place, returning true when text changed ---

        StringBuffer inPlaceBuffer = new StringBuffer(TEMPLATE);
        assertTrue(sub.replaceIn(inPlaceBuffer), TEMPLATE);
        assertEquals(RESULT, inPlaceBuffer.toString());
        // Sub-range variant: the untouched remainder still yields the full result.
        inPlaceBuffer = new StringBuffer(TEMPLATE);
        assertTrue(sub.replaceIn(inPlaceBuffer, 1, inPlaceBuffer.length() - 2));
        assertEquals(RESULT, inPlaceBuffer.toString());

        StringBuilder inPlaceBuilder = new StringBuilder(TEMPLATE);
        assertTrue(sub.replaceIn(inPlaceBuilder));
        assertEquals(RESULT, inPlaceBuilder.toString());
        inPlaceBuilder = new StringBuilder(TEMPLATE);
        assertTrue(sub.replaceIn(inPlaceBuilder, 1, inPlaceBuilder.length() - 2));
        assertEquals(RESULT, inPlaceBuilder.toString());

        TextStringBuilder inPlaceTextBuilder = new TextStringBuilder(TEMPLATE);
        assertTrue(sub.replaceIn(inPlaceTextBuilder));
        assertEquals(RESULT, inPlaceTextBuilder.toString());
        inPlaceTextBuilder = new TextStringBuilder(TEMPLATE);
        assertTrue(sub.replaceIn(inPlaceTextBuilder, 1, inPlaceTextBuilder.length() - 2));
        assertEquals(RESULT, inPlaceTextBuilder.toString());
    }
}
