package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Month;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestAccountingChronology_test_badDates {

    // Accounting chronology: ends on Sunday nearest end of August,
    // divided into 13 equal months of 4 weeks (28 days each),
    // with the leap week appended to month 13.
    // In a leap year (e.g. 2012), month 13 has 35 days; in a non-leap year (e.g. 2011) it has 28.
    private static final AccountingChronology INSTANCE =
            new AccountingChronologyBuilder()
                    .endsOn(DayOfWeek.SUNDAY)
                    .nearestEndOf(Month.AUGUST)
                    .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
                    .leapWeekInMonth(13)
                    .toChronology();

    public static Object[][] data_badDates() {
        return new Object[][] {
            // Invalid month numbers
            { 2012,  0,  0 },   // month 0 with day 0: both out of range
            { 2012, -1,  1 },   // negative month
            { 2012,  0,  1 },   // month 0
            { 2012, 14,  1 },   // month 14 exceeds the 13-month year
            { 2012, 15,  1 },   // month 15 exceeds the 13-month year

            // Invalid day numbers in regular months (months 1–12 always have exactly 28 days)
            { 2012,  1, -1 },   // negative day
            { 2012,  1,  0 },   // day 0
            { 2012,  1, 29 },   // day 29 exceeds the 28-day month

            // Invalid day numbers in month 13 during a leap year (2012 is a leap year: 35 days)
            { 2012, 13, -1 },   // negative day
            { 2012, 13,  0 },   // day 0
            { 2012, 13, 36 },   // day 36 exceeds the 35-day leap month
            { 2012, 13, 37 },   // day 37 exceeds the 35-day leap month
            { 2012, 13, 38 },   // day 38 exceeds the 35-day leap month

            // Invalid day numbers in month 13 during a non-leap year (2011: only 28 days)
            { 2011, 13, -1 },   // negative day
            { 2011, 13,  0 },   // day 0
            { 2011, 13, 29 },   // days 29–35 all exceed the 28-day non-leap month 13
            { 2011, 13, 30 },
            { 2011, 13, 31 },
            { 2011, 13, 32 },
            { 2011, 13, 33 },
            { 2011, 13, 34 },
            { 2011, 13, 35 },

            // Day 29 is invalid in every non-leap-week month (months 1–12 always have 28 days)
            { 2012,  2, 29 },
            { 2012,  3, 29 },
            { 2012,  4, 29 },
            { 2012,  5, 29 },
            { 2012,  6, 29 },
            { 2012,  7, 29 },
            { 2012,  8, 29 },
            { 2012,  9, 29 },
            { 2012, 10, 29 },
            { 2012, 11, 29 },
            { 2012, 12, 29 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_badDates")
    public void test_badDates(int year, int month, int dom) {
        assertThrows(DateTimeException.class, () -> INSTANCE.date(year, month, dom));
    }
}
