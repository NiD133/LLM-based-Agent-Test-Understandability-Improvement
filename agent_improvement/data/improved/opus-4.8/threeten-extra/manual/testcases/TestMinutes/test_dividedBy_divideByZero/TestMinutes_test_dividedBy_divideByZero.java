package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Minutes#dividedBy(int)} rejects division by zero.
 */
public class TestMinutes_test_dividedBy_divideByZero {

    @Test
    public void dividedBy_zero_throwsArithmeticException() {
        Minutes oneMinute = Minutes.of(1);

        assertThrows(ArithmeticException.class, () -> oneMinute.dividedBy(0));
    }
}
