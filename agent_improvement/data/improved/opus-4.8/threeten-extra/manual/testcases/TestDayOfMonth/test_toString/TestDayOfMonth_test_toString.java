package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests {@link DayOfMonth#toString()} along with the {@code now} factory methods.
 */
public class TestDayOfMonth_test_toString {

    /** The highest day-of-month value supported by {@link DayOfMonth}. */
    private static final int LAST_DAY_OF_MONTH = 31;

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        // DayOfMonth.now() must match the day-of-month of the current local date.
        assertEquals(LocalDate.now().getDayOfMonth(), DayOfMonth.now().getValue());
    }

    //-----------------------------------------------------------------------
    // now(ZoneId)
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        // DayOfMonth.now(zone) must match the day-of-month of "today" in that zone.
        assertEquals(LocalDate.now(tokyo).getDayOfMonth(), DayOfMonth.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // toString()
    //-----------------------------------------------------------------------
    @Test
    public void test_toString() {
        // Every valid day (1..31) renders as "DayOfMonth:<value>".
        for (int day = 1; day <= LAST_DAY_OF_MONTH; day++) {
            DayOfMonth dayOfMonth = DayOfMonth.of(day);
            assertEquals("DayOfMonth:" + day, dayOfMonth.toString());
        }
    }
}
