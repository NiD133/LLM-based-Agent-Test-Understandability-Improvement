package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestJulianChronology_test_LocalDate_adjustToJulianDate {

    // Verifies that a LocalDate adjusts correctly to its ISO equivalent
    // when a JulianDate is used as a TemporalAdjuster via LocalDate.with().
    // Julian 2012-06-23 is 13 days behind the Gregorian calendar in the 20th/21st century,
    // so the expected ISO date is 2012-07-06.
    @Test
    public void test_LocalDate_adjustToJulianDate() {
        JulianDate julianDate = JulianDate.of(2012, 6, 23);
        LocalDate isoDate = LocalDate.MIN.with(julianDate);
        assertEquals(LocalDate.of(2012, 7, 6), isoDate);
    }
}
