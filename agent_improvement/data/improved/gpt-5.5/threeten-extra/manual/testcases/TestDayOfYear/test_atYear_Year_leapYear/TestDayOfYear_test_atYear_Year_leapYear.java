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
    private static final LocalDate FIRST_DAY_OF_LEAP_YEAR = LocalDate.of(2008, 1, 1);

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
    public void test_atYear_Year_leapYear() {
        LocalDate expectedDate = FIRST_DAY_OF_LEAP_YEAR;

        for (int dayOfYear = 1; dayOfYear <= LEAP_YEAR_LENGTH; dayOfYear++) {
            DayOfYear test = DayOfYear.of(dayOfYear);

            assertEquals(expectedDate, test.atYear(YEAR_LEAP));
            expectedDate = expectedDate.plusDays(1);
        }
    }
}
