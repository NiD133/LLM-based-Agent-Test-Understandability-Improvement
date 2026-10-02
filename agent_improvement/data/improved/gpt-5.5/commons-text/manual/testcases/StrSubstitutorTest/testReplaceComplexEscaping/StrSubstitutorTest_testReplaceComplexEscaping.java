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

public class StrSubstitutorTest_testReplaceComplexEscaping {

    private static final String ESCAPED_NESTED_VARIABLE_TEMPLATE =
            "The $${${animal}} jumps over the ${target}.";
    private static final String ESCAPED_NESTED_VARIABLE_RESULT =
            "The ${quick brown fox} jumps over the lazy dog.";
    private static final String ESCAPED_NESTED_VARIABLE_WITH_DEFAULT_TEMPLATE =
            "The $${${animal}} jumps over the ${target}. $${${undefined.number:-1234567890}}.";
    private static final String ESCAPED_NESTED_VARIABLE_WITH_DEFAULT_RESULT =
            "The ${quick brown fox} jumps over the lazy dog. ${1234567890}.";

    private Map<String, String> values;

    private void assertNoReplacement(final String template) {
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
            final StrBuilder builder = new StrBuilder(template);
            assertFalse(substitutor.replaceIn(builder));
            assertEquals(template, builder.toString());
        }
    }

    private void assertReplacement(final String expectedResult, final String template, final boolean testSubstringApis) {
        final StrSubstitutor substitutor = new StrSubstitutor(values);
        assertReplacement(substitutor, expectedResult, template, testSubstringApis);
    }

    private void assertReplacement(final StrSubstitutor substitutor, final String expectedResult,
            final String template, final boolean testSubstringApis) {
        final String expectedSubstringResult = expectedResult.substring(1, expectedResult.length() - 1);

        assertStringReplacement(substitutor, expectedResult, expectedSubstringResult, template, testSubstringApis);
        assertCharArrayReplacement(substitutor, expectedResult, expectedSubstringResult, template, testSubstringApis);
        assertStringBufferReplacement(substitutor, expectedResult, expectedSubstringResult, template, testSubstringApis);
        assertStringBuilderReplacement(substitutor, expectedResult, expectedSubstringResult, template, testSubstringApis);
        assertStrBuilderReplacement(substitutor, expectedResult, expectedSubstringResult, template, testSubstringApis);
        assertObjectReplacement(substitutor, expectedResult, template);
        assertInPlaceReplacement(substitutor, expectedResult, template, testSubstringApis);
    }

    private void assertStringReplacement(final StrSubstitutor substitutor, final String expectedResult,
            final String expectedSubstringResult, final String template, final boolean testSubstringApis) {
        assertEquals(expectedResult, substitutor.replace(template));
        if (testSubstringApis) {
            assertEquals(expectedSubstringResult, substitutor.replace(template, 1, template.length() - 2));
        }
    }

    private void assertCharArrayReplacement(final StrSubstitutor substitutor, final String expectedResult,
            final String expectedSubstringResult, final String template, final boolean testSubstringApis) {
        final char[] chars = template.toCharArray();
        assertEquals(expectedResult, substitutor.replace(chars));
        if (testSubstringApis) {
            assertEquals(expectedSubstringResult, substitutor.replace(chars, 1, chars.length - 2));
        }
    }

    private void assertStringBufferReplacement(final StrSubstitutor substitutor, final String expectedResult,
            final String expectedSubstringResult, final String template, final boolean testSubstringApis) {
        final StringBuffer buffer = new StringBuffer(template);
        assertEquals(expectedResult, substitutor.replace(buffer));
        if (testSubstringApis) {
            assertEquals(expectedSubstringResult, substitutor.replace(buffer, 1, buffer.length() - 2));
        }
    }

    private void assertStringBuilderReplacement(final StrSubstitutor substitutor, final String expectedResult,
            final String expectedSubstringResult, final String template, final boolean testSubstringApis) {
        final StringBuilder builder = new StringBuilder(template);
        assertEquals(expectedResult, substitutor.replace(builder));
        if (testSubstringApis) {
            assertEquals(expectedSubstringResult, substitutor.replace(builder, 1, builder.length() - 2));
        }
    }

    private void assertStrBuilderReplacement(final StrSubstitutor substitutor, final String expectedResult,
            final String expectedSubstringResult, final String template, final boolean testSubstringApis) {
        final StrBuilder builder = new StrBuilder(template);
        assertEquals(expectedResult, substitutor.replace(builder));
        if (testSubstringApis) {
            assertEquals(expectedSubstringResult, substitutor.replace(builder, 1, builder.length() - 2));
        }
    }

    private void assertObjectReplacement(final StrSubstitutor substitutor, final String expectedResult,
            final String template) {
        final MutableObject<String> objectWhoseToStringReturnsTemplate = new MutableObject<>(template);
        assertEquals(expectedResult, substitutor.replace(objectWhoseToStringReturnsTemplate));
    }

    private void assertInPlaceReplacement(final StrSubstitutor substitutor, final String expectedResult,
            final String template, final boolean testSubstringApis) {
        assertStringBufferInPlaceReplacement(substitutor, expectedResult, template, testSubstringApis);
        assertStringBuilderInPlaceReplacement(substitutor, expectedResult, template, testSubstringApis);
        assertStrBuilderInPlaceReplacement(substitutor, expectedResult, template, testSubstringApis);
    }

    private void assertStringBufferInPlaceReplacement(final StrSubstitutor substitutor, final String expectedResult,
            final String template, final boolean testSubstringApis) {
        StringBuffer buffer = new StringBuffer(template);
        assertTrue(substitutor.replaceIn(buffer));
        assertEquals(expectedResult, buffer.toString());
        if (testSubstringApis) {
            buffer = new StringBuffer(template);
            assertTrue(substitutor.replaceIn(buffer, 1, buffer.length() - 2));
            assertEquals(expectedResult, buffer.toString());
        }
    }

    private void assertStringBuilderInPlaceReplacement(final StrSubstitutor substitutor, final String expectedResult,
            final String template, final boolean testSubstringApis) {
        StringBuilder builder = new StringBuilder(template);
        assertTrue(substitutor.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (testSubstringApis) {
            builder = new StringBuilder(template);
            assertTrue(substitutor.replaceIn(builder, 1, builder.length() - 2));
            assertEquals(expectedResult, builder.toString());
        }
    }

    private void assertStrBuilderInPlaceReplacement(final StrSubstitutor substitutor, final String expectedResult,
            final String template, final boolean testSubstringApis) {
        StrBuilder builder = new StrBuilder(template);
        assertTrue(substitutor.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (testSubstringApis) {
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

    @Test
    void testReplaceComplexEscaping() {
        assertReplacement(ESCAPED_NESTED_VARIABLE_RESULT, ESCAPED_NESTED_VARIABLE_TEMPLATE, true);
        assertReplacement(ESCAPED_NESTED_VARIABLE_WITH_DEFAULT_RESULT,
                ESCAPED_NESTED_VARIABLE_WITH_DEFAULT_TEMPLATE, true);
    }
}
