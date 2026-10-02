package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testConstructorMapPrefixSuffix {

    /**
     * Verifies the {@link StrSubstitutor#StrSubstitutor(Map, String, String)} constructor:
     * variables delimited by the custom prefix {@code "<"} and suffix {@code ">"} are resolved
     * from the supplied map, while any text that does not form a complete variable reference is
     * left untouched.
     */
    @Test
    void testConstructorMapPrefixSuffix() {
        final Map<String, String> variables = new HashMap<>();
        variables.put("name", "commons");

        final StrSubstitutor substitutor = new StrSubstitutor(variables, "<", ">");

        // "<name>" is resolved to "commons"; the leading "$< " is not a valid reference and is kept as-is.
        assertEquals("Hi < commons", substitutor.replace("Hi $< <name>"));
    }
}
