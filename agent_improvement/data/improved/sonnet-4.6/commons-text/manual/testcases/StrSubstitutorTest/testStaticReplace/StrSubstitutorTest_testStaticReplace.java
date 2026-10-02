package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testStaticReplace {

    /**
     * Verifies that {@link StrSubstitutor#replace(String, Map)} resolves
     * {@code ${name}} placeholders in the template using the provided map,
     * returning the fully substituted string without needing an instance.
     */
    @Test
    void testStaticReplace() {
        final Map<String, String> substitutions = new HashMap<>();
        substitutions.put("name", "commons");

        final String template = "Hi ${name}!";
        final String expected = "Hi commons!";

        assertEquals(expected, StrSubstitutor.replace(template, substitutions));
    }
}
