package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.api.Test;

public class TestJulianChronology_test_minus_Period {

    @Test
    public void test_minus_Period() {
        JulianDate startDate = JulianDate.of(2014, 5, 26);
        ChronoPeriod periodToSubtract = JulianChronology.INSTANCE.period(0, 2, 3);
        JulianDate expectedDate = JulianDate.of(2014, 3, 23);

        assertEquals(expectedDate, startDate.minus(periodToSubtract));
    }
}
