package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that subtracting a {@link Hours} amount overflows when the result
 * would exceed {@link Integer#MAX_VALUE}.
 */
public class TestHours_test_minus_TemporalAmount_overflowTooBig {

    @Test
    public void test_minus_TemporalAmount_overflowTooBig() {
        Hours almostMax = Hours.of(Integer.MAX_VALUE - 1);

        // Subtracting -2 hours adds 2, pushing the total past Integer.MAX_VALUE.
        assertThrows(ArithmeticException.class, () -> almostMax.minus(Hours.of(-2)));
    }
}
