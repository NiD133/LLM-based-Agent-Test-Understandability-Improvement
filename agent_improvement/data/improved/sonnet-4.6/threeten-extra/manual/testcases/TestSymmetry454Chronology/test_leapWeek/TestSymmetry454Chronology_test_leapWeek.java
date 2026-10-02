package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_leapWeek {

    // In the Symmetry454 calendar, a leap year appends an extra 7-day "leap week"
    // at the very end of December (days 29-35). Year 2015 is a leap year.
    private static final int LEAP_YEAR = 2015;
    private static final int DECEMBER = 12;
    private static final int LEAP_WEEK_FIRST_DAY = 29;
    private static final int LEAP_WEEK_LAST_DAY = 35;

    @Test
    public void test_leapWeek() {
        for (int day = LEAP_WEEK_FIRST_DAY; day <= LEAP_WEEK_LAST_DAY; day++) {
            assertTrue(
                Symmetry454Date.of(LEAP_YEAR, DECEMBER, day).isLeapWeek(),
                "Day " + day + " of December " + LEAP_YEAR + " should be in the leap week"
            );
        }
    }
}
