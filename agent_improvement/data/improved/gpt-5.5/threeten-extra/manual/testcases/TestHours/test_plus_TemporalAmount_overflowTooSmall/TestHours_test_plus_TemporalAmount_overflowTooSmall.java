package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestHours_test_plus_TemporalAmount_overflowTooSmall {

    private static final int ONE_ABOVE_MINIMUM_HOURS = Integer.MIN_VALUE + 1;
    private static final int HOURS_TO_SUBTRACT = -2;

    @Test
    public void test_plus_TemporalAmount_overflowTooSmall() {
        assertThrows(
                ArithmeticException.class,
                () -> Hours.of(ONE_ABOVE_MINIMUM_HOURS).plus(Hours.of(HOURS_TO_SUBTRACT)));
    }
}
