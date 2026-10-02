package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link StrSubstitutor} resolves two variables placed directly next
 * to each other at the end of a template, with no separator between them.
 */
public class StrSubstitutorTest_testReplaceAdjacentAtEnd {

    @Test
    void testReplaceAdjacentAtEnd() {
        // Two variables ("code" and "amount") sit back-to-back at the end of the template.
        final Map<String, String> values = new HashMap<>();
        values.put("code", "GBP");
        values.put("amount", "12.50");

        final StrSubstitutor substitutor = new StrSubstitutor(values);

        final String result = substitutor.replace("Amount is ${code}${amount}");

        assertEquals("Amount is GBP12.50", result);
    }
}
