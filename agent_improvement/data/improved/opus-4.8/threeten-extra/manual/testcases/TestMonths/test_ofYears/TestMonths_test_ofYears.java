package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Months#ofYears(int)}, which converts a number of years into the
 * equivalent number of months (years * 12).
 */
public class TestMonths_test_ofYears {

    /** The number of months in a single year, used to compute expected values. */
    private static final int MONTHS_PER_YEAR = 12;

    @Test
    public void ofYears_convertsYearsToMonths() {
        // Zero years yields zero months.
        assertEquals(0, Months.ofYears(0).getAmount());

        // Positive years are multiplied by 12.
        assertEquals(12, Months.ofYears(1).getAmount());
        assertEquals(24, Months.ofYears(2).getAmount());

        // Negative years are multiplied by 12, preserving the sign.
        assertEquals(-12, Months.ofYears(-1).getAmount());
        assertEquals(-24, Months.ofYears(-2).getAmount());

        // The largest positive number of years that does not overflow an int
        // when multiplied by 12.
        int maxYears = Integer.MAX_VALUE / MONTHS_PER_YEAR;
        assertEquals(maxYears * MONTHS_PER_YEAR, Months.ofYears(maxYears).getAmount());

        // The largest-magnitude negative number of years that does not overflow.
        int minYears = Integer.MIN_VALUE / MONTHS_PER_YEAR;
        assertEquals(minYears * MONTHS_PER_YEAR, Months.ofYears(minYears).getAmount());
    }
}
