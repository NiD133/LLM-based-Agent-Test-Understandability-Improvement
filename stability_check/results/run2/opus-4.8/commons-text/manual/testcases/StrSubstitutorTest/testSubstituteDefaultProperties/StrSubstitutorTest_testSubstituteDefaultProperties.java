package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Properties;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testSubstituteDefaultProperties {

    /**
     * Verifies that {@link StrSubstitutor#replace(Object, Properties)} resolves a
     * variable that is not stored directly in the given {@link Properties} but is
     * available through its chain of default properties.
     */
    @Test
    void testSubstituteDefaultProperties() {
        final String template = "${doesnotwork}";

        // Register the variable value as a system property.
        System.setProperty("doesnotwork", "It works!");

        // Build empty Properties that fall back to the system properties as defaults,
        // so "doesnotwork" is only reachable via the default chain.
        final Properties propertiesWithSystemDefaults = new Properties(System.getProperties());

        assertEquals("It works!", StrSubstitutor.replace(template, propertiesWithSystemDefaults));
    }
}
