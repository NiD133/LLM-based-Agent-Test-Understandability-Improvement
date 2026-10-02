package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testLANG1055 {

    /**
     * Test for LANG-1055: StrSubstitutor.replaceSystemProperties does not work consistently.
     * Verifies that replaceSystemProperties produces the same result as replace() with System.getProperties().
     */
    @Test
    void testLANG1055() {
        System.setProperty("test_key", "test_value");

        final String template = "test_key=${test_key}";
        final String expected = StrSubstitutor.replace(template, System.getProperties());
        final String actual = StrSubstitutor.replaceSystemProperties(template);

        assertEquals(expected, actual);
    }
}
