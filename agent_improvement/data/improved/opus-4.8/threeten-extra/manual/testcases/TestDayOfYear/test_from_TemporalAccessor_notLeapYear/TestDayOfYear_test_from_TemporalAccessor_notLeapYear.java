package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests the factory methods on {@link DayOfYear} that derive a day-of-year
 * from "now" and from a {@link java.time.temporal.TemporalAccessor}.
 */
public class TestDayOfYear_test_from_TemporalAccessor_notLeapYear {

    /** Number of days in a standard (non-leap) year, e.g. 2007. */
    private static final int DAYS_IN_STANDARD_YEAR = 365;

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        // DayOfYear.now() must match the day-of-year of LocalDate.now().
        int expectedDayOfYear = LocalDate.now().getDayOfYear();
        assertEquals(expectedDayOfYear, DayOfYear.now().getValue());
    }

    //-----------------------------------------------------------------------
    // now(ZoneId)
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        // DayOfYear.now(zone) must match LocalDate.now(zone) for the same zone.
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        int expectedDayOfYear = LocalDate.now(tokyo).getDayOfYear();
        assertEquals(expectedDayOfYear, DayOfYear.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // from(TemporalAccessor) across a non-leap year
    //-----------------------------------------------------------------------
    @Test
    public void test_from_TemporalAccessor_notLeapYear() {
        // Walk every day of the standard (non-leap) year 2007 and verify that
        // DayOfYear.from(date) returns the running day-of-year 1..365.
        LocalDate date = LocalDate.of(2007, 1, 1);
        for (int expectedDayOfYear = 1; expectedDayOfYear <= DAYS_IN_STANDARD_YEAR; expectedDayOfYear++) {
            assertEquals(expectedDayOfYear, DayOfYear.from(date).getValue());
            date = date.plusDays(1);
        }

        // The loop leaves 'date' on 1 Jan 2008, whose day-of-year wraps back to 1.
        assertEquals(1, DayOfYear.from(date).getValue());
    }
}
