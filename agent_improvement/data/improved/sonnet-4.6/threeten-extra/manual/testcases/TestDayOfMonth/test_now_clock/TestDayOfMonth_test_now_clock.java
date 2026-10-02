package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfMonth_test_now_clock {

    private static final ZoneId PARIS = ZoneId.of("Europe/Paris");

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfMonth(), DayOfMonth.now().getValue());
    }

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfMonth(), DayOfMonth.now(zone).getValue());
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_now_clock() {
        // Verify that DayOfMonth.now(clock) correctly reads the day from the clock
        // for every day in January 2008 (a month with 31 days, covering the full 1–31 range)
        for (int dayOfMonth = 1; dayOfMonth <= 31; dayOfMonth++) {
            Instant instant = LocalDate.of(2008, 1, dayOfMonth).atStartOfDay(PARIS).toInstant();
            Clock fixedClock = Clock.fixed(instant, PARIS);
            assertEquals(dayOfMonth, DayOfMonth.now(fixedClock).getValue());
        }
    }
}
