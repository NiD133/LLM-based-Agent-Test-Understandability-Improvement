package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfMonth_test_atMonth_int_31 {

    private static final int MAX_LENGTH = 31;

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

    //-----------------------------------------------------------------------
    /**
     * When DayOfMonth is 31, atMonth(int) must clamp to the actual maximum day of
     * each month: 31-day months keep day 31, 30-day months clamp to 30, and
     * February clamps to its maximum of 29.
     */
    @Test
    public void test_atMonth_int_31() {
        DayOfMonth test = DayOfMonth.of(31);

        // Months with 31 days — no clamping needed
        assertEquals(MonthDay.of(1, 31), test.atMonth(1));   // January
        assertEquals(MonthDay.of(3, 31), test.atMonth(3));   // March
        assertEquals(MonthDay.of(5, 31), test.atMonth(5));   // May
        assertEquals(MonthDay.of(7, 31), test.atMonth(7));   // July
        assertEquals(MonthDay.of(8, 31), test.atMonth(8));   // August
        assertEquals(MonthDay.of(10, 31), test.atMonth(10)); // October
        assertEquals(MonthDay.of(12, 31), test.atMonth(12)); // December

        // Months with 30 days — day 31 clamps to 30
        assertEquals(MonthDay.of(4, 30), test.atMonth(4));   // April
        assertEquals(MonthDay.of(6, 30), test.atMonth(6));   // June
        assertEquals(MonthDay.of(9, 30), test.atMonth(9));   // September
        assertEquals(MonthDay.of(11, 30), test.atMonth(11)); // November

        // February — day 31 clamps to 29 (the month's maximum regardless of leap year)
        assertEquals(MonthDay.of(2, 29), test.atMonth(2));
    }
}
