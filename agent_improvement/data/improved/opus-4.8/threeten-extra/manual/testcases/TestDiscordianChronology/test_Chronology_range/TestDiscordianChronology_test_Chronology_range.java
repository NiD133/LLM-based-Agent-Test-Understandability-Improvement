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
 * Verifies the valid value ranges that {@link DiscordianChronology} reports for each
 * supported temporal field.
 * <p>
 * In the Discordian calendar a year has 5 months of 73 days (5-day weeks). Several fields
 * use a {@link ValueRange} with two minimums to model "St. Tib's Day", the leap-day that
 * sits outside every month and week: its field value is {@code 0}, hence the smallest
 * minimum of {@code 0} versus the regular minimum of {@code 1}.
 */
public class TestDiscordianChronology_test_Chronology_range {

    private final DiscordianChronology chronology = DiscordianChronology.INSTANCE;

    @Test
    public void test_Chronology_range() {
        // Week-aligned fields: regular values run 1..5, but St. Tib's Day reports 0.
        assertEquals(ValueRange.of(0, 1, 0, 5), chronology.range(ALIGNED_DAY_OF_WEEK_IN_MONTH));
        assertEquals(ValueRange.of(0, 1, 5, 5), chronology.range(ALIGNED_DAY_OF_WEEK_IN_YEAR));
        assertEquals(ValueRange.of(0, 1, 0, 5), chronology.range(DAY_OF_WEEK));

        // Week-of-period fields.
        assertEquals(ValueRange.of(0, 1, 0, 15), chronology.range(ALIGNED_WEEK_OF_MONTH));
        assertEquals(ValueRange.of(0, 1, 73, 73), chronology.range(ALIGNED_WEEK_OF_YEAR));

        // Day and month counts.
        assertEquals(ValueRange.of(0, 1, 0, 73), chronology.range(DAY_OF_MONTH));
        assertEquals(ValueRange.of(1, 365, 366), chronology.range(DAY_OF_YEAR));
        assertEquals(ValueRange.of(0, 1, 5, 5), chronology.range(MONTH_OF_YEAR));

        // Year and era fields. There is a single era (YOLD).
        assertEquals(ValueRange.of(1, 999_999), chronology.range(YEAR));
        assertEquals(ValueRange.of(1, 999_999), chronology.range(YEAR_OF_ERA));
        assertEquals(ValueRange.of(1, 1), chronology.range(ERA));

        // Epoch-relative fields, derived from the supported year range.
        assertEquals(ValueRange.of(-1_145_400, 999_999 * 365L + 242_499), chronology.range(EPOCH_DAY));
        assertEquals(ValueRange.of(0, 999_999 * 5L + 5 - 1), chronology.range(PROLEPTIC_MONTH));
    }
}
