package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMonths_test_plus_int_overflowTooBig {

    @Test
    public void test_plus_int_overflowTooBig() {
        int nearMaximumMonthCount = Integer.MAX_VALUE - 1;
        int monthsToAdd = 2;

        assertThrows(ArithmeticException.class, () -> Months.of(nearMaximumMonthCount).plus(monthsToAdd));
    }
}
