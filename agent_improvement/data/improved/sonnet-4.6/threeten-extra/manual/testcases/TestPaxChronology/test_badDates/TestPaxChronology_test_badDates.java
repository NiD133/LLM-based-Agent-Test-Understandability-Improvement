package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_badDates {

    /**
     * Provides (year, month, dayOfMonth) triples that should all be rejected by
     * {@link PaxDate#of} with a {@link DateTimeException}.
     *
     * Pax calendar rules (relevant here):
     *  - Non-leap years have 13 months of 28 days each (months 1–13).
     *  - Leap years (e.g. 1900: last two digits "00", not divisible by 400) have
     *    14 months: months 1–12 with 28 days, month 13 (the "Pax" leap-month) with
     *    7 days, and month 14 with 28 days.
     *  - 1898 is NOT a leap year (last two digits 98, not divisible by 6 or 99).
     *  - 1900 IS a leap year (last two digits 00, 1900 % 400 != 0).
     */
    public static Object[][] data_badDates() {
        return new Object[][] {
            // --- invalid month values (any year) ---
            { 1900,  0,  0 },   // month 0 is always invalid
            { 1900, -1,  1 },   // negative month
            { 1900,  0,  1 },   // month 0
            { 1900, 15,  1 },   // month 15 exceeds maximum (14 in a leap year)
            { 1900, 16,  1 },   // month 16 exceeds maximum

            // --- invalid day values in a regular month (months 1–12, 28 days max) ---
            { 1900,  1, -1 },   // negative day
            { 1900,  1,  0 },   // day 0
            { 1900,  1, 29 },   // day 29 exceeds 28-day month

            // --- invalid day values in the leap-month (month 13, 7 days max in leap year 1900) ---
            { 1900, 13, -1 },   // negative day in leap-month
            { 1900, 13,  0 },   // day 0 in leap-month
            { 1900, 13,  8 },   // day 8 exceeds 7-day leap-month

            // --- invalid day values in month 14 of leap year 1900 (28 days max) ---
            { 1900, 14, -1 },   // negative day
            { 1900, 14,  0 },   // day 0
            { 1900, 14, 29 },   // day 29 exceeds 28-day month
            { 1900, 14, 30 },   // day 30 exceeds 28-day month

            // --- month/day combinations invalid in non-leap year 1898 ---
            { 1898, 13, -1 },   // negative day in month 13 (regular last month, 28 days)
            { 1898, 13,  0 },   // day 0 in month 13
            { 1898, 14, 29 },   // day 29 in month 14 (doesn't exist in non-leap year)
            { 1898, 14, 30 },   // day 30 in month 14
            { 1898, 14,  1 },   // month 14 does not exist in a non-leap year
            { 1898, 14,  2 },   // month 14 does not exist in a non-leap year

            // --- duplicates retained from original test data (leap year 1900, month 14) ---
            { 1900, 14, -1 },
            { 1900, 14,  0 },
            { 1900, 14, 29 },

            // --- day 29 is invalid for every regular month (1–12) in leap year 1900 ---
            { 1900,  2, 29 },
            { 1900,  3, 29 },
            { 1900,  4, 29 },
            { 1900,  5, 29 },
            { 1900,  6, 29 },
            { 1900,  7, 29 },
            { 1900,  8, 29 },
            { 1900,  9, 29 },
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
