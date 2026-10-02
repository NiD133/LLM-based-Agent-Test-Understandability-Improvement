package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_badDates {

    /**
     * Provides invalid (year, month, day) combinations for the International Fixed calendar.
     * The calendar requires: year >= 1, month in [1, 13], day in [1, 28] for most months,
     * day in [1, 29] only for month 6 in leap years or month 13 (Year Day).
     */
    public static Object[][] data_badDates() {
        return new Object[][] {
            // Year before the epoch (year must be >= 1)
            { -1, 13, 28 },
            { -1, 13, 29 },
            { 0, 1, 1 },

            // Month out of valid range [1, 13]
            { 1900, -2, 1 },
            { 1900, 14, 1 },
            { 1900, 15, 1 },

            // Day-of-month below minimum (must be >= 1)
            { 1900, 1, -1 },
            { 1900, 1, 0 },

            // Day 29 in a regular month of a non-leap year (only month 13 can have day 29)
            { 1900, 1, 29 },

            // Negative month combined with invalid days (leap year 1904)
            { 1904, -1, -2 },
            { 1904, -1, 0 },
            { 1904, -1, 1 },

            // Negative month combined with invalid days (non-leap year 1900)
            { 1900, -1, 0 },
            { 1900, -1, -2 },

            // Month 0 is not valid (months are 1-13)
            { 1900, 0, -1 },
            { 1900, 0, 1 },
            { 1900, 0, 2 },

            // Day 29 in months 2-12 of a non-leap year (these months only have 28 days)
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

            // Day 30 in month 13 (Year Day is day 29, the maximum)
            { 1900, 13, 30 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_badDates")
    public void test_badDates(int year, int month, int dom) {
        assertThrows(DateTimeException.class, () -> InternationalFixedDate.of(year, month, dom));
    }
}
