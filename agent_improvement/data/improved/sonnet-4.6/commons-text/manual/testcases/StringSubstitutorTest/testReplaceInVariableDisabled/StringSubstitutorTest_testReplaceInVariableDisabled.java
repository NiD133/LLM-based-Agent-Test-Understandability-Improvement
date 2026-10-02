package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplaceInVariableDisabled {

    private static final String ACTUAL_ANIMAL = "quick brown fox";
    private static final String ACTUAL_TARGET = "lazy dog";

    protected Map<String, String> values;

    /**
     * Hook for subclasses to wrap or replace the substitution call.
     */
    protected String replace(final StringSubstitutor stringSubstitutor, final String template) throws IOException {
        return stringSubstitutor.replace(template);
    }

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("animal", ACTUAL_ANIMAL);
        values.put("target", ACTUAL_TARGET);
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    /**
     * Tests that variable-name substitution is disabled by default.
     * <p>
     * When a template contains a nested variable expression such as
     * {@code ${animal.${species}}}, the substitutor does NOT resolve the
     * inner {@code ${species}} first and then use the result as part of
     * the outer variable name.  Instead, the entire outer expression is
     * left unchanged.  Simple (non-nested) variables such as {@code ${target}}
     * are still resolved normally.
     * </p>
     */
    @Test
    void testReplaceInVariableDisabled() throws IOException {
        values.put("animal.1", "fox");
        values.put("animal.2", "mouse");
        values.put("species", "2");
        final StringSubstitutor sub = new StringSubstitutor(values);

        // ${target} resolves to "lazy dog", but the nested ${animal.${species}} is left as-is.
        assertEquals(
            "The ${animal.${species}} jumps over the lazy dog.",
            replace(sub, "The ${animal.${species}} jumps over the ${target}.")
        );

        // Same expectation when the nested variable carries a default-value delimiter (:-).
        assertEquals(
            "The ${animal.${species:-1}} jumps over the lazy dog.",
            replace(sub, "The ${animal.${species:-1}} jumps over the ${target}.")
        );
    }
}
