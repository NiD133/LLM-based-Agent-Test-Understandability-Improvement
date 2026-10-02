package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMonths_test_plus_int_overflowTooSmall {

    @Test
    public void test_plus_int_overflowTooSmall() {
        // (MIN_VALUE + 1) + (-2) = MIN_VALUE - 1, which underflows int range
        int nearMinValue = Integer.MIN_VALUE + 1;
        int amountToAdd = -2;
        assertThrows(ArithmeticException.class, () -> Months.of(nearMinValue).plus(amountToAdd));
    }
}
