package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceSimple {

    private static final String ANIMAL_VALUE = "quick brown fox";
    private static final String TARGET_VALUE = "lazy dog";
    private static final String TEMPLATE = "The ${animal} jumps over the ${target}.";
    private static final String EXPECTED_RESULT = "The quick brown fox jumps over the lazy dog.";
    private static final int SUBSTRING_START = 1;

    private Map<String, String> values;

    @BeforeEach
    public void setUp() throws Exception {
        values = new HashMap<>();
        values.put("animal", ANIMAL_VALUE);
        values.put("target", TARGET_VALUE);
    }

    @AfterEach
    public void tearDown() throws Exception {
        values = null;
    }

    /**
     * Tests simple key replace.
     */
    @Test
    void testReplaceSimple() {
        assertReplacementAcrossSupportedInputs(EXPECTED_RESULT, TEMPLATE, true);
    }

    private void assertReplacementAcrossSupportedInputs(
            final String expectedResult,
            final String replaceTemplate,
            final boolean includeSubstringOverloads) {
        final StrSubstitutor substitutor = new StrSubstitutor(values);
        assertReplacementAcrossSupportedInputs(substitutor, expectedResult, replaceTemplate, includeSubstringOverloads);
    }

    private void assertReplacementAcrossSupportedInputs(
            final StrSubstitutor substitutor,
            final String expectedResult,
            final String replaceTemplate,
            final boolean includeSubstringOverloads) {
        final int substringLength = replaceTemplate.length() - 2;
        final String expectedSubstringResult = expectedResult.substring(
                SUBSTRING_START,
                expectedResult.length() - 1);

        assertStringReplacement(substitutor, expectedResult, expectedSubstringResult, replaceTemplate, substringLength,
                includeSubstringOverloads);
        assertCharArrayReplacement(substitutor, expectedResult, expectedSubstringResult, replaceTemplate,
                includeSubstringOverloads);
        assertStringBufferReplacement(substitutor, expectedResult, expectedSubstringResult, replaceTemplate,
                includeSubstringOverloads);
        assertStringBuilderReplacement(substitutor, expectedResult, expectedSubstringResult, replaceTemplate,
                includeSubstringOverloads);
        assertStrBuilderReplacement(substitutor, expectedResult, expectedSubstringResult, replaceTemplate,
                includeSubstringOverloads);
        assertObjectReplacement(substitutor, expectedResult, replaceTemplate);
    }

    private void assertStringReplacement(
            final StrSubstitutor substitutor,
            final String expectedResult,
            final String expectedSubstringResult,
            final String replaceTemplate,
            final int substringLength,
            final boolean includeSubstringOverloads) {
        assertEquals(expectedResult, substitutor.replace(replaceTemplate));
        if (includeSubstringOverloads) {
            assertEquals(expectedSubstringResult, substitutor.replace(replaceTemplate, SUBSTRING_START, substringLength));
        }
    }

    private void assertCharArrayReplacement(
            final StrSubstitutor substitutor,
            final String expectedResult,
            final String expectedSubstringResult,
            final String replaceTemplate,
            final boolean includeSubstringOverloads) {
        final char[] chars = replaceTemplate.toCharArray();

        assertEquals(expectedResult, substitutor.replace(chars));
        if (includeSubstringOverloads) {
            assertEquals(expectedSubstringResult, substitutor.replace(chars, SUBSTRING_START, chars.length - 2));
        }
    }

    private void assertStringBufferReplacement(
            final StrSubstitutor substitutor,
            final String expectedResult,
            final String expectedSubstringResult,
            final String replaceTemplate,
            final boolean includeSubstringOverloads) {
        StringBuffer buffer = new StringBuffer(replaceTemplate);

        assertEquals(expectedResult, substitutor.replace(buffer));
        if (includeSubstringOverloads) {
            assertEquals(expectedSubstringResult, substitutor.replace(buffer, SUBSTRING_START, buffer.length() - 2));
        }

        buffer = new StringBuffer(replaceTemplate);
        assertTrue(substitutor.replaceIn(buffer));
        assertEquals(expectedResult, buffer.toString());
        if (includeSubstringOverloads) {
            buffer = new StringBuffer(replaceTemplate);
            assertTrue(substitutor.replaceIn(buffer, SUBSTRING_START, buffer.length() - 2));
            assertEquals(expectedResult, buffer.toString());
        }
    }

    private void assertStringBuilderReplacement(
            final StrSubstitutor substitutor,
            final String expectedResult,
            final String expectedSubstringResult,
            final String replaceTemplate,
            final boolean includeSubstringOverloads) {
        StringBuilder builder = new StringBuilder(replaceTemplate);

        assertEquals(expectedResult, substitutor.replace(builder));
        if (includeSubstringOverloads) {
            assertEquals(expectedSubstringResult, substitutor.replace(builder, SUBSTRING_START, builder.length() - 2));
        }

        builder = new StringBuilder(replaceTemplate);
        assertTrue(substitutor.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (includeSubstringOverloads) {
            builder = new StringBuilder(replaceTemplate);
            assertTrue(substitutor.replaceIn(builder, SUBSTRING_START, builder.length() - 2));
            assertEquals(expectedResult, builder.toString());
        }
    }

    private void assertStrBuilderReplacement(
            final StrSubstitutor substitutor,
            final String expectedResult,
            final String expectedSubstringResult,
            final String replaceTemplate,
            final boolean includeSubstringOverloads) {
        StrBuilder builder = new StrBuilder(replaceTemplate);

        assertEquals(expectedResult, substitutor.replace(builder));
        if (includeSubstringOverloads) {
            assertEquals(expectedSubstringResult, substitutor.replace(builder, SUBSTRING_START, builder.length() - 2));
        }

        builder = new StrBuilder(replaceTemplate);
        assertTrue(substitutor.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (includeSubstringOverloads) {
            builder = new StrBuilder(replaceTemplate);
            assertTrue(substitutor.replaceIn(builder, SUBSTRING_START, builder.length() - 2));
            assertEquals(expectedResult, builder.toString());
        }
    }

    private void assertObjectReplacement(
            final StrSubstitutor substitutor,
            final String expectedResult,
            final String replaceTemplate) {
        final MutableObject<String> objectWhoseToStringReturnsTemplate = new MutableObject<>(replaceTemplate);

        assertEquals(expectedResult, substitutor.replace(objectWhoseToStringReturnsTemplate));
    }
}
