package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_range {

    public static Object[][] data_ranges() {
        return new Object[][] {
                { 2012, 1, 23, DAY_OF_MONTH, 1, 28 },
                { 2012, 2, 23, DAY_OF_MONTH, 1, 28 },
                { 2012, 3, 23, DAY_OF_MONTH, 1, 28 },
                { 2012, 4, 23, DAY_OF_MONTH, 1, 28 },
                { 2012, 5, 23, DAY_OF_MONTH, 1, 28 },
                { 2012, 6, 23, DAY_OF_MONTH, 1, 28 },
                { 2012, 7, 23, DAY_OF_MONTH, 1, 28 },
                { 2012, 8, 23, DAY_OF_MONTH, 1, 28 },
                { 2012, 9, 23, DAY_OF_MONTH, 1, 28 },
                { 2012, 10, 23, DAY_OF_MONTH, 1, 28 },
                { 2012, 11, 23, DAY_OF_MONTH, 1, 28 },
                { 2012, 12, 23, DAY_OF_MONTH, 1, 28 },
                { 2012, 13, 3, DAY_OF_MONTH, 1, 7 },
                { 2012, 14, 23, DAY_OF_MONTH, 1, 28 },
                { 2012, 1, 23, MONTH_OF_YEAR, 1, 14 },
                { 2012, 1, 23, DAY_OF_YEAR, 1, 371 },
                { 2012, 1, 23, ALIGNED_WEEK_OF_MONTH, 1, 4 },
                { 2012, 13, 3, ALIGNED_WEEK_OF_MONTH, 1, 1 },
                { 2012, 14, 23, ALIGNED_WEEK_OF_MONTH, 1, 4 },
                { 2011, 13, 23, DAY_OF_MONTH, 1, 28 },
                { 2011, 1, 23, MONTH_OF_YEAR, 1, 13 },
                { 2011, 13, 23, DAY_OF_YEAR, 1, 364 },
                { 2011, 13, 23, ALIGNED_WEEK_OF_MONTH, 1, 4 },
        };
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

        ValueRange expectedRange = ValueRange.of(expectedMinimum, expectedMaximum);
        ValueRange actualRange = PaxDate.of(year, month, dayOfMonth).range(field);

        assertEquals(expectedRange, actualRange);
    }
}
