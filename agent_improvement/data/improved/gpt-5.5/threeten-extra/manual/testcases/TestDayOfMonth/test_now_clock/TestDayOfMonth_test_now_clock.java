package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfMonth_test_now_clock {

    private static final int DAYS_IN_JANUARY = 31;
    private static final int TEST_YEAR = 2008;
    private static final int JANUARY = 1;
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
        for (int dayOfMonth = 1; dayOfMonth <= DAYS_IN_JANUARY; dayOfMonth++) {
            Clock clock = fixedParisClockOnJanuaryDay(dayOfMonth);
            assertEquals(dayOfMonth, DayOfMonth.now(clock).getValue());
        }
    }

    private Clock fixedParisClockOnJanuaryDay(int dayOfMonth) {
        Instant instant = LocalDate.of(TEST_YEAR, JANUARY, dayOfMonth)
                .atStartOfDay(PARIS)
                .toInstant();
        return Clock.fixed(instant, PARIS);
    }
}
