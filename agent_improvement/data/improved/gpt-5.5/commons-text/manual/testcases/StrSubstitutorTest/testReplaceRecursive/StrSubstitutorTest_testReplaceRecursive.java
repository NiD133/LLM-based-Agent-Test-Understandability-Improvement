package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceRecursive {

    private static final String RECURSIVE_TEMPLATE = "The ${animal} jumps over the ${target}.";
    private static final String RESOLVED_RECURSIVE_TEMPLATE = "The quick brown fox jumps over the lazy dog.";

    private Map<String, String> values;

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
     * Tests simple recursive replace.
     */
    @Test
    void testReplaceRecursive() {
        configureRecursiveAnimalAndTargetValues();

        doTestReplace(RESOLVED_RECURSIVE_TEMPLATE, RECURSIVE_TEMPLATE, true);

        values.put("pet", "${petCharacteristicUnknown:-lazy} dog");
        doTestReplace(RESOLVED_RECURSIVE_TEMPLATE, RECURSIVE_TEMPLATE, true);
    }

    private void configureRecursiveAnimalAndTargetValues() {
        values.put("animal", "${critter}");
        values.put("target", "${pet}");
        values.put("pet", "${petCharacteristic} dog");
        values.put("petCharacteristic", "lazy");
        values.put("critter", "${critterSpeed} ${critterColor} ${critterType}");
        values.put("critterSpeed", "quick");
        values.put("critterColor", "brown");
        values.put("critterType", "fox");
    }

    private void doTestReplace(final String expectedResult, final String replaceTemplate, final boolean substring) {
        doTestReplace(new StrSubstitutor(values), expectedResult, replaceTemplate, substring);
    }

    private void doTestReplace(
        final StrSubstitutor substitutor,
        final String expectedResult,
        final String replaceTemplate,
        final boolean substring) {

        final String expectedSubstringResult = expectedResult.substring(1, expectedResult.length() - 1);

        assertStringReplacement(substitutor, expectedResult, expectedSubstringResult, replaceTemplate, substring);
        assertCharArrayReplacement(substitutor, expectedResult, expectedSubstringResult, replaceTemplate, substring);
        assertStringBufferReplacement(substitutor, expectedResult, expectedSubstringResult, replaceTemplate, substring);
        assertStringBuilderReplacement(substitutor, expectedResult, expectedSubstringResult, replaceTemplate, substring);
        assertStrBuilderReplacement(substitutor, expectedResult, expectedSubstringResult, replaceTemplate, substring);
        assertObjectReplacement(substitutor, expectedResult, replaceTemplate);
        assertStringBufferReplacementInPlace(substitutor, expectedResult, replaceTemplate, substring);
        assertStringBuilderReplacementInPlace(substitutor, expectedResult, replaceTemplate, substring);
        assertStrBuilderReplacementInPlace(substitutor, expectedResult, replaceTemplate, substring);
    }

    private void assertStringReplacement(
        final StrSubstitutor substitutor,
        final String expectedResult,
        final String expectedSubstringResult,
        final String replaceTemplate,
        final boolean substring) {

        assertEquals(expectedResult, substitutor.replace(replaceTemplate));
        if (substring) {
            assertEquals(expectedSubstringResult, substitutor.replace(replaceTemplate, 1, replaceTemplate.length() - 2));
        }
    }

    private void assertCharArrayReplacement(
        final StrSubstitutor substitutor,
        final String expectedResult,
        final String expectedSubstringResult,
        final String replaceTemplate,
        final boolean substring) {

        final char[] chars = replaceTemplate.toCharArray();
        assertEquals(expectedResult, substitutor.replace(chars));
        if (substring) {
            assertEquals(expectedSubstringResult, substitutor.replace(chars, 1, chars.length - 2));
        }
    }

    private void assertStringBufferReplacement(
        final StrSubstitutor substitutor,
        final String expectedResult,
        final String expectedSubstringResult,
        final String replaceTemplate,
        final boolean substring) {

        final StringBuffer buffer = new StringBuffer(replaceTemplate);
        assertEquals(expectedResult, substitutor.replace(buffer));
        if (substring) {
            assertEquals(expectedSubstringResult, substitutor.replace(buffer, 1, buffer.length() - 2));
        }
    }

    private void assertStringBuilderReplacement(
        final StrSubstitutor substitutor,
        final String expectedResult,
        final String expectedSubstringResult,
        final String replaceTemplate,
        final boolean substring) {

        final StringBuilder builder = new StringBuilder(replaceTemplate);
        assertEquals(expectedResult, substitutor.replace(builder));
        if (substring) {
            assertEquals(expectedSubstringResult, substitutor.replace(builder, 1, builder.length() - 2));
        }
    }

    private void assertStrBuilderReplacement(
        final StrSubstitutor substitutor,
        final String expectedResult,
        final String expectedSubstringResult,
        final String replaceTemplate,
        final boolean substring) {

        final StrBuilder builder = new StrBuilder(replaceTemplate);
        assertEquals(expectedResult, substitutor.replace(builder));
        if (substring) {
            assertEquals(expectedSubstringResult, substitutor.replace(builder, 1, builder.length() - 2));
        }
    }

    private void assertObjectReplacement(
        final StrSubstitutor substitutor,
        final String expectedResult,
        final String replaceTemplate) {

        final MutableObject<String> objectWithTemplateToString = new MutableObject<>(replaceTemplate);
        assertEquals(expectedResult, substitutor.replace(objectWithTemplateToString));
    }

    private void assertStringBufferReplacementInPlace(
        final StrSubstitutor substitutor,
        final String expectedResult,
        final String replaceTemplate,
        final boolean substring) {

        StringBuffer buffer = new StringBuffer(replaceTemplate);
        assertTrue(substitutor.replaceIn(buffer));
        assertEquals(expectedResult, buffer.toString());
        if (substring) {
            buffer = new StringBuffer(replaceTemplate);
            assertTrue(substitutor.replaceIn(buffer, 1, buffer.length() - 2));
            assertEquals(expectedResult, buffer.toString());
        }
    }

    private void assertStringBuilderReplacementInPlace(
        final StrSubstitutor substitutor,
        final String expectedResult,
        final String replaceTemplate,
        final boolean substring) {

        StringBuilder builder = new StringBuilder(replaceTemplate);
        assertTrue(substitutor.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (substring) {
            builder = new StringBuilder(replaceTemplate);
            assertTrue(substitutor.replaceIn(builder, 1, builder.length() - 2));
            assertEquals(expectedResult, builder.toString());
        }
    }

    private void assertStrBuilderReplacementInPlace(
        final StrSubstitutor substitutor,
        final String expectedResult,
        final String replaceTemplate,
        final boolean substring) {

        StrBuilder builder = new StrBuilder(replaceTemplate);
        assertTrue(substitutor.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (substring) {
            builder = new StrBuilder(replaceTemplate);
            assertTrue(substitutor.replaceIn(builder, 1, builder.length() - 2));
            assertEquals(expectedResult, builder.toString());
        }
    }
}
