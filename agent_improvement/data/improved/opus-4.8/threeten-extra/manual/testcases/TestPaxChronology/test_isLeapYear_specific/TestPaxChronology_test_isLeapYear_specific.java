package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link PaxChronology#isLeapYear(long)} for a hand-picked set of years.
 *
 * <p>In the Pax calendar a year is a leap year (it gains the extra 7-day "Pax" month)
 * when, letting {@code d = year % 100} be the last two digits, one of the following holds:
 * <ul>
 *   <li>{@code |d| == 99}, e.g. 99 and -99; or</li>
 *   <li>{@code d} is divisible by 6 and the year is not divisible by 400, e.g. 6, 100, -6, -100.</li>
 * </ul>
 * Years that satisfy neither rule are common (non-leap) years; note that any multiple
 * of 400 (e.g. 400, 0, -400) is always common despite its last two digits being 0.
 */
@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_isLeapYear_specific {

    @Test
    public void test_isLeapYear_specific() {
        // Leap years.
        assertTrue(PaxChronology.INSTANCE.isLeapYear(100));   // last two digits 0, divisible by 6, not a multiple of 400
        assertTrue(PaxChronology.INSTANCE.isLeapYear(99));    // last two digits 99
        assertTrue(PaxChronology.INSTANCE.isLeapYear(6));     // last two digits 6, divisible by 6
        assertTrue(PaxChronology.INSTANCE.isLeapYear(-6));    // last two digits -6, divisible by 6
        assertTrue(PaxChronology.INSTANCE.isLeapYear(-99));   // last two digits -99
        assertTrue(PaxChronology.INSTANCE.isLeapYear(-100));  // last two digits 0, divisible by 6, not a multiple of 400

        // Common (non-leap) years.
        assertFalse(PaxChronology.INSTANCE.isLeapYear(400));  // multiple of 400
        assertFalse(PaxChronology.INSTANCE.isLeapYear(7));
        assertFalse(PaxChronology.INSTANCE.isLeapYear(5));
        assertFalse(PaxChronology.INSTANCE.isLeapYear(4));
        assertFalse(PaxChronology.INSTANCE.isLeapYear(3));
        assertFalse(PaxChronology.INSTANCE.isLeapYear(2));
        assertFalse(PaxChronology.INSTANCE.isLeapYear(1));
        assertFalse(PaxChronology.INSTANCE.isLeapYear(0));
        assertFalse(PaxChronology.INSTANCE.isLeapYear(-1));
        assertFalse(PaxChronology.INSTANCE.isLeapYear(-2));
        assertFalse(PaxChronology.INSTANCE.isLeapYear(-3));
        assertFalse(PaxChronology.INSTANCE.isLeapYear(-4));
        assertFalse(PaxChronology.INSTANCE.isLeapYear(-5));
        assertFalse(PaxChronology.INSTANCE.isLeapYear(-400)); // multiple of 400
    }
}
