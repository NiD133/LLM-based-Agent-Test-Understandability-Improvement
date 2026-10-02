package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Properties;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testSubstituteDefaultProperties {

    /**
     * Verifies that StrSubstitutor resolves variables using a Properties object
     * that delegates to System properties as its defaults.
     */
    @Test
    void testSubstituteDefaultProperties() {
        final String template = "${doesnotwork}";
        final String expectedValue = "It works!";

        System.setProperty("doesnotwork", expectedValue);

        // Wrap system properties so they serve as fallback defaults
        final Properties propsWithSystemDefaults = new Properties(System.getProperties());

        assertEquals(expectedValue, StrSubstitutor.replace(template, propsWithSystemDefaults));
    }
}
