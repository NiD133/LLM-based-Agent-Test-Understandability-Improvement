package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.temporal.TemporalAdjusters;
import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_adjust1 {

    /**
     * Verifies that applying the last-day-of-month adjuster to a Pax date
     * correctly returns day 28, which is the last day of any regular Pax month.
     */
    @Test
    public void test_adjust1() {
        // Pax year 2012, month 6, day 23 — a mid-month date in a regular 28-day month
        PaxDate base = PaxDate.of(2012, 6, 23);

        // Adjust to the last day of the same month
        PaxDate adjusted = base.with(TemporalAdjusters.lastDayOfMonth());

        // Regular Pax months have 28 days, so the last day is day 28
        assertEquals(PaxDate.of(2012, 6, 28), adjusted);
    }
}
