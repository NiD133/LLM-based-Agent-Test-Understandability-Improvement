package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfYear_test_atYear_Year_notLeapYear {

    private static final Year YEAR_STANDARD = Year.of(2007);

    /** Number of days in a non-leap (standard) year. */
    private static final int STANDARD_YEAR_LENGTH = 365;

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
    public void test_atYear_Year_notLeapYear() {
        // In a standard year, DayOfYear.of(i).atYear(2007) should map each
        // day-of-year (1..365) to the matching calendar date, starting at Jan 1.
        LocalDate expectedDate = LocalDate.of(2007, 1, 1);
        for (int dayOfYear = 1; dayOfYear <= STANDARD_YEAR_LENGTH; dayOfYear++) {
            DayOfYear day = DayOfYear.of(dayOfYear);
            assertEquals(expectedDate, day.atYear(YEAR_STANDARD));
            expectedDate = expectedDate.plusDays(1);
        }
    }
}
