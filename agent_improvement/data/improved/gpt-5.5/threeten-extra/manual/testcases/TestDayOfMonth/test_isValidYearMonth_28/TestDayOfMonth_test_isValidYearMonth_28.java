package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfMonth_test_isValidYearMonth_28 {

    private static final int DAY_VALID_IN_EVERY_MONTH = 28;
    private static final int LEAP_YEAR = 2012;

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
    public void test_isValidYearMonth_28() {
        DayOfMonth test = DayOfMonth.of(DAY_VALID_IN_EVERY_MONTH);

        assertValidYearMonth(test, 1);
        assertValidYearMonth(test, 2);
        assertValidYearMonth(test, 3);
        assertValidYearMonth(test, 4);
        assertValidYearMonth(test, 5);
        assertValidYearMonth(test, 6);
        assertValidYearMonth(test, 7);
        assertValidYearMonth(test, 8);
        assertValidYearMonth(test, 9);
        assertValidYearMonth(test, 10);
        assertValidYearMonth(test, 11);
        assertValidYearMonth(test, 12);
    }

    private static void assertValidYearMonth(DayOfMonth dayOfMonth, int month) {
        assertEquals(true, dayOfMonth.isValidYearMonth(YearMonth.of(LEAP_YEAR, month)));
    }
}
