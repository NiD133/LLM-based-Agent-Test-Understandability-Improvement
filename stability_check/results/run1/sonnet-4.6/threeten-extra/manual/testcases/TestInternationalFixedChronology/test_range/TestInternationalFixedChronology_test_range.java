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
public class TestInternationalFixedChronology_test_range {

    /**
     * Test data for range queries.
     *
     * <p>The International Fixed Calendar has two special days that stand outside the normal
     * week structure:
     * <ul>
     *   <li><b>Leap Day</b>: month 6, day 29 — present only in leap years (e.g. 2012)</li>
     *   <li><b>Year Day</b>: month 13, day 29 — present in every year</li>
     * </ul>
     *
     * These intercalary days belong to their respective months for day-of-month and
     * month-of-year purposes, but are treated as belonging to neither a week nor an
     * aligned-week group (hence their week-related ranges are [0, 0]).
     *
     * <p>Each row: { year, month, dayOfMonth, field, expectedRange }
     */
    public static Object[][] data_ranges() {
        return new Object[][] {
            // --- DAY_OF_MONTH ---
            // Leap Day (month 6, day 29 in a leap year) belongs to month 6 → range [1, 29]
            { 2012,  6, 29, DAY_OF_MONTH, ValueRange.of(1, 29) },
            // Year Day (month 13, day 29) belongs to month 13 → range [1, 29]
            { 2012, 13, 29, DAY_OF_MONTH, ValueRange.of(1, 29) },
            // Regular months have 28 days → range [1, 28]
            { 2012,  1, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012,  2, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012,  3, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012,  4, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012,  5, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            // Month 6 in a leap year has 29 days → range [1, 29]
            { 2012,  6, 23, DAY_OF_MONTH, ValueRange.of(1, 29) },
            { 2012,  7, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012,  8, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012,  9, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012, 10, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012, 11, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012, 12, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            // Month 13 always has 29 days (Year Day) → range [1, 29]
            { 2012, 13, 23, DAY_OF_MONTH, ValueRange.of(1, 29) },
            // Month 6 in a non-leap year has only 28 days → range [1, 28]
            { 2011,  6, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },

            // --- DAY_OF_YEAR ---
            // Leap Day is still within the same year, so a leap year has 366 days → [1, 366]
            { 2012,  1, 23, DAY_OF_YEAR, ValueRange.of(1, 366) },
            // Non-leap year has 365 days → [1, 365]
            { 2011, 13, 23, DAY_OF_YEAR, ValueRange.of(1, 365) },

            // --- MONTH_OF_YEAR ---
            // Always 13 months regardless of leap year
            { 2012,  1, 23, MONTH_OF_YEAR, ValueRange.of(1, 13) },
            { 2011, 13, 23, MONTH_OF_YEAR, ValueRange.of(1, 13) },

            // --- ALIGNED_DAY_OF_WEEK_IN_MONTH ---
            // Intercalary days (Leap Day, Year Day) are outside the week structure → [0, 0]
            { 2012,  6, 29, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(0, 0) },
            { 2012, 13, 29, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(0, 0) },
            // Normal days fall in a 7-day week → [1, 7]
            { 2012,  1, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7) },
            { 2012,  6, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7) },
            { 2012, 12, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7) },

            // --- ALIGNED_WEEK_OF_MONTH ---
            // Intercalary days are outside the week structure → [0, 0]
            { 2012,  6, 29, ALIGNED_WEEK_OF_MONTH, ValueRange.of(0, 0) },
            { 2012, 13, 29, ALIGNED_WEEK_OF_MONTH, ValueRange.of(0, 0) },
            // Normal days fall in one of 4 weeks per month → [1, 4]
            { 2012,  1, 23, ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 4) },
            { 2012,  6, 23, ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 4) },
            { 2012, 12, 23, ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 4) },

            // --- ALIGNED_DAY_OF_WEEK_IN_YEAR ---
            // Intercalary days are outside the week structure → [0, 0]
            { 2012,  6, 29, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(0, 0) },
            { 2012, 13, 29, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(0, 0) },
            // Normal days fall within a 7-day week → [1, 7]
            { 2012,  1, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7) },
            { 2012,  6, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7) },
            { 2012, 12, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7) },

            // --- ALIGNED_WEEK_OF_YEAR ---
            // Intercalary days are outside the week structure → [0, 0]
            { 2012,  6, 29, ALIGNED_WEEK_OF_YEAR, ValueRange.of(0, 0) },
            { 2012, 13, 29, ALIGNED_WEEK_OF_YEAR, ValueRange.of(0, 0) },
            // Normal days fall in one of 52 weeks per year → [1, 52]
            { 2012,  1, 23, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52) },
            { 2012,  6, 23, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52) },
            { 2012, 12, 23, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52) },

            // --- DAY_OF_WEEK ---
            // Intercalary days do not belong to any day-of-week → [0, 0]
            { 2012,  6, 29, DAY_OF_WEEK, ValueRange.of(0, 0) },
            { 2012, 13, 29, DAY_OF_WEEK, ValueRange.of(0, 0) },
            // Normal days span Monday–Sunday → [1, 7]
            { 2012,  1, 23, DAY_OF_WEEK, ValueRange.of(1, 7) },
            { 2012,  6, 23, DAY_OF_WEEK, ValueRange.of(1, 7) },
            { 2012, 12, 23, DAY_OF_WEEK, ValueRange.of(1, 7) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_ranges")
    public void test_range(int year, int month, int dom, TemporalField field, ValueRange range) {
        assertEquals(range, InternationalFixedDate.of(year, month, dom).range(field));
    }
}
