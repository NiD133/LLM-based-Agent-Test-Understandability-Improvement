package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceInVariableDisabled {

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
     * Tests whether substitution in variable names is disabled per default.
     * When a variable like ${animal.${species}} is encountered, the inner
     * ${species} should NOT be resolved because variable-in-variable substitution
     * is off by default. Only simple variable names like ${target} are resolved.
     */
    @Test
    void testReplaceInVariableDisabled() {
        values.put("animal.1", "fox");
        values.put("animal.2", "mouse");
        values.put("species", "2");
        final StrSubstitutor sub = new StrSubstitutor(values);

        // ${animal.${species}} stays unresolved; only ${target} is substituted
        final String templateWithNestedVar = "The ${animal.${species}} jumps over the ${target}.";
        final String expectedWithNestedVar  = "The ${animal.${species}} jumps over the lazy dog.";
        assertEquals(expectedWithNestedVar, sub.replace(templateWithNestedVar));

        // Same behaviour when the inner variable includes a default value (:-1)
        final String templateWithDefault = "The ${animal.${species:-1}} jumps over the ${target}.";
        final String expectedWithDefault  = "The ${animal.${species:-1}} jumps over the lazy dog.";
        assertEquals(expectedWithDefault, sub.replace(templateWithDefault));
    }
}
