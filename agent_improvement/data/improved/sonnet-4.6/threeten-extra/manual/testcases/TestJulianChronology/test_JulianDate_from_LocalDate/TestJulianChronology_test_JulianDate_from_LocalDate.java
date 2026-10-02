package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link JulianDate#from(java.time.temporal.TemporalAccessor)} correctly converts
 * an ISO {@link LocalDate} to the equivalent proleptic Julian calendar date.
 *
 * <p>The Julian calendar diverges from the ISO/Gregorian calendar because:
 * <ul>
 *   <li>Julian epoch 0001-01-01 maps to ISO 0000-12-30 (a two-day offset at the epoch).</li>
 *   <li>Julian leap years occur every 4 years without exception (no century-year skip),
 *       causing an additional one-day drift in century years such as 100 AD.</li>
 *   <li>The Gregorian reform (October 1582) is not applied – dates continue unbroken.</li>
 * </ul>
 */
public class TestJulianChronology_test_JulianDate_from_LocalDate {

    /**
     * Provides (julianDate, isoDate) pairs that must satisfy
     * {@code JulianDate.from(isoDate).equals(julianDate)}.
     *
     * <p>Rows are grouped by the calendar feature they exercise:
     * <ol>
     *   <li>Epoch boundary – first days of Julian year 1 map to late ISO year 0.</li>
     *   <li>Early leap-year handling – year 4 has a Julian Feb 29; ISO Feb 28/29 shift accordingly.</li>
     *   <li>Century-year drift – year 100 is a Julian leap year but not an ISO leap year,
     *       so ISO dates after Julian Feb 29 are one day ahead of their Julian counterparts.</li>
     *   <li>Cross-era rows around year 0 / year 1 boundary.</li>
     *   <li>Gregorian reform boundary (Julian 1582-10-04 == ISO 1582-10-14) and beyond.</li>
     *   <li>Modern dates to confirm the accumulated 13-day difference by year 2012.</li>
     * </ol>
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // --- Epoch boundary: Julian year 1 starts two days before ISO year 1 ---
            { JulianDate.of(1, 1, 1),    LocalDate.of(0, 12, 30) },
            { JulianDate.of(1, 1, 2),    LocalDate.of(0, 12, 31) },
            { JulianDate.of(1, 1, 3),    LocalDate.of(1,  1,  1) },

            // --- Year 1: February has 28 days in both calendars; March shows 2-day offset ---
            { JulianDate.of(1, 2, 28),   LocalDate.of(1,  2, 26) },
            { JulianDate.of(1, 3,  1),   LocalDate.of(1,  2, 27) },
            { JulianDate.of(1, 3,  2),   LocalDate.of(1,  2, 28) },
            { JulianDate.of(1, 3,  3),   LocalDate.of(1,  3,  1) },

            // --- Year 4: Julian has Feb 29 (leap); ISO also has Feb 29; same 2-day offset ---
            { JulianDate.of(4, 2, 28),   LocalDate.of(4,  2, 26) },
            { JulianDate.of(4, 2, 29),   LocalDate.of(4,  2, 27) },
            { JulianDate.of(4, 3,  1),   LocalDate.of(4,  2, 28) },
            { JulianDate.of(4, 3,  2),   LocalDate.of(4,  2, 29) },
            { JulianDate.of(4, 3,  3),   LocalDate.of(4,  3,  1) },

            // --- Year 100: Julian leap, ISO non-leap → offset grows to 3 days after Feb 28 ---
            { JulianDate.of(100, 2, 28), LocalDate.of(100, 2, 26) },
            { JulianDate.of(100, 2, 29), LocalDate.of(100, 2, 27) },  // Julian Feb 29 maps to ISO Feb 27
            { JulianDate.of(100, 3,  1), LocalDate.of(100, 2, 28) },  // drift grows: now 3 days
            { JulianDate.of(100, 3,  2), LocalDate.of(100, 3,  1) },
            { JulianDate.of(100, 3,  3), LocalDate.of(100, 3,  2) },

            // --- Cross-era rows: year 0 in proleptic Julian ---
            { JulianDate.of(0, 12, 31),  LocalDate.of(0, 12, 29) },
            { JulianDate.of(0, 12, 30),  LocalDate.of(0, 12, 28) },

            // --- Gregorian reform boundary (10-day gap inserted in ISO Oct 1582) ---
            { JulianDate.of(1582, 10, 4), LocalDate.of(1582, 10, 14) },
            { JulianDate.of(1582, 10, 5), LocalDate.of(1582, 10, 15) },

            // --- Modern dates: 13-day accumulated difference by the 20th/21st century ---
            { JulianDate.of(1945, 10, 30), LocalDate.of(1945, 11, 12) },
            { JulianDate.of(2012,  6, 22), LocalDate.of(2012,  7,  5) },
            { JulianDate.of(2012,  6, 23), LocalDate.of(2012,  7,  6) },
        };
    }

    /**
     * Verifies that converting an ISO {@link LocalDate} via {@link JulianDate#from} yields
     * the expected Julian date for every entry in {@link #data_samples()}.
     */
    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_JulianDate_from_LocalDate(JulianDate julian, LocalDate iso) {
        assertEquals(julian, JulianDate.from(iso));
    }
}
