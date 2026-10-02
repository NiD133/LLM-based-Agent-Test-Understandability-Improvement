package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Weeks#plus(int)} reports integer underflow.
 */
public class TestWeeks_test_plus_int_overflowTooSmall {

    @Test
    public void plus_whenResultUnderflowsIntMin_throwsArithmeticException() {
        // (Integer.MIN_VALUE + 1) + (-2) would be Integer.MIN_VALUE - 1, which
        // cannot fit in an int, so the addition must fail with an overflow error.
        Weeks nearMinimum = Weeks.of(Integer.MIN_VALUE + 1);

        assertThrows(ArithmeticException.class, () -> nearMinimum.plus(-2));
    }
}
