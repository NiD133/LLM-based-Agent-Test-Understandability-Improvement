package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceInVariable {

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
     * Tests whether a variable can be replaced in a variable name.
     *
     * Scenario 1: nested variable resolves "animal.2" → "mouse" (species=2)
     * Scenario 2: changing species to 1 resolves "animal.1" → "fox"
     * Scenario 3: unknown variables fall back to their default values via ":-"
     */
    @Test
    void testReplaceInVariable() {
        values.put("animal.1", "fox");
        values.put("animal.2", "mouse");
        values.put("species", "2");

        final StrSubstitutor sub = new StrSubstitutor(values);
        sub.setEnableSubstitutionInVariables(true);

        // Scenario 1: species=2 → animal.2 → "mouse"
        final String templateWithNestedVar = "The ${animal.${species}} jumps over the ${target}.";
        assertEquals("The mouse jumps over the lazy dog.", sub.replace(templateWithNestedVar));

        // Scenario 2: species=1 → animal.1 → "fox"
        values.put("species", "1");
        assertEquals("The fox jumps over the lazy dog.", sub.replace(templateWithNestedVar));

        // Scenario 3: unknown variables use their ":-" default values
        final String templateWithDefaults = "The ${unknown.animal.${unknown.species:-1}:-fox} jumps over the ${unknow.target:-lazy dog}.";
        assertEquals("The fox jumps over the lazy dog.", sub.replace(templateWithDefaults));
    }
}
