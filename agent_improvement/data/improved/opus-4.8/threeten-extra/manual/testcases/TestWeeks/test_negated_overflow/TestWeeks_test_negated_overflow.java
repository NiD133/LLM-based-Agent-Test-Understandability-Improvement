package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_negated_overflow {

    /**
     * Negating the smallest possible weeks value overflows an int, so
     * {@link Weeks#negated()} must throw an {@link ArithmeticException}.
     */
    @Test
    public void test_negated_overflow() {
        Weeks minValueWeeks = Weeks.of(Integer.MIN_VALUE);

        assertThrows(ArithmeticException.class, () -> minValueWeeks.negated());
    }
}
