package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplaceSimple {

    private static final String ANIMAL = "quick brown fox";
    private static final String TARGET = "lazy dog";
    private static final String TEMPLATE = "The ${animal} jumps over the ${target}.";
    private static final String REPLACED_TEMPLATE = "The quick brown fox jumps over the lazy dog.";

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
        values.put("animal", ANIMAL);
        values.put("target", TARGET);
    }

    @Test
    void testReplaceSimple() throws IOException {
        assertEveryReplaceOverloadProduces(REPLACED_TEMPLATE, TEMPLATE, true);
    }

    private void assertEveryReplaceOverloadProduces(final String expectedResult, final String template,
            final boolean testSubstringOverloads) throws IOException {
        final StringSubstitutor substitutor = new StringSubstitutor(values);
        final String expectedSubstringResult = testSubstringOverloads ? expectedResult.substring(1, expectedResult.length() - 1) : expectedResult;

        assertStringReplacement(substitutor, expectedResult, expectedSubstringResult, template, testSubstringOverloads);
        assertCharArrayReplacement(substitutor, expectedResult, expectedSubstringResult, template, testSubstringOverloads);
        assertStringBufferReplacement(substitutor, expectedResult, expectedSubstringResult, template, testSubstringOverloads);
        assertStringBuilderReplacement(substitutor, expectedResult, expectedSubstringResult, template, testSubstringOverloads);
        assertTextStringBuilderReplacement(substitutor, expectedResult, expectedSubstringResult, template, testSubstringOverloads);
        assertObjectReplacement(substitutor, expectedResult, template);
        assertReplaceInStringBuffer(substitutor, expectedResult, template, testSubstringOverloads);
        assertReplaceInStringBuilder(substitutor, expectedResult, template, testSubstringOverloads);
        assertReplaceInTextStringBuilder(substitutor, expectedResult, template, testSubstringOverloads);
    }

    private void assertStringReplacement(final StringSubstitutor substitutor, final String expectedResult,
            final String expectedSubstringResult, final String template, final boolean testSubstringOverloads) throws IOException {
        final String actual = replace(substitutor, template);
        assertEquals(expectedResult, actual, () -> String.format("Index of difference: %,d", StringUtils.indexOfDifference(expectedResult, actual)));
        if (testSubstringOverloads) {
            assertEquals(expectedSubstringResult, substitutor.replace(template, 1, template.length() - 2));
        }
    }

    private void assertCharArrayReplacement(final StringSubstitutor substitutor, final String expectedResult,
            final String expectedSubstringResult, final String template, final boolean testSubstringOverloads) {
        final char[] chars = template.toCharArray();
        assertEquals(expectedResult, substitutor.replace(chars));
        if (testSubstringOverloads) {
            assertEquals(expectedSubstringResult, substitutor.replace(chars, 1, chars.length - 2));
        }
    }

    private void assertStringBufferReplacement(final StringSubstitutor substitutor, final String expectedResult,
            final String expectedSubstringResult, final String template, final boolean testSubstringOverloads) {
        final StringBuffer buffer = new StringBuffer(template);
        assertEquals(expectedResult, substitutor.replace(buffer));
        if (testSubstringOverloads) {
            assertEquals(expectedSubstringResult, substitutor.replace(buffer, 1, buffer.length() - 2));
        }
    }

    private void assertStringBuilderReplacement(final StringSubstitutor substitutor, final String expectedResult,
            final String expectedSubstringResult, final String template, final boolean testSubstringOverloads) {
        final StringBuilder builder = new StringBuilder(template);
        assertEquals(expectedResult, substitutor.replace(builder));
        if (testSubstringOverloads) {
            assertEquals(expectedSubstringResult, substitutor.replace(builder, 1, builder.length() - 2));
        }
    }

    private void assertTextStringBuilderReplacement(final StringSubstitutor substitutor, final String expectedResult,
            final String expectedSubstringResult, final String template, final boolean testSubstringOverloads) {
        final TextStringBuilder builder = new TextStringBuilder(template);
        assertEquals(expectedResult, substitutor.replace(builder));
        if (testSubstringOverloads) {
            assertEquals(expectedSubstringResult, substitutor.replace(builder, 1, builder.length() - 2));
        }
    }

    private void assertObjectReplacement(final StringSubstitutor substitutor, final String expectedResult, final String template) {
        final MutableObject<String> objectWhoseToStringReturnsTemplate = new MutableObject<>(template);
        assertEquals(expectedResult, substitutor.replace(objectWhoseToStringReturnsTemplate));
    }

    private void assertReplaceInStringBuffer(final StringSubstitutor substitutor, final String expectedResult,
            final String template, final boolean testSubstringOverloads) {
        StringBuffer buffer = new StringBuffer(template);
        assertTrue(substitutor.replaceIn(buffer), template);
        assertEquals(expectedResult, buffer.toString());

        if (testSubstringOverloads) {
            buffer = new StringBuffer(template);
            assertTrue(substitutor.replaceIn(buffer, 1, buffer.length() - 2));
            assertEquals(expectedResult, buffer.toString());
        }
    }

    private void assertReplaceInStringBuilder(final StringSubstitutor substitutor, final String expectedResult,
            final String template, final boolean testSubstringOverloads) {
        StringBuilder builder = new StringBuilder(template);
        assertTrue(substitutor.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());

        if (testSubstringOverloads) {
            builder = new StringBuilder(template);
            assertTrue(substitutor.replaceIn(builder, 1, builder.length() - 2));
            assertEquals(expectedResult, builder.toString());
        }
    }

    private void assertReplaceInTextStringBuilder(final StringSubstitutor substitutor, final String expectedResult,
            final String template, final boolean testSubstringOverloads) {
        TextStringBuilder builder = new TextStringBuilder(template);
        assertTrue(substitutor.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());

        if (testSubstringOverloads) {
            builder = new TextStringBuilder(template);
            assertTrue(substitutor.replaceIn(builder, 1, builder.length() - 2));
            assertEquals(expectedResult, builder.toString());
        }
    }

    /**
     * For subclasses to override.
     *
     * @throws IOException Thrown by subclasses.
     */
    protected String replace(final StringSubstitutor stringSubstitutor, final String template) throws IOException {
        return stringSubstitutor.replace(template);
    }
}
