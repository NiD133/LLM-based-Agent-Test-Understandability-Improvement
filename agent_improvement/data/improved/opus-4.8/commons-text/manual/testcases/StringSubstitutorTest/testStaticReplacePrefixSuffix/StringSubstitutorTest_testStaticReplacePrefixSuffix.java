package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Tests the static {@link StringSubstitutor#replace(Object, Map, String, String)}
 * overload that accepts custom variable prefix and suffix delimiters.
 */
public class StringSubstitutorTest_testStaticReplacePrefixSuffix {

    /**
     * Verifies that the static replace helper substitutes variables when the
     * caller supplies non-default prefix ("&lt;") and suffix ("&gt;") delimiters.
     */
    @Test
    void testStaticReplacePrefixSuffix() {
        final Map<String, String> variables = new HashMap<>();
        variables.put("name", "commons");

        final String template = "Hi <name>!";
        final String prefix = "<";
        final String suffix = ">";

        final String result = StringSubstitutor.replace(template, variables, prefix, suffix);

        assertEquals("Hi commons!", result);
    }
}
