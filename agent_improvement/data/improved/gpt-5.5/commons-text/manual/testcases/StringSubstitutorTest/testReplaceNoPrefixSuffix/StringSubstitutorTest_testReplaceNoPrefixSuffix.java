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

public class StringSubstitutorTest_testReplaceNoPrefixSuffix {

    private static final String ACTUAL_ANIMAL = "quick brown fox";
    private static final String ACTUAL_TARGET = "lazy dog";
    private static final String TEMPLATE_WITH_SUFFIX_ONLY = "The animal} jumps over the ${target}.";
    private static final String EXPECTED_RESULT = "The animal} jumps over the lazy dog.";

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

    /**
     * For subclasses to override.
     *
     * @throws IOException Thrown by subclasses.
     */
    protected String replace(final StringSubstitutor stringSubstitutor, final String template) throws IOException {
        return stringSubstitutor.replace(template);
    }

    @Test
    void testReplaceNoPrefixSuffix() throws IOException {
        assertReplacementAcrossSupportedInputTypes(EXPECTED_RESULT, TEMPLATE_WITH_SUFFIX_ONLY, true);
    }

    private void assertReplacementAcrossSupportedInputTypes(final String expectedResult, final String replaceTemplate,
        final boolean substring) throws IOException {
        final StringSubstitutor substitutor = new StringSubstitutor(values);
        final String expectedShortResult = substring ? expectedResult.substring(1, expectedResult.length() - 1) : expectedResult;

        final String actual = replace(substitutor, replaceTemplate);
        assertEquals(expectedResult, actual,
            () -> String.format("Index of difference: %,d", StringUtils.indexOfDifference(expectedResult, actual)));

        assertReplacementFromImmutableInputs(substitutor, expectedResult, expectedShortResult, replaceTemplate, substring);
        assertReplacementFromMutableInputs(substitutor, expectedResult, expectedShortResult, replaceTemplate, substring);
        assertReplacementInMutableInputs(substitutor, expectedResult, replaceTemplate, substring);
    }

    private void assertReplacementFromImmutableInputs(final StringSubstitutor substitutor, final String expectedResult,
        final String expectedShortResult, final String replaceTemplate, final boolean substring) {
        if (substring) {
            assertEquals(expectedShortResult, substitutor.replace(replaceTemplate, 1, replaceTemplate.length() - 2));
        }

        final char[] chars = replaceTemplate.toCharArray();
        assertEquals(expectedResult, substitutor.replace(chars));
        if (substring) {
            assertEquals(expectedShortResult, substitutor.replace(chars, 1, chars.length - 2));
        }

        final MutableObject<String> obj = new MutableObject<>(replaceTemplate);
        assertEquals(expectedResult, substitutor.replace(obj));
    }

    private void assertReplacementFromMutableInputs(final StringSubstitutor substitutor, final String expectedResult,
        final String expectedShortResult, final String replaceTemplate, final boolean substring) {
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
    }

    private void assertReplacementInMutableInputs(final StringSubstitutor substitutor, final String expectedResult,
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
