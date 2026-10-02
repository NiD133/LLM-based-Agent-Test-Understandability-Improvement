package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Years#dividedBy(int)} rejects division by zero.
 */
public class TestYears_test_dividedBy_divideByZero {

    @Test
    public void dividedBy_zero_throwsArithmeticException() {
        Years oneYear = Years.of(1);

        assertThrows(ArithmeticException.class, () -> oneYear.dividedBy(0));
    }
}
