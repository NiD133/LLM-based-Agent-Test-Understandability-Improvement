package org.threeten.extra;

import static java.time.Month.APRIL;
import static java.time.Month.AUGUST;
import static java.time.Month.DECEMBER;
import static java.time.Month.FEBRUARY;
import static java.time.Month.JANUARY;
import static java.time.Month.JULY;
import static java.time.Month.JUNE;
import static java.time.Month.MARCH;
import static java.time.Month.MAY;
import static java.time.Month.NOVEMBER;
import static java.time.Month.OCTOBER;
import static java.time.Month.SEPTEMBER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfMonth_test_isValidYearMonth_29 {

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

    // Day 29 is valid in every month of a leap year (2012), but invalid in February of a non-leap year (2011).
    @Test
    public void test_isValidYearMonth_29() {
        DayOfMonth test = DayOfMonth.of(29);
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, JANUARY)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, FEBRUARY)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, MARCH)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, APRIL)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, MAY)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, JUNE)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, JULY)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, AUGUST)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, SEPTEMBER)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, OCTOBER)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, NOVEMBER)));
        assertTrue(test.isValidYearMonth(YearMonth.of(2012, DECEMBER)));
        assertFalse(test.isValidYearMonth(YearMonth.of(2011, FEBRUARY)));
    }
}
