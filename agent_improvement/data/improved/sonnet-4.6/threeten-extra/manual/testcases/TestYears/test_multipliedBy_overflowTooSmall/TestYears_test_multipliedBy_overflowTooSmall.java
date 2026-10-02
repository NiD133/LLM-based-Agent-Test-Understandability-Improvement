package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestYears_test_multipliedBy_overflowTooSmall {

    @Test
    public void test_multipliedBy_overflowTooSmall() {
        // Any value strictly below Integer.MIN_VALUE / 2, when doubled, overflows below Integer.MIN_VALUE.
        // (Integer.MIN_VALUE / 2 - 1) * 2 = Integer.MIN_VALUE - 2, which cannot be stored as an int.
        int valueThatOverflowsWhenDoubled = Integer.MIN_VALUE / 2 - 1;

        assertThrows(ArithmeticException.class, () -> Years.of(valueThatOverflowsWhenDoubled).multipliedBy(2));
    }
}
