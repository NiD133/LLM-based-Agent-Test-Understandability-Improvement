package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests the {@link Seconds#of(int)} factory method.
 * <p>
 * Each assertion creates a {@code Seconds} instance from a raw second count and
 * verifies that {@link Seconds#getAmount()} returns exactly that same count,
 * including the boundary values {@link Integer#MAX_VALUE} and {@link Integer#MIN_VALUE}.
 */
public class TestSeconds_test_of {

    @Test
    public void of_storesTheGivenSecondAmount() {
        // zero and small positive values
        assertEquals(0, Seconds.of(0).getAmount());
        assertEquals(1, Seconds.of(1).getAmount());
        assertEquals(2, Seconds.of(2).getAmount());

        // small negative values
        assertEquals(-1, Seconds.of(-1).getAmount());
        assertEquals(-2, Seconds.of(-2).getAmount());

        // int boundary values
        assertEquals(Integer.MAX_VALUE, Seconds.of(Integer.MAX_VALUE).getAmount());
        assertEquals(Integer.MIN_VALUE, Seconds.of(Integer.MIN_VALUE).getAmount());
    }
}
