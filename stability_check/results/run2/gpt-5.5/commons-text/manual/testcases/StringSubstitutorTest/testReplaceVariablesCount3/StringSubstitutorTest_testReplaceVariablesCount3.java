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

public class StringSubstitutorTest_testReplaceVariablesCount3 {

    private static final String ACTUAL_ANIMAL = "quick brown fox";

    private static final String ACTUAL_TARGET = "lazy dog";

    protected Map<String, String> values;

    protected void doReplace(final String expectedResult, final String replaceTemplate, final boolean substring) throws IOException {
        doTestReplace(new StringSubstitutor(values), expectedResult, replaceTemplate, substring);
    }

    protected void doTestReplace(final StringSubstitutor substitutor, final String expectedResult, final String replaceTemplate,
        final boolean substring) throws IOException {
        final String expectedShortResult = substring ? expectedResult.substring(1, expectedResult.length() - 1) : expectedResult;

        assertReplaceMethodsReturnExpectedText(substitutor, expectedResult, expectedShortResult, replaceTemplate, substring);
        assertReplaceInMethodsModifyText(substitutor, expectedResult, replaceTemplate, substring);
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
    void testReplaceVariablesCount3() throws IOException {
        final String[][] repeatedVariableCases = {
            {"121", "${a}${b}${a}"},
            {"112211", "${aa}${bb}${aa}"},
            {ACTUAL_ANIMAL + ACTUAL_ANIMAL + ACTUAL_ANIMAL, "${animal}${animal}${animal}"},
            {ACTUAL_TARGET + ACTUAL_TARGET + ACTUAL_TARGET, "${target}${target}${target}"}
        };

        for (final String[] repeatedVariableCase : repeatedVariableCases) {
            doReplace(repeatedVariableCase[0], repeatedVariableCase[1], false);
        }
    }

    private void assertReplaceMethodsReturnExpectedText(final StringSubstitutor substitutor, final String expectedResult,
        final String expectedShortResult, final String replaceTemplate, final boolean substring) throws IOException {
        final String actual = replace(substitutor, replaceTemplate);
        assertEquals(expectedResult, actual, () -> String.format("Index of difference: %,d", StringUtils.indexOfDifference(expectedResult, actual)));

        if (substring) {
            assertEquals(expectedShortResult, substitutor.replace(replaceTemplate, 1, replaceTemplate.length() - 2));
        }

        final char[] chars = replaceTemplate.toCharArray();
        assertEquals(expectedResult, substitutor.replace(chars));
        if (substring) {
            assertEquals(expectedShortResult, substitutor.replace(chars, 1, chars.length - 2));
        }

        final StringBuffer buffer = new StringBuffer(replaceTemplate);
        assertEquals(expectedResult, substitutor.replace(buffer));
        if (substring) {
            assertEquals(expectedShortResult, substitutor.replace(buffer, 1, buffer.length() - 2));
        }

        final StringBuilder builder = new StringBuilder(replaceTemplate);
        assertEquals(expectedResult, substitutor.replace(builder));
        if (substring) {
            assertEquals(expectedShortResult, substitutor.replace(builder, 1, builder.length() - 2));
        }

        final TextStringBuilder textBuilder = new TextStringBuilder(replaceTemplate);
        assertEquals(expectedResult, substitutor.replace(textBuilder));
        if (substring) {
            assertEquals(expectedShortResult, substitutor.replace(textBuilder, 1, textBuilder.length() - 2));
        }

        final MutableObject<String> objectWhoseToStringIsTemplate = new MutableObject<>(replaceTemplate);
        assertEquals(expectedResult, substitutor.replace(objectWhoseToStringIsTemplate));
    }

    private void assertReplaceInMethodsModifyText(final StringSubstitutor substitutor, final String expectedResult,
        final String replaceTemplate, final boolean substring) {
        StringBuffer buffer = new StringBuffer(replaceTemplate);
        assertTrue(substitutor.replaceIn(buffer), replaceTemplate);
        assertEquals(expectedResult, buffer.toString());

        if (substring) {
            buffer = new StringBuffer(replaceTemplate);
            assertTrue(substitutor.replaceIn(buffer, 1, buffer.length() - 2));
            assertEquals(expectedResult, buffer.toString());
        }

        StringBuilder builder = new StringBuilder(replaceTemplate);
        assertTrue(substitutor.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());

        if (substring) {
            builder = new StringBuilder(replaceTemplate);
            assertTrue(substitutor.replaceIn(builder, 1, builder.length() - 2));
            assertEquals(expectedResult, builder.toString());
        }

        TextStringBuilder textBuilder = new TextStringBuilder(replaceTemplate);
        assertTrue(substitutor.replaceIn(textBuilder));
        assertEquals(expectedResult, textBuilder.toString());

        if (substring) {
            textBuilder = new TextStringBuilder(replaceTemplate);
            assertTrue(substitutor.replaceIn(textBuilder, 1, textBuilder.length() - 2));
            assertEquals(expectedResult, textBuilder.toString());
        }
    }
}
