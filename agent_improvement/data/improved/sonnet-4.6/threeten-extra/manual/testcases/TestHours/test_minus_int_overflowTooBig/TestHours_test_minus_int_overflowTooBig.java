package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestHours_test_minus_int_overflowTooBig {

    @Test
    public void test_minus_int_overflowTooBig() {
        // Subtracting -2 is equivalent to adding 2, pushing the value above Integer.MAX_VALUE
        Hours nearMax = Hours.of(Integer.MAX_VALUE - 1);
        assertThrows(ArithmeticException.class, () -> nearMax.minus(-2));
    }
}
