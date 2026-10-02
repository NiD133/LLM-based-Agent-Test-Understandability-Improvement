package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Minutes#abs()} reports overflow.
 */
public class TestMinutes_test_abs_overflow {

    /**
     * Taking the absolute value of {@code Integer.MIN_VALUE} minutes cannot be
     * represented as a positive {@code int}, so {@code abs()} must overflow and
     * throw an {@link ArithmeticException}.
     */
    @Test
    public void abs_ofMinValue_throwsOnOverflow() {
        Minutes mostNegativeMinutes = Minutes.of(Integer.MIN_VALUE);

        assertThrows(ArithmeticException.class, () -> mostNegativeMinutes.abs());
    }
}
