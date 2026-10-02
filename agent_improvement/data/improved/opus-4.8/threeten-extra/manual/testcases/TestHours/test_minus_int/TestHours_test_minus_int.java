package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Hours#minus(int)}.
 */
public class TestHours_test_minus_int {

    @Test
    public void test_minus_int() {
        Hours fiveHours = Hours.of(5);

        // Subtracting zero leaves the amount unchanged.
        assertEquals(Hours.of(5), fiveHours.minus(0));

        // Subtracting a positive amount reduces the hours.
        assertEquals(Hours.of(3), fiveHours.minus(2));

        // Subtracting a negative amount increases the hours.
        assertEquals(Hours.of(7), fiveHours.minus(-2));

        // Subtracting a negative value may reach Integer.MAX_VALUE without overflow.
        assertEquals(Hours.of(Integer.MAX_VALUE), Hours.of(Integer.MAX_VALUE - 1).minus(-1));

        // Subtracting a positive value may reach Integer.MIN_VALUE without overflow.
        assertEquals(Hours.of(Integer.MIN_VALUE), Hours.of(Integer.MIN_VALUE + 1).minus(1));
    }
}
