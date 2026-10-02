package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Years#minus(java.time.temporal.TemporalAmount)} fails with an
 * {@link ArithmeticException} when the subtraction underflows the {@code int} range.
 */
public class TestYears_test_minus_TemporalAmount_overflowTooSmall {

    @Test
    public void minus_temporalAmount_throwsWhenResultUnderflowsIntRange() {
        // Subtracting 2 years from (Integer.MIN_VALUE + 1) years would yield a value
        // smaller than Integer.MIN_VALUE, so the operation must overflow.
        Years nearMinimumYears = Years.of(Integer.MIN_VALUE + 1);
        Years twoYears = Years.of(2);

        assertThrows(ArithmeticException.class, () -> nearMinimumYears.minus(twoYears));
    }
}
