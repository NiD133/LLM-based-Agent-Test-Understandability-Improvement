package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link BritishCutoverDate#of(int, int, int)} rejects out-of-range
 * year/month/day combinations by throwing a {@link DateTimeException}.
 */
public class TestBritishCutoverChronology_test_badDates {

    /**
     * Each row is a {year, month, dayOfMonth} triple that does not denote a valid date.
     * Cases cover invalid months, invalid days, and days that exceed the actual length
     * of the given month (including the February leap-year distinction between 1899 and 1900).
     */
    public static Object[][] data_badDates() {
        return new Object[][] {
            // invalid month values (0, negative, or beyond December)
            { 1900, 0, 0 },
            { 1900, -1, 1 },
            { 1900, 0, 1 },
            { 1900, 13, 1 },
            { 1900, 14, 1 },

            // invalid day-of-month for January
            { 1900, 1, -1 },
            { 1900, 1, 0 },
            { 1900, 1, 32 },

            // February 1900 (not a leap year): valid days are 1..28
            { 1900, 2, -1 },
            { 1900, 2, 0 },
            { 1900, 2, 30 },
            { 1900, 2, 31 },
            { 1900, 2, 32 },

            // February 1899 (not a leap year): valid days are 1..28
            { 1899, 2, -1 },
            { 1899, 2, 0 },
            { 1899, 2, 29 },
            { 1899, 2, 30 },
            { 1899, 2, 31 },
            { 1899, 2, 32 },

            // invalid day-of-month for December
            { 1900, 12, -1 },
            { 1900, 12, 0 },
            { 1900, 12, 32 },

            // day-of-month exceeding the length of each remaining month
            { 1900, 3, 32 },
            { 1900, 4, 31 },
            { 1900, 5, 32 },
            { 1900, 6, 31 },
            { 1900, 7, 32 },
            { 1900, 8, 32 },
            { 1900, 9, 31 },
            { 1900, 10, 32 },
            { 1900, 11, 31 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_badDates")
    public void test_badDates(int year, int month, int dom) {
        assertThrows(DateTimeException.class, () -> BritishCutoverDate.of(year, month, dom));
    }
}
