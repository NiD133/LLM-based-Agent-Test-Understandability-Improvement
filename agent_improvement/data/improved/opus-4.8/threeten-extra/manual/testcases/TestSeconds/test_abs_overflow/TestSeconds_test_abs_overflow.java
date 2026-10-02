package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Seconds#abs()} reports overflow.
 */
public class TestSeconds_test_abs_overflow {

    /**
     * The absolute value of {@code Integer.MIN_VALUE} cannot be represented as
     * an {@code int}, so calling {@code abs()} on it must throw an
     * {@link ArithmeticException} rather than silently overflowing.
     */
    @Test
    public void test_abs_overflow() {
        Seconds mostNegative = Seconds.of(Integer.MIN_VALUE);

        assertThrows(ArithmeticException.class, () -> mostNegative.abs());
    }
}
