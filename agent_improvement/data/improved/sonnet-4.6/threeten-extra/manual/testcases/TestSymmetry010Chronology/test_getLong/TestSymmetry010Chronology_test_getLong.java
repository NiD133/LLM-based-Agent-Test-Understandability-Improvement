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

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_getLong {

    /**
     * Test data for getLong(), covering three representative dates:
     *
     *  - 2014-05-26: a normal year (364 days), the 26th day of May (month 5, a 31-day long month).
     *  - 2012-09-26: Q3 boundary case; September is the 3rd month of Q3 and sits at the 3-quarter mark.
     *  - 2015-12-37: a leap year (371 days), the 37th day of December (the leap week adds days 31-37).
     *
     * Calendar arithmetic used:
     *  - A normal Sym010 year has 4 quarters of 91 days each (30+31+30) = 364 days = 52 weeks.
     *  - A leap year appends a 7-day leap week to December, giving 371 days = 53 weeks.
     *  - ALIGNED_WEEK_OF_YEAR counts 7-day weeks from the start of the year (week 1 = days 1-7).
     *  - ALIGNED_WEEK_OF_MONTH counts 7-day weeks from the start of the month (week 1 = days 1-7).
     */
    public static Object[][] data_getLong() {
        // Quarter/week dimensions used in expected values:
        //   One quarter = (4+5+4) weeks = 13 weeks = 91 days
        //   Normal year  = 4 * 13 weeks = 52 weeks = 364 days
        //   Leap year    = 52 weeks + 1 leap week = 53 weeks = 371 days

        return new Object[][] {
            // --- Scenario 1: 2014-05-26, normal year, May is a 31-day (long) month -----------
            // Day-of-year: Q1(91) + Apr(30) + 26 days into May = 147
            { 2014, 5, 26, DAY_OF_WEEK,                   2 },
            { 2014, 5, 26, DAY_OF_MONTH,                 26 },
            { 2014, 5, 26, DAY_OF_YEAR,     30 + 31 + 30 + 30 + 26 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH,  5 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH,         4 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR,   7 },
            // Aligned week of year: weeks in Q1(4+5+4) + weeks into Q2 so far (4+4) = 21
            { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR,  4 + 5 + 4 + 4 + 4 },
            { 2014, 5, 26, MONTH_OF_YEAR,                 5 },
            { 2014, 5, 26, PROLEPTIC_MONTH,    2014 * 12 + 5 - 1 },
            { 2014, 5, 26, YEAR,                       2014 },
            { 2014, 5, 26, ERA,                           1 },

            // --- Scenario 2: 0001-05-08, year 1 CE (earliest representable CE year) ----------
            { 1,    5,  8, ERA,                           1 },

            // --- Scenario 3: 2012-09-26, normal year, September ends Q3 ---------------------
            // Day-of-year: 3 full quarters (3 * 91 = 273) minus 4 remaining days = 269
            { 2012, 9, 26, DAY_OF_WEEK,                   1 },
            { 2012, 9, 26, DAY_OF_YEAR,       3 * (4 + 5 + 4) * 7 - 4 },
            { 2012, 9, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH,  5 },
            { 2012, 9, 26, ALIGNED_WEEK_OF_MONTH,         4 },
            { 2012, 9, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR,   3 },
            // Aligned week of year: exactly 3 complete quarters = 3 * 13 = 39
            { 2012, 9, 26, ALIGNED_WEEK_OF_YEAR,  3 * (4 + 5 + 4) },

            // --- Scenario 4: 2015-12-37, leap year, last day of the appended leap week -------
            // Day-of-year: 4 normal quarters (364) + full 7-day leap week = 371
            { 2015, 12, 37, DAY_OF_WEEK,                  5 },
            { 2015, 12, 37, DAY_OF_MONTH,                37 },
            { 2015, 12, 37, DAY_OF_YEAR,     4 * (4 + 5 + 4) * 7 + 7 },
            { 2015, 12, 37, ALIGNED_DAY_OF_WEEK_IN_MONTH, 2 },
            { 2015, 12, 37, ALIGNED_WEEK_OF_MONTH,        6 },
            { 2015, 12, 37, ALIGNED_DAY_OF_WEEK_IN_YEAR,  7 },
            { 2015, 12, 37, ALIGNED_WEEK_OF_YEAR,        53 },
            { 2015, 12, 37, MONTH_OF_YEAR,               12 },
            // Proleptic month: year 2015 has 12 months; month 12 of 2015 = index (2016*12 - 1)
            { 2015, 12, 37, PROLEPTIC_MONTH,   2016 * 12 - 1 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_getLong")
    public void test_getLong(int year, int month, int dom, TemporalField field, long expected) {
        assertEquals(expected, Symmetry010Date.of(year, month, dom).getLong(field));
    }
}
