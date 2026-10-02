package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link JulianDate#until(java.time.temporal.Temporal, java.time.temporal.TemporalUnit)}
 * measured in {@link java.time.temporal.ChronoUnit#DAYS DAYS}.
 * <p>
 * Each case pairs a Julian date with its equivalent ISO {@link LocalDate}. Because the two
 * represent the very same day, the number of days between them is exactly the offset that was
 * added to (or subtracted from) the ISO date.
 */
public class TestJulianChronology_test_until_DAYS {

    /**
     * Provides equivalent (Julian date, ISO date) pairs that denote the same calendar day.
     */
    public static Object[][] equivalentDatePairs() {
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

    @ParameterizedTest
    @MethodSource("equivalentDatePairs")
    public void until_inDays_returnsOffsetFromEquivalentIsoDate(JulianDate julian, LocalDate equivalentIso) {
        // The Julian date and the ISO date are the same day, so the distance equals the day offset.
        assertEquals(0, julian.until(equivalentIso.plusDays(0), DAYS));
        assertEquals(1, julian.until(equivalentIso.plusDays(1), DAYS));
        assertEquals(35, julian.until(equivalentIso.plusDays(35), DAYS));
        assertEquals(-40, julian.until(equivalentIso.minusDays(40), DAYS));
    }
}
