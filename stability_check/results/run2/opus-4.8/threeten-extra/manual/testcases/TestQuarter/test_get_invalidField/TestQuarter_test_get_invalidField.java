package org.threeten.extra;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Quarter#get(java.time.temporal.TemporalField)} rejects a field
 * that a quarter-of-year cannot supply.
 */
public class TestQuarter_test_get_invalidField {

    /**
     * MONTH_OF_YEAR is a ChronoField, and the only ChronoField a Quarter supports is
     * QUARTER_OF_YEAR. Requesting any other ChronoField must fail with
     * UnsupportedTemporalTypeException.
     */
    @Test
    public void get_withUnsupportedChronoField_throwsUnsupportedTemporalTypeException() {
        assertThrows(
                UnsupportedTemporalTypeException.class,
                () -> Quarter.Q2.get(MONTH_OF_YEAR));
    }
}
