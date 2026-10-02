package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceInVariableRecursive {

    /** Variable name to value mappings shared by each test. */
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
     * Tests complex and recursive substitution in variable names.
     *
     * <p>With substitution-in-variables enabled, a variable name may itself
     * contain variables. The inner variables are resolved first, building up
     * the name of the outer variable to look up. For example, in
     * {@code ${animal.${species.${color}}}} the lookups resolve from the
     * inside out:</p>
     * <ol>
     *   <li>{@code ${color}} -> "white"</li>
     *   <li>{@code ${species.white}} -> "1"</li>
     *   <li>{@code ${animal.1}} -> "white mouse"</li>
     * </ol>
     */
    @Test
    void testReplaceInVariableRecursive() {
        values.put("animal.1", "white mouse");
        values.put("animal.2", "brown fox");
        values.put("color", "white");
        values.put("species.white", "1");
        values.put("species.brown", "2");

        final StrSubstitutor sub = new StrSubstitutor(values);
        sub.setEnableSubstitutionInVariables(true);

        // color -> white, species.white -> 1, animal.1 -> white mouse
        assertEquals(
                "The white mouse jumps over the lazy dog.",
                sub.replace("The ${animal.${species.${color}}} jumps over the ${target}."));

        // unknownColor is undefined, so the ":-brown" default applies:
        // species.brown -> 2, animal.2 -> brown fox
        assertEquals(
                "The brown fox jumps over the lazy dog.",
                sub.replace("The ${animal.${species.${unknownColor:-brown}}} jumps over the ${target}."));
    }
}
