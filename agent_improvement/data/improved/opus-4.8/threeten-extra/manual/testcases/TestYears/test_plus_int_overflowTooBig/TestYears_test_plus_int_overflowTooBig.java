package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Years#plus(int)} rejects additions that overflow {@code int}.
 */
public class TestYears_test_plus_int_overflowTooBig {

    /**
     * Adding 2 to a {@code Years} value just below {@code Integer.MAX_VALUE}
     * exceeds the {@code int} range, so an {@link ArithmeticException} is thrown.
     */
    @Test
    public void plus_int_overflowsAboveMaxValue() {
        Years nearMaxValue = Years.of(Integer.MAX_VALUE - 1);

        assertThrows(ArithmeticException.class, () -> nearMaxValue.plus(2));
    }
}
