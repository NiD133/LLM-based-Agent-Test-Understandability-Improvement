package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link BritishCutoverChronology#dateYearDay(int, int)}.
 *
 * <p>The British cutover year 1752 skipped 11 days (Julian Sep 3–13 never existed),
 * so the year contains only 355 days instead of the usual 366.
 */
public class TestBritishCutoverChronology_test_era_yearDay {

    private static final BritishCutoverChronology CHRONO = BritishCutoverChronology.INSTANCE;

    @Test
    public void test_era_yearDay() {
        // Day 1 of 1752 is January 1
        assertEquals(BritishCutoverDate.of(1752, 1, 1),  CHRONO.dateYearDay(1752, 1));

        // Day 244 of 1752 is August 31 — last day of August, still well before the cutover
        assertEquals(BritishCutoverDate.of(1752, 8, 31), CHRONO.dateYearDay(1752, 244));

        // Day 246 of 1752 is September 2 — the last Julian day before the 11-day gap
        assertEquals(BritishCutoverDate.of(1752, 9, 2),  CHRONO.dateYearDay(1752, 246));

        // Day 247 of 1752 is September 14 — the first Gregorian day (Sep 3–13 were skipped)
        assertEquals(BritishCutoverDate.of(1752, 9, 14), CHRONO.dateYearDay(1752, 247));

        // Day 257 of 1752 is September 24 — ten days into the post-cutover Gregorian sequence
        assertEquals(BritishCutoverDate.of(1752, 9, 24), CHRONO.dateYearDay(1752, 257));

        // Day 258 of 1752 is September 25
        assertEquals(BritishCutoverDate.of(1752, 9, 25), CHRONO.dateYearDay(1752, 258));

        // Day 355 of 1752 is December 31 — the year ends at 355 because of the 11-day cutover gap
        assertEquals(BritishCutoverDate.of(1752, 12, 31), CHRONO.dateYearDay(1752, 355));

        // A modern year (no cutover): day 1 of 2014 is January 1
        assertEquals(BritishCutoverDate.of(2014, 1, 1),  CHRONO.dateYearDay(2014, 1));
    }
}
