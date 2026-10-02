package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@code Symmetry454Date#lengthOfMonth()} returns the correct month length
 * for December in both a standard year and a leap year.
 *
 * <p>In the Symmetry454 calendar, December normally has 28 days. In a leap year,
 * an extra week is appended to December, making it 35 days.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_lengthOfMonth_specific {

    // Year 2000 is a non-leap year in the Symmetry454 calendar.
    private static final int NON_LEAP_YEAR = 2000;
    // Year 2004 is a leap year in the Symmetry454 calendar (gets an extra week in December).
    private static final int LEAP_YEAR = 2004;

    private static final int DECEMBER = 12;
    private static final int STANDARD_DECEMBER_LENGTH = 28;
    private static final int LEAP_DECEMBER_LENGTH = 35;

    @Test
    public void test_lengthOfMonth_specific() {
        // December in a non-leap year has 28 days (4 weeks)
        assertEquals(STANDARD_DECEMBER_LENGTH,
                Symmetry454Date.of(NON_LEAP_YEAR, DECEMBER, 28).lengthOfMonth());

        // December in a leap year has 35 days (4 weeks + leap week)
        assertEquals(LEAP_DECEMBER_LENGTH,
                Symmetry454Date.of(LEAP_YEAR, DECEMBER, 28).lengthOfMonth());
    }
}
