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

/**
 * Tests {@link AccountingChronology}: adjusting an accounting date by setting a single
 * {@link TemporalField} to a new value via {@code AccountingDate.with(field, value)}.
 *
 * <p>The chronology under test uses an accounting year that ends on the Sunday nearest the
 * end of August, divided into thirteen even four-week months, with the leap week placed in
 * month 13.
 */
public class TestAccountingChronology_test_with_TemporalField {

    private static final AccountingChronology INSTANCE = new AccountingChronologyBuilder()
            .endsOn(DayOfWeek.SUNDAY)
            .nearestEndOf(Month.AUGUST)
            .withDivision(AccountingYearDivision.THIRTEEN_EVEN_MONTHS_OF_4_WEEKS)
            .leapWeekInMonth(13)
            .toChronology();

    /**
     * Cases for {@link #test_with_TemporalField}.
     *
     * <p>Each row is: starting date (year, month, dayOfMonth), the field to set and its new
     * value, then the expected resulting date (year, month, dayOfMonth).
     */
    public static Object[][] data_with() {
        return new Object[][] {
            // DAY_OF_WEEK: move within the week / keep the same day
            { 2014, 5, 26, DAY_OF_WEEK, 3, 2014, 5, 24 },
            { 2014, 5, 26, DAY_OF_WEEK, 5, 2014, 5, 26 },
            // DAY_OF_MONTH
            { 2014, 5, 26, DAY_OF_MONTH, 28, 2014, 5, 28 },
            { 2014, 5, 26, DAY_OF_MONTH, 26, 2014, 5, 26 },
            // DAY_OF_YEAR
            { 2014, 5, 26, DAY_OF_YEAR, 364, 2014, 13, 28 },
            { 2014, 5, 26, DAY_OF_YEAR, 138, 2014, 5, 26 },
            // ALIGNED_DAY_OF_WEEK_IN_MONTH
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 3, 2014, 5, 24 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5, 2014, 5, 26 },
            // ALIGNED_WEEK_OF_MONTH
            { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 1, 2014, 5, 5 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 4, 2014, 5, 26 },
            // ALIGNED_DAY_OF_WEEK_IN_YEAR
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 3, 2014, 5, 24 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 5, 2014, 5, 26 },
            // ALIGNED_WEEK_OF_YEAR
            { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 23, 2014, 6, 19 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 20, 2014, 5, 26 },
            // MONTH_OF_YEAR
            { 2014, 5, 26, MONTH_OF_YEAR, 7, 2014, 7, 26 },
            { 2014, 5, 26, MONTH_OF_YEAR, 5, 2014, 5, 26 },
            // PROLEPTIC_MONTH (13 months per accounting year)
            { 2014, 5, 26, PROLEPTIC_MONTH, 2013 * 13 + 3 - 1, 2013, 3, 26 },
            { 2014, 5, 26, PROLEPTIC_MONTH, 2014 * 13 + 5 - 1, 2014, 5, 26 },
            // YEAR
            { 2014, 5, 26, YEAR, 2012, 2012, 5, 26 },
            { 2014, 5, 26, YEAR, 2014, 2014, 5, 26 },
            // YEAR_OF_ERA
            { 2014, 5, 26, YEAR_OF_ERA, 2012, 2012, 5, 26 },
            { 2014, 5, 26, YEAR_OF_ERA, 2014, 2014, 5, 26 },
            // ERA (0 = BCE flips the proleptic year)
            { 2014, 5, 26, ERA, 0, -2013, 5, 26 },
            { 2014, 5, 26, ERA, 1, 2014, 5, 26 },
            // Setting MONTH_OF_YEAR to the 35-day leap month (13) clamps the day to 28
            { 2011, 3, 28, MONTH_OF_YEAR, 13, 2011, 13, 28 },
            { 2012, 3, 28, MONTH_OF_YEAR, 13, 2012, 13, 28 },
            // Leaving the leap month for a normal month clamps day 35 to 28
            { 2012, 13, 35, MONTH_OF_YEAR, 6, 2012, 6, 28 },
            // Leaving a leap year for a non-leap year clamps day 35 to 28
            { 2012, 13, 35, YEAR, 2011, 2011, 13, 28 },
            // YEAR_OF_ERA in the BCE era
            { -2013, 6, 8, YEAR_OF_ERA, 2012, -2011, 6, 8 },
            // A non-ChronoField TemporalField (ISO day-of-week) behaves like DAY_OF_WEEK
            { 2014, 5, 26, WeekFields.ISO.dayOfWeek(), 3, 2014, 5, 24 },
        };
    }

    /**
     * Setting {@code field} to {@code value} on the starting accounting date must yield the
     * expected accounting date.
     */
    @ParameterizedTest
    @MethodSource("data_with")
    public void test_with_TemporalField(int year, int month, int dom, TemporalField field, long value,
            int expectedYear, int expectedMonth, int expectedDom) {
        AccountingDate start = INSTANCE.date(year, month, dom);
        AccountingDate expected = INSTANCE.date(expectedYear, expectedMonth, expectedDom);

        assertEquals(expected, start.with(field, value));
    }
}
