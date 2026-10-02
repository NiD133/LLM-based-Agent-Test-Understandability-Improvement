package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testStaticReplace {

    /**
     * Verifies that the static {@link StrSubstitutor#replace(Object, Map)} helper
     * substitutes a {@code ${...}} placeholder using the supplied value map.
     */
    @Test
    void testStaticReplace() {
        final Map<String, String> values = new HashMap<>();
        values.put("name", "commons");

        final String result = StrSubstitutor.replace("Hi ${name}!", values);

        assertEquals("Hi commons!", result);
    }
}
