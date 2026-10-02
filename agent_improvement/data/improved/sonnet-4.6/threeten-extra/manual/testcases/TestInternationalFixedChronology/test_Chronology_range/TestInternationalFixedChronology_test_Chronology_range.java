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

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_Chronology_range {

    @Test
    public void test_Chronology_range() {
        InternationalFixedChronology chrono = InternationalFixedChronology.INSTANCE;

        // Special days (Leap Day, Year Day) are outside normal weeks, so their aligned/week fields
        // can be 0. Hence ValueRange.of(smallestMin, largestMin, smallestMax, largestMax).
        assertEquals(ValueRange.of(0, 1, 0, 7), chrono.range(ALIGNED_DAY_OF_WEEK_IN_MONTH));
        assertEquals(ValueRange.of(0, 1, 0, 7), chrono.range(ALIGNED_DAY_OF_WEEK_IN_YEAR));
        assertEquals(ValueRange.of(0, 1, 0, 4), chrono.range(ALIGNED_WEEK_OF_MONTH));
        assertEquals(ValueRange.of(0, 1, 0, 52), chrono.range(ALIGNED_WEEK_OF_YEAR));
        assertEquals(ValueRange.of(0, 1, 0, 7), chrono.range(DAY_OF_WEEK));

        // Day and month fields
        assertEquals(ValueRange.of(1, 29), chrono.range(DAY_OF_MONTH));
        assertEquals(ValueRange.of(1, 365, 366), chrono.range(DAY_OF_YEAR));
        assertEquals(ValueRange.of(1, 13), chrono.range(MONTH_OF_YEAR));

        // Era, year, and proleptic-month fields
        assertEquals(ValueRange.of(1, 1), chrono.range(ERA));
        assertEquals(ValueRange.of(1, 1_000_000), chrono.range(YEAR));
        assertEquals(ValueRange.of(1, 1_000_000), chrono.range(YEAR_OF_ERA));
        assertEquals(ValueRange.of(13, 1_000_000 * 13L - 1), chrono.range(PROLEPTIC_MONTH));

        // Epoch-day range spans from the start of year 1 to the end of year 1_000_000
        assertEquals(ValueRange.of(-719_528, 1_000_000 * 365L + 242_499 - 719_528), chrono.range(EPOCH_DAY));
    }
}
