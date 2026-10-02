package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Tests the static convenience method
 * {@link StringSubstitutor#replace(Object, Map)}, which substitutes
 * {@code ${key}} placeholders in a template using a single value map.
 */
public class StringSubstitutorTest_testStaticReplace {

    @Test
    void testStaticReplace() {
        final Map<String, String> values = new HashMap<>();
        values.put("name", "commons");

        final String result = StringSubstitutor.replace("Hi ${name}!", values);

        assertEquals("Hi commons!", result);
    }
}
