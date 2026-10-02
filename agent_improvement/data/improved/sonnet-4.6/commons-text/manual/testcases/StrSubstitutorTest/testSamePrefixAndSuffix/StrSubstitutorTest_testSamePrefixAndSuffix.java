package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Tests that StrSubstitutor correctly handles variable substitution when the
 * prefix and suffix delimiters are the same character (e.g. "@variable@").
 */
public class StrSubstitutorTest_testSamePrefixAndSuffix {

    @Test
    void testSamePrefixAndSuffix() {
        final Map<String, String> variables = new HashMap<>();
        variables.put("greeting", "Hello");
        variables.put(" there ", "XXX");
        variables.put("name", "commons");

        // Single variable: "@name@" should be replaced by "commons"
        assertEquals("Hi commons!",
                StrSubstitutor.replace("Hi @name@!", variables, "@", "@"));

        // Multiple variables in one template; " there " is a key but its surrounding
        // spaces are not delimited, so it stays literal and only @greeting@ and @name@
        // are substituted.
        assertEquals("Hello there commons!",
                StrSubstitutor.replace("@greeting@ there @name@!", variables, "@", "@"));
    }
}
