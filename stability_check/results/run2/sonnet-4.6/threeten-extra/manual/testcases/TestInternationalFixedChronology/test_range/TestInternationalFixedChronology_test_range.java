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
     * Test data for {@link #test_range}: each row is (year, month, day, field, expectedRange).
     *
     * The International Fixed Calendar has two special "intercalary" days that do not belong
     * to any regular week:
     *   - Leap Day  : month 6, day 29 (only in leap years)
     *   - Year Day  : month 13, day 29 (every year)
     *
     * For those days the week-related fields have range [0, 0]; for normal days the ranges
     * reflect the calendar's 13-month, 28-day-per-month structure.
     */
    public static Object[][] data_ranges() {
        return new Object[][] {
            // --- DAY_OF_MONTH ---
            // Leap Day (month 6, day 29 in leap year 2012): month has 29 days
            { 2012,  6, 29, DAY_OF_MONTH, ValueRange.of(1, 29) },
            // Year Day (month 13, day 29): month has 29 days
            { 2012, 13, 29, DAY_OF_MONTH, ValueRange.of(1, 29) },
            // Regular months in leap year 2012: all have 28 days except month 6 and 13
            { 2012,  1, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012,  2, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012,  3, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012,  4, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012,  5, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            // Month 6 in leap year 2012 has 29 days (Leap Day appended)
            { 2012,  6, 23, DAY_OF_MONTH, ValueRange.of(1, 29) },
            { 2012,  7, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012,  8, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012,  9, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012, 10, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012, 11, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012, 12, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            // Month 13 always has 29 days (Year Day appended)
            { 2012, 13, 23, DAY_OF_MONTH, ValueRange.of(1, 29) },
            // Non-leap year: month 6 has only 28 days
            { 2011,  6, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },

            // --- DAY_OF_YEAR ---
            // Leap year 2012: 366 days
            { 2012,  1, 23, DAY_OF_YEAR, ValueRange.of(1, 366) },
            // Non-leap year 2011: 365 days
            { 2011, 13, 23, DAY_OF_YEAR, ValueRange.of(1, 365) },

            // --- MONTH_OF_YEAR ---
            // Always 13 months regardless of leap year
            { 2012,  1, 23, MONTH_OF_YEAR, ValueRange.of(1, 13) },
            { 2011, 13, 23, MONTH_OF_YEAR, ValueRange.of(1, 13) },

            // --- ALIGNED_DAY_OF_WEEK_IN_MONTH ---
            // Intercalary days (Leap Day, Year Day) are outside any week: range [0, 0]
            { 2012,  6, 29, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(0, 0) },
            { 2012, 13, 29, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(0, 0) },
            // Regular days: day-of-week within month runs 1..7
            { 2012,  1, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7) },
            { 2012,  6, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7) },
            { 2012, 12, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7) },

            // --- ALIGNED_WEEK_OF_MONTH ---
            // Intercalary days are outside any week: range [0, 0]
            { 2012,  6, 29, ALIGNED_WEEK_OF_MONTH, ValueRange.of(0, 0) },
            { 2012, 13, 29, ALIGNED_WEEK_OF_MONTH, ValueRange.of(0, 0) },
            // Regular days: 4 complete weeks per month → weeks 1..4
            { 2012,  1, 23, ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 4) },
            { 2012,  6, 23, ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 4) },
            { 2012, 12, 23, ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 4) },

            // --- ALIGNED_DAY_OF_WEEK_IN_YEAR ---
            // Intercalary days are outside any week: range [0, 0]
            { 2012,  6, 29, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(0, 0) },
            { 2012, 13, 29, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(0, 0) },
            // Regular days: aligned day-of-week in year runs 1..7
            { 2012,  1, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7) },
            { 2012,  6, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7) },
            { 2012, 12, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7) },

            // --- ALIGNED_WEEK_OF_YEAR ---
            // Intercalary days are outside any week: range [0, 0]
            { 2012,  6, 29, ALIGNED_WEEK_OF_YEAR, ValueRange.of(0, 0) },
            { 2012, 13, 29, ALIGNED_WEEK_OF_YEAR, ValueRange.of(0, 0) },
            // Regular days: 52 weeks per year → weeks 1..52
            { 2012,  1, 23, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52) },
            { 2012,  6, 23, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52) },
            { 2012, 12, 23, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52) },

            // --- DAY_OF_WEEK ---
            // Intercalary days belong to no week: range [0, 0]
            { 2012,  6, 29, DAY_OF_WEEK, ValueRange.of(0, 0) },
            { 2012, 13, 29, DAY_OF_WEEK, ValueRange.of(0, 0) },
            // Regular days: day-of-week runs 1..7
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
