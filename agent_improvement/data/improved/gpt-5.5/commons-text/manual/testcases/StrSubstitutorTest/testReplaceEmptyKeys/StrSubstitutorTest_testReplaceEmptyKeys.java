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

public class StrSubstitutorTest_testReplaceEmptyKeys {

    private static final String EMPTY_KEY_TEMPLATE = "The ${} jumps over the ${target}.";
    private static final String EMPTY_KEY_RESULT = "The ${} jumps over the lazy dog.";
    private static final String EMPTY_KEY_WITH_DEFAULT_TEMPLATE = "The ${:-animal} jumps over the ${target}.";
    private static final String EMPTY_KEY_WITH_DEFAULT_RESULT = "The animal jumps over the lazy dog.";

    private Map<String, String> values;

    private void assertNoReplacement(final String replaceTemplate) {
        final StrSubstitutor substitutor = new StrSubstitutor(values);
        if (replaceTemplate == null) {
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
            return;
        }

        assertEquals(replaceTemplate, substitutor.replace(replaceTemplate));
        final StrBuilder builder = new StrBuilder(replaceTemplate);
        assertFalse(substitutor.replaceIn(builder));
        assertEquals(replaceTemplate, builder.toString());
    }

    private void assertReplacement(final String expectedResult, final String replaceTemplate, final boolean testSubstringOverloads) {
        final StrSubstitutor substitutor = new StrSubstitutor(values);
        assertReplacement(substitutor, expectedResult, replaceTemplate, testSubstringOverloads);
    }

    private void assertReplacement(final StrSubstitutor substitutor, final String expectedResult,
            final String replaceTemplate, final boolean testSubstringOverloads) {
        final String expectedSubstringResult = expectedResult.substring(1, expectedResult.length() - 1);

        assertStringReplacement(substitutor, expectedResult, expectedSubstringResult, replaceTemplate, testSubstringOverloads);
        assertCharArrayReplacement(substitutor, expectedResult, expectedSubstringResult, replaceTemplate, testSubstringOverloads);
        assertStringBufferReplacement(substitutor, expectedResult, expectedSubstringResult, replaceTemplate, testSubstringOverloads);
        assertStringBuilderReplacement(substitutor, expectedResult, expectedSubstringResult, replaceTemplate, testSubstringOverloads);
        assertStrBuilderReplacement(substitutor, expectedResult, expectedSubstringResult, replaceTemplate, testSubstringOverloads);
        assertObjectReplacement(substitutor, expectedResult, replaceTemplate);
        assertInPlaceReplacement(substitutor, expectedResult, replaceTemplate, testSubstringOverloads);
    }

    private void assertStringReplacement(final StrSubstitutor substitutor, final String expectedResult,
            final String expectedSubstringResult, final String replaceTemplate, final boolean testSubstringOverloads) {
        assertEquals(expectedResult, substitutor.replace(replaceTemplate));
        if (testSubstringOverloads) {
            assertEquals(expectedSubstringResult, substitutor.replace(replaceTemplate, 1, replaceTemplate.length() - 2));
        }
    }

    private void assertCharArrayReplacement(final StrSubstitutor substitutor, final String expectedResult,
            final String expectedSubstringResult, final String replaceTemplate, final boolean testSubstringOverloads) {
        final char[] chars = replaceTemplate.toCharArray();
        assertEquals(expectedResult, substitutor.replace(chars));
        if (testSubstringOverloads) {
            assertEquals(expectedSubstringResult, substitutor.replace(chars, 1, chars.length - 2));
        }
    }

    private void assertStringBufferReplacement(final StrSubstitutor substitutor, final String expectedResult,
            final String expectedSubstringResult, final String replaceTemplate, final boolean testSubstringOverloads) {
        final StringBuffer buffer = new StringBuffer(replaceTemplate);
        assertEquals(expectedResult, substitutor.replace(buffer));
        if (testSubstringOverloads) {
            assertEquals(expectedSubstringResult, substitutor.replace(buffer, 1, buffer.length() - 2));
        }
    }

    private void assertStringBuilderReplacement(final StrSubstitutor substitutor, final String expectedResult,
            final String expectedSubstringResult, final String replaceTemplate, final boolean testSubstringOverloads) {
        final StringBuilder builder = new StringBuilder(replaceTemplate);
        assertEquals(expectedResult, substitutor.replace(builder));
        if (testSubstringOverloads) {
            assertEquals(expectedSubstringResult, substitutor.replace(builder, 1, builder.length() - 2));
        }
    }

    private void assertStrBuilderReplacement(final StrSubstitutor substitutor, final String expectedResult,
            final String expectedSubstringResult, final String replaceTemplate, final boolean testSubstringOverloads) {
        final StrBuilder builder = new StrBuilder(replaceTemplate);
        assertEquals(expectedResult, substitutor.replace(builder));
        if (testSubstringOverloads) {
            assertEquals(expectedSubstringResult, substitutor.replace(builder, 1, builder.length() - 2));
        }
    }

    private void assertObjectReplacement(final StrSubstitutor substitutor, final String expectedResult,
            final String replaceTemplate) {
        final MutableObject<String> objectWhoseToStringReturnsTemplate = new MutableObject<>(replaceTemplate);
        assertEquals(expectedResult, substitutor.replace(objectWhoseToStringReturnsTemplate));
    }

    private void assertInPlaceReplacement(final StrSubstitutor substitutor, final String expectedResult,
            final String replaceTemplate, final boolean testSubstringOverloads) {
        assertStringBufferInPlaceReplacement(substitutor, expectedResult, replaceTemplate, testSubstringOverloads);
        assertStringBuilderInPlaceReplacement(substitutor, expectedResult, replaceTemplate, testSubstringOverloads);
        assertStrBuilderInPlaceReplacement(substitutor, expectedResult, replaceTemplate, testSubstringOverloads);
    }

    private void assertStringBufferInPlaceReplacement(final StrSubstitutor substitutor, final String expectedResult,
            final String replaceTemplate, final boolean testSubstringOverloads) {
        StringBuffer buffer = new StringBuffer(replaceTemplate);
        assertTrue(substitutor.replaceIn(buffer));
        assertEquals(expectedResult, buffer.toString());
        if (testSubstringOverloads) {
            buffer = new StringBuffer(replaceTemplate);
            assertTrue(substitutor.replaceIn(buffer, 1, buffer.length() - 2));
            assertEquals(expectedResult, buffer.toString());
        }
    }

    private void assertStringBuilderInPlaceReplacement(final StrSubstitutor substitutor, final String expectedResult,
            final String replaceTemplate, final boolean testSubstringOverloads) {
        StringBuilder builder = new StringBuilder(replaceTemplate);
        assertTrue(substitutor.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (testSubstringOverloads) {
            builder = new StringBuilder(replaceTemplate);
            assertTrue(substitutor.replaceIn(builder, 1, builder.length() - 2));
            assertEquals(expectedResult, builder.toString());
        }
    }

    private void assertStrBuilderInPlaceReplacement(final StrSubstitutor substitutor, final String expectedResult,
            final String replaceTemplate, final boolean testSubstringOverloads) {
        StrBuilder builder = new StrBuilder(replaceTemplate);
        assertTrue(substitutor.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (testSubstringOverloads) {
            builder = new StrBuilder(replaceTemplate);
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

    @Test
    void testReplaceEmptyKeys() {
        assertReplacement(EMPTY_KEY_RESULT, EMPTY_KEY_TEMPLATE, true);
        assertReplacement(EMPTY_KEY_WITH_DEFAULT_RESULT, EMPTY_KEY_WITH_DEFAULT_TEMPLATE, true);
    }
}
