package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.ZoneId;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfMonth_test_atMonth_int_28 {

    // Day 28 is the largest day valid in every month, including February
    private static final int DAY_28 = 28;

    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfMonth(), DayOfMonth.now().getValue());
    }

    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfMonth(), DayOfMonth.now(zone).getValue());
    }

    // Verify that day 28 combined with each month (1–12) produces the correct MonthDay
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12})
    public void test_atMonth_int_28(int month) {
        DayOfMonth dayOfMonth = DayOfMonth.of(DAY_28);
        assertEquals(MonthDay.of(month, DAY_28), dayOfMonth.atMonth(month));
    }
}
