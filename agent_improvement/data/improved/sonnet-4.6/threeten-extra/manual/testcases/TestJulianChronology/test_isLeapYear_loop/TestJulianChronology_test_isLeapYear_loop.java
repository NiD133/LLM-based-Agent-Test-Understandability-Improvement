package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestJulianChronology_test_isLeapYear_loop {

    @Test
    public void test_isLeapYear_loop() {
        for (int year = -200; year < 200; year++) {
            JulianDate base = JulianDate.of(year, 1, 1);
            assertEquals((year % 4) == 0, base.isLeapYear());
            assertEquals((year % 4) == 0, JulianChronology.INSTANCE.isLeapYear(year));
        }
    }
}
