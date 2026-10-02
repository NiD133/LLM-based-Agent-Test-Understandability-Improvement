package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestDays_test_negated_overflow {

    @Test
    public void test_negated_overflow() {
        assertThrows(ArithmeticException.class, () -> Days.of(Integer.MIN_VALUE).negated());
    }
}
