package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Symmetry010Chronology#isLeapYear(long)}.
 *
 * Leap years satisfy: 52 > ((52 * year + 146) % 293).
 * They occur every 5 or 6 years, spread evenly across a 293-year cycle.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_isLeapYear_specific {

    private static final Symmetry010Chronology CHRONO = Symmetry010Chronology.INSTANCE;

    @Test
    public void test_isLeapYear_specific() {
        // Years 3 and 9 are leap years in the early part of the cycle
        assertTrue(CHRONO.isLeapYear(3));
        assertTrue(CHRONO.isLeapYear(9));

        // Year 6 falls between two leap years and is not a leap year
        assertFalse(CHRONO.isLeapYear(6));

        // Year 2000 is not a leap year (unlike the ISO calendar)
        assertFalse(CHRONO.isLeapYear(2000));

        // Year 2004 is a leap year
        assertTrue(CHRONO.isLeapYear(2004));
    }
}
