package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests adding a Pax {@link java.time.chrono.ChronoPeriod} to a {@link PaxDate}.
 */
@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_plus_Period {

    @Test
    public void test_plus_Period() {
        // Adding a period of 0 years, 2 months and 2 days to 2014-05-26
        // advances the date to 2014-07-28.
        PaxDate startDate = PaxDate.of(2014, 5, 26);
        java.time.chrono.ChronoPeriod twoMonthsTwoDays = PaxChronology.INSTANCE.period(0, 2, 2);

        PaxDate result = startDate.plus(twoMonthsTwoDays);

        assertEquals(PaxDate.of(2014, 7, 28), result);
    }
}
