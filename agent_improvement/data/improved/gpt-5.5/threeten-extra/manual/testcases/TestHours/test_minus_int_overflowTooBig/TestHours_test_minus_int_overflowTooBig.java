package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestHours_test_minus_int_overflowTooBig {

    @Test
    public void test_minus_int_overflowTooBig() {
        int amountJustBelowMaximum = Integer.MAX_VALUE - 1;
        int amountToSubtract = -2;

        assertThrows(
                ArithmeticException.class,
                () -> Hours.of(amountJustBelowMaximum).minus(amountToSubtract));
    }
}
