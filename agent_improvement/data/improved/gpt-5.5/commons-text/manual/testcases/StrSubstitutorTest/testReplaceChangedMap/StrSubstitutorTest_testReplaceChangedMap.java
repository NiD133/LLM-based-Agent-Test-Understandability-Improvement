package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceChangedMap {

    private static final String ANIMAL_KEY = "animal";
    private static final String INITIAL_TARGET_KEY = "target";
    private static final String TEMPLATE = "The ${animal} jumps over the ${target}.";

    private Map<String, String> values;

    @BeforeEach
    public void setUp() throws Exception {
        values = new HashMap<>();
        values.put(ANIMAL_KEY, "quick brown fox");
        values.put(INITIAL_TARGET_KEY, "lazy dog");
    }

    @AfterEach
    public void tearDown() throws Exception {
        values = null;
    }

    /**
     * Tests key replace changing map after initialization (not recommended).
     */
    @Test
    void testReplaceChangedMap() {
        final StrSubstitutor sub = new StrSubstitutor(values);

        values.put(INITIAL_TARGET_KEY, "moon");

        assertEquals("The quick brown fox jumps over the moon.", sub.replace(TEMPLATE));
    }
}
