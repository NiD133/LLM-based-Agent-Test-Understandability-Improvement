package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests the {@code DayOfYear.now(...)} factory methods.
 */
public class TestDayOfYear_test_now_clock_leapYear {

    /** Number of days in a leap year (2008). */
    private static final int LEAP_YEAR_LENGTH = 366;

    /** Fixed time-zone used to build the test clocks. */
    private static final ZoneId PARIS = ZoneId.of("Europe/Paris");

    //-----------------------------------------------------------------------
    // now() and now(ZoneId) should agree with LocalDate's day-of-year.
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(tokyo).getDayOfYear(), DayOfYear.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // now(Clock): walking through every day of a leap year (2008) should
    // yield day-of-year values 1..366 in order.
    //-----------------------------------------------------------------------
    @Test
    public void test_now_clock_leapYear() {
        LocalDate date = LocalDate.of(2008, 1, 1);
        for (int expectedDayOfYear = 1; expectedDayOfYear <= LEAP_YEAR_LENGTH; expectedDayOfYear++) {
            Instant startOfDay = date.atStartOfDay(PARIS).toInstant();
            Clock clock = Clock.fixed(startOfDay, PARIS);

            DayOfYear actual = DayOfYear.now(clock);

            assertEquals(expectedDayOfYear, actual.getValue());
            date = date.plusDays(1);
        }
    }
}
