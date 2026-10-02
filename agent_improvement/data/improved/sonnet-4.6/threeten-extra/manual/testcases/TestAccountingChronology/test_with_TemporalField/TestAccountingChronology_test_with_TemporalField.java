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

    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    // columns: year, month, dom, field, value, expectedYear, expectedMonth, expectedDom
    public static Object[][] data_with() {
        return new Object[][] {
            // DAY_OF_WEEK: adjusts day within the same week
            { 2014, 5, 26, DAY_OF_WEEK, 3, 2014, 5, 24 },
            { 2014, 5, 26, DAY_OF_WEEK, 5, 2014, 5, 26 },
            // DAY_OF_MONTH: sets day within the same month
            { 2014, 5, 26, DAY_OF_MONTH, 28, 2014, 5, 28 },
            { 2014, 5, 26, DAY_OF_MONTH, 26, 2014, 5, 26 },
            // DAY_OF_YEAR: sets absolute day within the year
            { 2014, 5, 26, DAY_OF_YEAR, 364, 2014, 13, 28 },
            { 2014, 5, 26, DAY_OF_YEAR, 138, 2014,  5, 26 },
            // ALIGNED_DAY_OF_WEEK_IN_MONTH: adjusts day within the current week of the month
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 3, 2014, 5, 24 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5, 2014, 5, 26 },
            // ALIGNED_WEEK_OF_MONTH: moves to a different week within the same month
            { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 1, 2014, 5,  5 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 4, 2014, 5, 26 },
            // ALIGNED_DAY_OF_WEEK_IN_YEAR: adjusts day within the current week of the year
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 3, 2014, 5, 24 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 5, 2014, 5, 26 },
            // ALIGNED_WEEK_OF_YEAR: moves to a different week within the same year
            { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 23, 2014, 6, 19 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 20, 2014, 5, 26 },
            // MONTH_OF_YEAR: changes the month, keeping day-of-month where possible
            { 2014, 5, 26, MONTH_OF_YEAR, 7, 2014, 7, 26 },
            { 2014, 5, 26, MONTH_OF_YEAR, 5, 2014, 5, 26 },
            // PROLEPTIC_MONTH: absolute month index across years
            { 2014, 5, 26, PROLEPTIC_MONTH, 2013 * 13 + 3 - 1, 2013, 3, 26 },
            { 2014, 5, 26, PROLEPTIC_MONTH, 2014 * 13 + 5 - 1, 2014, 5, 26 },
            // YEAR: changes the year, keeping month and day
            { 2014, 5, 26, YEAR, 2012, 2012, 5, 26 },
            { 2014, 5, 26, YEAR, 2014, 2014, 5, 26 },
            // YEAR_OF_ERA: changes year within the current era
            { 2014, 5, 26, YEAR_OF_ERA, 2012, 2012, 5, 26 },
            { 2014, 5, 26, YEAR_OF_ERA, 2014, 2014, 5, 26 },
            // ERA: switching era negates the proleptic year
            { 2014, 5, 26, ERA, 0, -2013, 5, 26 },
            { 2014, 5, 26, ERA, 1,  2014, 5, 26 },
            // MONTH_OF_YEAR boundary: moving into month 13 (non-leap and leap year)
            { 2011, 3, 28, MONTH_OF_YEAR, 13, 2011, 13, 28 },
            { 2012, 3, 28, MONTH_OF_YEAR, 13, 2012, 13, 28 },
            // day is clamped when target month is shorter than current day-of-month
            { 2012, 13, 35, MONTH_OF_YEAR, 6, 2012,  6, 28 },
            { 2012, 13, 35, YEAR,       2011, 2011, 13, 28 },
            // BCE year: YEAR_OF_ERA counts backward from 1
            { -2013, 6, 8, YEAR_OF_ERA, 2012, -2011, 6, 8 },
            // ISO WeekFields: non-ChronoField implementation also supported
            { 2014, 5, 26, WeekFields.ISO.dayOfWeek(), 3, 2014, 5, 24 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_with")
    public void test_with_TemporalField(int year, int month, int dom, TemporalField field, long value,
            int expectedYear, int expectedMonth, int expectedDom) {
        assertEquals(
                INSTANCE.date(expectedYear, expectedMonth, expectedDom),
                INSTANCE.date(year, month, dom).with(field, value));
    }
}
