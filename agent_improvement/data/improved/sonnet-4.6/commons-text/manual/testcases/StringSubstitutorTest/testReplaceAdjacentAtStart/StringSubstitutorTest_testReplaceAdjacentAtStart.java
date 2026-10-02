package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testReplaceAdjacentAtStart {

    private Map<String, String> values;

    @BeforeEach
    public void setUp() {
        values = new HashMap<>();
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    /**
     * Two adjacent variable placeholders at the very start of the string (no
     * separator between them) must both be resolved in order.
     */
    @Test
    void testReplaceAdjacentAtStart() {
        values.put("code", "GBP");
        values.put("amount", "12.50");
        final StringSubstitutor sub = new StringSubstitutor(values);
        assertEquals("GBP12.50 charged", sub.replace("${code}${amount} charged"));
    }
}
