package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestJulianChronology_test_adjust_toLocalDate {

    @Test
    public void test_adjust_toLocalDate() {
        JulianDate originalJulianDate = JulianDate.of(2000, 1, 4);
        LocalDate isoAdjustmentDate = LocalDate.of(2012, 7, 6);

        JulianDate adjustedJulianDate = originalJulianDate.with(isoAdjustmentDate);

        assertEquals(JulianDate.of(2012, 6, 23), adjustedJulianDate);
    }
}
