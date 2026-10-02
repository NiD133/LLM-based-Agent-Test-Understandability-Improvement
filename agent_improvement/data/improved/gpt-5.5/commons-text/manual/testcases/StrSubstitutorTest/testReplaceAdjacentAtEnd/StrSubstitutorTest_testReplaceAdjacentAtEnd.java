package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceAdjacentAtEnd {

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
     * Tests adjacent keys.
     */
    @Test
    void testReplaceAdjacentAtEnd() {
        values.put("code", "GBP");
        values.put("amount", "12.50");

        final StrSubstitutor substitutor = new StrSubstitutor(values);

        assertEquals("Amount is GBP12.50", substitutor.replace("Amount is ${code}${amount}"));
    }
}
