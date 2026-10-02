package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

/**
 * Verifies that a {@link JulianDate} can be used as a {@code TemporalAdjuster}
 * to convert an ISO {@link LocalDate} into the equivalent date on the ISO time-line.
 */
public class TestJulianChronology_test_LocalDate_adjustToJulianDate {

    @Test
    public void test_LocalDate_adjustToJulianDate() {
        // Julian 2012-06-23 corresponds to ISO 2012-07-06 (13-day Julian/Gregorian offset).
        JulianDate julianDate = JulianDate.of(2012, 6, 23);

        // Adjusting any LocalDate with the JulianDate yields that equivalent ISO date.
        LocalDate adjustedIsoDate = LocalDate.MIN.with(julianDate);

        assertEquals(LocalDate.of(2012, 7, 6), adjustedIsoDate);
    }
}
