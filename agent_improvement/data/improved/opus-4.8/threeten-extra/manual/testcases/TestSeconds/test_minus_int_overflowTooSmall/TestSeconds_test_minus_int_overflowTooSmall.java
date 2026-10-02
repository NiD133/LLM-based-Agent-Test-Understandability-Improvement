package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_minus_int_overflowTooSmall {

    /**
     * Subtracting a positive amount from a value close to {@link Integer#MIN_VALUE}
     * must fail with an {@link ArithmeticException} rather than silently wrapping around.
     *
     * <p>Here the starting value is {@code Integer.MIN_VALUE + 1}; subtracting {@code 2}
     * would require {@code Integer.MIN_VALUE - 1}, which is below the {@code int} range.
     */
    @Test
    public void test_minus_int_overflowTooSmall() {
        Seconds nearMinValue = Seconds.of(Integer.MIN_VALUE + 1);

        assertThrows(ArithmeticException.class, () -> nearMinValue.minus(2));
    }
}
