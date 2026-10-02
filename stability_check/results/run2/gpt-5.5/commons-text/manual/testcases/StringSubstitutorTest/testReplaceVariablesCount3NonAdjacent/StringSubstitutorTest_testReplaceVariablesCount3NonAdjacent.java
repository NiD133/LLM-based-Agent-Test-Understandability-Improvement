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

public class StringSubstitutorTest_testReplaceVariablesCount3NonAdjacent {

    private static final String ACTUAL_ANIMAL = "quick brown fox";

    private static final String ACTUAL_TARGET = "lazy dog";

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
        values.put("animal", ACTUAL_ANIMAL);
        values.put("target", ACTUAL_TARGET);
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    @Test
    void testReplaceVariablesCount3NonAdjacent() throws IOException {
        doReplace("1 2 1", "${a} ${b} ${a}", false);
        doReplace("11 22 11", "${aa} ${bb} ${aa}", false);
        doReplace(ACTUAL_ANIMAL + " " + ACTUAL_ANIMAL + " " + ACTUAL_ANIMAL, "${animal} ${animal} ${animal}", false);
        doReplace(ACTUAL_TARGET + " " + ACTUAL_TARGET + " " + ACTUAL_TARGET, "${target} ${target} ${target}", false);
    }

    private void doReplace(final String expectedResult, final String replaceTemplate, final boolean substring) throws IOException {
        assertReplacementAcrossSupportedInputTypes(new StringSubstitutor(values), expectedResult, replaceTemplate, substring);
    }

    private void assertReplacementAcrossSupportedInputTypes(final StringSubstitutor substitutor, final String expectedResult,
            final String replaceTemplate, final boolean substring) throws IOException {
        final String expectedShortResult = substring ? expectedResult.substring(1, expectedResult.length() - 1) : expectedResult;

        assertStringReplacement(substitutor, expectedResult, expectedShortResult, replaceTemplate, substring);
        assertCharArrayReplacement(substitutor, expectedResult, expectedShortResult, replaceTemplate, substring);
        assertStringBufferReplacement(substitutor, expectedResult, expectedShortResult, replaceTemplate, substring);
        assertStringBuilderReplacement(substitutor, expectedResult, expectedShortResult, replaceTemplate, substring);
        assertTextStringBuilderReplacement(substitutor, expectedResult, expectedShortResult, replaceTemplate, substring);
        assertObjectReplacement(substitutor, expectedResult, replaceTemplate);
        assertStringBufferInPlaceReplacement(substitutor, expectedResult, replaceTemplate, substring);
        assertStringBuilderInPlaceReplacement(substitutor, expectedResult, replaceTemplate, substring);
        assertTextStringBuilderInPlaceReplacement(substitutor, expectedResult, replaceTemplate, substring);
    }

    private void assertStringReplacement(final StringSubstitutor substitutor, final String expectedResult,
            final String expectedShortResult, final String replaceTemplate, final boolean substring) throws IOException {
        final String actual = replace(substitutor, replaceTemplate);
        assertEquals(expectedResult, actual, () -> String.format("Index of difference: %,d",
                StringUtils.indexOfDifference(expectedResult, actual)));
        if (substring) {
            assertEquals(expectedShortResult, substitutor.replace(replaceTemplate, 1, replaceTemplate.length() - 2));
        }
    }

    private void assertCharArrayReplacement(final StringSubstitutor substitutor, final String expectedResult,
            final String expectedShortResult, final String replaceTemplate, final boolean substring) {
        final char[] chars = replaceTemplate.toCharArray();
        assertEquals(expectedResult, substitutor.replace(chars));
        if (substring) {
            assertEquals(expectedShortResult, substitutor.replace(chars, 1, chars.length - 2));
        }
    }

    private void assertStringBufferReplacement(final StringSubstitutor substitutor, final String expectedResult,
            final String expectedShortResult, final String replaceTemplate, final boolean substring) {
        final StringBuffer buffer = new StringBuffer(replaceTemplate);
        assertEquals(expectedResult, substitutor.replace(buffer));
        if (substring) {
            assertEquals(expectedShortResult, substitutor.replace(buffer, 1, buffer.length() - 2));
        }
    }

    private void assertStringBuilderReplacement(final StringSubstitutor substitutor, final String expectedResult,
            final String expectedShortResult, final String replaceTemplate, final boolean substring) {
        final StringBuilder builder = new StringBuilder(replaceTemplate);
        assertEquals(expectedResult, substitutor.replace(builder));
        if (substring) {
            assertEquals(expectedShortResult, substitutor.replace(builder, 1, builder.length() - 2));
        }
    }

    private void assertTextStringBuilderReplacement(final StringSubstitutor substitutor, final String expectedResult,
            final String expectedShortResult, final String replaceTemplate, final boolean substring) {
        final TextStringBuilder builder = new TextStringBuilder(replaceTemplate);
        assertEquals(expectedResult, substitutor.replace(builder));
        if (substring) {
            assertEquals(expectedShortResult, substitutor.replace(builder, 1, builder.length() - 2));
        }
    }

    private void assertObjectReplacement(final StringSubstitutor substitutor, final String expectedResult,
            final String replaceTemplate) {
        final MutableObject<String> objectWhoseToStringReturnsTemplate = new MutableObject<>(replaceTemplate);
        assertEquals(expectedResult, substitutor.replace(objectWhoseToStringReturnsTemplate));
    }

    private void assertStringBufferInPlaceReplacement(final StringSubstitutor substitutor, final String expectedResult,
            final String replaceTemplate, final boolean substring) {
        StringBuffer buffer = new StringBuffer(replaceTemplate);
        assertTrue(substitutor.replaceIn(buffer), replaceTemplate);
        assertEquals(expectedResult, buffer.toString());
        if (substring) {
            buffer = new StringBuffer(replaceTemplate);
            assertTrue(substitutor.replaceIn(buffer, 1, buffer.length() - 2));
            assertEquals(expectedResult, buffer.toString());
        }
    }

    private void assertStringBuilderInPlaceReplacement(final StringSubstitutor substitutor, final String expectedResult,
            final String replaceTemplate, final boolean substring) {
        StringBuilder builder = new StringBuilder(replaceTemplate);
        assertTrue(substitutor.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (substring) {
            builder = new StringBuilder(replaceTemplate);
            assertTrue(substitutor.replaceIn(builder, 1, builder.length() - 2));
            assertEquals(expectedResult, builder.toString());
        }
    }

    private void assertTextStringBuilderInPlaceReplacement(final StringSubstitutor substitutor, final String expectedResult,
            final String replaceTemplate, final boolean substring) {
        TextStringBuilder builder = new TextStringBuilder(replaceTemplate);
        assertTrue(substitutor.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (substring) {
            builder = new TextStringBuilder(replaceTemplate);
            assertTrue(substitutor.replaceIn(builder, 1, builder.length() - 2));
            assertEquals(expectedResult, builder.toString());
        }
    }

    private String replace(final StringSubstitutor stringSubstitutor, final String template) throws IOException {
        return stringSubstitutor.replace(template);
    }
}
