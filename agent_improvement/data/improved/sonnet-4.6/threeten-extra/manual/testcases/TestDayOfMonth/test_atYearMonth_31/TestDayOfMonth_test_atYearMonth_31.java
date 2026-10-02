package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfMonth_test_atYearMonth_31 {

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
    // DayOfMonth.of(31).atYearMonth() clamps to the last valid day of the month.
    // Months with 31 days return day 31; months with 30 days return day 30;
    // February returns 29 in a leap year and 28 in a non-leap year.
    @Test
    public void test_atYearMonth_31() {
        DayOfMonth day31 = DayOfMonth.of(31);

        // Months that have 31 days — no clamping needed
        assertEquals(LocalDate.of(2012,  1, 31), day31.atYearMonth(YearMonth.of(2012,  1)));
        assertEquals(LocalDate.of(2012,  3, 31), day31.atYearMonth(YearMonth.of(2012,  3)));
        assertEquals(LocalDate.of(2012,  5, 31), day31.atYearMonth(YearMonth.of(2012,  5)));
        assertEquals(LocalDate.of(2012,  7, 31), day31.atYearMonth(YearMonth.of(2012,  7)));
        assertEquals(LocalDate.of(2012,  8, 31), day31.atYearMonth(YearMonth.of(2012,  8)));
        assertEquals(LocalDate.of(2012, 10, 31), day31.atYearMonth(YearMonth.of(2012, 10)));
        assertEquals(LocalDate.of(2012, 12, 31), day31.atYearMonth(YearMonth.of(2012, 12)));

        // Months that have 30 days — clamped to day 30
        assertEquals(LocalDate.of(2012,  4, 30), day31.atYearMonth(YearMonth.of(2012,  4)));
        assertEquals(LocalDate.of(2012,  6, 30), day31.atYearMonth(YearMonth.of(2012,  6)));
        assertEquals(LocalDate.of(2012,  9, 30), day31.atYearMonth(YearMonth.of(2012,  9)));
        assertEquals(LocalDate.of(2012, 11, 30), day31.atYearMonth(YearMonth.of(2012, 11)));

        // February in a leap year (2012) — clamped to day 29
        assertEquals(LocalDate.of(2012,  2, 29), day31.atYearMonth(YearMonth.of(2012,  2)));

        // February in a non-leap year (2011) — clamped to day 28
        assertEquals(LocalDate.of(2011,  2, 28), day31.atYearMonth(YearMonth.of(2011,  2)));
    }
}
