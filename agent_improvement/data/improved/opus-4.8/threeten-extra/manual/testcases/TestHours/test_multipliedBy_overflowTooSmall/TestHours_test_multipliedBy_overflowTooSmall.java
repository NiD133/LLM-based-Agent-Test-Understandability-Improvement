package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Hours#multipliedBy(int)} reports numeric overflow when
 * the result would be smaller than {@link Integer#MIN_VALUE}.
 */
public class TestHours_test_multipliedBy_overflowTooSmall {

    @Test
    public void test_multipliedBy_overflowTooSmall() {
        // (Integer.MIN_VALUE / 2 - 1) is just past half of the minimum int value,
        // so doubling it underflows below Integer.MIN_VALUE and must overflow.
        Hours justBelowHalfMinValue = Hours.of(Integer.MIN_VALUE / 2 - 1);

        assertThrows(ArithmeticException.class, () -> justBelowHalfMinValue.multipliedBy(2));
    }
}
