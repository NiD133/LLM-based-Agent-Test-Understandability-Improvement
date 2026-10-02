package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfYear_test_atYear_int_leapYear {

    /** 2008 is a leap year, so it has 366 days. */
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
    public void test_atYear_int_leapYear() {
        // For each day-of-year in the leap year 2008, atYear(2008) must map
        // back to the calendar date that has that day-of-year, walking forward
        // one day at a time from January 1st through December 31st.
        LocalDate expectedDate = LocalDate.of(2008, 1, 1);
        for (int dayOfYear = 1; dayOfYear <= LEAP_YEAR_LENGTH; dayOfYear++) {
            DayOfYear test = DayOfYear.of(dayOfYear);

            assertEquals(expectedDate, test.atYear(2008));

            expectedDate = expectedDate.plusDays(1);
        }
    }
}
