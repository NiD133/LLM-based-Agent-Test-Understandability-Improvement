package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StringSubstitutor} resolves a simple two-character key
 * ({@code "${aa}"} -> {@code "11"}) consistently across every {@code replace}
 * and {@code replaceIn} overload.
 */
public class StringSubstitutorTest_testReplaceSimpleKeySize2 {

    /** Template holding a single variable reference to the key {@code "aa"}. */
    private static final String TEMPLATE = "${aa}";

    /** Value that the substitutor should produce for {@link #TEMPLATE}. */
    private static final String EXPECTED = "11";

    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        // A mix of short and normal keys; only "aa" is exercised by this test.
        values = new HashMap<>();
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    /**
     * Tests simple key replace across the various input types.
     */
    @Test
    void testReplaceSimpleKeySize2() throws IOException {
        final StringSubstitutor substitutor = new StringSubstitutor(values);

        // Every replace(...) overload returns the resolved value without mutating input.
        assertEquals(EXPECTED, substitutor.replace(TEMPLATE));
        assertEquals(EXPECTED, substitutor.replace(TEMPLATE.toCharArray()));
        assertEquals(EXPECTED, substitutor.replace(new StringBuffer(TEMPLATE)));
        assertEquals(EXPECTED, substitutor.replace(new StringBuilder(TEMPLATE)));
        assertEquals(EXPECTED, substitutor.replace(new TextStringBuilder(TEMPLATE)));
        assertEquals(EXPECTED, substitutor.replace(new MutableObject<>(TEMPLATE)));

        // Every replaceIn(...) overload substitutes in place and reports that it changed the buffer.
        final StringBuffer buffer = new StringBuffer(TEMPLATE);
        assertTrue(substitutor.replaceIn(buffer));
        assertEquals(EXPECTED, buffer.toString());

        final StringBuilder builder = new StringBuilder(TEMPLATE);
        assertTrue(substitutor.replaceIn(builder));
        assertEquals(EXPECTED, builder.toString());

        final TextStringBuilder textBuilder = new TextStringBuilder(TEMPLATE);
        assertTrue(substitutor.replaceIn(textBuilder));
        assertEquals(EXPECTED, textBuilder.toString());
    }
}
