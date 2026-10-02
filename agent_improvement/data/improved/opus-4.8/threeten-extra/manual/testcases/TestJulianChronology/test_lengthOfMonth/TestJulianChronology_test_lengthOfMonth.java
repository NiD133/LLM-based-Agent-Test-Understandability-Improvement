package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies {@link JulianDate#lengthOfMonth()}.
 * <p>
 * In the proleptic Julian calendar every fourth year is a leap year, so February
 * has 29 days whenever the year is divisible by four (e.g. 1900, 1904, 2000, 2100)
 * and 28 days otherwise. The remaining months keep their fixed lengths.
 */
public class TestJulianChronology_test_lengthOfMonth {

    /**
     * Each case provides: year, month, and the expected number of days in that month.
     */
    public static Object[][] data_lengthOfMonth() {
        return new Object[][] {
            // Every month of 1900, a Julian leap year (1900 % 4 == 0), so February has 29 days.
            { 1900, 1, 31 },
            { 1900, 2, 29 },
            { 1900, 3, 31 },
            { 1900, 4, 30 },
            { 1900, 5, 31 },
            { 1900, 6, 30 },
            { 1900, 7, 31 },
            { 1900, 8, 31 },
            { 1900, 9, 30 },
            { 1900, 10, 31 },
            { 1900, 11, 30 },
            { 1900, 12, 31 },
            // February length across years: 28 days unless the year is divisible by four.
            { 1901, 2, 28 },
            { 1902, 2, 28 },
            { 1903, 2, 28 },
            { 1904, 2, 29 },
            { 2000, 2, 29 },
            { 2100, 2, 29 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_lengthOfMonth")
    public void test_lengthOfMonth(int year, int month, int expectedLength) {
        assertEquals(expectedLength, JulianDate.of(year, month, 1).lengthOfMonth());
    }
}
