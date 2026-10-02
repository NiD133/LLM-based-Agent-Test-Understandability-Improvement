package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_minus_TemporalAmount_overflowTooSmall {

    private static final int ONE_WEEK_ABOVE_MINIMUM = Integer.MIN_VALUE + 1;
    private static final int WEEKS_TO_SUBTRACT = 2;

    @Test
    public void test_minus_TemporalAmount_overflowTooSmall() {
        assertThrows(
                ArithmeticException.class,
                () -> Weeks.of(ONE_WEEK_ABOVE_MINIMUM).minus(Weeks.of(WEEKS_TO_SUBTRACT)));
    }
}
