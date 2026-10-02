package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testToString {

    private static final String VARIABLE_PREFIX = "prefix";
    private static final String VARIABLE_SUFFIX = "suffix";

    @Test
    void testToString() {
        final StringSubstitutor substitutor = new StringSubstitutor(null, VARIABLE_PREFIX, VARIABLE_SUFFIX);

        final String description = substitutor.toString();

        assertTrue(description.contains("\"prefix\""), "Had: " + description);
    }
}
