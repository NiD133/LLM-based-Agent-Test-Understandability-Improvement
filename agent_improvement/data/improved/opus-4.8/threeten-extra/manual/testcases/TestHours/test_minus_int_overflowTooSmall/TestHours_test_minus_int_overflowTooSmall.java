package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Hours#minus(int)} fails fast when the subtraction would
 * underflow the {@code int} range, rather than silently wrapping around.
 */
public class TestHours_test_minus_int_overflowTooSmall {

    @Test
    public void minus_whenResultIsBelowIntMinValue_throwsArithmeticException() {
        // (Integer.MIN_VALUE + 1) - 2 cannot be represented as an int.
        Hours almostMinimum = Hours.of(Integer.MIN_VALUE + 1);

        assertThrows(ArithmeticException.class, () -> almostMinimum.minus(2));
    }
}
