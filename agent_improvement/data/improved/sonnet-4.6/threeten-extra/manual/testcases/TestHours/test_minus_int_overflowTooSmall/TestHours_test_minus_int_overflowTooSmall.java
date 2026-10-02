package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestHours_test_minus_int_overflowTooSmall {

    // Subtracting 2 from (MIN_VALUE + 1) would yield MIN_VALUE - 1, which underflows int range.
    @Test
    public void test_minus_int_overflowTooSmall() {
        assertThrows(ArithmeticException.class, () -> Hours.of(Integer.MIN_VALUE + 1).minus(2));
    }
}
