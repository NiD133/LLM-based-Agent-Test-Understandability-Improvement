package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link StrSubstitutor}'s ability to resolve a variable whose name is
 * itself produced by substituting another (nested) variable.
 */
public class StrSubstitutorTest_testReplaceInVariable {

    /** Variables made available to the substitutor under test. */
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
     * Verifies that a variable reference can appear inside another variable's
     * name (e.g. {@code ${animal.${species}}}) once substitution-in-variables
     * is enabled, including the case where a nested lookup falls back to a
     * default value.
     */
    @Test
    void testReplaceInVariable() {
        values.put("animal.1", "fox");
        values.put("animal.2", "mouse");
        values.put("species", "2");

        final StrSubstitutor sub = new StrSubstitutor(values);
        sub.setEnableSubstitutionInVariables(true);

        // "species" resolves to 2, so the name becomes "animal.2" -> "mouse".
        assertEquals("The mouse jumps over the lazy dog.",
                sub.replace("The ${animal.${species}} jumps over the ${target}."));

        // Changing "species" to 1 makes the name "animal.1" -> "fox".
        values.put("species", "1");
        assertEquals("The fox jumps over the lazy dog.",
                sub.replace("The ${animal.${species}} jumps over the ${target}."));

        // Unknown variables fall back to their ":-" default values.
        assertEquals("The fox jumps over the lazy dog.",
                sub.replace("The ${unknown.animal.${unknown.species:-1}:-fox} "
                        + "jumps over the ${unknow.target:-lazy dog}."));
    }
}
