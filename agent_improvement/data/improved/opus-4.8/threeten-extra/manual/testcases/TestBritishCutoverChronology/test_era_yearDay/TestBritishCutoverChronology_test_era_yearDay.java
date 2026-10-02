package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link BritishCutoverChronology#dateYearDay(int, int)} maps a
 * (year, day-of-year) pair to the correct {@link BritishCutoverDate}.
 *
 * <p>The interesting cases surround September 1752, when the British calendar
 * switched from Julian to Gregorian and the days 1752-09-03 through 1752-09-13
 * were skipped. As a result, day-of-year 247 in 1752 lands on 1752-09-14
 * (the day immediately after 1752-09-02), not on a "14th" sequential calendar day.
 */
public class TestBritishCutoverChronology_test_era_yearDay {

    @Test
    public void test_era_yearDay() {
        // First day of an ordinary year.
        assertEquals(
                BritishCutoverDate.of(1752, 1, 1),
                BritishCutoverChronology.INSTANCE.dateYearDay(1752, 1));

        // Days leading up to the 1752 cutover.
        assertEquals(
                BritishCutoverDate.of(1752, 8, 31),
                BritishCutoverChronology.INSTANCE.dateYearDay(1752, 244));
        assertEquals(
                BritishCutoverDate.of(1752, 9, 2),
                BritishCutoverChronology.INSTANCE.dateYearDay(1752, 246));

        // Day-of-year 247 skips the missing days 1752-09-03..1752-09-13.
        assertEquals(
                BritishCutoverDate.of(1752, 9, 14),
                BritishCutoverChronology.INSTANCE.dateYearDay(1752, 247));

        // Days after the cutover continue normally.
        assertEquals(
                BritishCutoverDate.of(1752, 9, 24),
                BritishCutoverChronology.INSTANCE.dateYearDay(1752, 257));
        assertEquals(
                BritishCutoverDate.of(1752, 9, 25),
                BritishCutoverChronology.INSTANCE.dateYearDay(1752, 258));

        // Last day of the (shortened) cutover year: 1752 had only 355 days.
        assertEquals(
                BritishCutoverDate.of(1752, 12, 31),
                BritishCutoverChronology.INSTANCE.dateYearDay(1752, 355));

        // First day of a modern, unaffected year.
        assertEquals(
                BritishCutoverDate.of(2014, 1, 1),
                BritishCutoverChronology.INSTANCE.dateYearDay(2014, 1));
    }
}
