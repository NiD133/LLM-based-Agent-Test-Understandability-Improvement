package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfYear_test_adjustInto_fromStartOfYear_leapYear {

    /** Number of days in a leap year (2008 is a leap year). */
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
    @Test
    public void test_adjustInto_fromStartOfYear_leapYear() {
        // Starting from Jan 1st of a leap year, adjusting that date to each
        // DayOfYear from 1 to 366 must yield the corresponding calendar date.
        LocalDate startOfLeapYear = LocalDate.of(2008, 1, 1);

        LocalDate expectedDate = startOfLeapYear;
        for (int dayOfYear = 1; dayOfYear <= LEAP_YEAR_LENGTH; dayOfYear++) {
            DayOfYear adjuster = DayOfYear.of(dayOfYear);

            assertEquals(expectedDate, adjuster.adjustInto(startOfLeapYear));

            expectedDate = expectedDate.plusDays(1);
        }
    }
}
