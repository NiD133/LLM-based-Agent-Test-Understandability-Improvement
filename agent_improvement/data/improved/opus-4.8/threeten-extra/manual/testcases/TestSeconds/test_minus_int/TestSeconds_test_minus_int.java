package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Seconds#minus(int)}, which returns a new {@code Seconds}
 * whose amount is this amount minus the given number of seconds.
 */
public class TestSeconds_test_minus_int {

    @Test
    public void test_minus_int() {
        Seconds fiveSeconds = Seconds.of(5);

        // Subtracting zero leaves the amount unchanged.
        assertEquals(Seconds.of(5), fiveSeconds.minus(0));

        // Subtracting a positive amount reduces the value: 5 - 2 = 3.
        assertEquals(Seconds.of(3), fiveSeconds.minus(2));

        // Subtracting a negative amount increases the value: 5 - (-2) = 7.
        assertEquals(Seconds.of(7), fiveSeconds.minus(-2));

        // Subtracting -1 reaches the maximum int without overflowing.
        assertEquals(
                Seconds.of(Integer.MAX_VALUE),
                Seconds.of(Integer.MAX_VALUE - 1).minus(-1));

        // Subtracting 1 reaches the minimum int without overflowing.
        assertEquals(
                Seconds.of(Integer.MIN_VALUE),
                Seconds.of(Integer.MIN_VALUE + 1).minus(1));
    }
}
