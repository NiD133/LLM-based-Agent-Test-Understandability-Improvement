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
 * Tests the {@link ValueRange} that {@link InternationalFixedChronology} reports
 * for each supported {@code ChronoField}.
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_Chronology_range {

    private static final InternationalFixedChronology CHRONOLOGY = InternationalFixedChronology.INSTANCE;

    @Test
    public void test_Chronology_range() {
        // Leap/Year Day sit outside the normal week, so the lower bound can be 0.
        assertEquals(ValueRange.of(0, 1, 0, 7), CHRONOLOGY.range(ALIGNED_DAY_OF_WEEK_IN_MONTH));
        assertEquals(ValueRange.of(0, 1, 0, 7), CHRONOLOGY.range(ALIGNED_DAY_OF_WEEK_IN_YEAR));
        assertEquals(ValueRange.of(0, 1, 0, 4), CHRONOLOGY.range(ALIGNED_WEEK_OF_MONTH));
        assertEquals(ValueRange.of(0, 1, 0, 52), CHRONOLOGY.range(ALIGNED_WEEK_OF_YEAR));
        assertEquals(ValueRange.of(0, 1, 0, 7), CHRONOLOGY.range(DAY_OF_WEEK));

        // Months hold 28 days, except the two that include a special 29th day.
        assertEquals(ValueRange.of(1, 29), CHRONOLOGY.range(DAY_OF_MONTH));
        assertEquals(ValueRange.of(1, 365, 366), CHRONOLOGY.range(DAY_OF_YEAR));
        assertEquals(ValueRange.of(1, 13), CHRONOLOGY.range(MONTH_OF_YEAR));

        // Only the CE era is supported.
        assertEquals(ValueRange.of(1, 1), CHRONOLOGY.range(ERA));

        // Year / proleptic-month / epoch-day ranges span the supported year window (1 to 1_000_000).
        assertEquals(ValueRange.of(-719_528, 1_000_000 * 365L + 242_499 - 719_528), CHRONOLOGY.range(EPOCH_DAY));
        assertEquals(ValueRange.of(13, 1_000_000 * 13L - 1), CHRONOLOGY.range(PROLEPTIC_MONTH));
        assertEquals(ValueRange.of(1, 1_000_000), CHRONOLOGY.range(YEAR));
        assertEquals(ValueRange.of(1, 1_000_000), CHRONOLOGY.range(YEAR_OF_ERA));
    }
}
