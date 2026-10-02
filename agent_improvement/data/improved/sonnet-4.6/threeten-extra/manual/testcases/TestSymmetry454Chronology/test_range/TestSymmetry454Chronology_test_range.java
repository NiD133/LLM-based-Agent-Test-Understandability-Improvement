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

    /**
     * Test data for {@link #test_range}: each row is
     * (year, month, dayOfMonth, field, expectedRange).
     *
     * Symmetry454 month lengths:
     *   - Months 2, 5, 8, 11 are "long" months (35 days / 5 weeks).
     *   - Month 12 is also long (35 days / 5 weeks) in leap years.
     *   - All other months have 28 days / 4 weeks.
     * Normal years have 364 days (52 weeks); leap years have 371 days (53 weeks).
     */
    public static Object[][] data_ranges() {
        return new Object[][] {

            // --- DAY_OF_MONTH: each Sym454 month in a normal year (2012) ---
            { 2012,  1, 23, DAY_OF_MONTH, ValueRange.of(1, 28) }, // Jan: normal month
            { 2012,  2, 23, DAY_OF_MONTH, ValueRange.of(1, 35) }, // Feb: long month (5 weeks)
            { 2012,  3, 23, DAY_OF_MONTH, ValueRange.of(1, 28) }, // Mar: normal month
            { 2012,  4, 23, DAY_OF_MONTH, ValueRange.of(1, 28) }, // Apr: normal month
            { 2012,  5, 23, DAY_OF_MONTH, ValueRange.of(1, 35) }, // May: long month (5 weeks)
            { 2012,  6, 23, DAY_OF_MONTH, ValueRange.of(1, 28) }, // Jun: normal month
            { 2012,  7, 23, DAY_OF_MONTH, ValueRange.of(1, 28) }, // Jul: normal month
            { 2012,  8, 23, DAY_OF_MONTH, ValueRange.of(1, 35) }, // Aug: long month (5 weeks)
            { 2012,  9, 23, DAY_OF_MONTH, ValueRange.of(1, 28) }, // Sep: normal month
            { 2012, 10, 23, DAY_OF_MONTH, ValueRange.of(1, 28) }, // Oct: normal month
            { 2012, 11, 23, DAY_OF_MONTH, ValueRange.of(1, 35) }, // Nov: long month (5 weeks)
            { 2012, 12, 23, DAY_OF_MONTH, ValueRange.of(1, 28) }, // Dec: normal month in non-leap year
            { 2015, 12, 23, DAY_OF_MONTH, ValueRange.of(1, 35) }, // Dec: long month in leap year (2015)

            // --- DAY_OF_WEEK: always 1–7 regardless of month or year ---
            { 2012,  1, 23, DAY_OF_WEEK, ValueRange.of(1, 7) },
            { 2012,  6, 23, DAY_OF_WEEK, ValueRange.of(1, 7) },
            { 2012, 12, 23, DAY_OF_WEEK, ValueRange.of(1, 7) },

            // --- DAY_OF_YEAR: 364 days in a normal year, 371 in a leap year ---
            { 2012,  1, 23, DAY_OF_YEAR, ValueRange.of(1, 364) }, // 2012 is a normal year
            { 2015,  1, 23, DAY_OF_YEAR, ValueRange.of(1, 371) }, // 2015 is a leap year

            // --- MONTH_OF_YEAR: always 1–12 ---
            { 2012,  1, 23, MONTH_OF_YEAR, ValueRange.of(1, 12) },

            // --- ALIGNED_DAY_OF_WEEK_IN_MONTH: always 1–7 ---
            { 2012,  1, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7) },
            { 2012,  6, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7) },
            { 2012, 12, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7) },

            // --- ALIGNED_WEEK_OF_MONTH: 1–4 for normal months, 1–5 for long months ---
            { 2012,  1, 23, ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 4) }, // normal month
            { 2012,  2, 23, ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 5) }, // long month
            { 2015, 12, 23, ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 5) }, // Dec in leap year = long

            // --- ALIGNED_DAY_OF_WEEK_IN_YEAR: always 1–7 ---
            { 2012,  1, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7) },
            { 2012,  6, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7) },
            { 2012, 12, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7) },

            // --- ALIGNED_WEEK_OF_YEAR: 1–52 in a normal year, 1–53 in a leap year ---
            { 2012,  1, 23, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52) }, // normal year
            { 2012,  6, 23, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52) }, // normal year
            { 2012, 12, 23, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52) }, // normal year
            { 2015, 12, 30, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 53) }, // leap year (week 53)
        };
    }

    @ParameterizedTest
    @MethodSource("data_ranges")
    public void test_range(int year, int month, int dom, TemporalField field, ValueRange range) {
        assertEquals(range, Symmetry454Date.of(year, month, dom).range(field));
    }
}
