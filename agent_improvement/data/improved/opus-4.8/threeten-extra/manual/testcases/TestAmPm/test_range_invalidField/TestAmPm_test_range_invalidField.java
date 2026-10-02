package org.threeten.extra;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link AmPm#range(java.time.temporal.TemporalField)} rejects a
 * field that the am-pm enum does not support.
 */
public class TestAmPm_test_range_invalidField {

    /**
     * Querying the range for an unsupported {@code ChronoField} (here
     * {@code MONTH_OF_YEAR}, which has no meaning for an am-pm) must fail with
     * an {@link UnsupportedTemporalTypeException}.
     */
    @Test
    public void range_withUnsupportedField_throwsUnsupportedTemporalTypeException() {
        assertThrows(UnsupportedTemporalTypeException.class, () -> AmPm.AM.range(MONTH_OF_YEAR));
    }
}
