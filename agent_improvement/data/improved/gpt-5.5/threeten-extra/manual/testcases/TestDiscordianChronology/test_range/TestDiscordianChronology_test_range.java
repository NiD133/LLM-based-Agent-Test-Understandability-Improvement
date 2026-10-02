package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_YEAR;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestDiscordianChronology_test_range {

    public static Object[][] data_ranges() {
        return new Object[][] {
                // St. Tib's Day is isolated from regular months.
                rangeCase(2010, 0, 0, DAY_OF_MONTH, 0, 0),
                rangeCase(2010, 1, 23, DAY_OF_MONTH, 1, 73),
                rangeCase(2010, 2, 23, DAY_OF_MONTH, 1, 73),
                rangeCase(2010, 3, 23, DAY_OF_MONTH, 1, 73),
                rangeCase(2010, 4, 23, DAY_OF_MONTH, 1, 73),
                rangeCase(2010, 5, 23, DAY_OF_MONTH, 1, 73),

                // Day-of-year counts St. Tib's Day in leap years.
                rangeCase(2010, 0, 0, DAY_OF_YEAR, 1, 366),
                rangeCase(2010, 1, 23, DAY_OF_YEAR, 1, 366),
                rangeCase(2011, 2, 23, DAY_OF_YEAR, 1, 365),

                // Month zero is only valid in leap years.
                rangeCase(2010, 0, 0, MONTH_OF_YEAR, 0, 5),
                rangeCase(2010, 1, 1, MONTH_OF_YEAR, 0, 5),
                rangeCase(2011, 1, 23, MONTH_OF_YEAR, 1, 5),

                // St. Tib's Day has no aligned day within a regular month.
                rangeCase(2010, 0, 0, ALIGNED_DAY_OF_WEEK_IN_MONTH, 0, 0),
                rangeCase(2010, 1, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1, 5),
                rangeCase(2010, 1, 59, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1, 5),
                rangeCase(2010, 1, 60, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1, 5),

                // The aligned day within a year includes zero only in leap years.
                rangeCase(2010, 0, 0, ALIGNED_DAY_OF_WEEK_IN_YEAR, 0, 5),
                rangeCase(2010, 1, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, 0, 5),
                rangeCase(2010, 1, 59, ALIGNED_DAY_OF_WEEK_IN_YEAR, 0, 5),
                rangeCase(2010, 1, 60, ALIGNED_DAY_OF_WEEK_IN_YEAR, 0, 5),
                rangeCase(2011, 1, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, 1, 5),

                // St. Tib's Day has no aligned week within a regular month.
                rangeCase(2010, 0, 0, ALIGNED_WEEK_OF_MONTH, 0, 0),
                rangeCase(2010, 1, 23, ALIGNED_WEEK_OF_MONTH, 1, 15),

                // The aligned week within a year includes zero only in leap years.
                rangeCase(2010, 0, 0, ALIGNED_WEEK_OF_YEAR, 0, 73),
                rangeCase(2010, 1, 23, ALIGNED_WEEK_OF_YEAR, 0, 73),
                rangeCase(2011, 1, 23, ALIGNED_WEEK_OF_YEAR, 1, 73),

                // St. Tib's Day is not part of the regular Discordian week.
                rangeCase(2010, 0, 0, DAY_OF_WEEK, 0, 0),
                rangeCase(2010, 1, 1, DAY_OF_WEEK, 1, 5),
        };
    }

    private static Object[] rangeCase(
            int year,
            int month,
            int dayOfMonth,
            TemporalField field,
            int expectedMinimum,
            int expectedMaximum) {

        return new Object[] {year, month, dayOfMonth, field, expectedMinimum, expectedMaximum};
    }

    @ParameterizedTest
    @MethodSource("data_ranges")
    public void test_range(
            int year,
            int month,
            int dayOfMonth,
            TemporalField field,
            int expectedMinimum,
            int expectedMaximum) {

        assertEquals(
                ValueRange.of(expectedMinimum, expectedMaximum),
                DiscordianDate.of(year, month, dayOfMonth).range(field));
    }
}
