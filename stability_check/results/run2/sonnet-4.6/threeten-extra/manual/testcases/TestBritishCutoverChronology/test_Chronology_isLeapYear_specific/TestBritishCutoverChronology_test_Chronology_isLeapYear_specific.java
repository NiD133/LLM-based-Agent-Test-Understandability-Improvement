package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestBritishCutoverChronology_test_Chronology_isLeapYear_specific {

    @Test
    public void test_Chronology_isLeapYear_specific() {
        // Before the 1752 Gregorian cutover, the Julian rule applies:
        // every year divisible by 4 is a leap year (no century exception).
        BritishCutoverChronology chrono = BritishCutoverChronology.INSTANCE;

        // Positive years: multiples of 4 are leap years
        assertTrue(chrono.isLeapYear(8));
        assertFalse(chrono.isLeapYear(7));
        assertFalse(chrono.isLeapYear(6));
        assertFalse(chrono.isLeapYear(5));
        assertTrue(chrono.isLeapYear(4));
        assertFalse(chrono.isLeapYear(3));
        assertFalse(chrono.isLeapYear(2));
        assertFalse(chrono.isLeapYear(1));

        // Year 0 is a leap year (divisible by 4 under Julian rules)
        assertTrue(chrono.isLeapYear(0));

        // Negative years: multiples of 4 are leap years
        assertFalse(chrono.isLeapYear(-1));
        assertFalse(chrono.isLeapYear(-2));
        assertFalse(chrono.isLeapYear(-3));
        assertTrue(chrono.isLeapYear(-4));
        assertFalse(chrono.isLeapYear(-5));
        assertFalse(chrono.isLeapYear(-6));
    }
}
