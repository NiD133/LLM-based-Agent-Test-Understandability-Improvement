package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_adjust1 {

    // Verifies that lastDayOfMonth() adjuster moves a mid-month Pax date to day 28,
    // the last day of a standard (non-leap) Pax month.
    @Test
    public void test_adjust1() {
        PaxDate base = PaxDate.of(2012, 6, 23);
        PaxDate adjusted = base.with(TemporalAdjusters.lastDayOfMonth());
        assertEquals(PaxDate.of(2012, 6, 28), adjusted);
    }
}
