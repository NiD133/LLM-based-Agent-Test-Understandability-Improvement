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

public class TestDayOfMonth_test_atMonth_Month_31 {

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
    public void test_atMonth_Month_31() {
        // Day-of-month 31 is the highest possible value. For each month, atMonth
        // should keep day 31 where the month is long enough, and otherwise clamp
        // it down to that month's last valid day (30 for short months, 29 for
        // February in this leap-year-friendly MonthDay representation).
        DayOfMonth dayThirtyFirst = DayOfMonth.of(31);

        assertEquals(MonthDay.of(1, 31), dayThirtyFirst.atMonth(JANUARY));
        assertEquals(MonthDay.of(2, 29), dayThirtyFirst.atMonth(FEBRUARY));
        assertEquals(MonthDay.of(3, 31), dayThirtyFirst.atMonth(MARCH));
        assertEquals(MonthDay.of(4, 30), dayThirtyFirst.atMonth(APRIL));
        assertEquals(MonthDay.of(5, 31), dayThirtyFirst.atMonth(MAY));
        assertEquals(MonthDay.of(6, 30), dayThirtyFirst.atMonth(JUNE));
        assertEquals(MonthDay.of(7, 31), dayThirtyFirst.atMonth(JULY));
        assertEquals(MonthDay.of(8, 31), dayThirtyFirst.atMonth(AUGUST));
        assertEquals(MonthDay.of(9, 30), dayThirtyFirst.atMonth(SEPTEMBER));
        assertEquals(MonthDay.of(10, 31), dayThirtyFirst.atMonth(OCTOBER));
        assertEquals(MonthDay.of(11, 30), dayThirtyFirst.atMonth(NOVEMBER));
        assertEquals(MonthDay.of(12, 31), dayThirtyFirst.atMonth(DECEMBER));
    }
}
