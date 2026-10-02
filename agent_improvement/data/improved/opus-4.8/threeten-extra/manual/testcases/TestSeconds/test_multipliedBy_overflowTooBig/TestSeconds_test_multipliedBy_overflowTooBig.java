package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Seconds#multipliedBy(int)} reports arithmetic overflow.
 */
public class TestSeconds_test_multipliedBy_overflowTooBig {

    @Test
    public void test_multipliedBy_overflowTooBig() {
        // Choose a value whose double exceeds Integer.MAX_VALUE, so multiplying by 2 overflows.
        Seconds justOverHalfMax = Seconds.of(Integer.MAX_VALUE / 2 + 1);

        assertThrows(ArithmeticException.class, () -> justOverHalfMax.multipliedBy(2));
    }
}
