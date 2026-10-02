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
 * Tests {@link InternationalFixedDate#range(TemporalField)}.
 *
 * <p>The International Fixed calendar has 13 months of 28 days each. Two special
 * days sit outside the normal weekly rhythm:
 * <ul>
 *   <li><b>Leap Day</b> &mdash; the 29th of month 6, only in leap years.</li>
 *   <li><b>Year Day</b> &mdash; the 29th of month 13, in every year.</li>
 * </ul>
 * Because these two days belong to no week, the week-related fields report the
 * empty range {@code (0, 0)} for them.
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_range {

    private static Arguments dateRange(int year, int month, int dayOfMonth, TemporalField field, ValueRange expectedRange) {
        return Arguments.of(year, month, dayOfMonth, field, expectedRange);
    }

    public static Object[][] data_ranges() {
        return new Object[][] {
            // DAY_OF_MONTH: normal months allow 1..28; months holding Leap Day (6) or Year Day (13) allow 1..29.
            { 2012, 6, 29, DAY_OF_MONTH, ValueRange.of(1, 29) },
            { 2012, 13, 29, DAY_OF_MONTH, ValueRange.of(1, 29) },
            { 2012, 1, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012, 2, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012, 3, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012, 4, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012, 5, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012, 6, 23, DAY_OF_MONTH, ValueRange.of(1, 29) },
            { 2012, 7, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012, 8, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012, 9, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012, 10, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012, 11, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012, 12, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2012, 13, 23, DAY_OF_MONTH, ValueRange.of(1, 29) },

            // DAY_OF_YEAR: leap year spans 1..366, common year 1..365.
            { 2012, 1, 23, DAY_OF_YEAR, ValueRange.of(1, 366) },
            { 2011, 13, 23, DAY_OF_YEAR, ValueRange.of(1, 365) },

            // MONTH_OF_YEAR: always 13 months, whether leap or common.
            { 2012, 1, 23, MONTH_OF_YEAR, ValueRange.of(1, 13) },
            { 2011, 13, 23, MONTH_OF_YEAR, ValueRange.of(1, 13) },

            // ALIGNED_DAY_OF_WEEK_IN_MONTH: Leap Day / Year Day belong to no week -> (0, 0); ordinary days -> 1..7.
            { 2012, 6, 29, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(0, 0) },
            { 2012, 13, 29, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(0, 0) },
            { 2012, 1, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7) },
            { 2012, 6, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7) },
            { 2012, 12, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7) },

            // ALIGNED_WEEK_OF_MONTH: Leap Day / Year Day -> (0, 0); ordinary days -> 4 weeks of the month.
            { 2012, 6, 29, ALIGNED_WEEK_OF_MONTH, ValueRange.of(0, 0) },
            { 2012, 13, 29, ALIGNED_WEEK_OF_MONTH, ValueRange.of(0, 0) },
            { 2012, 1, 23, ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 4) },
            { 2012, 6, 23, ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 4) },
            { 2012, 12, 23, ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 4) },

            // ALIGNED_DAY_OF_WEEK_IN_YEAR: Leap Day / Year Day -> (0, 0); ordinary days -> 1..7.
            { 2012, 6, 29, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(0, 0) },
            { 2012, 13, 29, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(0, 0) },
            { 2012, 1, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7) },
            { 2012, 6, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7) },
            { 2012, 12, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7) },

            // ALIGNED_WEEK_OF_YEAR: Leap Day / Year Day -> (0, 0); ordinary days -> 52 weeks of the year.
            { 2012, 6, 29, ALIGNED_WEEK_OF_YEAR, ValueRange.of(0, 0) },
            { 2012, 13, 29, ALIGNED_WEEK_OF_YEAR, ValueRange.of(0, 0) },
            { 2012, 1, 23, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52) },
            { 2012, 6, 23, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52) },
            { 2012, 12, 23, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52) },

            // DAY_OF_WEEK: Leap Day / Year Day belong to no week -> (0, 0); ordinary days -> 1..7.
            { 2012, 6, 29, DAY_OF_WEEK, ValueRange.of(0, 0) },
            { 2012, 13, 29, DAY_OF_WEEK, ValueRange.of(0, 0) },
            { 2012, 1, 23, DAY_OF_WEEK, ValueRange.of(1, 7) },
            { 2012, 6, 23, DAY_OF_WEEK, ValueRange.of(1, 7) },
            { 2012, 12, 23, DAY_OF_WEEK, ValueRange.of(1, 7) },
            { 2011, 6, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_ranges")
    public void test_range(int year, int month, int dom, TemporalField field, ValueRange expectedRange) {
        InternationalFixedDate date = InternationalFixedDate.of(year, month, dom);

        assertEquals(expectedRange, date.range(field));
    }
}
