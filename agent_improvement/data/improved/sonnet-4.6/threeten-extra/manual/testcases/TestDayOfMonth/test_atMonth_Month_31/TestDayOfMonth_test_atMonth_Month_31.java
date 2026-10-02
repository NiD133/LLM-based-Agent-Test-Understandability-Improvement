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
 * Tests that DayOfMonth.of(31).atMonth(month) clamps to the last valid day
 * when the target month has fewer than 31 days.
 */
public class TestDayOfMonth_test_atMonth_Month_31 {

    @Test
    public void test_atMonth_Month_31() {
        DayOfMonth test = DayOfMonth.of(31);

        // Months with 31 days: day stays 31
        assertEquals(MonthDay.of(1,  31), test.atMonth(JANUARY));
        assertEquals(MonthDay.of(3,  31), test.atMonth(MARCH));
        assertEquals(MonthDay.of(5,  31), test.atMonth(MAY));
        assertEquals(MonthDay.of(7,  31), test.atMonth(JULY));
        assertEquals(MonthDay.of(8,  31), test.atMonth(AUGUST));
        assertEquals(MonthDay.of(10, 31), test.atMonth(OCTOBER));
        assertEquals(MonthDay.of(12, 31), test.atMonth(DECEMBER));

        // Months with 30 days: day is clamped to 30
        assertEquals(MonthDay.of(4,  30), test.atMonth(APRIL));
        assertEquals(MonthDay.of(6,  30), test.atMonth(JUNE));
        assertEquals(MonthDay.of(9,  30), test.atMonth(SEPTEMBER));
        assertEquals(MonthDay.of(11, 30), test.atMonth(NOVEMBER));

        // February: day is clamped to its maximum length (29)
        assertEquals(MonthDay.of(2,  29), test.atMonth(FEBRUARY));
    }
}
