package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link BritishCutoverChronology#dateEpochDay(long)} reconstructs the
 * correct {@link BritishCutoverDate} from an epoch-day value.
 * <p>
 * Each sample pairs a British-cutover date with the ISO {@link LocalDate} that shares
 * its epoch day. Looking up that epoch day via the chronology must return the original
 * British-cutover date. The samples span the proleptic era, the 1582 Gregorian cutover,
 * and the September 1752 British cutover (where days 3-13 were skipped and are only
 * accepted leniently).
 */
public class TestBritishCutoverChronology_test_BritishCutoverDate_chronology_dateEpochDay {

    /**
     * @return rows of {@code { britishCutoverDate, equivalentIsoDate }}
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // Proleptic dates before year 1, where the Julian calendar runs ahead of ISO.
            { BritishCutoverDate.of(1, 1, 1), LocalDate.of(0, 12, 30) },
            { BritishCutoverDate.of(1, 1, 2), LocalDate.of(0, 12, 31) },
            { BritishCutoverDate.of(1, 1, 3), LocalDate.of(1, 1, 1) },
            { BritishCutoverDate.of(1, 2, 28), LocalDate.of(1, 2, 26) },
            { BritishCutoverDate.of(1, 3, 1), LocalDate.of(1, 2, 27) },
            { BritishCutoverDate.of(1, 3, 2), LocalDate.of(1, 2, 28) },
            { BritishCutoverDate.of(1, 3, 3), LocalDate.of(1, 3, 1) },
            { BritishCutoverDate.of(4, 2, 28), LocalDate.of(4, 2, 26) },
            { BritishCutoverDate.of(4, 2, 29), LocalDate.of(4, 2, 27) },
            { BritishCutoverDate.of(4, 3, 1), LocalDate.of(4, 2, 28) },
            { BritishCutoverDate.of(4, 3, 2), LocalDate.of(4, 2, 29) },
            { BritishCutoverDate.of(4, 3, 3), LocalDate.of(4, 3, 1) },
            { BritishCutoverDate.of(100, 2, 28), LocalDate.of(100, 2, 26) },
            { BritishCutoverDate.of(100, 2, 29), LocalDate.of(100, 2, 27) },
            { BritishCutoverDate.of(100, 3, 1), LocalDate.of(100, 2, 28) },
            { BritishCutoverDate.of(100, 3, 2), LocalDate.of(100, 3, 1) },
            { BritishCutoverDate.of(100, 3, 3), LocalDate.of(100, 3, 2) },
            { BritishCutoverDate.of(0, 12, 31), LocalDate.of(0, 12, 29) },
            { BritishCutoverDate.of(0, 12, 30), LocalDate.of(0, 12, 28) },

            // Around the 1582 Gregorian cutover (not observed by the British calendar).
            { BritishCutoverDate.of(1582, 10, 4), LocalDate.of(1582, 10, 14) },
            { BritishCutoverDate.of(1582, 10, 5), LocalDate.of(1582, 10, 15) },

            // Approaching and crossing the British cutover of September 1752.
            { BritishCutoverDate.of(1751, 12, 20), LocalDate.of(1751, 12, 31) },
            { BritishCutoverDate.of(1751, 12, 31), LocalDate.of(1752, 1, 11) },
            { BritishCutoverDate.of(1752, 1, 1), LocalDate.of(1752, 1, 12) },
            { BritishCutoverDate.of(1752, 9, 1), LocalDate.of(1752, 9, 12) },
            { BritishCutoverDate.of(1752, 9, 2), LocalDate.of(1752, 9, 13) },
            // The skipped days 1752-09-03..13 are accepted leniently and map into the gap.
            { BritishCutoverDate.of(1752, 9, 3), LocalDate.of(1752, 9, 14) },
            { BritishCutoverDate.of(1752, 9, 13), LocalDate.of(1752, 9, 24) },
            { BritishCutoverDate.of(1752, 9, 14), LocalDate.of(1752, 9, 14) },

            // Modern dates, where the British-cutover and ISO calendars agree.
            { BritishCutoverDate.of(1945, 11, 12), LocalDate.of(1945, 11, 12) },
            { BritishCutoverDate.of(2012, 7, 5), LocalDate.of(2012, 7, 5) },
            { BritishCutoverDate.of(2012, 7, 6), LocalDate.of(2012, 7, 6) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_BritishCutoverDate_chronology_dateEpochDay(BritishCutoverDate expectedCutoverDate, LocalDate isoEquivalent) {
        BritishCutoverDate fromEpochDay = BritishCutoverChronology.INSTANCE.dateEpochDay(isoEquivalent.toEpochDay());

        assertEquals(expectedCutoverDate, fromEpochDay);
    }
}
