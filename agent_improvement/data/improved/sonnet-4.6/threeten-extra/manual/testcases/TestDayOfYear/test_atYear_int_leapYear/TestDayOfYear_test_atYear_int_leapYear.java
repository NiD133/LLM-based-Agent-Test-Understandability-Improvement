package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfYear_test_atYear_int_leapYear {

    // 2008 is a leap year, so it has 366 days
    private static final int LEAP_YEAR_LENGTH = 366;
    private static final int LEAP_YEAR = 2008;

    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfYear(), DayOfYear.now(zone).getValue());
    }

    // Verifies that every day-of-year from 1 to 366 maps to the correct LocalDate in a leap year
    @Test
    public void test_atYear_int_leapYear() {
        LocalDate expected = LocalDate.of(LEAP_YEAR, 1, 1);
        for (int dayNumber = 1; dayNumber <= LEAP_YEAR_LENGTH; dayNumber++) {
            DayOfYear dayOfYear = DayOfYear.of(dayNumber);
            assertEquals(expected, dayOfYear.atYear(LEAP_YEAR));
            expected = expected.plusDays(1);
        }
    }
}
