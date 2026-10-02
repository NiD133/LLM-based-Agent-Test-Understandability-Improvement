package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Years#abs()} fails when the result cannot fit in an {@code int}.
 */
public class TestYears_test_abs_overflow {

    /**
     * The absolute value of {@code Integer.MIN_VALUE} is one greater than
     * {@code Integer.MAX_VALUE}, so negating it overflows and must throw.
     */
    @Test
    public void abs_ofIntegerMinValue_throwsArithmeticException() {
        Years extremeNegative = Years.of(Integer.MIN_VALUE);

        assertThrows(ArithmeticException.class, () -> extremeNegative.abs());
    }
}
