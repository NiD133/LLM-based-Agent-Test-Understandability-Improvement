package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceInVariable {

    /** Base lookup values shared by every test. */
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
     * Tests whether a variable can be replaced inside a variable name, i.e. nested
     * substitution such as {@code ${animal.${species}}}.
     */
    @Test
    void testReplaceInVariable() {
        // Add entries so that the outer variable name is itself built from a variable.
        values.put("animal.1", "fox");
        values.put("animal.2", "mouse");
        values.put("species", "2");

        final StrSubstitutor sub = new StrSubstitutor(values);
        sub.setEnableSubstitutionInVariables(true);

        // species=2 -> resolves the variable name to "animal.2" -> "mouse".
        assertEquals(
                "The mouse jumps over the lazy dog.",
                sub.replace("The ${animal.${species}} jumps over the ${target}."));

        // Change species to 1 -> resolves to "animal.1" -> "fox".
        values.put("species", "1");
        assertEquals(
                "The fox jumps over the lazy dog.",
                sub.replace("The ${animal.${species}} jumps over the ${target}."));

        // Unknown variables fall back to their default values (the ":-default" syntax),
        // including a default used while building a nested variable name.
        assertEquals(
                "The fox jumps over the lazy dog.",
                sub.replace("The ${unknown.animal.${unknown.species:-1}:-fox} "
                        + "jumps over the ${unknow.target:-lazy dog}."));
    }
}
