package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StrSubstitutor} resolves a single {@code ${key}} placeholder
 * to its mapped value, and that it does so consistently across every supported input type.
 */
public class StrSubstitutorTest_testReplaceSolo {

    private static final String TEMPLATE = "${animal}";
    private static final String EXPECTED = "quick brown fox";

    /** Substitution map used to build the substitutor under test. */
    private static Map<String, String> newValues() {
        final Map<String, String> values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
        return values;
    }

    /**
     * Tests simple key replace.
     *
     * <p>The template {@code "${animal}"} must resolve to {@code "quick brown fox"} regardless of
     * whether it is supplied as a String, char[], StringBuffer, StringBuilder, StrBuilder or a
     * generic Object, and regardless of whether the result is returned ({@code replace})
     * or applied in place ({@code replaceIn}).</p>
     */
    @Test
    void testReplaceSolo() {
        final StrSubstitutor sub = new StrSubstitutor(newValues());

        // replace(...) returns the resolved text for every accepted input type.
        assertEquals(EXPECTED, sub.replace(TEMPLATE));
        assertEquals(EXPECTED, sub.replace(TEMPLATE.toCharArray()));
        assertEquals(EXPECTED, sub.replace(new StringBuffer(TEMPLATE)));
        assertEquals(EXPECTED, sub.replace(new StringBuilder(TEMPLATE)));
        assertEquals(EXPECTED, sub.replace(new StrBuilder(TEMPLATE)));
        // An arbitrary object is resolved via its toString().
        assertEquals(EXPECTED, sub.replace(new MutableObject<>(TEMPLATE)));

        // replaceIn(...) mutates the buffer in place and reports that a replacement happened.
        final StringBuffer buffer = new StringBuffer(TEMPLATE);
        assertTrue(sub.replaceIn(buffer));
        assertEquals(EXPECTED, buffer.toString());

        final StringBuilder builder = new StringBuilder(TEMPLATE);
        assertTrue(sub.replaceIn(builder));
        assertEquals(EXPECTED, builder.toString());

        final StrBuilder strBuilder = new StrBuilder(TEMPLATE);
        assertTrue(sub.replaceIn(strBuilder));
        assertEquals(EXPECTED, strBuilder.toString());
    }
}
