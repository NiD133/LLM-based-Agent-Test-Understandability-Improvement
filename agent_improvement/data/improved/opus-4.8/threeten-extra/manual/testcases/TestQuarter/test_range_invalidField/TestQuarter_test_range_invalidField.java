package org.threeten.extra;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Quarter#range(java.time.temporal.TemporalField)} rejects an
 * unsupported temporal field.
 */
public class TestQuarter_test_range_invalidField {

    @Test
    public void range_withUnsupportedField_throwsUnsupportedTemporalType() {
        // MONTH_OF_YEAR is a ChronoField, which Quarter does not support for range().
        assertThrows(
                UnsupportedTemporalTypeException.class,
                () -> Quarter.Q1.range(MONTH_OF_YEAR));
    }
}
