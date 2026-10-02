package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestJulianChronology_test_lengthOfMonth {

    public static Object[][] data_lengthOfMonth() {
        return new Object[][] {
            // All months for year 1900, which is a Julian leap year (divisible by 4).
            // Note: 1900 is NOT a Gregorian leap year, illustrating the Julian rule difference.
            { 1900,  1, 31 },  // January
            { 1900,  2, 29 },  // February - 29 days because 1900 is a Julian leap year
            { 1900,  3, 31 },  // March
            { 1900,  4, 30 },  // April
            { 1900,  5, 31 },  // May
            { 1900,  6, 30 },  // June
            { 1900,  7, 31 },  // July
            { 1900,  8, 31 },  // August
            { 1900,  9, 30 },  // September
            { 1900, 10, 31 },  // October
            { 1900, 11, 30 },  // November
            { 1900, 12, 31 },  // December

            // February in common (non-leap) years has 28 days
            { 1901,  2, 28 },
            { 1902,  2, 28 },
            { 1903,  2, 28 },

            // February in Julian leap years (every year divisible by 4) has 29 days
            { 1904,  2, 29 },  // regular Julian leap year
            { 2000,  2, 29 },  // also a Gregorian leap year
            { 2100,  2, 29 },  // Julian leap year (but NOT a Gregorian leap year)
        };
    }

    @ParameterizedTest
    @MethodSource("data_lengthOfMonth")
    public void test_lengthOfMonth(int year, int month, int length) {
        assertEquals(length, JulianDate.of(year, month, 1).lengthOfMonth());
    }
}
