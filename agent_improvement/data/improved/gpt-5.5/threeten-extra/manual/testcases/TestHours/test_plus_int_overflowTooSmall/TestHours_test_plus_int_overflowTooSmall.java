package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestHours_test_plus_int_overflowTooSmall {

    @Test
    public void test_plus_int_overflowTooSmall() {
        int smallestValueThatCanStillSubtractOne = Integer.MIN_VALUE + 1;
        int amountThatOverflowsBelowIntegerMinimum = -2;

        assertThrows(
                ArithmeticException.class,
                () -> Hours.of(smallestValueThatCanStillSubtractOne)
                        .plus(amountThatOverflowsBelowIntegerMinimum));
    }
}
