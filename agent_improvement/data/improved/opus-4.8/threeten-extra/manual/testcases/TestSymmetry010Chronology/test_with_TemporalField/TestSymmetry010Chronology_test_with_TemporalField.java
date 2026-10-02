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

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link Symmetry010Date#with(TemporalField, long)}.
 * <p>
 * Each case starts from a source date {@code (year, month, dom)}, sets a single
 * {@link TemporalField} to {@code value}, and asserts that the result equals the
 * expected date {@code (expectedYear, expectedMonth, expectedDom)}.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_with_TemporalField {

    /**
     * Provides arguments for {@link #test_with_TemporalField}.
     * <p>
     * Columns: source year, source month, source day-of-month, field to change,
     * new field value, then the expected year, month and day-of-month.
     */
    public static Object[][] data_with() {
        return new Object[][] {
            // Adjusting day-of-week within the source week.
            { 2014, 5, 26, DAY_OF_WEEK, 1, 2014, 5, 20 },
            { 2014, 5, 26, DAY_OF_WEEK, 5, 2014, 5, 24 },

            // Adjusting day-of-month.
            { 2014, 5, 26, DAY_OF_MONTH, 28, 2014, 5, 28 },
            { 2014, 5, 26, DAY_OF_MONTH, 26, 2014, 5, 26 },

            // Adjusting day-of-year.
            { 2014, 5, 26, DAY_OF_YEAR, 364, 2014, 12, 30 },
            { 2014, 5, 26, DAY_OF_YEAR, 138, 2014, 5, 17 },

            // Adjusting aligned-day-of-week / aligned-week within month.
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 3, 2014, 5, 24 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5, 2014, 5, 26 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 1, 2014, 5, 5 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 4, 2014, 5, 26 },

            // Adjusting aligned-day-of-week / aligned-week within year.
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 2, 2014, 5, 21 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 5, 2014, 5, 24 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 23, 2014, 6, 9 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 20, 2014, 5, 19 },

            // Adjusting month, proleptic-month, year, year-of-era and era.
            { 2014, 5, 26, MONTH_OF_YEAR, 4, 2014, 4, 26 },
            { 2014, 5, 26, MONTH_OF_YEAR, 5, 2014, 5, 26 },
            { 2014, 5, 26, PROLEPTIC_MONTH, 2013 * 12 + 3 - 1, 2013, 3, 26 },
            { 2014, 5, 26, PROLEPTIC_MONTH, 2014 * 12 + 5 - 1, 2014, 5, 26 },
            { 2014, 5, 26, YEAR, 2012, 2012, 5, 26 },
            { 2014, 5, 26, YEAR, 2014, 2014, 5, 26 },
            { 2014, 5, 26, YEAR_OF_ERA, 2012, 2012, 5, 26 },
            { 2014, 5, 26, YEAR_OF_ERA, 2014, 2014, 5, 26 },
            { 2014, 5, 26, ERA, 1, 2014, 5, 26 },

            // Each aligned-day-of-week-in-month value across the leap week of December 2015.
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1, 2015, 12, 22 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_MONTH, 2, 2015, 12, 23 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_MONTH, 3, 2015, 12, 24 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_MONTH, 4, 2015, 12, 25 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5, 2015, 12, 26 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_MONTH, 6, 2015, 12, 27 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_MONTH, 7, 2015, 12, 28 },

            // Each aligned-day-of-week-in-year value across the leap week of December 2015.
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_YEAR, 1, 2015, 12, 17 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_YEAR, 2, 2015, 12, 18 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_YEAR, 3, 2015, 12, 19 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_YEAR, 4, 2015, 12, 20 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_YEAR, 5, 2015, 12, 21 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_YEAR, 6, 2015, 12, 22 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_YEAR, 7, 2015, 12, 23 },

            // No-op and shifting adjustments around the leap week.
            { 2015, 12, 29, ALIGNED_WEEK_OF_MONTH, 0, 2015, 12, 29 },
            { 2015, 12, 29, ALIGNED_WEEK_OF_MONTH, 3, 2015, 12, 15 },
            { 2015, 12, 29, ALIGNED_WEEK_OF_YEAR, 0, 2015, 12, 29 },
            { 2015, 12, 29, ALIGNED_WEEK_OF_YEAR, 3, 2015, 1, 20 },
            { 2015, 12, 29, DAY_OF_WEEK, 0, 2015, 12, 29 },

            // Each day-of-week value within the final week of December 2015.
            { 2015, 12, 28, DAY_OF_WEEK, 1, 2015, 12, 24 },
            { 2015, 12, 28, DAY_OF_WEEK, 2, 2015, 12, 25 },
            { 2015, 12, 28, DAY_OF_WEEK, 3, 2015, 12, 26 },
            { 2015, 12, 28, DAY_OF_WEEK, 4, 2015, 12, 27 },
            { 2015, 12, 28, DAY_OF_WEEK, 5, 2015, 12, 28 },
            { 2015, 12, 28, DAY_OF_WEEK, 6, 2015, 12, 29 },
            { 2015, 12, 28, DAY_OF_WEEK, 7, 2015, 12, 30 },

            // Day-of-month and month adjustments in the long December of 2015.
            { 2015, 12, 29, DAY_OF_MONTH, 1, 2015, 12, 1 },
            { 2015, 12, 29, DAY_OF_MONTH, 3, 2015, 12, 3 },
            { 2015, 12, 29, MONTH_OF_YEAR, 1, 2015, 1, 29 },
            { 2015, 12, 29, MONTH_OF_YEAR, 12, 2015, 12, 29 },
            { 2015, 12, 29, MONTH_OF_YEAR, 2, 2015, 2, 29 },

            // Changing the year to / from a leap year (clamps day 37 into a non-leap December).
            { 2015, 12, 37, YEAR, 2004, 2004, 12, 37 },
            { 2015, 12, 37, YEAR, 2013, 2013, 12, 30 },

            // Setting day-of-month / month back to the start of a month.
            { 2014, 3, 28, DAY_OF_MONTH, 1, 2014, 3, 1 },
            { 2014, 1, 28, DAY_OF_MONTH, 1, 2014, 1, 1 },
            { 2014, 3, 28, MONTH_OF_YEAR, 1, 2014, 1, 28 },

            // Setting day-of-year to the last day of the year.
            { 2015, 3, 28, DAY_OF_YEAR, 371, 2015, 12, 37 },
            { 2012, 3, 28, DAY_OF_YEAR, 364, 2012, 12, 30 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_with")
    public void test_with_TemporalField(int year, int month, int dom, TemporalField field, long value,
            int expectedYear, int expectedMonth, int expectedDom) {
        Symmetry010Date source = Symmetry010Date.of(year, month, dom);
        Symmetry010Date expected = Symmetry010Date.of(expectedYear, expectedMonth, expectedDom);

        assertEquals(expected, source.with(field, value));
    }
}
