package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestJulianChronology_test_isLeapYear_specific {

    @Test
    public void test_isLeapYear_specific() {
        // Positive proleptic years: every fourth year is a Julian leap year.
        assertTrue(JulianChronology.INSTANCE.isLeapYear(8));
        assertFalse(JulianChronology.INSTANCE.isLeapYear(7));
        assertFalse(JulianChronology.INSTANCE.isLeapYear(6));
        assertFalse(JulianChronology.INSTANCE.isLeapYear(5));
        assertTrue(JulianChronology.INSTANCE.isLeapYear(4));
        assertFalse(JulianChronology.INSTANCE.isLeapYear(3));
        assertFalse(JulianChronology.INSTANCE.isLeapYear(2));
        assertFalse(JulianChronology.INSTANCE.isLeapYear(1));

        // Year zero and negative proleptic years follow the same divisibility rule.
        assertTrue(JulianChronology.INSTANCE.isLeapYear(0));
        assertFalse(JulianChronology.INSTANCE.isLeapYear(-1));
        assertFalse(JulianChronology.INSTANCE.isLeapYear(-2));
        assertFalse(JulianChronology.INSTANCE.isLeapYear(-3));
        assertTrue(JulianChronology.INSTANCE.isLeapYear(-4));
        assertFalse(JulianChronology.INSTANCE.isLeapYear(-5));
        assertFalse(JulianChronology.INSTANCE.isLeapYear(-6));
    }
}
