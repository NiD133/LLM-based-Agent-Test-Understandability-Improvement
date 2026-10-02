package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Days#minus(java.time.temporal.TemporalAmount)}.
 * <p>
 * Each assertion subtracts another {@code Days} amount (a {@code TemporalAmount})
 * from a starting {@code Days} value and checks the resulting amount.
 */
public class TestDays_test_minus_TemporalAmount_Days {

    @Test
    public void test_minus_TemporalAmount_Days() {
        Days fiveDays = Days.of(5);

        // Subtracting zero leaves the amount unchanged: 5 - 0 = 5.
        assertEquals(Days.of(5), fiveDays.minus(Days.of(0)));

        // Subtracting a positive amount decreases the value: 5 - 2 = 3.
        assertEquals(Days.of(3), fiveDays.minus(Days.of(2)));

        // Subtracting a negative amount increases the value: 5 - (-2) = 7.
        assertEquals(Days.of(7), fiveDays.minus(Days.of(-2)));

        // Subtraction may reach the upper int bound: (MAX_VALUE - 1) - (-1) = MAX_VALUE.
        assertEquals(Days.of(Integer.MAX_VALUE), Days.of(Integer.MAX_VALUE - 1).minus(Days.of(-1)));

        // Subtraction may reach the lower int bound: (MIN_VALUE + 1) - 1 = MIN_VALUE.
        assertEquals(Days.of(Integer.MIN_VALUE), Days.of(Integer.MIN_VALUE + 1).minus(Days.of(1)));
    }
}
