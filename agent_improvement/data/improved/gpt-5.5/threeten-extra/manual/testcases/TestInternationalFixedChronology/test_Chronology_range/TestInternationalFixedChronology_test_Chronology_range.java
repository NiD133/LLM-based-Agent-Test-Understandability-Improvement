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
public class TestInternationalFixedChronology_test_Chronology_range {

    @Test
    public void test_Chronology_range() {
        assertEquals(ValueRange.of(0, 1, 0, 7), InternationalFixedChronology.INSTANCE.range(ALIGNED_DAY_OF_WEEK_IN_MONTH));
        assertEquals(ValueRange.of(0, 1, 0, 7), InternationalFixedChronology.INSTANCE.range(ALIGNED_DAY_OF_WEEK_IN_YEAR));
        assertEquals(ValueRange.of(0, 1, 0, 4), InternationalFixedChronology.INSTANCE.range(ALIGNED_WEEK_OF_MONTH));
        assertEquals(ValueRange.of(0, 1, 0, 52), InternationalFixedChronology.INSTANCE.range(ALIGNED_WEEK_OF_YEAR));

        assertEquals(ValueRange.of(0, 1, 0, 7), InternationalFixedChronology.INSTANCE.range(DAY_OF_WEEK));
        assertEquals(ValueRange.of(1, 29), InternationalFixedChronology.INSTANCE.range(DAY_OF_MONTH));
        assertEquals(ValueRange.of(1, 365, 366), InternationalFixedChronology.INSTANCE.range(DAY_OF_YEAR));

        assertEquals(ValueRange.of(1, 1), InternationalFixedChronology.INSTANCE.range(ERA));
        assertEquals(ValueRange.of(-719_528, 1_000_000 * 365L + 242_499 - 719_528), InternationalFixedChronology.INSTANCE.range(EPOCH_DAY));
        assertEquals(ValueRange.of(1, 13), InternationalFixedChronology.INSTANCE.range(MONTH_OF_YEAR));
        assertEquals(ValueRange.of(13, 1_000_000 * 13L - 1), InternationalFixedChronology.INSTANCE.range(PROLEPTIC_MONTH));
        assertEquals(ValueRange.of(1, 1_000_000), InternationalFixedChronology.INSTANCE.range(YEAR));
        assertEquals(ValueRange.of(1, 1_000_000), InternationalFixedChronology.INSTANCE.range(YEAR_OF_ERA));
    }
}
