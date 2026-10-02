package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.ValueRange;

import org.junit.jupiter.api.Test;

/**
 * Verifies the valid value ranges that the Julian chronology reports for its
 * core date fields via {@link JulianChronology#range(java.time.temporal.ChronoField)}.
 */
public class TestJulianChronology_test_Chronology_range {

    @Test
    public void range_returnsExpectedBoundsForEachDateField() {
        // day-of-week is always 1..7
        assertEquals(ValueRange.of(1, 7), JulianChronology.INSTANCE.range(DAY_OF_WEEK));
        // day-of-month spans 1..31, but the smallest maximum (February) is 28
        assertEquals(ValueRange.of(1, 28, 31), JulianChronology.INSTANCE.range(DAY_OF_MONTH));
        // day-of-year spans 1..366, but the smallest maximum (non-leap year) is 365
        assertEquals(ValueRange.of(1, 365, 366), JulianChronology.INSTANCE.range(DAY_OF_YEAR));
        // month-of-year is always 1..12
        assertEquals(ValueRange.of(1, 12), JulianChronology.INSTANCE.range(MONTH_OF_YEAR));
    }
}
