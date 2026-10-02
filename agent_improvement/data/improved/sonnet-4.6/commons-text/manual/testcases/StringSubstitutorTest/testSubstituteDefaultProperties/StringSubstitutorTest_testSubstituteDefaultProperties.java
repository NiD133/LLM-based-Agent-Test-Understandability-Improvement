package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

public class StringSubstitutorTest_testSubstituteDefaultProperties {

    /**
     * Verifies that StringSubstitutor.replace resolves variables from a Properties object
     * whose defaults are backed by System properties.
     */
    @Test
    void testSubstituteDefaultProperties() {
        final String template = "${doesnotwork}";
        System.setProperty("doesnotwork", "It works!");

        // Properties backed by System.getProperties() so the key resolves via the default chain
        final Properties props = new Properties(System.getProperties());
        final String expected = "It works!";
        final String actual = StringSubstitutor.replace(template, props);

        assertEquals(expected, actual,
            () -> String.format("expected.length()=%,d, actual.length()=%,d",
                StringUtils.length(expected), StringUtils.length(actual)));
    }
}
