package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_lengthOfMonth {

    /**
     * Test data: (year, month, day, expectedLengthOfMonth).
     *
     * In the IFC, regular months have 28 days. Month 13 (Year Day) always has 29 days.
     * Month 6 has 29 days only in a leap year (e.g. 1904); in non-leap years (e.g. 1900) it has 28.
     */
    public static Object[][] data_lengthOfMonth() {
        return new Object[][] {
            // Non-leap year 1900: months 1-12 have 28 days each
            { 1900,  1, 28, 28 },
            { 1900,  2, 28, 28 },
            { 1900,  3, 28, 28 },
            { 1900,  4, 28, 28 },
            { 1900,  5, 28, 28 },
            { 1900,  6, 28, 28 },
            { 1900,  7, 28, 28 },
            { 1900,  8, 28, 28 },
            { 1900,  9, 28, 28 },
            { 1900, 10, 28, 28 },
            { 1900, 11, 28, 28 },
            { 1900, 12, 28, 28 },
            // Month 13 (Year Day) is always 29 days long
            { 1900, 13, 29, 29 },
            // Leap year 1904: month 6 gains Leap Day, becoming 29 days long
            { 1904,  6, 29, 29 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_lengthOfMonth")
    public void test_lengthOfMonth(int year, int month, int day, int length) {
        assertEquals(length, InternationalFixedDate.of(year, month, day).lengthOfMonth());
    }
}
