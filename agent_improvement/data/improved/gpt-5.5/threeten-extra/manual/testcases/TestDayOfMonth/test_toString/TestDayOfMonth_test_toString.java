package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfMonth_test_toString {

    private static final int MIN_VALID_DAY_OF_MONTH = 1;
    private static final int MAX_VALID_DAY_OF_MONTH = 31;

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
    public void test_toString() {
        for (int dayOfMonth = MIN_VALID_DAY_OF_MONTH; dayOfMonth <= MAX_VALID_DAY_OF_MONTH; dayOfMonth++) {
            DayOfMonth testDay = DayOfMonth.of(dayOfMonth);
            assertEquals("DayOfMonth:" + dayOfMonth, testDay.toString());
        }
    }
}
