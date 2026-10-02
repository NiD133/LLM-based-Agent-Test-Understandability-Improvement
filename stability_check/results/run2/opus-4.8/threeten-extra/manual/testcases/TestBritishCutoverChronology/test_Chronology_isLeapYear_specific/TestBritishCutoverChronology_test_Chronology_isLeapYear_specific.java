package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies leap-year detection for specific proleptic years in the
 * {@link BritishCutoverChronology}.
 *
 * <p>For these early years the British cutover chronology follows the Julian
 * rule, where every year divisible by 4 (including year 0 and negative years)
 * is a leap year. The cases below walk the years from 8 down to -6 so that the
 * expected leap years (8, 4, 0, -4) and their non-leap neighbours are all
 * covered.
 */
public class TestBritishCutoverChronology_test_Chronology_isLeapYear_specific {

    private static final BritishCutoverChronology CHRONOLOGY = BritishCutoverChronology.INSTANCE;

    @Test
    public void test_Chronology_isLeapYear_specific() {
        // Julian rule: a year is leap exactly when it is divisible by 4.
        assertTrue(CHRONOLOGY.isLeapYear(8));
        assertFalse(CHRONOLOGY.isLeapYear(7));
        assertFalse(CHRONOLOGY.isLeapYear(6));
        assertFalse(CHRONOLOGY.isLeapYear(5));
        assertTrue(CHRONOLOGY.isLeapYear(4));
        assertFalse(CHRONOLOGY.isLeapYear(3));
        assertFalse(CHRONOLOGY.isLeapYear(2));
        assertFalse(CHRONOLOGY.isLeapYear(1));
        assertTrue(CHRONOLOGY.isLeapYear(0));
        assertFalse(CHRONOLOGY.isLeapYear(-1));
        assertFalse(CHRONOLOGY.isLeapYear(-2));
        assertFalse(CHRONOLOGY.isLeapYear(-3));
        assertTrue(CHRONOLOGY.isLeapYear(-4));
        assertFalse(CHRONOLOGY.isLeapYear(-5));
        assertFalse(CHRONOLOGY.isLeapYear(-6));
    }
}
