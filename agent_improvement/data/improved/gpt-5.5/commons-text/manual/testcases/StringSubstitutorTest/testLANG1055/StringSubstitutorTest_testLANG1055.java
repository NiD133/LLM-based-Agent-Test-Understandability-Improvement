package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testLANG1055 {

    private static final String PROPERTY_NAME = "test_key";
    private static final String PROPERTY_VALUE = "test_value";
    private static final String TEMPLATE = "test_key=${test_key}";

    /**
     * LANG-1055: replaceSystemProperties must behave the same as replacing
     * against the JVM system properties directly.
     */
    @Test
    void testLANG1055() {
        System.setProperty(PROPERTY_NAME, PROPERTY_VALUE);

        final String expected = StringSubstitutor.replace(TEMPLATE, System.getProperties());
        final String actual = StringSubstitutor.replaceSystemProperties(TEMPLATE);

        assertEquals(expected, actual);
    }
}
