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

/**
 * Tests {@link AccountingChronology#range(TemporalField)} as seen through a date's
 * {@code range(field)} method.
 *
 * <p>The chronology under test uses a 13-month, 4-week-per-month division whose leap
 * week falls in month 13, so a normal month spans 28 days (4 weeks) while month 13
 * spans 35 days in a leap year and 28 days otherwise.
 */
public class TestAccountingChronology_test_range {

    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    /**
     * Each row is: {year, month, dayOfMonth, field, expectedRangeMin, expectedRangeMax}.
     * The date locates which month/year is being queried; the field selects which range
     * the chronology should report for that date.
     */
    public static Object[][] data_ranges() {
        return new Object[][] {
            // DAY_OF_MONTH: every regular month of leap year 2012 holds 28 days...
            { 2012, 1, 23, DAY_OF_MONTH, 1, 28 },
            { 2012, 2, 23, DAY_OF_MONTH, 1, 28 },
            { 2012, 3, 23, DAY_OF_MONTH, 1, 28 },
            { 2012, 4, 23, DAY_OF_MONTH, 1, 28 },
            { 2012, 5, 23, DAY_OF_MONTH, 1, 28 },
            { 2012, 6, 23, DAY_OF_MONTH, 1, 28 },
            { 2012, 7, 23, DAY_OF_MONTH, 1, 28 },
            { 2012, 8, 23, DAY_OF_MONTH, 1, 28 },
            { 2012, 9, 23, DAY_OF_MONTH, 1, 28 },
            { 2012, 10, 23, DAY_OF_MONTH, 1, 28 },
            { 2012, 11, 23, DAY_OF_MONTH, 1, 28 },
            { 2012, 12, 23, DAY_OF_MONTH, 1, 28 },
            // ...but month 13 of leap year 2012 absorbs the leap week, so it holds 35 days.
            { 2012, 13, 23, DAY_OF_MONTH, 1, 35 },

            // DAY_OF_YEAR: leap year 2012 has 371 days (13 * 28 + 7 leap days).
            { 2012, 1, 23, DAY_OF_YEAR, 1, 371 },

            // ALIGNED_WEEK_OF_MONTH: 4 weeks per regular month, 5 in the leap month 13.
            { 2012, 12, 23, ALIGNED_WEEK_OF_MONTH, 1, 4 },
            { 2012, 13, 23, ALIGNED_WEEK_OF_MONTH, 1, 5 },
            { 2013, 1, 23, ALIGNED_WEEK_OF_MONTH, 1, 4 },

            // Non-leap year 2011: month 13 stays at 28 days, the year has 364 days.
            { 2011, 13, 23, DAY_OF_MONTH, 1, 28 },
            { 2011, 13, 23, DAY_OF_YEAR, 1, 364 },
            { 2011, 13, 23, ALIGNED_WEEK_OF_MONTH, 1, 4 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_ranges")
    public void test_range(int year, int month, int dom, TemporalField field, int expectedMin, int expectedMax) {
        assertEquals(ValueRange.of(expectedMin, expectedMax), INSTANCE.date(year, month, dom).range(field));
    }
}
