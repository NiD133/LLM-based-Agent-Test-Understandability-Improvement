package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Properties;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testSubstituteDefaultProperties {

    /**
     * Verifies that StrSubstitutor can resolve variables from a Properties object
     * whose defaults are backed by System.getProperties(). The variable name
     * "doesnotwork" is intentionally chosen to show that, despite the name,
     * substitution does work when the property exists in the defaults chain.
     */
    @Test
    void testSubstituteDefaultProperties() {
        // Arrange: register a system property and expose it via a Properties default chain
        final String propertyKey = "doesnotwork";
        final String propertyValue = "It works!";
        System.setProperty(propertyKey, propertyValue);

        final String templateWithVariable = "${" + propertyKey + "}";
        final Properties propsWithSystemDefaults = new Properties(System.getProperties());

        // Act: resolve the variable template using the properties-backed substitutor
        final String result = StrSubstitutor.replace(templateWithVariable, propsWithSystemDefaults);

        // Assert: the variable was resolved to the system property value
        assertEquals(propertyValue, result);
    }
}
