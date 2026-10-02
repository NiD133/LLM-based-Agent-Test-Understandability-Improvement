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
 * Tests the valid value ranges that {@link Symmetry454Chronology} reports for each
 * supported {@code ChronoField}.
 *
 * <p>The Symmetry454 calendar is a leap-week calendar: every month has 4 weeks (28 days),
 * except February, May, August and November, which have 5 weeks (35 days). Leap years add
 * an extra week to December, so the longer maxima below (e.g. 35 days, 53 weeks, 371 days)
 * reflect the longest months/years rather than the common case.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_Chronology_range {

    private static final Symmetry454Chronology CHRONOLOGY = Symmetry454Chronology.INSTANCE;

    @Test
    public void test_Chronology_range() {
        // Week-based fields: a week is always 7 days.
        assertEquals(ValueRange.of(1, 7), CHRONOLOGY.range(DAY_OF_WEEK));
        assertEquals(ValueRange.of(1, 7), CHRONOLOGY.range(ALIGNED_DAY_OF_WEEK_IN_MONTH));
        assertEquals(ValueRange.of(1, 7), CHRONOLOGY.range(ALIGNED_DAY_OF_WEEK_IN_YEAR));

        // Weeks within a month: 4 in a normal month, 5 in a long (or leap-extended) month.
        assertEquals(ValueRange.of(1, 4, 5), CHRONOLOGY.range(ALIGNED_WEEK_OF_MONTH));
        // Weeks within a year: 52 normally, 53 in a leap year.
        assertEquals(ValueRange.of(1, 52, 53), CHRONOLOGY.range(ALIGNED_WEEK_OF_YEAR));

        // Days within a month: 28 in a normal month, 35 in a long (or leap-extended) month.
        assertEquals(ValueRange.of(1, 28, 35), CHRONOLOGY.range(DAY_OF_MONTH));
        // Days within a year: 364 normally, 371 in a leap year (the extra leap week).
        assertEquals(ValueRange.of(1, 364, 371), CHRONOLOGY.range(DAY_OF_YEAR));

        // Calendar structure: 12 months per year, single CE era (0..1).
        assertEquals(ValueRange.of(1, 12), CHRONOLOGY.range(MONTH_OF_YEAR));
        assertEquals(ValueRange.of(0, 1), CHRONOLOGY.range(ERA));

        // Year-based fields are bounded by +/- 1,000,000 proleptic years.
        assertEquals(ValueRange.of(-1_000_000L, 1_000_000), CHRONOLOGY.range(YEAR));
        assertEquals(ValueRange.of(-1_000_000, 1_000_000), CHRONOLOGY.range(YEAR_OF_ERA));
        // Proleptic month = year * 12 (+/- one for the inclusive/exclusive bounds).
        assertEquals(ValueRange.of(-12_000_000L, 11_999_999L), CHRONOLOGY.range(PROLEPTIC_MONTH));

        // Epoch day bounds, expressed relative to the ISO epoch (1970-01-01).
        // Each value is: (+/- 1,000,000 years of 364 days) +/- (177,474 leap weeks of 7 days),
        // shifted by the 719,162-day offset between the Symmetry454 and ISO epochs.
        long maxEpochDay = 1_000_000 * 364L + 177_474 * 7 - 719_162;
        long minEpochDay = -1_000_000 * 364L - 177_474 * 7 - 719_162;
        assertEquals(ValueRange.of(minEpochDay, maxEpochDay), CHRONOLOGY.range(EPOCH_DAY));
    }
}
