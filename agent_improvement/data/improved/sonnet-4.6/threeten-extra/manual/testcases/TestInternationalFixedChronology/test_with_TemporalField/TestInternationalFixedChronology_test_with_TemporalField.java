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
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.temporal.TemporalField;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_with_TemporalField {

    // Each row: year, month, dom, field, newValue, expectedYear, expectedMonth, expectedDom
    public static Object[][] data_with() {
        return new Object[][] {
            // --- Standard date: 2014-05-26 (a normal weekday in the middle of a regular month) ---
            { 2014, 5, 26, DAY_OF_WEEK,                    1,                   2014, 5,  22 },
            { 2014, 5, 26, DAY_OF_WEEK,                    5,                   2014, 5,  26 },
            { 2014, 5, 26, DAY_OF_MONTH,                   28,                  2014, 5,  28 },
            { 2014, 5, 26, DAY_OF_MONTH,                   26,                  2014, 5,  26 },
            { 2014, 5, 26, DAY_OF_YEAR,                    364,                 2014, 13, 28 },
            { 2014, 5, 26, DAY_OF_YEAR,                    138,                 2014, 5,  26 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH,  3,                   2014, 5,  24 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH,  5,                   2014, 5,  26 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH,          1,                   2014, 5,  5  },
            { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH,          4,                   2014, 5,  26 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR,   2,                   2014, 5,  23 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR,   5,                   2014, 5,  26 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR,           23,                  2014, 6,  19 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR,           20,                  2014, 5,  26 },
            { 2014, 5, 26, MONTH_OF_YEAR,                  4,                   2014, 4,  26 },
            { 2014, 5, 26, MONTH_OF_YEAR,                  5,                   2014, 5,  26 },
            { 2014, 5, 26, PROLEPTIC_MONTH,                2013 * 13 + 3 - 1,   2013, 3,  26 },
            { 2014, 5, 26, PROLEPTIC_MONTH,                2014 * 13 + 5 - 1,   2014, 5,  26 },
            { 2014, 5, 26, YEAR,                           2012,                2012, 5,  26 },
            { 2014, 5, 26, YEAR,                           2014,                2014, 5,  26 },
            { 2014, 5, 26, YEAR_OF_ERA,                    2012,                2012, 5,  26 },
            { 2014, 5, 26, YEAR_OF_ERA,                    2014,                2014, 5,  26 },
            { 2014, 5, 26, ERA,                            1,                   2014, 5,  26 },

            // --- Year Day (2014-13-29): the intercalary day at end of year; not part of any week ---
            // Setting aligned-week/day fields to 0 keeps it as Year Day; non-zero values map into the last month
            { 2014, 13, 29, ALIGNED_DAY_OF_WEEK_IN_MONTH, 0,   2014, 13, 29 },
            { 2014, 13, 29, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1,   2014, 13, 22 },
            { 2014, 13, 29, ALIGNED_DAY_OF_WEEK_IN_MONTH, 2,   2014, 13, 23 },
            { 2014, 13, 29, ALIGNED_DAY_OF_WEEK_IN_MONTH, 3,   2014, 13, 24 },
            { 2014, 13, 29, ALIGNED_DAY_OF_WEEK_IN_MONTH, 4,   2014, 13, 25 },
            { 2014, 13, 29, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5,   2014, 13, 26 },
            { 2014, 13, 29, ALIGNED_DAY_OF_WEEK_IN_MONTH, 6,   2014, 13, 27 },
            { 2014, 13, 29, ALIGNED_DAY_OF_WEEK_IN_MONTH, 7,   2014, 13, 28 },
            { 2014, 13, 29, ALIGNED_DAY_OF_WEEK_IN_YEAR,  0,   2014, 13, 29 },
            { 2014, 13, 29, ALIGNED_DAY_OF_WEEK_IN_YEAR,  1,   2014, 13, 22 },
            { 2014, 13, 29, ALIGNED_DAY_OF_WEEK_IN_YEAR,  2,   2014, 13, 23 },
            { 2014, 13, 29, ALIGNED_DAY_OF_WEEK_IN_YEAR,  3,   2014, 13, 24 },
            { 2014, 13, 29, ALIGNED_DAY_OF_WEEK_IN_YEAR,  4,   2014, 13, 25 },
            { 2014, 13, 29, ALIGNED_DAY_OF_WEEK_IN_YEAR,  5,   2014, 13, 26 },
            { 2014, 13, 29, ALIGNED_DAY_OF_WEEK_IN_YEAR,  6,   2014, 13, 27 },
            { 2014, 13, 29, ALIGNED_DAY_OF_WEEK_IN_YEAR,  7,   2014, 13, 28 },
            { 2014, 13, 29, ALIGNED_WEEK_OF_MONTH,        0,   2014, 13, 29 },
            { 2014, 13, 29, ALIGNED_WEEK_OF_MONTH,        3,   2014, 13, 15 },
            { 2014, 13, 29, ALIGNED_WEEK_OF_YEAR,         0,   2014, 13, 29 },
            { 2014, 13, 29, ALIGNED_WEEK_OF_YEAR,         3,   2014, 1,  15 },
            // DAY_OF_WEEK: 0 stays as Year Day; regular values move into the last week of month 13
            { 2014, 13, 29, DAY_OF_WEEK,                  0,   2014, 13, 29 },
            { 2014, 13, 28, DAY_OF_WEEK,                  1,   2014, 13, 22 },
            { 2014, 13, 28, DAY_OF_WEEK,                  2,   2014, 13, 23 },
            { 2014, 13, 28, DAY_OF_WEEK,                  3,   2014, 13, 24 },
            { 2014, 13, 28, DAY_OF_WEEK,                  4,   2014, 13, 25 },
            { 2014, 13, 28, DAY_OF_WEEK,                  5,   2014, 13, 26 },
            { 2014, 13, 28, DAY_OF_WEEK,                  6,   2014, 13, 27 },
            { 2014, 13, 28, DAY_OF_WEEK,                  7,   2014, 13, 28 },
            { 2014, 13, 29, DAY_OF_MONTH,                 1,   2014, 13, 1  },
            { 2014, 13, 29, DAY_OF_MONTH,                 3,   2014, 13, 3  },
            // Changing month from Year Day: day is clamped to the target month's max (28 for regular months)
            { 2014, 13, 29, MONTH_OF_YEAR,                1,   2014, 1,  28 },
            { 2014, 13, 29, MONTH_OF_YEAR,                13,  2014, 13, 29 },
            { 2014, 13, 29, MONTH_OF_YEAR,                2,   2014, 2,  28 },
            { 2014, 13, 29, YEAR,                         2014, 2014, 13, 29 },
            { 2014, 13, 29, YEAR,                         2013, 2013, 13, 29 },

            // --- End-of-month boundary: setting day-of-month on last day of a regular month ---
            { 2014, 3, 28, DAY_OF_MONTH,   1,   2014, 3,  1  },
            { 2014, 1, 28, DAY_OF_MONTH,   1,   2014, 1,  1  },
            { 2014, 3, 28, MONTH_OF_YEAR,  1,   2014, 1,  28 },
            // DAY_OF_YEAR 365 lands on Year Day (non-leap), 366 on Year Day (leap)
            { 2014, 3, 28, DAY_OF_YEAR,    365, 2014, 13, 29 },
            { 2012, 3, 28, DAY_OF_YEAR,    366, 2012, 13, 29 },

            // --- Leap Day (2012-06-29): intercalary day after month 6 in a leap year ---
            // Setting aligned-week/day fields to 0 keeps it as Leap Day; non-zero maps into month 6
            { 2012, 6, 29, ALIGNED_DAY_OF_WEEK_IN_MONTH, 0,   2012, 6,  29 },
            { 2012, 6, 29, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1,   2012, 6,  22 },
            { 2012, 6, 29, ALIGNED_DAY_OF_WEEK_IN_MONTH, 2,   2012, 6,  23 },
            { 2012, 6, 29, ALIGNED_DAY_OF_WEEK_IN_MONTH, 3,   2012, 6,  24 },
            { 2012, 6, 29, ALIGNED_DAY_OF_WEEK_IN_MONTH, 4,   2012, 6,  25 },
            { 2012, 6, 29, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5,   2012, 6,  26 },
            { 2012, 6, 29, ALIGNED_DAY_OF_WEEK_IN_MONTH, 6,   2012, 6,  27 },
            { 2012, 6, 29, ALIGNED_DAY_OF_WEEK_IN_MONTH, 7,   2012, 6,  28 },
            { 2012, 6, 29, ALIGNED_DAY_OF_WEEK_IN_YEAR,  0,   2012, 6,  29 },
            { 2012, 6, 29, ALIGNED_DAY_OF_WEEK_IN_YEAR,  1,   2012, 6,  22 },
            { 2012, 6, 29, ALIGNED_DAY_OF_WEEK_IN_YEAR,  2,   2012, 6,  23 },
            { 2012, 6, 29, ALIGNED_DAY_OF_WEEK_IN_YEAR,  3,   2012, 6,  24 },
            { 2012, 6, 29, ALIGNED_DAY_OF_WEEK_IN_YEAR,  4,   2012, 6,  25 },
            { 2012, 6, 29, ALIGNED_DAY_OF_WEEK_IN_YEAR,  5,   2012, 6,  26 },
            { 2012, 6, 29, ALIGNED_DAY_OF_WEEK_IN_YEAR,  6,   2012, 6,  27 },
            { 2012, 6, 29, ALIGNED_DAY_OF_WEEK_IN_YEAR,  7,   2012, 6,  28 },
            { 2012, 6, 29, ALIGNED_WEEK_OF_MONTH,        0,   2012, 6,  29 },
            { 2012, 6, 29, ALIGNED_WEEK_OF_MONTH,        3,   2012, 6,  15 },
            { 2012, 6, 29, ALIGNED_WEEK_OF_YEAR,         0,   2012, 6,  29 },
            { 2012, 6, 29, ALIGNED_WEEK_OF_YEAR,         3,   2012, 1,  15 },
            // Wrapping around year boundaries using ALIGNED_WEEK_OF_YEAR
            { 2012, 1,  1,  ALIGNED_WEEK_OF_YEAR,        52,  2012, 13, 22 },
            { 2012, 13, 28, ALIGNED_WEEK_OF_YEAR,        1,   2012, 1,  7  },
            { 2012, 6, 29, DAY_OF_WEEK,                  0,   2012, 6,  29 },
            { 2012, 6, 29, DAY_OF_WEEK,                  1,   2012, 6,  22 },
            { 2012, 6, 29, DAY_OF_WEEK,                  2,   2012, 6,  23 },
            { 2012, 6, 29, DAY_OF_WEEK,                  3,   2012, 6,  24 },
            { 2012, 6, 29, DAY_OF_WEEK,                  4,   2012, 6,  25 },
            { 2012, 6, 29, DAY_OF_WEEK,                  5,   2012, 6,  26 },
            { 2012, 6, 29, DAY_OF_WEEK,                  6,   2012, 6,  27 },
            { 2012, 6, 29, DAY_OF_WEEK,                  7,   2012, 6,  28 },
            { 2012, 6, 29, DAY_OF_MONTH,                 1,   2012, 6,  1  },
            { 2012, 6, 29, DAY_OF_MONTH,                 3,   2012, 6,  3  },
            // Changing month from Leap Day: day is clamped to target month's max (28 for non-month-6 months)
            { 2012, 6, 29, MONTH_OF_YEAR,                6,   2012, 6,  29 },
            { 2012, 6, 29, MONTH_OF_YEAR,                7,   2012, 7,  28 },
            { 2012, 6, 29, MONTH_OF_YEAR,                2,   2012, 2,  28 },
            // Changing year from Leap Day: day is clamped to 28 if target year is not a leap year
            { 2012, 6, 29, YEAR,                         2012, 2012, 6,  29 },
            { 2012, 6, 29, YEAR,                         2013, 2013, 6,  28 },
            { 2012, 6, 29, YEAR,                         2011, 2011, 6,  28 },
            { 2012, 6, 29, YEAR,                         2016, 2016, 6,  29 },
            // Setting DAY_OF_MONTH to 29 from a non-special date reaches Leap Day in a leap year
            { 2012, 6, 22, DAY_OF_MONTH,                 29,  2012, 6,  29 },

            // --- End-of-month boundary in leap year ---
            { 2012, 3, 28, DAY_OF_MONTH,   1,   2012, 3,  1  },
            { 2012, 1, 28, DAY_OF_MONTH,   1,   2012, 1,  1  },
            { 2012, 3, 28, MONTH_OF_YEAR,  1,   2012, 1,  28 },
            // DAY_OF_YEAR 169 in a leap year (2012) lands on Leap Day; same ordinal in non-leap (2013) lands on 2013-07-01
            { 2012, 3, 28, DAY_OF_YEAR,    169, 2012, 6,  29 },
            { 2013, 3, 28, DAY_OF_YEAR,    169, 2013, 7,  1  },
            // Cross-year: moving year while sitting on day 1 of month 7 in a leap year
            { 2013, 7, 1,  YEAR,           2012, 2012, 7,  1  },
        };
    }

    @ParameterizedTest
    @MethodSource("data_with")
    public void test_with_TemporalField(
            int year, int month, int dom,
            TemporalField field, long value,
            int expectedYear, int expectedMonth, int expectedDom) {
        assertEquals(
                InternationalFixedDate.of(expectedYear, expectedMonth, expectedDom),
                InternationalFixedDate.of(year, month, dom).with(field, value));
    }
}
