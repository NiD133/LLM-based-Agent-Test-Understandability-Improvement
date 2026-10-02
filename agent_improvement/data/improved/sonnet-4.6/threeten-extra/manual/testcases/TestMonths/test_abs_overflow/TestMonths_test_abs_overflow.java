package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMonths_test_abs_overflow {

    @Test
    public void test_abs_overflow() {
        // abs() negates the value internally; negating Integer.MIN_VALUE overflows int range
        assertThrows(ArithmeticException.class, () -> Months.of(Integer.MIN_VALUE).abs());
    }
}
