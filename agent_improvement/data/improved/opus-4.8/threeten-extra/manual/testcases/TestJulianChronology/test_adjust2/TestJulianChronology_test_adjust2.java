package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.Test;

/**
 * Tests adjusting a {@link JulianDate} with a {@link TemporalAdjusters} adjuster.
 */
public class TestJulianChronology_test_adjust2 {

    @Test
    public void test_adjust2() {
        // 2012 is a Julian leap year, so February has 29 days.
        JulianDate dateInFebruary = JulianDate.of(2012, 2, 23);

        JulianDate lastDayOfFebruary = dateInFebruary.with(TemporalAdjusters.lastDayOfMonth());

        assertEquals(JulianDate.of(2012, 2, 29), lastDayOfFebruary);
    }
}
