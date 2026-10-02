package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Properties;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testSubstituteDefaultProperties {

    /**
     * Verifies that StrSubstitutor.replace resolves a variable from a Properties object
     * whose defaults are backed by System properties.
     *
     * Steps:
     *  1. Register a known key in System properties.
     *  2. Create a Properties object that delegates to System.getProperties() as defaults.
     *  3. Confirm that replacing "${doesnotwork}" with that Properties object yields the value.
     */
    @Test
    void testSubstituteDefaultProperties() {
        final String template = "${doesnotwork}";
        final String expectedValue = "It works!";

        System.setProperty("doesnotwork", expectedValue);

        // Properties wraps System properties as defaults, so the variable should resolve.
        final Properties propsWithSystemDefaults = new Properties(System.getProperties());

        assertEquals(expectedValue, StrSubstitutor.replace(template, propsWithSystemDefaults));
    }
}
