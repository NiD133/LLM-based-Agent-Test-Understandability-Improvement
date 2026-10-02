package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfMonth_test_isValidYearMonth_31 {

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
    public void test_isValidYearMonth_31() {
        DayOfMonth thirtyFirst = DayOfMonth.of(31);

        assertEquals(true, thirtyFirst.isValidYearMonth(YearMonth.of(2012, 1)));
        assertEquals(false, thirtyFirst.isValidYearMonth(YearMonth.of(2012, 2)));
        assertEquals(true, thirtyFirst.isValidYearMonth(YearMonth.of(2012, 3)));
        assertEquals(false, thirtyFirst.isValidYearMonth(YearMonth.of(2012, 4)));
        assertEquals(true, thirtyFirst.isValidYearMonth(YearMonth.of(2012, 5)));
        assertEquals(false, thirtyFirst.isValidYearMonth(YearMonth.of(2012, 6)));
        assertEquals(true, thirtyFirst.isValidYearMonth(YearMonth.of(2012, 7)));
        assertEquals(true, thirtyFirst.isValidYearMonth(YearMonth.of(2012, 8)));
        assertEquals(false, thirtyFirst.isValidYearMonth(YearMonth.of(2012, 9)));
        assertEquals(true, thirtyFirst.isValidYearMonth(YearMonth.of(2012, 10)));
        assertEquals(false, thirtyFirst.isValidYearMonth(YearMonth.of(2012, 11)));
        assertEquals(true, thirtyFirst.isValidYearMonth(YearMonth.of(2012, 12)));
    }
}
