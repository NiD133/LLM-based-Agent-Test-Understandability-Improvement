package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests the factory methods of {@link DayOfMonth} that obtain instances by value.
 */
public class TestDayOfMonth_test_of_int_singleton {

    /** The largest day-of-month value supported by {@link DayOfMonth} (1 to 31). */
    private static final int LAST_DAY_OF_MONTH = 31;

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void now_returnsTodaysDayOfMonth_inDefaultZone() {
        int expectedDay = LocalDate.now().getDayOfMonth();
        assertEquals(expectedDay, DayOfMonth.now().getValue());
    }

    //-----------------------------------------------------------------------
    // now(ZoneId)
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void now_returnsTodaysDayOfMonth_inGivenZone() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        int expectedDay = LocalDate.now(tokyo).getDayOfMonth();
        assertEquals(expectedDay, DayOfMonth.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // of(int)
    //-----------------------------------------------------------------------
    @Test
    public void of_returnsCachedSingletonHoldingGivenValue() {
        for (int day = 1; day <= LAST_DAY_OF_MONTH; day++) {
            DayOfMonth dayOfMonth = DayOfMonth.of(day);

            // The instance reports the value it was created with...
            assertEquals(day, dayOfMonth.getValue());
            // ...and of(int) returns the same cached singleton for that value.
            assertSame(dayOfMonth, DayOfMonth.of(day));
        }
    }
}
