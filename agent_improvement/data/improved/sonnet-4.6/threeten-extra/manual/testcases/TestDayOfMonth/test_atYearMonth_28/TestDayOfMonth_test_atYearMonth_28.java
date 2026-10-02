package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfMonth_test_atYearMonth_28 {

    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfMonth(), DayOfMonth.now().getValue());
    }

    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfMonth(), DayOfMonth.now(zone).getValue());
    }

    /**
     * Verifies that day 28 combined with any month of a given year always produces
     * the correct LocalDate, since every month has at least 28 days.
     */
    @Test
    public void test_atYearMonth_28() {
        DayOfMonth day28 = DayOfMonth.of(28);
        int year = 2012;

        assertEquals(LocalDate.of(year,  1, 28), day28.atYearMonth(YearMonth.of(year, Month.JANUARY)));
        assertEquals(LocalDate.of(year,  2, 28), day28.atYearMonth(YearMonth.of(year, Month.FEBRUARY)));
        assertEquals(LocalDate.of(year,  3, 28), day28.atYearMonth(YearMonth.of(year, Month.MARCH)));
        assertEquals(LocalDate.of(year,  4, 28), day28.atYearMonth(YearMonth.of(year, Month.APRIL)));
        assertEquals(LocalDate.of(year,  5, 28), day28.atYearMonth(YearMonth.of(year, Month.MAY)));
        assertEquals(LocalDate.of(year,  6, 28), day28.atYearMonth(YearMonth.of(year, Month.JUNE)));
        assertEquals(LocalDate.of(year,  7, 28), day28.atYearMonth(YearMonth.of(year, Month.JULY)));
        assertEquals(LocalDate.of(year,  8, 28), day28.atYearMonth(YearMonth.of(year, Month.AUGUST)));
        assertEquals(LocalDate.of(year,  9, 28), day28.atYearMonth(YearMonth.of(year, Month.SEPTEMBER)));
        assertEquals(LocalDate.of(year, 10, 28), day28.atYearMonth(YearMonth.of(year, Month.OCTOBER)));
        assertEquals(LocalDate.of(year, 11, 28), day28.atYearMonth(YearMonth.of(year, Month.NOVEMBER)));
        assertEquals(LocalDate.of(year, 12, 28), day28.atYearMonth(YearMonth.of(year, Month.DECEMBER)));
    }
}
