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

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings("static-method")
public class TestSymmetry454Chronology_test_getLong {

    private static final int WEEKS_IN_SHORT_MONTH = 4;
    private static final int WEEKS_IN_LONG_MONTH = 5;
    private static final int DAYS_IN_WEEK = 7;
    private static final int DAYS_IN_SHORT_MONTH = 28;
    private static final int DAYS_IN_LONG_MONTH = 35;
    private static final int WEEKS_IN_QUARTER = WEEKS_IN_SHORT_MONTH + WEEKS_IN_LONG_MONTH + WEEKS_IN_SHORT_MONTH;

    public static Object[][] data_getLong() {
        return new Object[][] {
                { 2014, 5, 26, DAY_OF_WEEK, 5 },
                { 2014, 5, 26, DAY_OF_MONTH, 26 },
                { 2014, 5, 26, DAY_OF_YEAR, DAYS_IN_SHORT_MONTH + DAYS_IN_LONG_MONTH + DAYS_IN_SHORT_MONTH + DAYS_IN_SHORT_MONTH + 26 },
                { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5 },
                { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 4 },
                { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 5 },
                { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR, WEEKS_IN_SHORT_MONTH + WEEKS_IN_LONG_MONTH + WEEKS_IN_SHORT_MONTH + WEEKS_IN_SHORT_MONTH + WEEKS_IN_SHORT_MONTH },
                { 2014, 5, 26, MONTH_OF_YEAR, 5 },
                { 2014, 5, 26, PROLEPTIC_MONTH, 2014 * 12 + 5 - 1 },
                { 2014, 5, 26, YEAR, 2014 },
                { 2014, 5, 26, ERA, 1 },
                { 1, 5, 8, ERA, 1 },

                { 2012, 9, 26, DAY_OF_WEEK, 5 },
                { 2012, 9, 26, DAY_OF_YEAR, 3 * WEEKS_IN_QUARTER * DAYS_IN_WEEK - 2 },
                { 2012, 9, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5 },
                { 2012, 9, 26, ALIGNED_WEEK_OF_MONTH, 4 },
                { 2012, 9, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 5 },
                { 2012, 9, 26, ALIGNED_WEEK_OF_YEAR, 3 * WEEKS_IN_QUARTER },

                { 2015, 12, 35, DAY_OF_WEEK, 7 },
                { 2015, 12, 35, DAY_OF_MONTH, 35 },
                { 2015, 12, 35, DAY_OF_YEAR, 4 * WEEKS_IN_QUARTER * DAYS_IN_WEEK + DAYS_IN_WEEK },
                { 2015, 12, 35, ALIGNED_DAY_OF_WEEK_IN_MONTH, 7 },
                { 2015, 12, 35, ALIGNED_WEEK_OF_MONTH, 5 },
                { 2015, 12, 35, ALIGNED_DAY_OF_WEEK_IN_YEAR, 7 },
                { 2015, 12, 35, ALIGNED_WEEK_OF_YEAR, 53 },
                { 2015, 12, 35, MONTH_OF_YEAR, 12 },
                { 2015, 12, 35, PROLEPTIC_MONTH, 2016 * 12 - 1 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_getLong")
    public void test_getLong(int year, int month, int dayOfMonth, TemporalField field, long expectedValue) {
        assertEquals(expectedValue, Symmetry454Date.of(year, month, dayOfMonth).getLong(field));
    }
}
