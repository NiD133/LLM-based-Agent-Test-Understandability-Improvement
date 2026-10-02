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

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_getLong {

    // Test date: Pax 2014-05-26 (year 2014, month 5, day 26)
    // In the Pax calendar, months 1-12 have 28 days; leap years insert a 7-day month 13
    // before the regular month 13 (which becomes month 14).
    // DAY_OF_YEAR for month 5, day 26 = 4 * 28 + 26 = 138
    public static Object[][] data_getLong() {
        return new Object[][] {
            // DAY_OF_WEEK: 2014-05-26 falls on day 4 of the week (Thursday-like)
            { 2014, 5, 26, DAY_OF_WEEK,                    4 },
            // DAY_OF_MONTH: straightforward day within month
            { 2014, 5, 26, DAY_OF_MONTH,                   26 },
            // DAY_OF_YEAR: months 1-4 each have 28 days, plus 26 days in month 5
            { 2014, 5, 26, DAY_OF_YEAR,                    28 + 28 + 28 + 28 + 26 },
            // ALIGNED_DAY_OF_WEEK_IN_MONTH: day 26 = week 4 * 7 - 2 → position 5 in aligned week
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH,   5 },
            // ALIGNED_WEEK_OF_MONTH: day 26 falls in the 4th aligned week (days 22-28)
            { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH,          4 },
            // ALIGNED_DAY_OF_WEEK_IN_YEAR: day 138 of year → (138-1) % 7 + 1 = 5
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR,    5 },
            // ALIGNED_WEEK_OF_YEAR: day 138 → ceiling(138/7) = 20
            { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR,           20 },
            // MONTH_OF_YEAR: straightforward month number
            { 2014, 5, 26, MONTH_OF_YEAR,                  5 },
            // PROLEPTIC_MONTH: total months from epoch to Pax 2014-05, accounting for leap years
            // (leap years add an extra month 13, shifting the regular month 13 to 14)
            { 2014, 5, 26, PROLEPTIC_MONTH,                2014 * 13 + 20 * 18 - 5 + 2 + 5 - 1 },
            // YEAR: calendar year
            { 2014, 5, 26, YEAR,                           2014 },
            // ERA: CE era = 1, BCE era = 0
            { 2014, 5, 26, ERA,                            1 },
            { 1,    6,  8, ERA,                            1 },
            { 0,    6,  8, ERA,                            0 },
            // ISO day-of-week via WeekFields: same value as DAY_OF_WEEK for this date
            { 2014, 5, 26, WeekFields.ISO.dayOfWeek(),    4 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_getLong")
    public void test_getLong(int year, int month, int dom, TemporalField field, long expected) {
        assertEquals(expected, PaxDate.of(year, month, dom).getLong(field));
    }
}
