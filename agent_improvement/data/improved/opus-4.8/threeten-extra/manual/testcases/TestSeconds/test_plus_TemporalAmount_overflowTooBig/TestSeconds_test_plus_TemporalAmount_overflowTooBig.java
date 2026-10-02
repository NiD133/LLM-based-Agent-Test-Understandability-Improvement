package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that adding two {@link Seconds} amounts overflows the {@code int} range
 * and is reported as an {@link ArithmeticException}.
 */
public class TestSeconds_test_plus_TemporalAmount_overflowTooBig {

    @Test
    public void test_plus_TemporalAmount_overflowTooBig() {
        // (Integer.MAX_VALUE - 1) + 2 exceeds Integer.MAX_VALUE, so the addition must overflow.
        Seconds nearMax = Seconds.of(Integer.MAX_VALUE - 1);
        Seconds two = Seconds.of(2);

        assertThrows(ArithmeticException.class, () -> nearMax.plus(two));
    }
}
