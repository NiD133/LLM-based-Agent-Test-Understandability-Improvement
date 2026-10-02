package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Days#minus(int)} fails with an {@link ArithmeticException}
 * when the subtraction would underflow below {@link Integer#MIN_VALUE}.
 */
public class TestDays_test_minus_int_overflowTooSmall {

    @Test
    public void minus_underflowsBelowIntMinValue_throwsArithmeticException() {
        // (Integer.MIN_VALUE + 1) - 2 cannot be represented as an int.
        Days nearMinimum = Days.of(Integer.MIN_VALUE + 1);

        assertThrows(ArithmeticException.class, () -> nearMinimum.minus(2));
    }
}
