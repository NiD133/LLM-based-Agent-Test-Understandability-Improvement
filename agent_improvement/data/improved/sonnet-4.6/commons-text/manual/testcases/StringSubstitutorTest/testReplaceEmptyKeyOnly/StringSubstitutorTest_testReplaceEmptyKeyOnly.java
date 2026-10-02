package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that StringSubstitutor leaves an empty-key expression ("${}") unchanged,
 * because an empty variable name cannot be resolved.
 */
public class StringSubstitutorTest_testReplaceEmptyKeyOnly {

    // The expression under test: prefix + empty key + suffix = "${}"
    private static final String EMPTY_KEY_EXPR = "${}";

    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    /**
     * An empty variable name "${}" has no mapping and must be returned as-is.
     */
    @Test
    void testReplaceEmptyKeyOnly() {
        StringSubstitutor substitutor = new StringSubstitutor(values);
        assertEquals(EMPTY_KEY_EXPR, substitutor.replace(EMPTY_KEY_EXPR));
    }
}
