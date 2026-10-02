package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testStaticReplacePrefixSuffix {

    /**
     * Verifies that the static {@link StrSubstitutor#replace(Object, Map, String, String)}
     * overload substitutes variables delimited by a custom prefix and suffix.
     */
    @Test
    void testStaticReplacePrefixSuffix() {
        final Map<String, String> valueMap = new HashMap<>();
        valueMap.put("name", "commons");

        final String template = "Hi <name>!";
        final String prefix = "<";
        final String suffix = ">";

        final String result = StrSubstitutor.replace(template, valueMap, prefix, suffix);

        assertEquals("Hi commons!", result);
    }
}
