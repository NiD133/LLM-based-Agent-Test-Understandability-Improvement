package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestHalf_test_length_boolean {

    @Test
    public void test_length_boolean() {
        // H1 (Jan-Jun): 182 days in a leap year, 181 in a standard year
        assertEquals(182, Half.H1.length(true));
        assertEquals(181, Half.H1.length(false));
        // H2 (Jul-Dec): always 184 days regardless of leap year
        assertEquals(184, Half.H2.length(true));
        assertEquals(184, Half.H2.length(false));
    }
}
