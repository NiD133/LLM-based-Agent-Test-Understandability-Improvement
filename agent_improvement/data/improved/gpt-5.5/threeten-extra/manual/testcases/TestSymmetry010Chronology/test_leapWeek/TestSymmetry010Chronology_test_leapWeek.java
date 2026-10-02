package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_leapWeek {

    private static final int LEAP_WEEK_YEAR = 2015;
    private static final int DECEMBER = 12;
    private static final int FIRST_LEAP_WEEK_DAY = 31;
    private static final int LAST_LEAP_WEEK_DAY = 37;

    @Test
    public void test_leapWeek() {
        for (int day = FIRST_LEAP_WEEK_DAY; day <= LAST_LEAP_WEEK_DAY; day++) {
            assertLeapWeekDay(day);
        }
    }

    private void assertLeapWeekDay(int dayOfMonth) {
        assertTrue(Symmetry010Date.of(LEAP_WEEK_YEAR, DECEMBER, dayOfMonth).isLeapWeek());
    }
}
