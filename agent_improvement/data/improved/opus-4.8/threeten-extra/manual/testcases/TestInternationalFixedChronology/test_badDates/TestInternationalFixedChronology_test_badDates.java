package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link InternationalFixedDate#of(int, int, int)} rejects
 * year/month/day combinations that do not denote a valid date.
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_badDates {

    /**
     * Each row is a {year, month, dayOfMonth} triple that must NOT be accepted
     * as a valid International Fixed calendar date. The calendar has 13 months;
     * every ordinary month has 28 days, month 6 of a leap year and month 13
     * each have a 29th day, and proleptic years must be positive.
     */
    public static Object[][] data_badDates() {
        return new Object[][] {
            // Non-positive proleptic year.
            { -1, 13, 28 },
            { -1, 13, 29 },
            { 0, 1, 1 },

            // Month outside the valid 1..13 range.
            { 1900, -2, 1 },
            { 1900, 14, 1 },
            { 1900, 15, 1 },
            { 1904, -1, -2 },
            { 1904, -1, 0 },
            { 1904, -1, 1 },
            { 1900, -1, 0 },
            { 1900, -1, -2 },
            { 1900, 0, -1 },
            { 1900, 0, 1 },
            { 1900, 0, 2 },

            // Day-of-month outside the valid range for its month.
            { 1900, 1, -1 },
            { 1900, 1, 0 },
            // Day 29 is invalid for an ordinary 28-day month (1900 is not a leap year,
            // so month 6 has no 29th day either).
            { 1900, 1, 29 },
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
            // Month 13 (Year Day) tops out at day 29.
            { 1900, 13, 30 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_badDates")
    public void test_badDates(int year, int month, int dom) {
        assertThrows(DateTimeException.class, () -> InternationalFixedDate.of(year, month, dom));
    }
}
