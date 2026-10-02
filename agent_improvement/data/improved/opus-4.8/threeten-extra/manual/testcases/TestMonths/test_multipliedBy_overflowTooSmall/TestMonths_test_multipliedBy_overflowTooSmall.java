package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Months#multipliedBy(int)} reports integer overflow
 * when the result would be smaller than {@link Integer#MIN_VALUE}.
 */
public class TestMonths_test_multipliedBy_overflowTooSmall {

    @Test
    public void test_multipliedBy_overflowTooSmall() {
        // Starting just below half of Integer.MIN_VALUE and doubling it
        // pushes the product past Integer.MIN_VALUE, so multiplication must overflow.
        Months tooSmallWhenDoubled = Months.of(Integer.MIN_VALUE / 2 - 1);

        assertThrows(ArithmeticException.class, () -> tooSmallWhenDoubled.multipliedBy(2));
    }
}
