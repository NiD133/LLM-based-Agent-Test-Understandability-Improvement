package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Months#dividedBy(int)} rejects division by zero.
 */
public class TestMonths_test_dividedBy_divideByZero {

    @Test
    public void dividedBy_zero_throwsArithmeticException() {
        // Dividing any amount of months by zero is undefined and must fail.
        assertThrows(ArithmeticException.class, () -> Months.of(1).dividedBy(0));
    }
}
