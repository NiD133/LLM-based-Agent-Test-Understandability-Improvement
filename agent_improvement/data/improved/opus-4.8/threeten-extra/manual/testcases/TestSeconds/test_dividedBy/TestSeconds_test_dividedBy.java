package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Seconds#dividedBy(int)}, which performs integer division of the
 * stored second count by the given divisor (truncating any remainder).
 */
public class TestSeconds_test_dividedBy {

    @Test
    public void dividedBy_usesTruncatingIntegerDivision() {
        Seconds twelveSeconds = Seconds.of(12);

        // Exact divisions: 12 / divisor has no remainder.
        assertEquals(Seconds.of(12), twelveSeconds.dividedBy(1));
        assertEquals(Seconds.of(6), twelveSeconds.dividedBy(2));
        assertEquals(Seconds.of(4), twelveSeconds.dividedBy(3));
        assertEquals(Seconds.of(3), twelveSeconds.dividedBy(4));
        assertEquals(Seconds.of(2), twelveSeconds.dividedBy(6));

        // Inexact divisions: the remainder is truncated towards zero (12 / 5 == 2).
        assertEquals(Seconds.of(2), twelveSeconds.dividedBy(5));

        // A negative divisor yields a negative result (12 / -3 == -4).
        assertEquals(Seconds.of(-4), twelveSeconds.dividedBy(-3));
    }
}
