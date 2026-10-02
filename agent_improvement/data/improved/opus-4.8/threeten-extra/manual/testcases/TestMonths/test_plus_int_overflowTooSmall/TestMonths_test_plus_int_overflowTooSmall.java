package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Months#plus(int)} fails with an {@link ArithmeticException}
 * when the addition underflows the {@code int} range.
 */
public class TestMonths_test_plus_int_overflowTooSmall {

    @Test
    public void test_plus_int_overflowTooSmall() {
        // Start just above Integer.MIN_VALUE, then add -2 to push the total below
        // Integer.MIN_VALUE, which must overflow rather than wrap around.
        Months nearMinimum = Months.of(Integer.MIN_VALUE + 1);

        assertThrows(ArithmeticException.class, () -> nearMinimum.plus(-2));
    }
}
