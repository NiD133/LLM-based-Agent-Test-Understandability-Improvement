package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_dividedBy {

    @Test
    public void test_dividedBy() {
        Minutes twelveMinutes = Minutes.of(12);

        // Dividing by 1 returns the same value unchanged
        assertEquals(Minutes.of(12), twelveMinutes.dividedBy(1));

        // Exact integer divisions
        assertEquals(Minutes.of(6), twelveMinutes.dividedBy(2));
        assertEquals(Minutes.of(4), twelveMinutes.dividedBy(3));
        assertEquals(Minutes.of(3), twelveMinutes.dividedBy(4));

        // Non-exact divisions truncate toward zero: 12/5 = 2 (not 2.4), 12/6 = 2
        assertEquals(Minutes.of(2), twelveMinutes.dividedBy(5));
        assertEquals(Minutes.of(2), twelveMinutes.dividedBy(6));

        // Negative divisor negates the result: 12 / -3 = -4
        assertEquals(Minutes.of(-4), twelveMinutes.dividedBy(-3));
    }
}
