package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Hours#minus(int)} fails fast when the subtraction
 * would push the result above {@link Integer#MAX_VALUE}.
 */
public class TestHours_test_minus_int_overflowTooBig {

    @Test
    public void minus_whenResultExceedsIntegerMaxValue_throwsArithmeticException() {
        // (Integer.MAX_VALUE - 1) - (-2) == Integer.MAX_VALUE + 1, which overflows an int.
        Hours nearMax = Hours.of(Integer.MAX_VALUE - 1);

        assertThrows(ArithmeticException.class, () -> nearMax.minus(-2));
    }
}
