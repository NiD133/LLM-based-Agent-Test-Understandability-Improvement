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

public class TestAccountingChronology_test_minus_TemporalUnit {

    private static final AccountingChronology INSTANCE =
            new AccountingChronologyBuilder()
                    .endsOn(DayOfWeek.SUNDAY)
                    .nearestEndOf(Month.AUGUST)
                    .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
                    .leapWeekInMonth(13)
                    .toChronology();

    public static Object[][] data_plus() {
        return new Object[][] {
                {2014, 5, 26, 0, DAYS, 2014, 5, 26},
                {2014, 5, 26, 8, DAYS, 2014, 6, 6},
                {2014, 5, 26, -3, DAYS, 2014, 5, 23},
                {2014, 5, 26, 0, WEEKS, 2014, 5, 26},
                {2014, 5, 26, 3, WEEKS, 2014, 6, 19},
                {2014, 5, 26, -5, WEEKS, 2014, 4, 19},
                {2014, 5, 26, 0, MONTHS, 2014, 5, 26},
                {2014, 5, 26, 3, MONTHS, 2014, 8, 26},
                {2014, 5, 26, -5, MONTHS, 2013, 13, 26},
                {2014, 5, 26, 0, YEARS, 2014, 5, 26},
                {2014, 5, 26, 3, YEARS, 2017, 5, 26},
                {2014, 5, 26, -5, YEARS, 2009, 5, 26},
                {2014, 5, 26, 0, DECADES, 2014, 5, 26},
                {2014, 5, 26, 3, DECADES, 2044, 5, 26},
                {2014, 5, 26, -5, DECADES, 1964, 5, 26},
                {2014, 5, 26, 0, CENTURIES, 2014, 5, 26},
                {2014, 5, 26, 3, CENTURIES, 2314, 5, 26},
                {2014, 5, 26, -5, CENTURIES, 1514, 5, 26},
                {2014, 5, 26, 0, MILLENNIA, 2014, 5, 26},
                {2014, 5, 26, 3, MILLENNIA, 5014, 5, 26},
                {2014, 5, 26, -5, MILLENNIA, 2014 - 5000, 5, 26},
                {2014, 5, 26, -1, ERAS, -2013, 5, 26},
        };
    }

    @ParameterizedTest
    @MethodSource("data_plus")
    public void test_minus_TemporalUnit(
            int expectedYear,
            int expectedMonth,
            int expectedDayOfMonth,
            long amountToSubtract,
            TemporalUnit unit,
            int startYear,
            int startMonth,
            int startDayOfMonth) {

        assertEquals(
                INSTANCE.date(expectedYear, expectedMonth, expectedDayOfMonth),
                INSTANCE.date(startYear, startMonth, startDayOfMonth).minus(amountToSubtract, unit));
    }
}
