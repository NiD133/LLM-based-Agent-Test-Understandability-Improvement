package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that negating the smallest possible {@code Days} value overflows.
 */
public class TestDays_test_negated_overflow {

    @Test
    public void negating_minimum_value_throws_arithmetic_exception() {
        // Negating Integer.MIN_VALUE has no positive int counterpart, so it must overflow.
        Days minimumDays = Days.of(Integer.MIN_VALUE);

        assertThrows(ArithmeticException.class, () -> minimumDays.negated());
    }
}
