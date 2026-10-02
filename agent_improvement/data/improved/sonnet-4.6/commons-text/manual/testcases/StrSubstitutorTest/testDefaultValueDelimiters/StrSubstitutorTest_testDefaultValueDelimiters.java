package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that StrSubstitutor correctly handles the value-delimiter feature,
 * which lets templates supply fallback values for unresolved variables
 * (e.g. {@code ${key:-default}}).
 */
public class StrSubstitutorTest_testDefaultValueDelimiters {

    private static final String EXPECTED_RESULT =
            "The fox jumps over the lazy dog. 1234567890.";

    private Map<String, String> map;

    @BeforeEach
    public void setUp() {
        map = new HashMap<>();
        map.put("animal", "fox");
        map.put("target", "dog");
        // "undefined.number" is intentionally absent so the default value kicks in.
    }

    @Test
    void testDefaultValueDelimiters() {
        // --- Scenario 1: default delimiter ":-" (bash-style) ---
        // The constructor that takes an escape char uses ":-" as its built-in default.
        StrSubstitutor sub = new StrSubstitutor(map, "${", "}", '$');
        assertEquals(
                EXPECTED_RESULT,
                sub.replace("The ${animal} jumps over the lazy ${target}. ${undefined.number:-1234567890}."));

        // --- Scenario 2: custom two-char delimiter "?:" ---
        sub = new StrSubstitutor(map, "${", "}", '$', "?:");
        assertEquals(
                EXPECTED_RESULT,
                sub.replace("The ${animal} jumps over the lazy ${target}. ${undefined.number?:1234567890}."));

        // --- Scenario 3: custom two-char delimiter "||" ---
        sub = new StrSubstitutor(map, "${", "}", '$', "||");
        assertEquals(
                EXPECTED_RESULT,
                sub.replace("The ${animal} jumps over the lazy ${target}. ${undefined.number||1234567890}."));

        // --- Scenario 4: custom single-char delimiter "!" ---
        sub = new StrSubstitutor(map, "${", "}", '$', "!");
        assertEquals(
                EXPECTED_RESULT,
                sub.replace("The ${animal} jumps over the lazy ${target}. ${undefined.number!1234567890}."));

        // --- Scenario 5: empty delimiter string, then explicitly cleared via setValueDelimiterMatcher(null) ---
        // Without a recognised delimiter, the whole token (including "!1234567890") is
        // treated as the variable name, which has no mapping, so the placeholder is kept verbatim.
        sub = new StrSubstitutor(map, "${", "}", '$', "");
        sub.setValueDelimiterMatcher(null);
        assertEquals(
                "The fox jumps over the lazy dog. ${undefined.number!1234567890}.",
                sub.replace("The ${animal} jumps over the lazy ${target}. ${undefined.number!1234567890}."));

        // --- Scenario 6: default constructor (uses ":-"), then delimiter cleared ---
        // Same behaviour as scenario 5: no delimiter means no fallback lookup.
        sub = new StrSubstitutor(map, "${", "}", '$');
        sub.setValueDelimiterMatcher(null);
        assertEquals(
                "The fox jumps over the lazy dog. ${undefined.number!1234567890}.",
                sub.replace("The ${animal} jumps over the lazy ${target}. ${undefined.number!1234567890}."));
    }
}
