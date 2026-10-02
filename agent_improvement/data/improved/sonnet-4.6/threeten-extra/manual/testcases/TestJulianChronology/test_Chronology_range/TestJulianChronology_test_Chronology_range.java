package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.ValueRange;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link JulianChronology#range(java.time.temporal.ChronoField)} returns the
 * correct {@link ValueRange} for calendar fields specific to the Julian calendar system.
 * DAY_OF_MONTH and DAY_OF_YEAR are variable-length because Julian leap years add an
 * extra day in February, making the maximum values 31 and 366 respectively.
 */
public class TestJulianChronology_test_Chronology_range {

    @Test
    public void test_Chronology_range_dayOfWeek() {
        // Days of the week always run 1–7, independent of the calendar system
        assertEquals(ValueRange.of(1, 7), JulianChronology.INSTANCE.range(DAY_OF_WEEK));
    }

    @Test
    public void test_Chronology_range_dayOfMonth() {
        // February has 28 days in common years and 29 in Julian leap years (every 4 years);
        // months with 31 days set the maximum to 31
        assertEquals(ValueRange.of(1, 28, 31), JulianChronology.INSTANCE.range(DAY_OF_MONTH));
    }

    @Test
    public void test_Chronology_range_dayOfYear() {
        // Julian leap years (every 4 years, without exception) yield 366-day years
        assertEquals(ValueRange.of(1, 365, 366), JulianChronology.INSTANCE.range(DAY_OF_YEAR));
    }

    @Test
    public void test_Chronology_range_monthOfYear() {
        // The Julian calendar always has exactly 12 months
        assertEquals(ValueRange.of(1, 12), JulianChronology.INSTANCE.range(MONTH_OF_YEAR));
    }
}
