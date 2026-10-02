package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_minus_Period {

    @Test
    public void test_minus_Period() {
        // Subtracting a period of (0 years, 2 months, 3 days) from Pax date 2014-05-26
        // should yield Pax date 2014-03-23
        PaxDate startDate = PaxDate.of(2014, 5, 26);
        ChronoPeriod twoMonthsAndThreeDays = PaxChronology.INSTANCE.period(0, 2, 3);
        PaxDate expectedDate = PaxDate.of(2014, 3, 23);

        assertEquals(expectedDate, startDate.minus(twoMonthsAndThreeDays));
    }
}
