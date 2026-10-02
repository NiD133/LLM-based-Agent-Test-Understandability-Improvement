package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.Test;

public class TestJulianChronology_test_adjust1 {

    @Test
    public void test_adjust1() {
        JulianDate dateInJune = JulianDate.of(2012, 6, 23);

        JulianDate actualEndOfMonth = dateInJune.with(TemporalAdjusters.lastDayOfMonth());

        assertEquals(JulianDate.of(2012, 6, 30), actualEndOfMonth);
    }
}
