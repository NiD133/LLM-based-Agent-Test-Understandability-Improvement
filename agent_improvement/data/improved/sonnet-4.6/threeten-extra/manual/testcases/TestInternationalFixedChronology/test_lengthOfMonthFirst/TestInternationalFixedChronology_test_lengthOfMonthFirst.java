package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_lengthOfMonthFirst {

    /**
     * Provides test data as {year, month, dayUnused, expectedLength}.
     * The dayUnused column is present for consistency with other length-of-month tests
     * but is not used here — the test always queries the first day of the month.
     *
     * Rules exercised:
     *  - Months 1–12 in a non-leap year have 28 days.
     *  - Month 13 (Year Day) always has 29 days (day 29 is Year Day).
     *  - Month 6 in a leap year has 29 days (day 29 is Leap Day).
     */
    public static Object[][] data_lengthOfMonth() {
        return new Object[][] {
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
            { 1900, 13, 29, 29 }, // Year Day month always has 29 days
            { 1904,  6, 29, 29 }, // Month 6 in a leap year has 29 days (Leap Day)
        };
    }

    @ParameterizedTest
    @MethodSource("data_lengthOfMonth")
    public void test_lengthOfMonthFirst(int year, int month, int dayUnused, int expectedLength) {
        assertEquals(expectedLength, InternationalFixedDate.of(year, month, 1).lengthOfMonth());
    }
}
