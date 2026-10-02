package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestJulianChronology_test_minus_Period {

    @Test
    public void test_minus_Period() {
        // Subtract 0 years, 2 months, 3 days from 2014-05-26 → expect 2014-03-23
        JulianDate start    = JulianDate.of(2014, 5, 26);
        JulianDate expected = JulianDate.of(2014, 3, 23);
        assertEquals(expected, start.minus(JulianChronology.INSTANCE.period(0, 2, 3)));
    }
}
