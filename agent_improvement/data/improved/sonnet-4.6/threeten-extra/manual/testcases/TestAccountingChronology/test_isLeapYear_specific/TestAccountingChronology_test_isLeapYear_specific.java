package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DayOfWeek;
import java.time.Month;

import org.junit.jupiter.api.Test;

public class TestAccountingChronology_test_isLeapYear_specific {

    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    @Test
    public void test_isLeapYear_specific() {
        // Accounting leap years occur roughly every 5-6 years when the year spans 53 weeks (371 days).
        // The pattern here: years 6, 0, and -5 are leap years; all others in this range are not.

        assertFalse(INSTANCE.isLeapYear(8),  "Year 8 is not a leap year");
        assertFalse(INSTANCE.isLeapYear(7),  "Year 7 is not a leap year");
        assertTrue( INSTANCE.isLeapYear(6),  "Year 6 is a leap year");
        assertFalse(INSTANCE.isLeapYear(5),  "Year 5 is not a leap year");
        assertFalse(INSTANCE.isLeapYear(4),  "Year 4 is not a leap year");
        assertFalse(INSTANCE.isLeapYear(3),  "Year 3 is not a leap year");
        assertFalse(INSTANCE.isLeapYear(2),  "Year 2 is not a leap year");
        assertFalse(INSTANCE.isLeapYear(1),  "Year 1 is not a leap year");
        assertTrue( INSTANCE.isLeapYear(0),  "Year 0 is a leap year");
        assertFalse(INSTANCE.isLeapYear(-1), "Year -1 is not a leap year");
        assertFalse(INSTANCE.isLeapYear(-2), "Year -2 is not a leap year");
        assertFalse(INSTANCE.isLeapYear(-3), "Year -3 is not a leap year");
        assertFalse(INSTANCE.isLeapYear(-4), "Year -4 is not a leap year");
        assertTrue( INSTANCE.isLeapYear(-5), "Year -5 is a leap year");
        assertFalse(INSTANCE.isLeapYear(-6), "Year -6 is not a leap year");
    }
}
