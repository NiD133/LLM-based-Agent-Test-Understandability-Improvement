package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Days#minus(int)} reports an overflow when the
 * subtraction would push the result past {@link Integer#MAX_VALUE}.
 */
public class TestDays_test_minus_int_overflowTooBig {

    @Test
    public void minus_whenResultExceedsMaxValue_throwsArithmeticException() {
        // (Integer.MAX_VALUE - 1) - (-2) == Integer.MAX_VALUE + 1, which overflows an int.
        Days nearMaxValue = Days.of(Integer.MAX_VALUE - 1);

        assertThrows(ArithmeticException.class, () -> nearMaxValue.minus(-2));
    }
}
