package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link BritishCutoverChronology#dateEpochDay(long)} reconstructs
 * the correct {@link BritishCutoverDate} from the ISO epoch-day of each sample date.
 *
 * <p>The British cutover calendar uses the Julian calendar before 14 Sep 1752 and
 * the Gregorian (ISO) calendar from that date onward. The two calendar systems
 * disagree on the mapping from calendar fields to epoch-day values, which is
 * exactly what these samples exercise.
 */
public class TestBritishCutoverChronology_test_BritishCutoverDate_chronology_dateEpochDay {

    /**
     * Sample pairs: a {@link BritishCutoverDate} and the ISO {@link LocalDate} that
     * shares the same epoch-day. Each pair is used to verify that
     * {@code BritishCutoverChronology.INSTANCE.dateEpochDay(iso.toEpochDay())}
     * returns the expected British cutover date.
     *
     * <p>Data is organised into sections that exercise different regions of the
     * calendar:
     * <ol>
     *   <li>Julian era (year 1 and nearby) – demonstrates the 2-day offset
     *       between Julian and ISO dates at the epoch.
     *   <li>Julian leap-year handling (years 4, 100) – where the two calendars
     *       disagree on whether a leap day exists.
     *   <li>Year 0 boundary – negative/zero proleptic years.
     *   <li>The 1582 Gregorian reform – the Vatican's 10-day skip, which Britain
     *       did NOT adopt; dates around 1582 remain Julian in this calendar.
     *   <li>The British cutover itself (Sep 1752) – the 11-day gap between
     *       2 Sep and 14 Sep, including lenient acceptance of "invalid" dates
     *       within the gap.
     *   <li>Post-cutover modern dates – no offset between British Cutover and ISO.
     * </ol>
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // --- Julian era: years 1–4 (2-day offset at epoch) ---
            { BritishCutoverDate.of(1, 1, 1),   LocalDate.of(0,  12, 30) },
            { BritishCutoverDate.of(1, 1, 2),   LocalDate.of(0,  12, 31) },
            { BritishCutoverDate.of(1, 1, 3),   LocalDate.of(1,   1,  1) },
            { BritishCutoverDate.of(1, 2, 28),  LocalDate.of(1,   2, 26) },
            { BritishCutoverDate.of(1, 3, 1),   LocalDate.of(1,   2, 27) },
            { BritishCutoverDate.of(1, 3, 2),   LocalDate.of(1,   2, 28) },
            { BritishCutoverDate.of(1, 3, 3),   LocalDate.of(1,   3,  1) },

            // --- Julian leap year (year 4): extra leap day present in Julian but not ISO ---
            { BritishCutoverDate.of(4, 2, 28),  LocalDate.of(4,   2, 26) },
            { BritishCutoverDate.of(4, 2, 29),  LocalDate.of(4,   2, 27) },
            { BritishCutoverDate.of(4, 3, 1),   LocalDate.of(4,   2, 28) },
            { BritishCutoverDate.of(4, 3, 2),   LocalDate.of(4,   2, 29) },
            { BritishCutoverDate.of(4, 3, 3),   LocalDate.of(4,   3,  1) },

            // --- Year 100: leap under Julian rules but not Gregorian ---
            { BritishCutoverDate.of(100, 2, 28), LocalDate.of(100, 2, 26) },
            { BritishCutoverDate.of(100, 2, 29), LocalDate.of(100, 2, 27) },
            { BritishCutoverDate.of(100, 3, 1),  LocalDate.of(100, 2, 28) },
            { BritishCutoverDate.of(100, 3, 2),  LocalDate.of(100, 3,  1) },
            { BritishCutoverDate.of(100, 3, 3),  LocalDate.of(100, 3,  2) },

            // --- Year 0 boundary (proleptic) ---
            { BritishCutoverDate.of(0, 12, 31),  LocalDate.of(0,  12, 29) },
            { BritishCutoverDate.of(0, 12, 30),  LocalDate.of(0,  12, 28) },

            // --- 1582 Vatican reform: Britain still on Julian, so no gap here ---
            { BritishCutoverDate.of(1582, 10, 4),  LocalDate.of(1582, 10, 14) },
            { BritishCutoverDate.of(1582, 10, 5),  LocalDate.of(1582, 10, 15) },

            // --- Pre-cutover dates near 1752: still Julian, 11-day offset ---
            { BritishCutoverDate.of(1751, 12, 20), LocalDate.of(1751, 12, 31) },
            { BritishCutoverDate.of(1751, 12, 31), LocalDate.of(1752,  1, 11) },
            { BritishCutoverDate.of(1752,  1,  1), LocalDate.of(1752,  1, 12) },

            // --- British cutover month (Sep 1752): days 1–2 are pre-cutover (Julian) ---
            { BritishCutoverDate.of(1752, 9, 1),  LocalDate.of(1752,  9, 12) },
            { BritishCutoverDate.of(1752, 9, 2),  LocalDate.of(1752,  9, 13) },

            // Days 3–13 fall inside the gap; leniently treated as Julian dates
            { BritishCutoverDate.of(1752, 9, 3),  LocalDate.of(1752,  9, 14) }, // leniently accept invalid
            { BritishCutoverDate.of(1752, 9, 13), LocalDate.of(1752,  9, 24) }, // leniently accept invalid

            // Day 14 is the first Gregorian date after the gap – no offset from ISO
            { BritishCutoverDate.of(1752, 9, 14), LocalDate.of(1752,  9, 14) },

            // --- Post-cutover: British Cutover calendar == ISO calendar ---
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
