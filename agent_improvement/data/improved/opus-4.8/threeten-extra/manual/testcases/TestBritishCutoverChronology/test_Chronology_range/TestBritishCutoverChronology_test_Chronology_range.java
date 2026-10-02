package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_YEAR;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.ValueRange;

import org.junit.jupiter.api.Test;

/**
 * Verifies the value ranges that {@link BritishCutoverChronology} reports for its supported fields.
 * <p>
 * Each range is expressed as {@code ValueRange.of(minimum, [smallestMaximum,] maximum)}. Where a
 * smallest-maximum is given, it reflects the shortest possible span for that field (for example,
 * the 355-day year 1752 in which the Julian-to-Gregorian cutover removed 11 days).
 */
public class TestBritishCutoverChronology_test_Chronology_range {

    private static final BritishCutoverChronology CHRONOLOGY = BritishCutoverChronology.INSTANCE;

    @Test
    public void test_Chronology_range() {
        // Days of the week are always 1 (Monday) through 7 (Sunday).
        assertEquals(ValueRange.of(1, 7), CHRONOLOGY.range(DAY_OF_WEEK));
        // A month always starts at day 1; its length varies from 28 up to 31.
        assertEquals(ValueRange.of(1, 28, 31), CHRONOLOGY.range(DAY_OF_MONTH));
        // A year always starts at day 1; its length varies from 355 (cutover year) up to 366.
        assertEquals(ValueRange.of(1, 355, 366), CHRONOLOGY.range(DAY_OF_YEAR));
        // Months run 1 through 12.
        assertEquals(ValueRange.of(1, 12), CHRONOLOGY.range(MONTH_OF_YEAR));
        // Aligned weeks within a month span from 3 (cutover month) up to 5.
        assertEquals(ValueRange.of(1, 3, 5), CHRONOLOGY.range(ALIGNED_WEEK_OF_MONTH));
        // Aligned weeks within a year span from 51 up to 53.
        assertEquals(ValueRange.of(1, 51, 53), CHRONOLOGY.range(ALIGNED_WEEK_OF_YEAR));
    }
}
