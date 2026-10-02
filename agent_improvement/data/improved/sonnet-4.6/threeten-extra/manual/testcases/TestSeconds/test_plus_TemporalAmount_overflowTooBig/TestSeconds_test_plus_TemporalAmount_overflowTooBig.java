package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_plus_TemporalAmount_overflowTooBig {

    // Adding 2 to (MAX_VALUE - 1) exceeds Integer.MAX_VALUE, so an ArithmeticException must be thrown.
    @Test
    public void test_plus_TemporalAmount_overflowTooBig() {
        assertThrows(ArithmeticException.class, () -> Seconds.of(Integer.MAX_VALUE - 1).plus(Seconds.of(2)));
    }
}
