package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_multipliedBy_overflowTooBig {

    /**
     * Multiplying a Minutes value whose doubled result exceeds Integer.MAX_VALUE
     * must fail with an ArithmeticException rather than silently overflowing.
     */
    @Test
    public void test_multipliedBy_overflowTooBig() {
        // (Integer.MAX_VALUE / 2 + 1) * 2 overflows the int range.
        Minutes justOverHalfMax = Minutes.of(Integer.MAX_VALUE / 2 + 1);

        assertThrows(ArithmeticException.class, () -> justOverHalfMax.multipliedBy(2));
    }
}
