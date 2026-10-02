package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link JulianDate#until(java.time.temporal.Temporal)} returns a zero period
 * when the target ISO LocalDate is the exact ISO equivalent of the source Julian date.
 * Each row in data_samples pairs a JulianDate with its corresponding ISO LocalDate
 * (the two represent the same instant on the proleptic calendar timeline).
 */
public class TestJulianChronology_test_JulianDate_until_LocalDate {

    public static Object[][] data_samples() {
        return new Object[][] {
            // Julian date 1-01-01 corresponds to ISO 0000-12-30
            { JulianDate.of(1, 1, 1),    LocalDate.of(0, 12, 30) },
            { JulianDate.of(1, 1, 2),    LocalDate.of(0, 12, 31) },
            { JulianDate.of(1, 1, 3),    LocalDate.of(1,  1,  1) },
            { JulianDate.of(1, 2, 28),   LocalDate.of(1,  2, 26) },
            { JulianDate.of(1, 3,  1),   LocalDate.of(1,  2, 27) },
            { JulianDate.of(1, 3,  2),   LocalDate.of(1,  2, 28) },
            { JulianDate.of(1, 3,  3),   LocalDate.of(1,  3,  1) },

            // Year 4: Julian is a leap year (Feb 29 exists)
            { JulianDate.of(4, 2, 28),   LocalDate.of(4,  2, 26) },
            { JulianDate.of(4, 2, 29),   LocalDate.of(4,  2, 27) },
            { JulianDate.of(4, 3,  1),   LocalDate.of(4,  2, 28) },
            { JulianDate.of(4, 3,  2),   LocalDate.of(4,  2, 29) },
            { JulianDate.of(4, 3,  3),   LocalDate.of(4,  3,  1) },

            // Year 100: Julian is a leap year; ISO/Gregorian is not
            { JulianDate.of(100, 2, 28), LocalDate.of(100, 2, 26) },
            { JulianDate.of(100, 2, 29), LocalDate.of(100, 2, 27) },
            { JulianDate.of(100, 3,  1), LocalDate.of(100, 2, 28) },
            { JulianDate.of(100, 3,  2), LocalDate.of(100, 3,  1) },
            { JulianDate.of(100, 3,  3), LocalDate.of(100, 3,  2) },

            // Boundary around year 0 / year 1 BC
            { JulianDate.of(0, 12, 31),  LocalDate.of(0, 12, 29) },
            { JulianDate.of(0, 12, 30),  LocalDate.of(0, 12, 28) },

            // Gregorian calendar reform boundary (Oct 1582)
            { JulianDate.of(1582, 10, 4), LocalDate.of(1582, 10, 14) },
            { JulianDate.of(1582, 10, 5), LocalDate.of(1582, 10, 15) },

            // Modern dates
            { JulianDate.of(1945, 10, 30), LocalDate.of(1945, 11, 12) },
            { JulianDate.of(2012,  6, 22), LocalDate.of(2012,  7,  5) },
            { JulianDate.of(2012,  6, 23), LocalDate.of(2012,  7,  6) },
        };
    }

    /**
     * When a JulianDate is compared to the ISO LocalDate that represents the same
     * calendar day, {@code until} must return a zero-length period.
     */
    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_JulianDate_until_LocalDate(JulianDate julian, LocalDate iso) {
        assertEquals(JulianChronology.INSTANCE.period(0, 0, 0), julian.until(iso));
    }
}
