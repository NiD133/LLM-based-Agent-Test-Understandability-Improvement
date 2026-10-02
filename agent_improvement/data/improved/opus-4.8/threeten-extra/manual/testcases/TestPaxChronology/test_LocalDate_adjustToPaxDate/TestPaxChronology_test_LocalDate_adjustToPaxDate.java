package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Tests that a {@link PaxDate} can be used as a {@code TemporalAdjuster} to
 * move a {@link LocalDate} onto the ISO date that corresponds to that Pax date.
 */
@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_LocalDate_adjustToPaxDate {

    @Test
    public void test_LocalDate_adjustToPaxDate() {
        // The Pax date 2012-06-23 corresponds to the ISO date 2012-06-04.
        PaxDate paxDate = PaxDate.of(2012, 6, 23);
        LocalDate expectedIsoDate = LocalDate.of(2012, 6, 4);

        // Adjusting any LocalDate (here LocalDate.MIN) with a PaxDate yields the
        // equivalent ISO date, regardless of the starting value.
        LocalDate adjustedIsoDate = LocalDate.MIN.with(paxDate);

        assertEquals(expectedIsoDate, adjustedIsoDate);
    }
}
