package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StrSubstitutor} correctly resolves two variables that
 * sit directly next to each other at the very start of a template, with no
 * separating text between them.
 */
public class StrSubstitutorTest_testReplaceAdjacentAtStart {

    /**
     * When a template begins with two adjacent {@code ${...}} placeholders,
     * each is replaced by its mapped value and the results are concatenated.
     */
    @Test
    void testReplaceAdjacentAtStart() {
        // Given: a substitutor whose variables include the two adjacent keys.
        final Map<String, String> values = new HashMap<>();
        values.put("code", "GBP");
        values.put("amount", "12.50");
        final StrSubstitutor sub = new StrSubstitutor(values);

        // When: the template starts with "${code}${amount}" back-to-back.
        final String result = sub.replace("${code}${amount} charged");

        // Then: both placeholders resolve and concatenate, with trailing text kept.
        assertEquals("GBP12.50 charged", result);
    }
}
