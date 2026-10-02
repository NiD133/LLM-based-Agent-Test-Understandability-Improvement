package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests that all 7 days of the leap week in a Symmetry010 leap year are identified as leap-week days.
 *
 * In the Symmetry010 calendar, leap years have an extra week (days 31-37) appended to December.
 * Year 2015 is a leap year, so December 2015 contains 37 days.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_leapWeek {

    // The leap week in a Symmetry010 leap year spans days 31-37 of December.
    private static final int LEAP_YEAR = 2015;
    private static final int DECEMBER = 12;
    private static final int LEAP_WEEK_FIRST_DAY = 31;
    private static final int LEAP_WEEK_LAST_DAY = 37;

    @Test
    public void test_leapWeek() {
        for (int day = LEAP_WEEK_FIRST_DAY; day <= LEAP_WEEK_LAST_DAY; day++) {
            assertTrue(Symmetry010Date.of(LEAP_YEAR, DECEMBER, day).isLeapWeek(),
                    "Day " + day + " of December " + LEAP_YEAR + " should be in the leap week");
        }
    }
}
