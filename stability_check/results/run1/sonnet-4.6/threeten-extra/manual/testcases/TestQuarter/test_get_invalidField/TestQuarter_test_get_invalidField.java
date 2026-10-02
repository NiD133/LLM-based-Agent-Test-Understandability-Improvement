package org.threeten.extra;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Quarter#get(java.time.temporal.TemporalField)} throws
 * {@link UnsupportedTemporalTypeException} when called with a {@code ChronoField}
 * that is not supported by {@code Quarter} (i.e., any field other than
 * {@code IsoFields.QUARTER_OF_YEAR}).
 */
public class TestQuarter_test_get_invalidField {

    @Test
    public void test_get_invalidField() {
        // Quarter only supports QUARTER_OF_YEAR; querying MONTH_OF_YEAR must throw
        assertThrows(UnsupportedTemporalTypeException.class, () -> Quarter.Q2.get(MONTH_OF_YEAR));
    }
}
