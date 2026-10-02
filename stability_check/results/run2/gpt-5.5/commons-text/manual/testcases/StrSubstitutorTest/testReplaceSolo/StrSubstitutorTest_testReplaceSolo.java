package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceSolo {

    private Map<String, String> values;

    private void assertNoReplacementChanges(final String template) {
        final StrSubstitutor substitutor = new StrSubstitutor(values);
        if (template == null) {
            assertNull(substitutor.replace((String) null));
            assertNull(substitutor.replace((String) null, 0, 100));
            assertNull(substitutor.replace((char[]) null));
            assertNull(substitutor.replace((char[]) null, 0, 100));
            assertNull(substitutor.replace((StringBuffer) null));
            assertNull(substitutor.replace((StringBuffer) null, 0, 100));
            assertNull(substitutor.replace((StrBuilder) null));
            assertNull(substitutor.replace((StrBuilder) null, 0, 100));
            assertNull(substitutor.replace((Object) null));
            assertFalse(substitutor.replaceIn((StringBuffer) null));
            assertFalse(substitutor.replaceIn((StringBuffer) null, 0, 100));
            assertFalse(substitutor.replaceIn((StrBuilder) null));
            assertFalse(substitutor.replaceIn((StrBuilder) null, 0, 100));
        } else {
            assertEquals(template, substitutor.replace(template));
            final StrBuilder unchangedBuilder = new StrBuilder(template);
            assertFalse(substitutor.replaceIn(unchangedBuilder));
            assertEquals(template, unchangedBuilder.toString());
        }
    }

    private void assertReplacementAcrossSourceTypes(final String expectedResult, final String template, final boolean testSubstringOverloads) {
        final StrSubstitutor substitutor = new StrSubstitutor(values);
        assertReplacementAcrossSourceTypes(substitutor, expectedResult, template, testSubstringOverloads);
    }

    private void assertReplacementAcrossSourceTypes(
            final StrSubstitutor substitutor,
            final String expectedResult,
            final String template,
            final boolean testSubstringOverloads) {
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

        StringBuffer templateBuffer = new StringBuffer(template);
        assertEquals(expectedResult, substitutor.replace(templateBuffer));
        if (testSubstringOverloads) {
            assertEquals(expectedSubstringResult, substitutor.replace(templateBuffer, 1, templateBuffer.length() - 2));
        }

        StringBuilder templateBuilder = new StringBuilder(template);
        assertEquals(expectedResult, substitutor.replace(templateBuilder));
        if (testSubstringOverloads) {
            assertEquals(expectedSubstringResult, substitutor.replace(templateBuilder, 1, templateBuilder.length() - 2));
        }

        StrBuilder templateStrBuilder = new StrBuilder(template);
        assertEquals(expectedResult, substitutor.replace(templateStrBuilder));
        if (testSubstringOverloads) {
            assertEquals(expectedSubstringResult, substitutor.replace(templateStrBuilder, 1, templateStrBuilder.length() - 2));
        }

        final MutableObject<String> templateObject = new MutableObject<>(template);
        assertEquals(expectedResult, substitutor.replace(templateObject));

        templateBuffer = new StringBuffer(template);
        assertTrue(substitutor.replaceIn(templateBuffer));
        assertEquals(expectedResult, templateBuffer.toString());
        if (testSubstringOverloads) {
            templateBuffer = new StringBuffer(template);
            assertTrue(substitutor.replaceIn(templateBuffer, 1, templateBuffer.length() - 2));
            assertEquals(expectedResult, templateBuffer.toString());
        }

        templateBuilder = new StringBuilder(template);
        assertTrue(substitutor.replaceIn(templateBuilder));
        assertEquals(expectedResult, templateBuilder.toString());
        if (testSubstringOverloads) {
            templateBuilder = new StringBuilder(template);
            assertTrue(substitutor.replaceIn(templateBuilder, 1, templateBuilder.length() - 2));
            assertEquals(expectedResult, templateBuilder.toString());
        }

        templateStrBuilder = new StrBuilder(template);
        assertTrue(substitutor.replaceIn(templateStrBuilder));
        assertEquals(expectedResult, templateStrBuilder.toString());
        if (testSubstringOverloads) {
            templateStrBuilder = new StrBuilder(template);
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

    /**
     * Tests simple key replace.
     */
    @Test
    void testReplaceSolo() {
        assertReplacementAcrossSourceTypes("quick brown fox", "${animal}", false);
    }
}
