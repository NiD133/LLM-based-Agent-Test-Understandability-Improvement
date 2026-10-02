package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplaceSimpleKeySize2 {

    private static final String SIMPLE_TWO_CHARACTER_KEY_TEMPLATE = "${aa}";
    private static final String SIMPLE_TWO_CHARACTER_KEY_RESULT = "11";

    @Test
    void testReplaceSimpleKeySize2() {
        final StringSubstitutor substitutor = new StringSubstitutor(createValues());

        assertEquals(SIMPLE_TWO_CHARACTER_KEY_RESULT, substitutor.replace(SIMPLE_TWO_CHARACTER_KEY_TEMPLATE));

        final char[] templateChars = SIMPLE_TWO_CHARACTER_KEY_TEMPLATE.toCharArray();
        assertEquals(SIMPLE_TWO_CHARACTER_KEY_RESULT, substitutor.replace(templateChars));

        StringBuffer stringBuffer = new StringBuffer(SIMPLE_TWO_CHARACTER_KEY_TEMPLATE);
        assertEquals(SIMPLE_TWO_CHARACTER_KEY_RESULT, substitutor.replace(stringBuffer));

        StringBuilder stringBuilder = new StringBuilder(SIMPLE_TWO_CHARACTER_KEY_TEMPLATE);
        assertEquals(SIMPLE_TWO_CHARACTER_KEY_RESULT, substitutor.replace(stringBuilder));

        TextStringBuilder textStringBuilder = new TextStringBuilder(SIMPLE_TWO_CHARACTER_KEY_TEMPLATE);
        assertEquals(SIMPLE_TWO_CHARACTER_KEY_RESULT, substitutor.replace(textStringBuilder));

        final MutableObject<String> templateObject = new MutableObject<>(SIMPLE_TWO_CHARACTER_KEY_TEMPLATE);
        assertEquals(SIMPLE_TWO_CHARACTER_KEY_RESULT, substitutor.replace(templateObject));

        stringBuffer = new StringBuffer(SIMPLE_TWO_CHARACTER_KEY_TEMPLATE);
        assertTrue(substitutor.replaceIn(stringBuffer), SIMPLE_TWO_CHARACTER_KEY_TEMPLATE);
        assertEquals(SIMPLE_TWO_CHARACTER_KEY_RESULT, stringBuffer.toString());

        stringBuilder = new StringBuilder(SIMPLE_TWO_CHARACTER_KEY_TEMPLATE);
        assertTrue(substitutor.replaceIn(stringBuilder));
        assertEquals(SIMPLE_TWO_CHARACTER_KEY_RESULT, stringBuilder.toString());

        textStringBuilder = new TextStringBuilder(SIMPLE_TWO_CHARACTER_KEY_TEMPLATE);
        assertTrue(substitutor.replaceIn(textStringBuilder));
        assertEquals(SIMPLE_TWO_CHARACTER_KEY_RESULT, textStringBuilder.toString());
    }

    private Map<String, String> createValues() {
        final Map<String, String> values = new HashMap<>();
        values.put("a", "1");
        values.put("aa", SIMPLE_TWO_CHARACTER_KEY_RESULT);
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
        return values;
    }
}
