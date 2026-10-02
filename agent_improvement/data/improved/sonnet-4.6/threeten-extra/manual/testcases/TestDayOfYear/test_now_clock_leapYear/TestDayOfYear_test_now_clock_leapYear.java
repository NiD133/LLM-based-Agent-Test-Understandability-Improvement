package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfYear_test_now_clock_leapYear {

    private static final Year YEAR_LEAP = Year.of(2008);

    private static final int LEAP_YEAR_LENGTH = 366;

    private static final ZoneId PARIS = ZoneId.of("Europe/Paris");

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

    // Verifies that DayOfYear.now(clock) returns the correct day for every day of a leap year (366 days).
    @Test
    public void test_now_clock_leapYear() {
        LocalDate date = LocalDate.of(YEAR_LEAP.getValue(), 1, 1);
        for (int i = 1; i <= LEAP_YEAR_LENGTH; i++) {
            Instant instant = date.atStartOfDay(PARIS).toInstant();
            Clock clock = Clock.fixed(instant, PARIS);
            DayOfYear test = DayOfYear.now(clock);
            assertEquals(i, test.getValue());
            date = date.plusDays(1);
        }
    }
}
