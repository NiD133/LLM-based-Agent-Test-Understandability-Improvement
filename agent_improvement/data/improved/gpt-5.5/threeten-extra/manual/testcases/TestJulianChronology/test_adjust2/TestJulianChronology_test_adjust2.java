package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.Test;

public class TestJulianChronology_test_adjust2 {

    @Test
    public void test_adjust2() {
        JulianDate dateInLeapFebruary = JulianDate.of(2012, 2, 23);
        TemporalAdjuster lastDayOfMonth = TemporalAdjusters.lastDayOfMonth();
        JulianDate expectedLeapDay = JulianDate.of(2012, 2, 29);

        JulianDate adjustedDate = dateInLeapFebruary.with(lastDayOfMonth);

        assertEquals(expectedLeapDay, adjustedDate);
    }
}
