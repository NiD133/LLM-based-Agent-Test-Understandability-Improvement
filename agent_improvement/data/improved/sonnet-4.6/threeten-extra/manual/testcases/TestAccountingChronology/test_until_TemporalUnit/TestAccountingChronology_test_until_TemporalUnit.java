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

public class TestAccountingChronology_test_until_TemporalUnit {

    // Accounting calendar: ends on Sunday nearest end of August,
    // 13 equal 4-week months, leap week appended to month 13.
    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    /**
     * Test data for {@link #test_until_TemporalUnit}.
     * Columns: year1, month1, dom1, year2, month2, dom2, unit, expected.
     *
     * Each row asserts that the number of whole {@code unit}s from
     * (year1, month1, dom1) to (year2, month2, dom2) equals {@code expected}.
     */
    public static Object[][] data_until() {
        return new Object[][] {
            // DAYS — same date, forward, backward
            { 2014, 5, 26,  2014, 5, 26,  DAYS,      0 },
            { 2014, 5, 26,  2014, 6,  4,  DAYS,      6 },
            { 2014, 5, 26,  2014, 5, 20,  DAYS,     -6 },

            // WEEKS — boundary: less than a full week vs. exactly one week
            { 2014, 5, 26,  2014, 5, 26,  WEEKS,     0 },
            { 2014, 5, 26,  2014, 6,  4,  WEEKS,     0 },
            { 2014, 5, 26,  2014, 6,  5,  WEEKS,     1 },

            // MONTHS — boundary: one day short of a month vs. exactly one month
            { 2014, 5, 26,  2014, 5, 26,  MONTHS,    0 },
            { 2014, 5, 26,  2014, 6, 25,  MONTHS,    0 },
            { 2014, 5, 26,  2014, 6, 26,  MONTHS,    1 },

            // YEARS — boundary: one day short of a year vs. exactly one year
            { 2014, 5, 26,  2014, 5, 26,  YEARS,     0 },
            { 2014, 5, 26,  2015, 5, 25,  YEARS,     0 },
            { 2014, 5, 26,  2015, 5, 26,  YEARS,     1 },

            // DECADES
            { 2014, 5, 26,  2014, 5, 26,  DECADES,   0 },
            { 2014, 5, 26,  2024, 5, 25,  DECADES,   0 },
            { 2014, 5, 26,  2024, 5, 26,  DECADES,   1 },

            // CENTURIES
            { 2014, 5, 26,  2014, 5, 26,  CENTURIES, 0 },
            { 2014, 5, 26,  2114, 5, 25,  CENTURIES, 0 },
            { 2014, 5, 26,  2114, 5, 26,  CENTURIES, 1 },

            // MILLENNIA
            { 2014, 5, 26,  2014, 5, 26,  MILLENNIA, 0 },
            { 2014, 5, 26,  3014, 5, 25,  MILLENNIA, 0 },
            { 2014, 5, 26,  3014, 5, 26,  MILLENNIA, 1 },

            // ERAS — same era (both BCE), then spanning BCE→CE
            { -2013, 5, 26,    0, 5, 26,  ERAS,      0 },
            { -2013, 5, 26, 2014, 5, 26,  ERAS,      1 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_until")
    public void test_until_TemporalUnit(
            int year1, int month1, int dom1,
            int year2, int month2, int dom2,
            TemporalUnit unit, long expected) {
        AccountingDate start = INSTANCE.date(year1, month1, dom1);
        AccountingDate end   = INSTANCE.date(year2, month2, dom2);
        assertEquals(expected, start.until(end, unit));
    }
}
