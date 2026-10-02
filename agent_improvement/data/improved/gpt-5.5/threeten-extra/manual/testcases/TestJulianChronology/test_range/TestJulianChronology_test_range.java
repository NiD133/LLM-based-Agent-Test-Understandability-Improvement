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

    public static Object[][] data_ranges() {
        return new Object[][] {
                // Day-of-month ranges for each month in a Julian leap year.
                { 2012, 1, 23, DAY_OF_MONTH, 1, 31 },
                { 2012, 2, 23, DAY_OF_MONTH, 1, 29 },
                { 2012, 3, 23, DAY_OF_MONTH, 1, 31 },
                { 2012, 4, 23, DAY_OF_MONTH, 1, 30 },
                { 2012, 5, 23, DAY_OF_MONTH, 1, 31 },
                { 2012, 6, 23, DAY_OF_MONTH, 1, 30 },
                { 2012, 7, 23, DAY_OF_MONTH, 1, 31 },
                { 2012, 8, 23, DAY_OF_MONTH, 1, 31 },
                { 2012, 9, 23, DAY_OF_MONTH, 1, 30 },
                { 2012, 10, 23, DAY_OF_MONTH, 1, 31 },
                { 2012, 11, 23, DAY_OF_MONTH, 1, 30 },
                { 2012, 12, 23, DAY_OF_MONTH, 1, 31 },

                // Leap-year and aligned-week ranges.
                { 2012, 1, 23, DAY_OF_YEAR, 1, 366 },
                { 2012, 1, 23, ALIGNED_WEEK_OF_MONTH, 1, 5 },
                { 2012, 2, 23, ALIGNED_WEEK_OF_MONTH, 1, 5 },
                { 2012, 3, 23, ALIGNED_WEEK_OF_MONTH, 1, 5 },

                // Common-year February ranges.
                { 2011, 2, 23, DAY_OF_MONTH, 1, 28 },
                { 2011, 2, 23, DAY_OF_YEAR, 1, 365 },
                { 2011, 2, 23, ALIGNED_WEEK_OF_MONTH, 1, 4 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_ranges")
    public void test_range(
            int year,
            int month,
            int dayOfMonth,
            TemporalField field,
            int expectedMinimum,
            int expectedMaximum) {

        assertEquals(
                ValueRange.of(expectedMinimum, expectedMaximum),
                JulianDate.of(year, month, dayOfMonth).range(field));
    }
}
