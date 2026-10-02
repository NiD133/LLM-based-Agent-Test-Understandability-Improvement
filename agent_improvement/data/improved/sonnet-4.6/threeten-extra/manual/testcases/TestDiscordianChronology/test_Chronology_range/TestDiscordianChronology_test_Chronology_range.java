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

public class TestDiscordianChronology_test_Chronology_range {

    // ValueRange.of(smallestMinimum, largestMinimum, smallestMaximum, largestMaximum)
    // captures how field boundaries vary across dates.  St. Tib's Day (the Discordian
    // leap-day) sits in pseudo-month 0 and is not part of any regular week, so every
    // week/day field may legitimately be 0 on that day — hence the minimum of 0.

    @Test
    public void test_Chronology_range() {

        // --- Week-alignment fields ---
        // St. Tib's Day is not part of any Discordian week, so its alignment value is 0.
        // In a regular month/year the values run 1–5 (days per week) or 1–15 (weeks per month).
        assertEquals(ValueRange.of(0, 1, 0,  5),  DiscordianChronology.INSTANCE.range(ALIGNED_DAY_OF_WEEK_IN_MONTH));
        assertEquals(ValueRange.of(0, 1, 5,  5),  DiscordianChronology.INSTANCE.range(ALIGNED_DAY_OF_WEEK_IN_YEAR));
        assertEquals(ValueRange.of(0, 1, 0, 15),  DiscordianChronology.INSTANCE.range(ALIGNED_WEEK_OF_MONTH));
        assertEquals(ValueRange.of(0, 1, 73, 73), DiscordianChronology.INSTANCE.range(ALIGNED_WEEK_OF_YEAR));
        assertEquals(ValueRange.of(0, 1, 0,  5),  DiscordianChronology.INSTANCE.range(DAY_OF_WEEK));

        // --- Day fields ---
        // DAY_OF_MONTH is 0 on St. Tib's Day; regular days run 1–73.
        // DAY_OF_YEAR ranges 1–365 in normal years and 1–366 in leap years.
        assertEquals(ValueRange.of(0, 1, 0,  73), DiscordianChronology.INSTANCE.range(DAY_OF_MONTH));
        assertEquals(ValueRange.of(1, 365, 366),  DiscordianChronology.INSTANCE.range(DAY_OF_YEAR));

        // --- Epoch day ---
        // Lower bound corresponds to YOLD year 1; upper bound to year 999,999.
        assertEquals(ValueRange.of(-1_145_400, 999_999 * 365L + 242_499),
                DiscordianChronology.INSTANCE.range(EPOCH_DAY));

        // --- Month fields ---
        // St. Tib's Day belongs to pseudo-month 0; normal months are 1–5.
        // PROLEPTIC_MONTH is zero-based across the full supported year range.
        assertEquals(ValueRange.of(0, 1, 5,  5),              DiscordianChronology.INSTANCE.range(MONTH_OF_YEAR));
        assertEquals(ValueRange.of(0, 999_999 * 5L + 5 - 1), DiscordianChronology.INSTANCE.range(PROLEPTIC_MONTH));

        // --- Year and era fields ---
        // There is exactly one era (YOLD), and proleptic years run from 1 to 999,999.
        assertEquals(ValueRange.of(1, 1),       DiscordianChronology.INSTANCE.range(ERA));
        assertEquals(ValueRange.of(1, 999_999), DiscordianChronology.INSTANCE.range(YEAR));
        assertEquals(ValueRange.of(1, 999_999), DiscordianChronology.INSTANCE.range(YEAR_OF_ERA));
    }
}
