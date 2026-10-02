package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfYear_test_toString {

    // DayOfYear supports days 1-366 (leap years include day 366)
    private static final int MAX_DAY_OF_YEAR = 366;

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfYear(), DayOfYear.now(zone).getValue());
    }

    //-----------------------------------------------------------------------
    // toString() should produce "DayOfYear:<n>" for every valid day-of-year (1-366)
    @Test
    public void test_toString() {
        for (int i = 1; i <= MAX_DAY_OF_YEAR; i++) {
            DayOfYear a = DayOfYear.of(i);
            assertEquals("DayOfYear:" + i, a.toString());
        }
    }
}
