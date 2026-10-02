package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that subtracting a {@link Hours} amount throws an
 * {@link ArithmeticException} when the result underflows {@code int}.
 */
public class TestHours_test_minus_TemporalAmount_overflowTooSmall {

    @Test
    public void minus_underflowsBelowIntMinValue_throwsArithmeticException() {
        // (Integer.MIN_VALUE + 1) - 2 falls below Integer.MIN_VALUE, so it overflows.
        Hours nearMinimum = Hours.of(Integer.MIN_VALUE + 1);
        Hours twoHours = Hours.of(2);

        assertThrows(ArithmeticException.class, () -> nearMinimum.minus(twoHours));
    }
}
