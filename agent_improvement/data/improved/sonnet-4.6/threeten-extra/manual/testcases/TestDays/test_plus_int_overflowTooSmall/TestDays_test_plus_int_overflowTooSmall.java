package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestDays_test_plus_int_overflowTooSmall {

    // One above MIN_VALUE so that adding -2 causes int underflow
    private static final int NEAR_MIN_VALUE = Integer.MIN_VALUE + 1;

    @Test
    public void test_plus_int_overflowTooSmall() {
        assertThrows(ArithmeticException.class, () -> Days.of(NEAR_MIN_VALUE).plus(-2));
    }
}
