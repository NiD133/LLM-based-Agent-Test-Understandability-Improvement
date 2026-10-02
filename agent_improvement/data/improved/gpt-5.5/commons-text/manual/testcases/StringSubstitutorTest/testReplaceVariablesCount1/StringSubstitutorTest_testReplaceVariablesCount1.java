package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplaceVariablesCount1 {

    private static final String VARIABLE_NAME = "animal";
    private static final String VARIABLE_TEMPLATE = "${" + VARIABLE_NAME + "}";
    private static final String ANIMAL_VALUE = "quick brown fox";

    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();

        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        values.put(VARIABLE_NAME, ANIMAL_VALUE);
        values.put("target", "lazy dog");
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    @Test
    void testReplaceVariablesCount1() {
        final StringSubstitutor substitutor = new StringSubstitutor(values);

        assertEquals(ANIMAL_VALUE, substitutor.replace(VARIABLE_TEMPLATE));

        final char[] templateChars = VARIABLE_TEMPLATE.toCharArray();
        assertEquals(ANIMAL_VALUE, substitutor.replace(templateChars));

        StringBuffer stringBuffer = new StringBuffer(VARIABLE_TEMPLATE);
        assertEquals(ANIMAL_VALUE, substitutor.replace(stringBuffer));

        StringBuilder stringBuilder = new StringBuilder(VARIABLE_TEMPLATE);
        assertEquals(ANIMAL_VALUE, substitutor.replace(stringBuilder));

        TextStringBuilder textStringBuilder = new TextStringBuilder(VARIABLE_TEMPLATE);
        assertEquals(ANIMAL_VALUE, substitutor.replace(textStringBuilder));

        final MutableObject<String> objectTemplate = new MutableObject<>(VARIABLE_TEMPLATE);
        assertEquals(ANIMAL_VALUE, substitutor.replace(objectTemplate));

        stringBuffer = new StringBuffer(VARIABLE_TEMPLATE);
        assertTrue(substitutor.replaceIn(stringBuffer), VARIABLE_TEMPLATE);
        assertEquals(ANIMAL_VALUE, stringBuffer.toString());

        stringBuilder = new StringBuilder(VARIABLE_TEMPLATE);
        assertTrue(substitutor.replaceIn(stringBuilder));
        assertEquals(ANIMAL_VALUE, stringBuilder.toString());

        textStringBuilder = new TextStringBuilder(VARIABLE_TEMPLATE);
        assertTrue(substitutor.replaceIn(textStringBuilder));
        assertEquals(ANIMAL_VALUE, textStringBuilder.toString());
    }
}
