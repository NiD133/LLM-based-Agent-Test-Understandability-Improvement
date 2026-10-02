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

public class StringSubstitutorTest_testReplaceIncompletePrefix {

    private static final String ACTUAL_ANIMAL = "quick brown fox";
    private static final String ACTUAL_TARGET = "lazy dog";
    private static final String EXPECTED_RESULT = "The {animal} jumps over the lazy dog.";
    private static final String TEMPLATE_WITH_INCOMPLETE_PREFIX =
            "The {animal} jumps over the ${target}.";

    protected Map<String, String> values;

    protected void doReplace(final String expectedResult, final String replaceTemplate,
            final boolean testSubstringOverloads) throws IOException {
        doTestReplace(new StringSubstitutor(values), expectedResult, replaceTemplate,
                testSubstringOverloads);
    }

    protected void doTestReplace(final StringSubstitutor substitutor, final String expectedResult,
            final String replaceTemplate, final boolean testSubstringOverloads) throws IOException {
        final String expectedShortResult = testSubstringOverloads
                ? expectedResult.substring(1, expectedResult.length() - 1)
                : expectedResult;

        final String actual = replace(substitutor, replaceTemplate);
        assertEquals(expectedResult, actual,
                () -> String.format("Index of difference: %,d",
                        StringUtils.indexOfDifference(expectedResult, actual)));
        if (testSubstringOverloads) {
            assertEquals(expectedShortResult,
                    substitutor.replace(replaceTemplate, 1, replaceTemplate.length() - 2));
        }

        final char[] chars = replaceTemplate.toCharArray();
        assertEquals(expectedResult, substitutor.replace(chars));
        if (testSubstringOverloads) {
            assertEquals(expectedShortResult, substitutor.replace(chars, 1, chars.length - 2));
        }

        StringBuffer buffer = new StringBuffer(replaceTemplate);
        assertEquals(expectedResult, substitutor.replace(buffer));
        if (testSubstringOverloads) {
            assertEquals(expectedShortResult, substitutor.replace(buffer, 1, buffer.length() - 2));
        }

        StringBuilder builder = new StringBuilder(replaceTemplate);
        assertEquals(expectedResult, substitutor.replace(builder));
        if (testSubstringOverloads) {
            assertEquals(expectedShortResult,
                    substitutor.replace(builder, 1, builder.length() - 2));
        }

        TextStringBuilder textBuilder = new TextStringBuilder(replaceTemplate);
        assertEquals(expectedResult, substitutor.replace(textBuilder));
        if (testSubstringOverloads) {
            assertEquals(expectedShortResult,
                    substitutor.replace(textBuilder, 1, textBuilder.length() - 2));
        }

        final MutableObject<String> objectWhoseToStringReturnsTemplate =
                new MutableObject<>(replaceTemplate);
        assertEquals(expectedResult, substitutor.replace(objectWhoseToStringReturnsTemplate));

        buffer = new StringBuffer(replaceTemplate);
        assertTrue(substitutor.replaceIn(buffer), replaceTemplate);
        assertEquals(expectedResult, buffer.toString());
        if (testSubstringOverloads) {
            buffer = new StringBuffer(replaceTemplate);
            assertTrue(substitutor.replaceIn(buffer, 1, buffer.length() - 2));
            assertEquals(expectedResult, buffer.toString());
        }

        builder = new StringBuilder(replaceTemplate);
        assertTrue(substitutor.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (testSubstringOverloads) {
            builder = new StringBuilder(replaceTemplate);
            assertTrue(substitutor.replaceIn(builder, 1, builder.length() - 2));
            assertEquals(expectedResult, builder.toString());
        }

        textBuilder = new TextStringBuilder(replaceTemplate);
        assertTrue(substitutor.replaceIn(textBuilder));
        assertEquals(expectedResult, textBuilder.toString());
        if (testSubstringOverloads) {
            textBuilder = new TextStringBuilder(replaceTemplate);
            assertTrue(substitutor.replaceIn(textBuilder, 1, textBuilder.length() - 2));
            assertEquals(expectedResult, textBuilder.toString());
        }
    }

    protected String replace(final StringSubstitutor stringSubstitutor, final String template)
            throws IOException {
        return stringSubstitutor.replace(template);
    }

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
    void testReplaceIncompletePrefix() throws IOException {
        doReplace(EXPECTED_RESULT, TEMPLATE_WITH_INCOMPLETE_PREFIX, true);
    }
}
