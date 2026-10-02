package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMonths_test_ofYears_overflow {

    @Test
    public void test_ofYears_overflow() {
        // Any year value whose product with 12 exceeds Integer.MAX_VALUE triggers ArithmeticException.
        // Integer.MAX_VALUE / 12 gives the largest year that fits; adding 12 more guarantees overflow.
        int yearsThatOverflow = (Integer.MAX_VALUE / 12) + 12;
        assertThrows(ArithmeticException.class, () -> Months.ofYears(yearsThatOverflow));
    }
}
