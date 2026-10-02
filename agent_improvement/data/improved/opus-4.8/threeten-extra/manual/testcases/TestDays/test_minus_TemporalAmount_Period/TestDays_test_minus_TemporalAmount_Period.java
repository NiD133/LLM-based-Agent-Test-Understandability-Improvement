package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Days#minus(java.time.temporal.TemporalAmount)} when the amount
 * subtracted is supplied as a {@link Period} of days.
 */
public class TestDays_test_minus_TemporalAmount_Period {

    @Test
    public void test_minus_TemporalAmount_Period() {
        Days fiveDays = Days.of(5);

        // Subtracting zero days leaves the value unchanged.
        assertEquals(Days.of(5), fiveDays.minus(Period.ofDays(0)));

        // Subtracting a positive period decreases the value: 5 - 2 = 3.
        assertEquals(Days.of(3), fiveDays.minus(Period.ofDays(2)));

        // Subtracting a negative period increases the value: 5 - (-2) = 7.
        assertEquals(Days.of(7), fiveDays.minus(Period.ofDays(-2)));

        // Subtraction may reach the int boundaries without overflowing:
        // (MAX_VALUE - 1) - (-1) = MAX_VALUE.
        assertEquals(
                Days.of(Integer.MAX_VALUE),
                Days.of(Integer.MAX_VALUE - 1).minus(Period.ofDays(-1)));

        // (MIN_VALUE + 1) - 1 = MIN_VALUE.
        assertEquals(
                Days.of(Integer.MIN_VALUE),
                Days.of(Integer.MIN_VALUE + 1).minus(Period.ofDays(1)));
    }
}
