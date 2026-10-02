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

    public static Object[][] data_getLong() {
        return new Object[][] {
            // Standard date in a non-leap year: 2014/05/26 (month 5, day 26)
            { 2014, 5, 26, DAY_OF_WEEK,                   5 },
            { 2014, 5, 26, DAY_OF_MONTH,                  26 },
            { 2014, 5, 26, DAY_OF_YEAR,                   28 + 28 + 28 + 28 + 26 }, // 4 full months + day 26
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH,  5 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH,         4 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR,   5 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR,          20 },
            { 2014, 5, 26, MONTH_OF_YEAR,                 5 },
            { 2014, 5, 26, PROLEPTIC_MONTH,               2014 * 13 + 5 - 1 },
            { 2014, 5, 26, YEAR,                          2014 },
            { 2014, 5, 26, ERA,                           1 },
            { 1,    5,  8, ERA,                           1 },

            // Standard date in a leap year: 2012/09/26 (month 9, day 26, Leap Day adds 1 to DAY_OF_YEAR)
            { 2012, 9, 26, DAY_OF_WEEK,                   5 },
            { 2012, 9, 26, DAY_OF_YEAR,                   28 + 28 + 28 + 28 + 28 + 28 + 1 + 28 + 28 + 26 }, // 6 months + Leap Day + 2 months + 26
            { 2012, 9, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH,  5 },
            { 2012, 9, 26, ALIGNED_WEEK_OF_MONTH,         4 },
            { 2014, 9, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR,   5 },
            { 2012, 9, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR,   5 },
            { 2012, 9, 28, ALIGNED_WEEK_OF_YEAR,          36 },
            { 2014, 9, 28, ALIGNED_WEEK_OF_YEAR,          36 },

            // Year Day (month 13, day 29): the extra day at the end of the year, not part of any week
            { 2014, 13, 29, DAY_OF_WEEK,                   0 },
            { 2014, 13, 29, DAY_OF_MONTH,                  29 },
            { 2014, 13, 29, DAY_OF_YEAR,                   13 * 28 + 1 },      // 13 regular months + Year Day
            { 2012, 13, 29, DAY_OF_YEAR,                   13 * 28 + 1 + 1 },  // leap year: Leap Day adds 1 more
            { 2014, 13, 29, ALIGNED_DAY_OF_WEEK_IN_MONTH,  0 },
            { 2014, 13, 29, ALIGNED_WEEK_OF_MONTH,         0 },
            { 2014, 13, 29, ALIGNED_DAY_OF_WEEK_IN_YEAR,   0 },
            { 2014, 13, 29, ALIGNED_WEEK_OF_YEAR,          0 },
            { 2014, 13, 29, MONTH_OF_YEAR,                 13 },
            { 2014, 13, 29, PROLEPTIC_MONTH,               2014 * 13 + 13 - 1 },

            // Last regular day of month 6 in a leap year: 2012/06/28 (day before Leap Day)
            { 2012, 6, 28, DAY_OF_WEEK,                   7 },
            { 2012, 6, 28, DAY_OF_MONTH,                  28 },
            { 2012, 6, 28, DAY_OF_YEAR,                   6 * 28 },
            { 2012, 6, 28, ALIGNED_DAY_OF_WEEK_IN_MONTH,  7 },
            { 2012, 6, 28, ALIGNED_WEEK_OF_MONTH,         4 },
            { 2012, 6, 28, ALIGNED_DAY_OF_WEEK_IN_YEAR,   7 },
            { 2012, 6, 28, ALIGNED_WEEK_OF_YEAR,          24 },
            { 2012, 6, 28, MONTH_OF_YEAR,                 6 },
            { 2012, 6, 28, PROLEPTIC_MONTH,               2012 * 13 + 6 - 1 },

            // Leap Day (month 6, day 29): the intercalary day in a leap year, not part of any week
            { 2012, 6, 29, DAY_OF_WEEK,                   0 },
            { 2012, 6, 29, DAY_OF_MONTH,                  29 },
            { 2012, 6, 29, DAY_OF_YEAR,                   6 * 28 + 1 }, // 6 full months + Leap Day
            { 2012, 6, 29, ALIGNED_DAY_OF_WEEK_IN_MONTH,  0 },
            { 2012, 6, 29, ALIGNED_WEEK_OF_MONTH,         0 },
            { 2012, 6, 29, ALIGNED_DAY_OF_WEEK_IN_YEAR,   0 },
            { 2012, 6, 29, ALIGNED_WEEK_OF_YEAR,          0 },
            { 2012, 6, 29, MONTH_OF_YEAR,                 6 },
            { 2012, 6, 29, PROLEPTIC_MONTH,               2012 * 13 + 6 - 1 },

            // First day of month 7 immediately after Leap Day: 2012/07/01 (week resets to day 1)
            { 2012, 7, 1, DAY_OF_WEEK,                   1 },
            { 2012, 7, 1, DAY_OF_MONTH,                  1 },
            { 2012, 7, 1, DAY_OF_YEAR,                   6 * 28 + 2 }, // 6 full months + Leap Day + first day of month 7
            { 2012, 7, 1, ALIGNED_DAY_OF_WEEK_IN_MONTH,  1 },
            { 2012, 7, 1, ALIGNED_WEEK_OF_MONTH,         1 },
            { 2012, 7, 1, ALIGNED_DAY_OF_WEEK_IN_YEAR,   1 },
            { 2012, 7, 1, ALIGNED_WEEK_OF_YEAR,          25 },
            { 2012, 7, 1, MONTH_OF_YEAR,                 7 },
            { 2012, 7, 1, PROLEPTIC_MONTH,               2012 * 13 + 7 - 1 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_getLong")
    public void test_getLong(int year, int month, int dom, TemporalField field, long expected) {
        assertEquals(expected, InternationalFixedDate.of(year, month, dom).getLong(field));
    }
}
