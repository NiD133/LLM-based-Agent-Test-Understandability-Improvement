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
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestAccountingChronology_test_getLong {

    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    public static Object[][] data_getLong() {
        return new Object[][] {
                { 2014, 5, 26, DAY_OF_WEEK, 5 },
                { 2014, 5, 26, DAY_OF_MONTH, 26 },
                { 2014, 5, 26, DAY_OF_YEAR, 28 + 28 + 28 + 28 + 26 },
                { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5 },
                { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 4 },
                { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 5 },
                { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 20 },
                { 2014, 5, 26, MONTH_OF_YEAR, 5 },
                { 2014, 5, 26, PROLEPTIC_MONTH, 2014 * 13 + 5 - 1 },
                { 2014, 5, 26, YEAR, 2014 },
                { 2014, 5, 26, ERA, 1 },
                { 1, 6, 8, ERA, 1 },
                { 0, 6, 8, ERA, 0 },
                { 2014, 5, 26, WeekFields.ISO.dayOfWeek(), 5 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_getLong")
    public void test_getLong(int year, int month, int dom, TemporalField field, long expected) {
        assertEquals(expected, INSTANCE.date(year, month, dom).getLong(field));
    }
}
