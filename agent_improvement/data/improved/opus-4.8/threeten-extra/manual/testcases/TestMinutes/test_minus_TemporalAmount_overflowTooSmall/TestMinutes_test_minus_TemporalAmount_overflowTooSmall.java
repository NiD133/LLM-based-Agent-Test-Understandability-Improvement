package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Minutes#minus(java.time.temporal.TemporalAmount)} reports
 * integer underflow by throwing an {@link ArithmeticException}.
 */
public class TestMinutes_test_minus_TemporalAmount_overflowTooSmall {

    /**
     * Subtracting a positive amount from a value that is already near
     * {@code Integer.MIN_VALUE} pushes the result below the {@code int} range.
     * <p>
     * Here {@code (Integer.MIN_VALUE + 1) - 2} underflows, so the subtraction
     * must fail with an {@link ArithmeticException} rather than wrap around.
     */
    @Test
    public void test_minus_TemporalAmount_overflowTooSmall() {
        Minutes nearMinimum = Minutes.of(Integer.MIN_VALUE + 1);
        Minutes amountToSubtract = Minutes.of(2);

        assertThrows(ArithmeticException.class, () -> nearMinimum.minus(amountToSubtract));
    }
}
