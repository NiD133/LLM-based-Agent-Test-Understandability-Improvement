package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestHours_test_negated_overflow {

    @Test
    public void negatedThrowsOnIntegerMinValueOverflow() {
        assertThrows(ArithmeticException.class, () -> Hours.of(Integer.MIN_VALUE).negated());
    }
}
