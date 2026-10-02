package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestYears_test_minus_int_overflowTooSmall {

    /**
     * Verifies that subtracting an integer from a Years value overflows when the result
     * would go below Integer.MIN_VALUE. Starting at (MIN_VALUE + 1) and subtracting 2
     * must throw ArithmeticException because Math.subtractExact detects the overflow.
     */
    @Test
    public void test_minus_int_overflowTooSmall() {
        Years nearMinimum = Years.of(Integer.MIN_VALUE + 1);
        assertThrows(ArithmeticException.class, () -> nearMinimum.minus(2));
    }
}
