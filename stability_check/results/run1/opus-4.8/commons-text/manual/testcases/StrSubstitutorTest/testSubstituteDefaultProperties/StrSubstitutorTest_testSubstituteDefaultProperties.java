package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Properties;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testSubstituteDefaultProperties {

    /**
     * Verifies that {@link StrSubstitutor#replace(Object, Properties)} resolves a
     * variable whose value is supplied through the {@link Properties} defaults.
     * <p>
     * The template references {@code doesnotwork}, which is not a key in the
     * {@code Properties} object itself. It is only available via the default
     * {@code Properties} (the system properties), so a successful substitution
     * proves that the defaults are consulted during replacement.
     */
    @Test
    void testSubstituteDefaultProperties() {
        final String template = "${doesnotwork}";
        System.setProperty("doesnotwork", "It works!");

        // Back the Properties with the system properties as defaults, so that
        // "doesnotwork" is only reachable through the default lookup.
        final Properties propertiesWithSystemDefaults = new Properties(System.getProperties());

        assertEquals("It works!", StrSubstitutor.replace(template, propertiesWithSystemDefaults));
    }
}
