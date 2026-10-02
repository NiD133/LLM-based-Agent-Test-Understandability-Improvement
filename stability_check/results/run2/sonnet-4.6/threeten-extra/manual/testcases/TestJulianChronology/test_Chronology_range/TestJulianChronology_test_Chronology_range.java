package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.ValueRange;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link JulianChronology#range} returns the correct {@link ValueRange}
 * for each calendar field supported by the Julian chronology.
 */
public class TestJulianChronology_test_Chronology_range {

    @Test
    public void test_Chronology_range() {
        // Day-of-week is always 1–7 regardless of the date
        assertEquals(ValueRange.of(1, 7), JulianChronology.INSTANCE.range(DAY_OF_WEEK));

        // Day-of-month varies: shortest month has 28 days, longest has 31
        assertEquals(ValueRange.of(1, 28, 31), JulianChronology.INSTANCE.range(DAY_OF_MONTH));

        // Day-of-year varies: 365 in a common year, 366 in a Julian leap year
        assertEquals(ValueRange.of(1, 365, 366), JulianChronology.INSTANCE.range(DAY_OF_YEAR));

        // Month-of-year is always 1–12
        assertEquals(ValueRange.of(1, 12), JulianChronology.INSTANCE.range(MONTH_OF_YEAR));
    }
}
