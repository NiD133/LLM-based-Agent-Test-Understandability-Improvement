package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_minus_int_overflowTooSmall {

    @Test
    public void test_minus_int_overflowTooSmall() {
        int amountJustAboveMinimum = Integer.MIN_VALUE + 1;
        int minutesToSubtract = 2;

        assertThrows(
                ArithmeticException.class,
                () -> Minutes.of(amountJustAboveMinimum).minus(minutesToSubtract));
    }
}
