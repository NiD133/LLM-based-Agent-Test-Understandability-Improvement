package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Days#ofWeeks(int)}, which builds a {@code Days} amount from a
 * number of weeks by multiplying it by 7 (the number of days in a week).
 */
public class TestDays_test_ofWeeks {

    private static final int DAYS_PER_WEEK = 7;

    @Test
    public void ofWeeks_convertsWeeksToDaysByMultiplyingBySeven() {
        // Zero weeks is zero days.
        assertEquals(0, Days.ofWeeks(0).getAmount());

        // Positive week counts scale up by 7.
        assertEquals(7, Days.ofWeeks(1).getAmount());
        assertEquals(14, Days.ofWeeks(2).getAmount());

        // Negative week counts scale the same way, keeping the sign.
        assertEquals(-7, Days.ofWeeks(-1).getAmount());
        assertEquals(-14, Days.ofWeeks(-2).getAmount());

        // The largest week counts that still fit in an int once multiplied by 7.
        int maxWeeks = Integer.MAX_VALUE / DAYS_PER_WEEK;
        int minWeeks = Integer.MIN_VALUE / DAYS_PER_WEEK;
        assertEquals(maxWeeks * DAYS_PER_WEEK, Days.ofWeeks(maxWeeks).getAmount());
        assertEquals(minWeeks * DAYS_PER_WEEK, Days.ofWeeks(minWeeks).getAmount());
    }
}
