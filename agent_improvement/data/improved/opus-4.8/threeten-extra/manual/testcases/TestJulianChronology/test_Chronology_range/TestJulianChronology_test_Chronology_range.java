package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.ValueRange;

import org.junit.jupiter.api.Test;

/**
 * Verifies the value ranges that the Julian chronology reports for its
 * date-related fields via {@link JulianChronology#range(java.time.temporal.ChronoField)}.
 */
public class TestJulianChronology_test_Chronology_range {

    @Test
    public void test_Chronology_range() {
        // Days of the week always span Monday(1) to Sunday(7).
        assertEquals(ValueRange.of(1, 7), JulianChronology.INSTANCE.range(DAY_OF_WEEK));

        // A month has at least 28 days and at most 31 days.
        assertEquals(ValueRange.of(1, 28, 31), JulianChronology.INSTANCE.range(DAY_OF_MONTH));

        // A year has at least 365 days and at most 366 days (leap year).
        assertEquals(ValueRange.of(1, 365, 366), JulianChronology.INSTANCE.range(DAY_OF_YEAR));

        // A year always has 12 months.
        assertEquals(ValueRange.of(1, 12), JulianChronology.INSTANCE.range(MONTH_OF_YEAR));
    }
}
