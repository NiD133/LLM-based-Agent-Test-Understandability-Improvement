package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class TestJulianChronology_test_LocalDateTime_adjustToJulianDate {

    @Test
    public void test_LocalDateTime_adjustToJulianDate() {
        JulianDate julianDate = JulianDate.of(2012, 6, 23);

        LocalDateTime adjustedDateTime = LocalDateTime.MIN.with(julianDate);

        assertEquals(LocalDateTime.of(2012, 7, 6, 0, 0), adjustedDateTime);
    }
}
