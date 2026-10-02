package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceSoloEscaping {

    private static final String ESCAPED_ANIMAL_VARIABLE = "$${animal}";
    private static final String LITERAL_ANIMAL_VARIABLE = "${animal}";

    private Map<String, String> values;

    private void assertEscapedVariableIsPreserved(final String expectedResult, final String replaceTemplate) {
        final StrSubstitutor substitutor = new StrSubstitutor(values);

        assertEquals(expectedResult, substitutor.replace(replaceTemplate));

        final char[] templateChars = replaceTemplate.toCharArray();
        assertEquals(expectedResult, substitutor.replace(templateChars));

        StringBuffer stringBuffer = new StringBuffer(replaceTemplate);
        assertEquals(expectedResult, substitutor.replace(stringBuffer));

        StringBuilder stringBuilder = new StringBuilder(replaceTemplate);
        assertEquals(expectedResult, substitutor.replace(stringBuilder));

        StrBuilder strBuilder = new StrBuilder(replaceTemplate);
        assertEquals(expectedResult, substitutor.replace(strBuilder));

        final MutableObject<String> templateObject = new MutableObject<>(replaceTemplate);
        assertEquals(expectedResult, substitutor.replace(templateObject));

        stringBuffer = new StringBuffer(replaceTemplate);
        assertTrue(substitutor.replaceIn(stringBuffer));
        assertEquals(expectedResult, stringBuffer.toString());

        stringBuilder = new StringBuilder(replaceTemplate);
        assertTrue(substitutor.replaceIn(stringBuilder));
        assertEquals(expectedResult, stringBuilder.toString());

        strBuilder = new StrBuilder(replaceTemplate);
        assertTrue(substitutor.replaceIn(strBuilder));
        assertEquals(expectedResult, strBuilder.toString());
    }

    @BeforeEach
    public void setUp() throws Exception {
        values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    @AfterEach
    public void tearDown() throws Exception {
        values = null;
    }

    @Test
    void testReplaceSoloEscaping() {
        assertEscapedVariableIsPreserved(LITERAL_ANIMAL_VARIABLE, ESCAPED_ANIMAL_VARIABLE);
    }
}
