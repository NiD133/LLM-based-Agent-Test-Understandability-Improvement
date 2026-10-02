package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testConstructorMapPrefixSuffix {

    /**
     * Tests the 3-arg constructor that accepts a variable map plus custom prefix and suffix delimiters.
     *
     * The template "Hi $< <name>" uses angle-bracket delimiters (<...>).
     * The escape character '$' before '<' produces a literal '<' in the output,
     * while the variable reference <name> is resolved from the map to "commons".
     * Expected result: "Hi < commons".
     */
    @Test
    void testConstructorMapPrefixSuffix() {
        final Map<String, String> map = new HashMap<>();
        map.put("name", "commons");

        final StrSubstitutor sub = new StrSubstitutor(map, "<", ">");

        assertEquals("Hi < commons", sub.replace("Hi $< <name>"));
    }
}
