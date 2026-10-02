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
 * Tests that {@link StringSubstitutor} replaces a simple two-character key
 * ({@code ${aa}} -> {@code 11}) consistently across every {@code replace} and
 * {@code replaceIn} overload.
 */
public class StringSubstitutorTest_testReplaceSimpleKeySize2 {

    /** Placeholder template containing the single key under test. */
    private static final String TEMPLATE = "${aa}";

    /** Expected output after the key {@code aa} is substituted. */
    private static final String EXPECTED = "11";

    /** Variable-to-value mappings made available to the substitutor. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        // Short keys and values, including the "aa" -> "11" pair under test.
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // Normal-length keys and values.
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    /**
     * Replacing {@code ${aa}} must yield {@code 11} regardless of which input
     * type is passed to {@code replace} / {@code replaceIn}.
     */
    @Test
    void testReplaceSimpleKeySize2() throws IOException {
        final StringSubstitutor substitutor = new StringSubstitutor(values);

        // replace(...) overloads return the substituted result as a new String.
        assertEquals(EXPECTED, substitutor.replace(TEMPLATE));
        assertEquals(EXPECTED, substitutor.replace(TEMPLATE.toCharArray()));
        assertEquals(EXPECTED, substitutor.replace(new StringBuffer(TEMPLATE)));
        assertEquals(EXPECTED, substitutor.replace(new StringBuilder(TEMPLATE)));
        assertEquals(EXPECTED, substitutor.replace(new TextStringBuilder(TEMPLATE)));
        // replace(Object) substitutes against the object's toString().
        assertEquals(EXPECTED, substitutor.replace(new MutableObject<>(TEMPLATE)));

        // replaceIn(...) overloads mutate the buffer in place and report whether
        // any substitution occurred.
        final StringBuffer stringBuffer = new StringBuffer(TEMPLATE);
        assertTrue(substitutor.replaceIn(stringBuffer));
        assertEquals(EXPECTED, stringBuffer.toString());

        final StringBuilder stringBuilder = new StringBuilder(TEMPLATE);
        assertTrue(substitutor.replaceIn(stringBuilder));
        assertEquals(EXPECTED, stringBuilder.toString());

        final TextStringBuilder textStringBuilder = new TextStringBuilder(TEMPLATE);
        assertTrue(substitutor.replaceIn(textStringBuilder));
        assertEquals(EXPECTED, textStringBuilder.toString());
    }
}
