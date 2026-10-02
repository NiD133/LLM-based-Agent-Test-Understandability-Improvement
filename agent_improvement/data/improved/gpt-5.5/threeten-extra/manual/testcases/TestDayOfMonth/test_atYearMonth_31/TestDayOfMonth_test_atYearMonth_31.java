package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfMonth_test_atYearMonth_31 {

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
    public void test_atYearMonth_31() {
        DayOfMonth endOfMonth = DayOfMonth.of(31);

        assertAtYearMonth(endOfMonth, 2012, 1, 31);
        assertAtYearMonth(endOfMonth, 2012, 2, 29);
        assertAtYearMonth(endOfMonth, 2012, 3, 31);
        assertAtYearMonth(endOfMonth, 2012, 4, 30);
        assertAtYearMonth(endOfMonth, 2012, 5, 31);
        assertAtYearMonth(endOfMonth, 2012, 6, 30);
        assertAtYearMonth(endOfMonth, 2012, 7, 31);
        assertAtYearMonth(endOfMonth, 2012, 8, 31);
        assertAtYearMonth(endOfMonth, 2012, 9, 30);
        assertAtYearMonth(endOfMonth, 2012, 10, 31);
        assertAtYearMonth(endOfMonth, 2012, 11, 30);
        assertAtYearMonth(endOfMonth, 2012, 12, 31);
        assertAtYearMonth(endOfMonth, 2011, 2, 28);
    }

    private static void assertAtYearMonth(DayOfMonth dayOfMonth, int year, int month, int expectedDay) {
        assertEquals(
                LocalDate.of(year, month, expectedDay),
                dayOfMonth.atYearMonth(YearMonth.of(year, month)));
    }
}
