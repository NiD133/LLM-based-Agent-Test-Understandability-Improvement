package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests {@link DayOfMonth#atMonth(int)} for the 28th of the month.
 */
public class TestDayOfMonth_test_atMonth_int_28 {

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
    public void test_atMonth_int_28() {
        DayOfMonth dayOfMonth = DayOfMonth.of(28);

        // Day 28 is valid in every month (it is the shortest month's length),
        // so atMonth(month) always yields the 28th and is never clamped.
        for (int month = 1; month <= 12; month++) {
            assertEquals(MonthDay.of(month, 28), dayOfMonth.atMonth(month));
        }
    }
}
