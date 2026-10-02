package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testStaticReplace {

    /**
     * Tests the static replace convenience method with the default variable
     * prefix and suffix.
     */
    @Test
    void testStaticReplace() {
        final Map<String, String> map = new HashMap<>();
        map.put("name", "commons");

        assertEquals("Hi commons!", StrSubstitutor.replace("Hi ${name}!", map));
    }
}
