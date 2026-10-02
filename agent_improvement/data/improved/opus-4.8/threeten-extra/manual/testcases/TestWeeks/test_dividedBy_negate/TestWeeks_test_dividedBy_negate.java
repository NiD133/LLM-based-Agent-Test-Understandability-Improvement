package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Weeks#dividedBy(int)} performs integer division
 * and yields a negated result when divided by a negative divisor.
 */
public class TestWeeks_test_dividedBy_negate {

    @Test
    public void dividedBy_negativeDivisor_negatesAndDivides() {
        Weeks twelveWeeks = Weeks.of(12);

        // 12 / -3 = -4, using integer division
        Weeks result = twelveWeeks.dividedBy(-3);

        assertEquals(Weeks.of(-4), result);
    }
}
