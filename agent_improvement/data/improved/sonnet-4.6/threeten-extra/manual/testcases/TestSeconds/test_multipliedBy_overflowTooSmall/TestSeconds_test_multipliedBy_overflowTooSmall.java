package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_multipliedBy_overflowTooSmall {

    // One below the midpoint of Integer.MIN_VALUE; multiplying by 2 pushes the
    // result past Integer.MIN_VALUE, causing signed-integer overflow.
    private static final int BELOW_HALF_MIN_VALUE = Integer.MIN_VALUE / 2 - 1;

    @Test
    public void test_multipliedBy_overflowTooSmall() {
        assertThrows(ArithmeticException.class,
                () -> Seconds.of(BELOW_HALF_MIN_VALUE).multipliedBy(2));
    }
}
