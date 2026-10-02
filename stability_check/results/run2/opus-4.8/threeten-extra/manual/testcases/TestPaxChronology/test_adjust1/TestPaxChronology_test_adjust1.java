package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.Test;

/**
 * Tests that adjusting a {@link PaxDate} with a {@link TemporalAdjusters} works as expected.
 */
@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_adjust1 {

    @Test
    public void adjustingToLastDayOfMonthReturnsDay28() {
        // Pax months (other than the leap month) have 28 days, so the last day of month 6 is the 28th.
        PaxDate midMonthDate = PaxDate.of(2012, 6, 23);

        PaxDate lastDayOfMonth = midMonthDate.with(TemporalAdjusters.lastDayOfMonth());

        assertEquals(PaxDate.of(2012, 6, 28), lastDayOfMonth);
    }
}
