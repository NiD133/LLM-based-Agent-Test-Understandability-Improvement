package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestBritishCutoverChronology_test_lengthOfMonth {

    public static Object[][] data_lengthOfMonth() {
        return new Object[][] {
            // Julian calendar rules before the British cutover.
            { 1700, 1, 31 },
            { 1700, 2, 29 },
            { 1700, 3, 31 },
            { 1700, 4, 30 },
            { 1700, 5, 31 },
            { 1700, 6, 30 },
            { 1700, 7, 31 },
            { 1700, 8, 31 },
            { 1700, 9, 30 },
            { 1700, 10, 31 },
            { 1700, 11, 30 },
            { 1700, 12, 31 },

            // The year immediately before the September 1752 cutover.
            { 1751, 1, 31 },
            { 1751, 2, 28 },
            { 1751, 3, 31 },
            { 1751, 4, 30 },
            { 1751, 5, 31 },
            { 1751, 6, 30 },
            { 1751, 7, 31 },
            { 1751, 8, 31 },
            { 1751, 9, 30 },
            { 1751, 10, 31 },
            { 1751, 11, 30 },
            { 1751, 12, 31 },

            // September 1752 skipped eleven calendar dates, so its real length is 19 days.
            { 1752, 1, 31 },
            { 1752, 2, 29 },
            { 1752, 3, 31 },
            { 1752, 4, 30 },
            { 1752, 5, 31 },
            { 1752, 6, 30 },
            { 1752, 7, 31 },
            { 1752, 8, 31 },
            { 1752, 9, 19 },
            { 1752, 10, 31 },
            { 1752, 11, 30 },
            { 1752, 12, 31 },

            // Gregorian calendar rules after the cutover.
            { 1753, 1, 31 },
            { 1753, 3, 31 },
            { 1753, 2, 28 },
            { 1753, 4, 30 },
            { 1753, 5, 31 },
            { 1753, 6, 30 },
            { 1753, 7, 31 },
            { 1753, 8, 31 },
            { 1753, 9, 30 },
            { 1753, 10, 31 },
            { 1753, 11, 30 },
            { 1753, 12, 31 },

            // February leap-year behavior across Julian and Gregorian rules.
            { 1500, 2, 29 },
            { 1600, 2, 29 },
            { 1700, 2, 29 },
            { 1800, 2, 28 },
            { 1900, 2, 28 },
            { 1901, 2, 28 },
            { 1902, 2, 28 },
            { 1903, 2, 28 },
            { 1904, 2, 29 },
            { 2000, 2, 29 },
            { 2100, 2, 28 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_lengthOfMonth")
    public void test_lengthOfMonth(int year, int month, int length) {
        assertEquals(length, BritishCutoverDate.of(year, month, 1).lengthOfMonth());
    }
}
