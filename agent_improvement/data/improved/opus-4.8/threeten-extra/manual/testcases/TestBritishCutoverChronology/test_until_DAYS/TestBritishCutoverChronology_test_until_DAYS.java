package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link BritishCutoverDate#until(java.time.temporal.Temporal, java.time.temporal.TemporalUnit)}
 * counts whole days correctly between a British-cutover date and an ISO {@link LocalDate}.
 */
public class TestBritishCutoverChronology_test_until_DAYS {

    /**
     * Pairs of equivalent dates: a {@link BritishCutoverDate} and the ISO {@link LocalDate}
     * representing the same point in time. Each pair is the reference point from which the
     * test measures day offsets.
     */
    public static Object[][] data_samples() {
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
            { BritishCutoverDate.of(1752, 9, 2), LocalDate.of(1752, 9, 13) },
            // dates inside the removed cutover gap are accepted leniently
            { BritishCutoverDate.of(1752, 9, 3), LocalDate.of(1752, 9, 14) },
            { BritishCutoverDate.of(1752, 9, 13), LocalDate.of(1752, 9, 24) },
            { BritishCutoverDate.of(1752, 9, 14), LocalDate.of(1752, 9, 14) },
            { BritishCutoverDate.of(1945, 11, 12), LocalDate.of(1945, 11, 12) },
            { BritishCutoverDate.of(2012, 7, 5), LocalDate.of(2012, 7, 5) },
            { BritishCutoverDate.of(2012, 7, 6), LocalDate.of(2012, 7, 6) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_until_DAYS(BritishCutoverDate cutover, LocalDate iso) {
        // The cutover date and iso refer to the same day, so the gap to an offset of that day
        // equals the offset itself.
        assertEquals(0, cutover.until(iso.plusDays(0), DAYS));
        assertEquals(1, cutover.until(iso.plusDays(1), DAYS));
        assertEquals(35, cutover.until(iso.plusDays(35), DAYS));
        assertEquals(-40, cutover.until(iso.minusDays(40), DAYS));
    }
}
