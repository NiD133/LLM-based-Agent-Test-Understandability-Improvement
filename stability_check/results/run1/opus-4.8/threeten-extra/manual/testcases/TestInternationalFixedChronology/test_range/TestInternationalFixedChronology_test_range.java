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
 * <p>
 * In the International Fixed calendar the Leap Day (month 6, day 29 of a leap year)
 * and the Year Day (month 13, day 29) sit outside the normal week structure: they
 * belong to no week and to no weekday. As a result, all week-based fields report the
 * empty range {@code [0, 0]} for those two days, while ordinary days report the usual
 * 7-day / 4-week ranges.
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_range {

    /**
     * Each case is: {year, month, dayOfMonth, field, expected range of that field for the date}.
     */
    public static Object[][] data_ranges() {
        return new Object[][] {
            // DAY_OF_MONTH: 28 days normally, 29 in months that carry the Leap Day (6) or Year Day (13).
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

            // DAY_OF_YEAR: 366 in a leap year, 365 otherwise.
            { 2012, 1, 23, DAY_OF_YEAR, ValueRange.of(1, 366) },

            // MONTH_OF_YEAR: always 13 months.
            { 2012, 1, 23, MONTH_OF_YEAR, ValueRange.of(1, 13) },

            // ALIGNED_DAY_OF_WEEK_IN_MONTH: empty for Leap/Year Day, else 1..7.
            { 2012, 6, 29, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(0, 0) },
            { 2012, 13, 29, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(0, 0) },
            { 2012, 1, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7) },
            { 2012, 6, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7) },
            { 2012, 12, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7) },

            // ALIGNED_WEEK_OF_MONTH: empty for Leap/Year Day, else 1..4.
            { 2012, 6, 29, ALIGNED_WEEK_OF_MONTH, ValueRange.of(0, 0) },
            { 2012, 13, 29, ALIGNED_WEEK_OF_MONTH, ValueRange.of(0, 0) },
            { 2012, 1, 23, ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 4) },
            { 2012, 6, 23, ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 4) },
            { 2012, 12, 23, ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 4) },

            // ALIGNED_DAY_OF_WEEK_IN_YEAR: empty for Leap/Year Day, else 1..7.
            { 2012, 6, 29, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(0, 0) },
            { 2012, 13, 29, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(0, 0) },
            { 2012, 1, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7) },
            { 2012, 6, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7) },
            { 2012, 12, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7) },

            // ALIGNED_WEEK_OF_YEAR: empty for Leap/Year Day, else 1..52.
            { 2012, 6, 29, ALIGNED_WEEK_OF_YEAR, ValueRange.of(0, 0) },
            { 2012, 13, 29, ALIGNED_WEEK_OF_YEAR, ValueRange.of(0, 0) },
            { 2012, 1, 23, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52) },
            { 2012, 6, 23, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52) },
            { 2012, 12, 23, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52) },

            // DAY_OF_WEEK: empty for Leap/Year Day, else 1..7.
            { 2012, 6, 29, DAY_OF_WEEK, ValueRange.of(0, 0) },
            { 2012, 13, 29, DAY_OF_WEEK, ValueRange.of(0, 0) },
            { 2012, 1, 23, DAY_OF_WEEK, ValueRange.of(1, 7) },
            { 2012, 6, 23, DAY_OF_WEEK, ValueRange.of(1, 7) },
            { 2012, 12, 23, DAY_OF_WEEK, ValueRange.of(1, 7) },

            // Non-leap year (2011): month 6 is a normal 28-day month and the year has 365 days.
            { 2011, 6, 23, DAY_OF_MONTH, ValueRange.of(1, 28) },
            { 2011, 13, 23, DAY_OF_YEAR, ValueRange.of(1, 365) },
            { 2011, 13, 23, MONTH_OF_YEAR, ValueRange.of(1, 13) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_ranges")
    public void test_range(int year, int month, int dom, TemporalField field, ValueRange range) {
        assertEquals(range, InternationalFixedDate.of(year, month, dom).range(field));
    }
}
