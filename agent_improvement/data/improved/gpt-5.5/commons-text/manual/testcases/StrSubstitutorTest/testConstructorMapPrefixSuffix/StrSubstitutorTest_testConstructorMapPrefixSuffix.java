package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testConstructorMapPrefixSuffix {

    @Test
    void testConstructorMapPrefixSuffix() {
        final Map<String, String> variableValues = new HashMap<>();
        variableValues.put("name", "commons");

        final StrSubstitutor substitutor = new StrSubstitutor(variableValues, "<", ">");

        assertEquals("Hi < commons", substitutor.replace("Hi $< <name>"));
    }
}
