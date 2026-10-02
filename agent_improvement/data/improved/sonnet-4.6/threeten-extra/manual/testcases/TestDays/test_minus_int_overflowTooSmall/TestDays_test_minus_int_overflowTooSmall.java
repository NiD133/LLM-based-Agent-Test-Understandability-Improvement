package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestDays_test_minus_int_overflowTooSmall {

    // Days.minus() uses Math.subtractExact, so subtracting 2 from (MIN_VALUE + 1)
    // wraps below Integer.MIN_VALUE and must throw ArithmeticException.
    @Test
    public void test_minus_int_overflowTooSmall() {
        assertThrows(ArithmeticException.class, () -> Days.of(Integer.MIN_VALUE + 1).minus(2));
    }
}
