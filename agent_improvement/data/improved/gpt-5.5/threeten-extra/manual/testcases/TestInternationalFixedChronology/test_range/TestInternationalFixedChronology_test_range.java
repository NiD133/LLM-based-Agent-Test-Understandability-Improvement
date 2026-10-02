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

    public static Object[][] data_ranges() {
        return new Object[][] {
                // Day-of-month varies for regular days, leap day, and year day.
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

                // Year-level ranges depend on whether the year is leap.
                { 2012, 1, 23, DAY_OF_YEAR, ValueRange.of(1, 366) },
                { 2012, 1, 23, MONTH_OF_YEAR, ValueRange.of(1, 13) },

                // Leap day and year day are outside the normal aligned week/day ranges.
                { 2012, 6, 29, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(0, 0) },
                { 2012, 13, 29, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(0, 0) },
                { 2012, 1, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7) },
                { 2012, 6, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7) },
                { 2012, 12, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7) },

                { 2012, 6, 29, ALIGNED_WEEK_OF_MONTH, ValueRange.of(0, 0) },
                { 2012, 13, 29, ALIGNED_WEEK_OF_MONTH, ValueRange.of(0, 0) },
                { 2012, 1, 23, ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 4) },
                { 2012, 6, 23, ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 4) },
                { 2012, 12, 23, ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 4) },

                { 2012, 6, 29, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(0, 0) },
                { 2012, 13, 29, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(0, 0) },
                { 2012, 1, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7) },
                { 2012, 6, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7) },
                { 2012, 12, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7) },

                { 2012, 6, 29, ALIGNED_WEEK_OF_YEAR, ValueRange.of(0, 0) },
                { 2012, 13, 29, ALIGNED_WEEK_OF_YEAR, ValueRange.of(0, 0) },
                { 2012, 1, 23, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52) },
                { 2012, 6, 23, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52) },
                { 2012, 12, 23, ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52) },

                { 2012, 6, 29, DAY_OF_WEEK, ValueRange.of(0, 0) },
                { 2012, 13, 29, DAY_OF_WEEK, ValueRange.of(0, 0) },
                { 2012, 1, 23, DAY_OF_WEEK, ValueRange.of(1, 7) },
                { 2012, 6, 23, DAY_OF_WEEK, ValueRange.of(1, 7) },
                { 2012, 12, 23, DAY_OF_WEEK, ValueRange.of(1, 7) },

                // Non-leap year checks keep the same month/year boundaries narrow.
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
