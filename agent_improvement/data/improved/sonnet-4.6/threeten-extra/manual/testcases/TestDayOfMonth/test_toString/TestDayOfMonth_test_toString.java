package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfMonth_test_toString {

    // DayOfMonth supports days 1 through 31
    private static final int MAX_DAY_OF_MONTH = 31;

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
    // toString() should produce "DayOfMonth:<day>" for every valid day 1-31
    @Test
    public void test_toString() {
        for (int day = 1; day <= MAX_DAY_OF_MONTH; day++) {
            DayOfMonth dayOfMonth = DayOfMonth.of(day);
            assertEquals("DayOfMonth:" + day, dayOfMonth.toString());
        }
    }
}
