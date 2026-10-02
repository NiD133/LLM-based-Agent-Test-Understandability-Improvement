package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.CENTURIES;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.DECADES;
import static java.time.temporal.ChronoUnit.ERAS;
import static java.time.temporal.ChronoUnit.MILLENNIA;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.WEEKS;
import static java.time.temporal.ChronoUnit.YEARS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.temporal.TemporalUnit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestAccountingChronology_test_plus_TemporalUnit {

    // A 13-month accounting calendar ending on Sunday nearest end of August,
    // with the leap week appended to month 13.
    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    /**
     * Test data for {@link #test_plus_TemporalUnit}.
     * Each row: startYear, startMonth, startDay, amount, unit, expectedYear, expectedMonth, expectedDay.
     */
    public static Object[][] data_plus() {
        return new Object[][] {
            // ---- DAYS ----
            { 2014, 5, 26,  0, DAYS,     2014,  5, 26 },   // add zero days → no change
            { 2014, 5, 26,  8, DAYS,     2014,  6,  6 },   // add 8 days → cross month boundary
            { 2014, 5, 26, -3, DAYS,     2014,  5, 23 },   // subtract 3 days

            // ---- WEEKS ----
            { 2014, 5, 26,  0, WEEKS,    2014,  5, 26 },
            { 2014, 5, 26,  3, WEEKS,    2014,  6, 19 },
            { 2014, 5, 26, -5, WEEKS,    2014,  4, 19 },

            // ---- MONTHS ----
            { 2014, 5, 26,  0, MONTHS,   2014,  5, 26 },
            { 2014, 5, 26,  3, MONTHS,   2014,  8, 26 },
            { 2014, 5, 26, -5, MONTHS,   2013, 13, 26 },   // wrap back to previous year's month 13

            // ---- YEARS ----
            { 2014, 5, 26,  0, YEARS,    2014,  5, 26 },
            { 2014, 5, 26,  3, YEARS,    2017,  5, 26 },
            { 2014, 5, 26, -5, YEARS,    2009,  5, 26 },

            // ---- DECADES ----
            { 2014, 5, 26,  0, DECADES,  2014,  5, 26 },
            { 2014, 5, 26,  3, DECADES,  2044,  5, 26 },
            { 2014, 5, 26, -5, DECADES,  1964,  5, 26 },

            // ---- CENTURIES ----
            { 2014, 5, 26,  0, CENTURIES, 2014,  5, 26 },
            { 2014, 5, 26,  3, CENTURIES, 2314,  5, 26 },
            { 2014, 5, 26, -5, CENTURIES, 1514,  5, 26 },

            // ---- MILLENNIA ----
            { 2014, 5, 26,  0, MILLENNIA, 2014,     5, 26 },
            { 2014, 5, 26,  3, MILLENNIA, 5014,     5, 26 },
            { 2014, 5, 26, -5, MILLENNIA, 2014 - 5000, 5, 26 },

            // ---- ERAS ----
            { 2014, 5, 26, -1, ERAS,     -2013,  5, 26 },  // flip from CE to BCE
        };
    }

    @ParameterizedTest
    @MethodSource("data_plus")
    public void test_plus_TemporalUnit(
            int year, int month, int dom,
            long amount, TemporalUnit unit,
            int expectedYear, int expectedMonth, int expectedDom) {
        assertEquals(
                INSTANCE.date(expectedYear, expectedMonth, expectedDom),
                INSTANCE.date(year, month, dom).plus(amount, unit));
    }
}
