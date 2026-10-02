package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link StrSubstitutor} when substitution inside variable names is enabled
 * via {@link StrSubstitutor#setEnableSubstitutionInVariables(boolean)}.
 *
 * <p>With that flag turned on, a {@code ${...}} expression nested inside another
 * variable name is resolved first, so the inner result becomes part of the outer
 * variable name that is then looked up.</p>
 */
public class StrSubstitutorTest_testReplaceInVariable {

    /** Variables made available to the substitutor under test. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    /**
     * Verifies that a variable can itself be replaced inside another variable's name.
     */
    @Test
    void testReplaceInVariable() {
        // Variables whose names ("animal.1", "animal.2") are selected dynamically,
        // plus "species" which chooses the suffix of that name.
        values.put("animal.1", "fox");
        values.put("animal.2", "mouse");
        values.put("species", "2");

        final StrSubstitutor sub = new StrSubstitutor(values);
        sub.setEnableSubstitutionInVariables(true);

        // species=2 -> outer variable name resolves to "animal.2" -> "mouse".
        assertEquals(
                "The mouse jumps over the lazy dog.",
                sub.replace("The ${animal.${species}} jumps over the ${target}."));

        // Switching species to 1 makes the same template resolve to "animal.1" -> "fox".
        values.put("species", "1");
        assertEquals(
                "The fox jumps over the lazy dog.",
                sub.replace("The ${animal.${species}} jumps over the ${target}."));

        // Unknown variables fall back to their ":-" default values, including a
        // nested default ("${unknown.species:-1}") used while building the name.
        assertEquals(
                "The fox jumps over the lazy dog.",
                sub.replace("The ${unknown.animal.${unknown.species:-1}:-fox} "
                        + "jumps over the ${unknow.target:-lazy dog}."));
    }
}
