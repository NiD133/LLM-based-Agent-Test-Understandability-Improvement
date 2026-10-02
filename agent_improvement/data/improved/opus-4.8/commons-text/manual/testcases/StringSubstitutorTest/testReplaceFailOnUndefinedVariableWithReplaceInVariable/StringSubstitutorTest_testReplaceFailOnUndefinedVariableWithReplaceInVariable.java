package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringSubstitutor} when undefined-variable failures and substitution
 * inside variable names are both enabled.
 *
 * <p>"Substitution in variables" means the variable name itself may contain a
 * placeholder. For example {@code ${animal.${species}}} first resolves
 * {@code ${species}}, then looks up the resulting composed name such as
 * {@code animal.1}.</p>
 */
public class StringSubstitutorTest_testReplaceFailOnUndefinedVariableWithReplaceInVariable {

    /** Substitution values shared by the test; rebuilt fresh in {@link #newSubstitutor()}. */
    private Map<String, String> values;

    /**
     * Builds a {@link StringSubstitutor} with the test values and the two features
     * under test enabled: throw on undefined variables, and allow substitution
     * within variable names.
     */
    private StringSubstitutor newSubstitutor() {
        values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
        values.put("animal.1", "fox");
        values.put("animal.2", "mouse");
        values.put("species", "2");
        values.put("statement.1", "2");
        values.put("recursive", "1");
        values.put("word", "variable");
        values.put("testok.2", "statement");

        final StringSubstitutor sub = new StringSubstitutor(values);
        sub.setEnableUndefinedVariableException(true);
        sub.setEnableSubstitutionInVariables(true);
        return sub;
    }

    @Test
    void testReplaceFailOnUndefinedVariableWithReplaceInVariable() throws IOException {
        final StringSubstitutor sub = newSubstitutor();

        // species=2 -> ${animal.2} -> "mouse"
        assertEquals("The mouse jumps over the lazy dog.",
                sub.replace("The ${animal.${species}} jumps over the ${target}."));

        // species=1 -> ${animal.1} -> "fox"
        values.put("species", "1");
        assertEquals("The fox jumps over the lazy dog.",
                sub.replace("The ${animal.${species}} jumps over the ${target}."));

        // ${test.${statement}} needs "statement", which is undefined (only "statement.1" exists) -> throws.
        assertEquals("Cannot resolve variable 'statement' (enableSubstitutionInVariables=true).",
                assertThrows(IllegalArgumentException.class,
                        () -> sub.replace("The ${test.${statement}} is a sample for missing ${word}."))
                        .getMessage());

        // Inner names resolve to the composed name "test.2", which is undefined -> throws.
        assertEquals("Cannot resolve variable 'test.2' (enableSubstitutionInVariables=true).",
                assertThrows(IllegalArgumentException.class,
                        () -> sub.replace("The ${test.${statement.${recursive}}} is a sample for missing ${word}."))
                        .getMessage());

        // statement.1=2, recursive=1 -> ${testok.2} -> "statement"
        assertEquals("statement", sub.replace("${testok.${statement.${recursive}}}"));

        // Leading "$$" escapes the placeholder, so only the inner names are substituted.
        assertEquals("${testok.2}", sub.replace("$${testok.${statement.${recursive}}}"));

        // word=variable, and ${testok.${statement.${recursive}}} -> "statement"
        assertEquals("The statement is a sample for missing variable.",
                sub.replace("The ${testok.${statement.${recursive}}} is a sample for missing ${word}."));
    }
}
