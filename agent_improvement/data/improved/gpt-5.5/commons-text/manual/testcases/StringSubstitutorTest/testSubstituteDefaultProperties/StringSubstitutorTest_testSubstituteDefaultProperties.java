package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Properties;

import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testSubstituteDefaultProperties {

    private static final String PROPERTY_NAME = "doesnotwork";
    private static final String PROPERTY_VALUE = "It works!";
    private static final String TEMPLATE = "${" + PROPERTY_NAME + "}";

    /**
     * Verifies that a {@link Properties} instance can resolve values inherited
     * from its defaults, not only values directly stored on the instance.
     */
    @Test
    void testSubstituteDefaultProperties() {
        System.setProperty(PROPERTY_NAME, PROPERTY_VALUE);

        final Properties propertiesWithSystemDefaults = new Properties(System.getProperties());

        assertEquals(PROPERTY_VALUE, StringSubstitutor.replace(TEMPLATE, propertiesWithSystemDefaults));
    }
}
