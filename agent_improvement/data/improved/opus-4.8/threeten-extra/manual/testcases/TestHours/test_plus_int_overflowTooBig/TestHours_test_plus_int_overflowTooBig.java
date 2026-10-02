package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Hours#plus(int)} reports overflow when the resulting
 * number of hours would exceed {@link Integer#MAX_VALUE}.
 */
public class TestHours_test_plus_int_overflowTooBig {

    @Test
    public void plus_int_throwsArithmeticException_whenResultOverflowsIntMaxValue() {
        Hours nearMaximum = Hours.of(Integer.MAX_VALUE - 1);

        // Adding 2 pushes the total one past Integer.MAX_VALUE, which must overflow.
        assertThrows(ArithmeticException.class, () -> nearMaximum.plus(2));
    }
}
