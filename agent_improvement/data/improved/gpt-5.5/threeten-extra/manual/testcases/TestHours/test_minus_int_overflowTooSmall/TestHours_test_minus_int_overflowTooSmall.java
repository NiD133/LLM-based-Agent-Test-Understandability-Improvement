package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestHours_test_minus_int_overflowTooSmall {

    @Test
    public void test_minus_int_overflowTooSmall() {
        int startingHours = Integer.MIN_VALUE + 1;
        int hoursToSubtract = 2;

        assertThrows(
                ArithmeticException.class,
                () -> Hours.of(startingHours).minus(hoursToSubtract));
    }
}
