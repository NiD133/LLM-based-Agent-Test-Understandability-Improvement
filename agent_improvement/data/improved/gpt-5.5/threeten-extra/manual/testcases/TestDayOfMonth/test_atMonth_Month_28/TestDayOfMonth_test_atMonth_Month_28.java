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

public class TestDayOfMonth_test_atMonth_Month_28 {

    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfMonth(), DayOfMonth.now().getValue());
    }

    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfMonth(), DayOfMonth.now(zone).getValue());
    }

    @Test
    public void test_atMonth_Month_28() {
        DayOfMonth test = DayOfMonth.of(28);

        assertEquals(MonthDay.of(1, 28), test.atMonth(JANUARY));
        assertEquals(MonthDay.of(2, 28), test.atMonth(FEBRUARY));
        assertEquals(MonthDay.of(3, 28), test.atMonth(MARCH));
        assertEquals(MonthDay.of(4, 28), test.atMonth(APRIL));
        assertEquals(MonthDay.of(5, 28), test.atMonth(MAY));
        assertEquals(MonthDay.of(6, 28), test.atMonth(JUNE));
        assertEquals(MonthDay.of(7, 28), test.atMonth(JULY));
        assertEquals(MonthDay.of(8, 28), test.atMonth(AUGUST));
        assertEquals(MonthDay.of(9, 28), test.atMonth(SEPTEMBER));
        assertEquals(MonthDay.of(10, 28), test.atMonth(OCTOBER));
        assertEquals(MonthDay.of(11, 28), test.atMonth(NOVEMBER));
        assertEquals(MonthDay.of(12, 28), test.atMonth(DECEMBER));
    }
}
