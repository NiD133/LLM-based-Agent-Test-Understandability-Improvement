package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_multipliedBy_overflowTooSmall {

    @Test
    public void test_multipliedBy_overflowTooSmall() {
        // Integer.MIN_VALUE / 2 - 1, when multiplied by 2, produces a value below Integer.MIN_VALUE
        int weeksBelowHalfMinValue = Integer.MIN_VALUE / 2 - 1;
        assertThrows(ArithmeticException.class, () -> Weeks.of(weeksBelowHalfMinValue).multipliedBy(2));
    }
}
