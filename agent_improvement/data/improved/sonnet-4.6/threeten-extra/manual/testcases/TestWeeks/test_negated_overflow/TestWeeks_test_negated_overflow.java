package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_negated_overflow {

    @Test
    public void test_negated_overflow() {
        // Integer.MIN_VALUE has no positive counterpart in two's complement,
        // so negating it must throw rather than silently wrap around.
        assertThrows(ArithmeticException.class, () -> Weeks.of(Integer.MIN_VALUE).negated());
    }
}
