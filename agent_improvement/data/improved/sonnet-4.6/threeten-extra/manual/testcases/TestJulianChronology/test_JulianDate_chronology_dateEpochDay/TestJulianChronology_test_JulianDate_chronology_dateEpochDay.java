package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestJulianChronology_test_JulianDate_chronology_dateEpochDay {

    /**
     * Pairs of (JulianDate, equivalent ISO LocalDate) used to verify that
     * converting an ISO epoch-day through JulianChronology reproduces the
     * original Julian date.  Each row exercises a distinct calendar boundary:
     * the Julian-to-Gregorian offset, century leap-year differences, the
     * 1582 switch-over point, and modern dates.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // Julian year 1, January — maps two days before ISO 0001-01-01
            { JulianDate.of(1, 1, 1),    LocalDate.of(0, 12, 30) },
            { JulianDate.of(1, 1, 2),    LocalDate.of(0, 12, 31) },
            { JulianDate.of(1, 1, 3),    LocalDate.of(1,  1,  1) },

            // Julian year 1, February/March boundary (no leap year)
            { JulianDate.of(1, 2, 28),   LocalDate.of(1,  2, 26) },
            { JulianDate.of(1, 3,  1),   LocalDate.of(1,  2, 27) },
            { JulianDate.of(1, 3,  2),   LocalDate.of(1,  2, 28) },
            { JulianDate.of(1, 3,  3),   LocalDate.of(1,  3,  1) },

            // Julian year 4, February/March boundary (leap year — Julian has Feb 29)
            { JulianDate.of(4, 2, 28),   LocalDate.of(4,  2, 26) },
            { JulianDate.of(4, 2, 29),   LocalDate.of(4,  2, 27) },
            { JulianDate.of(4, 3,  1),   LocalDate.of(4,  2, 28) },
            { JulianDate.of(4, 3,  2),   LocalDate.of(4,  2, 29) },
            { JulianDate.of(4, 3,  3),   LocalDate.of(4,  3,  1) },

            // Julian year 100 — leap in Julian, not in Gregorian (offset grows by 1 day here)
            { JulianDate.of(100, 2, 28), LocalDate.of(100, 2, 26) },
            { JulianDate.of(100, 2, 29), LocalDate.of(100, 2, 27) },
            { JulianDate.of(100, 3,  1), LocalDate.of(100, 2, 28) },
            { JulianDate.of(100, 3,  2), LocalDate.of(100, 3,  1) },
            { JulianDate.of(100, 3,  3), LocalDate.of(100, 3,  2) },

            // Dates just before/at the proleptic epoch (year 0 in Julian)
            { JulianDate.of(0, 12, 31),  LocalDate.of(0, 12, 29) },
            { JulianDate.of(0, 12, 30),  LocalDate.of(0, 12, 28) },

            // Gregorian reform boundary (1582-10-04 Julian = 1582-10-14 Gregorian)
            { JulianDate.of(1582, 10,  4), LocalDate.of(1582, 10, 14) },
            { JulianDate.of(1582, 10,  5), LocalDate.of(1582, 10, 15) },

            // Modern date — 13-day offset between Julian and Gregorian in the 20th century
            { JulianDate.of(1945, 10, 30), LocalDate.of(1945, 11, 12) },

            // Modern date — 13-day offset in the 21st century
            { JulianDate.of(2012,  6, 22), LocalDate.of(2012,  7,  5) },
            { JulianDate.of(2012,  6, 23), LocalDate.of(2012,  7,  6) },
        };
    }

    /**
     * Verifies that {@link JulianChronology#dateEpochDay(long)} correctly
     * reconstructs a Julian date from the epoch-day of its ISO equivalent.
     *
     * <p>The ISO {@link LocalDate#toEpochDay()} value is computed from the
     * Gregorian/ISO date, and the result of {@code dateEpochDay} must equal
     * the corresponding Julian date.
     */
    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_JulianDate_chronology_dateEpochDay(JulianDate julian, LocalDate iso) {
        assertEquals(julian, JulianChronology.INSTANCE.dateEpochDay(iso.toEpochDay()));
    }
}
