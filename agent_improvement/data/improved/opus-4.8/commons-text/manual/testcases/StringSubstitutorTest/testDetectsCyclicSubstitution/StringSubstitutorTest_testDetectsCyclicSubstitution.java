package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testDetectsCyclicSubstitution {

    /**
     * A substitution is cyclic when resolving a variable reproduces a reference to
     * that same variable, e.g. {@code name -> <name>}. Expanding it would never
     * terminate, so {@link StringSubstitutor} must reject it with an
     * {@link IllegalStateException} rather than loop forever.
     */
    @Test
    void testDetectsCyclicSubstitution() {
        // "name" resolves to "<name>", which references "name" again -> a cycle.
        final Map<String, String> cyclicValues = new HashMap<>();
        cyclicValues.put("name", "<name>");

        final String template = "Hi <name>.";

        assertThrows(IllegalStateException.class,
            () -> StringSubstitutor.replace(template, cyclicValues, "<", ">"));
    }
}
