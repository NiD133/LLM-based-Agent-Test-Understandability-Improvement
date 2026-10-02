package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestJulianChronology_test_adjust_toLocalDate {

    // Verifies that adjusting a JulianDate to an ISO LocalDate correctly converts
    // the ISO date back into the equivalent Julian calendar date.
    @Test
    public void test_adjust_toLocalDate() {
        JulianDate julian = JulianDate.of(2000, 1, 4);
        JulianDate test = julian.with(LocalDate.of(2012, 7, 6));
        assertEquals(JulianDate.of(2012, 6, 23), test);
    }
}
