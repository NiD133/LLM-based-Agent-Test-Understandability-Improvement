package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestBritishCutoverChronology_test_Chronology_isLeapYear_specific {

    @Test
    public void test_Chronology_isLeapYear_specific() {
        BritishCutoverChronology chrono = BritishCutoverChronology.INSTANCE;

        // Years <= 1752 use the Julian leap rule: divisible by 4 is always a leap year.
        // Positive years: 8 and 4 are leap; 5, 6, 7 are not.
        assertTrue(chrono.isLeapYear(8));
        assertFalse(chrono.isLeapYear(7));
        assertFalse(chrono.isLeapYear(6));
        assertFalse(chrono.isLeapYear(5));
        assertTrue(chrono.isLeapYear(4));
        assertFalse(chrono.isLeapYear(3));
        assertFalse(chrono.isLeapYear(2));
        assertFalse(chrono.isLeapYear(1));

        // Year 0 is divisible by 4, so it is a leap year under the Julian rule.
        assertTrue(chrono.isLeapYear(0));

        // Negative years: -4 is a leap year; -1, -2, -3, -5, -6 are not.
        assertFalse(chrono.isLeapYear(-1));
        assertFalse(chrono.isLeapYear(-2));
        assertFalse(chrono.isLeapYear(-3));
        assertTrue(chrono.isLeapYear(-4));
        assertFalse(chrono.isLeapYear(-5));
        assertFalse(chrono.isLeapYear(-6));
    }
}
