package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testStaticReplacePrefixSuffix {

    @Test
    void testStaticReplacePrefixSuffix() {
        final Map<String, String> map = new HashMap<>();
        map.put("name", "commons");

        assertEquals("Hi commons!", StrSubstitutor.replace("Hi <name>!", map, "<", ">"));
    }
}
