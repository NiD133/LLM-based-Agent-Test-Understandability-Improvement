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

public class StrSubstitutorTest_testDisableSubstitutionInValues {

    private static final String TEMPLATE = "The ${animal} jumps over the ${target}.";
    private static final String EXPECTED_WITH_UNRESOLVED_VALUE_VARIABLES = "The ${critter} jumps over the ${pet}.";

    private Map<String, String> values;

    private void assertNoReplacement(final String replaceTemplate) {
        final StrSubstitutor sub = new StrSubstitutor(values);
        if (replaceTemplate == null) {
            assertNull(sub.replace((String) null));
            assertNull(sub.replace((String) null, 0, 100));
            assertNull(sub.replace((char[]) null));
            assertNull(sub.replace((char[]) null, 0, 100));
            assertNull(sub.replace((StringBuffer) null));
            assertNull(sub.replace((StringBuffer) null, 0, 100));
            assertNull(sub.replace((StrBuilder) null));
            assertNull(sub.replace((StrBuilder) null, 0, 100));
            assertNull(sub.replace((Object) null));
            assertFalse(sub.replaceIn((StringBuffer) null));
            assertFalse(sub.replaceIn((StringBuffer) null, 0, 100));
            assertFalse(sub.replaceIn((StrBuilder) null));
            assertFalse(sub.replaceIn((StrBuilder) null, 0, 100));
        } else {
            assertEquals(replaceTemplate, sub.replace(replaceTemplate));
            final StrBuilder builder = new StrBuilder(replaceTemplate);
            assertFalse(sub.replaceIn(builder));
            assertEquals(replaceTemplate, builder.toString());
        }
    }

    private void assertReplacement(final String expectedResult, final String replaceTemplate, final boolean substring) {
        final StrSubstitutor sub = new StrSubstitutor(values);
        assertReplacement(sub, expectedResult, replaceTemplate, substring);
    }

    private void assertReplacement(final StrSubstitutor sub, final String expectedResult, final String replaceTemplate,
            final boolean substring) {
        final String expectedShortResult = expectedResult.substring(1, expectedResult.length() - 1);

        assertEquals(expectedResult, sub.replace(replaceTemplate));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(replaceTemplate, 1, replaceTemplate.length() - 2));
        }

        final char[] chars = replaceTemplate.toCharArray();
        assertEquals(expectedResult, sub.replace(chars));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(chars, 1, chars.length - 2));
        }

        StringBuffer buffer = new StringBuffer(replaceTemplate);
        assertEquals(expectedResult, sub.replace(buffer));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(buffer, 1, buffer.length() - 2));
        }

        StringBuilder stringBuilder = new StringBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(stringBuilder));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(stringBuilder, 1, stringBuilder.length() - 2));
        }

        StrBuilder strBuilder = new StrBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(strBuilder));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(strBuilder, 1, strBuilder.length() - 2));
        }

        final MutableObject<String> objectWhoseToStringReturnsTemplate = new MutableObject<>(replaceTemplate);
        assertEquals(expectedResult, sub.replace(objectWhoseToStringReturnsTemplate));

        buffer = new StringBuffer(replaceTemplate);
        assertTrue(sub.replaceIn(buffer));
        assertEquals(expectedResult, buffer.toString());
        if (substring) {
            buffer = new StringBuffer(replaceTemplate);
            assertTrue(sub.replaceIn(buffer, 1, buffer.length() - 2));
            assertEquals(expectedResult, buffer.toString());
        }

        stringBuilder = new StringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(stringBuilder));
        assertEquals(expectedResult, stringBuilder.toString());
        if (substring) {
            stringBuilder = new StringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(stringBuilder, 1, stringBuilder.length() - 2));
            assertEquals(expectedResult, stringBuilder.toString());
        }

        strBuilder = new StrBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(strBuilder));
        assertEquals(expectedResult, strBuilder.toString());
        if (substring) {
            strBuilder = new StrBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(strBuilder, 1, strBuilder.length() - 2));
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
    void testDisableSubstitutionInValues() {
        final StrSubstitutor sub = new StrSubstitutor(values);
        sub.setDisableSubstitutionInValues(true);

        values.put("animal", "${critter}");
        values.put("target", "${pet}");
        values.put("pet", "${petCharacteristic} dog");
        values.put("petCharacteristic", "lazy");
        values.put("critter", "${critterSpeed} ${critterColor} ${critterType}");
        values.put("critterSpeed", "quick");
        values.put("critterColor", "brown");
        values.put("critterType", "fox");

        assertReplacement(sub, EXPECTED_WITH_UNRESOLVED_VALUE_VARIABLES, TEMPLATE, true);
    }
}
