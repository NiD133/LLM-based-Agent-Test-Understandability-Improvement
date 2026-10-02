package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Tests for BritishCutoverChronology.isLeapYear() with specific proleptic years.
 *
 * For years <= 1752 (cutover year), the Julian leap year rule applies:
 * every year divisible by 4 is a leap year (no century exception).
 */
public class TestBritishCutoverChronology_test_Chronology_isLeapYear_specific {

    @Test
    public void test_Chronology_isLeapYear_specific() {
        // BC years: Julian rule — divisible by 4 is a leap year
        assertFalse(BritishCutoverChronology.INSTANCE.isLeapYear(-6));
        assertFalse(BritishCutoverChronology.INSTANCE.isLeapYear(-5));
        assertTrue(BritishCutoverChronology.INSTANCE.isLeapYear(-4));
        assertFalse(BritishCutoverChronology.INSTANCE.isLeapYear(-3));
        assertFalse(BritishCutoverChronology.INSTANCE.isLeapYear(-2));
        assertFalse(BritishCutoverChronology.INSTANCE.isLeapYear(-1));

        // Year 0 (1 BC proleptic) is a Julian leap year
        assertTrue(BritishCutoverChronology.INSTANCE.isLeapYear(0));

        // AD years before cutover: Julian rule — divisible by 4 is a leap year
        assertFalse(BritishCutoverChronology.INSTANCE.isLeapYear(1));
        assertFalse(BritishCutoverChronology.INSTANCE.isLeapYear(2));
        assertFalse(BritishCutoverChronology.INSTANCE.isLeapYear(3));
        assertTrue(BritishCutoverChronology.INSTANCE.isLeapYear(4));
        assertFalse(BritishCutoverChronology.INSTANCE.isLeapYear(5));
        assertFalse(BritishCutoverChronology.INSTANCE.isLeapYear(6));
        assertFalse(BritishCutoverChronology.INSTANCE.isLeapYear(7));
        assertTrue(BritishCutoverChronology.INSTANCE.isLeapYear(8));
    }
}
