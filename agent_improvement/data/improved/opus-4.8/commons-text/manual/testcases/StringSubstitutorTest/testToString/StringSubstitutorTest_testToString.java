package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringSubstitutor#toString()}.
 */
public class StringSubstitutorTest_testToString {

    /**
     * Verifies that {@link StringSubstitutor#toString()} includes the configured
     * variable prefix in its output.
     */
    @Test
    void testToString() {
        // Build a substitutor with a known prefix ("prefix") and suffix ("suffix").
        final StringSubstitutor substitutor = new StringSubstitutor(null, "prefix", "suffix");

        final String description = substitutor.toString();

        // The string representation should quote and report the prefix we set.
        assertTrue(description.contains("\"prefix\""), "Had: " + description);
    }
}
