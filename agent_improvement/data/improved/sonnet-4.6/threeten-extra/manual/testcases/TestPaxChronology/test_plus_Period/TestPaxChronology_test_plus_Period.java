package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_plus_Period {

    @Test
    public void test_plus_Period() {
        PaxDate startDate = PaxDate.of(2014, 5, 26);
        // period of 0 years, 2 months, and 2 days
        ChronoPeriod period = PaxChronology.INSTANCE.period(0, 2, 2);
        PaxDate expectedDate = PaxDate.of(2014, 7, 28);
        assertEquals(expectedDate, startDate.plus(period));
    }
}
