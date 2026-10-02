package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Seconds#dividedBy(int)} rejects a zero divisor.
 */
public class TestSeconds_test_dividedBy_divideByZero {

    @Test
    public void dividedBy_zeroDivisor_throwsArithmeticException() {
        Seconds oneSecond = Seconds.of(1);

        assertThrows(ArithmeticException.class, () -> oneSecond.dividedBy(0));
    }
}
