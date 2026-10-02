package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceNoPrefixNoSuffix {

    private static final String TEMPLATE = "The animal jumps over the ${target}.";
    private static final String FULLY_REPLACED = "The animal jumps over the lazy dog.";
    private static final String SUBSTRING_REPLACED = "he animal jumps over the lazy dog";

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
     * Tests when no prefix or suffix.
     */
    @Test
    void testReplaceNoPrefixNoSuffix() {
        final StrSubstitutor substitutor = new StrSubstitutor(values);

        assertReplaceResultForEverySourceType(substitutor);
        assertReplaceInResultForEveryMutableSourceType(substitutor);
    }

    private void assertReplaceResultForEverySourceType(final StrSubstitutor substitutor) {
        assertEquals(FULLY_REPLACED, substitutor.replace(TEMPLATE));
        assertEquals(SUBSTRING_REPLACED, substitutor.replace(TEMPLATE, 1, TEMPLATE.length() - 2));

        final char[] chars = TEMPLATE.toCharArray();
        assertEquals(FULLY_REPLACED, substitutor.replace(chars));
        assertEquals(SUBSTRING_REPLACED, substitutor.replace(chars, 1, chars.length - 2));

        final StringBuffer buffer = new StringBuffer(TEMPLATE);
        assertEquals(FULLY_REPLACED, substitutor.replace(buffer));
        assertEquals(SUBSTRING_REPLACED, substitutor.replace(buffer, 1, buffer.length() - 2));

        final StringBuilder builder = new StringBuilder(TEMPLATE);
        assertEquals(FULLY_REPLACED, substitutor.replace(builder));
        assertEquals(SUBSTRING_REPLACED, substitutor.replace(builder, 1, builder.length() - 2));

        final StrBuilder strBuilder = new StrBuilder(TEMPLATE);
        assertEquals(FULLY_REPLACED, substitutor.replace(strBuilder));
        assertEquals(SUBSTRING_REPLACED, substitutor.replace(strBuilder, 1, strBuilder.length() - 2));

        final MutableObject<String> objectWhoseToStringReturnsTemplate = new MutableObject<>(TEMPLATE);
        assertEquals(FULLY_REPLACED, substitutor.replace(objectWhoseToStringReturnsTemplate));
    }

    private void assertReplaceInResultForEveryMutableSourceType(final StrSubstitutor substitutor) {
        StringBuffer buffer = new StringBuffer(TEMPLATE);
        assertTrue(substitutor.replaceIn(buffer));
        assertEquals(FULLY_REPLACED, buffer.toString());

        buffer = new StringBuffer(TEMPLATE);
        assertTrue(substitutor.replaceIn(buffer, 1, buffer.length() - 2));
        assertEquals(FULLY_REPLACED, buffer.toString());

        StringBuilder builder = new StringBuilder(TEMPLATE);
        assertTrue(substitutor.replaceIn(builder));
        assertEquals(FULLY_REPLACED, builder.toString());

        builder = new StringBuilder(TEMPLATE);
        assertTrue(substitutor.replaceIn(builder, 1, builder.length() - 2));
        assertEquals(FULLY_REPLACED, builder.toString());

        StrBuilder strBuilder = new StrBuilder(TEMPLATE);
        assertTrue(substitutor.replaceIn(strBuilder));
        assertEquals(FULLY_REPLACED, strBuilder.toString());

        strBuilder = new StrBuilder(TEMPLATE);
        assertTrue(substitutor.replaceIn(strBuilder, 1, strBuilder.length() - 2));
        assertEquals(FULLY_REPLACED, strBuilder.toString());
    }
}
