package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_minus_int_overflowTooSmall {

    @Test
    public void test_minus_int_overflowTooSmall() {
        assertThrows(ArithmeticException.class, () -> Seconds.of(Integer.MIN_VALUE + 1).minus(2));
    }
}
