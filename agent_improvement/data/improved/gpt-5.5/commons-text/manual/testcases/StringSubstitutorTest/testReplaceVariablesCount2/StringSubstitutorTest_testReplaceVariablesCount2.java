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
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class StringSubstitutorTest_testReplaceVariablesCount2 {

    private static final String ANIMAL_VALUE = "quick brown fox";

    private static final String TARGET_VALUE = "lazy dog";

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
        values.put("animal", ANIMAL_VALUE);
        values.put("target", TARGET_VALUE);
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    /**
     * Verifies that adjacent variables are each resolved without consuming
     * neighboring variable markers.
     */
    @Test
    void testReplaceVariablesCount2() throws IOException {
        assertReplacementAcrossSupportedInputs("1122", "${aa}${bb}", false);
        assertReplacementAcrossSupportedInputs(ANIMAL_VALUE + ANIMAL_VALUE, "${animal}${animal}", false);
        assertReplacementAcrossSupportedInputs(TARGET_VALUE + TARGET_VALUE, "${target}${target}", false);
        assertReplacementAcrossSupportedInputs(ANIMAL_VALUE + TARGET_VALUE, "${animal}${target}", false);
    }

    private void assertReplacementAcrossSupportedInputs(final String expectedResult, final String template, final boolean substring)
            throws IOException {
        assertReplacementAcrossSupportedInputs(new StringSubstitutor(values), expectedResult, template, substring);
    }

    private void assertReplacementAcrossSupportedInputs(final StringSubstitutor substitutor, final String expectedResult, final String template,
            final boolean substring) throws IOException {
        final String expectedShortResult = substring ? expectedResult.substring(1, expectedResult.length() - 1) : expectedResult;

        final String actual = replace(substitutor, template);
        assertEquals(expectedResult, actual, () -> String.format("Index of difference: %,d", StringUtils.indexOfDifference(expectedResult, actual)));
        if (substring) {
            assertEquals(expectedShortResult, substitutor.replace(template, 1, template.length() - 2));
        }

        final char[] chars = template.toCharArray();
        assertEquals(expectedResult, substitutor.replace(chars));
        if (substring) {
            assertEquals(expectedShortResult, substitutor.replace(chars, 1, chars.length - 2));
        }

        StringBuffer stringBuffer = new StringBuffer(template);
        assertEquals(expectedResult, substitutor.replace(stringBuffer));
        if (substring) {
            assertEquals(expectedShortResult, substitutor.replace(stringBuffer, 1, stringBuffer.length() - 2));
        }

        StringBuilder stringBuilder = new StringBuilder(template);
        assertEquals(expectedResult, substitutor.replace(stringBuilder));
        if (substring) {
            assertEquals(expectedShortResult, substitutor.replace(stringBuilder, 1, stringBuilder.length() - 2));
        }

        TextStringBuilder textStringBuilder = new TextStringBuilder(template);
        assertEquals(expectedResult, substitutor.replace(textStringBuilder));
        if (substring) {
            assertEquals(expectedShortResult, substitutor.replace(textStringBuilder, 1, textStringBuilder.length() - 2));
        }

        final MutableObject<String> objectWhoseToStringIsTheTemplate = new MutableObject<>(template);
        assertEquals(expectedResult, substitutor.replace(objectWhoseToStringIsTheTemplate));

        stringBuffer = new StringBuffer(template);
        assertTrue(substitutor.replaceIn(stringBuffer), template);
        assertEquals(expectedResult, stringBuffer.toString());
        if (substring) {
            stringBuffer = new StringBuffer(template);
            assertTrue(substitutor.replaceIn(stringBuffer, 1, stringBuffer.length() - 2));
            assertEquals(expectedResult, stringBuffer.toString());
        }

        stringBuilder = new StringBuilder(template);
        assertTrue(substitutor.replaceIn(stringBuilder));
        assertEquals(expectedResult, stringBuilder.toString());
        if (substring) {
            stringBuilder = new StringBuilder(template);
            assertTrue(substitutor.replaceIn(stringBuilder, 1, stringBuilder.length() - 2));
            assertEquals(expectedResult, stringBuilder.toString());
        }

        textStringBuilder = new TextStringBuilder(template);
        assertTrue(substitutor.replaceIn(textStringBuilder));
        assertEquals(expectedResult, textStringBuilder.toString());
        if (substring) {
            textStringBuilder = new TextStringBuilder(template);
            assertTrue(substitutor.replaceIn(textStringBuilder, 1, textStringBuilder.length() - 2));
            assertEquals(expectedResult, textStringBuilder.toString());
        }
    }

    /**
     * For subclasses to override.
     *
     * @throws IOException Thrown by subclasses.
     */
    protected String replace(final StringSubstitutor stringSubstitutor, final String template) throws IOException {
        return stringSubstitutor.replace(template);
    }
}
