package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.Test;

public class TestJulianChronology_test_adjust2 {

    // Verifies that applying lastDayOfMonth() to a Julian date in a leap year
    // returns day 29 for February (Julian leap year rule: divisible by 4).
    @Test
    public void test_adjust2() {
        JulianDate base = JulianDate.of(2012, 2, 23);
        JulianDate test = base.with(TemporalAdjusters.lastDayOfMonth());
        assertEquals(JulianDate.of(2012, 2, 29), test);
    }
}
