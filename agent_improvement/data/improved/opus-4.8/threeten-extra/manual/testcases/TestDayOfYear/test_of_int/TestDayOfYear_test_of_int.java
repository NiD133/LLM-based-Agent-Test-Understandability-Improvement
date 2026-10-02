package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests the factory and current-value behaviour of {@link DayOfYear}.
 */
public class TestDayOfYear_test_of_int {

    /** The longest possible year (a leap year has 366 days). */
    private static final int LEAP_YEAR_LENGTH = 366;

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        // DayOfYear.now() must agree with the day-of-year of today's local date.
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    //-----------------------------------------------------------------------
    // now(ZoneId)
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        // DayOfYear.now(zone) must agree with the day-of-year of today's date in that zone.
        assertEquals(LocalDate.now(tokyo).getDayOfYear(), DayOfYear.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // of(int)
    //-----------------------------------------------------------------------
    @Test
    public void test_of_int() {
        // Every valid day-of-year (1..366) should round-trip through getValue(),
        // and of(int) should return the same cached singleton for a given value.
        for (int dayOfYear = 1; dayOfYear <= LEAP_YEAR_LENGTH; dayOfYear++) {
            DayOfYear test = DayOfYear.of(dayOfYear);
            assertEquals(dayOfYear, test.getValue());
            assertSame(test, DayOfYear.of(dayOfYear));
        }
    }
}
