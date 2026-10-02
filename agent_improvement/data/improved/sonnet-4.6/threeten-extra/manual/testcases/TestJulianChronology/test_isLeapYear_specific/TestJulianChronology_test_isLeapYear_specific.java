package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link JulianChronology#isLeapYear} correctly identifies leap years.
 * In the Julian calendar, every year divisible by 4 is a leap year (prolepticYear % 4 == 0),
 * including year 0 and negative proleptic years.
 */
public class TestJulianChronology_test_isLeapYear_specific {

    private static final JulianChronology JULIAN = JulianChronology.INSTANCE;

    @Test
    public void test_isLeapYear_specific() {
        // Positive years: every 4th year is a leap year
        assertTrue(JULIAN.isLeapYear(8));
        assertFalse(JULIAN.isLeapYear(7));
        assertFalse(JULIAN.isLeapYear(6));
        assertFalse(JULIAN.isLeapYear(5));
        assertTrue(JULIAN.isLeapYear(4));
        assertFalse(JULIAN.isLeapYear(3));
        assertFalse(JULIAN.isLeapYear(2));
        assertFalse(JULIAN.isLeapYear(1));

        // Year 0 is divisible by 4, so it is a leap year
        assertTrue(JULIAN.isLeapYear(0));

        // Negative years: the same divisibility-by-4 rule applies
        assertFalse(JULIAN.isLeapYear(-1));
        assertFalse(JULIAN.isLeapYear(-2));
        assertFalse(JULIAN.isLeapYear(-3));
        assertTrue(JULIAN.isLeapYear(-4));
        assertFalse(JULIAN.isLeapYear(-5));
        assertFalse(JULIAN.isLeapYear(-6));
    }
}
