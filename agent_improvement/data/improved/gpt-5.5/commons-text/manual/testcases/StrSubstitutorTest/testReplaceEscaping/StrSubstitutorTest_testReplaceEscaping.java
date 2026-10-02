package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceEscaping {

    private Map<String, String> values;

    private void assertReplacementAcrossSupportedInputs(final String expectedResult, final String template,
            final boolean testSubstringOverloads) {
        final StrSubstitutor substitutor = new StrSubstitutor(values);
        assertReplacementAcrossSupportedInputs(substitutor, expectedResult, template, testSubstringOverloads);
    }

    private void assertReplacementAcrossSupportedInputs(final StrSubstitutor substitutor, final String expectedResult,
            final String template, final boolean testSubstringOverloads) {
        final String expectedSubstringResult = expectedResult.substring(1, expectedResult.length() - 1);

        assertEquals(expectedResult, substitutor.replace(template));
        if (testSubstringOverloads) {
            assertEquals(expectedSubstringResult, substitutor.replace(template, 1, template.length() - 2));
        }

        final char[] templateChars = template.toCharArray();
        assertEquals(expectedResult, substitutor.replace(templateChars));
        if (testSubstringOverloads) {
            assertEquals(expectedSubstringResult, substitutor.replace(templateChars, 1, templateChars.length - 2));
        }

        StringBuffer buffer = new StringBuffer(template);
        assertEquals(expectedResult, substitutor.replace(buffer));
        if (testSubstringOverloads) {
            assertEquals(expectedSubstringResult, substitutor.replace(buffer, 1, buffer.length() - 2));
        }

        StringBuilder builder = new StringBuilder(template);
        assertEquals(expectedResult, substitutor.replace(builder));
        if (testSubstringOverloads) {
            assertEquals(expectedSubstringResult, substitutor.replace(builder, 1, builder.length() - 2));
        }

        StrBuilder strBuilder = new StrBuilder(template);
        assertEquals(expectedResult, substitutor.replace(strBuilder));
        if (testSubstringOverloads) {
            assertEquals(expectedSubstringResult, substitutor.replace(strBuilder, 1, strBuilder.length() - 2));
        }

        final MutableObject<String> objectWhoseToStringIsTemplate = new MutableObject<>(template);
        assertEquals(expectedResult, substitutor.replace(objectWhoseToStringIsTemplate));

        buffer = new StringBuffer(template);
        assertTrue(substitutor.replaceIn(buffer));
        assertEquals(expectedResult, buffer.toString());
        if (testSubstringOverloads) {
            buffer = new StringBuffer(template);
            assertTrue(substitutor.replaceIn(buffer, 1, buffer.length() - 2));
            assertEquals(expectedResult, buffer.toString());
        }

        builder = new StringBuilder(template);
        assertTrue(substitutor.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (testSubstringOverloads) {
            builder = new StringBuilder(template);
            assertTrue(substitutor.replaceIn(builder, 1, builder.length() - 2));
            assertEquals(expectedResult, builder.toString());
        }

        strBuilder = new StrBuilder(template);
        assertTrue(substitutor.replaceIn(strBuilder));
        assertEquals(expectedResult, strBuilder.toString());
        if (testSubstringOverloads) {
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
    void testReplaceEscaping() {
        assertReplacementAcrossSupportedInputs("The ${animal} jumps over the lazy dog.",
                "The $${animal} jumps over the ${target}.", true);
    }
}
