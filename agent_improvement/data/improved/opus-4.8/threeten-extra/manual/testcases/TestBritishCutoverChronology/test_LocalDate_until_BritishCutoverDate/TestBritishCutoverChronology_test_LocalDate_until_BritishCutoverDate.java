package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Period;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link LocalDate#until(java.time.chrono.ChronoLocalDate)} returns a
 * zero {@link Period} when the target {@link BritishCutoverDate} denotes the very
 * same day on the timeline as the ISO date.
 * <p>
 * Each sample pairs a {@code BritishCutoverDate} with the ISO {@code LocalDate}
 * that falls on the identical day. Because both operands describe the same day,
 * the period between them must always be {@link Period#ZERO}.
 */
public class TestBritishCutoverChronology_test_LocalDate_until_BritishCutoverDate {

    /**
     * Pairs of equivalent dates: {British cutover date, equivalent ISO date}.
     * The two dates in each row represent the same point on the timeline, so the
     * period from the ISO date to the cutover date is expected to be zero.
     */
    public static Object[][] data_equivalentDates() {
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
            { BritishCutoverDate.of(1582, 10, 4), LocalDate.of(1582, 10, 14) },
            { BritishCutoverDate.of(1582, 10, 5), LocalDate.of(1582, 10, 15) },
            { BritishCutoverDate.of(1751, 12, 20), LocalDate.of(1751, 12, 31) },
            { BritishCutoverDate.of(1751, 12, 31), LocalDate.of(1752, 1, 11) },
            { BritishCutoverDate.of(1752, 1, 1), LocalDate.of(1752, 1, 12) },
            { BritishCutoverDate.of(1752, 9, 1), LocalDate.of(1752, 9, 12) },
            // dates inside the September 1752 cutover gap are leniently accepted
            { BritishCutoverDate.of(1752, 9, 2), LocalDate.of(1752, 9, 13) },
            { BritishCutoverDate.of(1752, 9, 3), LocalDate.of(1752, 9, 14) },
            { BritishCutoverDate.of(1752, 9, 13), LocalDate.of(1752, 9, 24) },
            { BritishCutoverDate.of(1752, 9, 14), LocalDate.of(1752, 9, 14) },
            { BritishCutoverDate.of(1945, 11, 12), LocalDate.of(1945, 11, 12) },
            { BritishCutoverDate.of(2012, 7, 5), LocalDate.of(2012, 7, 5) },
            { BritishCutoverDate.of(2012, 7, 6), LocalDate.of(2012, 7, 6) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_equivalentDates")
    public void localDate_until_equivalentBritishCutoverDate_isZeroPeriod(BritishCutoverDate cutover, LocalDate iso) {
        assertEquals(Period.ZERO, iso.until(cutover));
    }
}
