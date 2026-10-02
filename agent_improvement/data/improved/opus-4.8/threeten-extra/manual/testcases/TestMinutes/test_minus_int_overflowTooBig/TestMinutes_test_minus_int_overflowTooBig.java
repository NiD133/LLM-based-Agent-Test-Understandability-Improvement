package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Minutes#minus(int)} throws {@link ArithmeticException}
 * when the subtraction overflows the upper bound of an {@code int}.
 */
public class TestMinutes_test_minus_int_overflowTooBig {

    @Test
    public void minus_whenResultExceedsIntegerMaxValue_throwsArithmeticException() {
        // (Integer.MAX_VALUE - 1) - (-2) == Integer.MAX_VALUE + 1, which overflows an int.
        Minutes almostMaxMinutes = Minutes.of(Integer.MAX_VALUE - 1);

        assertThrows(ArithmeticException.class, () -> almostMaxMinutes.minus(-2));
    }
}
