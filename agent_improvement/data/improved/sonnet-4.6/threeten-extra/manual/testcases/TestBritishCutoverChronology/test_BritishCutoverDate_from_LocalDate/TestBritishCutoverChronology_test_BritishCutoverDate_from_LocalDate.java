package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link BritishCutoverDate#from(java.time.temporal.TemporalAccessor)}
 * correctly converts an ISO {@link LocalDate} to the corresponding
 * {@link BritishCutoverDate}.
 *
 * <p>Before the British cutover on 14 September 1752, dates follow the Julian
 * calendar, which is 11 days behind the Gregorian (ISO) calendar at that point.
 * On and after the cutover date, both calendars agree.
 */
public class TestBritishCutoverChronology_test_BritishCutoverDate_from_LocalDate {

    /**
     * Pairs of (expected BritishCutoverDate, ISO LocalDate) used by
     * {@link #test_BritishCutoverDate_from_LocalDate}.
     *
     * <p>The table is organised into five groups:
     * <ol>
     *   <li>Proleptic Julian dates around year 1 AD — the British calendar starts
     *       two days before the ISO epoch (ISO 0000-12-30 = British 0001-01-01).</li>
     *   <li>Julian leap-year behaviour: years 4 and 100 are leap years under
     *       Julian rules (unlike the Gregorian rule that skips century years).</li>
     *   <li>BC dates (proleptic year 0).</li>
     *   <li>Dates in the run-up to the 1752 British cutover, where the offset
     *       between Julian and Gregorian has grown to 11 days.</li>
     *   <li>The cutover gap itself (Sep 3–13 1752 did not exist in Britain):
     *       those day-of-month values are accepted leniently and treated as
     *       Julian dates shifted by 11 days into the post-gap Gregorian range.</li>
     *   <li>On and after the cutover date (Sep 14 1752) the British calendar
     *       matches the ISO calendar exactly.</li>
     * </ol>
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // --- Proleptic Julian dates around year 1 AD ---
            // British 0001-01-01 corresponds to ISO 0000-12-30 (2-day offset at epoch)
            { BritishCutoverDate.of(1, 1, 1),   LocalDate.of(0,  12, 30) },
            { BritishCutoverDate.of(1, 1, 2),   LocalDate.of(0,  12, 31) },
            { BritishCutoverDate.of(1, 1, 3),   LocalDate.of(1,   1,  1) },
            { BritishCutoverDate.of(1, 2, 28),  LocalDate.of(1,   2, 26) },
            { BritishCutoverDate.of(1, 3,  1),  LocalDate.of(1,   2, 27) },
            { BritishCutoverDate.of(1, 3,  2),  LocalDate.of(1,   2, 28) },
            { BritishCutoverDate.of(1, 3,  3),  LocalDate.of(1,   3,  1) },

            // --- Julian leap-year: year 4 has Feb 29 (same as Gregorian) ---
            { BritishCutoverDate.of(4, 2, 28),  LocalDate.of(4,   2, 26) },
            { BritishCutoverDate.of(4, 2, 29),  LocalDate.of(4,   2, 27) },
            { BritishCutoverDate.of(4, 3,  1),  LocalDate.of(4,   2, 28) },
            { BritishCutoverDate.of(4, 3,  2),  LocalDate.of(4,   2, 29) },
            { BritishCutoverDate.of(4, 3,  3),  LocalDate.of(4,   3,  1) },

            // --- Julian leap-year: year 100 is a leap year under Julian rules ---
            // (Gregorian skips it, so from here the calendars diverge by 1 extra day)
            { BritishCutoverDate.of(100, 2, 28), LocalDate.of(100, 2, 26) },
            { BritishCutoverDate.of(100, 2, 29), LocalDate.of(100, 2, 27) },
            { BritishCutoverDate.of(100, 3,  1), LocalDate.of(100, 2, 28) },
            { BritishCutoverDate.of(100, 3,  2), LocalDate.of(100, 3,  1) },
            { BritishCutoverDate.of(100, 3,  3), LocalDate.of(100, 3,  2) },

            // --- BC (proleptic year 0) ---
            { BritishCutoverDate.of(0, 12, 31),  LocalDate.of(0,  12, 29) },
            { BritishCutoverDate.of(0, 12, 30),  LocalDate.of(0,  12, 28) },

            // --- Dates around 1582: the Vatican cutover (10-day gap, not British) ---
            // Britain still used Julian in 1582, so these map with a 10-day ISO offset
            { BritishCutoverDate.of(1582, 10,  4), LocalDate.of(1582, 10, 14) },
            { BritishCutoverDate.of(1582, 10,  5), LocalDate.of(1582, 10, 15) },

            // --- Dates close to the 1752 British cutover (11-day Julian/Gregorian gap) ---
            { BritishCutoverDate.of(1751, 12, 20), LocalDate.of(1751, 12, 31) },
            { BritishCutoverDate.of(1751, 12, 31), LocalDate.of(1752,  1, 11) },
            { BritishCutoverDate.of(1752,  1,  1), LocalDate.of(1752,  1, 12) },
            { BritishCutoverDate.of(1752,  9,  1), LocalDate.of(1752,  9, 12) },
            { BritishCutoverDate.of(1752,  9,  2), LocalDate.of(1752,  9, 13) },

            // --- Dates inside the cutover gap (Sep 3–13 1752 never existed in Britain) ---
            // The implementation leniently accepts them as Julian dates and shifts by 11 days
            { BritishCutoverDate.of(1752,  9,  3), LocalDate.of(1752,  9, 14) }, // leniently accept invalid
            { BritishCutoverDate.of(1752,  9, 13), LocalDate.of(1752,  9, 24) }, // leniently accept invalid

            // --- On and after the cutover date: British calendar == ISO calendar ---
            { BritishCutoverDate.of(1752,  9, 14), LocalDate.of(1752,  9, 14) },
            { BritishCutoverDate.of(1945, 11, 12), LocalDate.of(1945, 11, 12) },
            { BritishCutoverDate.of(2012,  7,  5), LocalDate.of(2012,  7,  5) },
            { BritishCutoverDate.of(2012,  7,  6), LocalDate.of(2012,  7,  6) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_BritishCutoverDate_from_LocalDate(BritishCutoverDate cutover, LocalDate iso) {
        assertEquals(cutover, BritishCutoverDate.from(iso));
    }
}
