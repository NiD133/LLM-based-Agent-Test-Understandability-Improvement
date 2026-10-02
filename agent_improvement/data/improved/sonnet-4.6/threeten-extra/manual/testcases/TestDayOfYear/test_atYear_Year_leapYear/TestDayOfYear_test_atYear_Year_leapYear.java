package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfYear_test_atYear_Year_leapYear {

    private static final Year YEAR_LEAP = Year.of(2008);

    private static final int LEAP_YEAR_LENGTH = 366;

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

    // Verifies that every day-of-year ordinal (1–366) maps to the correct LocalDate
    // when combined with a leap year (2008), covering the full 366-day calendar.
    @Test
    public void test_atYear_Year_leapYear() {
        LocalDate expected = LocalDate.of(2008, 1, 1);
        for (int i = 1; i <= LEAP_YEAR_LENGTH; i++) {
            DayOfYear test = DayOfYear.of(i);
            assertEquals(expected, test.atYear(YEAR_LEAP));
            expected = expected.plusDays(1);
        }
    }
}
