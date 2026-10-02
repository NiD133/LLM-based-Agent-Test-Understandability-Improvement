package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests the {@code DayOfMonth.now(...)} factory methods, which obtain the
 * current day-of-month from a clock or time-zone.
 */
public class TestDayOfMonth_test_now_clock {

    /** Highest possible day-of-month, used as the upper bound when sweeping a month. */
    private static final int LAST_DAY_OF_MONTH = 31;

    private static final ZoneId PARIS = ZoneId.of("Europe/Paris");

    //-----------------------------------------------------------------------
    // now() - uses the system clock in the default time-zone
    //-----------------------------------------------------------------------
    // Retried because the day-of-month could roll over between the two reads
    // of the (live) system clock around midnight.
    @RetryingTest(100)
    public void test_now() {
        int expectedDay = LocalDate.now().getDayOfMonth();
        assertEquals(expectedDay, DayOfMonth.now().getValue());
    }

    //-----------------------------------------------------------------------
    // now(ZoneId) - uses the system clock in an explicit time-zone
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        int expectedDay = LocalDate.now(tokyo).getDayOfMonth();
        assertEquals(expectedDay, DayOfMonth.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // now(Clock) - uses a fixed clock, so the result is fully deterministic
    //-----------------------------------------------------------------------
    @Test
    public void test_now_clock() {
        // Walk through every day of January 2008 (a month with all 31 days).
        // For each day, pin a clock to the start of that day in Paris and
        // verify DayOfMonth.now(clock) reports exactly that day.
        for (int day = 1; day <= LAST_DAY_OF_MONTH; day++) {
            Instant startOfDay = LocalDate.of(2008, 1, day).atStartOfDay(PARIS).toInstant();
            Clock fixedClock = Clock.fixed(startOfDay, PARIS);

            assertEquals(day, DayOfMonth.now(fixedClock).getValue());
        }
    }
}
