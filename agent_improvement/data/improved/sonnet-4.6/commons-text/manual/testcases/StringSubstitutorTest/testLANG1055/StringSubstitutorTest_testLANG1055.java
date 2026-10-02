package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Regression test for LANG-1055: StringSubstitutor.replaceSystemProperties
 * must produce the same result as replace(template, System.getProperties()).
 */
public class StringSubstitutorTest_testLANG1055 {

    /**
     * Verifies that replaceSystemProperties is consistent with explicitly
     * passing System.getProperties() to the static replace method.
     * Before the fix both methods were backed by different lookup strategies
     * and could return different results.
     */
    @Test
    void testLANG1055() {
        System.setProperty("test_key", "test_value");

        final String template = "test_key=${test_key}";
        final String expected = StringSubstitutor.replace(template, System.getProperties());
        final String actual   = StringSubstitutor.replaceSystemProperties(template);

        assertEquals(expected, actual);
    }
}
