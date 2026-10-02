package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.ZoneId;

import org.junitpioneer.jupiter.RetryingTest;
import org.junit.jupiter.api.Test;

public class TestDayOfMonth_test_atMonth_int_28 {

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
    public void test_atMonth_int_28() {
        DayOfMonth dayOfMonth = DayOfMonth.of(28);

        assertEquals(MonthDay.of(1, 28), dayOfMonth.atMonth(1));
        assertEquals(MonthDay.of(2, 28), dayOfMonth.atMonth(2));
        assertEquals(MonthDay.of(3, 28), dayOfMonth.atMonth(3));
        assertEquals(MonthDay.of(4, 28), dayOfMonth.atMonth(4));
        assertEquals(MonthDay.of(5, 28), dayOfMonth.atMonth(5));
        assertEquals(MonthDay.of(6, 28), dayOfMonth.atMonth(6));
        assertEquals(MonthDay.of(7, 28), dayOfMonth.atMonth(7));
        assertEquals(MonthDay.of(8, 28), dayOfMonth.atMonth(8));
        assertEquals(MonthDay.of(9, 28), dayOfMonth.atMonth(9));
        assertEquals(MonthDay.of(10, 28), dayOfMonth.atMonth(10));
        assertEquals(MonthDay.of(11, 28), dayOfMonth.atMonth(11));
        assertEquals(MonthDay.of(12, 28), dayOfMonth.atMonth(12));
    }
}
