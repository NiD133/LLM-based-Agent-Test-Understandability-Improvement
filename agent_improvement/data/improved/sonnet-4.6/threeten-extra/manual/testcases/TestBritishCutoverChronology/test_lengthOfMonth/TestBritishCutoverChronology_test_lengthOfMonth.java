package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestBritishCutoverChronology_test_lengthOfMonth {

    /**
     * Test data: {year, month, expectedLengthOfMonth}
     *
     * Covers several distinct scenarios:
     *  - Pre-cutover Julian year (1700): February has 29 days because Julian rules treat
     *    century years as leap years.
     *  - Pre-cutover non-leap year (1751): normal month lengths.
     *  - Cutover year (1752): September has only 19 days because days 3–13 were skipped.
     *  - Post-cutover year (1753): Gregorian rules apply.
     *  - Leap-year boundary cases across Julian/Gregorian transition: 1700 is a Julian
     *    leap year but 1800/1900/2100 are not Gregorian leap years; 1600/2000 are.
     */
    public static Object[][] data_lengthOfMonth() {
        return new Object[][] {
            // --- Year 1700: Julian leap year (century years ARE leap under Julian rules) ---
            { 1700,  1, 31 },
            { 1700,  2, 29 },
            { 1700,  3, 31 },
            { 1700,  4, 30 },
            { 1700,  5, 31 },
            { 1700,  6, 30 },
            { 1700,  7, 31 },
            { 1700,  8, 31 },
            { 1700,  9, 30 },
            { 1700, 10, 31 },
            { 1700, 11, 30 },
            { 1700, 12, 31 },

            // --- Year 1751: pre-cutover, non-leap year ---
            { 1751,  1, 31 },
            { 1751,  2, 28 },
            { 1751,  3, 31 },
            { 1751,  4, 30 },
            { 1751,  5, 31 },
            { 1751,  6, 30 },
            { 1751,  7, 31 },
            { 1751,  8, 31 },
            { 1751,  9, 30 },
            { 1751, 10, 31 },
            { 1751, 11, 30 },
            { 1751, 12, 31 },

            // --- Year 1752: cutover year; September has only 19 days (days 3-13 were skipped) ---
            { 1752,  1, 31 },
            { 1752,  2, 29 },
            { 1752,  3, 31 },
            { 1752,  4, 30 },
            { 1752,  5, 31 },
            { 1752,  6, 30 },
            { 1752,  7, 31 },
            { 1752,  8, 31 },
            { 1752,  9, 19 },
            { 1752, 10, 31 },
            { 1752, 11, 30 },
            { 1752, 12, 31 },

            // --- Year 1753: post-cutover, Gregorian rules, non-leap year ---
            { 1753,  1, 31 },
            { 1753,  2, 28 },
            { 1753,  3, 31 },
            { 1753,  4, 30 },
            { 1753,  5, 31 },
            { 1753,  6, 30 },
            { 1753,  7, 31 },
            { 1753,  8, 31 },
            { 1753,  9, 30 },
            { 1753, 10, 31 },
            { 1753, 11, 30 },
            { 1753, 12, 31 },

            // --- February length across the Julian/Gregorian leap-year boundary ---
            // Julian rules: all years divisible by 4 are leap, including centuries
            { 1500,  2, 29 },
            { 1600,  2, 29 },
            { 1700,  2, 29 },
            // Gregorian rules (post-cutover): centuries must also be divisible by 400
            { 1800,  2, 28 },
            { 1900,  2, 28 },
            { 1901,  2, 28 },
            { 1902,  2, 28 },
            { 1903,  2, 28 },
            { 1904,  2, 29 },
            { 2000,  2, 29 },
            { 2100,  2, 28 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_lengthOfMonth")
    public void test_lengthOfMonth(int year, int month, int length) {
        assertEquals(length, BritishCutoverDate.of(year, month, 1).lengthOfMonth());
    }
}
