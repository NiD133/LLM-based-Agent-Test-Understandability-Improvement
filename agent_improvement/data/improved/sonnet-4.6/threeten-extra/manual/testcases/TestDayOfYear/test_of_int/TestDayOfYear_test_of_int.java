package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfYear_test_of_int {

    // Maximum valid day-of-year value, which occurs in leap years
    private static final int LEAP_YEAR_LENGTH = 366;

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
    @Test
    public void test_of_int() {
        // DayOfYear caches all 366 instances; verify both the correct value and that
        // repeated calls return the identical cached instance (singleton per day number)
        for (int dayOfYear = 1; dayOfYear <= LEAP_YEAR_LENGTH; dayOfYear++) {
            DayOfYear day = DayOfYear.of(dayOfYear);
            assertEquals(dayOfYear, day.getValue());
            assertSame(day, DayOfYear.of(dayOfYear));
        }
    }
}
