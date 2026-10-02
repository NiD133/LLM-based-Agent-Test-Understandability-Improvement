package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Months#plus(java.time.temporal.TemporalAmount)} fails with an
 * {@link ArithmeticException} when the addition overflows the {@code int} range.
 */
public class TestMonths_test_plus_TemporalAmount_overflowTooBig {

    @Test
    public void plus_overflowsWhenResultExceedsIntMax() {
        Months nearMax = Months.of(Integer.MAX_VALUE - 1);
        Months two = Months.of(2);

        // (Integer.MAX_VALUE - 1) + 2 overflows int, so addExact must throw.
        assertThrows(ArithmeticException.class, () -> nearMax.plus(two));
    }
}
