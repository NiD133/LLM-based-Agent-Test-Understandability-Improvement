package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testDetectsCyclicSubstitution {

    /**
     * A variable whose value references itself (e.g. {@code name -> "<name>"}) creates an
     * infinite substitution cycle. StringSubstitutor must detect this and throw
     * {@link IllegalStateException} rather than looping forever.
     */
    @Test
    void testDetectsCyclicSubstitution() {
        final Map<String, String> map = new HashMap<>();
        // "name" resolves to "<name>", which would trigger another lookup for "name" — a cycle.
        map.put("name", "<name>");
        assertThrows(IllegalStateException.class,
                () -> StringSubstitutor.replace("Hi <name>.", map, "<", ">"));
    }
}
