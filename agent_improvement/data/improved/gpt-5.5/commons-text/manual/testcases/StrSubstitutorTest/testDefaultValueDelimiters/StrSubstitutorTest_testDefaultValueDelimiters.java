package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testDefaultValueDelimiters {

    private static final String EXPECTED_WITH_DEFAULT_VALUE = "The fox jumps over the lazy dog. 1234567890.";
    private static final String EXPECTED_WITH_UNRESOLVED_DEFAULT_VALUE =
            "The fox jumps over the lazy dog. ${undefined.number!1234567890}.";

    @Test
    void testDefaultValueDelimiters() {
        final Map<String, String> map = new HashMap<>();
        map.put("animal", "fox");
        map.put("target", "dog");

        StrSubstitutor sub = new StrSubstitutor(map, "${", "}", '$');
        assertEquals(EXPECTED_WITH_DEFAULT_VALUE,
                sub.replace("The ${animal} jumps over the lazy ${target}. ${undefined.number:-1234567890}."));

        sub = new StrSubstitutor(map, "${", "}", '$', "?:");
        assertEquals(EXPECTED_WITH_DEFAULT_VALUE,
                sub.replace("The ${animal} jumps over the lazy ${target}. ${undefined.number?:1234567890}."));

        sub = new StrSubstitutor(map, "${", "}", '$', "||");
        assertEquals(EXPECTED_WITH_DEFAULT_VALUE,
                sub.replace("The ${animal} jumps over the lazy ${target}. ${undefined.number||1234567890}."));

        sub = new StrSubstitutor(map, "${", "}", '$', "!");
        assertEquals(EXPECTED_WITH_DEFAULT_VALUE,
                sub.replace("The ${animal} jumps over the lazy ${target}. ${undefined.number!1234567890}."));

        sub = new StrSubstitutor(map, "${", "}", '$', "");
        sub.setValueDelimiterMatcher(null);
        assertEquals(EXPECTED_WITH_UNRESOLVED_DEFAULT_VALUE,
                sub.replace("The ${animal} jumps over the lazy ${target}. ${undefined.number!1234567890}."));

        sub = new StrSubstitutor(map, "${", "}", '$');
        sub.setValueDelimiterMatcher(null);
        assertEquals(EXPECTED_WITH_UNRESOLVED_DEFAULT_VALUE,
                sub.replace("The ${animal} jumps over the lazy ${target}. ${undefined.number!1234567890}."));
    }
}
