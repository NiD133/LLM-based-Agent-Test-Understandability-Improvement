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

import java.time.MonthDay;

import org.junit.jupiter.api.Test;

/**
 * Tests that DayOfMonth.atMonth(Month) produces the correct MonthDay for each month
 * when the day is 28 — the highest day that is valid in every month of the year,
 * so no clamping to the month's maximum length occurs here.
 */
public class TestDayOfMonth_test_atMonth_Month_28 {

    @Test
    public void test_atMonth_Month_28() {
        DayOfMonth day28 = DayOfMonth.of(28);

        assertEquals(MonthDay.of(1,  28), day28.atMonth(JANUARY));
        assertEquals(MonthDay.of(2,  28), day28.atMonth(FEBRUARY));
        assertEquals(MonthDay.of(3,  28), day28.atMonth(MARCH));
        assertEquals(MonthDay.of(4,  28), day28.atMonth(APRIL));
        assertEquals(MonthDay.of(5,  28), day28.atMonth(MAY));
        assertEquals(MonthDay.of(6,  28), day28.atMonth(JUNE));
        assertEquals(MonthDay.of(7,  28), day28.atMonth(JULY));
        assertEquals(MonthDay.of(8,  28), day28.atMonth(AUGUST));
        assertEquals(MonthDay.of(9,  28), day28.atMonth(SEPTEMBER));
        assertEquals(MonthDay.of(10, 28), day28.atMonth(OCTOBER));
        assertEquals(MonthDay.of(11, 28), day28.atMonth(NOVEMBER));
        assertEquals(MonthDay.of(12, 28), day28.atMonth(DECEMBER));
    }
}
