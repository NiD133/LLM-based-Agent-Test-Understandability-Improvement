package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceInVariableDisabled {

    private static final String TEMPLATE_WITH_NESTED_VARIABLE_NAME =
            "The ${animal.${species}} jumps over the ${target}.";
    private static final String TEMPLATE_WITH_NESTED_VARIABLE_NAME_AND_DEFAULT =
            "The ${animal.${species:-1}} jumps over the ${target}.";
    private static final String EXPECTED_WITH_UNRESOLVED_NESTED_VARIABLE_NAME =
            "The ${animal.${species}} jumps over the lazy dog.";
    private static final String EXPECTED_WITH_UNRESOLVED_NESTED_VARIABLE_NAME_AND_DEFAULT =
            "The ${animal.${species:-1}} jumps over the lazy dog.";

    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    /**
     * Tests whether substitution in variable names is disabled per default.
     */
    @Test
    void testReplaceInVariableDisabled() {
        values.put("animal.1", "fox");
        values.put("animal.2", "mouse");
        values.put("species", "2");

        final StrSubstitutor sub = new StrSubstitutor(values);

        assertEquals(
                EXPECTED_WITH_UNRESOLVED_NESTED_VARIABLE_NAME,
                sub.replace(TEMPLATE_WITH_NESTED_VARIABLE_NAME));
        assertEquals(
                EXPECTED_WITH_UNRESOLVED_NESTED_VARIABLE_NAME_AND_DEFAULT,
                sub.replace(TEMPLATE_WITH_NESTED_VARIABLE_NAME_AND_DEFAULT));
    }
}
