package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceUnknownKey {

    private static final String UNKNOWN_PERSON_TEMPLATE = "The ${person} jumps over the ${target}.";
    private static final String UNKNOWN_PERSON_RESULT = "The ${person} jumps over the lazy dog.";
    private static final String UNKNOWN_PERSON_WITH_DEFAULT_TEMPLATE =
            "The ${person} jumps over the ${target}. ${undefined.number:-1234567890}.";
    private static final String UNKNOWN_PERSON_WITH_DEFAULT_RESULT =
            "The ${person} jumps over the lazy dog. 1234567890.";

    private Map<String, String> values;

    private void assertReplacementAcrossSupportedInputTypes(final String expectedResult,
                                                            final String template,
                                                            final boolean checkSubstringOverloads) {
        assertReplacementAcrossSupportedInputTypes(new StrSubstitutor(values), expectedResult, template, checkSubstringOverloads);
    }

    private void assertReplacementAcrossSupportedInputTypes(final StrSubstitutor substitutor,
                                                            final String expectedResult,
                                                            final String template,
                                                            final boolean checkSubstringOverloads) {
        final String expectedSubstringResult = expectedResult.substring(1, expectedResult.length() - 1);

        assertStringReplacement(substitutor, expectedResult, expectedSubstringResult, template, checkSubstringOverloads);
        assertCharArrayReplacement(substitutor, expectedResult, expectedSubstringResult, template, checkSubstringOverloads);
        assertStringBufferReplacement(substitutor, expectedResult, expectedSubstringResult, template, checkSubstringOverloads);
        assertStringBuilderReplacement(substitutor, expectedResult, expectedSubstringResult, template, checkSubstringOverloads);
        assertStrBuilderReplacement(substitutor, expectedResult, expectedSubstringResult, template, checkSubstringOverloads);
        assertObjectReplacement(substitutor, expectedResult, template);
        assertReplaceInStringBuffer(substitutor, expectedResult, template, checkSubstringOverloads);
        assertReplaceInStringBuilder(substitutor, expectedResult, template, checkSubstringOverloads);
        assertReplaceInStrBuilder(substitutor, expectedResult, template, checkSubstringOverloads);
    }

    private void assertStringReplacement(final StrSubstitutor substitutor,
                                         final String expectedResult,
                                         final String expectedSubstringResult,
                                         final String template,
                                         final boolean checkSubstringOverloads) {
        assertEquals(expectedResult, substitutor.replace(template));
        if (checkSubstringOverloads) {
            assertEquals(expectedSubstringResult, substitutor.replace(template, 1, template.length() - 2));
        }
    }

    private void assertCharArrayReplacement(final StrSubstitutor substitutor,
                                            final String expectedResult,
                                            final String expectedSubstringResult,
                                            final String template,
                                            final boolean checkSubstringOverloads) {
        final char[] chars = template.toCharArray();
        assertEquals(expectedResult, substitutor.replace(chars));
        if (checkSubstringOverloads) {
            assertEquals(expectedSubstringResult, substitutor.replace(chars, 1, chars.length - 2));
        }
    }

    private void assertStringBufferReplacement(final StrSubstitutor substitutor,
                                               final String expectedResult,
                                               final String expectedSubstringResult,
                                               final String template,
                                               final boolean checkSubstringOverloads) {
        final StringBuffer buffer = new StringBuffer(template);
        assertEquals(expectedResult, substitutor.replace(buffer));
        if (checkSubstringOverloads) {
            assertEquals(expectedSubstringResult, substitutor.replace(buffer, 1, buffer.length() - 2));
        }
    }

    private void assertStringBuilderReplacement(final StrSubstitutor substitutor,
                                                final String expectedResult,
                                                final String expectedSubstringResult,
                                                final String template,
                                                final boolean checkSubstringOverloads) {
        final StringBuilder builder = new StringBuilder(template);
        assertEquals(expectedResult, substitutor.replace(builder));
        if (checkSubstringOverloads) {
            assertEquals(expectedSubstringResult, substitutor.replace(builder, 1, builder.length() - 2));
        }
    }

    private void assertStrBuilderReplacement(final StrSubstitutor substitutor,
                                             final String expectedResult,
                                             final String expectedSubstringResult,
                                             final String template,
                                             final boolean checkSubstringOverloads) {
        final StrBuilder builder = new StrBuilder(template);
        assertEquals(expectedResult, substitutor.replace(builder));
        if (checkSubstringOverloads) {
            assertEquals(expectedSubstringResult, substitutor.replace(builder, 1, builder.length() - 2));
        }
    }

    private void assertObjectReplacement(final StrSubstitutor substitutor,
                                         final String expectedResult,
                                         final String template) {
        final MutableObject<String> objectWhoseToStringReturnsTemplate = new MutableObject<>(template);
        assertEquals(expectedResult, substitutor.replace(objectWhoseToStringReturnsTemplate));
    }

    private void assertReplaceInStringBuffer(final StrSubstitutor substitutor,
                                             final String expectedResult,
                                             final String template,
                                             final boolean checkSubstringOverloads) {
        StringBuffer buffer = new StringBuffer(template);
        assertTrue(substitutor.replaceIn(buffer));
        assertEquals(expectedResult, buffer.toString());

        if (checkSubstringOverloads) {
            buffer = new StringBuffer(template);
            assertTrue(substitutor.replaceIn(buffer, 1, buffer.length() - 2));
            assertEquals(expectedResult, buffer.toString());
        }
    }

    private void assertReplaceInStringBuilder(final StrSubstitutor substitutor,
                                              final String expectedResult,
                                              final String template,
                                              final boolean checkSubstringOverloads) {
        StringBuilder builder = new StringBuilder(template);
        assertTrue(substitutor.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());

        if (checkSubstringOverloads) {
            builder = new StringBuilder(template);
            assertTrue(substitutor.replaceIn(builder, 1, builder.length() - 2));
            assertEquals(expectedResult, builder.toString());
        }
    }

    private void assertReplaceInStrBuilder(final StrSubstitutor substitutor,
                                           final String expectedResult,
                                           final String template,
                                           final boolean checkSubstringOverloads) {
        StrBuilder builder = new StrBuilder(template);
        assertTrue(substitutor.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());

        if (checkSubstringOverloads) {
            builder = new StrBuilder(template);
            assertTrue(substitutor.replaceIn(builder, 1, builder.length() - 2));
            assertEquals(expectedResult, builder.toString());
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

    /**
     * Tests unknown key replace.
     */
    @Test
    void testReplaceUnknownKey() {
        assertReplacementAcrossSupportedInputTypes(UNKNOWN_PERSON_RESULT, UNKNOWN_PERSON_TEMPLATE, true);
        assertReplacementAcrossSupportedInputTypes(
                UNKNOWN_PERSON_WITH_DEFAULT_RESULT,
                UNKNOWN_PERSON_WITH_DEFAULT_TEMPLATE,
                true);
    }
}
