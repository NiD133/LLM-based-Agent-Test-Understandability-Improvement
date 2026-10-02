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
 * Tests that {@link StringSubstitutor} resolves a single, one-character key
 * ({@code ${a}} -> {@code 1}) consistently across every {@code replace} and
 * {@code replaceIn} overload.
 */
public class StringSubstitutorTest_testReplaceSimpleKeySize1 {

    /** Template containing exactly one variable to substitute. */
    private static final String TEMPLATE = "${a}";

    /** Expected output after the variable {@code a} is resolved. */
    private static final String EXPECTED = "1";

    /** Lookup values shared by all overloads under test. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        // Short keys/values, including overlapping prefixes, to ensure the
        // single-character key "a" is matched exactly rather than greedily.
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // Normal-length keys/values.
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    /**
     * Tests simple key replace across every input type the substitutor accepts.
     */
    @Test
    void testReplaceSimpleKeySize1() throws IOException {
        final StringSubstitutor substitutor = new StringSubstitutor(values);

        // replace(...) returns the resolved text without mutating the input.
        assertEquals(EXPECTED, substitutor.replace(TEMPLATE));
        assertEquals(EXPECTED, substitutor.replace(TEMPLATE.toCharArray()));
        assertEquals(EXPECTED, substitutor.replace(new StringBuffer(TEMPLATE)));
        assertEquals(EXPECTED, substitutor.replace(new StringBuilder(TEMPLATE)));
        assertEquals(EXPECTED, substitutor.replace(new TextStringBuilder(TEMPLATE)));
        // replace(Object) substitutes against the object's toString().
        assertEquals(EXPECTED, substitutor.replace(new MutableObject<>(TEMPLATE)));

        // replaceIn(...) mutates the buffer in place and reports whether a
        // substitution occurred.
        final StringBuffer buffer = new StringBuffer(TEMPLATE);
        assertTrue(substitutor.replaceIn(buffer), TEMPLATE);
        assertEquals(EXPECTED, buffer.toString());

        final StringBuilder builder = new StringBuilder(TEMPLATE);
        assertTrue(substitutor.replaceIn(builder));
        assertEquals(EXPECTED, builder.toString());

        final TextStringBuilder textBuilder = new TextStringBuilder(TEMPLATE);
        assertTrue(substitutor.replaceIn(textBuilder));
        assertEquals(EXPECTED, textBuilder.toString());
    }
}
