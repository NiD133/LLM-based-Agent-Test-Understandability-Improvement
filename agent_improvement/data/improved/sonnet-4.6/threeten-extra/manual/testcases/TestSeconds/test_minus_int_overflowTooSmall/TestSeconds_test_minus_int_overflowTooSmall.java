package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_minus_int_overflowTooSmall {

    @Test
    public void test_minus_int_overflowTooSmall() {
        // Subtracting 2 from (MIN_VALUE + 1) would go below Integer.MIN_VALUE — must throw
        assertThrows(ArithmeticException.class, () -> Seconds.of(Integer.MIN_VALUE + 1).minus(2));
    }
}
