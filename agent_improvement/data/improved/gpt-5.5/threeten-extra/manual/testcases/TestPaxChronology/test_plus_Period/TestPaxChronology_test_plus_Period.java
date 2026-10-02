package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.api.Test;

public class TestPaxChronology_test_plus_Period {

    @Test
    public void test_plus_Period() {
        PaxDate startDate = PaxDate.of(2014, 5, 26);
        ChronoPeriod periodToAdd = PaxChronology.INSTANCE.period(0, 2, 2);
        PaxDate expectedDate = PaxDate.of(2014, 7, 28);

        assertEquals(expectedDate, startDate.plus(periodToAdd));
    }
}
