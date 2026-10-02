package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestYears_test_abs_overflow {

    /**
     * Integer.MIN_VALUE has no positive counterpart in 32-bit signed arithmetic,
     * so negating it (as abs() does internally) throws ArithmeticException.
     */
    @Test
    public void test_abs_overflow() {
        assertThrows(ArithmeticException.class, () -> Years.of(Integer.MIN_VALUE).abs());
    }
}
