package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_abs_overflow {

    @Test
    public void test_abs_overflow() {
        // Integer.MIN_VALUE has no positive counterpart in int range:
        // Math.abs(Integer.MIN_VALUE) would be 2^31, which exceeds Integer.MAX_VALUE.
        assertThrows(ArithmeticException.class, () -> Weeks.of(Integer.MIN_VALUE).abs());
    }
}
