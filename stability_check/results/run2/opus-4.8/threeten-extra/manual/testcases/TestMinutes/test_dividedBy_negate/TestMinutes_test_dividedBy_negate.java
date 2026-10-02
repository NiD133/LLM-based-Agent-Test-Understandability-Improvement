package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Minutes#dividedBy(int)} performs signed integer
 * division, so dividing a positive amount by a negative divisor yields a
 * negative result.
 */
public class TestMinutes_test_dividedBy_negate {

    @Test
    public void dividingByNegativeDivisorNegatesResult() {
        Minutes twelveMinutes = Minutes.of(12);

        Minutes result = twelveMinutes.dividedBy(-3);

        assertEquals(Minutes.of(-4), result);
    }
}
