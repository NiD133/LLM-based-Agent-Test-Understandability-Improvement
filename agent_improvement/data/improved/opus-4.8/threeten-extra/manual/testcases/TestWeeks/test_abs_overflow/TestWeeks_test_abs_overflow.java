package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies the overflow behaviour of {@link Weeks#abs()}.
 */
public class TestWeeks_test_abs_overflow {

    /**
     * Taking the absolute value of {@code Integer.MIN_VALUE} weeks cannot be
     * represented as a positive {@code int}, so {@code abs()} must overflow and
     * throw an {@link ArithmeticException}.
     */
    @Test
    public void abs_ofIntegerMinValue_throwsArithmeticException() {
        Weeks mostNegative = Weeks.of(Integer.MIN_VALUE);

        assertThrows(ArithmeticException.class, () -> mostNegative.abs());
    }
}
