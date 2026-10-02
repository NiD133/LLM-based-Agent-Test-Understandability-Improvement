package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Period;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that each Julian calendar date and its corresponding ISO LocalDate represent
 * the same instant in time, so that {@code iso.until(julian)} returns {@link Period#ZERO}.
 */
public class TestJulianChronology_test_LocalDate_until_JulianDate {

    /**
     * Pairs of (JulianDate, equivalent ISO LocalDate).
     * The Julian calendar runs 2 days behind ISO in the first century AD,
     * diverges further around century boundaries (no Gregorian leap correction),
     * and by the 20th century is 13 days behind ISO.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // Year 1 AD — Julian epoch starts 2 days before ISO epoch
            { JulianDate.of(1,  1,  1),  LocalDate.of(0,  12, 30) },
            { JulianDate.of(1,  1,  2),  LocalDate.of(0,  12, 31) },
            { JulianDate.of(1,  1,  3),  LocalDate.of(1,   1,  1) },

            // End of February, year 1 (not a leap year in either calendar)
            { JulianDate.of(1,  2, 28),  LocalDate.of(1,   2, 26) },
            { JulianDate.of(1,  3,  1),  LocalDate.of(1,   2, 27) },
            { JulianDate.of(1,  3,  2),  LocalDate.of(1,   2, 28) },
            { JulianDate.of(1,  3,  3),  LocalDate.of(1,   3,  1) },

            // End of February, year 4 (leap year in both calendars — offset stays at 2)
            { JulianDate.of(4,  2, 28),  LocalDate.of(4,   2, 26) },
            { JulianDate.of(4,  2, 29),  LocalDate.of(4,   2, 27) },
            { JulianDate.of(4,  3,  1),  LocalDate.of(4,   2, 28) },
            { JulianDate.of(4,  3,  2),  LocalDate.of(4,   2, 29) },
            { JulianDate.of(4,  3,  3),  LocalDate.of(4,   3,  1) },

            // End of February, year 100 (leap in Julian but NOT in Gregorian — offset grows to 3)
            { JulianDate.of(100, 2, 28), LocalDate.of(100,  2, 26) },
            { JulianDate.of(100, 2, 29), LocalDate.of(100,  2, 27) },
            { JulianDate.of(100, 3,  1), LocalDate.of(100,  2, 28) },
            { JulianDate.of(100, 3,  2), LocalDate.of(100,  3,  1) },
            { JulianDate.of(100, 3,  3), LocalDate.of(100,  3,  2) },

            // Proleptic BC dates (year 0 in Julian)
            { JulianDate.of(0,  12, 31), LocalDate.of(0,  12, 29) },
            { JulianDate.of(0,  12, 30), LocalDate.of(0,  12, 28) },

            // Gregorian calendar reform in 1582 (Julian Oct 4 is directly followed by ISO Oct 15)
            { JulianDate.of(1582, 10,  4), LocalDate.of(1582, 10, 14) },
            { JulianDate.of(1582, 10,  5), LocalDate.of(1582, 10, 15) },

            // 20th century — offset is 13 days
            { JulianDate.of(1945, 10, 30), LocalDate.of(1945, 11, 12) },

            // Modern dates — offset is still 13 days
            { JulianDate.of(2012,  6, 22), LocalDate.of(2012,  7,  5) },
            { JulianDate.of(2012,  6, 23), LocalDate.of(2012,  7,  6) },
        };
    }

    /**
     * Verifies that {@code iso.until(julian)} returns {@link Period#ZERO}, confirming that
     * each Julian date and its paired ISO date represent the same point on the timeline.
     */
    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_LocalDate_until_JulianDate(JulianDate julian, LocalDate iso) {
        assertEquals(Period.ZERO, iso.until(julian));
    }
}
