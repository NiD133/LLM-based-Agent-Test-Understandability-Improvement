package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_plus_int_overflowTooBig {

    @Test
    public void plus_int_throwsWhenResultOverflowsIntMax() {
        // (Integer.MAX_VALUE - 1) + 2 exceeds Integer.MAX_VALUE, so the addition overflows.
        Minutes nearMaxMinutes = Minutes.of(Integer.MAX_VALUE - 1);

        assertThrows(ArithmeticException.class, () -> nearMaxMinutes.plus(2));
    }
}
