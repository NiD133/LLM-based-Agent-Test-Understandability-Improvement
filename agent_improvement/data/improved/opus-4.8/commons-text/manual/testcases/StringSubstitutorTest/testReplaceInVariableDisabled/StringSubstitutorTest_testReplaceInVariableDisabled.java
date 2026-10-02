package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StringSubstitutor} does NOT substitute variables nested inside another
 * variable name by default (i.e. variable-name substitution is disabled unless explicitly enabled).
 */
public class StringSubstitutorTest_testReplaceInVariableDisabled {

    /** Variable values shared by every test, keyed by variable name. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    /**
     * Tests that substitution inside variable names is disabled per default.
     *
     * <p>When the inner {@code ${species}} reference would be needed to build the outer variable
     * name {@code animal.<species>}, the substitutor must leave the whole nested expression
     * untouched. Only the standalone {@code ${target}} variable is resolved.</p>
     */
    @Test
    void testReplaceInVariableDisabled() throws IOException {
        // Variables that would only be reachable if nested variable-name substitution were enabled.
        values.put("animal.1", "fox");
        values.put("animal.2", "mouse");
        values.put("species", "2");

        final StringSubstitutor substitutor = new StringSubstitutor(values);

        // The nested ${species} reference is left as-is, so the whole ${animal.${species}} stays
        // literal; only the top-level ${target} variable is replaced.
        assertEquals(
                "The ${animal.${species}} jumps over the lazy dog.",
                substitutor.replace("The ${animal.${species}} jumps over the ${target}."));

        // Same expectation when the nested reference uses a default-value expression (${species:-1}).
        assertEquals(
                "The ${animal.${species:-1}} jumps over the lazy dog.",
                substitutor.replace("The ${animal.${species:-1}} jumps over the ${target}."));
    }
}
