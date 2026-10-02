package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMonths_test_minus_TemporalAmount_overflowTooBig {

    private static final Months NEAR_MAXIMUM_MONTHS = Months.of(Integer.MAX_VALUE - 1);
    private static final Months NEGATIVE_TWO_MONTHS = Months.of(-2);

    @Test
    public void test_minus_TemporalAmount_overflowTooBig() {
        assertThrows(
                ArithmeticException.class,
                () -> NEAR_MAXIMUM_MONTHS.minus(NEGATIVE_TWO_MONTHS));
    }
}
