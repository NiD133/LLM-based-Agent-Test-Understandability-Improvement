package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Minutes#dividedBy(int)} performs signed integer division,
 * producing a negated result when divided by a negative divisor.
 */
public class TestMinutes_test_dividedBy_negate {

    @Test
    public void dividedBy_negativeDivisor_returnsNegatedQuotient() {
        Minutes twelveMinutes = Minutes.of(12);

        // 12 / -3 = -4 (integer division, sign flipped by the negative divisor)
        Minutes result = twelveMinutes.dividedBy(-3);

        assertEquals(Minutes.of(-4), result);
    }
}
