package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Hours#dividedBy(int)} rejects division by zero.
 */
public class TestHours_test_dividedBy_divideByZero {

    @Test
    public void dividedBy_zero_throwsArithmeticException() {
        // Dividing any Hours amount by zero is undefined and must fail fast.
        assertThrows(ArithmeticException.class, () -> Hours.of(1).dividedBy(0));
    }
}
