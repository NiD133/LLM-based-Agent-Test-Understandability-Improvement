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

public class TestBritishCutoverChronology_test_range {

    public static Object[][] data_ranges() {
        return new Object[][] {
                // Day-of-month ranges before, during, and after the 1752 cutover.
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

                // Year-level ranges around leap years and the shortened cutover year.
                { 1700, 1, 23, DAY_OF_YEAR, 1, 366 },
                { 1751, 1, 23, DAY_OF_YEAR, 1, 365 },
                { 1752, 1, 23, DAY_OF_YEAR, 1, 355 },
                { 1753, 1, 23, DAY_OF_YEAR, 1, 365 },
                { 2012, 1, 23, DAY_OF_YEAR, 1, 366 },
                { 2011, 2, 23, DAY_OF_YEAR, 1, 365 },

                // Aligned week ranges affected by the shortened September 1752.
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
                { 1752, 12, 23, ALIGNED_WEEK_OF_YEAR, 1, 51 },
                { 2011, 2, 23, ALIGNED_WEEK_OF_YEAR, 1, 53 },
                { 2012, 2, 23, ALIGNED_WEEK_OF_YEAR, 1, 53 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_ranges")
    public void test_range(int year, int month, int dom, TemporalField field, int expectedMin, int expectedMax) {
        assertEquals(ValueRange.of(expectedMin, expectedMax), BritishCutoverDate.of(year, month, dom).range(field));
    }
}
