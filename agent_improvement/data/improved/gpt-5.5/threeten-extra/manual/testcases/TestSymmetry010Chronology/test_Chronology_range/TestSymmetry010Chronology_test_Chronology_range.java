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
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_Chronology_range {

    private static final long MAX_YEAR = 1_000_000L;
    private static final long DAYS_PER_STANDARD_YEAR = 364L;
    private static final long DAYS_PER_LEAP_WEEK = 7L;
    private static final long LEAP_YEARS_BEFORE_MAX_YEAR = 177_474L;
    private static final long DAYS_0001_TO_1970 = 719_162L;

    private static final ValueRange EPOCH_DAY_RANGE = ValueRange.of(
            -MAX_YEAR * DAYS_PER_STANDARD_YEAR - LEAP_YEARS_BEFORE_MAX_YEAR * DAYS_PER_LEAP_WEEK - DAYS_0001_TO_1970,
             MAX_YEAR * DAYS_PER_STANDARD_YEAR + LEAP_YEARS_BEFORE_MAX_YEAR * DAYS_PER_LEAP_WEEK - DAYS_0001_TO_1970);

    public static Stream<Arguments> chronologyRanges() {
        return Stream.of(
                Arguments.of(ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7)),
                Arguments.of(ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7)),
                Arguments.of(ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 4, 5)),
                Arguments.of(ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52, 53)),
                Arguments.of(DAY_OF_WEEK, ValueRange.of(1, 7)),
                Arguments.of(DAY_OF_MONTH, ValueRange.of(1, 30, 37)),
                Arguments.of(DAY_OF_YEAR, ValueRange.of(1, 364, 371)),
                Arguments.of(ERA, ValueRange.of(0, 1)),
                Arguments.of(EPOCH_DAY, EPOCH_DAY_RANGE),
                Arguments.of(MONTH_OF_YEAR, ValueRange.of(1, 12)),
                Arguments.of(PROLEPTIC_MONTH, ValueRange.of(-12_000_000L, 11_999_999L)),
                Arguments.of(YEAR, ValueRange.of(-1_000_000L, 1_000_000)),
                Arguments.of(YEAR_OF_ERA, ValueRange.of(-1_000_000, 1_000_000)));
    }

    @ParameterizedTest
    @MethodSource("chronologyRanges")
    public void test_Chronology_range(ChronoField field, ValueRange expectedRange) {
        assertEquals(expectedRange, Symmetry010Chronology.INSTANCE.range(field));
    }
}
