package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Hours#plus(int)} rejects additions that would underflow
 * below {@link Integer#MIN_VALUE}.
 */
public class TestHours_test_plus_int_overflowTooSmall {

    @Test
    public void plus_whenResultUnderflowsIntMin_throwsArithmeticException() {
        Hours nearMinimum = Hours.of(Integer.MIN_VALUE + 1);

        // Adding -2 would push the total below Integer.MIN_VALUE, which must overflow.
        assertThrows(ArithmeticException.class, () -> nearMinimum.plus(-2));
    }
}
