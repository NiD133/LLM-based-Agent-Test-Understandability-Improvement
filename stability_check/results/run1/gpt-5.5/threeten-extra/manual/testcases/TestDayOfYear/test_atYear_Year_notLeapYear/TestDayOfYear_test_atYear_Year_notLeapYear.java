package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfYear_test_atYear_Year_notLeapYear {

    private static final Year NON_LEAP_YEAR = Year.of(2007);
    private static final int DAYS_IN_NON_LEAP_YEAR = 365;
    private static final LocalDate FIRST_DAY_OF_NON_LEAP_YEAR = LocalDate.of(2007, 1, 1);

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
    public void test_atYear_Year_notLeapYear() {
        LocalDate expectedDate = FIRST_DAY_OF_NON_LEAP_YEAR;

        for (int dayOfYear = 1; dayOfYear <= DAYS_IN_NON_LEAP_YEAR; dayOfYear++) {
            DayOfYear actualDayOfYear = DayOfYear.of(dayOfYear);

            assertEquals(expectedDate, actualDayOfYear.atYear(NON_LEAP_YEAR));
            expectedDate = expectedDate.plusDays(1);
        }
    }
}
