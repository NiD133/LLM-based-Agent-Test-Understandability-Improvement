package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Days#dividedBy(int)} rejects division by zero.
 */
public class TestDays_test_dividedBy_divideByZero {

    @Test
    public void dividedBy_zero_throwsArithmeticException() {
        assertThrows(ArithmeticException.class, () -> Days.of(1).dividedBy(0));
    }
}
