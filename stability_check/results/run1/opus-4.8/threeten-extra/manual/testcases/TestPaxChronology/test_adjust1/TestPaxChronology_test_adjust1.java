package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_adjust1 {

    /**
     * Adjusting a PaxDate with {@link TemporalAdjusters#lastDayOfMonth()} should
     * move the date to the final day of its month. In the Pax calendar month 6 of
     * a normal year has 28 days, so 2012-06-23 becomes 2012-06-28.
     */
    @Test
    public void test_adjust1() {
        PaxDate dateInMonth = PaxDate.of(2012, 6, 23);

        PaxDate lastDayOfMonth = dateInMonth.with(TemporalAdjusters.lastDayOfMonth());

        assertEquals(PaxDate.of(2012, 6, 28), lastDayOfMonth);
    }
}
