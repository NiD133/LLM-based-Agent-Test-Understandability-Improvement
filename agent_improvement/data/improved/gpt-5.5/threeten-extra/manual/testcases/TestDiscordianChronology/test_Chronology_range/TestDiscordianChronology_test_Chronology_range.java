package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_YEAR;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.EPOCH_DAY;
import static java.time.temporal.ChronoField.ERA;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.PROLEPTIC_MONTH;
import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestDiscordianChronology_test_Chronology_range {

    public static Object[][] chronologyRanges() {
        return new Object[][] {
                {ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(0, 1, 0, 5)},
                {ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(0, 1, 5, 5)},
                {ALIGNED_WEEK_OF_MONTH, ValueRange.of(0, 1, 0, 15)},
                {ALIGNED_WEEK_OF_YEAR, ValueRange.of(0, 1, 73, 73)},
                {DAY_OF_WEEK, ValueRange.of(0, 1, 0, 5)},
                {DAY_OF_MONTH, ValueRange.of(0, 1, 0, 73)},
                {DAY_OF_YEAR, ValueRange.of(1, 365, 366)},
                {EPOCH_DAY, ValueRange.of(-1_145_400, 999_999 * 365L + 242_499)},
                {ERA, ValueRange.of(1, 1)},
                {MONTH_OF_YEAR, ValueRange.of(0, 1, 5, 5)},
                {PROLEPTIC_MONTH, ValueRange.of(0, 999_999 * 5L + 5 - 1)},
                {YEAR, ValueRange.of(1, 999_999)},
                {YEAR_OF_ERA, ValueRange.of(1, 999_999)},
        };
    }

    @ParameterizedTest
    @MethodSource("chronologyRanges")
    public void test_Chronology_range(ChronoField field, ValueRange expectedRange) {
        assertEquals(expectedRange, DiscordianChronology.INSTANCE.range(field));
    }
}
