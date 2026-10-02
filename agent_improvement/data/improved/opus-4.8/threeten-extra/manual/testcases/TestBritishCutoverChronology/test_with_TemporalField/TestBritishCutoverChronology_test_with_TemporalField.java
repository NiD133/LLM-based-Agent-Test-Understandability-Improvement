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
 * Tests {@link BritishCutoverDate#with(TemporalField, long)}.
 *
 * <p>The British calendar dropped 11 days in September 1752: the day after
 * 1752-09-02 was 1752-09-14. Many of the cases below probe dates in and
 * around that cutover gap, where {@code with} resolves leniently (a value
 * that lands inside the missing 1752-09-03..1752-09-13 range is shifted
 * forward rather than rejected).
 */
public class TestBritishCutoverChronology_test_with_TemporalField {

    /**
     * Each row is: starting {year, month, dom}, the field to set and its value,
     * then the expected resulting {year, month, dom}.
     */
    public static Object[][] data_with() {
        return new Object[][] {
            // --- Adjusting a date sitting just before the 1752 cutover gap ---
            { 1752, 9, 2, DAY_OF_WEEK, 1, 1752, 8, 31 },
            { 1752, 9, 2, DAY_OF_WEEK, 4, 1752, 9, 14 },
            { 1752, 9, 2, DAY_OF_MONTH, 1, 1752, 9, 1 },                   // lenient: skips into gap
            { 1752, 9, 2, DAY_OF_MONTH, 3, 1752, 9, 14 },                  // lenient: skips into gap
            { 1752, 9, 2, DAY_OF_MONTH, 13, 1752, 9, 24 },
            { 1752, 9, 2, DAY_OF_MONTH, 14, 1752, 9, 14 },
            { 1752, 9, 2, DAY_OF_MONTH, 30, 1752, 9, 30 },
            { 1752, 9, 2, DAY_OF_YEAR, 31 + 29 + 31 + 30 + 31 + 30 + 31 + 31 + 1, 1752, 9, 1 },
            { 1752, 9, 2, DAY_OF_YEAR, 31 + 29 + 31 + 30 + 31 + 30 + 31 + 31 + 3, 1752, 9, 14 }, // lenient
            { 1752, 9, 2, DAY_OF_YEAR, 356, 1753, 1, 1 },                  // lenient
            { 1752, 9, 2, DAY_OF_YEAR, 366, 1753, 1, 11 },
            { 1752, 9, 2, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1, 1752, 9, 1 },
            { 1752, 9, 2, ALIGNED_DAY_OF_WEEK_IN_MONTH, 3, 1752, 9, 14 },
            { 1752, 9, 2, ALIGNED_WEEK_OF_MONTH, 2, 1752, 9, 20 },
            { 1752, 9, 2, ALIGNED_WEEK_OF_MONTH, 3, 1752, 9, 27 },         // lenient
            { 1752, 9, 2, ALIGNED_WEEK_OF_MONTH, 4, 1752, 10, 4 },         // lenient
            { 1752, 9, 2, ALIGNED_WEEK_OF_MONTH, 5, 1752, 10, 11 },
            { 1752, 9, 2, ALIGNED_DAY_OF_WEEK_IN_YEAR, 2, 1752, 9, 14 },
            { 1752, 9, 2, ALIGNED_DAY_OF_WEEK_IN_YEAR, 3, 1752, 9, 15 },
            { 1752, 9, 2, ALIGNED_WEEK_OF_YEAR, 1, 1752, 1, 1 },
            { 1752, 9, 2, ALIGNED_WEEK_OF_YEAR, 35, 1752, 8, 26 },
            { 1752, 9, 2, ALIGNED_WEEK_OF_YEAR, 37, 1752, 9, 20 },
            { 1752, 9, 2, ALIGNED_WEEK_OF_YEAR, 51, 1752, 12, 27 },        // lenient
            { 1752, 9, 2, ALIGNED_WEEK_OF_YEAR, 52, 1753, 1, 3 },
            { 1752, 9, 2, MONTH_OF_YEAR, 8, 1752, 8, 2 },
            { 1752, 9, 2, MONTH_OF_YEAR, 10, 1752, 10, 2 },

            // --- Adjusting a date sitting just after the 1752 cutover gap ---
            { 1752, 9, 14, DAY_OF_WEEK, 1, 1752, 8, 31 },
            { 1752, 9, 14, DAY_OF_WEEK, 3, 1752, 9, 2 },
            { 1752, 9, 14, DAY_OF_MONTH, 1, 1752, 9, 1 },
            { 1752, 9, 14, DAY_OF_MONTH, 2, 1752, 9, 2 },                  // lenient
            { 1752, 9, 14, DAY_OF_MONTH, 3, 1752, 9, 14 },
            { 1752, 9, 14, DAY_OF_MONTH, 30, 1752, 9, 30 },
            { 1752, 9, 14, DAY_OF_YEAR, 31 + 29 + 31 + 30 + 31 + 30 + 31 + 31 + 1, 1752, 9, 1 },
            { 1752, 9, 14, DAY_OF_YEAR, 31 + 29 + 31 + 30 + 31 + 30 + 31 + 31 + 2, 1752, 9, 2 }, // lenient
            { 1752, 9, 14, DAY_OF_YEAR, 356, 1753, 1, 1 },                 // lenient
            { 1752, 9, 14, DAY_OF_YEAR, 366, 1753, 1, 11 },
            { 1752, 9, 14, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1, 1752, 9, 1 },
            { 1752, 9, 14, ALIGNED_DAY_OF_WEEK_IN_MONTH, 2, 1752, 9, 2 },
            { 1752, 9, 14, ALIGNED_WEEK_OF_MONTH, 2, 1752, 9, 21 },
            { 1752, 9, 14, ALIGNED_WEEK_OF_MONTH, 3, 1752, 9, 28 },        // lenient
            { 1752, 9, 14, ALIGNED_WEEK_OF_MONTH, 4, 1752, 10, 5 },        // lenient
            { 1752, 9, 14, ALIGNED_WEEK_OF_MONTH, 5, 1752, 10, 12 },
            { 1752, 9, 14, ALIGNED_DAY_OF_WEEK_IN_YEAR, 2, 1752, 9, 14 },
            { 1752, 9, 14, ALIGNED_DAY_OF_WEEK_IN_YEAR, 3, 1752, 9, 15 },
            { 1752, 9, 14, ALIGNED_WEEK_OF_YEAR, 1, 1752, 1, 2 },
            { 1752, 9, 14, ALIGNED_WEEK_OF_YEAR, 35, 1752, 8, 27 },
            { 1752, 9, 14, ALIGNED_WEEK_OF_YEAR, 37, 1752, 9, 21 },
            { 1752, 9, 14, ALIGNED_WEEK_OF_YEAR, 51, 1752, 12, 28 },       // lenient
            { 1752, 9, 14, ALIGNED_WEEK_OF_YEAR, 52, 1753, 1, 4 },
            { 1752, 9, 14, MONTH_OF_YEAR, 8, 1752, 8, 14 },
            { 1752, 9, 14, MONTH_OF_YEAR, 10, 1752, 10, 14 },

            // --- Changing month/year so the result lands inside the cutover gap (lenient) ---
            { 1752, 8, 4, MONTH_OF_YEAR, 9, 1752, 9, 15 },                 // lenient
            { 1752, 10, 8, MONTH_OF_YEAR, 9, 1752, 9, 19 },                // lenient
            { 1751, 9, 4, YEAR, 1752, 1752, 9, 15 },                       // lenient
            { 1753, 9, 8, YEAR, 1752, 1752, 9, 19 },                       // lenient
            { 1751, 9, 4, YEAR_OF_ERA, 1752, 1752, 9, 15 },                // lenient
            { 1753, 9, 8, YEAR_OF_ERA, 1752, 1752, 9, 19 },

            // --- Ordinary modern-date adjustments (no cutover involved) ---
            { 2014, 5, 26, DAY_OF_WEEK, 3, 2014, 5, 28 },
            { 2014, 5, 26, DAY_OF_WEEK, 7, 2014, 6, 1 },
            { 2014, 5, 26, DAY_OF_MONTH, 31, 2014, 5, 31 },
            { 2014, 5, 26, DAY_OF_MONTH, 26, 2014, 5, 26 },
            { 2014, 5, 26, DAY_OF_YEAR, 365, 2014, 12, 31 },
            { 2014, 5, 26, DAY_OF_YEAR, 146, 2014, 5, 26 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 3, 2014, 5, 24 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5, 2014, 5, 26 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 1, 2014, 5, 5 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 4, 2014, 5, 26 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 2, 2014, 5, 22 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 6, 2014, 5, 26 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 23, 2014, 6, 9 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 21, 2014, 5, 26 },
            { 2014, 5, 26, MONTH_OF_YEAR, 7, 2014, 7, 26 },
            { 2014, 5, 26, MONTH_OF_YEAR, 5, 2014, 5, 26 },
            { 2014, 5, 26, PROLEPTIC_MONTH, 2013 * 12 + 3 - 1, 2013, 3, 26 },
            { 2014, 5, 26, PROLEPTIC_MONTH, 2014 * 12 + 5 - 1, 2014, 5, 26 },
            { 2014, 5, 26, YEAR, 2012, 2012, 5, 26 },
            { 2014, 5, 26, YEAR, 2014, 2014, 5, 26 },
            { 2014, 5, 26, YEAR_OF_ERA, 2012, 2012, 5, 26 },
            { 2014, 5, 26, YEAR_OF_ERA, 2014, 2014, 5, 26 },
            { 2014, 5, 26, ERA, 0, -2013, 5, 26 },
            { 2014, 5, 26, ERA, 1, 2014, 5, 26 },

            // --- Adjustments that clamp the day to the (possibly shorter) target month ---
            { 2011, 3, 31, MONTH_OF_YEAR, 2, 2011, 2, 28 },
            { 2012, 3, 31, MONTH_OF_YEAR, 2, 2012, 2, 29 },
            { 2012, 3, 31, MONTH_OF_YEAR, 6, 2012, 6, 30 },
            { 2012, 2, 29, YEAR, 2011, 2011, 2, 28 },
            { -2013, 6, 8, YEAR_OF_ERA, 2012, -2011, 6, 8 },

            // --- A non-ChronoField field (ISO day-of-week) ---
            { 2014, 5, 26, WeekFields.ISO.dayOfWeek(), 2, 2014, 5, 27 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_with")
    public void test_with_TemporalField(int year, int month, int dom, TemporalField field, long value,
            int expectedYear, int expectedMonth, int expectedDom) {
        BritishCutoverDate start = BritishCutoverDate.of(year, month, dom);
        BritishCutoverDate expected = BritishCutoverDate.of(expectedYear, expectedMonth, expectedDom);

        assertEquals(expected, start.with(field, value));
    }
}
