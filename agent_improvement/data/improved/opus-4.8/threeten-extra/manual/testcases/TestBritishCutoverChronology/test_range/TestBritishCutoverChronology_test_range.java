package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_YEAR;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link BritishCutoverDate#range(TemporalField)}.
 *
 * <p>For a given date, {@code range(field)} reports the smallest and largest values the field can
 * take. The interesting cases all cluster around September 1752, when the British cutover dropped
 * the 11 days from the 3rd to the 13th, shortening that month and that year.
 */
public class TestBritishCutoverChronology_test_range {

    /**
     * Cases of {year, month, dayOfMonth, field, expectedMin, expectedMax}.
     *
     * <p>The date's day-of-month is irrelevant to the expected range; 23 is used throughout simply
     * as an arbitrary valid day.
     */
    public static Object[][] data_ranges() {
        return new Object[][] {
            // DAY_OF_MONTH: min is always 1; max is the length of the month.
            // 1700 is a Julian leap year, so February has 29 days.
            { 1700, 1, 23, DAY_OF_MONTH, 1, 31 },
            { 1700, 2, 23, DAY_OF_MONTH, 1, 29 },
            { 1700, 3, 23, DAY_OF_MONTH, 1, 31 },
            { 1700, 4, 23, DAY_OF_MONTH, 1, 30 },
            { 1700, 5, 23, DAY_OF_MONTH, 1, 31 },
            { 1700, 6, 23, DAY_OF_MONTH, 1, 30 },
            { 1700, 7, 23, DAY_OF_MONTH, 1, 31 },
            { 1700, 8, 23, DAY_OF_MONTH, 1, 31 },
            { 1700, 9, 23, DAY_OF_MONTH, 1, 30 },
            { 1700, 10, 23, DAY_OF_MONTH, 1, 31 },
            { 1700, 11, 23, DAY_OF_MONTH, 1, 30 },
            { 1700, 12, 23, DAY_OF_MONTH, 1, 31 },
            // 1751 is a common year (February has 28 days).
            { 1751, 1, 23, DAY_OF_MONTH, 1, 31 },
            { 1751, 2, 23, DAY_OF_MONTH, 1, 28 },
            { 1751, 3, 23, DAY_OF_MONTH, 1, 31 },
            { 1751, 4, 23, DAY_OF_MONTH, 1, 30 },
            { 1751, 5, 23, DAY_OF_MONTH, 1, 31 },
            { 1751, 6, 23, DAY_OF_MONTH, 1, 30 },
            { 1751, 7, 23, DAY_OF_MONTH, 1, 31 },
            { 1751, 8, 23, DAY_OF_MONTH, 1, 31 },
            { 1751, 9, 23, DAY_OF_MONTH, 1, 30 },
            { 1751, 10, 23, DAY_OF_MONTH, 1, 31 },
            { 1751, 11, 23, DAY_OF_MONTH, 1, 30 },
            { 1751, 12, 23, DAY_OF_MONTH, 1, 31 },
            // 1752 is the cutover year and a leap year. The DAY_OF_MONTH range still spans
            // 1..30 for September even though 11 days are missing from the middle of it.
            { 1752, 1, 23, DAY_OF_MONTH, 1, 31 },
            { 1752, 2, 23, DAY_OF_MONTH, 1, 29 },
            { 1752, 3, 23, DAY_OF_MONTH, 1, 31 },
            { 1752, 4, 23, DAY_OF_MONTH, 1, 30 },
            { 1752, 5, 23, DAY_OF_MONTH, 1, 31 },
            { 1752, 6, 23, DAY_OF_MONTH, 1, 30 },
            { 1752, 7, 23, DAY_OF_MONTH, 1, 31 },
            { 1752, 8, 23, DAY_OF_MONTH, 1, 31 },
            { 1752, 9, 23, DAY_OF_MONTH, 1, 30 },
            { 1752, 10, 23, DAY_OF_MONTH, 1, 31 },
            { 1752, 11, 23, DAY_OF_MONTH, 1, 30 },
            { 1752, 12, 23, DAY_OF_MONTH, 1, 31 },
            // 2012 is a Gregorian leap year; 2011 is common.
            { 2012, 1, 23, DAY_OF_MONTH, 1, 31 },
            { 2012, 2, 23, DAY_OF_MONTH, 1, 29 },
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
            { 2011, 2, 23, DAY_OF_MONTH, 1, 28 },

            // DAY_OF_YEAR: max is the length of the year. 1752 loses 11 days, so 366 - 11 = 355.
            { 1700, 1, 23, DAY_OF_YEAR, 1, 366 },
            { 1751, 1, 23, DAY_OF_YEAR, 1, 365 },
            { 1752, 1, 23, DAY_OF_YEAR, 1, 355 },
            { 1753, 1, 23, DAY_OF_YEAR, 1, 365 },
            { 2012, 1, 23, DAY_OF_YEAR, 1, 366 },
            { 2011, 2, 23, DAY_OF_YEAR, 1, 365 },

            // ALIGNED_WEEK_OF_MONTH: max is 5 for full months, but only 3 for the short
            // September 1752 (its 19 days span just three aligned weeks).
            { 1752, 1, 23, ALIGNED_WEEK_OF_MONTH, 1, 5 },
            { 1752, 2, 23, ALIGNED_WEEK_OF_MONTH, 1, 5 },
            { 1752, 3, 23, ALIGNED_WEEK_OF_MONTH, 1, 5 },
            { 1752, 4, 23, ALIGNED_WEEK_OF_MONTH, 1, 5 },
            { 1752, 5, 23, ALIGNED_WEEK_OF_MONTH, 1, 5 },
            { 1752, 6, 23, ALIGNED_WEEK_OF_MONTH, 1, 5 },
            { 1752, 7, 23, ALIGNED_WEEK_OF_MONTH, 1, 5 },
            { 1752, 8, 23, ALIGNED_WEEK_OF_MONTH, 1, 5 },
            { 1752, 9, 23, ALIGNED_WEEK_OF_MONTH, 1, 3 },
            { 1752, 10, 23, ALIGNED_WEEK_OF_MONTH, 1, 5 },
            { 1752, 11, 23, ALIGNED_WEEK_OF_MONTH, 1, 5 },
            { 1752, 12, 23, ALIGNED_WEEK_OF_MONTH, 1, 5 },
            { 2012, 1, 23, ALIGNED_WEEK_OF_MONTH, 1, 5 },
            { 2012, 2, 23, ALIGNED_WEEK_OF_MONTH, 1, 5 },
            { 2012, 3, 23, ALIGNED_WEEK_OF_MONTH, 1, 5 },
            { 2011, 2, 23, ALIGNED_WEEK_OF_MONTH, 1, 4 },

            // ALIGNED_WEEK_OF_YEAR: max is 51 for the short cutover year, 53 otherwise.
            { 1752, 12, 23, ALIGNED_WEEK_OF_YEAR, 1, 51 },
            { 2011, 2, 23, ALIGNED_WEEK_OF_YEAR, 1, 53 },
            { 2012, 2, 23, ALIGNED_WEEK_OF_YEAR, 1, 53 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_ranges")
    public void test_range(int year, int month, int dom, TemporalField field, int expectedMin, int expectedMax) {
        ValueRange actualRange = BritishCutoverDate.of(year, month, dom).range(field);
        assertEquals(ValueRange.of(expectedMin, expectedMax), actualRange);
    }
}
