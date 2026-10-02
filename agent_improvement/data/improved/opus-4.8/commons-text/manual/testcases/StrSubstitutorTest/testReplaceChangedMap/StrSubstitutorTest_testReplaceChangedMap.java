package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceChangedMap {

    /** Backing map of variables shared with the substitutor under test. */
    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    /**
     * Verifies that a StrSubstitutor keeps a live reference to its source map:
     * mutating the map after construction (not recommended in practice) is
     * reflected by later replace() calls.
     */
    @Test
    void testReplaceChangedMap() {
        final StrSubstitutor sub = new StrSubstitutor(values);

        // Change a value in the map after the substitutor was built.
        values.put("target", "moon");

        // The substitutor should resolve "${target}" to the updated value.
        final String result = sub.replace("The ${animal} jumps over the ${target}.");
        assertEquals("The quick brown fox jumps over the moon.", result);
    }
}
