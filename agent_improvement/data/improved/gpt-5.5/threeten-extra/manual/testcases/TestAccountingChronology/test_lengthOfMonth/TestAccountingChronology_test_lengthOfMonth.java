package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Month;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestAccountingChronology_test_lengthOfMonth {

    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    public static Object[][] data_lengthOfMonth() {
        return new Object[][] {
                { 2012, 1, 28 },
                { 2012, 2, 28 },
                { 2012, 3, 28 },
                { 2012, 4, 28 },
                { 2012, 5, 28 },
                { 2012, 6, 28 },
                { 2012, 7, 28 },
                { 2012, 8, 28 },
                { 2012, 9, 28 },
                { 2012, 10, 28 },
                { 2012, 11, 28 },
                { 2012, 12, 28 },
                { 2012, 13, 35 },
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
