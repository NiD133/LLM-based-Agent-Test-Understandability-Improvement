package org.threeten.extra;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Quarter#get(java.time.temporal.TemporalField)} rejects a field
 * that a quarter-of-year does not support.
 */
public class TestQuarter_test_get_invalidField {

    @Test
    public void test_get_invalidField() {
        // MONTH_OF_YEAR is a ChronoField that Quarter does not support, so get() must reject it.
        assertThrows(UnsupportedTemporalTypeException.class, () -> Quarter.Q2.get(MONTH_OF_YEAR));
    }
}
