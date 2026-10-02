package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Seconds#dividedBy(int)} performs integer division by a
 * negative divisor, yielding a negative result.
 */
public class TestSeconds_test_dividedBy_negate {

    @Test
    public void dividedBy_negativeDivisor_negatesResult() {
        Seconds twelveSeconds = Seconds.of(12);

        // 12 / -3 == -4
        assertEquals(Seconds.of(-4), twelveSeconds.dividedBy(-3));
    }
}
