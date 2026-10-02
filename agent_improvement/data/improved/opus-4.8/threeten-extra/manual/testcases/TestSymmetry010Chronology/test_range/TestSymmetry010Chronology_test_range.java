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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link Symmetry010Date#range(TemporalField)}, i.e. the valid value range
 * that a given temporal field can take for a specific Symmetry010 date.
 * <p>
 * In the Symmetry010 calendar, 2012 is a common (364-day) year and 2015 is a leap
 * (371-day) year whose December holds an extra "leap week", which is why the leap-year
 * cases below report wider day/week ranges.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_range {

    /**
     * Each case is: year, month, day-of-month, the field being queried, and the
     * expected range of valid values for that field on that date.
     */
    public static Object[][] data_ranges() {
        return new Object[][] {
            // DAY_OF_MONTH: 30-day months, 31-day middle months, and the 37-day December of a leap year.
            { 2012, 1, 23, DAY_OF_MONTH, ValueRange.of(1, 30) },
            { 2012, 2, 23, DAY_OF_MONTH, ValueRange.of(1, 31) },
            { 2012, 3, 23, DAY_OF_MONTH, ValueRange.of(1, 30) },
            { 2012, 4, 23, DAY_OF_MONTH, ValueRange.of(1, 30) },
            { 2012, 5, 23, DAY_OF_MONTH, ValueRange.of(1, 31) },
            { 2012, 6, 23, DAY_OF_MONTH, ValueRange.of(1, 30) },
            { 2012, 7, 23, DAY_OF_MONTH, ValueRange.of(1, 30) },
            { 2012, 8, 23, DAY_OF_MONTH, ValueRange.of(1, 31) },
            { 2012, 9, 23, DAY_OF_MONTH, ValueRange.of(1, 30) },
            { 2012, 10, 23, DAY_OF_MONTH, ValueRange.of(1, 30) },
            { 2012, 11, 23, DAY_OF_MONTH, ValueRange.of(1, 31) },
            { 2012, 12, 23, DAY_OF_MONTH, ValueRange.of(1, 30) },
            { 2015, 12, 23, DAY_OF_MONTH, ValueRange.of(1, 37) }, // leap-year December

            // DAY_OF_WEEK: always 1..7.
            { 2012, 1, 23, DAY_OF_WEEK, ValueRange.of(1, 7) },
            { 2012, 6, 23, DAY_OF_WEEK, ValueRange.of(1, 7) },
            { 2012, 12, 23, DAY_OF_WEEK, ValueRange.of(1, 7) },

            // DAY_OF_YEAR: 364 days in a common year, 371 in a leap year.
            { 2012, 1, 23, DAY_OF_YEAR, ValueRange.of(1, 364) },
            { 2015, 1, 23, DAY_OF_YEAR, ValueRange.of(1, 371) },

            // MONTH_OF_YEAR: always 1..12.
            { 2012, 1, 23, MONTH_OF_YEAR, ValueRange.of(1, 12) },

            // ALIGNED_DAY_OF_WEEK_IN_MONTH: always 1..7.
            { 2012, 1, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7) },
            { 2012, 6, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7) },
            { 2012, 12, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7) },

            // ALIGNED_WEEK_OF_MONTH: 4 weeks normally, 5 in the leap-year December.
            { 2012, 1, 23, ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 4) },
            { 2012, 2, 23, ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 4) },
            { 2015, 12, 23, ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 5) },

            // ALIGNED_DAY_OF_WEEK_IN_YEAR: always 1..7.
            { 2012, 1, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7) },
            { 2012, 6, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7) },
            { 2012, 12, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7) },

            // ALIGNED_WEEK_OF_YEAR: 52 weeks normally, 53 with the leap week.
            { 2012, 1, 23, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52) },
            { 2012, 6, 23, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52) },
            { 2012, 12, 23, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52) },
            { 2015, 12, 30, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 53) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_ranges")
    public void test_range(int year, int month, int dom, TemporalField field, ValueRange range) {
        assertEquals(range, Symmetry010Date.of(year, month, dom).range(field));
    }
}
