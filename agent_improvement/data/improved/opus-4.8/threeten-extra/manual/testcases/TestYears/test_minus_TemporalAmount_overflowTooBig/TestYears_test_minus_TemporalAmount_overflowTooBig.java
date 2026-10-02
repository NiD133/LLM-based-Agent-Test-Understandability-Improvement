package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Years#minus(java.time.temporal.TemporalAmount)} throws
 * {@link ArithmeticException} when the subtraction overflows {@code int}.
 */
public class TestYears_test_minus_TemporalAmount_overflowTooBig {

    @Test
    public void test_minus_TemporalAmount_overflowTooBig() {
        // (Integer.MAX_VALUE - 1) - (-2) == Integer.MAX_VALUE + 1, which overflows an int.
        Years almostMax = Years.of(Integer.MAX_VALUE - 1);
        Years amountToSubtract = Years.of(-2);

        assertThrows(ArithmeticException.class, () -> almostMax.minus(amountToSubtract));
    }
}
