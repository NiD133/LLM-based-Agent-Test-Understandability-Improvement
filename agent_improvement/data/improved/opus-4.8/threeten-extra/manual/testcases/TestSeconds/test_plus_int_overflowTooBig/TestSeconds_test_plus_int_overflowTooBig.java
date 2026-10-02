package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Seconds#plus(int)} reports integer overflow.
 */
public class TestSeconds_test_plus_int_overflowTooBig {

    @Test
    public void test_plus_int_overflowTooBig() {
        // Start one below the maximum, then add enough to overflow past Integer.MAX_VALUE.
        Seconds nearMaxValue = Seconds.of(Integer.MAX_VALUE - 1);

        assertThrows(ArithmeticException.class, () -> nearMaxValue.plus(2));
    }
}
