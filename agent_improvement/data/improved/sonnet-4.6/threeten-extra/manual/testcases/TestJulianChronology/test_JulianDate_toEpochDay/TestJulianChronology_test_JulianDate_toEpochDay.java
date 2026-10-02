package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that JulianDate.toEpochDay() returns the same epoch-day value as the
 * equivalent proleptic ISO (Gregorian) LocalDate.
 *
 * Julian and Gregorian calendars diverge because the Julian calendar treats
 * every 4th year as a leap year, while the Gregorian skips century years
 * (unless divisible by 400). The offset accumulates over time:
 *   - Before 200 AD  : Julian dates are 2 days behind ISO
 *   - Around 1582    : Julian dates are 10 days behind ISO (Gregorian reform)
 *   - Modern era     : Julian dates are 13 days behind ISO
 */
public class TestJulianChronology_test_JulianDate_toEpochDay {

    /**
     * Pairs of (JulianDate, equivalent ISO LocalDate) that must share the same epoch-day.
     *
     * Groups:
     *  1. Year 1 AD – the very start of the proleptic Julian calendar; Julian 1-Jan-1
     *     maps to ISO 0-Dec-30, showing the 2-day initial offset.
     *  2. Julian leap year 4 AD – Julian has Feb 29 in year 4; Gregorian also does,
     *     but the dates still differ by 2.
     *  3. Julian year 100 – Julian treats it as a leap year; Gregorian does not.
     *     After March this introduces an extra day of divergence (now 3 days).
     *  4. Year 0 BC – demonstrates dates just before year 1.
     *  5. 1582 – the Gregorian calendar reform year; the 10-day offset is visible.
     *  6. Modern dates – show the full 13-day accumulated offset.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // --- Year 1 AD: initial 2-day offset between Julian and ISO ---
            { JulianDate.of(1, 1, 1),  LocalDate.of(0, 12, 30) },
            { JulianDate.of(1, 1, 2),  LocalDate.of(0, 12, 31) },
            { JulianDate.of(1, 1, 3),  LocalDate.of(1,  1,  1) },
            { JulianDate.of(1, 2, 28), LocalDate.of(1,  2, 26) },
            { JulianDate.of(1, 3, 1),  LocalDate.of(1,  2, 27) },
            { JulianDate.of(1, 3, 2),  LocalDate.of(1,  2, 28) },
            { JulianDate.of(1, 3, 3),  LocalDate.of(1,  3,  1) },

            // --- Year 4 AD: first Julian leap year (both calendars have Feb 29) ---
            { JulianDate.of(4, 2, 28), LocalDate.of(4,  2, 26) },
            { JulianDate.of(4, 2, 29), LocalDate.of(4,  2, 27) },
            { JulianDate.of(4, 3, 1),  LocalDate.of(4,  2, 28) },
            { JulianDate.of(4, 3, 2),  LocalDate.of(4,  2, 29) },
            { JulianDate.of(4, 3, 3),  LocalDate.of(4,  3,  1) },

            // --- Year 100: Julian leap, Gregorian non-leap; offset grows to 3 after Feb ---
            { JulianDate.of(100, 2, 28), LocalDate.of(100, 2, 26) },
            { JulianDate.of(100, 2, 29), LocalDate.of(100, 2, 27) },
            { JulianDate.of(100, 3, 1),  LocalDate.of(100, 2, 28) },
            { JulianDate.of(100, 3, 2),  LocalDate.of(100, 3,  1) },
            { JulianDate.of(100, 3, 3),  LocalDate.of(100, 3,  2) },

            // --- Year 0 (1 BC): dates just before year 1 AD ---
            { JulianDate.of(0, 12, 31), LocalDate.of(0, 12, 29) },
            { JulianDate.of(0, 12, 30), LocalDate.of(0, 12, 28) },

            // --- 1582: Gregorian calendar reform; 10-day offset visible ---
            { JulianDate.of(1582, 10, 4), LocalDate.of(1582, 10, 14) },
            { JulianDate.of(1582, 10, 5), LocalDate.of(1582, 10, 15) },

            // --- Modern era: 13-day accumulated offset ---
            { JulianDate.of(1945, 10, 30), LocalDate.of(1945, 11, 12) },
            { JulianDate.of(2012,  6, 22), LocalDate.of(2012,  7,  5) },
            { JulianDate.of(2012,  6, 23), LocalDate.of(2012,  7,  6) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_JulianDate_toEpochDay(JulianDate julian, LocalDate iso) {
        assertEquals(iso.toEpochDay(), julian.toEpochDay());
    }
}
