package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testStaticReplacePrefixSuffix {

    /**
     * Verifies that the static replace method correctly substitutes variables
     * when custom prefix and suffix delimiters are provided instead of the
     * default "${" / "}" markers.
     */
    @Test
    void testStaticReplacePrefixSuffix() {
        final Map<String, String> map = new HashMap<>();
        map.put("name", "commons");

        final String result = StringSubstitutor.replace("Hi <name>!", map, "<", ">");

        assertEquals("Hi commons!", result);
    }
}
