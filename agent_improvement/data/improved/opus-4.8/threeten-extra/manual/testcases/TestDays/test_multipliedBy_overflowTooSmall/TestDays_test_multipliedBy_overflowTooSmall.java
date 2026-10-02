package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Days#multipliedBy(int)} reports numeric overflow when the
 * result would be smaller than {@link Integer#MIN_VALUE}.
 */
public class TestDays_test_multipliedBy_overflowTooSmall {

    @Test
    public void test_multipliedBy_overflowTooSmall() {
        // (MIN_VALUE / 2 - 1) is just past half of the smallest int, so doubling
        // it underflows below Integer.MIN_VALUE and must raise ArithmeticException.
        Days tooSmallWhenDoubled = Days.of(Integer.MIN_VALUE / 2 - 1);

        assertThrows(ArithmeticException.class, () -> tooSmallWhenDoubled.multipliedBy(2));
    }
}
