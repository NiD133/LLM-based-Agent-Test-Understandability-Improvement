package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestSeconds_test_negated_overflow {

    @Test
    @DisplayName("negated() on Integer.MIN_VALUE throws ArithmeticException due to int overflow")
    public void test_negated_overflow() {
        // Negating Integer.MIN_VALUE cannot be represented as an int, so overflow must be detected.
        Seconds minValue = Seconds.of(Integer.MIN_VALUE);
        assertThrows(ArithmeticException.class, () -> minValue.negated());
    }
}
