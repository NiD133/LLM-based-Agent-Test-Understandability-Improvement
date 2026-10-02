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
 * Tests {@link StringSubstitutor} replacement of a simple three-character key
 * ({@code ${aaa}}) across every non-substring {@code replace} / {@code replaceIn}
 * overload.
 */
public class StringSubstitutorTest_testReplaceSimpleKeySize3 {

    /** Template containing a single variable reference to the key {@code aaa}. */
    private static final String TEMPLATE = "${aaa}";

    /** Value the substitutor should expand {@link #TEMPLATE} into. */
    private static final String EXPECTED_RESULT = "111";

    /** Lookup values shared by the substitutor under test. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        // Keys of increasing length that share a common prefix, so lookups of
        // different key sizes (1, 2 and 3 characters) can be exercised.
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
     * Asserts that the substitutor expands {@link #TEMPLATE} into
     * {@link #EXPECTED_RESULT} regardless of the concrete input type.
     */
    private void assertTemplateIsReplaced() {
        final StringSubstitutor substitutor = new StringSubstitutor(values);

        // replace(...) overloads each return a new String with the key expanded.
        assertEquals(EXPECTED_RESULT, substitutor.replace(TEMPLATE));
        assertEquals(EXPECTED_RESULT, substitutor.replace(TEMPLATE.toCharArray()));
        assertEquals(EXPECTED_RESULT, substitutor.replace(new StringBuffer(TEMPLATE)));
        assertEquals(EXPECTED_RESULT, substitutor.replace(new StringBuilder(TEMPLATE)));
        assertEquals(EXPECTED_RESULT, substitutor.replace(new TextStringBuilder(TEMPLATE)));
        // replace(Object) uses the argument's toString(), which returns TEMPLATE.
        assertEquals(EXPECTED_RESULT, substitutor.replace(new MutableObject<>(TEMPLATE)));

        // replaceIn(...) overloads mutate the buffer in place and report success.
        final StringBuffer stringBuffer = new StringBuffer(TEMPLATE);
        assertTrue(substitutor.replaceIn(stringBuffer));
        assertEquals(EXPECTED_RESULT, stringBuffer.toString());

        final StringBuilder stringBuilder = new StringBuilder(TEMPLATE);
        assertTrue(substitutor.replaceIn(stringBuilder));
        assertEquals(EXPECTED_RESULT, stringBuilder.toString());

        final TextStringBuilder textStringBuilder = new TextStringBuilder(TEMPLATE);
        assertTrue(substitutor.replaceIn(textStringBuilder));
        assertEquals(EXPECTED_RESULT, textStringBuilder.toString());
    }

    /**
     * Tests simple key replace.
     */
    @Test
    void testReplaceSimpleKeySize3() throws IOException {
        assertTemplateIsReplaced();
    }
}
