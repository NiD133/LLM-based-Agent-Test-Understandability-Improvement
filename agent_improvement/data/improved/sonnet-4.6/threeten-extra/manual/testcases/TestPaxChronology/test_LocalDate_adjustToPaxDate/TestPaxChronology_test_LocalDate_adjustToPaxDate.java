package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Verifies that a {@link PaxDate} can act as a {@link java.time.temporal.TemporalAdjuster}
 * for a {@link LocalDate}, converting it to the ISO date that corresponds to the same
 * epoch-day as the given Pax date.
 *
 * <p>Pax 2012-06-23 maps to ISO 2012-06-04 (Pax year 2012, month 6, day 23 falls on
 * the same epoch-day as ISO 4 June 2012).
 */
@SuppressWarnings("static-method")
public class TestPaxChronology_test_LocalDate_adjustToPaxDate {

    @Test
    public void test_LocalDate_adjustToPaxDate() {
        // PaxDate implements TemporalAdjuster; calling LocalDate.with(paxDate)
        // returns the ISO LocalDate for the same epoch-day.
        PaxDate pax = PaxDate.of(2012, 6, 23);
        LocalDate result = LocalDate.MIN.with(pax);
        assertEquals(LocalDate.of(2012, 6, 4), result);
    }
}
