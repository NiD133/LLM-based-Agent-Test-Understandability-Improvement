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

/**
 * Verifies {@link DiscordianDate#range(TemporalField)}.
 * <p>
 * The Discordian calendar has 5 months of 73 days, 5-day weeks, and 73 weeks per year.
 * St. Tib's Day (represented as month 0, day 0) is a leap-year-only day that sits between
 * the 59th and 60th day of the first month and belongs to no month and no week. Because of
 * this, the valid range of several fields depends on whether the queried date is St. Tib's
 * Day and on whether the year is a leap year. The cases below are grouped per field to make
 * those special rules explicit.
 */
public class TestDiscordianChronology_test_range {

    // 2010 and 2018 are Discordian leap years; 2011 is not.

    public static Object[][] data_ranges() {
        return new Object[][] {
            // DAY_OF_MONTH: St. Tib's Day spans (0..0); every real month spans (1..73).
            { 2010, 0, 0, DAY_OF_MONTH, 0, 0 },
            { 2010, 1, 23, DAY_OF_MONTH, 1, 73 },
            { 2010, 2, 23, DAY_OF_MONTH, 1, 73 },
            { 2010, 3, 23, DAY_OF_MONTH, 1, 73 },
            { 2010, 4, 23, DAY_OF_MONTH, 1, 73 },
            { 2010, 5, 23, DAY_OF_MONTH, 1, 73 },

            // DAY_OF_YEAR: an ordinal count that includes St. Tib's Day,
            // so (1..366) in a leap year and (1..365) otherwise.
            { 2010, 0, 0, DAY_OF_YEAR, 1, 366 },
            { 2010, 1, 23, DAY_OF_YEAR, 1, 366 },
            { 2011, 2, 23, DAY_OF_YEAR, 1, 365 },

            // MONTH_OF_YEAR: St. Tib's Day still belongs to the year,
            // so (0..5) in a leap year and (1..5) otherwise.
            { 2010, 0, 0, MONTH_OF_YEAR, 0, 5 },
            { 2010, 1, 1, MONTH_OF_YEAR, 0, 5 },
            { 2011, 1, 23, MONTH_OF_YEAR, 1, 5 },

            // ALIGNED_DAY_OF_WEEK_IN_MONTH: St. Tib's Day spans (0..0); real days span (1..5).
            { 2010, 0, 0, ALIGNED_DAY_OF_WEEK_IN_MONTH, 0, 0 },
            { 2010, 1, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1, 5 },
            { 2010, 1, 59, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1, 5 },
            { 2010, 1, 60, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1, 5 },

            // ALIGNED_DAY_OF_WEEK_IN_YEAR: St. Tib's Day stays in the year,
            // so (0..5) in a leap year and (1..5) otherwise.
            { 2010, 0, 0, ALIGNED_DAY_OF_WEEK_IN_YEAR, 0, 5 },
            { 2010, 1, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, 0, 5 },
            { 2010, 1, 59, ALIGNED_DAY_OF_WEEK_IN_YEAR, 0, 5 },
            { 2010, 1, 60, ALIGNED_DAY_OF_WEEK_IN_YEAR, 0, 5 },
            { 2011, 1, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, 1, 5 },

            // ALIGNED_WEEK_OF_MONTH: St. Tib's Day spans (0..0); real days span (1..15).
            { 2010, 0, 0, ALIGNED_WEEK_OF_MONTH, 0, 0 },
            { 2010, 1, 23, ALIGNED_WEEK_OF_MONTH, 1, 15 },

            // ALIGNED_WEEK_OF_YEAR: St. Tib's Day stays in the year,
            // so (0..73) in a leap year and (1..73) otherwise.
            { 2010, 0, 0, ALIGNED_WEEK_OF_YEAR, 0, 73 },
            { 2010, 1, 23, ALIGNED_WEEK_OF_YEAR, 0, 73 },
            { 2011, 1, 23, ALIGNED_WEEK_OF_YEAR, 1, 73 },

            // DAY_OF_WEEK: St. Tib's Day is its own week (0..0); real days span (1..5).
            { 2010, 0, 0, DAY_OF_WEEK, 0, 0 },
            { 2010, 1, 1, DAY_OF_WEEK, 1, 5 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_ranges")
    public void test_range(int year, int month, int dom, TemporalField field, int expectedMin, int expectedMax) {
        ValueRange expectedRange = ValueRange.of(expectedMin, expectedMax);
        ValueRange actualRange = DiscordianDate.of(year, month, dom).range(field);
        assertEquals(expectedRange, actualRange);
    }
}
