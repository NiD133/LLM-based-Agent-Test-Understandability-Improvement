package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link JulianDate#range(TemporalField)}, which reports the valid
 * value-range of a temporal field for a given Julian date.
 */
public class TestJulianChronology_test_range {

    /**
     * Cases for {@link #test_range}, each laid out as:
     * {@code { year, month, dayOfMonth, field, expectedMinimum, expectedMaximum }}.
     *
     * <p>The expected range depends on the date because, for example, February's
     * day count and the year's day count differ between leap years (2012) and
     * common years (2011).
     */
    public static Object[][] data_ranges() {
        return new Object[][] {
            // DAY_OF_MONTH: the range follows the length of each month in leap year 2012.
            { 2012, 1, 23, DAY_OF_MONTH, 1, 31 },
            { 2012, 2, 23, DAY_OF_MONTH, 1, 29 },   // leap February has 29 days
            { 2012, 3, 23, DAY_OF_MONTH, 1, 31 },
            { 2012, 4, 23, DAY_OF_MONTH, 1, 30 },
            { 2012, 5, 23, DAY_OF_MONTH, 1, 31 },
            { 2012, 6, 23, DAY_OF_MONTH, 1, 30 },
            { 2012, 7, 23, DAY_OF_MONTH, 1, 31 },
            { 2012, 8, 23, DAY_OF_MONTH, 1, 31 },
            { 2012, 9, 23, DAY_OF_MONTH, 1, 30 },
            { 2012, 10, 23, DAY_OF_MONTH, 1, 31 },
            { 2012, 11, 23, DAY_OF_MONTH, 1, 30 },
            { 2012, 12, 23, DAY_OF_MONTH, 1, 31 },

            // DAY_OF_YEAR and ALIGNED_WEEK_OF_MONTH in leap year 2012.
            { 2012, 1, 23, DAY_OF_YEAR, 1, 366 },           // 366 days in a leap year
            { 2012, 1, 23, ALIGNED_WEEK_OF_MONTH, 1, 5 },
            { 2012, 2, 23, ALIGNED_WEEK_OF_MONTH, 1, 5 },
            { 2012, 3, 23, ALIGNED_WEEK_OF_MONTH, 1, 5 },

            // Common year 2011: shorter February, year and final aligned week.
            { 2011, 2, 23, DAY_OF_MONTH, 1, 28 },           // common February has 28 days
            { 2011, 2, 23, DAY_OF_YEAR, 1, 365 },           // 365 days in a common year
            { 2011, 2, 23, ALIGNED_WEEK_OF_MONTH, 1, 4 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_ranges")
    public void test_range(int year, int month, int dom, TemporalField field, int expectedMin, int expectedMax) {
        ValueRange expectedRange = ValueRange.of(expectedMin, expectedMax);
        assertEquals(expectedRange, JulianDate.of(year, month, dom).range(field));
    }
}
