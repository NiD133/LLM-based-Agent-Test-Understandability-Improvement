package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link InternationalFixedDate#minus(java.time.temporal.TemporalAmount)} with a
 * {@link java.time.chrono.ChronoPeriod} produced by the International Fixed chronology.
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_minus_Period {

    @Test
    public void test_minus_Period() {
        // Subtracting a period of 2 months and 3 days from 2014-05-26 yields 2014-03-23.
        InternationalFixedDate startDate = InternationalFixedDate.of(2014, 5, 26);
        java.time.chrono.ChronoPeriod twoMonthsThreeDays = InternationalFixedChronology.INSTANCE.period(0, 2, 3);

        InternationalFixedDate result = startDate.minus(twoMonthsThreeDays);

        assertEquals(InternationalFixedDate.of(2014, 3, 23), result);
    }
}
