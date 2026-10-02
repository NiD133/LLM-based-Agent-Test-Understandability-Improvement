package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_minus_TemporalAmount_overflowTooSmall {

    /**
     * Subtracting a positive amount from a value close to {@link Integer#MIN_VALUE}
     * pushes the result below the {@code int} range, so {@code minus} must fail
     * with an {@link ArithmeticException} rather than silently overflowing.
     */
    @Test
    public void test_minus_TemporalAmount_overflowTooSmall() {
        Seconds almostMinValue = Seconds.of(Integer.MIN_VALUE + 1);
        Seconds twoSeconds = Seconds.of(2);

        assertThrows(ArithmeticException.class, () -> almostMinValue.minus(twoSeconds));
    }
}
