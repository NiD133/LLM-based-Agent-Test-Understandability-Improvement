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
     * Test data for {@link #test_range}.
     *
     * <p>Each row is: year, month, dayOfMonth, field, expectedRange.
     *
     * <p>Key calendar rules tested here:
     * <ul>
     *   <li>Leap Day (month 6, day 29 in a leap year) and Year Day (month 13, day 29) are
     *       "intercalary" days that do not belong to any week; their aligned-week and day-of-week
     *       fields collapse to the empty range [0, 0].</li>
     *   <li>Month 6 in a leap year has 29 days; all other ordinary months have 28 days.</li>
     *   <li>Month 13 always has 29 days (day 29 = Year Day).</li>
     *   <li>A leap year has 366 days; a non-leap year has 365 days.</li>
     * </ul>
     */
    public static Object[][] data_ranges() {
        return new Object[][] {
            // --- DAY_OF_MONTH range ---
            // Leap Day (month 6, day 29) in a leap year: month 6 has 29 days
            { 2012, 6, 29, DAY_OF_MONTH, ValueRange.of(1, 29) },
            // Year Day (month 13, day 29): month 13 always has 29 days
            { 2012, 13, 29, DAY_OF_MONTH, ValueRange.of(1, 29) },
            // Ordinary months in a leap year all have 28 days
            { 2012, 1, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012, 2, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012, 3, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012, 4, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012, 5, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            // Month 6 in a leap year extends to day 29
            { 2012, 6, 23, DAY_OF_MONTH, ValueRange.of(1, 29) },
            { 2012, 7, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012, 8, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012, 9, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012, 10, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012, 11, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012, 12, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            // Month 13 always extends to day 29 (Year Day)
            { 2012, 13, 23, DAY_OF_MONTH, ValueRange.of(1, 29) },
            // Non-leap year: month 6 has only 28 days
            { 2011, 6, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },

            // --- DAY_OF_YEAR range ---
            // Leap year: 366 days total
            { 2012, 1, 23, DAY_OF_YEAR, ValueRange.of(1, 366) },
            // Non-leap year: 365 days total
            { 2011, 13, 23, DAY_OF_YEAR, ValueRange.of(1, 365) },

            // --- MONTH_OF_YEAR range ---
            // Always 13 months in a year (Leap Day stays in month 6, not an extra month)
            { 2012, 1, 23, MONTH_OF_YEAR, ValueRange.of(1, 13) },
            { 2011, 13, 23, MONTH_OF_YEAR, ValueRange.of(1, 13) },

            // --- ALIGNED_DAY_OF_WEEK_IN_MONTH range ---
            // Intercalary days (Leap Day and Year Day) do not belong to any week: range is [0, 0]
            { 2012, 6, 29, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(0, 0) },
            { 2012, 13, 29, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(0, 0) },
            // Regular days belong to a week: range is [1, 7]
            { 2012, 1, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7) },
            { 2012, 6, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7) },
            { 2012, 12, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7) },

            // --- ALIGNED_WEEK_OF_MONTH range ---
            // Intercalary days: range is [0, 0]
            { 2012, 6, 29, ALIGNED_WEEK_OF_MONTH, ValueRange.of(0, 0) },
            { 2012, 13, 29, ALIGNED_WEEK_OF_MONTH, ValueRange.of(0, 0) },
            // Regular days: each month has exactly 4 weeks, range is [1, 4]
            { 2012, 1, 23, ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 4) },
            { 2012, 6, 23, ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 4) },
            { 2012, 12, 23, ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 4) },

            // --- ALIGNED_DAY_OF_WEEK_IN_YEAR range ---
            // Intercalary days: range is [0, 0]
            { 2012, 6, 29, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(0, 0) },
            { 2012, 13, 29, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(0, 0) },
            // Regular days: range is [1, 7]
            { 2012, 1, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7) },
            { 2012, 6, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7) },
            { 2012, 12, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7) },

            // --- ALIGNED_WEEK_OF_YEAR range ---
            // Intercalary days: range is [0, 0]
            { 2012, 6, 29, ALIGNED_WEEK_OF_YEAR, ValueRange.of(0, 0) },
            { 2012, 13, 29, ALIGNED_WEEK_OF_YEAR, ValueRange.of(0, 0) },
            // Regular days: 52 weeks in a year, range is [1, 52]
            { 2012, 1, 23, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52) },
            { 2012, 6, 23, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52) },
            { 2012, 12, 23, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52) },

            // --- DAY_OF_WEEK range ---
            // Intercalary days: range is [0, 0]
            { 2012, 6, 29, DAY_OF_WEEK, ValueRange.of(0, 0) },
            { 2012, 13, 29, DAY_OF_WEEK, ValueRange.of(0, 0) },
            // Regular days: Mon-Sun mapped to 1-7, range is [1, 7]
            { 2012, 1, 23, DAY_OF_WEEK, ValueRange.of(1, 7) },
            { 2012, 6, 23, DAY_OF_WEEK, ValueRange.of(1, 7) },
            { 2012, 12, 23, DAY_OF_WEEK, ValueRange.of(1, 7) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_ranges")
    public void test_range(int year, int month, int dom, TemporalField field, ValueRange range) {
        assertEquals(range, InternationalFixedDate.of(year, month, dom).range(field));
    }
}
