package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Seconds#minus(int)} fails fast when the subtraction
 * would push the result above {@link Integer#MAX_VALUE}.
 */
public class TestSeconds_test_minus_int_overflowTooBig {

    @Test
    public void minus_whenResultOverflowsMaxValue_throwsArithmeticException() {
        // Subtracting -2 from (MAX_VALUE - 1) is equivalent to adding 2,
        // which exceeds Integer.MAX_VALUE and must overflow.
        Seconds nearMax = Seconds.of(Integer.MAX_VALUE - 1);

        assertThrows(ArithmeticException.class, () -> nearMax.minus(-2));
    }
}
