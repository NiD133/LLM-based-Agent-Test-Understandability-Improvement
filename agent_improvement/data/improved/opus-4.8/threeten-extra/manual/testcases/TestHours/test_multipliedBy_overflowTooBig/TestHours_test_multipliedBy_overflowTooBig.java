package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Hours#multipliedBy(int)} rejects results that overflow an {@code int}.
 */
public class TestHours_test_multipliedBy_overflowTooBig {

    @Test
    public void test_multipliedBy_overflowTooBig() {
        // (Integer.MAX_VALUE / 2 + 1) * 2 exceeds Integer.MAX_VALUE, so the multiplication must overflow.
        Hours justOverHalfOfMax = Hours.of(Integer.MAX_VALUE / 2 + 1);

        assertThrows(ArithmeticException.class, () -> justOverHalfOfMax.multipliedBy(2));
    }
}
