package org.threeten.extra;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Half#getLong} rejects an unsupported field.
 */
public class TestHalf_test_getLong_invalidField {

    @Test
    public void getLong_withUnsupportedChronoField_throwsUnsupportedTemporalTypeException() {
        // MONTH_OF_YEAR is a ChronoField other than HALF_OF_YEAR, so it is not supported by Half.
        assertThrows(UnsupportedTemporalTypeException.class, () -> Half.H2.getLong(MONTH_OF_YEAR));
    }
}
