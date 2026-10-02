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

import java.time.Month;
import java.time.MonthDay;

import org.junit.jupiter.api.Test;

public class TestDayOfMonth_test_atMonth_Month_31 {

    @Test
    public void test_atMonth_Month_31() {
        DayOfMonth test = DayOfMonth.of(31);

        assertAtMonth(test, JANUARY, MonthDay.of(1, 31));
        assertAtMonth(test, FEBRUARY, MonthDay.of(2, 29));
        assertAtMonth(test, MARCH, MonthDay.of(3, 31));
        assertAtMonth(test, APRIL, MonthDay.of(4, 30));
        assertAtMonth(test, MAY, MonthDay.of(5, 31));
        assertAtMonth(test, JUNE, MonthDay.of(6, 30));
        assertAtMonth(test, JULY, MonthDay.of(7, 31));
        assertAtMonth(test, AUGUST, MonthDay.of(8, 31));
        assertAtMonth(test, SEPTEMBER, MonthDay.of(9, 30));
        assertAtMonth(test, OCTOBER, MonthDay.of(10, 31));
        assertAtMonth(test, NOVEMBER, MonthDay.of(11, 30));
        assertAtMonth(test, DECEMBER, MonthDay.of(12, 31));
    }

    private static void assertAtMonth(DayOfMonth day, Month month, MonthDay expectedMonthDay) {
        assertEquals(expectedMonthDay, day.atMonth(month));
    }
}
