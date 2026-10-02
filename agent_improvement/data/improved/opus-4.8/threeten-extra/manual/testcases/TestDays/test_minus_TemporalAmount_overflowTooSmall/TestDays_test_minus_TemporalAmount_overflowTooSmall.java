package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Days#minus(java.time.temporal.TemporalAmount)} reports an
 * arithmetic overflow when the subtraction would drop below {@link Integer#MIN_VALUE}.
 */
public class TestDays_test_minus_TemporalAmount_overflowTooSmall {

    @Test
    public void minus_temporalAmount_belowMinValue_throwsArithmeticException() {
        // Starting one above the smallest possible value, subtracting 2 days
        // underflows the int range and must fail.
        Days almostMinimum = Days.of(Integer.MIN_VALUE + 1);
        Days twoDays = Days.of(2);

        assertThrows(ArithmeticException.class, () -> almostMinimum.minus(twoDays));
    }
}
