package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_abs_overflow {

    @Test
    public void test_abs_overflow() {
        // Integer.MIN_VALUE cannot be negated within int range, so abs() must throw
        assertThrows(ArithmeticException.class, () -> Seconds.of(Integer.MIN_VALUE).abs());
    }
}
