package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_plus_int_overflowTooSmall {

    @Test
    public void test_plus_int_overflowTooSmall() {
        int nearMinimumSeconds = Integer.MIN_VALUE + 1;
        int secondsToSubtract = -2;

        assertThrows(
                ArithmeticException.class,
                () -> Seconds.of(nearMinimumSeconds).plus(secondsToSubtract));
    }
}
