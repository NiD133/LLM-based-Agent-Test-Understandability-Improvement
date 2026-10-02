package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link PaxDate#of(int, int, int)} rejects invalid
 * year/month/day-of-month combinations by throwing a {@link DateTimeException}.
 */
@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_badDates {

    /**
     * Provides {year, month, dayOfMonth} triples that do not form a valid Pax date.
     * <p>
     * The Pax calendar has 13 months (14 in a leap year), each with 28 days,
     * except the inserted leap month 'Pax' which has only 7 days. 1900 is a leap
     * year and 1898 is not, so their valid ranges differ.
     */
    public static Object[][] data_badDates() {
        return new Object[][] {
            // Month and day both out of range.
            { 1900, 0, 0 },

            // Month out of the 1..14 range.
            { 1900, -1, 1 },
            { 1900, 0, 1 },
            { 1900, 15, 1 },
            { 1900, 16, 1 },

            // Day-of-month out of range for a normal 28-day month.
            { 1900, 1, -1 },
            { 1900, 1, 0 },
            { 1900, 1, 29 },

            // Day-of-month out of range for month 13 (7-day leap month in 1900).
            { 1900, 13, -1 },
            { 1900, 13, 0 },
            { 1900, 13, 8 },

            // Day-of-month out of range for month 14 in leap year 1900.
            { 1900, 14, -1 },
            { 1900, 14, 0 },
            { 1900, 14, 29 },
            { 1900, 14, 30 },

            // Non-leap year 1898 has no 14th month, and month 13 is a full 28-day month.
            { 1898, 13, -1 },
            { 1898, 13, 0 },
            { 1898, 14, 29 },
            { 1898, 14, 30 },
            { 1898, 14, 1 },
            { 1898, 14, 2 },

            // Repeat of the leap-year month-14 out-of-range cases for 1900.
            { 1900, 14, -1 },
            { 1900, 14, 0 },
            { 1900, 14, 29 },

            // Day 29 is invalid for every standard month (they only have 28 days).
            { 1900, 2, 29 },
            { 1900, 3, 29 },
            { 1900, 4, 29 },
            { 1900, 5, 29 },
            { 1900, 6, 29 },
            { 1900, 7, 29 },
            { 1900, 8, 29 },
            { 1900, 9, 29 },
            { 1900, 10, 29 },
            { 1900, 11, 29 },
            { 1900, 12, 29 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_badDates")
    public void test_badDates(int year, int month, int dom) {
        assertThrows(DateTimeException.class, () -> PaxDate.of(year, month, dom));
    }
}
