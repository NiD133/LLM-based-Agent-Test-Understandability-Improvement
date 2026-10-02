package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Months#ofYears(int)} reports numeric overflow.
 */
public class TestMonths_test_ofYears_overflow {

    /**
     * {@code ofYears} multiplies the year count by 12 (months per year).
     * A year count just above {@code Integer.MAX_VALUE / 12} cannot be
     * represented as months in an {@code int}, so the conversion must
     * fail with an {@link ArithmeticException} rather than silently wrap.
     */
    @Test
    public void test_ofYears_overflow() {
        int yearsThatOverflowWhenConvertedToMonths = (Integer.MAX_VALUE / 12) + 12;

        assertThrows(ArithmeticException.class,
                () -> Months.ofYears(yearsThatOverflowWhenConvertedToMonths));
    }
}
