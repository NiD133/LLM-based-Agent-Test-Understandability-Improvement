package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Years#plus(java.time.temporal.TemporalAmount)} reports an
 * arithmetic overflow instead of silently wrapping around.
 */
public class TestYears_test_plus_TemporalAmount_overflowTooBig {

    @Test
    public void plus_temporalAmount_overflowsWhenResultExceedsIntMax() {
        // (Integer.MAX_VALUE - 1) + 2 would exceed Integer.MAX_VALUE, so adding
        // another TemporalAmount of 2 years must throw rather than overflow.
        Years almostMaxYears = Years.of(Integer.MAX_VALUE - 1);
        Years twoYears = Years.of(2);

        assertThrows(ArithmeticException.class, () -> almostMaxYears.plus(twoYears));
    }
}
