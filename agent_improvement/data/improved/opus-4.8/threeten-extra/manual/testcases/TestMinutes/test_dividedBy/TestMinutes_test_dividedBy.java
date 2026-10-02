package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Minutes#dividedBy(int)}.
 * <p>
 * Division uses integer arithmetic, so any fractional part is truncated
 * towards zero (for example, 12 minutes divided by 5 yields 2 minutes).
 */
public class TestMinutes_test_dividedBy {

    @Test
    public void test_dividedBy() {
        Minutes twelveMinutes = Minutes.of(12);

        // Exact divisions.
        assertEquals(Minutes.of(12), twelveMinutes.dividedBy(1));
        assertEquals(Minutes.of(6), twelveMinutes.dividedBy(2));
        assertEquals(Minutes.of(4), twelveMinutes.dividedBy(3));
        assertEquals(Minutes.of(3), twelveMinutes.dividedBy(4));
        assertEquals(Minutes.of(2), twelveMinutes.dividedBy(6));

        // Inexact division truncates towards zero: 12 / 5 == 2.
        assertEquals(Minutes.of(2), twelveMinutes.dividedBy(5));

        // Division by a negative divisor yields a negative result: 12 / -3 == -4.
        assertEquals(Minutes.of(-4), twelveMinutes.dividedBy(-3));
    }
}
