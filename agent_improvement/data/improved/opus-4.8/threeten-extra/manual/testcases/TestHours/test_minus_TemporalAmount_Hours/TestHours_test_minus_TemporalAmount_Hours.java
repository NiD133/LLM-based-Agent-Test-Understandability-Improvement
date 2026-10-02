package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Hours#minus(java.time.temporal.TemporalAmount)} when the amount
 * to subtract is itself an {@code Hours} value.
 * <p>
 * Subtraction should be a straightforward integer difference of the hour
 * amounts, including the boundary cases that just touch {@link Integer#MAX_VALUE}
 * and {@link Integer#MIN_VALUE} without overflowing.
 */
public class TestHours_test_minus_TemporalAmount_Hours {

    @Test
    public void test_minus_TemporalAmount_Hours() {
        Hours fiveHours = Hours.of(5);

        // Subtracting zero leaves the value unchanged.
        assertEquals(Hours.of(5), fiveHours.minus(Hours.of(0)));

        // Subtracting a positive amount decreases the value: 5 - 2 = 3.
        assertEquals(Hours.of(3), fiveHours.minus(Hours.of(2)));

        // Subtracting a negative amount increases the value: 5 - (-2) = 7.
        assertEquals(Hours.of(7), fiveHours.minus(Hours.of(-2)));

        // Boundary cases that land exactly on the int limits without overflowing.
        assertEquals(Hours.of(Integer.MAX_VALUE), Hours.of(Integer.MAX_VALUE - 1).minus(Hours.of(-1)));
        assertEquals(Hours.of(Integer.MIN_VALUE), Hours.of(Integer.MIN_VALUE + 1).minus(Hours.of(1)));
    }
}
