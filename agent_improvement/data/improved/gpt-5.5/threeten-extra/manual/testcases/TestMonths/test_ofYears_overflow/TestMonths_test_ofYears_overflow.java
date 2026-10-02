package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMonths_test_ofYears_overflow {

    @Test
    public void test_ofYears_overflow() {
        int yearsBeyondMaximumMonthCount = (Integer.MAX_VALUE / 12) + 12;

        assertThrows(ArithmeticException.class, () -> Months.ofYears(yearsBeyondMaximumMonthCount));
    }
}
