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

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for {@link DayOfMonth#atMonth(java.time.Month)}.
 */
public class TestDayOfMonth_test_atMonth_Month_28 {

    /**
     * Verifies that {@code now()} reports the same day-of-month as the system clock.
     * Retried because the value can change at midnight while the test runs.
     */
    @RetryingTest(100)
    public void now_matchesSystemClock() {
        int expectedDay = LocalDate.now().getDayOfMonth();
        assertEquals(expectedDay, DayOfMonth.now().getValue());
    }

    /**
     * Verifies that {@code now(ZoneId)} reports the same day-of-month as the clock in that zone.
     * Retried because the value can change at midnight while the test runs.
     */
    @RetryingTest(100)
    public void now_withZone_matchesZonedClock() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        int expectedDay = LocalDate.now(tokyo).getDayOfMonth();
        assertEquals(expectedDay, DayOfMonth.now(tokyo).getValue());
    }

    /**
     * Day 28 is valid in every month (it never exceeds any month's length, not even February),
     * so {@code atMonth} should keep the day unchanged for all twelve months.
     */
    @Test
    public void atMonth_day28_keepsDayInEveryMonth() {
        DayOfMonth day28 = DayOfMonth.of(28);

        assertEquals(MonthDay.of(1, 28), day28.atMonth(JANUARY));
        assertEquals(MonthDay.of(2, 28), day28.atMonth(FEBRUARY));
        assertEquals(MonthDay.of(3, 28), day28.atMonth(MARCH));
        assertEquals(MonthDay.of(4, 28), day28.atMonth(APRIL));
        assertEquals(MonthDay.of(5, 28), day28.atMonth(MAY));
        assertEquals(MonthDay.of(6, 28), day28.atMonth(JUNE));
        assertEquals(MonthDay.of(7, 28), day28.atMonth(JULY));
        assertEquals(MonthDay.of(8, 28), day28.atMonth(AUGUST));
        assertEquals(MonthDay.of(9, 28), day28.atMonth(SEPTEMBER));
        assertEquals(MonthDay.of(10, 28), day28.atMonth(OCTOBER));
        assertEquals(MonthDay.of(11, 28), day28.atMonth(NOVEMBER));
        assertEquals(MonthDay.of(12, 28), day28.atMonth(DECEMBER));
    }
}
