package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfMonth_test_atYearMonth_28 {

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
    public void test_atYearMonth_28() {
        int year = 2012;
        int dayOfMonth = 28;
        DayOfMonth test = DayOfMonth.of(dayOfMonth);

        for (int month = 1; month <= 12; month++) {
            assertEquals(
                    LocalDate.of(year, month, dayOfMonth),
                    test.atYearMonth(YearMonth.of(year, month)));
        }
    }
}
