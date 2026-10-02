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

@SuppressWarnings({"static-method"})
public class TestSymmetry454Chronology_test_Chronology_range {

    private static final Symmetry454Chronology CHRONO = Symmetry454Chronology.INSTANCE;

    // Maximum supported proleptic year, mirroring the CUT's MAX_YEAR constant
    private static final long MAX_YEAR = 1_000_000L;

    @Test
    public void test_Chronology_range() {
        // Day-of-week fields are always 1–7 regardless of month or year type
        assertEquals(ValueRange.of(1, 7), CHRONO.range(ALIGNED_DAY_OF_WEEK_IN_MONTH));
        assertEquals(ValueRange.of(1, 7), CHRONO.range(ALIGNED_DAY_OF_WEEK_IN_YEAR));
        assertEquals(ValueRange.of(1, 7), CHRONO.range(DAY_OF_WEEK));

        // Week-count fields vary: standard months have 4 weeks, long months have 5;
        // standard years have 52 weeks, leap years have 53
        assertEquals(ValueRange.of(1, 4, 5), CHRONO.range(ALIGNED_WEEK_OF_MONTH));
        assertEquals(ValueRange.of(1, 52, 53), CHRONO.range(ALIGNED_WEEK_OF_YEAR));

        // Day fields vary because long months have 35 days and leap years have 371 days
        assertEquals(ValueRange.of(1, 28, 35), CHRONO.range(DAY_OF_MONTH));
        assertEquals(ValueRange.of(1, 364, 371), CHRONO.range(DAY_OF_YEAR));

        // Era is binary: 0 = BCE, 1 = CE (same eras as Gregorian/ISO)
        assertEquals(ValueRange.of(0, 1), CHRONO.range(ERA));

        // Twelve months per year, always
        assertEquals(ValueRange.of(1, 12), CHRONO.range(MONTH_OF_YEAR));

        // Proleptic month spans the full supported year range (12 months each)
        assertEquals(ValueRange.of(-MAX_YEAR * 12, MAX_YEAR * 12 - 1), CHRONO.range(PROLEPTIC_MONTH));

        // Year and year-of-era span ±1 000 000
        assertEquals(ValueRange.of(-MAX_YEAR, MAX_YEAR), CHRONO.range(YEAR));
        assertEquals(ValueRange.of(-MAX_YEAR, MAX_YEAR), CHRONO.range(YEAR_OF_ERA));

        // Epoch-day range is derived from the supported year span, leap-week count, and the
        // offset between the Symmetry454 epoch and the Unix epoch (1970-01-01)
        long leapWeeksBefore = Symmetry454Chronology.getLeapYearsBefore(MAX_YEAR);
        long minEpochDay = -MAX_YEAR * Symmetry454Chronology.DAYS_IN_YEAR
                - leapWeeksBefore * Symmetry454Chronology.DAYS_IN_WEEK
                - Symmetry454Chronology.DAYS_0001_TO_1970;
        long maxEpochDay =  MAX_YEAR * Symmetry454Chronology.DAYS_IN_YEAR
                + leapWeeksBefore * Symmetry454Chronology.DAYS_IN_WEEK
                - Symmetry454Chronology.DAYS_0001_TO_1970;
        assertEquals(ValueRange.of(minEpochDay, maxEpochDay), CHRONO.range(EPOCH_DAY));
    }
}
