package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestHours_test_multipliedBy_overflowTooSmall {

    private static final int FIRST_VALUE_BELOW_SAFE_HALF_MINIMUM = Integer.MIN_VALUE / 2 - 1;
    private static final int DOUBLING_MULTIPLIER = 2;

    @Test
    public void test_multipliedBy_overflowTooSmall() {
        Hours hoursJustBelowSafeHalfMinimum = Hours.of(FIRST_VALUE_BELOW_SAFE_HALF_MINIMUM);

        assertThrows(
                ArithmeticException.class,
                () -> hoursJustBelowSafeHalfMinimum.multipliedBy(DOUBLING_MULTIPLIER));
    }
}
