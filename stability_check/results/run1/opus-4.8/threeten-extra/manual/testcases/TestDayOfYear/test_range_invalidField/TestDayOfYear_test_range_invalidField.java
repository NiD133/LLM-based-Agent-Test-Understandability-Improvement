package org.threeten.extra;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link DayOfYear#range(java.time.temporal.TemporalField)} rejects
 * a field that a day-of-year does not support.
 */
public class TestDayOfYear_test_range_invalidField {

    /** A day-of-year only supports DAY_OF_YEAR, so any other field is unsupported. */
    private static final DayOfYear DAY_OF_YEAR_12 = DayOfYear.of(12);

    @Test
    public void range_withUnsupportedField_throwsUnsupportedTemporalTypeException() {
        assertThrows(
                UnsupportedTemporalTypeException.class,
                () -> DAY_OF_YEAR_12.range(MONTH_OF_YEAR));
    }
}
