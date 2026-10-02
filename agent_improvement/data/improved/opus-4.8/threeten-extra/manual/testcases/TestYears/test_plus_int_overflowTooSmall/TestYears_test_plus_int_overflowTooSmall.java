package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Years#plus(int)} fails with an {@link ArithmeticException}
 * when the addition underflows the {@code int} range.
 */
public class TestYears_test_plus_int_overflowTooSmall {

    @Test
    public void plus_int_belowIntMinValue_throwsArithmeticException() {
        // (Integer.MIN_VALUE + 1) + (-2) underflows the int range.
        Years almostMinValue = Years.of(Integer.MIN_VALUE + 1);

        assertThrows(ArithmeticException.class, () -> almostMinValue.plus(-2));
    }
}
