package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testToString {

    /**
     * Verifies that toString() includes the configured prefix string (quoted) in its output,
     * allowing callers to inspect the substitutor's current prefix setting.
     */
    @Test
    void testToString() {
        final StringSubstitutor s = new StringSubstitutor(null, "prefix", "suffix");
        final String str = s.toString();
        assertTrue(str.contains("\"prefix\""), "Had: " + str);
    }
}
