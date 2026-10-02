package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestBritishCutoverChronology_test_plusDays {

    // Maps BritishCutoverDate to its equivalent ISO LocalDate.
    // The British calendar runs 2 days behind ISO before the Julian leap year adjustment,
    // 11 days behind from 1582-10-05 through 1752-09-13 (Julian vs Gregorian drift),
    // and aligns 1:1 with ISO from 1752-09-14 (the cutover date) onward.
    public static Object[][] data_samples() {
        return new Object[][] {
            // Very early dates: British year 1 is offset 2 days behind ISO
            { BritishCutoverDate.of(1, 1, 1),    LocalDate.of(0, 12, 30) },
            { BritishCutoverDate.of(1, 1, 2),    LocalDate.of(0, 12, 31) },
            { BritishCutoverDate.of(1, 1, 3),    LocalDate.of(1,  1,  1) },
            { BritishCutoverDate.of(1, 2, 28),   LocalDate.of(1,  2, 26) },
            { BritishCutoverDate.of(1, 3,  1),   LocalDate.of(1,  2, 27) },
            { BritishCutoverDate.of(1, 3,  2),   LocalDate.of(1,  2, 28) },
            { BritishCutoverDate.of(1, 3,  3),   LocalDate.of(1,  3,  1) },

            // Year 4: Julian leap year, Feb 29 exists in both calendars
            { BritishCutoverDate.of(4, 2, 28),   LocalDate.of(4,  2, 26) },
            { BritishCutoverDate.of(4, 2, 29),   LocalDate.of(4,  2, 27) },
            { BritishCutoverDate.of(4, 3,  1),   LocalDate.of(4,  2, 28) },
            { BritishCutoverDate.of(4, 3,  2),   LocalDate.of(4,  2, 29) },
            { BritishCutoverDate.of(4, 3,  3),   LocalDate.of(4,  3,  1) },

            // Year 100: Julian leap but not Gregorian; offset grows by 1 day after this year
            { BritishCutoverDate.of(100, 2, 28), LocalDate.of(100, 2, 26) },
            { BritishCutoverDate.of(100, 2, 29), LocalDate.of(100, 2, 27) },
            { BritishCutoverDate.of(100, 3,  1), LocalDate.of(100, 2, 28) },
            { BritishCutoverDate.of(100, 3,  2), LocalDate.of(100, 3,  1) },
            { BritishCutoverDate.of(100, 3,  3), LocalDate.of(100, 3,  2) },

            // Year 0 (1 BC): negative proleptic year
            { BritishCutoverDate.of(0, 12, 31),  LocalDate.of(0, 12, 29) },
            { BritishCutoverDate.of(0, 12, 30),  LocalDate.of(0, 12, 28) },

            // Around 1582-10-04/05: the original Gregorian reform (not adopted in Britain,
            // but the Julian/Gregorian offset becomes 10 days from this point onward)
            { BritishCutoverDate.of(1582, 10,  4), LocalDate.of(1582, 10, 14) },
            { BritishCutoverDate.of(1582, 10,  5), LocalDate.of(1582, 10, 15) },

            // Pre-cutover dates in the 1750s: British runs 11 days behind ISO
            { BritishCutoverDate.of(1751, 12, 20), LocalDate.of(1751, 12, 31) },
            { BritishCutoverDate.of(1751, 12, 31), LocalDate.of(1752,  1, 11) },
            { BritishCutoverDate.of(1752,  1,  1), LocalDate.of(1752,  1, 12) },
            { BritishCutoverDate.of(1752,  9,  1), LocalDate.of(1752,  9, 12) },
            { BritishCutoverDate.of(1752,  9,  2), LocalDate.of(1752,  9, 13) },

            // Dates within the gap (Sept 3–13 1752 were skipped in British history);
            // accepted leniently — treated as Julian and shifted 11 days forward
            { BritishCutoverDate.of(1752, 9,  3), LocalDate.of(1752,  9, 14) }, // leniently accept invalid
            { BritishCutoverDate.of(1752, 9, 13), LocalDate.of(1752,  9, 24) },

            // The cutover date and beyond: British aligns 1:1 with ISO
            { BritishCutoverDate.of(1752, 9, 14),   LocalDate.of(1752,  9, 14) },

            // Modern dates: no offset
            { BritishCutoverDate.of(1945, 11, 12), LocalDate.of(1945, 11, 12) },
            { BritishCutoverDate.of(2012,  7,  5), LocalDate.of(2012,  7,  5) },
            { BritishCutoverDate.of(2012,  7,  6), LocalDate.of(2012,  7,  6) },
        };
    }

    /**
     * Verifies that adding a number of days to a BritishCutoverDate yields the correct ISO LocalDate.
     * Tests zero, positive, and negative offsets to cover both forward and backward arithmetic.
     */
    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_plusDays(BritishCutoverDate cutover, LocalDate iso) {
        assertEquals(iso,             LocalDate.from(cutover.plus(  0, DAYS)));
        assertEquals(iso.plusDays( 1), LocalDate.from(cutover.plus(  1, DAYS)));
        assertEquals(iso.plusDays(35), LocalDate.from(cutover.plus( 35, DAYS)));
        assertEquals(iso.plusDays(-1), LocalDate.from(cutover.plus( -1, DAYS)));
        assertEquals(iso.plusDays(-60), LocalDate.from(cutover.plus(-60, DAYS)));
    }
}
