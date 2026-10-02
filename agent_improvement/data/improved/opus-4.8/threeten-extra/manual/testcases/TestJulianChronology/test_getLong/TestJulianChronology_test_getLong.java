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
import java.time.temporal.WeekFields;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link JulianDate#getLong(TemporalField)} returns the expected value
 * for a range of temporal fields, all queried from the reference date 26 May 2014 (Julian)
 * unless otherwise noted.
 */
public class TestJulianChronology_test_getLong {

    /**
     * Cases of [year, month, dayOfMonth, field, expectedValue].
     * <p>
     * Most rows query fields of the Julian date 2014-05-26; the trailing rows cover the
     * AD/BC era boundary.
     */
    public static Object[][] data_getLong() {
        return new Object[][] {
            // Date-based fields of 2014-05-26 (Julian).
            { 2014, 5, 26, DAY_OF_WEEK, 7 },
            { 2014, 5, 26, DAY_OF_MONTH, 26 },
            // Day-of-year: full Jan(31) + Feb(28) + Mar(31) + Apr(30) + 26 days into May.
            { 2014, 5, 26, DAY_OF_YEAR, 31 + 28 + 31 + 30 + 26 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 4 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 6 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 21 },
            { 2014, 5, 26, MONTH_OF_YEAR, 5 },
            // Proleptic-month counts months since year 0: 2014 full years plus (May - 1) months.
            { 2014, 5, 26, PROLEPTIC_MONTH, 2014 * 12 + 5 - 1 },
            { 2014, 5, 26, YEAR, 2014 },
            { 2014, 5, 26, ERA, 1 },

            // Era field across the AD/BC boundary: AD year 1 -> era 1, proleptic year 0 -> era 0 (BC).
            { 1, 6, 8, ERA, 1 },
            { 0, 6, 8, ERA, 0 },

            // Locale-independent ISO day-of-week field.
            { 2014, 5, 26, WeekFields.ISO.dayOfWeek(), 7 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_getLong")
    public void test_getLong(int year, int month, int dom, TemporalField field, long expected) {
        JulianDate date = JulianDate.of(year, month, dom);

        assertEquals(expected, date.getLong(field));
    }
}
