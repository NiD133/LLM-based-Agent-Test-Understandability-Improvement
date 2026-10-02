package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Weeks#minus(java.time.temporal.TemporalAmount)} fails with an
 * {@link ArithmeticException} when the subtraction overflows the {@code int} range.
 */
public class TestWeeks_test_minus_TemporalAmount_overflowTooBig {

    @Test
    public void minus_overflowsWhenResultExceedsIntMaxValue() {
        // Subtracting -2 weeks adds 2, pushing (Integer.MAX_VALUE - 1) past Integer.MAX_VALUE.
        Weeks nearMax = Weeks.of(Integer.MAX_VALUE - 1);
        Weeks minusTwo = Weeks.of(-2);

        assertThrows(ArithmeticException.class, () -> nearMax.minus(minusTwo));
    }
}
