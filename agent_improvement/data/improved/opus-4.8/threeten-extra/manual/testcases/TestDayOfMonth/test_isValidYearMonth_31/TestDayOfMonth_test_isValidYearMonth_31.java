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
 * Tests for {@link DayOfMonth}, focusing on {@link DayOfMonth#isValidYearMonth(YearMonth)}.
 */
public class TestDayOfMonth_test_isValidYearMonth_31 {

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void now_returnsCurrentDayOfMonthInDefaultZone() {
        assertEquals(LocalDate.now().getDayOfMonth(), DayOfMonth.now().getValue());
    }

    @RetryingTest(100)
    public void now_withZone_returnsCurrentDayOfMonthInThatZone() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(tokyo).getDayOfMonth(), DayOfMonth.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // isValidYearMonth(YearMonth)
    //-----------------------------------------------------------------------
    /**
     * The 31st day is only valid in months that have 31 days.
     * Checked here against every month of 2012 (a leap year).
     */
    @Test
    public void isValidYearMonth_day31_validOnlyIn31DayMonths() {
        DayOfMonth day31 = DayOfMonth.of(31);

        assertTrue(day31.isValidYearMonth(YearMonth.of(2012, 1)));   // January   - 31 days
        assertFalse(day31.isValidYearMonth(YearMonth.of(2012, 2)));  // February  - 29 days (leap year)
        assertTrue(day31.isValidYearMonth(YearMonth.of(2012, 3)));   // March     - 31 days
        assertFalse(day31.isValidYearMonth(YearMonth.of(2012, 4)));  // April     - 30 days
        assertTrue(day31.isValidYearMonth(YearMonth.of(2012, 5)));   // May       - 31 days
        assertFalse(day31.isValidYearMonth(YearMonth.of(2012, 6)));  // June      - 30 days
        assertTrue(day31.isValidYearMonth(YearMonth.of(2012, 7)));   // July      - 31 days
        assertTrue(day31.isValidYearMonth(YearMonth.of(2012, 8)));   // August    - 31 days
        assertFalse(day31.isValidYearMonth(YearMonth.of(2012, 9)));  // September - 30 days
        assertTrue(day31.isValidYearMonth(YearMonth.of(2012, 10)));  // October   - 31 days
        assertFalse(day31.isValidYearMonth(YearMonth.of(2012, 11))); // November  - 30 days
        assertTrue(day31.isValidYearMonth(YearMonth.of(2012, 12)));  // December  - 31 days
    }
}
