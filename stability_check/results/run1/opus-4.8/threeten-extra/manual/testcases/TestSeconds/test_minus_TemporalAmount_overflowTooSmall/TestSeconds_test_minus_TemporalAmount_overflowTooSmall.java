package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Seconds#minus(java.time.temporal.TemporalAmount)} rejects a
 * subtraction whose result would underflow the {@code int} range.
 */
public class TestSeconds_test_minus_TemporalAmount_overflowTooSmall {

    @Test
    public void test_minus_TemporalAmount_overflowTooSmall() {
        // (Integer.MIN_VALUE + 1) - 2 underflows int, so an ArithmeticException is expected.
        Seconds nearMinimum = Seconds.of(Integer.MIN_VALUE + 1);
        Seconds twoSeconds = Seconds.of(2);

        assertThrows(ArithmeticException.class, () -> nearMinimum.minus(twoSeconds));
    }
}
