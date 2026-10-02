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

import java.time.temporal.TemporalField;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link Symmetry010Date#getLong(TemporalField)}, which returns the value
 * of a single calendar field for a Symmetry010 date.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_getLong {

    /**
     * Cases of the form {year, month, dayOfMonth, field, expectedValue}.
     * <p>
     * Some expected values are written as arithmetic expressions that mirror the
     * Symmetry010 calendar structure (e.g. 30 + 31 + 30 + 30 days for the first
     * four full months), making the intent of the number easier to follow.
     */
    public static Object[][] data_getLong() {
        return new Object[][] {
            // 2014-05-26: an ordinary date, every supported field queried
            { 2014, 5, 26, DAY_OF_WEEK, 2 },
            { 2014, 5, 26, DAY_OF_MONTH, 26 },
            { 2014, 5, 26, DAY_OF_YEAR, 30 + 31 + 30 + 30 + 26 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 4 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 7 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 4 + 5 + 4 + 4 + 4 },
            { 2014, 5, 26, MONTH_OF_YEAR, 5 },
            { 2014, 5, 26, PROLEPTIC_MONTH, 2014 * 12 + 5 - 1 },
            { 2014, 5, 26, YEAR, 2014 },
            { 2014, 5, 26, ERA, 1 },

            // Era is CE (1) for any year-of-era
            { 1, 5, 8, ERA, 1 },

            // 2012-09-26: start of a quarter aligns to a Monday
            { 2012, 9, 26, DAY_OF_WEEK, 1 },
            { 2012, 9, 26, DAY_OF_YEAR, 3 * (4 + 5 + 4) * 7 - 4 },
            { 2012, 9, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5 },
            { 2012, 9, 26, ALIGNED_WEEK_OF_MONTH, 4 },
            { 2012, 9, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 3 },
            { 2012, 9, 26, ALIGNED_WEEK_OF_YEAR, 3 * (4 + 5 + 4) },

            // 2015-12-37: a leap year, December has 37 days (last day of the leap week)
            { 2015, 12, 37, DAY_OF_WEEK, 5 },
            { 2015, 12, 37, DAY_OF_MONTH, 37 },
            { 2015, 12, 37, DAY_OF_YEAR, 4 * (4 + 5 + 4) * 7 + 7 },
            { 2015, 12, 37, ALIGNED_DAY_OF_WEEK_IN_MONTH, 2 },
            { 2015, 12, 37, ALIGNED_WEEK_OF_MONTH, 6 },
            { 2015, 12, 37, ALIGNED_DAY_OF_WEEK_IN_YEAR, 7 },
            { 2015, 12, 37, ALIGNED_WEEK_OF_YEAR, 53 },
            { 2015, 12, 37, MONTH_OF_YEAR, 12 },
            { 2015, 12, 37, PROLEPTIC_MONTH, 2016 * 12 - 1 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_getLong")
    public void test_getLong(int year, int month, int dom, TemporalField field, long expected) {
        assertEquals(expected, Symmetry010Date.of(year, month, dom).getLong(field));
    }
}
