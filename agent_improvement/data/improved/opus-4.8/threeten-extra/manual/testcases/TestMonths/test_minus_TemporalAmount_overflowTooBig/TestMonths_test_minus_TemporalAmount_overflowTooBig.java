package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Months#minus(java.time.temporal.TemporalAmount)} reports
 * numeric overflow when the subtraction pushes the month count past
 * {@link Integer#MAX_VALUE}.
 */
public class TestMonths_test_minus_TemporalAmount_overflowTooBig {

    /**
     * Subtracting {@code -2} months from {@code Integer.MAX_VALUE - 1} months is
     * equivalent to adding 2, which exceeds {@code Integer.MAX_VALUE}. The
     * operation must therefore fail with an {@link ArithmeticException} rather
     * than silently wrapping around.
     */
    @Test
    public void test_minus_TemporalAmount_overflowTooBig() {
        Months almostMaxMonths = Months.of(Integer.MAX_VALUE - 1);
        Months negativeTwoMonths = Months.of(-2);

        assertThrows(ArithmeticException.class, () -> almostMaxMonths.minus(negativeTwoMonths));
    }
}
