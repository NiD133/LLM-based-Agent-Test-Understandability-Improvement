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
public class TestSymmetry010Chronology_test_range {

    /**
     * Provides (year, month, dayOfMonth, field, expectedRange) tuples for {@link #test_range}.
     *
     * <p>The Symmetry010 calendar has 12 months per year. Months 1, 3, 4, 6, 7, 9, 10, 12 have
     * 30 days; months 2, 5, 8, 11 have 31 days. In a leap year (e.g. 2015) December gains an
     * extra week, extending it to 37 days and the year to 371 days / 53 aligned weeks.
     */
    public static Object[][] data_ranges() {
        return new Object[][] {
            // --- DAY_OF_MONTH: each month reports its own length ---
            { 2012,  1, 23, DAY_OF_MONTH, ValueRange.of(1, 30) },   // Jan: 30-day month
            { 2012,  2, 23, DAY_OF_MONTH, ValueRange.of(1, 31) },   // Feb: 31-day month
            { 2012,  3, 23, DAY_OF_MONTH, ValueRange.of(1, 30) },   // Mar: 30-day month
            { 2012,  4, 23, DAY_OF_MONTH, ValueRange.of(1, 30) },   // Apr: 30-day month
            { 2012,  5, 23, DAY_OF_MONTH, ValueRange.of(1, 31) },   // May: 31-day month
            { 2012,  6, 23, DAY_OF_MONTH, ValueRange.of(1, 30) },   // Jun: 30-day month
            { 2012,  7, 23, DAY_OF_MONTH, ValueRange.of(1, 30) },   // Jul: 30-day month
            { 2012,  8, 23, DAY_OF_MONTH, ValueRange.of(1, 31) },   // Aug: 31-day month
            { 2012,  9, 23, DAY_OF_MONTH, ValueRange.of(1, 30) },   // Sep: 30-day month
            { 2012, 10, 23, DAY_OF_MONTH, ValueRange.of(1, 30) },   // Oct: 30-day month
            { 2012, 11, 23, DAY_OF_MONTH, ValueRange.of(1, 31) },   // Nov: 31-day month
            { 2012, 12, 23, DAY_OF_MONTH, ValueRange.of(1, 30) },   // Dec: 30-day month (non-leap)
            { 2015, 12, 23, DAY_OF_MONTH, ValueRange.of(1, 37) },   // Dec in leap year 2015: 37 days

            // --- DAY_OF_WEEK: always 1–7 regardless of month ---
            { 2012,  1, 23, DAY_OF_WEEK, ValueRange.of(1, 7) },
            { 2012,  6, 23, DAY_OF_WEEK, ValueRange.of(1, 7) },
            { 2012, 12, 23, DAY_OF_WEEK, ValueRange.of(1, 7) },

            // --- DAY_OF_YEAR: 364 days in normal year, 371 in leap year ---
            { 2012,  1, 23, DAY_OF_YEAR, ValueRange.of(1, 364) },   // non-leap year
            { 2015,  1, 23, DAY_OF_YEAR, ValueRange.of(1, 371) },   // leap year 2015

            // --- MONTH_OF_YEAR: always 1–12 ---
            { 2012,  1, 23, MONTH_OF_YEAR, ValueRange.of(1, 12) },

            // --- ALIGNED_DAY_OF_WEEK_IN_MONTH: always 1–7 ---
            { 2012,  1, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7) },
            { 2012,  6, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7) },
            { 2012, 12, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7) },

            // --- ALIGNED_WEEK_OF_MONTH: 4 weeks for normal months, 5 for the leap-week month ---
            { 2012,  1, 23, ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 4) },   // 30-day month → 4 weeks
            { 2012,  2, 23, ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 4) },   // 31-day month → still 4 full + partial? no, 4 aligned weeks
            { 2015, 12, 23, ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 5) },   // Dec in leap year → 5 weeks

            // --- ALIGNED_DAY_OF_WEEK_IN_YEAR: always 1–7 ---
            { 2012,  1, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7) },
            { 2012,  6, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7) },
            { 2012, 12, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7) },

            // --- ALIGNED_WEEK_OF_YEAR: 52 in normal year, 53 in leap year ---
            { 2012,  1, 23, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52) },   // non-leap year
            { 2012,  6, 23, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52) },
            { 2012, 12, 23, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52) },
            { 2015, 12, 30, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 53) },   // leap year 2015
        };
    }

    @ParameterizedTest
    @MethodSource("data_ranges")
    public void test_range(int year, int month, int dom, TemporalField field, ValueRange range) {
        assertEquals(range, Symmetry010Date.of(year, month, dom).range(field));
    }
}
