package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMonths_test_multipliedBy_overflowTooSmall {

    @Test
    public void test_multipliedBy_overflowTooSmall() {
        // Any value below Integer.MIN_VALUE / 2 will overflow when multiplied by 2,
        // because the product would be less than Integer.MIN_VALUE.
        int startingMonths = Integer.MIN_VALUE / 2 - 1;
        assertThrows(ArithmeticException.class, () -> Months.of(startingMonths).multipliedBy(2));
    }
}
