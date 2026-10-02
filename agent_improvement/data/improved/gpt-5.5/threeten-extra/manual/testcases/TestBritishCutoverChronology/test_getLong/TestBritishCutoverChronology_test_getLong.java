package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_YEAR;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.ERA;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.PROLEPTIC_MONTH;
import static java.time.temporal.ChronoField.YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestBritishCutoverChronology_test_getLong {

    public static Object[][] data_getLong() {
        return new Object[][] {
                { 1752, 5, 26, DAY_OF_WEEK, 2 },
                { 1752, 5, 26, DAY_OF_MONTH, 26 },
                { 1752, 5, 26, DAY_OF_YEAR, 31 + 29 + 31 + 30 + 26 },
                { 1752, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5 },
                { 1752, 5, 26, ALIGNED_WEEK_OF_MONTH, 4 },
                { 1752, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 7 },
                { 1752, 5, 26, ALIGNED_WEEK_OF_YEAR, 21 },
                { 1752, 5, 26, MONTH_OF_YEAR, 5 },

                { 1752, 9, 2, DAY_OF_WEEK, 3 },
                { 1752, 9, 2, DAY_OF_MONTH, 2 },
                { 1752, 9, 2, DAY_OF_YEAR, 31 + 29 + 31 + 30 + 31 + 30 + 31 + 31 + 2 },
                { 1752, 9, 2, ALIGNED_DAY_OF_WEEK_IN_MONTH, 2 },
                { 1752, 9, 2, ALIGNED_WEEK_OF_MONTH, 1 },
                { 1752, 9, 2, ALIGNED_DAY_OF_WEEK_IN_YEAR, 1 },
                { 1752, 9, 2, ALIGNED_WEEK_OF_YEAR, 36 },
                { 1752, 9, 2, MONTH_OF_YEAR, 9 },

                { 1752, 9, 14, DAY_OF_WEEK, 4 },
                { 1752, 9, 14, DAY_OF_MONTH, 14 },
                { 1752, 9, 14, DAY_OF_YEAR, 31 + 29 + 31 + 30 + 31 + 30 + 31 + 31 + 3 },
                { 1752, 9, 14, ALIGNED_DAY_OF_WEEK_IN_MONTH, 3 },
                { 1752, 9, 14, ALIGNED_WEEK_OF_MONTH, 1 },
                { 1752, 9, 14, ALIGNED_DAY_OF_WEEK_IN_YEAR, 2 },
                { 1752, 9, 14, ALIGNED_WEEK_OF_YEAR, 36 },
                { 1752, 9, 14, MONTH_OF_YEAR, 9 },

                { 2014, 5, 26, DAY_OF_WEEK, 1 },
                { 2014, 5, 26, DAY_OF_MONTH, 26 },
                { 2014, 5, 26, DAY_OF_YEAR, 31 + 28 + 31 + 30 + 26 },
                { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5 },
                { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 4 },
                { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 6 },
                { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 21 },
                { 2014, 5, 26, MONTH_OF_YEAR, 5 },
                { 2014, 5, 26, PROLEPTIC_MONTH, 2014 * 12 + 5 - 1 },
                { 2014, 5, 26, YEAR, 2014 },
                { 2014, 5, 26, ERA, 1 },

                { 1, 6, 8, ERA, 1 },
                { 0, 6, 8, ERA, 0 },
                { 2014, 5, 26, WeekFields.ISO.dayOfWeek(), 1 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_getLong")
    public void test_getLong(int year, int month, int dom, TemporalField field, long expected) {
        assertEquals(expected, BritishCutoverDate.of(year, month, dom).getLong(field));
    }
}
