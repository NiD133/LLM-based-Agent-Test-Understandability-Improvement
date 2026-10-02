package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestDays_test_plus_int_overflowTooSmall {

    @Test
    public void test_plus_int_overflowTooSmall() {
        assertThrows(ArithmeticException.class, () -> Days.of(Integer.MIN_VALUE + 1).plus(-2));
    }
}
