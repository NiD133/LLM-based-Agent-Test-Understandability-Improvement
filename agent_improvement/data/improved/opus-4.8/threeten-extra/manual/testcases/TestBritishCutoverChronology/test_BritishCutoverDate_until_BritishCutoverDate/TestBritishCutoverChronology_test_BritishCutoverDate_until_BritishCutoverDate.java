package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.ChronoPeriod;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link BritishCutoverDate#until(java.time.chrono.ChronoLocalDate)} returns a
 * zero-length period when a date is measured against itself, across a range of dates that
 * spans the British (1752) Gregorian cutover.
 */
public class TestBritishCutoverChronology_test_BritishCutoverDate_until_BritishCutoverDate {

    /**
     * A representative spread of British cutover dates: early years, leap-year boundaries,
     * century years, dates around the 1582/1752 cutover gap, and modern dates.
     */
    public static BritishCutoverDate[] data_sampleDates() {
        return new BritishCutoverDate[] {
            BritishCutoverDate.of(1, 1, 1),
            BritishCutoverDate.of(1, 1, 2),
            BritishCutoverDate.of(1, 1, 3),
            BritishCutoverDate.of(1, 2, 28),
            BritishCutoverDate.of(1, 3, 1),
            BritishCutoverDate.of(1, 3, 2),
            BritishCutoverDate.of(1, 3, 3),
            BritishCutoverDate.of(4, 2, 28),
            BritishCutoverDate.of(4, 2, 29),
            BritishCutoverDate.of(4, 3, 1),
            BritishCutoverDate.of(4, 3, 2),
            BritishCutoverDate.of(4, 3, 3),
            BritishCutoverDate.of(100, 2, 28),
            BritishCutoverDate.of(100, 2, 29),
            BritishCutoverDate.of(100, 3, 1),
            BritishCutoverDate.of(100, 3, 2),
            BritishCutoverDate.of(100, 3, 3),
            BritishCutoverDate.of(0, 12, 31),
            BritishCutoverDate.of(0, 12, 30),
            BritishCutoverDate.of(1582, 10, 4),
            BritishCutoverDate.of(1582, 10, 5),
            BritishCutoverDate.of(1751, 12, 20),
            BritishCutoverDate.of(1751, 12, 31),
            BritishCutoverDate.of(1752, 1, 1),
            BritishCutoverDate.of(1752, 9, 1),
            BritishCutoverDate.of(1752, 9, 2),
            // dates in the cutover gap are accepted leniently
            BritishCutoverDate.of(1752, 9, 3),
            BritishCutoverDate.of(1752, 9, 13),
            BritishCutoverDate.of(1752, 9, 14),
            BritishCutoverDate.of(1945, 11, 12),
            BritishCutoverDate.of(2012, 7, 5),
            BritishCutoverDate.of(2012, 7, 6),
        };
    }

    @ParameterizedTest
    @MethodSource("data_sampleDates")
    public void test_BritishCutoverDate_until_BritishCutoverDate(BritishCutoverDate date) {
        ChronoPeriod zeroPeriod = BritishCutoverChronology.INSTANCE.period(0, 0, 0);
        assertEquals(zeroPeriod, date.until(date));
    }
}
