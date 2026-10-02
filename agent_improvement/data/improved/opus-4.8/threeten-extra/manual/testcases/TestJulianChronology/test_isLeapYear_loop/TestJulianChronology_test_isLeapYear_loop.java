package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies the Julian leap-year rule: a proleptic year is a leap year
 * if and only if it is exactly divisible by four.
 */
public class TestJulianChronology_test_isLeapYear_loop {

    @Test
    public void test_isLeapYear_loop() {
        // Walk across a range of proleptic years spanning both eras (BC and AD).
        for (int year = -200; year < 200; year++) {
            boolean expectedLeap = (year % 4) == 0;

            // The rule must hold both via the JulianDate instance...
            JulianDate date = JulianDate.of(year, 1, 1);
            assertEquals(expectedLeap, date.isLeapYear());

            // ...and via the JulianChronology singleton.
            assertEquals(expectedLeap, JulianChronology.INSTANCE.isLeapYear(year));
        }
    }
}
