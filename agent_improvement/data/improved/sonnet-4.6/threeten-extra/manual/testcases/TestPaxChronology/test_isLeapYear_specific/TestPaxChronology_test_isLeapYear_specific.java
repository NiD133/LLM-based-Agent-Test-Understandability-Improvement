package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_isLeapYear_specific {

    /**
     * Pax leap year rules:
     *  1. Divisible by 400                        → NOT leap
     *  2. Last two digits are "00" (but not ÷400) → IS leap
     *  3. Last two digits are "99"                → IS leap
     *  4. Last two digits divisible by 6          → IS leap
     *  5. Everything else                         → NOT leap
     *  6. Year 0 is never a leap year
     */
    @Test
    public void test_isLeapYear_specific() {
        // Rule 1: years divisible by 400 are NOT leap years
        assertFalse(PaxChronology.INSTANCE.isLeapYear(400));
        assertFalse(PaxChronology.INSTANCE.isLeapYear(-400));

        // Rule 2: years ending in "00" that are NOT divisible by 400 ARE leap years
        assertTrue(PaxChronology.INSTANCE.isLeapYear(100));
        assertTrue(PaxChronology.INSTANCE.isLeapYear(-100));

        // Rule 3: years whose last two digits are 99 ARE leap years
        assertTrue(PaxChronology.INSTANCE.isLeapYear(99));
        assertTrue(PaxChronology.INSTANCE.isLeapYear(-99));

        // Rule 4: years whose last two digits are divisible by 6 ARE leap years
        assertTrue(PaxChronology.INSTANCE.isLeapYear(6));
        assertTrue(PaxChronology.INSTANCE.isLeapYear(-6));

        // Rule 5 & 6: all remaining years are NOT leap years
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
    }
}
