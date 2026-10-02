package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that subtracting from a near-minimum Weeks value overflows.
 */
public class TestWeeks_test_minus_int_overflowTooSmall {

    @Test
    public void test_minus_int_overflowTooSmall() {
        Weeks nearMinimum = Weeks.of(Integer.MIN_VALUE + 1);

        // Subtracting 2 would underflow below Integer.MIN_VALUE.
        assertThrows(ArithmeticException.class, () -> nearMinimum.minus(2));
    }
}
