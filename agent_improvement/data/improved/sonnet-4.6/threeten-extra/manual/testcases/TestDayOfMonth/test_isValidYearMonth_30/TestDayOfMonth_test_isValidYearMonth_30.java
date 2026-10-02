package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfMonth_test_isValidYearMonth_30 {

    private static final DayOfMonth TEST = DayOfMonth.of(12);

    private static final ZoneId PARIS = ZoneId.of("Europe/Paris");

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

    @Test
    public void test_isValidYearMonth_30() {
        // Day 30 is valid in all months except February, which never reaches 30 days
        DayOfMonth day30 = DayOfMonth.of(30);

        assertTrue(day30.isValidYearMonth(YearMonth.of(2012, Month.JANUARY)));
        assertFalse(day30.isValidYearMonth(YearMonth.of(2012, Month.FEBRUARY)));
        assertTrue(day30.isValidYearMonth(YearMonth.of(2012, Month.MARCH)));
        assertTrue(day30.isValidYearMonth(YearMonth.of(2012, Month.APRIL)));
        assertTrue(day30.isValidYearMonth(YearMonth.of(2012, Month.MAY)));
        assertTrue(day30.isValidYearMonth(YearMonth.of(2012, Month.JUNE)));
        assertTrue(day30.isValidYearMonth(YearMonth.of(2012, Month.JULY)));
        assertTrue(day30.isValidYearMonth(YearMonth.of(2012, Month.AUGUST)));
        assertTrue(day30.isValidYearMonth(YearMonth.of(2012, Month.SEPTEMBER)));
        assertTrue(day30.isValidYearMonth(YearMonth.of(2012, Month.OCTOBER)));
        assertTrue(day30.isValidYearMonth(YearMonth.of(2012, Month.NOVEMBER)));
        assertTrue(day30.isValidYearMonth(YearMonth.of(2012, Month.DECEMBER)));
    }
}
