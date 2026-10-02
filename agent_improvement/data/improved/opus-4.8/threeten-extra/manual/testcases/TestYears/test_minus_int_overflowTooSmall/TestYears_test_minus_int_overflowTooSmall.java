package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that subtracting an int from a {@link Years} value throws an
 * {@link ArithmeticException} when the result underflows {@code Integer.MIN_VALUE}.
 */
public class TestYears_test_minus_int_overflowTooSmall {

    @Test
    public void test_minus_int_overflowTooSmall() {
        // (Integer.MIN_VALUE + 1) - 2 underflows the int range, so minus(int) must throw.
        Years nearMinimum = Years.of(Integer.MIN_VALUE + 1);

        assertThrows(ArithmeticException.class, () -> nearMinimum.minus(2));
    }
}
