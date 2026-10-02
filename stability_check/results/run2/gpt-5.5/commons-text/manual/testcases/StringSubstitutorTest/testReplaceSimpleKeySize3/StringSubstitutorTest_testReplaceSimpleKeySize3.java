package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplaceSimpleKeySize3 {

    private static final String THREE_CHARACTER_KEY_TEMPLATE = "${aaa}";
    private static final String THREE_CHARACTER_KEY_VALUE = "111";

    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();

        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", THREE_CHARACTER_KEY_VALUE);
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    @Test
    void testReplaceSimpleKeySize3() throws IOException {
        final StringSubstitutor substitutor = new StringSubstitutor(values);

        assertReplacementResultForEveryInputType(substitutor, THREE_CHARACTER_KEY_TEMPLATE, THREE_CHARACTER_KEY_VALUE);
    }

    private void assertReplacementResultForEveryInputType(final StringSubstitutor substitutor, final String template,
            final String expected) throws IOException {
        assertEquals(expected, replace(substitutor, template));

        final char[] chars = template.toCharArray();
        assertEquals(expected, substitutor.replace(chars));

        StringBuffer buffer = new StringBuffer(template);
        assertEquals(expected, substitutor.replace(buffer));

        StringBuilder builder = new StringBuilder(template);
        assertEquals(expected, substitutor.replace(builder));

        TextStringBuilder textBuilder = new TextStringBuilder(template);
        assertEquals(expected, substitutor.replace(textBuilder));

        final MutableObject<String> objectWhoseToStringReturnsTemplate = new MutableObject<>(template);
        assertEquals(expected, substitutor.replace(objectWhoseToStringReturnsTemplate));

        buffer = new StringBuffer(template);
        assertTrue(substitutor.replaceIn(buffer), template);
        assertEquals(expected, buffer.toString());

        builder = new StringBuilder(template);
        assertTrue(substitutor.replaceIn(builder));
        assertEquals(expected, builder.toString());

        textBuilder = new TextStringBuilder(template);
        assertTrue(substitutor.replaceIn(textBuilder));
        assertEquals(expected, textBuilder.toString());
    }

    private String replace(final StringSubstitutor substitutor, final String template) throws IOException {
        return substitutor.replace(template);
    }
}
