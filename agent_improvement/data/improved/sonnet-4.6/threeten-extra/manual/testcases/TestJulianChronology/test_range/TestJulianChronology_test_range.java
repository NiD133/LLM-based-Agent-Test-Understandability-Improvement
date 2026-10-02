package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestJulianChronology_test_range {

    /**
     * Provides (year, month, dayOfMonth, field, expectedMin, expectedMax) tuples
     * covering:
     *  - DAY_OF_MONTH ranges for each calendar month in a leap year (2012)
     *  - DAY_OF_YEAR range for a leap year vs. a common year
     *  - ALIGNED_WEEK_OF_MONTH ranges for months with different lengths
     */
    public static Object[][] data_ranges() {
        return new Object[][] {
            // DAY_OF_MONTH: 31-day months in leap year 2012
            { 2012,  1, 23, DAY_OF_MONTH, 1, 31 },
            { 2012,  3, 23, DAY_OF_MONTH, 1, 31 },
            { 2012,  5, 23, DAY_OF_MONTH, 1, 31 },
            { 2012,  7, 23, DAY_OF_MONTH, 1, 31 },
            { 2012,  8, 23, DAY_OF_MONTH, 1, 31 },
            { 2012, 10, 23, DAY_OF_MONTH, 1, 31 },
            { 2012, 12, 23, DAY_OF_MONTH, 1, 31 },

            // DAY_OF_MONTH: 30-day months in leap year 2012
            { 2012,  4, 23, DAY_OF_MONTH, 1, 30 },
            { 2012,  6, 23, DAY_OF_MONTH, 1, 30 },
            { 2012,  9, 23, DAY_OF_MONTH, 1, 30 },
            { 2012, 11, 23, DAY_OF_MONTH, 1, 30 },

            // DAY_OF_MONTH: February in leap year (29 days)
            { 2012,  2, 23, DAY_OF_MONTH, 1, 29 },

            // DAY_OF_MONTH: February in common year (28 days)
            { 2011,  2, 23, DAY_OF_MONTH, 1, 28 },

            // DAY_OF_YEAR: 366 days in Julian leap year 2012
            { 2012,  1, 23, DAY_OF_YEAR, 1, 366 },

            // DAY_OF_YEAR: 365 days in Julian common year 2011
            { 2011,  2, 23, DAY_OF_YEAR, 1, 365 },

            // ALIGNED_WEEK_OF_MONTH: 5-week months in 2012
            { 2012,  1, 23, ALIGNED_WEEK_OF_MONTH, 1, 5 },
            { 2012,  2, 23, ALIGNED_WEEK_OF_MONTH, 1, 5 },
            { 2012,  3, 23, ALIGNED_WEEK_OF_MONTH, 1, 5 },

            // ALIGNED_WEEK_OF_MONTH: 4-week month (February in common year 2011)
            { 2011,  2, 23, ALIGNED_WEEK_OF_MONTH, 1, 4 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_ranges")
    public void test_range(int year, int month, int dom, TemporalField field, int expectedMin, int expectedMax) {
        assertEquals(ValueRange.of(expectedMin, expectedMax), JulianDate.of(year, month, dom).range(field));
    }
}
