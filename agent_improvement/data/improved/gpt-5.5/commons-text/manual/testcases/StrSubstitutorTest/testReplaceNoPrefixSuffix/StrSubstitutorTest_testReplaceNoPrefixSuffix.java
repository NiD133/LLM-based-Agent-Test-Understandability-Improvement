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

public class StrSubstitutorTest_testReplaceNoPrefixSuffix {

    private static final String ANIMAL_VALUE = "quick brown fox";
    private static final String TARGET_VALUE = "lazy dog";
    private static final String TEMPLATE_WITH_SUFFIX_ONLY = "The animal} jumps over the ${target}.";
    private static final String EXPECTED_REPLACEMENT = "The animal} jumps over the lazy dog.";

    private Map<String, String> values;

    private void assertNoReplacement(final String template) {
        final StrSubstitutor substitutor = new StrSubstitutor(values);
        if (template == null) {
            assertNullInputsRemainNull(substitutor);
            return;
        }

        assertEquals(template, substitutor.replace(template));
        final StrBuilder builder = new StrBuilder(template);
        assertFalse(substitutor.replaceIn(builder));
        assertEquals(template, builder.toString());
    }

    private void assertNullInputsRemainNull(final StrSubstitutor substitutor) {
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
    }

    private void assertReplacement(final String expected, final String template, final boolean testSubstringOverloads) {
        final StrSubstitutor substitutor = new StrSubstitutor(values);
        assertReplacement(substitutor, expected, template, testSubstringOverloads);
    }

    private void assertReplacement(final StrSubstitutor substitutor, final String expected, final String template,
        final boolean testSubstringOverloads) {
        final String expectedSubstringResult = expected.substring(1, expected.length() - 1);

        assertReplaceReturnsExpectedText(substitutor, expected, expectedSubstringResult, template, testSubstringOverloads);
        assertReplaceInMutatesToExpectedText(substitutor, expected, template, testSubstringOverloads);
    }

    private void assertReplaceReturnsExpectedText(final StrSubstitutor substitutor, final String expected,
        final String expectedSubstringResult, final String template, final boolean testSubstringOverloads) {
        assertEquals(expected, substitutor.replace(template));
        if (testSubstringOverloads) {
            assertEquals(expectedSubstringResult, substitutor.replace(template, 1, template.length() - 2));
        }

        final char[] chars = template.toCharArray();
        assertEquals(expected, substitutor.replace(chars));
        if (testSubstringOverloads) {
            assertEquals(expectedSubstringResult, substitutor.replace(chars, 1, chars.length - 2));
        }

        final StringBuffer buffer = new StringBuffer(template);
        assertEquals(expected, substitutor.replace(buffer));
        if (testSubstringOverloads) {
            assertEquals(expectedSubstringResult, substitutor.replace(buffer, 1, buffer.length() - 2));
        }

        final StringBuilder stringBuilder = new StringBuilder(template);
        assertEquals(expected, substitutor.replace(stringBuilder));
        if (testSubstringOverloads) {
            assertEquals(expectedSubstringResult, substitutor.replace(stringBuilder, 1, stringBuilder.length() - 2));
        }

        final StrBuilder strBuilder = new StrBuilder(template);
        assertEquals(expected, substitutor.replace(strBuilder));
        if (testSubstringOverloads) {
            assertEquals(expectedSubstringResult, substitutor.replace(strBuilder, 1, strBuilder.length() - 2));
        }

        final MutableObject<String> objectWhoseToStringReturnsTemplate = new MutableObject<>(template);
        assertEquals(expected, substitutor.replace(objectWhoseToStringReturnsTemplate));
    }

    private void assertReplaceInMutatesToExpectedText(final StrSubstitutor substitutor, final String expected,
        final String template, final boolean testSubstringOverloads) {
        StringBuffer buffer = new StringBuffer(template);
        assertTrue(substitutor.replaceIn(buffer));
        assertEquals(expected, buffer.toString());
        if (testSubstringOverloads) {
            buffer = new StringBuffer(template);
            assertTrue(substitutor.replaceIn(buffer, 1, buffer.length() - 2));
            assertEquals(expected, buffer.toString());
        }

        StringBuilder stringBuilder = new StringBuilder(template);
        assertTrue(substitutor.replaceIn(stringBuilder));
        assertEquals(expected, stringBuilder.toString());
        if (testSubstringOverloads) {
            stringBuilder = new StringBuilder(template);
            assertTrue(substitutor.replaceIn(stringBuilder, 1, stringBuilder.length() - 2));
            assertEquals(expected, stringBuilder.toString());
        }

        StrBuilder strBuilder = new StrBuilder(template);
        assertTrue(substitutor.replaceIn(strBuilder));
        assertEquals(expected, strBuilder.toString());
        if (testSubstringOverloads) {
            strBuilder = new StrBuilder(template);
            assertTrue(substitutor.replaceIn(strBuilder, 1, strBuilder.length() - 2));
            assertEquals(expected, strBuilder.toString());
        }
    }

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

    @Test
    void testReplaceNoPrefixSuffix() {
        assertReplacement(EXPECTED_REPLACEMENT, TEMPLATE_WITH_SUFFIX_ONLY, true);
    }
}
