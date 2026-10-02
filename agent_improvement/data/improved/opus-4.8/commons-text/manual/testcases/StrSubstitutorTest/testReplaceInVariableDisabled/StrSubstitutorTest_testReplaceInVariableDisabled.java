package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceInVariableDisabled {

    /** Variable name -> value pairs available to the substitutor. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    /**
     * By default, StrSubstitutor does not resolve variables that appear *inside*
     * another variable's name. So a nested reference such as
     * {@code ${animal.${species}}} is left untouched, while a plain top-level
     * variable such as {@code ${target}} is still replaced.
     */
    @Test
    void testReplaceInVariableDisabled() {
        values.put("animal.1", "fox");
        values.put("animal.2", "mouse");
        values.put("species", "2");
        final StrSubstitutor sub = new StrSubstitutor(values);

        // The nested ${species} is NOT resolved, so ${animal.${species}} stays as-is;
        // only the top-level ${target} is replaced with "lazy dog".
        assertEquals(
                "The ${animal.${species}} jumps over the lazy dog.",
                sub.replace("The ${animal.${species}} jumps over the ${target}."));

        // Same behaviour when the nested variable carries a default value (:-1).
        assertEquals(
                "The ${animal.${species:-1}} jumps over the lazy dog.",
                sub.replace("The ${animal.${species:-1}} jumps over the ${target}."));
    }
}
