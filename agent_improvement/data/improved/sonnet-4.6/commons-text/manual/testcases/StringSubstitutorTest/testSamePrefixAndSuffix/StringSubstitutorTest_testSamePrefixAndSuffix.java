package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Verifies that StringSubstitutor correctly handles the case where the variable
 * prefix and suffix are the same character (e.g., @variable@ instead of ${variable}).
 */
public class StringSubstitutorTest_testSamePrefixAndSuffix {

    @Test
    void testSamePrefixAndSuffix() {
        final Map<String, String> map = new HashMap<>();
        map.put("greeting", "Hello");
        // " there " is a valid map key but must NOT be substituted: it has no @ delimiters in the template
        map.put(" there ", "XXX");
        map.put("name", "commons");

        // Single variable: @name@ -> "commons"
        assertEquals("Hi commons!",
                StringSubstitutor.replace("Hi @name@!", map, "@", "@"));

        // Multiple variables with literal text between them:
        // @greeting@ -> "Hello", literal " there " is unchanged, @name@ -> "commons"
        assertEquals("Hello there commons!",
                StringSubstitutor.replace("@greeting@ there @name@!", map, "@", "@"));
    }
}
