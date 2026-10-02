package org.threeten.extra;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Half#get(java.time.temporal.TemporalField)} throws
 * {@link UnsupportedTemporalTypeException} when queried with a field that
 * {@code Half} does not support, such as {@code MONTH_OF_YEAR}.
 */
public class TestHalf_test_get_invalidField {

    @Test
    public void test_get_invalidField() {
        // Half only supports HALF_OF_YEAR; any ChronoField other than that must throw
        assertThrows(UnsupportedTemporalTypeException.class, () -> Half.H2.get(MONTH_OF_YEAR));
    }
}
