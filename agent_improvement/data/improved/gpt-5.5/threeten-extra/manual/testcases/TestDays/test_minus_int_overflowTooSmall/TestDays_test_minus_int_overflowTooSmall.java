package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestDays_test_minus_int_overflowTooSmall {

    private static final int DAYS_JUST_ABOVE_INTEGER_MIN_VALUE = Integer.MIN_VALUE + 1;
    private static final int DAYS_TO_SUBTRACT = 2;

    @Test
    public void test_minus_int_overflowTooSmall() {
        assertThrows(
                ArithmeticException.class,
                () -> Days.of(DAYS_JUST_ABOVE_INTEGER_MIN_VALUE).minus(DAYS_TO_SUBTRACT));
    }
}
