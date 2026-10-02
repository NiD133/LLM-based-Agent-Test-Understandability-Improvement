package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestDays_test_multipliedBy_overflowTooSmall {

    @Test
    public void test_multipliedBy_overflowTooSmall() {
        // One below half of MIN_VALUE: multiplying by 2 produces a value smaller than Integer.MIN_VALUE
        int belowHalfMinValue = Integer.MIN_VALUE / 2 - 1;
        assertThrows(ArithmeticException.class, () -> Days.of(belowHalfMinValue).multipliedBy(2));
    }
}
