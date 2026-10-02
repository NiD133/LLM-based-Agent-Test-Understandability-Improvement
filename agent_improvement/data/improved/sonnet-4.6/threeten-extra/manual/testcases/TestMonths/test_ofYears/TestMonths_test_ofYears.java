package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMonths_test_ofYears {

    private static final int MONTHS_PER_YEAR = 12;
    private static final int MAX_YEARS_WITHOUT_OVERFLOW = Integer.MAX_VALUE / MONTHS_PER_YEAR;
    private static final int MIN_YEARS_WITHOUT_OVERFLOW = Integer.MIN_VALUE / MONTHS_PER_YEAR;

    @Test
    public void test_ofYears() {
        // Zero years
        assertEquals(0, Months.ofYears(0).getAmount());

        // Positive years
        assertEquals(12, Months.ofYears(1).getAmount());
        assertEquals(24, Months.ofYears(2).getAmount());
        assertEquals(MAX_YEARS_WITHOUT_OVERFLOW * MONTHS_PER_YEAR,
                Months.ofYears(MAX_YEARS_WITHOUT_OVERFLOW).getAmount());

        // Negative years
        assertEquals(-12, Months.ofYears(-1).getAmount());
        assertEquals(-24, Months.ofYears(-2).getAmount());
        assertEquals(MIN_YEARS_WITHOUT_OVERFLOW * MONTHS_PER_YEAR,
                Months.ofYears(MIN_YEARS_WITHOUT_OVERFLOW).getAmount());
    }
}
