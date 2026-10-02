package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestHours_test_negated_overflow {

    // Negating Integer.MIN_VALUE would produce a value outside int range, so an ArithmeticException is expected.
    @Test
    public void test_negated_overflow() {
        assertThrows(ArithmeticException.class, () -> Hours.of(Integer.MIN_VALUE).negated());
    }
}
