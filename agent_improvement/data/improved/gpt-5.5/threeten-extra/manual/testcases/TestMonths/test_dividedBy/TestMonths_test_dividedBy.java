package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMonths_test_dividedBy {

    @Test
    public void test_dividedBy() {
        Months twelveMonths = Months.of(12);

        assertDividedBy(twelveMonths, 1, Months.of(12));
        assertDividedBy(twelveMonths, 2, Months.of(6));
        assertDividedBy(twelveMonths, 3, Months.of(4));
        assertDividedBy(twelveMonths, 4, Months.of(3));
        assertDividedBy(twelveMonths, 5, Months.of(2));
        assertDividedBy(twelveMonths, 6, Months.of(2));
        assertDividedBy(twelveMonths, -3, Months.of(-4));
    }

    private static void assertDividedBy(Months months, int divisor, Months expected) {
        assertEquals(expected, months.dividedBy(divisor));
    }
}
