package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplaceVariablesCount1Escaping5To4 {

    private static final String ACTUAL_ANIMAL = "quick brown fox";
    private static final String ACTUAL_TARGET = "lazy dog";

    private static final String FIVE_DOLLARS_BEFORE_SHORT_VARIABLE = "$$$$${a}";
    private static final String FOUR_DOLLARS_BEFORE_SHORT_VARIABLE = "$$$${a}";
    private static final String FIVE_DOLLARS_BEFORE_ANIMAL_VARIABLE = "$$$$${animal}";
    private static final String FOUR_DOLLARS_BEFORE_ANIMAL_VARIABLE = "$$$${animal}";

    protected Map<String, String> values;

    protected void doReplace(final String expectedResult, final String replaceTemplate, final boolean substring)
            throws IOException {
        doTestReplace(new StringSubstitutor(values), expectedResult, replaceTemplate, substring);
    }

    protected void doTestReplace(final StringSubstitutor sub, final String expectedResult,
            final String replaceTemplate, final boolean substring) throws IOException {
        final String expectedShortResult = substring ? expectedResult.substring(1, expectedResult.length() - 1)
                : expectedResult;

        final String actual = replace(sub, replaceTemplate);
        assertEquals(expectedResult, actual,
                () -> String.format("Index of difference: %,d", StringUtils.indexOfDifference(expectedResult, actual)));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(replaceTemplate, 1, replaceTemplate.length() - 2));
        }

        final char[] chars = replaceTemplate.toCharArray();
        assertEquals(expectedResult, sub.replace(chars));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(chars, 1, chars.length - 2));
        }

        StringBuffer stringBuffer = new StringBuffer(replaceTemplate);
        assertEquals(expectedResult, sub.replace(stringBuffer));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(stringBuffer, 1, stringBuffer.length() - 2));
        }

        StringBuilder stringBuilder = new StringBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(stringBuilder));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(stringBuilder, 1, stringBuilder.length() - 2));
        }

        TextStringBuilder textStringBuilder = new TextStringBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(textStringBuilder));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(textStringBuilder, 1, textStringBuilder.length() - 2));
        }

        final MutableObject<String> objectWhoseToStringReturnsTemplate = new MutableObject<>(replaceTemplate);
        assertEquals(expectedResult, sub.replace(objectWhoseToStringReturnsTemplate));

        stringBuffer = new StringBuffer(replaceTemplate);
        assertTrue(sub.replaceIn(stringBuffer), replaceTemplate);
        assertEquals(expectedResult, stringBuffer.toString());
        if (substring) {
            stringBuffer = new StringBuffer(replaceTemplate);
            assertTrue(sub.replaceIn(stringBuffer, 1, stringBuffer.length() - 2));
            assertEquals(expectedResult, stringBuffer.toString());
        }

        stringBuilder = new StringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(stringBuilder));
        assertEquals(expectedResult, stringBuilder.toString());
        if (substring) {
            stringBuilder = new StringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(stringBuilder, 1, stringBuilder.length() - 2));
            assertEquals(expectedResult, stringBuilder.toString());
        }

        textStringBuilder = new TextStringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(textStringBuilder));
        assertEquals(expectedResult, textStringBuilder.toString());
        if (substring) {
            textStringBuilder = new TextStringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(textStringBuilder, 1, textStringBuilder.length() - 2));
            assertEquals(expectedResult, textStringBuilder.toString());
        }
    }

    protected String replace(final StringSubstitutor stringSubstitutor, final String template) throws IOException {
        return stringSubstitutor.replace(template);
    }

    @BeforeEach
    public void setUp() throws Exception {
        values = new HashMap<>();
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        values.put("animal", ACTUAL_ANIMAL);
        values.put("target", ACTUAL_TARGET);
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    @Test
    void testReplaceVariablesCount1Escaping5To4() throws IOException {
        doReplace(FOUR_DOLLARS_BEFORE_SHORT_VARIABLE, FIVE_DOLLARS_BEFORE_SHORT_VARIABLE, false);
        doReplace(FOUR_DOLLARS_BEFORE_ANIMAL_VARIABLE, FIVE_DOLLARS_BEFORE_ANIMAL_VARIABLE, false);
    }
}
