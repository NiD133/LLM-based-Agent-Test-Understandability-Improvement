package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_YEAR;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.ERA;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.PROLEPTIC_MONTH;
import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestAccountingChronology_test_with_TemporalField {

    private static final AccountingChronology ACCOUNTING_13_MONTH_YEAR =
            new AccountingChronologyBuilder()
                    .endsOn(DayOfWeek.SUNDAY)
                    .nearestEndOf(Month.AUGUST)
                    .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
                    .leapWeekInMonth(13)
                    .toChronology();

    public static Object[][] data_with() {
        return new Object[][] {
                withFieldChanged(2014, 5, 26, DAY_OF_WEEK, 3, 2014, 5, 24),
                withFieldChanged(2014, 5, 26, DAY_OF_WEEK, 5, 2014, 5, 26),
                withFieldChanged(2014, 5, 26, DAY_OF_MONTH, 28, 2014, 5, 28),
                withFieldChanged(2014, 5, 26, DAY_OF_MONTH, 26, 2014, 5, 26),
                withFieldChanged(2014, 5, 26, DAY_OF_YEAR, 364, 2014, 13, 28),
                withFieldChanged(2014, 5, 26, DAY_OF_YEAR, 138, 2014, 5, 26),
                withFieldChanged(2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 3, 2014, 5, 24),
                withFieldChanged(2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5, 2014, 5, 26),
                withFieldChanged(2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 1, 2014, 5, 5),
                withFieldChanged(2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 4, 2014, 5, 26),
                withFieldChanged(2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 3, 2014, 5, 24),
                withFieldChanged(2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 5, 2014, 5, 26),
                withFieldChanged(2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 23, 2014, 6, 19),
                withFieldChanged(2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 20, 2014, 5, 26),
                withFieldChanged(2014, 5, 26, MONTH_OF_YEAR, 7, 2014, 7, 26),
                withFieldChanged(2014, 5, 26, MONTH_OF_YEAR, 5, 2014, 5, 26),
                withFieldChanged(2014, 5, 26, PROLEPTIC_MONTH, 2013 * 13 + 3 - 1, 2013, 3, 26),
                withFieldChanged(2014, 5, 26, PROLEPTIC_MONTH, 2014 * 13 + 5 - 1, 2014, 5, 26),
                withFieldChanged(2014, 5, 26, YEAR, 2012, 2012, 5, 26),
                withFieldChanged(2014, 5, 26, YEAR, 2014, 2014, 5, 26),
                withFieldChanged(2014, 5, 26, YEAR_OF_ERA, 2012, 2012, 5, 26),
                withFieldChanged(2014, 5, 26, YEAR_OF_ERA, 2014, 2014, 5, 26),
                withFieldChanged(2014, 5, 26, ERA, 0, -2013, 5, 26),
                withFieldChanged(2014, 5, 26, ERA, 1, 2014, 5, 26),
                withFieldChanged(2011, 3, 28, MONTH_OF_YEAR, 13, 2011, 13, 28),
                withFieldChanged(2012, 3, 28, MONTH_OF_YEAR, 13, 2012, 13, 28),
                withFieldChanged(2012, 13, 35, MONTH_OF_YEAR, 6, 2012, 6, 28),
                withFieldChanged(2012, 13, 35, YEAR, 2011, 2011, 13, 28),
                withFieldChanged(-2013, 6, 8, YEAR_OF_ERA, 2012, -2011, 6, 8),
                withFieldChanged(2014, 5, 26, WeekFields.ISO.dayOfWeek(), 3, 2014, 5, 24),
        };
    }

    private static Object[] withFieldChanged(
            int year,
            int month,
            int dayOfMonth,
            TemporalField field,
            long newValue,
            int expectedYear,
            int expectedMonth,
            int expectedDayOfMonth) {

        return new Object[] {
                year,
                month,
                dayOfMonth,
                field,
                newValue,
                expectedYear,
                expectedMonth,
                expectedDayOfMonth,
        };
    }

    @ParameterizedTest
    @MethodSource("data_with")
    public void test_with_TemporalField(
            int year,
            int month,
            int dayOfMonth,
            TemporalField field,
            long newValue,
            int expectedYear,
            int expectedMonth,
            int expectedDayOfMonth) {

        assertEquals(
                ACCOUNTING_13_MONTH_YEAR.date(expectedYear, expectedMonth, expectedDayOfMonth),
                ACCOUNTING_13_MONTH_YEAR.date(year, month, dayOfMonth).with(field, newValue));
    }
}
