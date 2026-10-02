package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestDays_test_multipliedBy_overflowTooSmall {

    @Test
    public void test_multipliedBy_overflowTooSmall() {
        int valueBelowHalfMinInt = Integer.MIN_VALUE / 2 - 1;

        assertThrows(ArithmeticException.class,
                () -> Days.of(valueBelowHalfMinInt).multipliedBy(2));
    }
}
