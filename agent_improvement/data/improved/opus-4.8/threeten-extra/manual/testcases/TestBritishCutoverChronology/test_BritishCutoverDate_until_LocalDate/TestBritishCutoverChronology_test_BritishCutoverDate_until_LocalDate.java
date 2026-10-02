package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link BritishCutoverDate#until(java.time.temporal.Temporal)} when the end point is the
 * equivalent ISO {@link LocalDate}.
 *
 * <p>Each sample pairs a {@code BritishCutoverDate} with the ISO date that represents the very same
 * day on the time line. Because both arguments denote the same instant in time, the elapsed period
 * between them must always be zero (zero years, zero months, zero days).
 */
public class TestBritishCutoverChronology_test_BritishCutoverDate_until_LocalDate {

    /**
     * Pairs of {@code {britishCutoverDate, equivalentIsoDate}} that denote the same day.
     *
     * <p>The pairs span the proleptic early years, the 1582 Gregorian reform date (which the British
     * calendar ignores), and the British cutover of September 1752 where 1752-09-03 through
     * 1752-09-13 were skipped. Some British dates inside the skipped gap are accepted leniently and
     * map onto the following valid ISO day.
     */
    public static Object[][] data_equivalentBritishAndIsoDates() {
        return new Object[][] {
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
            // 1582 Gregorian reform: ignored by the British calendar, so the dates keep diverging.
            { BritishCutoverDate.of(1582, 10, 4), LocalDate.of(1582, 10, 14) },
            { BritishCutoverDate.of(1582, 10, 5), LocalDate.of(1582, 10, 15) },
            { BritishCutoverDate.of(1751, 12, 20), LocalDate.of(1751, 12, 31) },
            { BritishCutoverDate.of(1751, 12, 31), LocalDate.of(1752, 1, 11) },
            { BritishCutoverDate.of(1752, 1, 1), LocalDate.of(1752, 1, 12) },
            { BritishCutoverDate.of(1752, 9, 1), LocalDate.of(1752, 9, 12) },
            { BritishCutoverDate.of(1752, 9, 2), LocalDate.of(1752, 9, 13) },
            // 1752-09-03..1752-09-13 were skipped; these invalid days are accepted leniently.
            { BritishCutoverDate.of(1752, 9, 3), LocalDate.of(1752, 9, 14) },
            { BritishCutoverDate.of(1752, 9, 13), LocalDate.of(1752, 9, 24) },
            { BritishCutoverDate.of(1752, 9, 14), LocalDate.of(1752, 9, 14) },
            // From the cutover onward the British and ISO calendars agree.
            { BritishCutoverDate.of(1945, 11, 12), LocalDate.of(1945, 11, 12) },
            { BritishCutoverDate.of(2012, 7, 5), LocalDate.of(2012, 7, 5) },
            { BritishCutoverDate.of(2012, 7, 6), LocalDate.of(2012, 7, 6) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_equivalentBritishAndIsoDates")
    public void until_equivalentIsoDate_returnsZeroPeriod(BritishCutoverDate britishDate, LocalDate equivalentIsoDate) {
        assertEquals(BritishCutoverChronology.INSTANCE.period(0, 0, 0), britishDate.until(equivalentIsoDate));
    }
}
