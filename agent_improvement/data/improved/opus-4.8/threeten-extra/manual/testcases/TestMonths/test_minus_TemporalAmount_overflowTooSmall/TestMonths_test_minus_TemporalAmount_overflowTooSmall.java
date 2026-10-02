package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Months#minus(java.time.temporal.TemporalAmount)} fails
 * with an {@link ArithmeticException} when the subtraction underflows {@code int}.
 */
public class TestMonths_test_minus_TemporalAmount_overflowTooSmall {

    @Test
    public void minus_underflowsIntRange_throwsArithmeticException() {
        // The smallest representable value is Integer.MIN_VALUE; starting one above it
        // and subtracting 2 pushes the result below Integer.MIN_VALUE, causing overflow.
        Months nearMinimum = Months.of(Integer.MIN_VALUE + 1);
        Months amountToSubtract = Months.of(2);

        assertThrows(ArithmeticException.class, () -> nearMinimum.minus(amountToSubtract));
    }
}
