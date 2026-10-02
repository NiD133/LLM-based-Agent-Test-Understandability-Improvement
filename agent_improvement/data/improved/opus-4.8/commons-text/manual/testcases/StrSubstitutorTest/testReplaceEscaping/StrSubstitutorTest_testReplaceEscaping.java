package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceEscaping {

    /** Variables made available to the substitutor under test. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    /**
     * Tests that a doubled prefix ({@code $$}) escapes a variable reference so it is left untouched,
     * while a normal reference ({@code ${target}}) is still substituted.
     */
    @Test
    void testReplaceEscaping() {
        final String template = "The $${animal} jumps over the ${target}.";
        final String expected = "The ${animal} jumps over the lazy dog.";

        assertReplaceWorksForEveryInputType(expected, template);
    }

    /**
     * Runs the same escaping replacement through every {@code replace} / {@code replaceIn} overload of
     * {@link StrSubstitutor} (String, char[], StringBuffer, StringBuilder, StrBuilder and Object inputs),
     * asserting both the whole-template result and the result of substituting only the inner substring.
     *
     * <p>The "inner substring" drops the first and last character of the template. Because the
     * surrounding (untouched) characters happen to be identical in template and result, the in-place
     * {@code replaceIn(..., offset, length)} overloads are expected to yield the full result.</p>
     */
    private void assertReplaceWorksForEveryInputType(final String expected, final String template) {
        final StrSubstitutor sub = new StrSubstitutor(values);

        // Result expected when only the inner portion (first and last char removed) is processed.
        final String expectedInner = expected.substring(1, expected.length() - 1);
        final int innerOffset = 1;

        // --- replace(...) returning a new String ---
        assertEquals(expected, sub.replace(template));
        assertEquals(expectedInner, sub.replace(template, innerOffset, template.length() - 2));

        final char[] chars = template.toCharArray();
        assertEquals(expected, sub.replace(chars));
        assertEquals(expectedInner, sub.replace(chars, innerOffset, chars.length - 2));

        StringBuffer buffer = new StringBuffer(template);
        assertEquals(expected, sub.replace(buffer));
        assertEquals(expectedInner, sub.replace(buffer, innerOffset, buffer.length() - 2));

        StringBuilder builder = new StringBuilder(template);
        assertEquals(expected, sub.replace(builder));
        assertEquals(expectedInner, sub.replace(builder, innerOffset, builder.length() - 2));

        StrBuilder strBuilder = new StrBuilder(template);
        assertEquals(expected, sub.replace(strBuilder));
        assertEquals(expectedInner, sub.replace(strBuilder, innerOffset, strBuilder.length() - 2));

        // Object input: its toString() returns the template.
        final MutableObject<String> objectInput = new MutableObject<>(template);
        assertEquals(expected, sub.replace(objectInput));

        // --- replaceIn(...) mutating the buffer in place and returning whether it changed ---
        buffer = new StringBuffer(template);
        assertTrue(sub.replaceIn(buffer));
        assertEquals(expected, buffer.toString());

        buffer = new StringBuffer(template);
        assertTrue(sub.replaceIn(buffer, innerOffset, buffer.length() - 2));
        assertEquals(expected, buffer.toString()); // remainder outside the range is left untouched

        builder = new StringBuilder(template);
        assertTrue(sub.replaceIn(builder));
        assertEquals(expected, builder.toString());

        builder = new StringBuilder(template);
        assertTrue(sub.replaceIn(builder, innerOffset, builder.length() - 2));
        assertEquals(expected, builder.toString());

        strBuilder = new StrBuilder(template);
        assertTrue(sub.replaceIn(strBuilder));
        assertEquals(expected, strBuilder.toString());

        strBuilder = new StrBuilder(template);
        assertTrue(sub.replaceIn(strBuilder, innerOffset, strBuilder.length() - 2));
        assertEquals(expected, strBuilder.toString());
    }
}
