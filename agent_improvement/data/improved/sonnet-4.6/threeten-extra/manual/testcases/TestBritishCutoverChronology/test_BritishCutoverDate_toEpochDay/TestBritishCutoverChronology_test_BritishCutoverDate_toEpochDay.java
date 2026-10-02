package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link BritishCutoverDate#toEpochDay()} returns the same epoch-day
 * as the equivalent ISO {@link LocalDate}.
 *
 * <p>The British calendar uses Julian rules before the cutover (Wednesday 2 Sep 1752)
 * and Gregorian (ISO) rules from Thursday 14 Sep 1752 onwards.  Julian dates are
 * 2 days behind ISO in year 1, growing to 11 days behind by 1752.  The test data
 * below covers every interesting boundary.
 */
public class TestBritishCutoverChronology_test_BritishCutoverDate_toEpochDay {

    /**
     * Pairs of (BritishCutoverDate, equivalent ISO LocalDate) used by
     * {@link #test_BritishCutoverDate_toEpochDay}.
     *
     * <p>Scenario groups:
     * <ul>
     *   <li>Very early Julian dates (year 1) — 2-day offset from ISO
     *   <li>Julian leap years: year 4 and year 100 (Julian has an extra Feb 29)
     *   <li>BCE boundary dates (year 0)
     *   <li>1582 region — the Gregorian reform date that Britain ignored
     *   <li>1751–1752 transition — year-start convention change and the 11-day gap
     *   <li>Post-cutover Gregorian dates (1945, 2012) — no offset from ISO
     * </ul>
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // --- Very early Julian dates (year 1): Julian is 2 days behind ISO ---
            { BritishCutoverDate.of(1, 1, 1),  LocalDate.of(0, 12, 30) },
            { BritishCutoverDate.of(1, 1, 2),  LocalDate.of(0, 12, 31) },
            { BritishCutoverDate.of(1, 1, 3),  LocalDate.of(1,  1,  1) },
            { BritishCutoverDate.of(1, 2, 28), LocalDate.of(1,  2, 26) },
            { BritishCutoverDate.of(1, 3,  1), LocalDate.of(1,  2, 27) },
            { BritishCutoverDate.of(1, 3,  2), LocalDate.of(1,  2, 28) },
            { BritishCutoverDate.of(1, 3,  3), LocalDate.of(1,  3,  1) },

            // --- Julian leap year 4: Julian has Feb 29, ISO (proleptic) does not ---
            { BritishCutoverDate.of(4, 2, 28), LocalDate.of(4, 2, 26) },
            { BritishCutoverDate.of(4, 2, 29), LocalDate.of(4, 2, 27) },
            { BritishCutoverDate.of(4, 3,  1), LocalDate.of(4, 2, 28) },
            { BritishCutoverDate.of(4, 3,  2), LocalDate.of(4, 2, 29) },
            { BritishCutoverDate.of(4, 3,  3), LocalDate.of(4, 3,  1) },

            // --- Julian leap year 100: Julian has Feb 29, Gregorian does not ---
            { BritishCutoverDate.of(100, 2, 28), LocalDate.of(100, 2, 26) },
            { BritishCutoverDate.of(100, 2, 29), LocalDate.of(100, 2, 27) },
            { BritishCutoverDate.of(100, 3,  1), LocalDate.of(100, 2, 28) },
            { BritishCutoverDate.of(100, 3,  2), LocalDate.of(100, 3,  1) },
            { BritishCutoverDate.of(100, 3,  3), LocalDate.of(100, 3,  2) },

            // --- BCE boundary (year 0 in proleptic numbering) ---
            { BritishCutoverDate.of(0, 12, 31), LocalDate.of(0, 12, 29) },
            { BritishCutoverDate.of(0, 12, 30), LocalDate.of(0, 12, 28) },

            // --- 1582: Gregorian reform that Britain did NOT adopt until 1752 ---
            // Before the cutover Britain still used Julian rules, so no gap here.
            { BritishCutoverDate.of(1582, 10,  4), LocalDate.of(1582, 10, 14) },
            { BritishCutoverDate.of(1582, 10,  5), LocalDate.of(1582, 10, 15) },

            // --- Approaching the 1752 cutover: 11-day Julian-vs-ISO offset ---
            { BritishCutoverDate.of(1751, 12, 20), LocalDate.of(1751, 12, 31) },
            { BritishCutoverDate.of(1751, 12, 31), LocalDate.of(1752,  1, 11) },
            { BritishCutoverDate.of(1752,  1,  1), LocalDate.of(1752,  1, 12) },

            // --- September 1752: the cutover month ---
            // Julian dates 1 and 2 Sep map to ISO 12 and 13 Sep (still pre-cutover).
            { BritishCutoverDate.of(1752, 9,  1), LocalDate.of(1752, 9, 12) },
            { BritishCutoverDate.of(1752, 9,  2), LocalDate.of(1752, 9, 13) },
            // Dates 3–13 are inside the skipped gap; leniently accepted as Julian.
            { BritishCutoverDate.of(1752, 9,  3), LocalDate.of(1752, 9, 14) }, // leniently accept invalid
            { BritishCutoverDate.of(1752, 9, 13), LocalDate.of(1752, 9, 24) },
            // 14 Sep 1752 is the first Gregorian (ISO) date — no offset from this point.
            { BritishCutoverDate.of(1752, 9, 14), LocalDate.of(1752, 9, 14) },

            // --- Post-cutover Gregorian dates: British == ISO, no offset ---
            { BritishCutoverDate.of(1945, 11, 12), LocalDate.of(1945, 11, 12) },
            { BritishCutoverDate.of(2012,  7,  5), LocalDate.of(2012,  7,  5) },
            { BritishCutoverDate.of(2012,  7,  6), LocalDate.of(2012,  7,  6) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_BritishCutoverDate_toEpochDay(BritishCutoverDate cutover, LocalDate iso) {
        assertEquals(iso.toEpochDay(), cutover.toEpochDay());
    }
}
