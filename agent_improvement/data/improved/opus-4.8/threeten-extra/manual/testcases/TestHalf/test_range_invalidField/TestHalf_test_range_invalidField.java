package org.threeten.extra;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Half#range(java.time.temporal.TemporalField)} rejects an
 * unsupported field.
 */
public class TestHalf_test_range_invalidField {

    @Test
    public void range_withUnsupportedChronoField_throwsUnsupportedTemporalType() {
        // MONTH_OF_YEAR is a ChronoField, which Half does not support.
        assertThrows(
                UnsupportedTemporalTypeException.class,
                () -> Half.H1.range(MONTH_OF_YEAR));
    }
}
