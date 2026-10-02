package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMonths_test_plus_TemporalAmount_overflowTooBig {

    @Test
    public void test_plus_TemporalAmount_overflowTooBig() {
        Months amountJustBelowIntegerMaximum = Months.of(Integer.MAX_VALUE - 1);
        Months twoMonths = Months.of(2);

        assertThrows(ArithmeticException.class, () -> amountJustBelowIntegerMaximum.plus(twoMonths));
    }
}
