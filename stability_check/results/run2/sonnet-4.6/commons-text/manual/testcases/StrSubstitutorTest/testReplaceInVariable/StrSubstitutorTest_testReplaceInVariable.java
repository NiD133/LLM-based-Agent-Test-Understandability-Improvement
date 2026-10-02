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
     * Tests whether a variable reference can be dynamically resolved when the variable
     * name itself contains another variable substitution.
     *
     * For example, "${animal.${species}}" first resolves "species" to "2", producing
     * "animal.2", and then resolves "animal.2" to the final value.
     */
    @Test
    void testReplaceInVariable() {
        // Set up two animal variants and a "species" selector variable
        values.put("animal.1", "fox");
        values.put("animal.2", "mouse");
        values.put("species", "2");

        final StrSubstitutor sub = new StrSubstitutor(values);
        // Enable resolution of variables inside variable names
        sub.setEnableSubstitutionInVariables(true);

        // With species=2, "${animal.${species}}" resolves to "${animal.2}" → "mouse"
        String templateWithSpecies2 = "The ${animal.${species}} jumps over the ${target}.";
        assertEquals("The mouse jumps over the lazy dog.", sub.replace(templateWithSpecies2));

        // Change species to 1 so that "${animal.${species}}" now resolves to "${animal.1}" → "fox"
        values.put("species", "1");
        String templateWithSpecies1 = "The ${animal.${species}} jumps over the ${target}.";
        assertEquals("The fox jumps over the lazy dog.", sub.replace(templateWithSpecies1));

        // When all outer variables are unknown, fall back to defaults embedded in the template.
        // "${unknown.animal.${unknown.species:-1}:-fox}" resolves "unknown.species" via default "1",
        // making the outer key "unknown.animal.1" which is also unknown, so the outer default "fox" is used.
        // "${unknow.target:-lazy dog}" is unknown, so falls back to "lazy dog".
        String templateWithDefaultFallbacks =
                "The ${unknown.animal.${unknown.species:-1}:-fox} jumps over the ${unknow.target:-lazy dog}.";
        assertEquals("The fox jumps over the lazy dog.", sub.replace(templateWithDefaultFallbacks));
    }
}
