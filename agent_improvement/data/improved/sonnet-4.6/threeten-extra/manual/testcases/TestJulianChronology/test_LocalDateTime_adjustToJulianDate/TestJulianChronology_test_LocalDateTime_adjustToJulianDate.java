package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class TestJulianChronology_test_LocalDateTime_adjustToJulianDate {

    /**
     * Verifies that adjusting LocalDateTime.MIN to a JulianDate produces the
     * correct ISO LocalDateTime. Julian 2012-06-23 corresponds to ISO 2012-07-06
     * (the Julian calendar runs 13 days behind the Gregorian calendar by 2012).
     */
    @Test
    public void test_LocalDateTime_adjustToJulianDate() {
        JulianDate julian = JulianDate.of(2012, 6, 23);
        LocalDateTime result = LocalDateTime.MIN.with(julian);
        assertEquals(LocalDateTime.of(2012, 7, 6, 0, 0), result);
    }
}
