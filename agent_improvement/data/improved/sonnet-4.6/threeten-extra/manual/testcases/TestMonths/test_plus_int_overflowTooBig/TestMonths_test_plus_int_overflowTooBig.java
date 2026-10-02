package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMonths_test_plus_int_overflowTooBig {

    @Test
    public void test_plus_int_overflowTooBig() {
        // Adding 2 to MAX_VALUE - 1 exceeds Integer.MAX_VALUE; plus() must throw
        assertThrows(ArithmeticException.class, () -> Months.of(Integer.MAX_VALUE - 1).plus(2));
    }
}
