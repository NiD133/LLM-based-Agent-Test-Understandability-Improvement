package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestHours_test_negated_overflow {

    /**
     * Negating the smallest possible int value has no positive counterpart,
     * so {@link Hours#negated()} must overflow and throw an exception.
     */
    @Test
    public void test_negated_overflow() {
        Hours minValueHours = Hours.of(Integer.MIN_VALUE);

        assertThrows(ArithmeticException.class, () -> minValueHours.negated());
    }
}
