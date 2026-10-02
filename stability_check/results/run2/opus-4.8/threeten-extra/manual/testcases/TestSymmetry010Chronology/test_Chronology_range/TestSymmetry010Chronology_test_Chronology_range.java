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

import java.time.temporal.ValueRange;

import org.junit.jupiter.api.Test;

/**
 * Verifies the valid-value ranges reported by
 * {@link Symmetry010Chronology#range(java.time.temporal.ChronoField)} for every
 * field the chronology supports.
 * <p>
 * A {@link ValueRange} may be either fixed ({@code of(min, max)}) or variable, where the
 * upper bound differs between the smallest and largest possible maximum
 * ({@code of(min, smallestMax, largestMax)}). The Symmetry010 calendar has such
 * variable ranges because leap years add a leap week to the end of December.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_Chronology_range {

    private static final Symmetry010Chronology SYM010 = Symmetry010Chronology.INSTANCE;

    // Magic numbers used to derive the EPOCH_DAY range, matching the chronology's own constants:
    // MAX_YEAR (highest supported proleptic year), the leap weeks accumulated over that span,
    // and the offset of Sym010 epoch (0001-01-01) from the ISO 1970-01-01 epoch.
    private static final long MAX_YEAR = 1_000_000L;
    private static final int DAYS_IN_YEAR = 364;
    private static final long LEAP_WEEKS_BEFORE_MAX_YEAR = 177_474L;
    private static final int DAYS_IN_WEEK = 7;
    private static final long DAYS_0001_TO_1970 = 719_162L;

    @Test
    public void test_Chronology_range() {
        // Weekday-based fields always span the 7 days of a week.
        assertEquals(ValueRange.of(1, 7), SYM010.range(ALIGNED_DAY_OF_WEEK_IN_MONTH));
        assertEquals(ValueRange.of(1, 7), SYM010.range(ALIGNED_DAY_OF_WEEK_IN_YEAR));
        assertEquals(ValueRange.of(1, 7), SYM010.range(DAY_OF_WEEK));

        // A month has 4 weeks, or 5 in the leap-week December.
        assertEquals(ValueRange.of(1, 4, 5), SYM010.range(ALIGNED_WEEK_OF_MONTH));
        // A year has 52 weeks, or 53 in a leap year.
        assertEquals(ValueRange.of(1, 52, 53), SYM010.range(ALIGNED_WEEK_OF_YEAR));

        // A month has 30 or 31 days, extended to 37 in the leap-week December.
        assertEquals(ValueRange.of(1, 30, 37), SYM010.range(DAY_OF_MONTH));
        // A year has 364 days, or 371 in a leap year.
        assertEquals(ValueRange.of(1, 364, 371), SYM010.range(DAY_OF_YEAR));

        assertEquals(ValueRange.of(1, 12), SYM010.range(MONTH_OF_YEAR));

        // Eras are BCE (0) and CE (1).
        assertEquals(ValueRange.of(0, 1), SYM010.range(ERA));

        // Year-based ranges are bounded by the maximum supported proleptic year.
        assertEquals(
                ValueRange.of(
                        -MAX_YEAR * DAYS_IN_YEAR - LEAP_WEEKS_BEFORE_MAX_YEAR * DAYS_IN_WEEK - DAYS_0001_TO_1970,
                        MAX_YEAR * DAYS_IN_YEAR + LEAP_WEEKS_BEFORE_MAX_YEAR * DAYS_IN_WEEK - DAYS_0001_TO_1970),
                SYM010.range(EPOCH_DAY));
        assertEquals(ValueRange.of(-12_000_000L, 11_999_999L), SYM010.range(PROLEPTIC_MONTH));
        assertEquals(ValueRange.of(-1_000_000L, 1_000_000), SYM010.range(YEAR));
        assertEquals(ValueRange.of(-1_000_000, 1_000_000), SYM010.range(YEAR_OF_ERA));
    }
}
