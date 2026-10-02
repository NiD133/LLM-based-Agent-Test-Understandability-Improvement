package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_abs_overflow {

    private static final int MINIMUM_WEEK_COUNT = Integer.MIN_VALUE;

    @Test
    public void test_abs_overflow() {
        assertThrows(
                ArithmeticException.class,
                () -> Weeks.of(MINIMUM_WEEK_COUNT).abs());
    }
}
