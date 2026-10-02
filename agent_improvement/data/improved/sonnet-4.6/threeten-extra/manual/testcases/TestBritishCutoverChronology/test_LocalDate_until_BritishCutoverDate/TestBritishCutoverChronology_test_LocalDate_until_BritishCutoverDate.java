package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Period;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestBritishCutoverChronology_test_LocalDate_until_BritishCutoverDate {

    /**
     * Pairs of (BritishCutoverDate, ISO LocalDate) that represent the same instant.
     * Each BritishCutoverDate and its paired LocalDate are the same day, so
     * LocalDate.until(BritishCutoverDate) should always be Period.ZERO.
     *
     * Before the cutover (pre-1752) the Julian calendar is 2 days behind ISO,
     * at the 1582 gap the difference is 10 days, and after Sep 14 1752 the
     * calendars are identical.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // --- Early proleptic dates (Julian offset = 2 days behind ISO) ---
            { BritishCutoverDate.of(1, 1, 1),    LocalDate.of(0, 12, 30) },
            { BritishCutoverDate.of(1, 1, 2),    LocalDate.of(0, 12, 31) },
            { BritishCutoverDate.of(1, 1, 3),    LocalDate.of(1, 1, 1)  },
            { BritishCutoverDate.of(1, 2, 28),   LocalDate.of(1, 2, 26) },
            { BritishCutoverDate.of(1, 3, 1),    LocalDate.of(1, 2, 27) },
            { BritishCutoverDate.of(1, 3, 2),    LocalDate.of(1, 2, 28) },
            { BritishCutoverDate.of(1, 3, 3),    LocalDate.of(1, 3, 1)  },
            // --- Julian leap-year handling (year 4 is a leap year in both calendars) ---
            { BritishCutoverDate.of(4, 2, 28),   LocalDate.of(4, 2, 26) },
            { BritishCutoverDate.of(4, 2, 29),   LocalDate.of(4, 2, 27) },
            { BritishCutoverDate.of(4, 3, 1),    LocalDate.of(4, 2, 28) },
            { BritishCutoverDate.of(4, 3, 2),    LocalDate.of(4, 2, 29) },
            { BritishCutoverDate.of(4, 3, 3),    LocalDate.of(4, 3, 1)  },
            // --- Julian century year (year 100 is a leap year in Julian but not Gregorian) ---
            { BritishCutoverDate.of(100, 2, 28), LocalDate.of(100, 2, 26) },
            { BritishCutoverDate.of(100, 2, 29), LocalDate.of(100, 2, 27) },
            { BritishCutoverDate.of(100, 3, 1),  LocalDate.of(100, 2, 28) },
            { BritishCutoverDate.of(100, 3, 2),  LocalDate.of(100, 3, 1)  },
            { BritishCutoverDate.of(100, 3, 3),  LocalDate.of(100, 3, 2)  },
            // --- Year 0 (proleptic BC 1) ---
            { BritishCutoverDate.of(0, 12, 31),  LocalDate.of(0, 12, 29) },
            { BritishCutoverDate.of(0, 12, 30),  LocalDate.of(0, 12, 28) },
            // --- Around 1582: Gregorian reform (10-day gap elsewhere, but not in Britain yet) ---
            { BritishCutoverDate.of(1582, 10, 4), LocalDate.of(1582, 10, 14) },
            { BritishCutoverDate.of(1582, 10, 5), LocalDate.of(1582, 10, 15) },
            // --- Late Julian period approaching British cutover (1751) ---
            { BritishCutoverDate.of(1751, 12, 20), LocalDate.of(1751, 12, 31) },
            { BritishCutoverDate.of(1751, 12, 31), LocalDate.of(1752, 1, 11) },
            // --- 1752: year of the British Julian-to-Gregorian cutover ---
            { BritishCutoverDate.of(1752, 1, 1),  LocalDate.of(1752, 1, 12) },
            { BritishCutoverDate.of(1752, 9, 1),  LocalDate.of(1752, 9, 12) },
            { BritishCutoverDate.of(1752, 9, 2),  LocalDate.of(1752, 9, 13) },
            { BritishCutoverDate.of(1752, 9, 3),  LocalDate.of(1752, 9, 14) }, // leniently accept invalid
            { BritishCutoverDate.of(1752, 9, 13), LocalDate.of(1752, 9, 24) },
            // --- First day of Gregorian (ISO) alignment in Britain ---
            { BritishCutoverDate.of(1752, 9, 14), LocalDate.of(1752, 9, 14) },
            // --- Post-cutover: calendars are identical ---
            { BritishCutoverDate.of(1945, 11, 12), LocalDate.of(1945, 11, 12) },
            { BritishCutoverDate.of(2012, 7, 5),   LocalDate.of(2012, 7, 5)  },
            { BritishCutoverDate.of(2012, 7, 6),   LocalDate.of(2012, 7, 6)  },
        };
    }

    /**
     * Verifies that a BritishCutoverDate and its ISO LocalDate equivalent represent
     * the same instant, i.e. LocalDate.until(BritishCutoverDate) == Period.ZERO.
     */
    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_LocalDate_until_BritishCutoverDate(BritishCutoverDate cutover, LocalDate iso) {
        assertEquals(Period.ZERO, iso.until(cutover));
    }
}
