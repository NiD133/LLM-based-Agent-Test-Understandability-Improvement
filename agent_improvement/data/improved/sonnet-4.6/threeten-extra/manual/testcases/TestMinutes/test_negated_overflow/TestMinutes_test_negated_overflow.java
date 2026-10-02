package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_negated_overflow {

    // Integer.MIN_VALUE has no positive counterpart in int range, so negating it overflows.
    @Test
    public void test_negated_overflow() {
        assertThrows(ArithmeticException.class, () -> Minutes.of(Integer.MIN_VALUE).negated());
    }
}
