package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.Test;

public class TestJulianChronology_test_adjust1 {

    // Verifies that a TemporalAdjuster (lastDayOfMonth) can be applied to a JulianDate,
    // adjusting June 23 to the last day of June (day 30) in Julian year 2012.
    @Test
    public void test_adjust1() {
        JulianDate base = JulianDate.of(2012, 6, 23);
        JulianDate test = base.with(TemporalAdjusters.lastDayOfMonth());
        assertEquals(JulianDate.of(2012, 6, 30), test);
    }
}
