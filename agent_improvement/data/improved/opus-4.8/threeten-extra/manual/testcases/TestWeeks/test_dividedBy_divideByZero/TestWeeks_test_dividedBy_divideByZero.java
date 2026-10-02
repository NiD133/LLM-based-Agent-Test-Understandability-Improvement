package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that dividing a {@link Weeks} amount by zero is rejected.
 */
public class TestWeeks_test_dividedBy_divideByZero {

    @Test
    public void dividedBy_zero_throwsArithmeticException() {
        Weeks oneWeek = Weeks.of(1);

        assertThrows(ArithmeticException.class, () -> oneWeek.dividedBy(0));
    }
}
