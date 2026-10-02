package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_isLeapYear_specific {

    @Test
    public void test_isLeapYear_specific() {
        // Positive years around century, six-year, and regular-year boundaries.
        assertFalse(PaxChronology.INSTANCE.isLeapYear(400));
        assertTrue(PaxChronology.INSTANCE.isLeapYear(100));
        assertTrue(PaxChronology.INSTANCE.isLeapYear(99));
        assertFalse(PaxChronology.INSTANCE.isLeapYear(7));
        assertTrue(PaxChronology.INSTANCE.isLeapYear(6));
        assertFalse(PaxChronology.INSTANCE.isLeapYear(5));
        assertFalse(PaxChronology.INSTANCE.isLeapYear(4));
        assertFalse(PaxChronology.INSTANCE.isLeapYear(3));
        assertFalse(PaxChronology.INSTANCE.isLeapYear(2));
        assertFalse(PaxChronology.INSTANCE.isLeapYear(1));

        // Zero and negative years mirror the original edge cases.
        assertFalse(PaxChronology.INSTANCE.isLeapYear(0));
        assertFalse(PaxChronology.INSTANCE.isLeapYear(-1));
        assertFalse(PaxChronology.INSTANCE.isLeapYear(-2));
        assertFalse(PaxChronology.INSTANCE.isLeapYear(-3));
        assertFalse(PaxChronology.INSTANCE.isLeapYear(-4));
        assertFalse(PaxChronology.INSTANCE.isLeapYear(-5));
        assertTrue(PaxChronology.INSTANCE.isLeapYear(-6));
        assertTrue(PaxChronology.INSTANCE.isLeapYear(-99));
        assertTrue(PaxChronology.INSTANCE.isLeapYear(-100));
        assertFalse(PaxChronology.INSTANCE.isLeapYear(-400));
    }
}
