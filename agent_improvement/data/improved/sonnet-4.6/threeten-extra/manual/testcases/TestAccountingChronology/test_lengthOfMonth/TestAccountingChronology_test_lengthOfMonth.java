package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Month;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestAccountingChronology_test_lengthOfMonth {

    // A 13-month accounting calendar where every regular month has 4 weeks (28 days),
    // and the leap week is appended to month 13, making it 35 days in a leap year.
    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    /**
     * Provides (year, month, expectedLength) triples.
     * Months 1-12 are always 28 days; month 13 is 35 days in a leap year, 28 otherwise.
     */
    public static Object[][] data_lengthOfMonth() {
        return new Object[][] {
            // Leap year 2012: months 1-12 have 28 days, month 13 has 35 days
            { 2012,  1, 28 },
            { 2012,  2, 28 },
            { 2012,  3, 28 },
            { 2012,  4, 28 },
            { 2012,  5, 28 },
            { 2012,  6, 28 },
            { 2012,  7, 28 },
            { 2012,  8, 28 },
            { 2012,  9, 28 },
            { 2012, 10, 28 },
            { 2012, 11, 28 },
            { 2012, 12, 28 },
            { 2012, 13, 35 },  // leap year: month 13 gets the extra week
            // Non-leap years: month 13 has only 28 days
            { 2013, 13, 28 },
            { 2014, 13, 28 },
            { 2015, 13, 28 },
            { 2016, 13, 28 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_lengthOfMonth")
    public void test_lengthOfMonth(int year, int month, int length) {
        assertEquals(length, INSTANCE.date(year, month, 1).lengthOfMonth());
    }
}
