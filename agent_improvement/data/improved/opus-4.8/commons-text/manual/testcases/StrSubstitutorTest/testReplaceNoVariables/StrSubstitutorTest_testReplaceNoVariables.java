package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link StrSubstitutor} leaves a template unchanged when it
 * contains no variable placeholders.
 */
public class StrSubstitutorTest_testReplaceNoVariables {

    /** A template that contains no {@code ${...}} placeholders. */
    private static final String TEMPLATE_WITHOUT_VARIABLES = "The balloon arrived.";

    /** Values available for substitution; none of them are referenced by the template. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    @Test
    void testReplaceNoVariables() {
        final StrSubstitutor substitutor = new StrSubstitutor(values);

        // replace(String) returns the template untouched.
        assertEquals(TEMPLATE_WITHOUT_VARIABLES, substitutor.replace(TEMPLATE_WITHOUT_VARIABLES));

        // replaceIn(StrBuilder) reports no change and leaves the builder content untouched.
        final StrBuilder builder = new StrBuilder(TEMPLATE_WITHOUT_VARIABLES);
        assertFalse(substitutor.replaceIn(builder));
        assertEquals(TEMPLATE_WITHOUT_VARIABLES, builder.toString());
    }
}
