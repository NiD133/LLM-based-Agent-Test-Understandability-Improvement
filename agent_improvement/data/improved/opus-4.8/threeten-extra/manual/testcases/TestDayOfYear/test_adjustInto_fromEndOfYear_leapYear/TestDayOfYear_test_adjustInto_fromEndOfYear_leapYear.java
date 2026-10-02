package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfYear_test_adjustInto_fromEndOfYear_leapYear {

    /** Number of days in a leap year (2008). */
    private static final int LEAP_YEAR_LENGTH = 366;

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfYear(), DayOfYear.now(zone).getValue());
    }

    //-----------------------------------------------------------------------
    // adjustInto() must set the day-of-year on the target date regardless of the
    // target's starting position. Starting from the last day of a leap year,
    // applying each DayOfYear from 1 to 366 should yield every date of that leap
    // year in order, beginning with 1 January.
    //-----------------------------------------------------------------------
    @Test
    public void test_adjustInto_fromEndOfYear_leapYear() {
        LocalDate endOfLeapYear = LocalDate.of(2008, 12, 31);
        LocalDate expectedDate = LocalDate.of(2008, 1, 1);

        for (int dayOfYear = 1; dayOfYear <= LEAP_YEAR_LENGTH; dayOfYear++) {
            DayOfYear test = DayOfYear.of(dayOfYear);

            assertEquals(expectedDate, test.adjustInto(endOfLeapYear));

            expectedDate = expectedDate.plusDays(1);
        }
    }
}
