package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_adjust1 {

    @Test
    public void test_adjust1() {
        PaxDate baseDate = PaxDate.of(2012, 6, 23);

        PaxDate lastDayOfMonth = baseDate.with(TemporalAdjusters.lastDayOfMonth());

        assertEquals(PaxDate.of(2012, 6, 28), lastDayOfMonth);
    }
}
