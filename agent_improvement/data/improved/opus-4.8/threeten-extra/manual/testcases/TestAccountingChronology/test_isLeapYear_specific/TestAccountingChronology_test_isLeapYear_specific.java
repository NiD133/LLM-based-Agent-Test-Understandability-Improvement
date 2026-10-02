package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DayOfWeek;
import java.time.Month;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link AccountingChronology#isLeapYear(long)} for an accounting calendar
 * that places its leap week in month 13.
 *
 * <p>In this calendar a leap year is one that contains the extra leap week, so
 * {@code isLeapYear} must return {@code true} only for those specific years and
 * {@code false} for every other year. The cases below check a contiguous run of
 * proleptic years around zero so both the leap and non-leap outcomes are covered.
 */
public class TestAccountingChronology_test_isLeapYear_specific {

    /**
     * Accounting calendar that ends on the Sunday nearest the end of August,
     * divides the year into thirteen 4-week months, and carries its leap week
     * in month 13.
     */
    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_isLeapYear_specific() {
        // The only leap years in the checked range (-6..8) are 6, 0 and -5.
        assertTrue(INSTANCE.isLeapYear(6), "year 6 should be a leap year");
        assertTrue(INSTANCE.isLeapYear(0), "year 0 should be a leap year");
        assertTrue(INSTANCE.isLeapYear(-5), "year -5 should be a leap year");

        // Every other year in the range is a common (non-leap) year.
        assertFalse(INSTANCE.isLeapYear(8), "year 8 should not be a leap year");
        assertFalse(INSTANCE.isLeapYear(7), "year 7 should not be a leap year");
        assertFalse(INSTANCE.isLeapYear(5), "year 5 should not be a leap year");
        assertFalse(INSTANCE.isLeapYear(4), "year 4 should not be a leap year");
        assertFalse(INSTANCE.isLeapYear(3), "year 3 should not be a leap year");
        assertFalse(INSTANCE.isLeapYear(2), "year 2 should not be a leap year");
        assertFalse(INSTANCE.isLeapYear(1), "year 1 should not be a leap year");
        assertFalse(INSTANCE.isLeapYear(-1), "year -1 should not be a leap year");
        assertFalse(INSTANCE.isLeapYear(-2), "year -2 should not be a leap year");
        assertFalse(INSTANCE.isLeapYear(-3), "year -3 should not be a leap year");
        assertFalse(INSTANCE.isLeapYear(-4), "year -4 should not be a leap year");
        assertFalse(INSTANCE.isLeapYear(-6), "year -6 should not be a leap year");
    }
}
