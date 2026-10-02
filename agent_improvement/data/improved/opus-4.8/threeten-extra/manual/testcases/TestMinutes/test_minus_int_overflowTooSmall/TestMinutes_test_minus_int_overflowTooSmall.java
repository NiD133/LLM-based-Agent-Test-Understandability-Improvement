package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Minutes#minus(int)} fails fast when the subtraction
 * would underflow below {@link Integer#MIN_VALUE}.
 */
public class TestMinutes_test_minus_int_overflowTooSmall {

    @Test
    public void minus_whenResultUnderflowsIntRange_throwsArithmeticException() {
        Minutes nearMinimum = Minutes.of(Integer.MIN_VALUE + 1);

        // Subtracting 2 pushes the result one step past Integer.MIN_VALUE.
        assertThrows(ArithmeticException.class, () -> nearMinimum.minus(2));
    }
}
