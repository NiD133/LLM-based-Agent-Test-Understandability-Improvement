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
public class TestInternationalFixedChronology_test_getLong {

    private static final int DAYS_PER_STANDARD_MONTH = 28;
    private static final int MONTHS_PER_YEAR = 13;

    public static Object[][] data_getLong() {
        return new Object[][] {
                // Standard date in a non-leap year.
                {2014, 5, 26, DAY_OF_WEEK, 5},
                {2014, 5, 26, DAY_OF_MONTH, 26},
                {2014, 5, 26, DAY_OF_YEAR, 4 * DAYS_PER_STANDARD_MONTH + 26},
                {2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5},
                {2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 4},
                {2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 5},
                {2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 20},
                {2014, 5, 26, MONTH_OF_YEAR, 5},
                {2014, 5, 26, PROLEPTIC_MONTH, 2014 * MONTHS_PER_YEAR + 5 - 1},
                {2014, 5, 26, YEAR, 2014},
                {2014, 5, 26, ERA, 1},
                {1, 5, 8, ERA, 1},

                // Standard dates after leap day in a leap year.
                {2012, 9, 26, DAY_OF_WEEK, 5},
                {2012, 9, 26, DAY_OF_YEAR, 6 * DAYS_PER_STANDARD_MONTH + 1 + 2 * DAYS_PER_STANDARD_MONTH + 26},
                {2012, 9, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5},
                {2012, 9, 26, ALIGNED_WEEK_OF_MONTH, 4},
                {2014, 9, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 5},
                {2012, 9, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 5},
                {2012, 9, 28, ALIGNED_WEEK_OF_YEAR, 36},
                {2014, 9, 28, ALIGNED_WEEK_OF_YEAR, 36},

                // Year day is represented as day 29 of month 13.
                {2014, 13, 29, DAY_OF_WEEK, 0},
                {2014, 13, 29, DAY_OF_MONTH, 29},
                {2014, 13, 29, DAY_OF_YEAR, MONTHS_PER_YEAR * DAYS_PER_STANDARD_MONTH + 1},
                {2012, 13, 29, DAY_OF_YEAR, MONTHS_PER_YEAR * DAYS_PER_STANDARD_MONTH + 1 + 1},
                {2014, 13, 29, ALIGNED_DAY_OF_WEEK_IN_MONTH, 0},
                {2014, 13, 29, ALIGNED_WEEK_OF_MONTH, 0},
                {2014, 13, 29, ALIGNED_DAY_OF_WEEK_IN_YEAR, 0},
                {2014, 13, 29, ALIGNED_WEEK_OF_YEAR, 0},
                {2014, 13, 29, MONTH_OF_YEAR, 13},
                {2014, 13, 29, PROLEPTIC_MONTH, 2014 * MONTHS_PER_YEAR + 13 - 1},

                // Month 6 around leap day.
                {2012, 6, 28, DAY_OF_WEEK, 7},
                {2012, 6, 28, DAY_OF_MONTH, 28},
                {2012, 6, 28, DAY_OF_YEAR, 6 * DAYS_PER_STANDARD_MONTH},
                {2012, 6, 28, ALIGNED_DAY_OF_WEEK_IN_MONTH, 7},
                {2012, 6, 28, ALIGNED_WEEK_OF_MONTH, 4},
                {2012, 6, 28, ALIGNED_DAY_OF_WEEK_IN_YEAR, 7},
                {2012, 6, 28, ALIGNED_WEEK_OF_YEAR, 24},
                {2012, 6, 28, MONTH_OF_YEAR, 6},
                {2012, 6, 28, PROLEPTIC_MONTH, 2012 * MONTHS_PER_YEAR + 6 - 1},
                {2012, 6, 29, DAY_OF_WEEK, 0},
                {2012, 6, 29, DAY_OF_MONTH, 29},
                {2012, 6, 29, DAY_OF_YEAR, 6 * DAYS_PER_STANDARD_MONTH + 1},
                {2012, 6, 29, ALIGNED_DAY_OF_WEEK_IN_MONTH, 0},
                {2012, 6, 29, ALIGNED_WEEK_OF_MONTH, 0},
                {2012, 6, 29, ALIGNED_DAY_OF_WEEK_IN_YEAR, 0},
                {2012, 6, 29, ALIGNED_WEEK_OF_YEAR, 0},
                {2012, 6, 29, MONTH_OF_YEAR, 6},
                {2012, 6, 29, PROLEPTIC_MONTH, 2012 * MONTHS_PER_YEAR + 6 - 1},

                // First regular day after leap day.
                {2012, 7, 1, DAY_OF_WEEK, 1},
                {2012, 7, 1, DAY_OF_MONTH, 1},
                {2012, 7, 1, DAY_OF_YEAR, 6 * DAYS_PER_STANDARD_MONTH + 2},
                {2012, 7, 1, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1},
                {2012, 7, 1, ALIGNED_WEEK_OF_MONTH, 1},
                {2012, 7, 1, ALIGNED_DAY_OF_WEEK_IN_YEAR, 1},
                {2012, 7, 1, ALIGNED_WEEK_OF_YEAR, 25},
                {2012, 7, 1, MONTH_OF_YEAR, 7},
                {2012, 7, 1, PROLEPTIC_MONTH, 2012 * MONTHS_PER_YEAR + 7 - 1}
        };
    }

    @ParameterizedTest
    @MethodSource("data_getLong")
    public void test_getLong(int year, int month, int dayOfMonth, TemporalField field, long expected) {
        assertEquals(expected, InternationalFixedDate.of(year, month, dayOfMonth).getLong(field));
    }
}
