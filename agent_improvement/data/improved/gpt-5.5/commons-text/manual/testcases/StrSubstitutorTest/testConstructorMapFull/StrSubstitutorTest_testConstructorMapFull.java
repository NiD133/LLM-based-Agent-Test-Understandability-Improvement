package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testConstructorMapFull {

    private static final String VARIABLE_PREFIX = "<";
    private static final String VARIABLE_SUFFIX = ">";
    private static final char ESCAPE_CHARACTER = '!';
    private static final String VALUE_DELIMITER = "||";

    /**
     * Tests map-backed substitution with custom delimiters, escaping, and default values.
     */
    @Test
    void testConstructorMapFull() {
        final Map<String, String> variables = new HashMap<>();
        variables.put("name", "commons");

        StrSubstitutor substitutor = new StrSubstitutor(variables, VARIABLE_PREFIX, VARIABLE_SUFFIX, ESCAPE_CHARACTER);
        assertEquals("Hi < commons", substitutor.replace("Hi !< <name>"));

        substitutor = new StrSubstitutor(variables, VARIABLE_PREFIX, VARIABLE_SUFFIX, ESCAPE_CHARACTER, VALUE_DELIMITER);
        assertEquals("Hi < commons", substitutor.replace("Hi !< <name2||commons>"));
    }
}
