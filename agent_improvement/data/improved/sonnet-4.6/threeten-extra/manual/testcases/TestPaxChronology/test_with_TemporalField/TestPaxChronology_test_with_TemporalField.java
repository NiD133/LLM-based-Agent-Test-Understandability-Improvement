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

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_with_TemporalField {

    /**
     * Test cases for {@link PaxDate#with(TemporalField, long)}.
     *
     * Each row: year, month, dom, field, newValue, expectedYear, expectedMonth, expectedDom
     *
     * The test verifies that adjusting a PaxDate by the given field to the given value
     * produces the expected date. Identical "before" and "after" rows verify idempotency.
     */
    public static Object[][] data_with() {
        return new Object[][] {
            // DAY_OF_WEEK: shift within the current week
            { 2014, 5, 26, DAY_OF_WEEK, 3, 2014, 5, 25 },
            { 2014, 5, 26, DAY_OF_WEEK, 4, 2014, 5, 26 },

            // DAY_OF_MONTH: set the absolute day within the current month
            { 2014, 5, 26, DAY_OF_MONTH, 28, 2014, 5, 28 },
            { 2014, 5, 26, DAY_OF_MONTH, 26, 2014, 5, 26 },

            // DAY_OF_YEAR: set the absolute day within the current year
            { 2014, 5, 26, DAY_OF_YEAR, 364, 2014, 13, 28 },
            { 2014, 5, 26, DAY_OF_YEAR, 138, 2014, 5, 26 },

            // ALIGNED_DAY_OF_WEEK_IN_MONTH: shift within the current aligned week of month
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 3, 2014, 5, 24 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5, 2014, 5, 26 },

            // ALIGNED_WEEK_OF_MONTH: set the week within the current month (keeping aligned day-of-week)
            { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 1, 2014, 5, 5 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 4, 2014, 5, 26 },

            // ALIGNED_DAY_OF_WEEK_IN_YEAR: shift within the current aligned week of year
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 3, 2014, 5, 24 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 5, 2014, 5, 26 },

            // ALIGNED_WEEK_OF_YEAR: set the week within the current year (keeping aligned day-of-week)
            { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 23, 2014, 6, 19 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 20, 2014, 5, 26 },

            // MONTH_OF_YEAR: change the month, keeping the day clamped to the new month's length
            { 2014, 5, 26, MONTH_OF_YEAR, 7, 2014, 7, 26 },
            { 2014, 5, 26, MONTH_OF_YEAR, 5, 2014, 5, 26 },
            // non-leap year: month 13 has 28 days, day-28 fits
            { 2011, 3, 28, MONTH_OF_YEAR, 13, 2011, 13, 28 },
            // leap year: month 13 is the Pax week (7 days), day-28 is clamped to 7
            { 2012, 3, 28, MONTH_OF_YEAR, 13, 2012, 13, 7 },
            { 2012, 3, 28, MONTH_OF_YEAR, 6, 2012, 6, 28 },

            // PROLEPTIC_MONTH: set the absolute month count since epoch
            { 2014, 5, 26, PROLEPTIC_MONTH, 2013 * 13 + 20 * 18 - 5 + 2 + 3 - 1, 2013, 3, 26 },
            { 2014, 5, 26, PROLEPTIC_MONTH, 2013 * 13 + 20 * 18 - 5 + 2 + 5 - 1, 2013, 5, 26 },

            // YEAR: change the proleptic year, keeping month and day
            { 2014, 5, 26, YEAR, 2012, 2012, 5, 26 },
            { 2014, 5, 26, YEAR, 2014, 2014, 5, 26 },
            // leap-year edge: month-13 day-7 moved to a non-leap year still valid as month 13
            { 2012, 13, 7, YEAR, 2011, 2011, 13, 7 },

            // YEAR_OF_ERA: same as YEAR for CE dates; negative era applies the inverse
            { 2014, 5, 26, YEAR_OF_ERA, 2012, 2012, 5, 26 },
            { 2014, 5, 26, YEAR_OF_ERA, 2014, 2014, 5, 26 },
            // BCE year-of-era: yearOfEra 2012 on year -2013 gives proleptic year -2011
            { -2013, 6, 8, YEAR_OF_ERA, 2012, -2011, 6, 8 },

            // ERA: flipping CE (1) to BCE (0) negates the proleptic year
            { 2014, 5, 26, ERA, 0, -2013, 5, 26 },
            { 2014, 5, 26, ERA, 1, 2014, 5, 26 },

            // ISO WeekFields: ISO day-of-week behaves identically to DAY_OF_WEEK for this date
            { 2014, 5, 26, WeekFields.ISO.dayOfWeek(), 3, 2014, 5, 25 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_with")
    public void test_with_TemporalField(
            int year, int month, int dom,
            TemporalField field, long value,
            int expectedYear, int expectedMonth, int expectedDom) {
        assertEquals(
            PaxDate.of(expectedYear, expectedMonth, expectedDom),
            PaxDate.of(year, month, dom).with(field, value));
    }
}
