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
 * Tests that {@link StringSubstitutor} resolves a 3-character variable key (e.g. {@code ${aaa}})
 * consistently across every flavour of its {@code replace} / {@code replaceIn} API.
 */
public class StringSubstitutorTest_testReplaceSimpleKeySize3 {

    /** Template containing a single 3-character variable. */
    private static final String TEMPLATE = "${aaa}";

    /** Value the {@code aaa} variable resolves to. */
    private static final String EXPECTED = "111";

    /** Variable lookup data shared by the substitutor under test. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        // Keys of increasing length, so the size-3 key "aaa" is unambiguous.
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
     * Tests simple key replace for a key of length 3.
     */
    @Test
    void testReplaceSimpleKeySize3() throws IOException {
        final StringSubstitutor substitutor = new StringSubstitutor(values);

        // Every read-only "replace" overload returns the resolved value.
        assertEquals(EXPECTED, substitutor.replace(TEMPLATE));
        assertEquals(EXPECTED, substitutor.replace(TEMPLATE.toCharArray()));
        assertEquals(EXPECTED, substitutor.replace(new StringBuffer(TEMPLATE)));
        assertEquals(EXPECTED, substitutor.replace(new StringBuilder(TEMPLATE)));
        assertEquals(EXPECTED, substitutor.replace(new TextStringBuilder(TEMPLATE)));
        assertEquals(EXPECTED, substitutor.replace(new MutableObject<>(TEMPLATE)));

        // Every in-place "replaceIn" overload reports a change and rewrites the buffer.
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
