package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that adding a number of days to a {@link JulianDate} matches the
 * equivalent day arithmetic on the corresponding ISO {@link LocalDate}.
 */
public class TestJulianChronology_test_plusDays {

    /**
     * Pairs of equivalent dates: a {@link JulianDate} and the ISO
     * {@link LocalDate} that represents the very same point on the time-line.
     * The samples cover early proleptic years, leap years, century boundaries
     * and dates around the historical Julian/Gregorian transition.
     */
    public static Object[][] equivalentJulianAndIsoDates() {
        return new Object[][] {
            { JulianDate.of(1, 1, 1), LocalDate.of(0, 12, 30) },
            { JulianDate.of(1, 1, 2), LocalDate.of(0, 12, 31) },
            { JulianDate.of(1, 1, 3), LocalDate.of(1, 1, 1) },
            { JulianDate.of(1, 2, 28), LocalDate.of(1, 2, 26) },
            { JulianDate.of(1, 3, 1), LocalDate.of(1, 2, 27) },
            { JulianDate.of(1, 3, 2), LocalDate.of(1, 2, 28) },
            { JulianDate.of(1, 3, 3), LocalDate.of(1, 3, 1) },
            { JulianDate.of(4, 2, 28), LocalDate.of(4, 2, 26) },
            { JulianDate.of(4, 2, 29), LocalDate.of(4, 2, 27) },
            { JulianDate.of(4, 3, 1), LocalDate.of(4, 2, 28) },
            { JulianDate.of(4, 3, 2), LocalDate.of(4, 2, 29) },
            { JulianDate.of(4, 3, 3), LocalDate.of(4, 3, 1) },
            { JulianDate.of(100, 2, 28), LocalDate.of(100, 2, 26) },
            { JulianDate.of(100, 2, 29), LocalDate.of(100, 2, 27) },
            { JulianDate.of(100, 3, 1), LocalDate.of(100, 2, 28) },
            { JulianDate.of(100, 3, 2), LocalDate.of(100, 3, 1) },
            { JulianDate.of(100, 3, 3), LocalDate.of(100, 3, 2) },
            { JulianDate.of(0, 12, 31), LocalDate.of(0, 12, 29) },
            { JulianDate.of(0, 12, 30), LocalDate.of(0, 12, 28) },
            { JulianDate.of(1582, 10, 4), LocalDate.of(1582, 10, 14) },
            { JulianDate.of(1582, 10, 5), LocalDate.of(1582, 10, 15) },
            { JulianDate.of(1945, 10, 30), LocalDate.of(1945, 11, 12) },
            { JulianDate.of(2012, 6, 22), LocalDate.of(2012, 7, 5) },
            { JulianDate.of(2012, 6, 23), LocalDate.of(2012, 7, 6) },
        };
    }

    /**
     * For each equivalent date pair, adding a given number of days to the
     * Julian date must land on the same calendar day as adding the same number
     * of days to the ISO date.
     */
    @ParameterizedTest
    @MethodSource("equivalentJulianAndIsoDates")
    public void plusDays_matchesIsoDayArithmetic(JulianDate julian, LocalDate iso) {
        assertEquals(iso, LocalDate.from(julian.plus(0, DAYS)));
        assertEquals(iso.plusDays(1), LocalDate.from(julian.plus(1, DAYS)));
        assertEquals(iso.plusDays(35), LocalDate.from(julian.plus(35, DAYS)));
        assertEquals(iso.plusDays(-1), LocalDate.from(julian.plus(-1, DAYS)));
        assertEquals(iso.plusDays(-60), LocalDate.from(julian.plus(-60, DAYS)));
    }
}
