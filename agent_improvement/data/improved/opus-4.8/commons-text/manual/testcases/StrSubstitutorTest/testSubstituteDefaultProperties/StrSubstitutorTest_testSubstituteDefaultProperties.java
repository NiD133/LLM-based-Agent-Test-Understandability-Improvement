package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Properties;
import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testSubstituteDefaultProperties {

    /**
     * Verifies that {@link StrSubstitutor#replace(Object, Properties)} resolves a
     * placeholder from the default values of a {@link Properties} object.
     *
     * <p>The {@code Properties} instance has no values of its own; it only carries
     * the system properties as its defaults. The substitutor must therefore fall
     * back to those defaults to resolve {@code ${doesnotwork}}.</p>
     */
    @Test
    void testSubstituteDefaultProperties() {
        final String template = "${doesnotwork}";
        System.setProperty("doesnotwork", "It works!");

        // Properties with the system properties as defaults (no own entries).
        final Properties propertiesWithSystemDefaults = new Properties(System.getProperties());

        assertEquals("It works!", StrSubstitutor.replace(template, propertiesWithSystemDefaults));
    }
}
