package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestBritishCutoverChronology_test_LocalDate_from_BritishCutoverDate {

    /**
     * Pairs of (BritishCutoverDate, expected ISO LocalDate) used to verify that
     * LocalDate.from(cutoverDate) returns the correct proleptic-Gregorian equivalent.
     *
     * The British calendar follows the Julian calendar until 14 Sep 1752 and the
     * Gregorian (ISO) calendar from that date onward.  Before the cutover the two
     * calendars diverge by an offset that grows over the centuries (2 days in year 1,
     * 11 days by 1752).  After the cutover the two calendars are identical.
     *
     * Cases are grouped by the region of the timeline they exercise:
     *   1. Year 1 (Julian/ISO offset = 2 days)
     *   2. Julian leap-year behaviour (year 4, year 100)
     *   3. BC dates (year 0 / year -1 proleptic)
     *   4. Around the 1582 Vatican introduction (offset already 10 days)
     *   5. 1751-1752 run-up to the British cutover (offset = 11 days)
     *   6. The cutover month itself (Sep 1752): Julian dates 3-13 map leniently
     *   7. Post-cutover dates: British == ISO
     */
    public static Object[][] data_samples() {
        return new Object[][] {

            // --- Year 1: Julian is 2 days ahead of ISO ---
            { BritishCutoverDate.of(1, 1, 1),  LocalDate.of(0, 12, 30) },
            { BritishCutoverDate.of(1, 1, 2),  LocalDate.of(0, 12, 31) },
            { BritishCutoverDate.of(1, 1, 3),  LocalDate.of(1,  1,  1) },
            { BritishCutoverDate.of(1, 2, 28), LocalDate.of(1,  2, 26) },
            { BritishCutoverDate.of(1, 3,  1), LocalDate.of(1,  2, 27) },
            { BritishCutoverDate.of(1, 3,  2), LocalDate.of(1,  2, 28) },
            { BritishCutoverDate.of(1, 3,  3), LocalDate.of(1,  3,  1) },

            // --- Julian leap year 4: Feb 29 exists in Julian, not in ISO ---
            { BritishCutoverDate.of(4, 2, 28), LocalDate.of(4,  2, 26) },
            { BritishCutoverDate.of(4, 2, 29), LocalDate.of(4,  2, 27) },
            { BritishCutoverDate.of(4, 3,  1), LocalDate.of(4,  2, 28) },
            { BritishCutoverDate.of(4, 3,  2), LocalDate.of(4,  2, 29) },
            { BritishCutoverDate.of(4, 3,  3), LocalDate.of(4,  3,  1) },

            // --- Year 100: leap in Julian, not in Gregorian (offset grows by 1) ---
            { BritishCutoverDate.of(100, 2, 28), LocalDate.of(100,  2, 26) },
            { BritishCutoverDate.of(100, 2, 29), LocalDate.of(100,  2, 27) },
            { BritishCutoverDate.of(100, 3,  1), LocalDate.of(100,  2, 28) },
            { BritishCutoverDate.of(100, 3,  2), LocalDate.of(100,  3,  1) },
            { BritishCutoverDate.of(100, 3,  3), LocalDate.of(100,  3,  2) },

            // --- BC / proleptic-year 0 ---
            { BritishCutoverDate.of(0, 12, 31), LocalDate.of(0, 12, 29) },
            { BritishCutoverDate.of(0, 12, 30), LocalDate.of(0, 12, 28) },

            // --- Around the Vatican cutover of 1582 (offset = 10 days) ---
            { BritishCutoverDate.of(1582, 10,  4), LocalDate.of(1582, 10, 14) },
            { BritishCutoverDate.of(1582, 10,  5), LocalDate.of(1582, 10, 15) },

            // --- Run-up to the British cutover: offset = 11 days ---
            { BritishCutoverDate.of(1751, 12, 20), LocalDate.of(1751, 12, 31) },
            { BritishCutoverDate.of(1751, 12, 31), LocalDate.of(1752,  1, 11) },
            { BritishCutoverDate.of(1752,  1,  1), LocalDate.of(1752,  1, 12) },
            { BritishCutoverDate.of(1752,  9,  1), LocalDate.of(1752,  9, 12) },
            { BritishCutoverDate.of(1752,  9,  2), LocalDate.of(1752,  9, 13) },

            // --- Gap days (Sep 3-13 1752): leniently accepted as Julian, shifted +11 ---
            { BritishCutoverDate.of(1752,  9,  3), LocalDate.of(1752,  9, 14) }, // leniently accept invalid
            { BritishCutoverDate.of(1752,  9, 13), LocalDate.of(1752,  9, 24) },

            // --- First day of Gregorian era in Britain: Sep 14 1752 ---
            { BritishCutoverDate.of(1752,  9, 14), LocalDate.of(1752,  9, 14) },

            // --- Post-cutover: British calendar == ISO calendar ---
            { BritishCutoverDate.of(1945, 11, 12), LocalDate.of(1945, 11, 12) },
            { BritishCutoverDate.of(2012,  7,  5), LocalDate.of(2012,  7,  5) },
            { BritishCutoverDate.of(2012,  7,  6), LocalDate.of(2012,  7,  6) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_LocalDate_from_BritishCutoverDate(BritishCutoverDate cutover, LocalDate iso) {
        assertEquals(iso, LocalDate.from(cutover));
    }
}
