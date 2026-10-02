package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestYears_test_negated_overflow {

    /**
     * Negating the smallest possible value has no positive counterpart that fits
     * in an int, so {@link Years#negated()} must fail with an {@link ArithmeticException}.
     */
    @Test
    public void test_negated_overflow() {
        Years minValueYears = Years.of(Integer.MIN_VALUE);

        assertThrows(ArithmeticException.class, () -> minValueYears.negated());
    }
}
