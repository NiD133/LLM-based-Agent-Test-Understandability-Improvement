package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testStaticReplace {

    /**
     * Verifies that the static replace method resolves a single variable from a map.
     */
    @Test
    void testStaticReplace() {
        final Map<String, String> map = new HashMap<>();
        map.put("name", "commons");

        final String result = StringSubstitutor.replace("Hi ${name}!", map);

        assertEquals("Hi commons!", result);
    }
}
