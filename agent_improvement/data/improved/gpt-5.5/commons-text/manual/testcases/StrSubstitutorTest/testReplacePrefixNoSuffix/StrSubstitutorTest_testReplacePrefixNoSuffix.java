package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplacePrefixNoSuffix {

    private static final String TEMPLATE_WITH_PREFIX_BUT_NO_SUFFIX =
            "The ${animal jumps over the ${target} ${target}.";
    private static final String EXPECTED_UNMATCHED_PREFIX_LEFT_UNCHANGED =
            "The ${animal jumps over the ${target} lazy dog.";

    private Map<String, String> values;

    private void assertReplacementAcrossInputTypes(final String expectedResult, final String template,
            final boolean assertSubstringOverloads) {
        final StrSubstitutor substitutor = new StrSubstitutor(values);
        assertReplacementAcrossInputTypes(substitutor, expectedResult, template, assertSubstringOverloads);
    }

    private void assertReplacementAcrossInputTypes(final StrSubstitutor substitutor, final String expectedResult,
            final String template, final boolean assertSubstringOverloads) {
        final String expectedSubstringResult = expectedResult.substring(1, expectedResult.length() - 1);

        assertEquals(expectedResult, substitutor.replace(template));
        if (assertSubstringOverloads) {
            assertEquals(expectedSubstringResult, substitutor.replace(template, 1, template.length() - 2));
        }

        final char[] templateChars = template.toCharArray();
        assertEquals(expectedResult, substitutor.replace(templateChars));
        if (assertSubstringOverloads) {
            assertEquals(expectedSubstringResult, substitutor.replace(templateChars, 1, templateChars.length - 2));
        }

        StringBuffer stringBuffer = new StringBuffer(template);
        assertEquals(expectedResult, substitutor.replace(stringBuffer));
        if (assertSubstringOverloads) {
            assertEquals(expectedSubstringResult, substitutor.replace(stringBuffer, 1, stringBuffer.length() - 2));
        }

        StringBuilder stringBuilder = new StringBuilder(template);
        assertEquals(expectedResult, substitutor.replace(stringBuilder));
        if (assertSubstringOverloads) {
            assertEquals(expectedSubstringResult, substitutor.replace(stringBuilder, 1, stringBuilder.length() - 2));
        }

        StrBuilder strBuilder = new StrBuilder(template);
        assertEquals(expectedResult, substitutor.replace(strBuilder));
        if (assertSubstringOverloads) {
            assertEquals(expectedSubstringResult, substitutor.replace(strBuilder, 1, strBuilder.length() - 2));
        }

        final MutableObject<String> templateObject = new MutableObject<>(template);
        assertEquals(expectedResult, substitutor.replace(templateObject));

        stringBuffer = new StringBuffer(template);
        assertTrue(substitutor.replaceIn(stringBuffer));
        assertEquals(expectedResult, stringBuffer.toString());
        if (assertSubstringOverloads) {
            stringBuffer = new StringBuffer(template);
            assertTrue(substitutor.replaceIn(stringBuffer, 1, stringBuffer.length() - 2));
            assertEquals(expectedResult, stringBuffer.toString());
        }

        stringBuilder = new StringBuilder(template);
        assertTrue(substitutor.replaceIn(stringBuilder));
        assertEquals(expectedResult, stringBuilder.toString());
        if (assertSubstringOverloads) {
            stringBuilder = new StringBuilder(template);
            assertTrue(substitutor.replaceIn(stringBuilder, 1, stringBuilder.length() - 2));
            assertEquals(expectedResult, stringBuilder.toString());
        }

        strBuilder = new StrBuilder(template);
        assertTrue(substitutor.replaceIn(strBuilder));
        assertEquals(expectedResult, strBuilder.toString());
        if (assertSubstringOverloads) {
            strBuilder = new StrBuilder(template);
            assertTrue(substitutor.replaceIn(strBuilder, 1, strBuilder.length() - 2));
            assertEquals(expectedResult, strBuilder.toString());
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
    void testReplacePrefixNoSuffix() {
        assertReplacementAcrossInputTypes(
                EXPECTED_UNMATCHED_PREFIX_LEFT_UNCHANGED,
                TEMPLATE_WITH_PREFIX_BUT_NO_SUFFIX,
                true);
    }
}
