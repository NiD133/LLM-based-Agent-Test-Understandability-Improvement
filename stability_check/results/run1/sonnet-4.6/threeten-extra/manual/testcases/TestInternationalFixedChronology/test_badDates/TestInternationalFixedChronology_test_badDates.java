package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_badDates {

    /**
     * Provides (year, month, day) triples that must all be rejected by
     * {@link InternationalFixedDate#of(int, int, int)} with a {@link DateTimeException}.
     *
     * Groups:
     *  - invalid years (zero, negative)
     *  - invalid months (out of [1, 13] range)
     *  - combined invalid month and day
     *  - day-of-month out of range for standard months (max is 28)
     *  - day 29 in month 6 of a non-leap year (1900)
     *  - day 30 in month 13 (max is 29)
     */
    public static Object[][] data_badDates() {
        return new Object[][] {
            // Invalid year: year must be >= 1
            { -1, 13, 28 },
            { -1, 13, 29 },
            {  0,  1,  1 },

            // Invalid month: must be in [1, 13]
            { 1900, -2,  1 },
            { 1900, 14,  1 },
            { 1900, 15,  1 },

            // Invalid month combined with invalid day
            { 1904, -1, -2 },
            { 1904, -1,  0 },
            { 1904, -1,  1 },
            { 1900, -1,  0 },
            { 1900, -1, -2 },
            { 1900,  0, -1 },
            { 1900,  0,  1 },
            { 1900,  0,  2 },

            // Invalid day-of-month: day must be >= 1
            { 1900, 1, -1 },
            { 1900, 1,  0 },

            // Day 29 is only valid in month 6 (leap year) and month 13; all other months cap at 28
            { 1900,  1, 29 },
            { 1900,  2, 29 },
            { 1900,  3, 29 },
            { 1900,  4, 29 },
            { 1900,  5, 29 },
            { 1900,  6, 29 },  // month 6, day 29 is only valid in a leap year; 1900 is not a leap year
            { 1900,  7, 29 },
            { 1900,  8, 29 },
            { 1900,  9, 29 },
            { 1900, 10, 29 },
            { 1900, 11, 29 },
            { 1900, 12, 29 },

            // Day 30 is never valid; month 13 allows at most 29 days (the Year Day)
            { 1900, 13, 30 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_badDates")
    public void test_badDates(int year, int month, int dom) {
        assertThrows(DateTimeException.class, () -> InternationalFixedDate.of(year, month, dom));
    }
}
