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
        assertFalse(INSTANCE.isLeapYear(8));
        assertFalse(INSTANCE.isLeapYear(7));
        assertTrue(INSTANCE.isLeapYear(6));
        assertFalse(INSTANCE.isLeapYear(5));
        assertFalse(INSTANCE.isLeapYear(4));
        assertFalse(INSTANCE.isLeapYear(3));
        assertFalse(INSTANCE.isLeapYear(2));
        assertFalse(INSTANCE.isLeapYear(1));
        assertTrue(INSTANCE.isLeapYear(0));
        assertFalse(INSTANCE.isLeapYear(-1));
        assertFalse(INSTANCE.isLeapYear(-2));
        assertFalse(INSTANCE.isLeapYear(-3));
        assertFalse(INSTANCE.isLeapYear(-4));
        assertTrue(INSTANCE.isLeapYear(-5));
        assertFalse(INSTANCE.isLeapYear(-6));
    }
}
