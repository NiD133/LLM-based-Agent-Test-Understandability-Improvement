package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Hours#abs()} reports overflow instead of returning a wrong value.
 */
public class TestHours_test_abs_overflow {

    /**
     * The absolute value of {@code Integer.MIN_VALUE} cannot be represented as an
     * {@code int}, so taking the absolute value of the most-negative Hours amount
     * must throw an {@link ArithmeticException} rather than overflow silently.
     */
    @Test
    public void test_abs_overflow() {
        Hours mostNegative = Hours.of(Integer.MIN_VALUE);

        assertThrows(ArithmeticException.class, () -> mostNegative.abs());
    }
}
