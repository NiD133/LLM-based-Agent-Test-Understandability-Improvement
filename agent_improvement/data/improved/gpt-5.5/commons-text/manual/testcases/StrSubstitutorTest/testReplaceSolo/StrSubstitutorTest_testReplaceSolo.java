package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceSolo {

    private static final String VARIABLE_TEMPLATE = "${animal}";
    private static final String VARIABLE_VALUE = "quick brown fox";

    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("animal", VARIABLE_VALUE);
        values.put("target", "lazy dog");
    }

    /**
     * Tests simple key replace.
     */
    @Test
    void testReplaceSolo() {
        final StrSubstitutor substitutor = new StrSubstitutor(values);

        assertReplacementResult(substitutor);
        assertReplacementInMutableInputs(substitutor);
    }

    private void assertReplacementResult(final StrSubstitutor substitutor) {
        assertEquals(VARIABLE_VALUE, substitutor.replace(VARIABLE_TEMPLATE));

        final char[] templateChars = VARIABLE_TEMPLATE.toCharArray();
        assertEquals(VARIABLE_VALUE, substitutor.replace(templateChars));

        final StringBuffer stringBuffer = new StringBuffer(VARIABLE_TEMPLATE);
        assertEquals(VARIABLE_VALUE, substitutor.replace(stringBuffer));

        final StringBuilder stringBuilder = new StringBuilder(VARIABLE_TEMPLATE);
        assertEquals(VARIABLE_VALUE, substitutor.replace(stringBuilder));

        final StrBuilder strBuilder = new StrBuilder(VARIABLE_TEMPLATE);
        assertEquals(VARIABLE_VALUE, substitutor.replace(strBuilder));

        final MutableObject<String> objectWhoseToStringIsTheTemplate = new MutableObject<>(VARIABLE_TEMPLATE);
        assertEquals(VARIABLE_VALUE, substitutor.replace(objectWhoseToStringIsTheTemplate));
    }

    private void assertReplacementInMutableInputs(final StrSubstitutor substitutor) {
        final StringBuffer stringBuffer = new StringBuffer(VARIABLE_TEMPLATE);
        assertTrue(substitutor.replaceIn(stringBuffer));
        assertEquals(VARIABLE_VALUE, stringBuffer.toString());

        final StringBuilder stringBuilder = new StringBuilder(VARIABLE_TEMPLATE);
        assertTrue(substitutor.replaceIn(stringBuilder));
        assertEquals(VARIABLE_VALUE, stringBuilder.toString());

        final StrBuilder strBuilder = new StrBuilder(VARIABLE_TEMPLATE);
        assertTrue(substitutor.replaceIn(strBuilder));
        assertEquals(VARIABLE_VALUE, strBuilder.toString());
    }
}
