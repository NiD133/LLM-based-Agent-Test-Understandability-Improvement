package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_multipliedBy_overflowTooSmall {

    private static final int MULTIPLIER = 2;
    private static final int VALUE_TOO_SMALL_TO_DOUBLE = Integer.MIN_VALUE / MULTIPLIER - 1;

    @Test
    public void test_multipliedBy_overflowTooSmall() {
        assertThrows(
                ArithmeticException.class,
                () -> Minutes.of(VALUE_TOO_SMALL_TO_DOUBLE).multipliedBy(MULTIPLIER));
    }
}
