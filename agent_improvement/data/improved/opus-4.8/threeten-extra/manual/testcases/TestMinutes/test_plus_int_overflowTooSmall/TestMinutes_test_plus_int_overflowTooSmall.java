package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Minutes#plus(int)} reports overflow when the result
 * would fall below {@link Integer#MIN_VALUE}.
 */
public class TestMinutes_test_plus_int_overflowTooSmall {

    @Test
    public void plus_whenResultUnderflowsIntRange_throwsArithmeticException() {
        Minutes nearMinimum = Minutes.of(Integer.MIN_VALUE + 1);

        // Adding -2 would push the total below Integer.MIN_VALUE, causing overflow.
        assertThrows(ArithmeticException.class, () -> nearMinimum.plus(-2));
    }
}
