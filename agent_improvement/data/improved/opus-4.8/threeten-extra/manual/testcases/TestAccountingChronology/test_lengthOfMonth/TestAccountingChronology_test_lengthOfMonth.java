package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Month;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link AccountingDate#lengthOfMonth()} for an Accounting chronology that
 * divides the year into thirteen even months of four weeks (28 days each), with
 * the leap-week added to month 13.
 */
public class TestAccountingChronology_test_lengthOfMonth {

    /**
     * Accounting chronology under test: year ends on the Sunday nearest the end of
     * August, divided into THIRTEEN_EVEN_MONTHS_OF_4_WEEKS, leap-week in month 13.
     */
    private static final AccountingChronology ACCOUNTING_13_MONTHS =
            new AccountingChronologyBuilder()
                    .endsOn(DayOfWeek.SUNDAY)
                    .nearestEndOf(Month.AUGUST)
                    .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
                    .leapWeekInMonth(13)
                    .toChronology();

    /**
     * Each case is {year, month, expectedLengthOfMonth}.
     * <p>
     * Every month normally has 28 days (four weeks). The exception is month 13 of a
     * leap year (2012), which gains the leap-week and so has 35 days; in non-leap
     * years (2013-2016) month 13 is back to 28 days.
     */
    public static Object[][] data_lengthOfMonth() {
        return new Object[][] {
            // 2012 is a leap year: months 1-12 are 28 days, month 13 is 35 days.
            {2012, 1, 28},
            {2012, 2, 28},
            {2012, 3, 28},
            {2012, 4, 28},
            {2012, 5, 28},
            {2012, 6, 28},
            {2012, 7, 28},
            {2012, 8, 28},
            {2012, 9, 28},
            {2012, 10, 28},
            {2012, 11, 28},
            {2012, 12, 28},
            {2012, 13, 35},
            // Non-leap years: month 13 is back to the standard 28 days.
            {2013, 13, 28},
            {2014, 13, 28},
            {2015, 13, 28},
            {2016, 13, 28},
        };
    }

    @ParameterizedTest
    @MethodSource("data_lengthOfMonth")
    public void test_lengthOfMonth(int year, int month, int expectedLength) {
        assertEquals(expectedLength, ACCOUNTING_13_MONTHS.date(year, month, 1).lengthOfMonth());
    }
}
