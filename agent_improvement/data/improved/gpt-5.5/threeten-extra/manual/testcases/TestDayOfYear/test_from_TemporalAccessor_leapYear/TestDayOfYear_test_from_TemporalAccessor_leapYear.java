package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfYear_test_from_TemporalAccessor_leapYear {

    private static final int LEAP_YEAR_LENGTH = 366;

    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfYear(), DayOfYear.now(zone).getValue());
    }

    @Test
    public void test_from_TemporalAccessor_leapYear() {
        LocalDate date = LocalDate.of(2008, 1, 1);
        for (int expectedDayOfYear = 1; expectedDayOfYear <= LEAP_YEAR_LENGTH; expectedDayOfYear++) {
            DayOfYear dayOfYear = DayOfYear.from(date);
            assertEquals(expectedDayOfYear, dayOfYear.getValue());
            date = date.plusDays(1);
        }
    }
}
