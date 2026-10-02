package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that subtracting a {@link Minutes} amount overflows the int range
 * when the result would exceed {@link Integer#MAX_VALUE}.
 */
public class TestMinutes_test_minus_TemporalAmount_overflowTooBig {

    @Test
    public void test_minus_TemporalAmount_overflowTooBig() {
        // (MAX_VALUE - 1) minus (-2) equals (MAX_VALUE + 1), which overflows an int.
        Minutes nearMaxValue = Minutes.of(Integer.MAX_VALUE - 1);
        Minutes negativeTwo = Minutes.of(-2);

        assertThrows(ArithmeticException.class, () -> nearMaxValue.minus(negativeTwo));
    }
}
