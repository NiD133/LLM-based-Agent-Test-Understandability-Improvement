package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Properties;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testSubstituteDefaultProperties {

    private static final String PROPERTY_NAME = "doesnotwork";
    private static final String PROPERTY_PLACEHOLDER = "${doesnotwork}";
    private static final String PROPERTY_VALUE = "It works!";

    /**
     * Test the replace of a properties object.
     */
    @Test
    void testSubstituteDefaultProperties() {
        System.setProperty(PROPERTY_NAME, PROPERTY_VALUE);

        // The new Properties instance has no direct entries; lookup should fall
        // back to the System properties supplied as its defaults.
        final Properties propertiesWithSystemDefaults = new Properties(System.getProperties());

        assertEquals(PROPERTY_VALUE, StrSubstitutor.replace(PROPERTY_PLACEHOLDER, propertiesWithSystemDefaults));
    }
}
