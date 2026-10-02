package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceChangedMap {

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
     * Verifies that StrSubstitutor holds a live reference to the supplied map,
     * so mutations made after construction are reflected when replace() is called.
     */
    @Test
    void testReplaceChangedMap() {
        final StrSubstitutor sub = new StrSubstitutor(values);

        // Mutate the map after the substitutor was created
        values.put("target", "moon");

        // The substitutor should use the updated value, not the original "lazy dog"
        assertEquals(
            "The quick brown fox jumps over the moon.",
            sub.replace("The ${animal} jumps over the ${target}.")
        );
    }
}
