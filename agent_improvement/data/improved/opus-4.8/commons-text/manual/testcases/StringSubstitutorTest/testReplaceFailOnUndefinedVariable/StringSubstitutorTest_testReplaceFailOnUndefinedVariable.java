package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StringSubstitutorTest_testReplaceFailOnUndefinedVariable {

    private static final String ANIMAL = "quick brown fox";
    private static final String TARGET = "lazy dog";

    /** Variable name-to-value mappings used to build the substitutor under test. */
    private Map<String, String> values;

    @BeforeEach
    void setUp() {
        values = new HashMap<>();
        values.put("animal", ANIMAL);
        values.put("target", TARGET);
    }

    /**
     * When undefined-variable exceptions are enabled, a template that references an unresolvable
     * variable must throw {@link IllegalArgumentException}; templates that supply a default value
     * for the missing variable must still resolve successfully.
     */
    @Test
    void testReplaceFailOnUndefinedVariable() throws IOException {
        values.put("animal.1", "fox");
        values.put("animal.2", "mouse");
        values.put("species", "2");

        final StringSubstitutor sub = new StringSubstitutor(values);
        sub.setEnableUndefinedVariableException(true);

        // Nested variable references are not substituted (enableSubstitutionInVariables=false),
        // so the inner "${species}" stays literal and the outer variable cannot be resolved.
        assertEquals(
            "Cannot resolve variable 'animal.${species' (enableSubstitutionInVariables=false).",
            assertThrows(IllegalArgumentException.class,
                () -> sub.replace("The ${animal.${species}} jumps over the ${target}."))
                .getMessage());

        assertEquals(
            "Cannot resolve variable 'animal.${species:-1' (enableSubstitutionInVariables=false).",
            assertThrows(IllegalArgumentException.class,
                () -> sub.replace("The ${animal.${species:-1}} jumps over the ${target}."))
                .getMessage());

        // "${unknown}" has no value and no default, so resolution fails.
        assertEquals(
            "Cannot resolve variable 'unknown' (enableSubstitutionInVariables=false).",
            assertThrows(IllegalArgumentException.class,
                () -> sub.replace("The ${test:-statement} is a sample for missing ${unknown}."))
                .getMessage());

        // When a default value is provided, the undefined variable is replaced instead of throwing.
        assertEquals("The statement is a sample for missing variable.",
            sub.replace("The ${test:-statement} is a sample for missing ${unknown:-variable}."));

        // A defined variable resolves normally even with the exception enabled.
        assertEquals("The fox jumps over the lazy dog.",
            sub.replace("The ${animal.1} jumps over the ${target}."));
    }
}
