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

public class TestDiscordianChronology_test_with_TemporalField {

    public static Object[][] data_with() {
        return new Object[][] {
                withCase(2014, 5, 26, DAY_OF_WEEK, 1, 2014, 5, 24),
                withCase(2014, 5, 26, DAY_OF_WEEK, 3, 2014, 5, 26),
                withCase(2014, 5, 26, DAY_OF_MONTH, 31, 2014, 5, 31),
                withCase(2014, 5, 26, DAY_OF_MONTH, 26, 2014, 5, 26),
                withCase(2014, 5, 26, DAY_OF_YEAR, 365, 2014, 5, 72),
                withCase(2014, 5, 26, DAY_OF_YEAR, 319, 2014, 5, 26),
                withCase(2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 3, 2014, 5, 28),
                withCase(2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1, 2014, 5, 26),
                withCase(2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 1, 2014, 5, 1),
                withCase(2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 6, 2014, 5, 26),
                withCase(2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 2, 2014, 5, 25),
                withCase(2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 3, 2014, 5, 26),
                withCase(2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 23, 2014, 2, 40),
                withCase(2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 64, 2014, 5, 26),
                withCase(2014, 5, 26, MONTH_OF_YEAR, 4, 2014, 4, 26),
                withCase(2014, 5, 26, MONTH_OF_YEAR, 5, 2014, 5, 26),
                withCase(2014, 5, 26, PROLEPTIC_MONTH, 2013 * 5 + 3 - 1, 2013, 3, 26),
                withCase(2014, 5, 26, PROLEPTIC_MONTH, 2014 * 5 + 5 - 1, 2014, 5, 26),
                withCase(2014, 5, 26, YEAR, 2012, 2012, 5, 26),
                withCase(2014, 5, 26, YEAR, 2014, 2014, 5, 26),
                withCase(2014, 5, 26, YEAR_OF_ERA, 2012, 2012, 5, 26),
                withCase(2014, 5, 26, YEAR_OF_ERA, 2014, 2014, 5, 26),
                withCase(2014, 5, 26, ERA, 1, 2014, 5, 26),

                withCase(2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_MONTH, 0, 2014, 0, 0),
                withCase(2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1, 2014, 1, 56),
                withCase(2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_MONTH, 2, 2014, 1, 57),
                withCase(2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_MONTH, 3, 2014, 1, 58),
                withCase(2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_MONTH, 4, 2014, 1, 59),
                withCase(2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5, 2014, 1, 60),
                withCase(2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_YEAR, 0, 2014, 0, 0),
                withCase(2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_YEAR, 1, 2014, 1, 56),
                withCase(2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_YEAR, 2, 2014, 1, 57),
                withCase(2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_YEAR, 3, 2014, 1, 58),
                withCase(2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_YEAR, 4, 2014, 1, 59),
                withCase(2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_YEAR, 5, 2014, 1, 60),
                withCase(2014, 0, 0, ALIGNED_WEEK_OF_MONTH, 0, 2014, 0, 0),
                withCase(2014, 0, 0, ALIGNED_WEEK_OF_MONTH, 3, 2014, 1, 15),
                withCase(2014, 0, 0, ALIGNED_WEEK_OF_YEAR, 0, 2014, 0, 0),
                withCase(2014, 0, 0, ALIGNED_WEEK_OF_YEAR, 3, 2014, 1, 15),
                withCase(2014, 0, 0, DAY_OF_WEEK, 0, 2014, 0, 0),
                withCase(2014, 0, 0, DAY_OF_WEEK, 1, 2014, 1, 56),
                withCase(2014, 0, 0, DAY_OF_WEEK, 2, 2014, 1, 57),
                withCase(2014, 0, 0, DAY_OF_WEEK, 3, 2014, 1, 58),
                withCase(2014, 0, 0, DAY_OF_WEEK, 4, 2014, 1, 59),
                withCase(2014, 0, 0, DAY_OF_WEEK, 5, 2014, 1, 60),
                withCase(2014, 0, 0, DAY_OF_MONTH, 0, 2014, 0, 0),
                withCase(2014, 0, 0, DAY_OF_MONTH, 3, 2014, 1, 3),
                withCase(2014, 0, 0, MONTH_OF_YEAR, 0, 2014, 0, 0),
                withCase(2014, 0, 0, MONTH_OF_YEAR, 1, 2014, 1, 60),
                withCase(2014, 0, 0, MONTH_OF_YEAR, 2, 2014, 2, 60),
                withCase(2014, 0, 0, YEAR, 2014, 2014, 0, 0),
                withCase(2014, 0, 0, YEAR, 2013, 2013, 1, 60),
                withCase(2014, 0, 0, YEAR, 2015, 2015, 1, 60),
                withCase(2014, 0, 0, YEAR, 2018, 2018, 0, 0),

                withCase(2014, 3, 31, DAY_OF_MONTH, 0, 2014, 0, 0),
                withCase(2014, 1, 31, DAY_OF_MONTH, 0, 2014, 0, 0),
                withCase(2014, 3, 31, MONTH_OF_YEAR, 0, 2014, 0, 0),
                withCase(2014, 3, 31, DAY_OF_YEAR, 60, 2014, 0, 0),
                withCase(2013, 3, 31, DAY_OF_YEAR, 60, 2013, 1, 60),
                withCase(2013, 1, 60, YEAR, 2014, 2014, 1, 60),
        };
    }

    private static Object[] withCase(
            int year,
            int month,
            int dayOfMonth,
            TemporalField field,
            long newValue,
            int expectedYear,
            int expectedMonth,
            int expectedDayOfMonth) {

        return new Object[] {
                year,
                month,
                dayOfMonth,
                field,
                newValue,
                expectedYear,
                expectedMonth,
                expectedDayOfMonth,
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
                DiscordianDate.of(expectedYear, expectedMonth, expectedDayOfMonth),
                DiscordianDate.of(year, month, dayOfMonth).with(field, newValue));
    }
}
