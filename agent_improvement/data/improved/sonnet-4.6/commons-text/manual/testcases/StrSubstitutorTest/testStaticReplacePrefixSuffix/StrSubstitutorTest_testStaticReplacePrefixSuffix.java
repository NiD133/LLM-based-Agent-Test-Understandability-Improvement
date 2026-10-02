package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link StrSubstitutor#replace(String, Map, String, String)} with custom
 * variable prefix and suffix delimiters.
 */
public class StrSubstitutorTest_testStaticReplacePrefixSuffix {

    /**
     * Verifies that the static replace method correctly substitutes a variable
     * enclosed in custom delimiters ("<" and ">") using values from the provided map.
     */
    @Test
    void testStaticReplacePrefixSuffix() {
        final Map<String, String> map = new HashMap<>();
        map.put("name", "commons");

        assertEquals("Hi commons!", StrSubstitutor.replace("Hi <name>!", map, "<", ">"));
    }
}
