package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests {@link DayOfMonth#isValidYearMonth(YearMonth)}, which checks whether a
 * day-of-month forms a valid date when combined with a given year-month.
 */
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

    //-----------------------------------------------------------------------
    /**
     * The 29th day exists in every month, even in February of a leap year, but
     * not in February of a common (non-leap) year.
     */
    @Test
    public void test_isValidYearMonth_29() {
        DayOfMonth day29 = DayOfMonth.of(29);

        // 2012 is a leap year, so the 29th is valid for all twelve of its months.
        assertTrue(day29.isValidYearMonth(YearMonth.of(2012, 1)));   // January
        assertTrue(day29.isValidYearMonth(YearMonth.of(2012, 2)));   // February (leap)
        assertTrue(day29.isValidYearMonth(YearMonth.of(2012, 3)));   // March
        assertTrue(day29.isValidYearMonth(YearMonth.of(2012, 4)));   // April
        assertTrue(day29.isValidYearMonth(YearMonth.of(2012, 5)));   // May
        assertTrue(day29.isValidYearMonth(YearMonth.of(2012, 6)));   // June
        assertTrue(day29.isValidYearMonth(YearMonth.of(2012, 7)));   // July
        assertTrue(day29.isValidYearMonth(YearMonth.of(2012, 8)));   // August
        assertTrue(day29.isValidYearMonth(YearMonth.of(2012, 9)));   // September
        assertTrue(day29.isValidYearMonth(YearMonth.of(2012, 10)));  // October
        assertTrue(day29.isValidYearMonth(YearMonth.of(2012, 11)));  // November
        assertTrue(day29.isValidYearMonth(YearMonth.of(2012, 12)));  // December

        // 2011 is not a leap year, so February 29th does not exist.
        assertFalse(day29.isValidYearMonth(YearMonth.of(2011, 2)));
    }
}
