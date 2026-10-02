package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_YEAR;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.ValueRange;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link BritishCutoverChronology#range} returns the correct
 * {@link ValueRange} for each temporal field that deviates from or extends the
 * standard ISO range due to the Julian-to-Gregorian cutover in September 1752.
 *
 * <ul>
 *   <li>{@code DAY_OF_WEEK} – always 1–7, unchanged from ISO.</li>
 *   <li>{@code DAY_OF_MONTH} – 1 to 28–31; lower max because September 1752
 *       only has 19 days but the field still reports the structural maximum.</li>
 *   <li>{@code DAY_OF_YEAR} – 1 to 355–366; minimum max is 355 (cutover year 1752).</li>
 *   <li>{@code MONTH_OF_YEAR} – always 1–12, unchanged from ISO.</li>
 *   <li>{@code ALIGNED_WEEK_OF_MONTH} – 1 to 3–5; minimum max is 3
 *       (September 1752 has only 3 aligned weeks).</li>
 *   <li>{@code ALIGNED_WEEK_OF_YEAR} – 1 to 51–53; minimum max is 51 (cutover year).</li>
 * </ul>
 */
public class TestBritishCutoverChronology_test_Chronology_range {

    private static final BritishCutoverChronology CHRONO = BritishCutoverChronology.INSTANCE;

    @Test
    public void test_Chronology_range() {
        // Fixed 1-to-7 range — identical to ISO
        assertEquals(ValueRange.of(1, 7),        CHRONO.range(DAY_OF_WEEK));

        // Day-of-month: structural range spans 28–31 across all months/years
        assertEquals(ValueRange.of(1, 28, 31),   CHRONO.range(DAY_OF_MONTH));

        // Day-of-year: cutover year 1752 has only 355 days (11 days skipped in Sep)
        assertEquals(ValueRange.of(1, 355, 366), CHRONO.range(DAY_OF_YEAR));

        // Month-of-year: 12 months, same as ISO
        assertEquals(ValueRange.of(1, 12),       CHRONO.range(MONTH_OF_YEAR));

        // Aligned-week-of-month: September 1752 yields only 3 aligned weeks (min max)
        assertEquals(ValueRange.of(1, 3, 5),     CHRONO.range(ALIGNED_WEEK_OF_MONTH));

        // Aligned-week-of-year: cutover year 1752 yields only 51 aligned weeks (min max)
        assertEquals(ValueRange.of(1, 51, 53),   CHRONO.range(ALIGNED_WEEK_OF_YEAR));
    }
}
