package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceInVariableRecursive {

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
     * Tests three-level recursive substitution in variable names.
     *
     * Variable lookup chain for the known-color case:
     *   ${color}          → "white"
     *   ${species.white}  → "1"
     *   ${animal.1}       → "white mouse"
     *
     * Variable lookup chain for the unknown-color case (uses default value "brown"):
     *   ${unknownColor:-brown} → "brown"  (key absent, default "brown" is used)
     *   ${species.brown}       → "2"
     *   ${animal.2}            → "brown fox"
     */
    @Test
    void testReplaceInVariableRecursive() {
        values.put("animal.2", "brown fox");
        values.put("animal.1", "white mouse");
        values.put("color", "white");
        values.put("species.white", "1");
        values.put("species.brown", "2");

        final StrSubstitutor sub = new StrSubstitutor(values);
        // Enable substitution inside variable names so nested ${...} expressions are resolved
        sub.setEnableSubstitutionInVariables(true);

        // color → "white" → species.white → "1" → animal.1 → "white mouse"
        assertEquals(
                "The white mouse jumps over the lazy dog.",
                sub.replace("The ${animal.${species.${color}}} jumps over the ${target}."));

        // unknownColor is absent so the default "brown" applies:
        // unknownColor:-brown → "brown" → species.brown → "2" → animal.2 → "brown fox"
        assertEquals(
                "The brown fox jumps over the lazy dog.",
                sub.replace("The ${animal.${species.${unknownColor:-brown}}} jumps over the ${target}."));
    }
}
