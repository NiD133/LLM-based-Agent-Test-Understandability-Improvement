package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_length_boolean {

    private static final boolean LEAP_YEAR = true;
    private static final boolean STANDARD_YEAR = false;

    // Q1 (Jan–Mar): 91 days in a leap year, 90 in a standard year
    @Test
    public void test_length_Q1_leapYear() {
        assertEquals(91, Quarter.Q1.length(LEAP_YEAR));
    }

    @Test
    public void test_length_Q1_standardYear() {
        assertEquals(90, Quarter.Q1.length(STANDARD_YEAR));
    }

    // Q2 (Apr–Jun): always 91 days regardless of leap year
    @Test
    public void test_length_Q2_leapYear() {
        assertEquals(91, Quarter.Q2.length(LEAP_YEAR));
    }

    @Test
    public void test_length_Q2_standardYear() {
        assertEquals(91, Quarter.Q2.length(STANDARD_YEAR));
    }

    // Q3 (Jul–Sep): always 92 days regardless of leap year
    @Test
    public void test_length_Q3_leapYear() {
        assertEquals(92, Quarter.Q3.length(LEAP_YEAR));
    }

    @Test
    public void test_length_Q3_standardYear() {
        assertEquals(92, Quarter.Q3.length(STANDARD_YEAR));
    }

    // Q4 (Oct–Dec): always 92 days regardless of leap year
    @Test
    public void test_length_Q4_leapYear() {
        assertEquals(92, Quarter.Q4.length(LEAP_YEAR));
    }

    @Test
    public void test_length_Q4_standardYear() {
        assertEquals(92, Quarter.Q4.length(STANDARD_YEAR));
    }
}
