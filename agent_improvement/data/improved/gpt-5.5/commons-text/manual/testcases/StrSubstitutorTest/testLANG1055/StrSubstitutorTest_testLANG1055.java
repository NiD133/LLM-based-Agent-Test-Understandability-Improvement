package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testLANG1055 {

    /**
     * Test for LANG-1055: StrSubstitutor.replaceSystemProperties does not work consistently.
     */
    @Test
    void testLANG1055() {
        System.setProperty("test_key", "test_value");

        final String expected = StrSubstitutor.replace("test_key=${test_key}", System.getProperties());
        final String actual = StrSubstitutor.replaceSystemProperties("test_key=${test_key}");

        assertEquals(expected, actual);
    }
}
