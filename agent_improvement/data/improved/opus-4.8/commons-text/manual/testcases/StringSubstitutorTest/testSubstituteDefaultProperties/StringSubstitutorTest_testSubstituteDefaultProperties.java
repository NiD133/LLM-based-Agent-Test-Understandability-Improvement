package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Properties;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringSubstitutor#replace(Object, Properties)} when the variable's value is only
 * reachable through the {@link Properties} object's <em>defaults</em> rather than its own entries.
 */
public class StringSubstitutorTest_testSubstituteDefaultProperties {

    /**
     * Verifies that {@code replace(source, properties)} resolves a variable whose value lives in the
     * default {@link Properties} (here, the system properties) rather than in the properties object
     * directly.
     */
    @Test
    void testSubstituteDefaultProperties() {
        final String template = "${doesnotwork}";

        // The variable's value is placed in the system properties, which then back the new Properties
        // object as its defaults. The Properties object itself holds no direct entry for the key.
        System.setProperty("doesnotwork", "It works!");
        final Properties propertiesWithSystemDefaults = new Properties(System.getProperties());

        final String result = StringSubstitutor.replace(template, propertiesWithSystemDefaults);

        assertEquals("It works!", result);
    }
}
