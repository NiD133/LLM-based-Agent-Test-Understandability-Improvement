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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestJulianChronology_test_getLong {

    /**
     * Test data for getLong: each row is (year, month, dayOfMonth, field, expectedValue).
     * Most cases use the reference date 2014-05-26 (Julian), which is a Sunday.
     */
    public static Object[][] data_getLong() {
        return new Object[][] {
            // Day-of-week: 2014-05-26 is a Sunday (ISO value 7)
            { 2014, 5, 26, DAY_OF_WEEK,                    7 },
            { 2014, 5, 26, DAY_OF_MONTH,                   26 },
            // Day 146 = Jan(31) + Feb(28) + Mar(31) + Apr(30) + 26 days into May
            { 2014, 5, 26, DAY_OF_YEAR,                    31 + 28 + 31 + 30 + 26 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH,   5 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH,          4 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR,    6 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR,           21 },
            { 2014, 5, 26, MONTH_OF_YEAR,                  5 },
            // Proleptic month = year * 12 + (month - 1)
            { 2014, 5, 26, PROLEPTIC_MONTH,                2014 * 12 + 5 - 1 },
            { 2014, 5, 26, YEAR,                           2014 },
            // Era: AD = 1, BC = 0
            { 2014, 5, 26, ERA,                            1 },
            {    1, 6,  8, ERA,                            1 },
            {    0, 6,  8, ERA,                            0 },
            // ISO WeekFields also reports Sunday as day 7
            { 2014, 5, 26, WeekFields.ISO.dayOfWeek(),     7 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_getLong")
    @DisplayName("getLong returns the correct value for each temporal field")
    public void test_getLong(int year, int month, int dom, TemporalField field, long expected) {
        assertEquals(expected, JulianDate.of(year, month, dom).getLong(field));
    }
}
