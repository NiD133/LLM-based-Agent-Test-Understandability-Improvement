package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public class TestSymmetry454Chronology_test_leapWeek {

    private static final int LEAP_WEEK_YEAR = 2015;
    private static final int LEAP_WEEK_MONTH = 12;
    private static final int FIRST_LEAP_WEEK_DAY = 29;
    private static final int LAST_LEAP_WEEK_DAY = 35;

    @Test
    public void test_leapWeek() {
        for (int day = FIRST_LEAP_WEEK_DAY; day <= LAST_LEAP_WEEK_DAY; day++) {
            assertTrue(Symmetry454Date.of(LEAP_WEEK_YEAR, LEAP_WEEK_MONTH, day).isLeapWeek());
        }
    }
}
