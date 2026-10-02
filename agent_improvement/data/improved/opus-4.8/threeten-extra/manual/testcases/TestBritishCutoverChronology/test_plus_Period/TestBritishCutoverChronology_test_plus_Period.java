package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link BritishCutoverDate#plus} when the amount to add is a
 * {@link java.time.chrono.ChronoPeriod} created via
 * {@link BritishCutoverChronology#period(int, int, int)} (years, months, days).
 *
 * <p>The British calendar dropped the days from 1752-09-03 to 1752-09-13
 * (inclusive) when switching from the Julian to the Gregorian calendar, so
 * arithmetic that crosses this gap is the interesting case to cover.
 */
public class TestBritishCutoverChronology_test_plus_Period {

    @Test
    public void test_plus_Period() {
        // Adding 1 month and 3 days lands past the September 1752 cutover gap.
        assertEquals(
                BritishCutoverDate.of(1752, 10, 5),
                BritishCutoverDate.of(1752, 9, 2).plus(BritishCutoverChronology.INSTANCE.period(0, 1, 3)));

        // Adding exactly 1 month moves from August into the cutover-shortened September.
        assertEquals(
                BritishCutoverDate.of(1752, 9, 23),
                BritishCutoverDate.of(1752, 8, 12).plus(BritishCutoverChronology.INSTANCE.period(0, 1, 0)));

        // A modern date well clear of the cutover: add 2 months and 3 days.
        assertEquals(
                BritishCutoverDate.of(2014, 7, 29),
                BritishCutoverDate.of(2014, 5, 26).plus(BritishCutoverChronology.INSTANCE.period(0, 2, 3)));
    }
}
