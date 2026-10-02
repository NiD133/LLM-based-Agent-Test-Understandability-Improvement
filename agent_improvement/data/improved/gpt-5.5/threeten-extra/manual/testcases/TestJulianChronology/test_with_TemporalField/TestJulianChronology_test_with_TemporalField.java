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

public class TestJulianChronology_test_with_TemporalField {

    public static Object[][] data_with() {
        return new Object[][] {
                {2014, 5, 26, DAY_OF_WEEK, 3, 2014, 5, 22},
                {2014, 5, 26, DAY_OF_WEEK, 7, 2014, 5, 26},
                {2014, 5, 26, DAY_OF_MONTH, 31, 2014, 5, 31},
                {2014, 5, 26, DAY_OF_MONTH, 26, 2014, 5, 26},
                {2014, 5, 26, DAY_OF_YEAR, 365, 2014, 12, 31},
                {2014, 5, 26, DAY_OF_YEAR, 146, 2014, 5, 26},
                {2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 3, 2014, 5, 24},
                {2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5, 2014, 5, 26},
                {2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 1, 2014, 5, 5},
                {2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 4, 2014, 5, 26},
                {2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 2, 2014, 5, 22},
                {2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 6, 2014, 5, 26},
                {2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 23, 2014, 6, 9},
                {2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 21, 2014, 5, 26},
                {2014, 5, 26, MONTH_OF_YEAR, 7, 2014, 7, 26},
                {2014, 5, 26, MONTH_OF_YEAR, 5, 2014, 5, 26},
                {2014, 5, 26, PROLEPTIC_MONTH, 2013 * 12 + 3 - 1, 2013, 3, 26},
                {2014, 5, 26, PROLEPTIC_MONTH, 2014 * 12 + 5 - 1, 2014, 5, 26},
                {2014, 5, 26, YEAR, 2012, 2012, 5, 26},
                {2014, 5, 26, YEAR, 2014, 2014, 5, 26},
                {2014, 5, 26, YEAR_OF_ERA, 2012, 2012, 5, 26},
                {2014, 5, 26, YEAR_OF_ERA, 2014, 2014, 5, 26},
                {2014, 5, 26, ERA, 0, -2013, 5, 26},
                {2014, 5, 26, ERA, 1, 2014, 5, 26},
                {2011, 3, 31, MONTH_OF_YEAR, 2, 2011, 2, 28},
                {2012, 3, 31, MONTH_OF_YEAR, 2, 2012, 2, 29},
                {2012, 3, 31, MONTH_OF_YEAR, 6, 2012, 6, 30},
                {2012, 2, 29, YEAR, 2011, 2011, 2, 28},
                {-2013, 6, 8, YEAR_OF_ERA, 2012, -2011, 6, 8},
                {2014, 5, 26, WeekFields.ISO.dayOfWeek(), 3, 2014, 5, 22},
        };
    }

    @ParameterizedTest
    @MethodSource("data_with")
    public void test_with_TemporalField(
            int year,
            int month,
            int dayOfMonth,
            TemporalField field,
            long newValue,
            int expectedYear,
            int expectedMonth,
            int expectedDayOfMonth) {

        assertEquals(
                JulianDate.of(expectedYear, expectedMonth, expectedDayOfMonth),
                JulianDate.of(year, month, dayOfMonth).with(field, newValue));
    }
}
