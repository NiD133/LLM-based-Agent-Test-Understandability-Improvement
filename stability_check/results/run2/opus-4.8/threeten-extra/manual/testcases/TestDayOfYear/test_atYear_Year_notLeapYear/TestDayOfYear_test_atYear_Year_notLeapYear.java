package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests {@link DayOfYear#atYear(Year)} for a standard (non-leap) year.
 */
public class TestDayOfYear_test_atYear_Year_notLeapYear {

    /** A standard, non-leap year (365 days). */
    private static final Year NON_LEAP_YEAR = Year.of(2007);

    /** Number of days in a standard, non-leap year. */
    private static final int NON_LEAP_YEAR_LENGTH = 365;

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(tokyo).getDayOfYear(), DayOfYear.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_atYear_Year_notLeapYear() {
        // Walking day-by-day through a non-leap year, atYear(2007) must map
        // day-of-year 1..365 onto the matching calendar dates Jan 1 .. Dec 31.
        LocalDate expectedDate = LocalDate.of(2007, 1, 1);
        for (int dayOfYear = 1; dayOfYear <= NON_LEAP_YEAR_LENGTH; dayOfYear++) {
            DayOfYear day = DayOfYear.of(dayOfYear);

            assertEquals(expectedDate, day.atYear(NON_LEAP_YEAR));

            expectedDate = expectedDate.plusDays(1);
        }
    }
}
