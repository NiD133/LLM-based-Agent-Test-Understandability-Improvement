package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestHours_test_plus_int_overflowTooSmall {

    // Adding -2 to (MIN_VALUE + 1) would produce a result below Integer.MIN_VALUE.
    private static final int NEAR_MIN = Integer.MIN_VALUE + 1;

    @Test
    public void test_plus_int_overflowTooSmall() {
        assertThrows(ArithmeticException.class, () -> Hours.of(NEAR_MIN).plus(-2));
    }
}
