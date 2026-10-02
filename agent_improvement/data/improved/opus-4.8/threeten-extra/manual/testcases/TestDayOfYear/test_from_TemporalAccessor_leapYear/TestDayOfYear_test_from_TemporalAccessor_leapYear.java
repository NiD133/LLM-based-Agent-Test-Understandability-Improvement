package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfYear_test_from_TemporalAccessor_leapYear {

    /** Number of days in a leap year, e.g. 2008. */
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
    public void test_from_TemporalAccessor_leapYear() {
        // Walk every day of the leap year 2008 and confirm DayOfYear.from
        // extracts a day-of-year that matches the expected sequence 1..366.
        LocalDate date = LocalDate.of(2008, 1, 1);
        for (int expectedDayOfYear = 1; expectedDayOfYear <= LEAP_YEAR_LENGTH; expectedDayOfYear++) {
            DayOfYear actual = DayOfYear.from(date);

            assertEquals(expectedDayOfYear, actual.getValue());

            date = date.plusDays(1);
        }
    }
}
