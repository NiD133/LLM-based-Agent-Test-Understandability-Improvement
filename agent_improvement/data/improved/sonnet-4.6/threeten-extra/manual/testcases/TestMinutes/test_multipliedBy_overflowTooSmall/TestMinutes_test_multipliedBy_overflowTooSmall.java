package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_multipliedBy_overflowTooSmall {

    @Test
    public void test_multipliedBy_overflowTooSmall() {
        // A value one below the midpoint of MIN_VALUE causes overflow when doubled
        int justBelowHalfMinValue = Integer.MIN_VALUE / 2 - 1;
        assertThrows(ArithmeticException.class, () -> Minutes.of(justBelowHalfMinValue).multipliedBy(2));
    }
}
