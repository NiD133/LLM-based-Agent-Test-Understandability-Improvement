package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_minus_TemporalAmount_overflowTooSmall {

    // One above MIN_VALUE: subtracting 2 from this value wraps below Integer.MIN_VALUE
    private static final int ONE_ABOVE_MIN = Integer.MIN_VALUE + 1;

    @Test
    public void test_minus_TemporalAmount_overflowTooSmall() {
        assertThrows(ArithmeticException.class,
                () -> Seconds.of(ONE_ABOVE_MIN).minus(Seconds.of(2)));
    }
}
