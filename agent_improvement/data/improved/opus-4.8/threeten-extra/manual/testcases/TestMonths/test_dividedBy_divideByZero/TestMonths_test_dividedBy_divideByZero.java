package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that dividing a {@link Months} amount by zero is rejected.
 */
public class TestMonths_test_dividedBy_divideByZero {

    @Test
    public void dividedBy_zero_throwsArithmeticException() {
        Months oneMonth = Months.of(1);

        assertThrows(ArithmeticException.class, () -> oneMonth.dividedBy(0));
    }
}
