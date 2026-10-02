package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_YEAR;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_range {

    public static Object[][] data_ranges() {
        return new Object[][] {
                // Day of month follows the Symmetry454 4-5-4 month pattern.
                { 2012, 1, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
                { 2012, 2, 23, DAY_OF_MONTH, ValueRange.of(1, 35) },
                { 2012, 3, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
                { 2012, 4, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
                { 2012, 5, 23, DAY_OF_MONTH, ValueRange.of(1, 35) },
                { 2012, 6, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
                { 2012, 7, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
                { 2012, 8, 23, DAY_OF_MONTH, ValueRange.of(1, 35) },
                { 2012, 9, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
                { 2012, 10, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
                { 2012, 11, 23, DAY_OF_MONTH, ValueRange.of(1, 35) },
                { 2012, 12, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
                { 2015, 12, 23, DAY_OF_MONTH, ValueRange.of(1, 35) },

                // Weekday ranges are fixed for every month and year.
                { 2012, 1, 23, DAY_OF_WEEK, ValueRange.of(1, 7) },
                { 2012, 6, 23, DAY_OF_WEEK, ValueRange.of(1, 7) },
                { 2012, 12, 23, DAY_OF_WEEK, ValueRange.of(1, 7) },

                // Leap years add a final week to the year.
                { 2012, 1, 23, DAY_OF_YEAR, ValueRange.of(1, 364) },
                { 2015, 1, 23, DAY_OF_YEAR, ValueRange.of(1, 371) },

                // Month numbering is stable across normal and leap years.
                { 2012, 1, 23, MONTH_OF_YEAR, ValueRange.of(1, 12) },

                // Aligned day-of-week ranges are fixed within months.
                { 2012, 1, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7) },
                { 2012, 6, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7) },
                { 2012, 12, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7) },

                // Aligned week-of-month follows the same 4-5-4 month pattern.
                { 2012, 1, 23, ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 4) },
                { 2012, 2, 23, ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 5) },
                { 2015, 12, 23, ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 5) },

                // Aligned day-of-week ranges are fixed within years.
                { 2012, 1, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7) },
                { 2012, 6, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7) },
                { 2012, 12, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7) },

                // Leap years add a 53rd aligned week to the year.
                { 2012, 1, 23, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52) },
                { 2012, 6, 23, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52) },
                { 2012, 12, 23, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52) },
                { 2015, 12, 30, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 53) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_ranges")
    public void test_range(
            int year,
            int month,
            int dayOfMonth,
            TemporalField field,
            ValueRange expectedRange) {

        assertEquals(expectedRange, Symmetry454Date.of(year, month, dayOfMonth).range(field));
    }
}
