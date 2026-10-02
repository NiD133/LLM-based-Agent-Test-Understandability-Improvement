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

import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link JulianDate#with(TemporalField, long)}: setting a single field on
 * a Julian date and verifying the resulting date.
 */
public class TestJulianChronology_test_with_TemporalField {

    /**
     * Each case is: starting date (year, month, day-of-month), the field to set
     * and the value to set it to, then the expected resulting date
     * (year, month, day-of-month).
     */
    public static Object[][] data_with() {
        return new Object[][] {
            // adjusting the day-of-week within the week
            { 2014, 5, 26, DAY_OF_WEEK, 3, 2014, 5, 22 },
            { 2014, 5, 26, DAY_OF_WEEK, 7, 2014, 5, 26 },
            // adjusting the day-of-month
            { 2014, 5, 26, DAY_OF_MONTH, 31, 2014, 5, 31 },
            { 2014, 5, 26, DAY_OF_MONTH, 26, 2014, 5, 26 },
            // adjusting the day-of-year
            { 2014, 5, 26, DAY_OF_YEAR, 365, 2014, 12, 31 },
            { 2014, 5, 26, DAY_OF_YEAR, 146, 2014, 5, 26 },
            // adjusting the aligned day-of-week / week within the month
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 3, 2014, 5, 24 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5, 2014, 5, 26 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 1, 2014, 5, 5 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 4, 2014, 5, 26 },
            // adjusting the aligned day-of-week / week within the year
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 2, 2014, 5, 22 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 6, 2014, 5, 26 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 23, 2014, 6, 9 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 21, 2014, 5, 26 },
            // adjusting the month-of-year
            { 2014, 5, 26, MONTH_OF_YEAR, 7, 2014, 7, 26 },
            { 2014, 5, 26, MONTH_OF_YEAR, 5, 2014, 5, 26 },
            // adjusting the proleptic-month
            { 2014, 5, 26, PROLEPTIC_MONTH, 2013 * 12 + 3 - 1, 2013, 3, 26 },
            { 2014, 5, 26, PROLEPTIC_MONTH, 2014 * 12 + 5 - 1, 2014, 5, 26 },
            // adjusting the year / year-of-era
            { 2014, 5, 26, YEAR, 2012, 2012, 5, 26 },
            { 2014, 5, 26, YEAR, 2014, 2014, 5, 26 },
            { 2014, 5, 26, YEAR_OF_ERA, 2012, 2012, 5, 26 },
            { 2014, 5, 26, YEAR_OF_ERA, 2014, 2014, 5, 26 },
            // adjusting the era (era 0 flips the proleptic year)
            { 2014, 5, 26, ERA, 0, -2013, 5, 26 },
            { 2014, 5, 26, ERA, 1, 2014, 5, 26 },
            // changing the month clamps the day-of-month to the shorter month
            { 2011, 3, 31, MONTH_OF_YEAR, 2, 2011, 2, 28 },
            { 2012, 3, 31, MONTH_OF_YEAR, 2, 2012, 2, 29 },
            { 2012, 3, 31, MONTH_OF_YEAR, 6, 2012, 6, 30 },
            // changing to a non-leap year clamps Feb 29 to Feb 28
            { 2012, 2, 29, YEAR, 2011, 2011, 2, 28 },
            // adjusting year-of-era for a BC (negative proleptic) year
            { -2013, 6, 8, YEAR_OF_ERA, 2012, -2011, 6, 8 },
            // a localized week field behaves like the ISO day-of-week
            { 2014, 5, 26, WeekFields.ISO.dayOfWeek(), 3, 2014, 5, 22 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_with")
    public void test_with_TemporalField(int year, int month, int dom, TemporalField field, long value,
            int expectedYear, int expectedMonth, int expectedDom) {
        JulianDate adjusted = JulianDate.of(year, month, dom).with(field, value);
        assertEquals(JulianDate.of(expectedYear, expectedMonth, expectedDom), adjusted);
    }
}
