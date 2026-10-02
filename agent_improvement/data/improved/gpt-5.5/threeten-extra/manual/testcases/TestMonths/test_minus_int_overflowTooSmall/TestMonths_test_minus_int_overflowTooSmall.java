package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMonths_test_minus_int_overflowTooSmall {

    @Test
    public void test_minus_int_overflowTooSmall() {
        int oneAboveMinimumMonths = Integer.MIN_VALUE + 1;
        int monthsToSubtract = 2;

        assertThrows(
                ArithmeticException.class,
                () -> Months.of(oneAboveMinimumMonths).minus(monthsToSubtract));
    }
}
