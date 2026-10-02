package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestHours_test_multipliedBy_overflowTooSmall {

    @Test
    public void test_multipliedBy_overflowTooSmall() {
        // A value just below Integer.MIN_VALUE / 2 causes overflow when multiplied by 2
        int valueThatOverflowsWhenDoubled = Integer.MIN_VALUE / 2 - 1;
        assertThrows(ArithmeticException.class, () -> Hours.of(valueThatOverflowsWhenDoubled).multipliedBy(2));
    }
}
