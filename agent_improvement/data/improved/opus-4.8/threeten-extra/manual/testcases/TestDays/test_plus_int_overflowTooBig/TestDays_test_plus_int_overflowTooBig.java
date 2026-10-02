package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Days#plus(int)} rejects additions whose result would
 * exceed {@link Integer#MAX_VALUE}.
 */
public class TestDays_test_plus_int_overflowTooBig {

    /**
     * Adding 2 to a value one below {@code Integer.MAX_VALUE} pushes the result
     * past the upper int limit, so an {@link ArithmeticException} must be thrown.
     */
    @Test
    public void test_plus_int_overflowTooBig() {
        Days nearMaxValue = Days.of(Integer.MAX_VALUE - 1);

        assertThrows(ArithmeticException.class, () -> nearMaxValue.plus(2));
    }
}
