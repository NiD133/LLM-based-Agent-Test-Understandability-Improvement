package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Days#plus(java.time.temporal.TemporalAmount)} when the amount
 * added is a {@link Period} expressed purely in days.
 */
public class TestDays_test_plus_TemporalAmount_Period {

    @Test
    public void test_plus_TemporalAmount_Period() {
        Days fiveDays = Days.of(5);

        // Adding a zero-day period leaves the value unchanged.
        assertEquals(Days.of(5), fiveDays.plus(Period.ofDays(0)));

        // Adding a positive period increases the number of days.
        assertEquals(Days.of(7), fiveDays.plus(Period.ofDays(2)));

        // Adding a negative period decreases the number of days.
        assertEquals(Days.of(3), fiveDays.plus(Period.ofDays(-2)));

        // Adding may grow the value right up to the int boundaries.
        assertEquals(Days.of(Integer.MAX_VALUE),
                Days.of(Integer.MAX_VALUE - 1).plus(Period.ofDays(1)));
        assertEquals(Days.of(Integer.MIN_VALUE),
                Days.of(Integer.MIN_VALUE + 1).plus(Period.ofDays(-1)));
    }
}
