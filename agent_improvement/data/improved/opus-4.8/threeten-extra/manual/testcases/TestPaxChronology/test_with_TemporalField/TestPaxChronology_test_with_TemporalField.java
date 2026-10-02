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
 * Tests {@link PaxDate#with(TemporalField, long)}: setting a single temporal field
 * on a PaxDate must yield the expected PaxDate.
 */
@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_with_TemporalField {

    /**
     * Each row is: {@code startYear, startMonth, startDom, field, newValue,
     * expectedYear, expectedMonth, expectedDom}.
     *
     * <p>Rows are grouped by the field being set; within each field there is a row
     * that moves the date and a row that sets the field to its current value (no-op).
     */
    public static Object[][] data_with() {
        return new Object[][] {
            // DAY_OF_WEEK
            { 2014, 5, 26, DAY_OF_WEEK, 3, 2014, 5, 25 },
            { 2014, 5, 26, DAY_OF_WEEK, 4, 2014, 5, 26 },

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

            // PROLEPTIC_MONTH
            { 2014, 5, 26, PROLEPTIC_MONTH, 2013 * 13 + 20 * 18 - 5 + 2 + 3 - 1, 2013, 3, 26 },
            { 2014, 5, 26, PROLEPTIC_MONTH, 2013 * 13 + 20 * 18 - 5 + 2 + 5 - 1, 2013, 5, 26 },

            // YEAR
            { 2014, 5, 26, YEAR, 2012, 2012, 5, 26 },
            { 2014, 5, 26, YEAR, 2014, 2014, 5, 26 },

            // YEAR_OF_ERA
            { 2014, 5, 26, YEAR_OF_ERA, 2012, 2012, 5, 26 },
            { 2014, 5, 26, YEAR_OF_ERA, 2014, 2014, 5, 26 },

            // ERA
            { 2014, 5, 26, ERA, 0, -2013, 5, 26 },
            { 2014, 5, 26, ERA, 1, 2014, 5, 26 },

            // MONTH_OF_YEAR across leap (14-month) years
            { 2011, 3, 28, MONTH_OF_YEAR, 13, 2011, 13, 28 },
            { 2012, 3, 28, MONTH_OF_YEAR, 13, 2012, 13, 7 },
            { 2012, 3, 28, MONTH_OF_YEAR, 6, 2012, 6, 28 },

            // YEAR / YEAR_OF_ERA edge cases
            { 2012, 13, 7, YEAR, 2011, 2011, 13, 7 },
            { -2013, 6, 8, YEAR_OF_ERA, 2012, -2011, 6, 8 },

            // Non-ISO week field
            { 2014, 5, 26, WeekFields.ISO.dayOfWeek(), 3, 2014, 5, 25 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_with")
    public void test_with_TemporalField(int year, int month, int dom, TemporalField field, long value,
            int expectedYear, int expectedMonth, int expectedDom) {
        PaxDate start = PaxDate.of(year, month, dom);
        PaxDate expected = PaxDate.of(expectedYear, expectedMonth, expectedDom);

        assertEquals(expected, start.with(field, value));
    }
}
