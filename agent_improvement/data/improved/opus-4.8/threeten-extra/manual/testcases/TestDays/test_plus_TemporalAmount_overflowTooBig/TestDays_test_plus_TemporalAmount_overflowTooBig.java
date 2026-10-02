package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that adding two {@link Days} amounts whose total exceeds
 * {@link Integer#MAX_VALUE} fails with an {@link ArithmeticException}
 * rather than silently overflowing.
 */
public class TestDays_test_plus_TemporalAmount_overflowTooBig {

    @Test
    public void plus_temporalAmount_overflowingIntMaxValue_throwsArithmeticException() {
        Days almostMaxDays = Days.of(Integer.MAX_VALUE - 1);
        Days twoDays = Days.of(2);

        // (Integer.MAX_VALUE - 1) + 2 overflows the int range.
        assertThrows(ArithmeticException.class, () -> almostMaxDays.plus(twoDays));
    }
}
