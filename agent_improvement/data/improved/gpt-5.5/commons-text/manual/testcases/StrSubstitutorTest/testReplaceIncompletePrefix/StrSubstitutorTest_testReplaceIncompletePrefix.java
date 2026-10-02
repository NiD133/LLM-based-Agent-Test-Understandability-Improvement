package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceIncompletePrefix {

    private static final String TEMPLATE = "The {animal} jumps over the ${target}.";
    private static final String EXPECTED_RESULT = "The {animal} jumps over the lazy dog.";

    private Map<String, String> values;

    private void assertReplacementAcrossSupportedInputs(final String expectedResult, final String replaceTemplate,
            final boolean includeSubstringChecks) {
        final StrSubstitutor substitutor = new StrSubstitutor(values);
        assertReplacementAcrossSupportedInputs(substitutor, expectedResult, replaceTemplate, includeSubstringChecks);
    }

    private void assertReplacementAcrossSupportedInputs(final StrSubstitutor substitutor, final String expectedResult,
            final String replaceTemplate, final boolean includeSubstringChecks) {
        final String expectedSubstringResult = expectedResult.substring(1, expectedResult.length() - 1);

        assertEquals(expectedResult, substitutor.replace(replaceTemplate));
        if (includeSubstringChecks) {
            assertEquals(expectedSubstringResult, substitutor.replace(replaceTemplate, 1, replaceTemplate.length() - 2));
        }

        final char[] templateChars = replaceTemplate.toCharArray();
        assertEquals(expectedResult, substitutor.replace(templateChars));
        if (includeSubstringChecks) {
            assertEquals(expectedSubstringResult, substitutor.replace(templateChars, 1, templateChars.length - 2));
        }

        StringBuffer templateBuffer = new StringBuffer(replaceTemplate);
        assertEquals(expectedResult, substitutor.replace(templateBuffer));
        if (includeSubstringChecks) {
            assertEquals(expectedSubstringResult, substitutor.replace(templateBuffer, 1, templateBuffer.length() - 2));
        }

        StringBuilder templateBuilder = new StringBuilder(replaceTemplate);
        assertEquals(expectedResult, substitutor.replace(templateBuilder));
        if (includeSubstringChecks) {
            assertEquals(expectedSubstringResult, substitutor.replace(templateBuilder, 1, templateBuilder.length() - 2));
        }

        StrBuilder templateStrBuilder = new StrBuilder(replaceTemplate);
        assertEquals(expectedResult, substitutor.replace(templateStrBuilder));
        if (includeSubstringChecks) {
            assertEquals(
                    expectedSubstringResult,
                    substitutor.replace(templateStrBuilder, 1, templateStrBuilder.length() - 2));
        }

        final MutableObject<String> templateObject = new MutableObject<>(replaceTemplate);
        assertEquals(expectedResult, substitutor.replace(templateObject));

        templateBuffer = new StringBuffer(replaceTemplate);
        assertTrue(substitutor.replaceIn(templateBuffer));
        assertEquals(expectedResult, templateBuffer.toString());
        if (includeSubstringChecks) {
            templateBuffer = new StringBuffer(replaceTemplate);
            assertTrue(substitutor.replaceIn(templateBuffer, 1, templateBuffer.length() - 2));
            assertEquals(expectedResult, templateBuffer.toString());
        }

        templateBuilder = new StringBuilder(replaceTemplate);
        assertTrue(substitutor.replaceIn(templateBuilder));
        assertEquals(expectedResult, templateBuilder.toString());
        if (includeSubstringChecks) {
            templateBuilder = new StringBuilder(replaceTemplate);
            assertTrue(substitutor.replaceIn(templateBuilder, 1, templateBuilder.length() - 2));
            assertEquals(expectedResult, templateBuilder.toString());
        }

        templateStrBuilder = new StrBuilder(replaceTemplate);
        assertTrue(substitutor.replaceIn(templateStrBuilder));
        assertEquals(expectedResult, templateStrBuilder.toString());
        if (includeSubstringChecks) {
            templateStrBuilder = new StrBuilder(replaceTemplate);
            assertTrue(substitutor.replaceIn(templateStrBuilder, 1, templateStrBuilder.length() - 2));
            assertEquals(expectedResult, templateStrBuilder.toString());
        }
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
    void testReplaceIncompletePrefix() {
        assertReplacementAcrossSupportedInputs(EXPECTED_RESULT, TEMPLATE, true);
    }
}
