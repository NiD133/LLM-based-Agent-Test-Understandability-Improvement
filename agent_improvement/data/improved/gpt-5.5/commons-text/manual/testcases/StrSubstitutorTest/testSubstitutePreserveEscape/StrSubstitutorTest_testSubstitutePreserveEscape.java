package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testSubstitutePreserveEscape {

    @Test
    void testSubstitutePreserveEscape() {
        final String template = "${not-escaped} $${escaped}";
        final String expectedWhenEscapesAreRemoved = "value ${escaped}";
        final String expectedWhenEscapesArePreserved = "value $${escaped}";

        final Map<String, String> map = new HashMap<>();
        map.put("not-escaped", "value");

        final StrSubstitutor sub = new StrSubstitutor(map, "${", "}", '$');
        assertFalse(sub.isPreserveEscapes());
        assertEquals(expectedWhenEscapesAreRemoved, sub.replace(template));

        sub.setPreserveEscapes(true);
        assertTrue(sub.isPreserveEscapes());
        assertEquals(expectedWhenEscapesArePreserved, sub.replace(template));
    }
}
