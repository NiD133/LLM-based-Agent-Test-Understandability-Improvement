package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Months#minus(int)} fails fast when the subtraction would
 * underflow the {@code int} range.
 */
public class TestMonths_test_minus_int_overflowTooSmall {

    @Test
    public void minus_whenResultUnderflowsIntMin_throwsArithmeticException() {
        // Smallest representable value plus one; subtracting 2 drops below Integer.MIN_VALUE.
        Months nearMinimum = Months.of(Integer.MIN_VALUE + 1);

        assertThrows(ArithmeticException.class, () -> nearMinimum.minus(2));
    }
}
