package org.threeten.extra;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Quarter#get(java.time.temporal.TemporalField)} rejects a field
 * it does not support.
 */
public class TestQuarter_test_get_invalidField {

    @Test
    public void get_withUnsupportedField_throwsException() {
        // MONTH_OF_YEAR is a ChronoField, which Quarter does not support.
        assertThrows(UnsupportedTemporalTypeException.class, () -> Quarter.Q2.get(MONTH_OF_YEAR));
    }
}
