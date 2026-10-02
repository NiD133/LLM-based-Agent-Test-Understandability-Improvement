package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Minutes#minus(int)}, which returns a new {@code Minutes}
 * equal to this amount with the given number of minutes subtracted.
 */
public class TestMinutes_test_minus_int {

    @Test
    public void minus_int_subtractsTheGivenNumberOfMinutes() {
        Minutes fiveMinutes = Minutes.of(5);

        // Subtracting zero leaves the amount unchanged.
        assertEquals(Minutes.of(5), fiveMinutes.minus(0));
        // Subtracting a positive value decreases the amount: 5 - 2 = 3.
        assertEquals(Minutes.of(3), fiveMinutes.minus(2));
        // Subtracting a negative value increases the amount: 5 - (-2) = 7.
        assertEquals(Minutes.of(7), fiveMinutes.minus(-2));

        // Subtraction may reach the int boundaries without overflowing.
        assertEquals(
                Minutes.of(Integer.MAX_VALUE),
                Minutes.of(Integer.MAX_VALUE - 1).minus(-1));
        assertEquals(
                Minutes.of(Integer.MIN_VALUE),
                Minutes.of(Integer.MIN_VALUE + 1).minus(1));
    }
}
