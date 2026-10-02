package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.MonthDay;

import org.junit.jupiter.api.Test;

public class TestDayOfMonth_test_atMonth_int_31 {

    @Test
    public void test_atMonth_int_31() {
        DayOfMonth dayOfMonth = DayOfMonth.of(31);

        assertAtMonthReturnsLastValidDay(dayOfMonth, 1, 31);
        assertAtMonthReturnsLastValidDay(dayOfMonth, 2, 29);
        assertAtMonthReturnsLastValidDay(dayOfMonth, 3, 31);
        assertAtMonthReturnsLastValidDay(dayOfMonth, 4, 30);
        assertAtMonthReturnsLastValidDay(dayOfMonth, 5, 31);
        assertAtMonthReturnsLastValidDay(dayOfMonth, 6, 30);
        assertAtMonthReturnsLastValidDay(dayOfMonth, 7, 31);
        assertAtMonthReturnsLastValidDay(dayOfMonth, 8, 31);
        assertAtMonthReturnsLastValidDay(dayOfMonth, 9, 30);
        assertAtMonthReturnsLastValidDay(dayOfMonth, 10, 31);
        assertAtMonthReturnsLastValidDay(dayOfMonth, 11, 30);
        assertAtMonthReturnsLastValidDay(dayOfMonth, 12, 31);
    }

    private static void assertAtMonthReturnsLastValidDay(
            DayOfMonth dayOfMonth,
            int month,
            int expectedDay) {

        assertEquals(MonthDay.of(month, expectedDay), dayOfMonth.atMonth(month));
    }
}
