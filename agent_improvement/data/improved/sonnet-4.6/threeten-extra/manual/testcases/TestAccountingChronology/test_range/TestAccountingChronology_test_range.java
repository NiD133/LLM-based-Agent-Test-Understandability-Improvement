package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestAccountingChronology_test_range {

    // 13-month accounting year: ends on the nearest Sunday to end of August,
    // divided into 13 equal 4-week months, with the leap week appended to month 13.
    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    /**
     * Parameters: (year, month, dayOfMonth, field, expectedMin, expectedMax).
     *
     * Covers three temporal fields across leap and non-leap years:
     *  - DAY_OF_MONTH  : 1–28 for regular months; 1–35 for the leap month (month 13)
     *  - DAY_OF_YEAR   : 1–371 in a leap year; 1–364 in a standard year
     *  - ALIGNED_WEEK_OF_MONTH : 1–4 for regular months; 1–5 for the leap month
     */
    public static Object[][] data_ranges() {
        return new Object[][] {
            // --- Leap year 2012 ---
            // Regular months 1–12 each have exactly 28 days
            { 2012,  1, 23, DAY_OF_MONTH,          1, 28 },
            { 2012,  2, 23, DAY_OF_MONTH,          1, 28 },
            { 2012,  3, 23, DAY_OF_MONTH,          1, 28 },
            { 2012,  4, 23, DAY_OF_MONTH,          1, 28 },
            { 2012,  5, 23, DAY_OF_MONTH,          1, 28 },
            { 2012,  6, 23, DAY_OF_MONTH,          1, 28 },
            { 2012,  7, 23, DAY_OF_MONTH,          1, 28 },
            { 2012,  8, 23, DAY_OF_MONTH,          1, 28 },
            { 2012,  9, 23, DAY_OF_MONTH,          1, 28 },
            { 2012, 10, 23, DAY_OF_MONTH,          1, 28 },
            { 2012, 11, 23, DAY_OF_MONTH,          1, 28 },
            { 2012, 12, 23, DAY_OF_MONTH,          1, 28 },
            // Leap month 13 gains an extra week → 35 days
            { 2012, 13, 23, DAY_OF_MONTH,          1, 35 },
            // Leap year has 371 days in total
            { 2012,  1, 23, DAY_OF_YEAR,           1, 371 },
            // Regular month has 4 aligned weeks
            { 2012, 12, 23, ALIGNED_WEEK_OF_MONTH, 1,  4 },
            // Leap month 13 spans 5 aligned weeks
            { 2012, 13, 23, ALIGNED_WEEK_OF_MONTH, 1,  5 },

            // --- Non-leap year 2013 ---
            // Regular months still have 4 aligned weeks
            { 2013,  1, 23, ALIGNED_WEEK_OF_MONTH, 1,  4 },

            // --- Non-leap year 2011 ---
            // Month 13 has only 28 days (no leap week)
            { 2011, 13, 23, DAY_OF_MONTH,          1, 28 },
            // Standard year has 364 days in total
            { 2011, 13, 23, DAY_OF_YEAR,           1, 364 },
            // Month 13 has 4 aligned weeks (no leap week)
            { 2011, 13, 23, ALIGNED_WEEK_OF_MONTH, 1,  4 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_ranges")
    public void test_range(int year, int month, int dom, TemporalField field, int expectedMin, int expectedMax) {
        assertEquals(ValueRange.of(expectedMin, expectedMax), INSTANCE.date(year, month, dom).range(field));
    }
}
