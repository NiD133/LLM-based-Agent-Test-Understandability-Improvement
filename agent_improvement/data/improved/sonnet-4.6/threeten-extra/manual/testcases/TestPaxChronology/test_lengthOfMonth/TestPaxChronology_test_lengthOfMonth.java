package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_lengthOfMonth {

    // Each row: { prolepticYear, month, expectedLengthInDays }
    // Standard months are 28 days. Month 13 (the "Pax" leap-month) is 7 days in
    // leap years and 28 days in non-leap years. Month 14 is always 28 days (leap years only).
    public static Object[][] data_lengthOfMonth() {
        return new Object[][] {
            // Non-leap year 1900 (last two digits "00", but 1900 % 400 != 0, so it IS a leap year):
            { 1900,  1, 28 },
            { 1900,  2, 28 },
            { 1900,  3, 28 },
            { 1900,  4, 28 },
            { 1900,  5, 28 },
            { 1900,  6, 28 },
            { 1900,  7, 28 },
            { 1900,  8, 28 },
            { 1900,  9, 28 },
            { 1900, 10, 28 },
            { 1900, 11, 28 },
            { 1900, 12, 28 },
            { 1900, 13,  7 }, // leap-month "Pax" — 7 days in a leap year
            { 1900, 14, 28 }, // month 14 exists only in a leap year

            // Non-leap years: month 13 has 28 days (no inserted Pax week)
            { 1901, 13, 28 },
            { 1902, 13, 28 },
            { 1903, 13, 28 },
            { 1904, 13, 28 },
            { 1905, 13, 28 },

            // 1906 is a leap year (last two digits 06, divisible by 6)
            { 1906, 13,  7 },

            // 2000 is NOT a leap year (divisible by 400)
            { 2000, 13, 28 },

            // 2100 IS a leap year (last two digits "00", but 2100 % 400 != 0)
            { 2100, 13,  7 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_lengthOfMonth")
    public void test_lengthOfMonth(int year, int month, int length) {
        assertEquals(length, PaxDate.of(year, month, 1).lengthOfMonth());
    }
}
