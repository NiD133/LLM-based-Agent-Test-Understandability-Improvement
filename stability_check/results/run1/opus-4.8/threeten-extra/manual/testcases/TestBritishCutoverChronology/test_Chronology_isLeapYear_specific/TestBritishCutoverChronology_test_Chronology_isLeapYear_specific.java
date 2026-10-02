package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link BritishCutoverChronology#isLeapYear(long)} for a set of specific years.
 * <p>
 * All years checked here (8 and earlier) fall before the 1752 cutover, so the British
 * cutover chronology applies the Julian rule: a year is a leap year exactly when it is
 * divisible by 4. This holds for both positive and negative (proleptic) years.
 */
public class TestBritishCutoverChronology_test_Chronology_isLeapYear_specific {

    private static final BritishCutoverChronology CHRONOLOGY = BritishCutoverChronology.INSTANCE;

    @Test
    public void test_Chronology_isLeapYear_specific() {
        // Years divisible by 4 are leap years under the Julian rule.
        assertTrue(CHRONOLOGY.isLeapYear(8));
        assertTrue(CHRONOLOGY.isLeapYear(4));
        assertTrue(CHRONOLOGY.isLeapYear(0));
        assertTrue(CHRONOLOGY.isLeapYear(-4));

        // Years not divisible by 4 are common (non-leap) years.
        assertFalse(CHRONOLOGY.isLeapYear(7));
        assertFalse(CHRONOLOGY.isLeapYear(6));
        assertFalse(CHRONOLOGY.isLeapYear(5));
        assertFalse(CHRONOLOGY.isLeapYear(3));
        assertFalse(CHRONOLOGY.isLeapYear(2));
        assertFalse(CHRONOLOGY.isLeapYear(1));
        assertFalse(CHRONOLOGY.isLeapYear(-1));
        assertFalse(CHRONOLOGY.isLeapYear(-2));
        assertFalse(CHRONOLOGY.isLeapYear(-3));
        assertFalse(CHRONOLOGY.isLeapYear(-5));
        assertFalse(CHRONOLOGY.isLeapYear(-6));
    }
}
