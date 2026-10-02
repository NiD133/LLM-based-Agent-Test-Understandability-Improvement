package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMonths_test_ofYears {

    private static final int MONTHS_PER_YEAR = 12;

    @Test
    public void test_ofYears() {
        assertMonthsFromYears(0, 0);
        assertMonthsFromYears(1, MONTHS_PER_YEAR);
        assertMonthsFromYears(2, 2 * MONTHS_PER_YEAR);
        assertMonthsFromYears(Integer.MAX_VALUE / MONTHS_PER_YEAR,
                (Integer.MAX_VALUE / MONTHS_PER_YEAR) * MONTHS_PER_YEAR);
        assertMonthsFromYears(-1, -MONTHS_PER_YEAR);
        assertMonthsFromYears(-2, -2 * MONTHS_PER_YEAR);
        assertMonthsFromYears(Integer.MIN_VALUE / MONTHS_PER_YEAR,
                (Integer.MIN_VALUE / MONTHS_PER_YEAR) * MONTHS_PER_YEAR);
    }

    private static void assertMonthsFromYears(int years, int expectedMonths) {
        assertEquals(expectedMonths, Months.ofYears(years).getAmount());
    }
}
