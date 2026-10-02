package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestBritishCutoverChronology_test_Chronology_isLeapYear_specific {

    @Test
    public void test_Chronology_isLeapYear_specific() {
        // Julian leap-year rule around AD years.
        assertTrue(BritishCutoverChronology.INSTANCE.isLeapYear(8));
        assertFalse(BritishCutoverChronology.INSTANCE.isLeapYear(7));
        assertFalse(BritishCutoverChronology.INSTANCE.isLeapYear(6));
        assertFalse(BritishCutoverChronology.INSTANCE.isLeapYear(5));
        assertTrue(BritishCutoverChronology.INSTANCE.isLeapYear(4));
        assertFalse(BritishCutoverChronology.INSTANCE.isLeapYear(3));
        assertFalse(BritishCutoverChronology.INSTANCE.isLeapYear(2));
        assertFalse(BritishCutoverChronology.INSTANCE.isLeapYear(1));

        // The same four-year pattern continues across proleptic year zero and BC years.
        assertTrue(BritishCutoverChronology.INSTANCE.isLeapYear(0));
        assertFalse(BritishCutoverChronology.INSTANCE.isLeapYear(-1));
        assertFalse(BritishCutoverChronology.INSTANCE.isLeapYear(-2));
        assertFalse(BritishCutoverChronology.INSTANCE.isLeapYear(-3));
        assertTrue(BritishCutoverChronology.INSTANCE.isLeapYear(-4));
        assertFalse(BritishCutoverChronology.INSTANCE.isLeapYear(-5));
        assertFalse(BritishCutoverChronology.INSTANCE.isLeapYear(-6));
    }
}
