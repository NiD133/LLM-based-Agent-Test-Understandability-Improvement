package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

/**
 * Verifies that adjusting a {@link LocalDateTime} with a {@link JulianDate}
 * converts the Julian calendar date into the equivalent ISO date, while
 * leaving the time-of-day untouched.
 */
public class TestJulianChronology_test_LocalDateTime_adjustToJulianDate {

    @Test
    public void test_LocalDateTime_adjustToJulianDate() {
        // Julian 2012-06-23 corresponds to ISO 2012-07-06.
        JulianDate julianDate = JulianDate.of(2012, 6, 23);

        // Adjusting LocalDateTime.MIN replaces only the date part with the
        // converted ISO date; the time stays at the start of the day (00:00).
        LocalDateTime adjusted = LocalDateTime.MIN.with(julianDate);

        assertEquals(LocalDateTime.of(2012, 7, 6, 0, 0), adjusted);
    }
}
