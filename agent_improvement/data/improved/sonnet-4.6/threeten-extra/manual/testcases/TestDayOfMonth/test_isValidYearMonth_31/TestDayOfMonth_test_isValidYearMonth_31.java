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

public class TestDayOfMonth_test_isValidYearMonth_31 {

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
    @Test
    public void test_isValidYearMonth_31() {
        DayOfMonth day31 = DayOfMonth.of(31);

        // Months with 31 days — day 31 is valid
        assertTrue(day31.isValidYearMonth(YearMonth.of(2012, Month.JANUARY)));
        assertTrue(day31.isValidYearMonth(YearMonth.of(2012, Month.MARCH)));
        assertTrue(day31.isValidYearMonth(YearMonth.of(2012, Month.MAY)));
        assertTrue(day31.isValidYearMonth(YearMonth.of(2012, Month.JULY)));
        assertTrue(day31.isValidYearMonth(YearMonth.of(2012, Month.AUGUST)));
        assertTrue(day31.isValidYearMonth(YearMonth.of(2012, Month.OCTOBER)));
        assertTrue(day31.isValidYearMonth(YearMonth.of(2012, Month.DECEMBER)));

        // Months with fewer than 31 days — day 31 is invalid
        assertFalse(day31.isValidYearMonth(YearMonth.of(2012, Month.FEBRUARY)));
        assertFalse(day31.isValidYearMonth(YearMonth.of(2012, Month.APRIL)));
        assertFalse(day31.isValidYearMonth(YearMonth.of(2012, Month.JUNE)));
        assertFalse(day31.isValidYearMonth(YearMonth.of(2012, Month.SEPTEMBER)));
        assertFalse(day31.isValidYearMonth(YearMonth.of(2012, Month.NOVEMBER)));
    }
}
