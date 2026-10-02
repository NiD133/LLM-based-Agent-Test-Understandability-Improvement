package org.threeten.extra;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Half#get(java.time.temporal.TemporalField)} rejects a
 * {@code ChronoField} that the half-of-year does not support.
 */
public class TestHalf_test_get_invalidField {

    @Test
    public void get_withUnsupportedChronoField_throwsUnsupportedTemporalTypeException() {
        // MONTH_OF_YEAR is a ChronoField, which Half never supports.
        assertThrows(UnsupportedTemporalTypeException.class, () -> Half.H2.get(MONTH_OF_YEAR));
    }
}
