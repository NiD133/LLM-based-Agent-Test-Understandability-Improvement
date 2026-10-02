package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_minus_TemporalAmount_overflowTooBig {

    @Test
    public void test_minus_TemporalAmount_overflowTooBig() {
        // Subtracting -2 from (MAX_VALUE - 1) is equivalent to adding 2,
        // pushing the result past Integer.MAX_VALUE and causing overflow.
        assertThrows(ArithmeticException.class,
                () -> Seconds.of(Integer.MAX_VALUE - 1).minus(Seconds.of(-2)));
    }
}
