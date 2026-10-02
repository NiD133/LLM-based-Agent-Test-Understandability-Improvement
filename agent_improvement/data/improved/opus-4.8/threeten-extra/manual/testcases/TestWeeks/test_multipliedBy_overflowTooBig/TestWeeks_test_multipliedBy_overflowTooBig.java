package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Weeks#multipliedBy(int)} throws {@link ArithmeticException}
 * when the multiplication overflows the {@code int} range.
 */
public class TestWeeks_test_multipliedBy_overflowTooBig {

    @Test
    public void test_multipliedBy_overflowTooBig() {
        // (Integer.MAX_VALUE / 2 + 1) * 2 exceeds Integer.MAX_VALUE, so the
        // multiplication must overflow and raise an ArithmeticException.
        int weeksJustOverHalfMax = Integer.MAX_VALUE / 2 + 1;

        assertThrows(
                ArithmeticException.class,
                () -> Weeks.of(weeksJustOverHalfMax).multipliedBy(2));
    }
}
