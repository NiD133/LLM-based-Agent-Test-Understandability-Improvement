package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfYear_test_now_clock_leapYear {

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

    @Test
    public void test_now_clock_leapYear() {
        LocalDate currentDate = LocalDate.of(2008, 1, 1);
        for (int expectedDayOfYear = 1; expectedDayOfYear <= LEAP_YEAR_LENGTH; expectedDayOfYear++) {
            Instant startOfDayInParis = currentDate.atStartOfDay(PARIS).toInstant();
            Clock fixedClock = Clock.fixed(startOfDayInParis, PARIS);

            DayOfYear actualDayOfYear = DayOfYear.now(fixedClock);

            assertEquals(expectedDayOfYear, actualDayOfYear.getValue());
            currentDate = currentDate.plusDays(1);
        }
    }
}
