package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_abs_overflow {

    // Integer.MIN_VALUE has no positive counterpart in int range, so abs() must throw ArithmeticException
    @Test
    public void test_abs_overflow() {
        assertThrows(ArithmeticException.class, () -> Minutes.of(Integer.MIN_VALUE).abs());
    }
}
