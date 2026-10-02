package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfMonth_test_isValidYearMonth_29 {

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
    public void test_isValidYearMonth_29() {
        DayOfMonth dayOfMonth = DayOfMonth.of(29);

        assertTrue(dayOfMonth.isValidYearMonth(YearMonth.of(2012, 1)));
        assertTrue(dayOfMonth.isValidYearMonth(YearMonth.of(2012, 2)));
        assertTrue(dayOfMonth.isValidYearMonth(YearMonth.of(2012, 3)));
        assertTrue(dayOfMonth.isValidYearMonth(YearMonth.of(2012, 4)));
        assertTrue(dayOfMonth.isValidYearMonth(YearMonth.of(2012, 5)));
        assertTrue(dayOfMonth.isValidYearMonth(YearMonth.of(2012, 6)));
        assertTrue(dayOfMonth.isValidYearMonth(YearMonth.of(2012, 7)));
        assertTrue(dayOfMonth.isValidYearMonth(YearMonth.of(2012, 8)));
        assertTrue(dayOfMonth.isValidYearMonth(YearMonth.of(2012, 9)));
        assertTrue(dayOfMonth.isValidYearMonth(YearMonth.of(2012, 10)));
        assertTrue(dayOfMonth.isValidYearMonth(YearMonth.of(2012, 11)));
        assertTrue(dayOfMonth.isValidYearMonth(YearMonth.of(2012, 12)));

        assertFalse(dayOfMonth.isValidYearMonth(YearMonth.of(2011, 2)));
    }
}
