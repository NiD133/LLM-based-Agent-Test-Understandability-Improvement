package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testSamePrefixAndSuffix {

    private static final String AT_SIGN = "@";

    @Test
    void testSamePrefixAndSuffix() {
        final Map<String, String> values = new HashMap<>();
        values.put("greeting", "Hello");
        values.put(" there ", "XXX");
        values.put("name", "commons");

        assertEquals("Hi commons!", StrSubstitutor.replace("Hi @name@!", values, AT_SIGN, AT_SIGN));
        assertEquals("Hello there commons!", StrSubstitutor.replace("@greeting@ there @name@!", values, AT_SIGN, AT_SIGN));
    }
}
