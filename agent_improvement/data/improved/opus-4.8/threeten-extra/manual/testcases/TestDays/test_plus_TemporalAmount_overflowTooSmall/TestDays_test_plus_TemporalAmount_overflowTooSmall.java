package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Days#plus(java.time.temporal.TemporalAmount)} reports an
 * arithmetic overflow when the result would fall below {@link Integer#MIN_VALUE}.
 */
public class TestDays_test_plus_TemporalAmount_overflowTooSmall {

    @Test
    public void plus_belowMinValue_throwsArithmeticException() {
        // Starting one above the smallest int and subtracting 2 more days
        // pushes the total below Integer.MIN_VALUE, which must overflow.
        Days nearMinimum = Days.of(Integer.MIN_VALUE + 1);
        Days twoDaysBack = Days.of(-2);

        assertThrows(ArithmeticException.class, () -> nearMinimum.plus(twoDaysBack));
    }
}
