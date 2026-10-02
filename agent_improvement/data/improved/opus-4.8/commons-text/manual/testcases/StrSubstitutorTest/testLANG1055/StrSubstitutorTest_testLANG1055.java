package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Regression test for LANG-1055: {@link StrSubstitutor#replaceSystemProperties(Object)}
 * should behave consistently with substituting against {@link System#getProperties()} directly.
 */
public class StrSubstitutorTest_testLANG1055 {

    @Test
    void replaceSystemPropertiesMatchesManualSystemPropertySubstitution() {
        // Given a system property that the template will reference.
        System.setProperty("test_key", "test_value");
        final String template = "test_key=${test_key}";

        // When substituting via the system-properties convenience method...
        final String actual = StrSubstitutor.replaceSystemProperties(template);

        // ...it must equal substituting against System.getProperties() explicitly.
        final String expected = StrSubstitutor.replace(template, System.getProperties());
        assertEquals(expected, actual);
    }
}
