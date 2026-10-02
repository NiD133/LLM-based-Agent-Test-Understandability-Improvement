package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.Test;

/**
 * Tests that a {@link PaxDate} can be adjusted with the standard
 * {@link TemporalAdjusters#lastDayOfMonth()} adjuster.
 */
@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_adjust1 {

    @Test
    public void test_adjust1() {
        // The 6th Pax month has 28 days, so the last day of June 2012 is the 28th.
        PaxDate june23 = PaxDate.of(2012, 6, 23);

        PaxDate lastDayOfJune = june23.with(TemporalAdjusters.lastDayOfMonth());

        assertEquals(PaxDate.of(2012, 6, 28), lastDayOfJune);
    }
}
