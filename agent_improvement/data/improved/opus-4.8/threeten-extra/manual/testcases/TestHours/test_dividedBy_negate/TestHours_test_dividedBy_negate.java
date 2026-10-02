package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Hours#dividedBy(int)} divides by a negative divisor,
 * producing a negated result.
 */
public class TestHours_test_dividedBy_negate {

    @Test
    public void dividedBy_negativeDivisor_negatesQuotient() {
        Hours twelveHours = Hours.of(12);

        // 12 / -3 = -4
        Hours quotient = twelveHours.dividedBy(-3);

        assertEquals(Hours.of(-4), quotient);
    }
}
