package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceAdjacentAtStart {

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
     * Tests that two adjacent variables at the start of the template are both resolved correctly,
     * with no separator between them (e.g. "${code}${amount}" → "GBP12.50").
     */
    @Test
    void testReplaceAdjacentAtStart() {
        values.put("code", "GBP");
        values.put("amount", "12.50");
        final StrSubstitutor sub = new StrSubstitutor(values);
        assertEquals("GBP12.50 charged", sub.replace("${code}${amount} charged"));
    }
}
