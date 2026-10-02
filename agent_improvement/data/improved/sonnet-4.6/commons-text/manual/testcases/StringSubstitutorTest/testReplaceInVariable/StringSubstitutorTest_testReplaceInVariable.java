package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplaceInVariable {

    private static final String ACTUAL_TARGET = "lazy dog";

    protected Map<String, String> values;

    /**
     * Asserts two CharSequences are equal, including both lengths in the failure message
     * to make it easier to spot length mismatches during debugging.
     */
    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual, () -> String.format("expected.length()=%,d, actual.length()=%,d",
                StringUtils.length(expected), StringUtils.length(actual)));
    }

    protected String replace(final StringSubstitutor substitutor, final String template) throws IOException {
        return substitutor.replace(template);
    }

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("target", ACTUAL_TARGET);
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    /**
     * Tests that variable names can themselves contain variable references (nested substitution).
     * When {@code setEnableSubstitutionInVariables(true)} is set, a template like
     * {@code ${animal.${species}}} first resolves the inner {@code ${species}} to obtain
     * the actual key suffix, then resolves the full key to get the final value.
     * Also verifies that unknown variables fall back gracefully to their {@code :-default} values.
     */
    @Test
    void testReplaceInVariable() throws IOException {
        values.put("animal.1", "fox");
        values.put("animal.2", "mouse");
        values.put("species", "2");

        final StringSubstitutor sub = new StringSubstitutor(values);
        sub.setEnableSubstitutionInVariables(true);

        // species=2 → inner ${species} resolves to "2" → full key becomes animal.2 → "mouse"
        assertEqualsCharSeq("The mouse jumps over the lazy dog.",
                replace(sub, "The ${animal.${species}} jumps over the ${target}."));

        // Change species to 1 → full key becomes animal.1 → "fox"
        values.put("species", "1");
        assertEqualsCharSeq("The fox jumps over the lazy dog.",
                replace(sub, "The ${animal.${species}} jumps over the ${target}."));

        // Unknown variables fall back to their :- defaults;
        // unknown.species:-1 → "1", unknown.animal.1:-fox → "fox", unknow.target:-lazy dog → "lazy dog"
        assertEqualsCharSeq("The fox jumps over the lazy dog.",
                replace(sub, "The ${unknown.animal.${unknown.species:-1}:-fox} jumps over the ${unknow.target:-lazy dog}."));
    }
}
