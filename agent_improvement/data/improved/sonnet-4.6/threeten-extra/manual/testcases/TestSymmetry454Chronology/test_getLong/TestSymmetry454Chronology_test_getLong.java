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

/**
 * Tests {@link Symmetry454Date#getLong(TemporalField)} for a variety of dates and temporal fields.
 *
 * <p>The Symmetry454 calendar has the following structure:
 * <ul>
 *   <li>12 months per year; months 1, 3, 4, 6, 7, 9, 10, 12 have 28 days (4 weeks);
 *       months 2, 5, 8, 11 have 35 days (5 weeks).</li>
 *   <li>Normal years: 364 days (52 weeks); leap years: 371 days (53 weeks) — the leap week
 *       is appended to December.</li>
 *   <li>Each quarter spans (4+5+4)*7 = 91 days / 13 weeks.</li>
 * </ul>
 */
@SuppressWarnings({"static-method"})
public class TestSymmetry454Chronology_test_getLong {

    /**
     * Provides test cases as {year, month, dayOfMonth, field, expectedValue}.
     *
     * <p>Cases are grouped by reference date. Arithmetic expressions are left expanded
     * so readers can verify the calendar arithmetic directly.
     */
    public static Object[][] data_getLong() {
        return new Object[][] {

            // --- 2014-05-26: a regular Friday in month 5 (a 35-day long month) ---
            // Month layout: months 1-4 contribute 28+35+28+28 = 119 days; day 26 of month 5 adds 26.
            { 2014, 5, 26, DAY_OF_WEEK,                     5 },
            { 2014, 5, 26, DAY_OF_MONTH,                    26 },
            { 2014, 5, 26, DAY_OF_YEAR,                     28 + 35 + 28 + 28 + 26 },   // = 145
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH,    5 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH,           4 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR,     5 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR,            4 + 5 + 4 + 4 + 4 },        // weeks in months 1-5 up to week 4 = 21
            { 2014, 5, 26, MONTH_OF_YEAR,                   5 },
            { 2014, 5, 26, PROLEPTIC_MONTH,                 2014 * 12 + 5 - 1 },        // = 24172
            { 2014, 5, 26, YEAR,                            2014 },
            { 2014, 5, 26, ERA,                             1 },                         // CE = 1

            // --- ERA is 1 (CE) for any positive proleptic year ---
            { 1,    5, 8,  ERA,                             1 },

            // --- 2012-09-26: day 5 of week 4 in month 9, end of the third quarter ---
            // Three full quarters = 3 * (4+5+4) * 7 days; day 26 of month 9 is 5 days before
            // the quarter boundary (91*3 = 273, day 26 of month 9 = 273 - 2 = 271).
            { 2012, 9, 26, DAY_OF_WEEK,                     5 },
            { 2012, 9, 26, DAY_OF_YEAR,                     3 * (4 + 5 + 4) * 7 - 2 }, // = 271
            { 2012, 9, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH,    5 },
            { 2012, 9, 26, ALIGNED_WEEK_OF_MONTH,           4 },
            { 2012, 9, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR,     5 },
            { 2012, 9, 26, ALIGNED_WEEK_OF_YEAR,            3 * (4 + 5 + 4) },          // = 39

            // --- 2015-12-35: last day of a leap year (the 53rd week / leap-week Saturday) ---
            // 2015 is a leap year: December gets an extra week, making it a 35-day month.
            // DAY_OF_YEAR: 4 full quarters (4*(4+5+4)*7 = 364) plus the 7-day leap week = 371.
            { 2015, 12, 35, DAY_OF_WEEK,                    7 },
            { 2015, 12, 35, DAY_OF_MONTH,                   35 },
            { 2015, 12, 35, DAY_OF_YEAR,                    4 * (4 + 5 + 4) * 7 + 7 }, // = 371
            { 2015, 12, 35, ALIGNED_DAY_OF_WEEK_IN_MONTH,   7 },
            { 2015, 12, 35, ALIGNED_WEEK_OF_MONTH,          5 },
            { 2015, 12, 35, ALIGNED_DAY_OF_WEEK_IN_YEAR,    7 },
            { 2015, 12, 35, ALIGNED_WEEK_OF_YEAR,           53 },
            { 2015, 12, 35, MONTH_OF_YEAR,                  12 },
            { 2015, 12, 35, PROLEPTIC_MONTH,                2016 * 12 - 1 },            // = 24191
        };
    }

    @ParameterizedTest
    @MethodSource("data_getLong")
    public void test_getLong(int year, int month, int dom, TemporalField field, long expected) {
        assertEquals(expected, Symmetry454Date.of(year, month, dom).getLong(field));
    }
}
