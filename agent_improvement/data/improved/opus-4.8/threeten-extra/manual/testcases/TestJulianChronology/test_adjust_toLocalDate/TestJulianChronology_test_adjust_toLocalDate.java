package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

/**
 * Tests adjusting a {@link JulianDate} to match a given ISO {@link LocalDate}.
 *
 * <p>{@code JulianDate.with(LocalDate)} replaces the date with the Julian
 * equivalent of the supplied ISO date. Because the Julian calendar drifts from
 * the ISO/Gregorian calendar, the same calendar day is represented by different
 * year/month/day numbers in each system.
 */
public class TestJulianChronology_test_adjust_toLocalDate {

    @Test
    public void test_adjust_toLocalDate() {
        // Start from an arbitrary Julian date; its actual value is irrelevant
        // because with(LocalDate) overwrites the date entirely.
        JulianDate startingPoint = JulianDate.of(2000, 1, 4);

        // Adjust it to the Julian equivalent of ISO date 2012-07-06.
        JulianDate adjusted = startingPoint.with(LocalDate.of(2012, 7, 6));

        // ISO 2012-07-06 corresponds to Julian 2012-06-23 (a 13-day offset).
        assertEquals(JulianDate.of(2012, 6, 23), adjusted);
    }
}
