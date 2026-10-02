package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Months#abs()} reports overflow rather than returning a wrong value.
 */
public class TestMonths_test_abs_overflow {

    /**
     * The absolute value of {@code Integer.MIN_VALUE} cannot be represented as an int,
     * so {@code abs()} must fail with an {@link ArithmeticException} instead of overflowing.
     */
    @Test
    public void abs_ofIntegerMinValue_throwsArithmeticException() {
        Months mostNegative = Months.of(Integer.MIN_VALUE);

        assertThrows(ArithmeticException.class, () -> mostNegative.abs());
    }
}
