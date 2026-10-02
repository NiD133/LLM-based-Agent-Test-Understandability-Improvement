package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Days#minus(int)}, which returns a new {@code Days} whose amount
 * is this amount minus the given number of days.
 */
public class TestDays_test_minus_int {

    @Test
    public void test_minus_int() {
        Days fiveDays = Days.of(5);

        // Subtracting zero leaves the amount unchanged.
        assertEquals(Days.of(5), fiveDays.minus(0));
        // Subtracting a positive amount decreases the result.
        assertEquals(Days.of(3), fiveDays.minus(2));
        // Subtracting a negative amount increases the result.
        assertEquals(Days.of(7), fiveDays.minus(-2));

        // The result may reach the int boundaries without overflowing.
        assertEquals(Days.of(Integer.MAX_VALUE), Days.of(Integer.MAX_VALUE - 1).minus(-1));
        assertEquals(Days.of(Integer.MIN_VALUE), Days.of(Integer.MIN_VALUE + 1).minus(1));
    }
}
