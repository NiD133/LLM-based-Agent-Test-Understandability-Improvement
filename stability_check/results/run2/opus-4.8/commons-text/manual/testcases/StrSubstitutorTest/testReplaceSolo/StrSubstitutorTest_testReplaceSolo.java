package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link StrSubstitutor} resolves a template consisting of a single
 * variable reference ({@code ${animal}}) against a map, exercising every
 * {@code replace(...)} and {@code replaceIn(...)} input type.
 */
public class StrSubstitutorTest_testReplaceSolo {

    private static final String TEMPLATE = "${animal}";
    private static final String EXPECTED = "quick brown fox";

    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    /**
     * Tests simple key replace: a lone {@code ${animal}} variable must resolve
     * to its mapped value regardless of how the source text is supplied.
     */
    @Test
    void testReplaceSolo() {
        final StrSubstitutor sub = new StrSubstitutor(values);

        // replace(...) returns the resolved text for each supported input type
        assertEquals(EXPECTED, sub.replace(TEMPLATE));
        assertEquals(EXPECTED, sub.replace(TEMPLATE.toCharArray()));
        assertEquals(EXPECTED, sub.replace(new StringBuffer(TEMPLATE)));
        assertEquals(EXPECTED, sub.replace(new StringBuilder(TEMPLATE)));
        assertEquals(EXPECTED, sub.replace(new StrBuilder(TEMPLATE)));
        // an arbitrary object is resolved via its toString()
        assertEquals(EXPECTED, sub.replace(new MutableObject<>(TEMPLATE)));

        // replaceIn(...) mutates the buffer in place and reports that a change was made
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
