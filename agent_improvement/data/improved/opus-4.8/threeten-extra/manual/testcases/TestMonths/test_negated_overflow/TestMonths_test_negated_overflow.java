package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that negating the most negative possible amount of months overflows.
 */
public class TestMonths_test_negated_overflow {

    @Test
    public void negating_Integer_MIN_VALUE_months_throws_ArithmeticException() {
        // negated() computes -1 * months; for Integer.MIN_VALUE this exceeds the
        // positive int range, so an ArithmeticException is expected.
        Months mostNegative = Months.of(Integer.MIN_VALUE);

        assertThrows(ArithmeticException.class, () -> mostNegative.negated());
    }
}
