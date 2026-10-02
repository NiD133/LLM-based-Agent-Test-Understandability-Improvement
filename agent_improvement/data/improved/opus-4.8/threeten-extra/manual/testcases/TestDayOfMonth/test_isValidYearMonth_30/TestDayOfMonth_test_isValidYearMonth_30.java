package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for {@link DayOfMonth#isValidYearMonth(YearMonth)} and the
 * {@code now} factory methods.
 */
public class TestDayOfMonth_test_isValidYearMonth_30 {

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfMonth(), DayOfMonth.now().getValue());
    }

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(tokyo).getDayOfMonth(), DayOfMonth.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    /**
     * The 30th is a valid day for every month except February.
     * 2012 is a leap year, so February still has only 29 days,
     * which means the 30th remains invalid for that month.
     */
    @Test
    public void test_isValidYearMonth_30() {
        DayOfMonth thirtieth = DayOfMonth.of(30);

        assertEquals(true, thirtieth.isValidYearMonth(YearMonth.of(2012, 1)));   // January   (31 days)
        assertEquals(false, thirtieth.isValidYearMonth(YearMonth.of(2012, 2)));  // February  (29 days, leap year)
        assertEquals(true, thirtieth.isValidYearMonth(YearMonth.of(2012, 3)));   // March     (31 days)
        assertEquals(true, thirtieth.isValidYearMonth(YearMonth.of(2012, 4)));   // April     (30 days)
        assertEquals(true, thirtieth.isValidYearMonth(YearMonth.of(2012, 5)));   // May       (31 days)
        assertEquals(true, thirtieth.isValidYearMonth(YearMonth.of(2012, 6)));   // June      (30 days)
        assertEquals(true, thirtieth.isValidYearMonth(YearMonth.of(2012, 7)));   // July      (31 days)
        assertEquals(true, thirtieth.isValidYearMonth(YearMonth.of(2012, 8)));   // August    (31 days)
        assertEquals(true, thirtieth.isValidYearMonth(YearMonth.of(2012, 9)));   // September (30 days)
        assertEquals(true, thirtieth.isValidYearMonth(YearMonth.of(2012, 10)));  // October   (31 days)
        assertEquals(true, thirtieth.isValidYearMonth(YearMonth.of(2012, 11)));  // November  (30 days)
        assertEquals(true, thirtieth.isValidYearMonth(YearMonth.of(2012, 12)));  // December  (31 days)
    }
}
