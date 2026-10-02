package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.Test;

/**
 * Tests that a {@link JulianDate} can be adjusted with a standard
 * {@link TemporalAdjusters} instance.
 */
public class TestJulianChronology_test_adjust1 {

    @Test
    public void adjustToLastDayOfMonth_returnsLastDayOfThatMonth() {
        JulianDate dateInJune = JulianDate.of(2012, 6, 23);

        JulianDate adjusted = dateInJune.with(TemporalAdjusters.lastDayOfMonth());

        assertEquals(JulianDate.of(2012, 6, 30), adjusted);
    }
}
