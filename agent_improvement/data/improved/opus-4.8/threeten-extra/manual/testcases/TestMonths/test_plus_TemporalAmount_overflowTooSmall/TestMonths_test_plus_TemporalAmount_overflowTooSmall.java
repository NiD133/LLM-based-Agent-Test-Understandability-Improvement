package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Months#plus(java.time.temporal.TemporalAmount)} reports
 * integer underflow rather than silently wrapping around.
 */
public class TestMonths_test_plus_TemporalAmount_overflowTooSmall {

    /**
     * Adding a negative amount to a value just above {@code Integer.MIN_VALUE}
     * pushes the result below the {@code int} range, so an
     * {@link ArithmeticException} must be thrown.
     */
    @Test
    public void test_plus_TemporalAmount_overflowTooSmall() {
        Months almostMinValue = Months.of(Integer.MIN_VALUE + 1);
        Months negativeTwo = Months.of(-2);

        assertThrows(ArithmeticException.class, () -> almostMinValue.plus(negativeTwo));
    }
}
