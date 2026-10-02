package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestJulianChronology_test_LocalDate_adjustToJulianDate {

    @Test
    public void test_LocalDate_adjustToJulianDate() {
        JulianDate julianDate = JulianDate.of(2012, 6, 23);

        LocalDate adjustedDate = LocalDate.MIN.with(julianDate);

        assertEquals(LocalDate.of(2012, 7, 6), adjustedDate);
    }
}
