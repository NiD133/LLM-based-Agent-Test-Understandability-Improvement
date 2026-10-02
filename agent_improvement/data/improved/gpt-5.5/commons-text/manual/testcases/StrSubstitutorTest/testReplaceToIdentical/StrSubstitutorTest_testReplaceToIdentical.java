package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceToIdentical {

    private static final String TEMPLATE = "The ${animal} jumps.";
    private static final int SUBSTRING_OFFSET = 1;
    private static final int SUBSTRING_TRIM_LENGTH = 2;

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
     * Tests replace creates output same as input.
     */
    @Test
    void testReplaceToIdentical() {
        values.put("animal", "$${${thing}}");
        values.put("thing", "animal");

        assertReplacementResult(TEMPLATE, TEMPLATE, true);
    }

    private void assertReplacementResult(final String expectedResult, final String replaceTemplate, final boolean includeSubstringChecks) {
        final StrSubstitutor substitutor = new StrSubstitutor(values);
        assertReplacementResult(substitutor, expectedResult, replaceTemplate, includeSubstringChecks);
    }

    private void assertReplacementResult(final StrSubstitutor substitutor, final String expectedResult, final String replaceTemplate,
            final boolean includeSubstringChecks) {
        final String expectedSubstringResult = expectedResult.substring(SUBSTRING_OFFSET, expectedResult.length() - SUBSTRING_TRIM_LENGTH);

        assertReplaceReturnsExpectedResult(substitutor, expectedResult, replaceTemplate, expectedSubstringResult, includeSubstringChecks);
        assertReplaceInUpdatesMutableSources(substitutor, expectedResult, replaceTemplate, includeSubstringChecks);
    }

    private void assertReplaceReturnsExpectedResult(final StrSubstitutor substitutor, final String expectedResult, final String replaceTemplate,
            final String expectedSubstringResult, final boolean includeSubstringChecks) {
        assertEquals(expectedResult, substitutor.replace(replaceTemplate));
        if (includeSubstringChecks) {
            assertEquals(expectedSubstringResult, substitutor.replace(replaceTemplate, SUBSTRING_OFFSET, replaceTemplate.length() - SUBSTRING_TRIM_LENGTH));
        }

        final char[] chars = replaceTemplate.toCharArray();
        assertEquals(expectedResult, substitutor.replace(chars));
        if (includeSubstringChecks) {
            assertEquals(expectedSubstringResult, substitutor.replace(chars, SUBSTRING_OFFSET, chars.length - SUBSTRING_TRIM_LENGTH));
        }

        final StringBuffer buffer = new StringBuffer(replaceTemplate);
        assertEquals(expectedResult, substitutor.replace(buffer));
        if (includeSubstringChecks) {
            assertEquals(expectedSubstringResult, substitutor.replace(buffer, SUBSTRING_OFFSET, buffer.length() - SUBSTRING_TRIM_LENGTH));
        }

        final StringBuilder builder = new StringBuilder(replaceTemplate);
        assertEquals(expectedResult, substitutor.replace(builder));
        if (includeSubstringChecks) {
            assertEquals(expectedSubstringResult, substitutor.replace(builder, SUBSTRING_OFFSET, builder.length() - SUBSTRING_TRIM_LENGTH));
        }

        final StrBuilder strBuilder = new StrBuilder(replaceTemplate);
        assertEquals(expectedResult, substitutor.replace(strBuilder));
        if (includeSubstringChecks) {
            assertEquals(expectedSubstringResult, substitutor.replace(strBuilder, SUBSTRING_OFFSET, strBuilder.length() - SUBSTRING_TRIM_LENGTH));
        }

        final MutableObject<String> objectWhoseToStringReturnsTemplate = new MutableObject<>(replaceTemplate);
        assertEquals(expectedResult, substitutor.replace(objectWhoseToStringReturnsTemplate));
    }

    private void assertReplaceInUpdatesMutableSources(final StrSubstitutor substitutor, final String expectedResult, final String replaceTemplate,
            final boolean includeSubstringChecks) {
        StringBuffer buffer = new StringBuffer(replaceTemplate);
        assertTrue(substitutor.replaceIn(buffer));
        assertEquals(expectedResult, buffer.toString());
        if (includeSubstringChecks) {
            buffer = new StringBuffer(replaceTemplate);
            assertTrue(substitutor.replaceIn(buffer, SUBSTRING_OFFSET, buffer.length() - SUBSTRING_TRIM_LENGTH));
            assertEquals(expectedResult, buffer.toString());
        }

        StringBuilder builder = new StringBuilder(replaceTemplate);
        assertTrue(substitutor.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (includeSubstringChecks) {
            builder = new StringBuilder(replaceTemplate);
            assertTrue(substitutor.replaceIn(builder, SUBSTRING_OFFSET, builder.length() - SUBSTRING_TRIM_LENGTH));
            assertEquals(expectedResult, builder.toString());
        }

        StrBuilder strBuilder = new StrBuilder(replaceTemplate);
        assertTrue(substitutor.replaceIn(strBuilder));
        assertEquals(expectedResult, strBuilder.toString());
        if (includeSubstringChecks) {
            strBuilder = new StrBuilder(replaceTemplate);
            assertTrue(substitutor.replaceIn(strBuilder, SUBSTRING_OFFSET, strBuilder.length() - SUBSTRING_TRIM_LENGTH));
            assertEquals(expectedResult, strBuilder.toString());
        }
    }
}
