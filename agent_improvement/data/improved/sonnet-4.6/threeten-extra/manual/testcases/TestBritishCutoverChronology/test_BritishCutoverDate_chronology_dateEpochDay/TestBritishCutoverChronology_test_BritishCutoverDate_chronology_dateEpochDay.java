package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link BritishCutoverChronology#dateEpochDay(long)} correctly round-trips
 * from an ISO {@link LocalDate} epoch-day back to the equivalent {@link BritishCutoverDate}.
 *
 * <p>The data covers the full range of interesting cases:
 * <ul>
 *   <li>Dates well before the Julian/Gregorian cutover (year 1 through early 1582)</li>
 *   <li>Dates around the 1582 Vatican cutover (Oct 1582)</li>
 *   <li>Dates around the British cutover transition: 2 Sep 1752 → 14 Sep 1752</li>
 *   <li>Dates that fall in the "skipped" cutover gap and are accepted leniently</li>
 *   <li>Dates well after the cutover (Gregorian era)</li>
 * </ul>
 */
public class TestBritishCutoverChronology_test_BritishCutoverDate_chronology_dateEpochDay {

    /**
     * Pairs of (BritishCutoverDate, equivalent ISO LocalDate) used to verify
     * that converting the ISO epoch-day through the chronology yields the correct
     * BritishCutoverDate.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // Early Julian dates (year 1)
            { BritishCutoverDate.of(1, 1, 1),    LocalDate.of(0, 12, 30) },
            { BritishCutoverDate.of(1, 1, 2),    LocalDate.of(0, 12, 31) },
            { BritishCutoverDate.of(1, 1, 3),    LocalDate.of(1,  1,  1) },
            { BritishCutoverDate.of(1, 2, 28),   LocalDate.of(1,  2, 26) },
            { BritishCutoverDate.of(1, 3, 1),    LocalDate.of(1,  2, 27) },
            { BritishCutoverDate.of(1, 3, 2),    LocalDate.of(1,  2, 28) },
            { BritishCutoverDate.of(1, 3, 3),    LocalDate.of(1,  3,  1) },

            // Julian leap year (year 4)
            { BritishCutoverDate.of(4, 2, 28),   LocalDate.of(4,  2, 26) },
            { BritishCutoverDate.of(4, 2, 29),   LocalDate.of(4,  2, 27) },
            { BritishCutoverDate.of(4, 3, 1),    LocalDate.of(4,  2, 28) },
            { BritishCutoverDate.of(4, 3, 2),    LocalDate.of(4,  2, 29) },
            { BritishCutoverDate.of(4, 3, 3),    LocalDate.of(4,  3,  1) },

            // Julian century year — leap in Julian but not Gregorian (year 100)
            { BritishCutoverDate.of(100, 2, 28), LocalDate.of(100, 2, 26) },
            { BritishCutoverDate.of(100, 2, 29), LocalDate.of(100, 2, 27) },
            { BritishCutoverDate.of(100, 3, 1),  LocalDate.of(100, 2, 28) },
            { BritishCutoverDate.of(100, 3, 2),  LocalDate.of(100, 3,  1) },
            { BritishCutoverDate.of(100, 3, 3),  LocalDate.of(100, 3,  2) },

            // BC dates (year 0 in proleptic notation)
            { BritishCutoverDate.of(0, 12, 31),  LocalDate.of(0, 12, 29) },
            { BritishCutoverDate.of(0, 12, 30),  LocalDate.of(0, 12, 28) },

            // Around the 1582 Vatican cutover (10-day gap already accumulated)
            { BritishCutoverDate.of(1582, 10,  4), LocalDate.of(1582, 10, 14) },
            { BritishCutoverDate.of(1582, 10,  5), LocalDate.of(1582, 10, 15) },

            // Pre-British-cutover: the Julian/Gregorian offset is 11 days
            { BritishCutoverDate.of(1751, 12, 20), LocalDate.of(1751, 12, 31) },
            { BritishCutoverDate.of(1751, 12, 31), LocalDate.of(1752,  1, 11) },
            { BritishCutoverDate.of(1752,  1,  1), LocalDate.of(1752,  1, 12) },
            { BritishCutoverDate.of(1752,  9,  1), LocalDate.of(1752,  9, 12) },
            { BritishCutoverDate.of(1752,  9,  2), LocalDate.of(1752,  9, 13) },

            // Dates inside the skipped gap (3–13 Sep 1752) — accepted leniently
            { BritishCutoverDate.of(1752,  9,  3), LocalDate.of(1752,  9, 14) }, // leniently accept invalid
            { BritishCutoverDate.of(1752,  9, 13), LocalDate.of(1752,  9, 24) }, // leniently accept invalid

            // First Gregorian day and immediately after the gap
            { BritishCutoverDate.of(1752,  9, 14), LocalDate.of(1752,  9, 14) },

            // Modern Gregorian dates (ISO-aligned after cutover)
            { BritishCutoverDate.of(1945, 11, 12), LocalDate.of(1945, 11, 12) },
            { BritishCutoverDate.of(2012,  7,  5), LocalDate.of(2012,  7,  5) },
            { BritishCutoverDate.of(2012,  7,  6), LocalDate.of(2012,  7,  6) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_BritishCutoverDate_chronology_dateEpochDay(BritishCutoverDate cutover, LocalDate iso) {
        assertEquals(cutover, BritishCutoverChronology.INSTANCE.dateEpochDay(iso.toEpochDay()));
    }
}
