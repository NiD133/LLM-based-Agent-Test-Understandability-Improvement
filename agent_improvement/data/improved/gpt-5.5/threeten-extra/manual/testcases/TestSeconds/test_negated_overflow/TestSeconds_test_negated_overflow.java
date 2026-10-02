package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_negated_overflow {

    @Test
    public void test_negated_overflow() {
        int minimumIntSeconds = Integer.MIN_VALUE;

        assertThrows(ArithmeticException.class, () -> Seconds.of(minimumIntSeconds).negated());
    }
}
