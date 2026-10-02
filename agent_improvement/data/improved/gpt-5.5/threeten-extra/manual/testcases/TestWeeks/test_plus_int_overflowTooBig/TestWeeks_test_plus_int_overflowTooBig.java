package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_plus_int_overflowTooBig {

    @Test
    public void test_plus_int_overflowTooBig() {
        Weeks weeksJustBelowMaximum = Weeks.of(Integer.MAX_VALUE - 1);

        assertThrows(ArithmeticException.class, () -> weeksJustBelowMaximum.plus(2));
    }
}
