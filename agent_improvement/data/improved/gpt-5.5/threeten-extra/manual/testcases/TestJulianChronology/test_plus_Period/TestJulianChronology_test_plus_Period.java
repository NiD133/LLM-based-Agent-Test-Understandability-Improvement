package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestJulianChronology_test_plus_Period {

    @Test
    public void test_plus_Period() {
        JulianDate startDate = JulianDate.of(2014, 5, 26);
        JulianDate expectedDate = JulianDate.of(2014, 7, 29);

        assertEquals(
                expectedDate,
                startDate.plus(JulianChronology.INSTANCE.period(0, 2, 3)));
    }
}
