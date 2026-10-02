package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link InternationalFixedChronology#isLeapYear(long)} against the
 * specific leap-year rule of the International Fixed calendar, which mirrors
 * the Gregorian rule: a year is a leap year when it is divisible by 4, except
 * for centuries that are not divisible by 400.
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_isLeapYear_specific {

    private final InternationalFixedChronology chronology = InternationalFixedChronology.INSTANCE;

    @Test
    public void test_isLeapYear_specific() {
        // Century divisible by 400 -> leap year.
        assertTrue(chronology.isLeapYear(400));
        // Century not divisible by 400 -> common year.
        assertFalse(chronology.isLeapYear(100));
        // Divisible by 4 (non-century) -> leap year.
        assertTrue(chronology.isLeapYear(4));
        // Not divisible by 4 -> common year.
        assertFalse(chronology.isLeapYear(3));
        assertFalse(chronology.isLeapYear(2));
        assertFalse(chronology.isLeapYear(1));
    }
}
