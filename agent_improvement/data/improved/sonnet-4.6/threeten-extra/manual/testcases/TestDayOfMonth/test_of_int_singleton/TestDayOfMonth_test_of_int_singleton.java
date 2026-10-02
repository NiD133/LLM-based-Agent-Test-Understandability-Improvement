package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfMonth_test_of_int_singleton {

    // DayOfMonth supports values 1–31 (the maximum number of days in any month)
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
    // DayOfMonth.of() caches instances, so calling it twice with the same value
    // must return the exact same object (identity equality, not just value equality).
    @Test
    public void test_of_int_singleton() {
        for (int i = 1; i <= MAX_DAY_OF_MONTH; i++) {
            DayOfMonth test = DayOfMonth.of(i);
            assertEquals(i, test.getValue());
            assertSame(test, DayOfMonth.of(i));
        }
    }
}
