package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestHours_test_multipliedBy_overflowTooBig {

    @Test
    public void test_multipliedBy_overflowTooBig() {
        // The smallest int value that overflows when multiplied by 2:
        // Integer.MAX_VALUE / 2 + 1 exceeds the halfway point, so doubling it exceeds Integer.MAX_VALUE.
        int smallestOverflowingValue = Integer.MAX_VALUE / 2 + 1;
        assertThrows(ArithmeticException.class, () -> Hours.of(smallestOverflowingValue).multipliedBy(2));
    }
}
