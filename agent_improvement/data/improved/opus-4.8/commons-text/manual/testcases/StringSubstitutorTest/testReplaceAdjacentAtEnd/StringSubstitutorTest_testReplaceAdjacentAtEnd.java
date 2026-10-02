package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringSubstitutor#replace(String)} when two variable expressions
 * sit directly next to each other at the end of a template.
 */
public class StringSubstitutorTest_testReplaceAdjacentAtEnd {

    /**
     * Two adjacent variables, {@code ${code}${amount}}, are each replaced and the
     * results are concatenated with nothing inserted between them.
     */
    @Test
    void testReplaceAdjacentAtEnd() throws IOException {
        final Map<String, String> values = new HashMap<>();
        values.put("code", "GBP");
        values.put("amount", "12.50");
        final StringSubstitutor substitutor = new StringSubstitutor(values);

        final String result = substitutor.replace("Amount is ${code}${amount}");

        assertEquals("Amount is GBP12.50", result);
    }
}
