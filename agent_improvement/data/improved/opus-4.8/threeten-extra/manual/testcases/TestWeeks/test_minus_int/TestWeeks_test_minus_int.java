package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Weeks#minus(int)}, which subtracts a number of weeks and
 * returns a new {@code Weeks} instance (the original is left unchanged).
 */
public class TestWeeks_test_minus_int {

    @Test
    public void test_minus_int() {
        Weeks fiveWeeks = Weeks.of(5);

        // Subtracting zero leaves the amount unchanged.
        assertEquals(Weeks.of(5), fiveWeeks.minus(0));

        // Subtracting a positive number reduces the amount.
        assertEquals(Weeks.of(3), fiveWeeks.minus(2));

        // Subtracting a negative number increases the amount.
        assertEquals(Weeks.of(7), fiveWeeks.minus(-2));

        // Subtraction is allowed right up to the int boundaries without overflowing.
        assertEquals(Weeks.of(Integer.MAX_VALUE), Weeks.of(Integer.MAX_VALUE - 1).minus(-1));
        assertEquals(Weeks.of(Integer.MIN_VALUE), Weeks.of(Integer.MIN_VALUE + 1).minus(1));
    }
}
