package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfYear_test_now_clock_notLeapYear {

    private static final int STANDARD_YEAR_LENGTH = 365;

    private static final ZoneId PARIS = ZoneId.of("Europe/Paris");

    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfYear(), DayOfYear.now(zone).getValue());
    }

    // Verify that DayOfYear.now(clock) correctly reports each day of a standard (non-leap) year
    @Test
    public void test_now_clock_notLeapYear() {
        LocalDate date = LocalDate.of(2007, 1, 1);
        for (int expectedDay = 1; expectedDay <= STANDARD_YEAR_LENGTH; expectedDay++) {
            Instant instant = date.atStartOfDay(PARIS).toInstant();
            Clock clock = Clock.fixed(instant, PARIS);
            DayOfYear dayOfYear = DayOfYear.now(clock);
            assertEquals(expectedDay, dayOfYear.getValue());
            date = date.plusDays(1);
        }
    }
}
