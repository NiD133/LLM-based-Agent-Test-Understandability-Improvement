package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestHours_test_plus_int_overflowTooBig {

    @Test
    public void test_plus_int_overflowTooBig() {
        assertThrows(ArithmeticException.class, () -> Hours.of(Integer.MAX_VALUE - 1).plus(2));
    }
}
