package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Years#minus(int)} fails fast on arithmetic overflow.
 */
public class TestYears_test_minus_int_overflowTooBig {

    /**
     * Subtracting a negative amount adds to the year count. Starting from
     * (Integer.MAX_VALUE - 1) and subtracting -2 pushes the result past
     * Integer.MAX_VALUE, which must raise an ArithmeticException.
     */
    @Test
    public void test_minus_int_overflowTooBig() {
        Years nearMaxValue = Years.of(Integer.MAX_VALUE - 1);

        assertThrows(ArithmeticException.class, () -> nearMaxValue.minus(-2));
    }
}
