package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link JulianChronology#isLeapYear(long)}.
 * <p>
 * In the Julian calendar a proleptic year is a leap year when it is exactly
 * divisible by four. This holds for negative years too, where year 0 counts as
 * a leap year. The cases below walk a contiguous span of years (8 down to -6) so
 * that the repeating "leap every four years" pattern is easy to see at a glance.
 */
public class TestJulianChronology_test_isLeapYear_specific {

    @Test
    public void test_isLeapYear_specific() {
        // Leap years (divisible by four): 8, 4, 0, -4
        assertTrue(JulianChronology.INSTANCE.isLeapYear(8));
        assertFalse(JulianChronology.INSTANCE.isLeapYear(7));
        assertFalse(JulianChronology.INSTANCE.isLeapYear(6));
        assertFalse(JulianChronology.INSTANCE.isLeapYear(5));
        assertTrue(JulianChronology.INSTANCE.isLeapYear(4));
        assertFalse(JulianChronology.INSTANCE.isLeapYear(3));
        assertFalse(JulianChronology.INSTANCE.isLeapYear(2));
        assertFalse(JulianChronology.INSTANCE.isLeapYear(1));
        assertTrue(JulianChronology.INSTANCE.isLeapYear(0));
        assertFalse(JulianChronology.INSTANCE.isLeapYear(-1));
        assertFalse(JulianChronology.INSTANCE.isLeapYear(-2));
        assertFalse(JulianChronology.INSTANCE.isLeapYear(-3));
        assertTrue(JulianChronology.INSTANCE.isLeapYear(-4));
        assertFalse(JulianChronology.INSTANCE.isLeapYear(-5));
        assertFalse(JulianChronology.INSTANCE.isLeapYear(-6));
    }
}
