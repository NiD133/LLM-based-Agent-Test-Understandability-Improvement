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

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_Chronology_range {

    private static final long MAX_YEAR = 1_000_000L;
    private static final long DAYS_IN_STANDARD_YEAR = 364L;
    private static final long LEAP_WEEKS_BEFORE_MAX_YEAR = 177_474L;
    private static final long DAYS_PER_WEEK = 7L;
    private static final long DAYS_0001_TO_1970 = 719_162L;

    @Test
    public void test_Chronology_range() {
        assertChronologyRange(ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7));
        assertChronologyRange(ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7));
        assertChronologyRange(ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 4, 5));
        assertChronologyRange(ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52, 53));
        assertChronologyRange(DAY_OF_WEEK, ValueRange.of(1, 7));
        assertChronologyRange(DAY_OF_MONTH, ValueRange.of(1, 30, 37));
        assertChronologyRange(DAY_OF_YEAR, ValueRange.of(1, 364, 371));
        assertChronologyRange(ERA, ValueRange.of(0, 1));
        assertChronologyRange(EPOCH_DAY, expectedEpochDayRange());
        assertChronologyRange(MONTH_OF_YEAR, ValueRange.of(1, 12));
        assertChronologyRange(PROLEPTIC_MONTH, ValueRange.of(-12_000_000L, 11_999_999L));
        assertChronologyRange(YEAR, ValueRange.of(-1_000_000L, 1_000_000));
        assertChronologyRange(YEAR_OF_ERA, ValueRange.of(-1_000_000, 1_000_000));
    }

    private static void assertChronologyRange(ChronoField field, ValueRange expectedRange) {
        assertEquals(expectedRange, Symmetry010Chronology.INSTANCE.range(field));
    }

    private static ValueRange expectedEpochDayRange() {
        long maxYearDays = MAX_YEAR * DAYS_IN_STANDARD_YEAR;
        long leapWeekDays = LEAP_WEEKS_BEFORE_MAX_YEAR * DAYS_PER_WEEK;

        return ValueRange.of(
                -maxYearDays - leapWeekDays - DAYS_0001_TO_1970,
                maxYearDays + leapWeekDays - DAYS_0001_TO_1970);
    }
}
