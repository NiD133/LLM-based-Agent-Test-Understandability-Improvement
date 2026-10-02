package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link BritishCutoverChronology#date(java.time.temporal.TemporalAccessor)}
 * converts an ISO {@link LocalDate} into the equivalent {@link BritishCutoverDate}.
 */
public class TestBritishCutoverChronology_test_Chronology_date_Temporal {

    /**
     * Pairs of equivalent dates: each ISO {@link LocalDate} and the
     * {@link BritishCutoverDate} it should map to.
     * <p>
     * The British cutover chronology follows the Julian calendar up to the 1752
     * cutover and the Gregorian calendar afterwards, so the cutover and ISO dates
     * differ in the early Julian range and around the 1582/1752 transitions, then
     * coincide from late 1752 onwards.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // early years: Julian calendar runs ahead of proleptic ISO
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

            // around the 1582 Gregorian reform (not applied by the British cutover)
            { BritishCutoverDate.of(1582, 10, 4), LocalDate.of(1582, 10, 14) },
            { BritishCutoverDate.of(1582, 10, 5), LocalDate.of(1582, 10, 15) },

            // around the 1752 British cutover (2 Sep was followed by 14 Sep)
            { BritishCutoverDate.of(1751, 12, 20), LocalDate.of(1751, 12, 31) },
            { BritishCutoverDate.of(1751, 12, 31), LocalDate.of(1752, 1, 11) },
            { BritishCutoverDate.of(1752, 1, 1), LocalDate.of(1752, 1, 12) },
            { BritishCutoverDate.of(1752, 9, 1), LocalDate.of(1752, 9, 12) },
            { BritishCutoverDate.of(1752, 9, 2), LocalDate.of(1752, 9, 13) },
            { BritishCutoverDate.of(1752, 9, 3), LocalDate.of(1752, 9, 14) }, // leniently accept invalid cutover-gap date
            { BritishCutoverDate.of(1752, 9, 13), LocalDate.of(1752, 9, 24) }, // leniently accept invalid cutover-gap date
            { BritishCutoverDate.of(1752, 9, 14), LocalDate.of(1752, 9, 14) },

            // after the cutover the calendars coincide
            { BritishCutoverDate.of(1945, 11, 12), LocalDate.of(1945, 11, 12) },
            { BritishCutoverDate.of(2012, 7, 5), LocalDate.of(2012, 7, 5) },
            { BritishCutoverDate.of(2012, 7, 6), LocalDate.of(2012, 7, 6) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_Chronology_date_Temporal(BritishCutoverDate cutover, LocalDate iso) {
        assertEquals(cutover, BritishCutoverChronology.INSTANCE.date(iso));
    }
}
