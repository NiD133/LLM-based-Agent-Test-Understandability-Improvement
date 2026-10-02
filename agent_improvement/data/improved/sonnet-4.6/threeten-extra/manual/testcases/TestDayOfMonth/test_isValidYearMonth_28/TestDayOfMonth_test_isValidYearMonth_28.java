package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfMonth_test_isValidYearMonth_28 {

    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfMonth(), DayOfMonth.now().getValue());
    }

    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfMonth(), DayOfMonth.now(zone).getValue());
    }

    @Test
    public void test_isValidYearMonth_28() {
        DayOfMonth day28 = DayOfMonth.of(28);
        // Day 28 is valid in every month of 2012, including February (2012 is a leap year with 29 days in Feb)
        assertTrue(day28.isValidYearMonth(YearMonth.of(2012, 1)));
        assertTrue(day28.isValidYearMonth(YearMonth.of(2012, 2)));
        assertTrue(day28.isValidYearMonth(YearMonth.of(2012, 3)));
        assertTrue(day28.isValidYearMonth(YearMonth.of(2012, 4)));
        assertTrue(day28.isValidYearMonth(YearMonth.of(2012, 5)));
        assertTrue(day28.isValidYearMonth(YearMonth.of(2012, 6)));
        assertTrue(day28.isValidYearMonth(YearMonth.of(2012, 7)));
        assertTrue(day28.isValidYearMonth(YearMonth.of(2012, 8)));
        assertTrue(day28.isValidYearMonth(YearMonth.of(2012, 9)));
        assertTrue(day28.isValidYearMonth(YearMonth.of(2012, 10)));
        assertTrue(day28.isValidYearMonth(YearMonth.of(2012, 11)));
        assertTrue(day28.isValidYearMonth(YearMonth.of(2012, 12)));
    }
}
