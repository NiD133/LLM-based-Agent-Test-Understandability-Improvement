package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Weeks#minus(java.time.temporal.TemporalAmount)} fails
 * with an {@link ArithmeticException} when the subtraction underflows the
 * {@code int} range.
 */
public class TestWeeks_test_minus_TemporalAmount_overflowTooSmall {

    @Test
    public void minus_throwsWhenResultUnderflowsIntRange() {
        // Starting just above the smallest possible value and subtracting a
        // positive amount pushes the result below Integer.MIN_VALUE.
        Weeks nearMinimum = Weeks.of(Integer.MIN_VALUE + 1);
        Weeks twoWeeks = Weeks.of(2);

        assertThrows(ArithmeticException.class, () -> nearMinimum.minus(twoWeeks));
    }
}
