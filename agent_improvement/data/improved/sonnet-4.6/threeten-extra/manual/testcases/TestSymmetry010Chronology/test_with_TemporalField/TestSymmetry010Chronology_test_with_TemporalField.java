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

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_with_TemporalField {

    public static Object[][] data_with() {
        return new Object[][] {
            // DAY_OF_WEEK: adjusts to the given weekday within the current week
            { 2014, 5, 26, DAY_OF_WEEK, 1, 2014, 5, 20 },
            { 2014, 5, 26, DAY_OF_WEEK, 5, 2014, 5, 24 },
            { 2015, 12, 29, DAY_OF_WEEK, 0, 2015, 12, 29 },
            { 2015, 12, 28, DAY_OF_WEEK, 1, 2015, 12, 24 },
            { 2015, 12, 28, DAY_OF_WEEK, 2, 2015, 12, 25 },
            { 2015, 12, 28, DAY_OF_WEEK, 3, 2015, 12, 26 },
            { 2015, 12, 28, DAY_OF_WEEK, 4, 2015, 12, 27 },
            { 2015, 12, 28, DAY_OF_WEEK, 5, 2015, 12, 28 },
            { 2015, 12, 28, DAY_OF_WEEK, 6, 2015, 12, 29 },
            { 2015, 12, 28, DAY_OF_WEEK, 7, 2015, 12, 30 },

            // DAY_OF_MONTH: sets the day within the same month
            { 2014, 5, 26, DAY_OF_MONTH, 28, 2014, 5, 28 },
            { 2014, 5, 26, DAY_OF_MONTH, 26, 2014, 5, 26 },
            { 2015, 12, 29, DAY_OF_MONTH,  1, 2015, 12,  1 },
            { 2015, 12, 29, DAY_OF_MONTH,  3, 2015, 12,  3 },
            { 2014,  3, 28, DAY_OF_MONTH,  1, 2014,  3,  1 },
            { 2014,  1, 28, DAY_OF_MONTH,  1, 2014,  1,  1 },

            // DAY_OF_YEAR: sets the day-of-year, adjusting month and day accordingly
            { 2014,  5, 26, DAY_OF_YEAR, 364, 2014, 12, 30 },
            { 2014,  5, 26, DAY_OF_YEAR, 138, 2014,  5, 17 },
            { 2015,  3, 28, DAY_OF_YEAR, 371, 2015, 12, 37 },
            { 2012,  3, 28, DAY_OF_YEAR, 364, 2012, 12, 30 },

            // ALIGNED_DAY_OF_WEEK_IN_MONTH: adjusts within the aligned week block of the month
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 3, 2014, 5, 24 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5, 2014, 5, 26 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1, 2015, 12, 22 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_MONTH, 2, 2015, 12, 23 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_MONTH, 3, 2015, 12, 24 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_MONTH, 4, 2015, 12, 25 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5, 2015, 12, 26 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_MONTH, 6, 2015, 12, 27 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_MONTH, 7, 2015, 12, 28 },

            // ALIGNED_WEEK_OF_MONTH: adjusts to the given week-of-month, preserving aligned day
            { 2014,  5, 26, ALIGNED_WEEK_OF_MONTH, 1, 2014,  5,  5 },
            { 2014,  5, 26, ALIGNED_WEEK_OF_MONTH, 4, 2014,  5, 26 },
            { 2015, 12, 29, ALIGNED_WEEK_OF_MONTH, 0, 2015, 12, 29 },
            { 2015, 12, 29, ALIGNED_WEEK_OF_MONTH, 3, 2015, 12, 15 },

            // ALIGNED_DAY_OF_WEEK_IN_YEAR: adjusts within the aligned week block of the year
            { 2014,  5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 2, 2014,  5, 21 },
            { 2014,  5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 5, 2014,  5, 24 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_YEAR, 1, 2015, 12, 17 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_YEAR, 2, 2015, 12, 18 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_YEAR, 3, 2015, 12, 19 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_YEAR, 4, 2015, 12, 20 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_YEAR, 5, 2015, 12, 21 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_YEAR, 6, 2015, 12, 22 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_YEAR, 7, 2015, 12, 23 },

            // ALIGNED_WEEK_OF_YEAR: adjusts to the given week-of-year, preserving aligned day
            { 2014,  5, 26, ALIGNED_WEEK_OF_YEAR, 23, 2014,  6,  9 },
            { 2014,  5, 26, ALIGNED_WEEK_OF_YEAR, 20, 2014,  5, 19 },
            { 2015, 12, 29, ALIGNED_WEEK_OF_YEAR,  0, 2015, 12, 29 },
            { 2015, 12, 29, ALIGNED_WEEK_OF_YEAR,  3, 2015,  1, 20 },

            // MONTH_OF_YEAR: changes the month, keeping the same day-of-month
            { 2014,  5, 26, MONTH_OF_YEAR,  4, 2014,  4, 26 },
            { 2014,  5, 26, MONTH_OF_YEAR,  5, 2014,  5, 26 },
            { 2015, 12, 29, MONTH_OF_YEAR,  1, 2015,  1, 29 },
            { 2015, 12, 29, MONTH_OF_YEAR, 12, 2015, 12, 29 },
            { 2015, 12, 29, MONTH_OF_YEAR,  2, 2015,  2, 29 },
            { 2014,  3, 28, MONTH_OF_YEAR,  1, 2014,  1, 28 },

            // PROLEPTIC_MONTH: sets the year and month together as a single proleptic-month value
            { 2014, 5, 26, PROLEPTIC_MONTH, 2013 * 12 + 3 - 1, 2013, 3, 26 },
            { 2014, 5, 26, PROLEPTIC_MONTH, 2014 * 12 + 5 - 1, 2014, 5, 26 },

            // YEAR: changes the year, keeping month and day (clamping leap-week day if needed)
            { 2014,  5, 26, YEAR, 2012, 2012,  5, 26 },
            { 2014,  5, 26, YEAR, 2014, 2014,  5, 26 },
            { 2015, 12, 37, YEAR, 2004, 2004, 12, 37 },
            { 2015, 12, 37, YEAR, 2013, 2013, 12, 30 },

            // YEAR_OF_ERA: same as YEAR for CE dates
            { 2014, 5, 26, YEAR_OF_ERA, 2012, 2012, 5, 26 },
            { 2014, 5, 26, YEAR_OF_ERA, 2014, 2014, 5, 26 },

            // ERA: only CE (value=1) is valid; date is unchanged
            { 2014, 5, 26, ERA, 1, 2014, 5, 26 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_with")
    public void test_with_TemporalField(
            int year, int month, int dom,
            TemporalField field, long value,
            int expectedYear, int expectedMonth, int expectedDom) {
        assertEquals(
                Symmetry010Date.of(expectedYear, expectedMonth, expectedDom),
                Symmetry010Date.of(year, month, dom).with(field, value));
    }
}
